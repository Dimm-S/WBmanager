package com.dimm.wbmanager.order;

import com.dimm.wbmanager.item.Item;
import com.dimm.wbmanager.item.ItemService;
import com.dimm.wbmanager.order.dto.OrderInputDto;
import com.dimm.wbmanager.updatestat.UpdateStatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UpdateStatService updateStatService;
    private final ItemService itemService;
    private final static String API_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
            ".eyJhY2Nlc3NJRCI6IjRmYTI1YzU3LTRmZDMtNDI4MC1hZDI4LTBmYmRhNzUwYmIzMSJ9" +
            ".71_50subAYWjteFgH0hViHwT2ov6blHTCgTaWl6KznQ";

    @Override
    public void updateTable() throws IOException, InterruptedException {

        final RestTemplate restTemplate = new RestTemplate();
        final String incomes = restTemplate.getForObject
                ("https://suppliers-stats.wildberries.ru/api/v1/supplier/orders" +
                        "?dateFrom=2022-09-01&key=Yjk0ZDQyZjYtMDBkNy00YjcwLTk4NDItNzZhMGJiNjk2Nzdm", String.class);
        ObjectMapper objectMapper = new ObjectMapper();

        CollectionType listType = objectMapper   //описание типа коллекции, в которую надо преобразовать String
                .getTypeFactory()
                .constructCollectionType(ArrayList.class, OrderInputDto.class);
        List<OrderInputDto> ordersDtoList = objectMapper.readValue(incomes, listType); // маппинг String в лист объектов

        List<Order> ordersList = ordersDtoList.stream()
                .map(orderMapper::mapInputToOrder)
                .collect(Collectors.toList());

        orderRepository.saveAll(ordersList);
    }

    /** Количество заказов полное (без учёта отмен) и общая стоимость этих заказов (цена за минусом скидки),
     за указанные даты */
    @Override
    public List<List<Object[]>> getOrdersAndSum(LocalDate from, LocalDate to) {
        return orderRepository.getOrdersAndSum(from, to);
    }

    /** Количество заказов полное (без учёта отмен) и общая стоимость этих заказов (цена за минусом скидки),
     за конкретную дату */
    @Override
    public List<List<Object[]>> getOrdersAndSumByDate(String date) {
        return orderRepository.getOrdersAndSumByDate(LocalDate.parse(date));
    }

    @Override
    public List<List<Object[]>> getMonthOrdersAndSumByItems() {
        return orderRepository.getMonthOrdersAndSumByItems();
    }

    /** Обновление базы заказов по расписанию */
    @Scheduled(initialDelay = 60000, fixedDelay = 1800000)
    public void ordersAutoUpdate() {
        log.info("Выполняется обновление заказов");
        Order lastOrder = orderRepository.getLast();
        LocalDateTime date = lastOrder.getLastChangeDate();
        log.info("Последняя доступная дата {} ", date);

        WebClient client = WebClient.builder()
                .baseUrl("https://statistics-api.wildberries.ru/api/v1/supplier/orders?dateFrom=" + date.plusSeconds(1))
                .defaultHeader("Authorization", API_TOKEN)
                .defaultHeader("Content-Type", "application/json")
                .build();

        Flux<OrderInputDto> orders = client
                .get()
                .retrieve()
                .bodyToFlux(OrderInputDto.class);

        List<OrderInputDto> ordersDtoList = orders.collectList().block();

        if (ordersDtoList == null) {
            log.info("Нет новых записей");
        } else {
            for (OrderInputDto dto : ordersDtoList) {
                if (checkOdid(dto.getOdid())) { //если запись с таким odid уже есть, то её необходимо обновить (через удаление)
                    log.info("Обновление записи {}", dto.getOdid());
                    orderRepository.deleteById(orderRepository.findByOdid(dto.getOdid()).getId()); //удаление записи с существующим odid
                    orderRepository.save(orderMapper.mapInputToOrder(dto)); //добавление новой записи
                    updateStatService.addRecord("orders", true, dto.getOdid()); //запись в статистику
                } else {
                    try {
                        orderRepository.save(orderMapper.mapInputToOrder(dto)); //добавление новой записи
                        log.info("Добавлена новая запись");
                        updateStatService.addRecord("orders", false, dto.getOdid()); //запись в статистику
                    } catch (DataIntegrityViolationException exception) {
                        itemService.saveNewItem(new Item(dto.getBarcode(), dto.getBrand(), dto.getSupplierArticle(),
                                dto.getTechSize(), dto.getNmId(), "Unidentified item"));
                        log.info("Добавлен новый товар, требуется корректировка");
                        orderRepository.save(orderMapper.mapInputToOrder(dto)); //добавление новой записи
                        log.info("Добавлена новая запись");
                        updateStatService.addRecord("orders", false, dto.getOdid()); //запись в статистику
                    }
                }
            }
        }
    }

    /**
     * Проверка существования заказа
     * @param odid уникальный идентификатор позиции заказа
     * @return true или false
     */
    private Boolean checkOdid(Long odid) {
        return orderRepository.findByOdid(odid) != null;
    }
}

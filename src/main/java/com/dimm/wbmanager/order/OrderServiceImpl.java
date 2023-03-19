package com.dimm.wbmanager.order;

import com.dimm.wbmanager.order.dto.OrderInputDto;
import com.dimm.wbmanager.updatestat.UpdateStatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UpdateStatService updateStatService;
    private final static String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2Nlc3NJRCI6IjRmYTI1YzU3LTRmZDMtNDI4MC1hZDI4LTBmYmRhNzUwYmIzMSJ9.71_50subAYWjteFgH0hViHwT2ov6blHTCgTaWl6KznQ";

    @Override
    public void updateTable() throws IOException, InterruptedException {

        final RestTemplate restTemplate = new RestTemplate();
        final String incomes = restTemplate.getForObject
                ("https://suppliers-stats.wildberries.ru/api/v1/supplier/orders" +
                        "?dateFrom=2022-09-01&key=Yjk0ZDQyZjYtMDBkNy00YjcwLTk4NDItNzZhMGJiNjk2Nzdm", String.class);
        ObjectMapper objectMapper = new ObjectMapper();

        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, OrderInputDto.class); //описание типа коллекции, в которую надо преобразовать String
        List<OrderInputDto> ordersDtoList = objectMapper.readValue(incomes, listType); // маппинг String в лист объектов

        List<Order> ordersList = ordersDtoList.stream()
                .map(orderMapper::mapInputToOrder)
                .collect(Collectors.toList());

        orderRepository.saveAll(ordersList);
    }

    @Override
    public List<List<Object[]>> getOrdersAndSum() {
        return orderRepository.getOrdersAndSum();
    }

    @Override
    public List<List<Object[]>> getOrdersAndSumByDate(String date) {
        return orderRepository.getOrdersAndSumByDate(LocalDate.parse(date));
    }

    @Scheduled(initialDelay = 60000, fixedDelay = 1800000)
    public void ordersAutoUpdate() {
        log.info("Выполняется обновление заказов");
        Order lastOrder = orderRepository.getLast();
        LocalDateTime date = lastOrder.getLastChangeDate();
        log.info("Последняя доступная дата {} ", date);

        WebClient client = WebClient.builder()
                .baseUrl("https://statistics-api.wildberries.ru/api/v1/supplier/orders?dateFrom=" + date.plusSeconds(1))
                .defaultHeader("Authorization", TOKEN)
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
                    orderRepository.save(orderMapper.mapInputToOrder(dto)); //добавление новой записи
                    log.info("Добавлена новая запись");
                    updateStatService.addRecord("orders", false, dto.getOdid()); //запись в статистику
                }
            }
        }
    }

    private Boolean checkOdid(Long odid) {
        return orderRepository.findByOdid(odid) != null;
    }
}

package com.dimm.wbmanager.order;

import com.dimm.wbmanager.order.dto.OrderInputDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    @Scheduled(initialDelay = 30000, fixedDelay = 60000)
    public void ordersAutoUpdate() {
        log.info("Выполняется обновление заказов");
        Order order = orderRepository.getLast();
        LocalDateTime date = order.getLastChangeDate();
        log.info("Последняя доступная дата {} ", date);
    }
}

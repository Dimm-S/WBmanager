package com.dimm.wbmanager.sale;

import com.dimm.wbmanager.sale.dto.SaleInputDto;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;

    @Override
    public void updateTable() throws IOException, InterruptedException {
        final RestTemplate restTemplate = new RestTemplate();
        final String sales = restTemplate.getForObject
                ("https://suppliers-stats.wildberries.ru/api/v1/supplier/sales" +
                        "?dateFrom=2022-09-01&key=Yjk0ZDQyZjYtMDBkNy00YjcwLTk4NDItNzZhMGJiNjk2Nzdm", String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, SaleInputDto.class); //описание типа коллекции, в которую надо преобразовать String
        List<SaleInputDto> salesDtoList = objectMapper.readValue(sales, listType); // маппинг String в лист объектов

        List<Sale> ordersList = salesDtoList.stream()
                .map(saleMapper::mapInputToSale)
                .collect(Collectors.toList());

        saleRepository.saveAll(ordersList);
    }

    @Override
    public List<List<Object>> getSalesByDays() {
        List<Object[]> salesByDay = saleRepository.getSalesByDays();
        List<List<Object>> salesList = new ArrayList<>();
        for (Object[] l : salesByDay) {
            salesList.add(saleMapper.mapArrayToList(l));
        }
        return salesList;
    }

    @Override
    public List<List<Object[]>> getSalesAndSum() {
        return saleRepository.getSalesAndSum();
    }

    @Override
    public List<List<Object[]>> getReturnsAndSum() {
        return saleRepository.getReturnsAndSum();
    }

    @Override
    public List<List<Object[]>> getForPay() {
        return saleRepository.getForPay();
    }

    @Override
    public List<List<Object[]>> getSalesAndSumAndReturnsAndForPayByDate(String date) {
        return saleRepository.getSalesAndSumAndReturnsAndForPayByDate(LocalDate.parse(date));
    }
}

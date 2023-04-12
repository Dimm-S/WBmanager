package com.dimm.wbmanager.reportdetailbyperiod;

import com.dimm.wbmanager.analytics.AnalyticsMapper;
import com.dimm.wbmanager.analytics.dto.AmountByMonthDto;
import com.dimm.wbmanager.reportdetailbyperiod.dto.ReportDetailByPeriodInputDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportDetailByPeriodServiceImpl implements ReportDetailByPeriodService {
    private final ReportDetailByPeriodRepository reportDetailByPeriodRepository;
    private final ReportDetailByPeriodMapper reportDetailByPeriodMapper;
    private final AnalyticsMapper analyticsMapper;
    private final static String API_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
            ".eyJhY2Nlc3NJRCI6IjRmYTI1YzU3LTRmZDMtNDI4MC1hZDI4LTBmYmRhNzUwYmIzMSJ9" +
            ".71_50subAYWjteFgH0hViHwT2ov6blHTCgTaWl6KznQ";

    @Override
    public void updateTable() throws JsonProcessingException {
/*
        final RestTemplate restTemplate = new RestTemplate();
        final String report = restTemplate.getForObject
                ("https://suppliers-stats.wildberries.ru/api/v1/supplier/reportDetailByPeriod" +
                        "?dateFrom=2022-01-01&key=Yjk0ZDQyZjYtMDBkNy00YjcwLTk4NDItNzZhMGJiNjk2Nzdm" +
                        "&dateto=2023-01-25", String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType listType = objectMapper
                .getTypeFactory()
                .constructCollectionType(ArrayList.class, ReportDetailByPeriodInputDto.class); //описание типа коллекции, в которую надо преобразовать String
        List<ReportDetailByPeriodInputDto> reportsDtoList = objectMapper.readValue(report, listType); // маппинг String в лист объектов

        List<ReportDetailByPeriod> reportsList = reportsDtoList.stream()
                .map(reportDetailByPeriodMapper::mapInputToReport)
                .collect(Collectors.toList());

        reportDetailByPeriodRepository.saveAll(reportsList);
*/
        WebClient client = WebClient.builder()
                .baseUrl("https://statistics-api.wildberries.ru/api/v1/supplier/reportDetailByPeriod" +
                        "?dateFrom=2023-01-30&dateto=2023-04-02")
                .defaultHeader("Authorization", API_TOKEN)
                .defaultHeader("Content-Type", "application/json")
                .build();

        Flux<ReportDetailByPeriodInputDto> reports = client
                .get()
                .retrieve()
                .bodyToFlux(ReportDetailByPeriodInputDto.class);

        List<ReportDetailByPeriodInputDto> reportsDtoList = reports.collectList().block();

        for (ReportDetailByPeriodInputDto dto : reportsDtoList) {
            reportDetailByPeriodRepository.save(reportDetailByPeriodMapper.mapInputToReport(dto));
        }

    }

    @Override
    public List<AmountByMonthDto> getAmountByMonthForTable() {
        List<Object[]> list = reportDetailByPeriodRepository.getAmountByMonth();
        List<Object[]> returns = reportDetailByPeriodRepository.getReturns();
        List<Object[]> amount = new ArrayList<>();
        for (int i = 0; i < list.size() - 1; i++) {  //TODO возможен разный размер list-ов
            Object[] array ={list.get(i)[0], list.get(i)[1],
                    (Float) list.get(i)[2] - (Float) returns.get(i)[2],
                    (Float) list.get(i)[3] - (Float) returns.get(i)[3]};
            amount.add(array);
        }

        return amount.stream()
                .map(analyticsMapper::mapToAmountByMonthFromObject)
                .collect(Collectors.toList());
    }

    @Override
    public List<List<Object>> getAmountByMonthForChart() {
        List<Object[]> list = reportDetailByPeriodRepository.getAmountByMonth();
        List<Object[]> returns = reportDetailByPeriodRepository.getReturns();
        List<Object[]> amount = new ArrayList<>();
        for (int i = 0; i < list.size() - 1; i++) {  //TODO возможен разный размер list-ов
            Object[] array ={list.get(i)[0], list.get(i)[1],
                    (Float) list.get(i)[2] - (Float) returns.get(i)[2],
                    (Float) list.get(i)[3] - (Float) returns.get(i)[3]};
            amount.add(array);
        }
        List<List<Object>> listlist = new ArrayList<>();
        for (Object[] l : amount) {
            listlist.add(analyticsMapper.mapArrayToList(l));
        }
        return listlist;
    }

    @Override
    public List<List<Object[]>> getMonthSalesAndBuybacksByItems() {
        return reportDetailByPeriodRepository.getMonthSalesAndBuybacksByItems();
    }
}

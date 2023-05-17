package com.dimm.wbmanager.reportdetailbyperiod;

import com.dimm.wbmanager.Month;
import com.dimm.wbmanager.analytics.AnalyticsMapper;
import com.dimm.wbmanager.analytics.dto.AmountByMonthDto;
import com.dimm.wbmanager.reportdetailbyperiod.dto.ReportDetailByPeriodInputDto;
import com.dimm.wbmanager.utils.Token;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportDetailByPeriodServiceImpl implements ReportDetailByPeriodService {
    private final ReportDetailByPeriodRepository reportDetailByPeriodRepository;
    private final ReportDetailByPeriodMapper reportDetailByPeriodMapper;
    private final AnalyticsMapper analyticsMapper;
    private final Token token;

    @Override
    public void updateTable() throws JsonProcessingException {

        LocalDateTime dateFrom = reportDetailByPeriodRepository.getLast().getDateTo().plusDays(1);
        LocalDateTime dateTo = dateFrom.plusDays(6);

        WebClient client = WebClient.builder()
                .baseUrl("https://statistics-api.wildberries.ru/api/v1/supplier/reportDetailByPeriod" +
                        "?dateFrom=" + dateFrom.toLocalDate() + "&dateto=" + dateTo.toLocalDate())
                .defaultHeader("Authorization", token.getToken())
                .defaultHeader("Content-Type", "application/json")
                .build();

        Flux<ReportDetailByPeriodInputDto> reports = client
                .get()
                .retrieve()
                .bodyToFlux(ReportDetailByPeriodInputDto.class);

        List<ReportDetailByPeriodInputDto> reportsDtoList = reports.collectList().block();

        if (reportsDtoList == null) {
            log.info("Нет новых записей");
        } else {
            for (ReportDetailByPeriodInputDto dto : reportsDtoList) {
                reportDetailByPeriodRepository.save(reportDetailByPeriodMapper.mapInputToReport(dto));
            }
        }
    }

    @Override
    public List<AmountByMonthDto> getAmountByMonthForTable() {
        List<Object[]> list = reportDetailByPeriodRepository.getAmountByMonth();
        List<Object[]> returns = reportDetailByPeriodRepository.getReturns();
        List<Object[]> amount = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {  //TODO возможен разный размер list-ов
            Object[] array = {list.get(i)[0], list.get(i)[1],
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
        List<Object[]> sales = reportDetailByPeriodRepository.getAmountByMonth();
        List<Object[]> returns = reportDetailByPeriodRepository.getReturns();
        List<Object[]> amount = new ArrayList<>();
        for (int i = 0; i < sales.size(); i++) {  //TODO возможен разный размер list-ов
            Object[] array = {sales.get(i)[0], sales.get(i)[1],
                    (Float) sales.get(i)[2] - (Float) returns.get(i)[2],
                    -((Float) sales.get(i)[3] - (Float) returns.get(i)[3]),
                    (-((Float) sales.get(i)[3] - (Float) returns.get(i)[3])) /
                            ((Float) sales.get(i)[2] - (Float) returns.get(i)[2]) * 100};
            amount.add(array);
        }
        List<List<Object>> listlist = new ArrayList<>();
        for (Object[] l : amount) {
            listlist.add(analyticsMapper.mapArrayToList(l));
        }
        return listlist;
    }

    @Override
    public List<Object[]> getSalesAndReturnsByMonths() {
        return reportDetailByPeriodRepository.getSalesAndReturnsByMonths();
    }

    /**
     * Продажи и возвраты за месяц с группировкой по наименованиям товаров
     * @param month месяц
     * @return
     */
    @Override
    public List<List<Object[]>> getMonthSalesAndBuybacksByItems(String month) {
        return reportDetailByPeriodRepository.getMonthSalesAndBuybacksByItems(Month.valueOf(month).ordinal() + 1);
    }

    /**
     * Продажи и возвараты товара помесячно
     */
    @Override
    public List<Object[]> getItemSalesAndReturnsByMonths(String item) {
        return reportDetailByPeriodRepository.getItemSalesAndReturnsByMonths(item);
    }

    @Override
    public List<List<Object>> getBrandsDistr() {
        List<Object[]> brands = reportDetailByPeriodRepository.getBrandsDistr();
        List<List<Object>> listlist = new ArrayList<>();
        for (Object[] l : brands) {
            listlist.add(reportDetailByPeriodMapper.mapArrayToList(l));
        }
        return listlist;
    }

    @Override
    public List<List<Object>> getTopItems() {
        List<Object[]> items = reportDetailByPeriodRepository.getTopItems();
        List<List<Object>> listlist = new ArrayList<>();
        for (Object[] l : items) {
            listlist.add(reportDetailByPeriodMapper.mapArrayToList(l));
        }
        return listlist;
    }
}

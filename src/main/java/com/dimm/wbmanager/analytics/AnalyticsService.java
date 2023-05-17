package com.dimm.wbmanager.analytics;

import com.dimm.wbmanager.analytics.dto.*;
import com.dimm.wbmanager.item.Item;

import java.util.List;

public interface AnalyticsService {

    List<AmountByMonthDto> getAmountByMonthForTable();

    List<List<Object>> getAmountByMonthForChart();

    List<OrdersSalesReturnsForPayDto> getOrdersAndSalesAndReturnsByDates();

    List<OrdersSalesReturnsForPayDto> getOrdersSalesReturnsForPayByMonth(String month);

    OrdersSalesReturnsForPayDtoSummary getSummaryMonth(List<OrdersSalesReturnsForPayDto> listMonth);

    List<OrdersAndSalesByDateDto> getOrdersAndSalesByDate(String date);

    List<MonthDetailedReportDto> getMonthDetailedReport(String month);

    List<List<Object>> getDetailedReportByMonthInObjects();

    List<StatByMonthsInfoDto> getOverallStat();

    List<StatByMonthsInfoDto> getItemStat(String item);

    List<Item> getAllItems();

    List<String> getUnindItemsBarcodes();

    List<List<Object>> getBrandsDistr();

    List<List<Object>> getTopItems();
}

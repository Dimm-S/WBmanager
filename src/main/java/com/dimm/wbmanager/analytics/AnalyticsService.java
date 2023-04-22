package com.dimm.wbmanager.analytics;

import com.dimm.wbmanager.analytics.dto.AmountByMonthDto;
import com.dimm.wbmanager.analytics.dto.DetailedReportByMonthDto;
import com.dimm.wbmanager.analytics.dto.OrdersAndSalesByDateDto;
import com.dimm.wbmanager.analytics.dto.OrdersSalesReturnsForPayDto;

import java.util.List;

public interface AnalyticsService {

    List<AmountByMonthDto> getAmountByMonthForTable();

    List<List<Object>> getAmountByMonthForChart();

    List<OrdersSalesReturnsForPayDto> getOrdersAndSalesAndReturnsByDates();

    List<OrdersSalesReturnsForPayDto> getOrdersSalesReturnsForPayByMonth(String month);

    List<OrdersAndSalesByDateDto> getOrdersAndSalesByDate(String date);

    List<DetailedReportByMonthDto> getDetailedReportByMonth(String month);

    List<List<Object>> getDetailedReportByMonthInObjects();
}

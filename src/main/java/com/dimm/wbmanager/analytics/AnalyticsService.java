package com.dimm.wbmanager.analytics;

import com.dimm.wbmanager.analytics.dto.AmountByMonthDto;
import com.dimm.wbmanager.analytics.dto.OrdersAndSalesByDateDto;
import com.dimm.wbmanager.analytics.dto.OrdersAndSalesForDashbordDto;

import java.time.LocalDate;
import java.util.List;

public interface AnalyticsService {

    List<AmountByMonthDto> getAmountByMonthForTable();

    List<List<Object>> getAmountByMonthForChart();

    List<OrdersAndSalesForDashbordDto> getOrdersAndSalesAndReturnsByDates();

    List<OrdersAndSalesByDateDto> getOrdersAndSalesByDate(String date);
}

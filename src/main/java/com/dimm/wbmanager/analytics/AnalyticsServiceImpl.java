package com.dimm.wbmanager.analytics;

import com.dimm.wbmanager.analytics.dto.AmountByMonthDto;
import com.dimm.wbmanager.analytics.dto.OrdersAndSalesByDateDto;
import com.dimm.wbmanager.analytics.dto.OrdersAndSalesForDashbordDto;
import com.dimm.wbmanager.order.OrderService;
import com.dimm.wbmanager.reportdetailbyperiod.ReportDetailByPeriodService;
import com.dimm.wbmanager.sale.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService{
    private final ReportDetailByPeriodService reportDetailByPeriodService;
    private final OrderService orderService;
    private final SaleService saleService;
    private final AnalyticsMapper analyticsMapper;

    @Override
    public List<AmountByMonthDto> getAmountByMonthForTable() {
        return reportDetailByPeriodService.getAmountByMonthForTable();
    }

    @Override
    public List<List<Object>> getAmountByMonthForChart() {
        return reportDetailByPeriodService.getAmountByMonthForChart();
    }

    @Override
    public List<OrdersAndSalesForDashbordDto> getOrdersAndSalesAndReturnsByDates() {
        List<LocalDate> datesList = getDatesList();
        List<List<Object[]>> orders = orderService.getOrdersAndSum();
        List<List<Object[]>> sales = saleService.getSalesAndSum();
        List<List<Object[]>> returns = saleService.getReturnsAndSum();
        List<List<Object[]>> forPay = saleService.getForPay();

        return analyticsMapper.mapForDayliTable(
                datesList, orders, sales, returns, forPay);
    }

    @Override
    public List<OrdersAndSalesByDateDto> getOrdersAndSalesByDate(String date) {
        List<List<Object[]>> orders = orderService.getOrdersAndSumByDate(date);
        List<List<Object[]>> salesAndReturnsAndForPay = saleService.getSalesAndSumAndReturnsAndForPayByDate(date);
        return analyticsMapper.mapToDetails(orders, salesAndReturnsAndForPay);
    }

    private static List<LocalDate> getDatesList() { //todo Заменить на -7 дней от текущей даты
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(LocalDate.of(2023, 1, 16));
        dateList.add(LocalDate.of(2023, 1, 17));
        dateList.add(LocalDate.of(2023, 1, 18));
        dateList.add(LocalDate.of(2023, 1, 19));
        dateList.add(LocalDate.of(2023, 1, 20));
        dateList.add(LocalDate.of(2023, 1, 21));
        dateList.add(LocalDate.of(2023, 1, 22));

        return dateList;
    }
}

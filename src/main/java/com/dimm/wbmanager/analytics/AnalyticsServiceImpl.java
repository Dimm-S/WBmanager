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

    /**
     * Заказы и продажи на диапазон дат (последние 7 дней)
     * @return
     */
    @Override
    public List<OrdersAndSalesForDashbordDto> getOrdersAndSalesAndReturnsByDates() {
        List<LocalDate> datesList = getDatesList();
        List<List<Object[]>> orders = orderService.getOrdersAndSum(datesList.get(0), datesList.get(6));
        List<List<Object[]>> sales = saleService.getSalesAndSum(datesList.get(0), datesList.get(6));
        List<List<Object[]>> returns = saleService.getReturnsAndSum(datesList.get(0), datesList.get(6));
        List<List<Object[]>> forPay = saleService.getForPay(datesList.get(0), datesList.get(6));

        return analyticsMapper.mapForDayliTable(
                datesList, orders, sales, returns, forPay);
    }

    /**
     * Заказы и продажи на указанную дату
     * @param date
     * @return
     */
    @Override
    public List<OrdersAndSalesByDateDto> getOrdersAndSalesByDate(String date) {
        List<List<Object[]>> orders = orderService.getOrdersAndSumByDate(date);
        List<List<Object[]>> salesAndReturnsAndForPay = saleService.getSalesAndSumAndReturnsAndForPayByDate(date);
        return analyticsMapper.mapToDetails(orders, salesAndReturnsAndForPay);
    }

    private static List<LocalDate> getDatesList() {
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(LocalDate.now().minusDays(6));
        dateList.add(LocalDate.now().minusDays(5));
        dateList.add(LocalDate.now().minusDays(4));
        dateList.add(LocalDate.now().minusDays(3));
        dateList.add(LocalDate.now().minusDays(2));
        dateList.add(LocalDate.now().minusDays(1));
        dateList.add(LocalDate.now());

        return dateList;
    }
}

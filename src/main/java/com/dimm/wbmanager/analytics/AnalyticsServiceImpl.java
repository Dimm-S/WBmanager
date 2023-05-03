package com.dimm.wbmanager.analytics;

import com.dimm.wbmanager.Month;
import com.dimm.wbmanager.analytics.dto.AmountByMonthDto;
import com.dimm.wbmanager.analytics.dto.DetailedReportByMonthDto;
import com.dimm.wbmanager.analytics.dto.OrdersAndSalesByDateDto;
import com.dimm.wbmanager.analytics.dto.OrdersSalesReturnsForPayDto;
import com.dimm.wbmanager.item.Item;
import com.dimm.wbmanager.item.ItemService;
import com.dimm.wbmanager.order.OrderService;
import com.dimm.wbmanager.reportdetailbyperiod.ReportDetailByPeriodService;
import com.dimm.wbmanager.sale.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService{
    private final ReportDetailByPeriodService reportDetailByPeriodService;
    private final OrderService orderService;
    private final SaleService saleService;
    private final AnalyticsMapper analyticsMapper;
    private final ItemService itemService;

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
    public List<OrdersSalesReturnsForPayDto> getOrdersAndSalesAndReturnsByDates() {
        List<LocalDate> datesList = getDatesList(LocalDate.now().minusDays(6), LocalDate.now());
        List<List<Object[]>> orders = orderService.getOrdersAndSum(datesList.get(0), datesList.get(6));
        List<List<Object[]>> sales = saleService.getSalesAndSum(datesList.get(0), datesList.get(6));
        List<List<Object[]>> returns = saleService.getReturnsAndSum(datesList.get(0), datesList.get(6));
        List<List<Object[]>> forPay = saleService.getForPay(datesList.get(0), datesList.get(6));

        return analyticsMapper.mapForDayliTable(
                datesList, orders, sales, returns, forPay);
    }

    /**
     * Заказы и продажи за месяц
     * @param month
     * @return
     */
    @Override
    public List<OrdersSalesReturnsForPayDto> getOrdersSalesReturnsForPayByMonth(String month) {
        LocalDate dateFrom = LocalDate.parse("2023-0" + (Month.valueOf(month).ordinal() + 1) + "-01"); //TODO исправить месяц
        LocalDate dateTo = (LocalDate) TemporalAdjusters.lastDayOfMonth().adjustInto(dateFrom);
        List<LocalDate> datesList = getDatesList(dateFrom, dateTo);

        List<List<Object[]>> orders = orderService.getOrdersAndSum(dateFrom, dateTo);
        List<List<Object[]>> sales = saleService.getSalesAndSum(dateFrom, dateTo);
        List<List<Object[]>> returns = saleService.getReturnsAndSum(dateFrom, dateTo);
        List<List<Object[]>> forPay = saleService.getForPay(dateFrom, dateTo);

        return analyticsMapper.mapForDayliTable(
                datesList, orders, sales, returns, forPay);
    }

    /**
     * Заказы и продажи на указанную дату
     * @param date дата отчёта
     * @return
     */
    @Override
    public List<OrdersAndSalesByDateDto> getOrdersAndSalesByDate(String date) {
        List<List<Object[]>> orders = orderService.getOrdersAndSumByDate(date);
        List<List<Object[]>> salesAndReturnsAndForPay = saleService.getSalesAndSumAndReturnsAndForPayByDate(date);
        List<OrdersAndSalesByDateDto> list = analyticsMapper.mapToDetails(orders, salesAndReturnsAndForPay);

        OrdersAndSalesByDateDto total = new OrdersAndSalesByDateDto("ИТОГО",
                0, 0D, 0, 0F, 0, 0F, 0F);
        for (OrdersAndSalesByDateDto r : list) {
            total.setOrdersQuantity(total.getOrdersQuantity() + r.getOrdersQuantity());
            total.setOrdersSum(total.getOrdersSum() + r.getOrdersSum());
            total.setSalesQuantity(total.getSalesQuantity() + r.getSalesQuantity());
            total.setSalesSum(total.getSalesSum() + r.getSalesSum());
            total.setReturnsQuantity(total.getReturnsQuantity() + r.getReturnsQuantity());
            total.setReturnSum(total.getReturnSum() + r.getReturnSum());
            total.setForPay(total.getForPay() + r.getForPay());
        }

        list.add(total);
        return list;
    }

    /**
     * Детальный отчет за месяц: заказы, продажи, возвраты (для обычной таблицы)
     * @param month наименование месяца
     * @return
     */
    @Override
    public List<DetailedReportByMonthDto> getDetailedReportByMonth(String month) {
        List<List<Object[]>> orders = orderService.getMonthOrdersAndSumByItems(month);
        List<List<Object[]>> sales = reportDetailByPeriodService.getMonthSalesAndBuybacksByItems(month);
        List<DetailedReportByMonthDto> list = analyticsMapper.mapToDetailedMonthDto(orders, sales);

        DetailedReportByMonthDto total = new DetailedReportByMonthDto("ИТОГО",
                0, 0D, 0, 0F, 0, 0F,
                0D, 0D, 0D, 0D);
        for (DetailedReportByMonthDto r : list) {
            total.setOrdersQuantity(total.getOrdersQuantity() + r.getOrdersQuantity());
            total.setOrdersSum(total.getOrdersSum() + r.getOrdersSum());
            total.setSalesQuantity(total.getSalesQuantity() + r.getSalesQuantity());
            total.setSalesSum(total.getSalesSum() + r.getSalesSum());
            total.setReturnsQuantity(total.getReturnsQuantity() + r.getReturnsQuantity());
            total.setReturnSum(total.getReturnSum() + r.getReturnSum());
            total.setReturnsPctQnt(total.getReturnsPctQnt() + r.getReturnsPctQnt());
            total.setReturnsPctSum(total.getReturnsPctSum() + r.getReturnSum());
            total.setBuyoutPctInQnt(total.getBuyoutPctInQnt() + r.getBuyoutPctInQnt());
            total.setBuyoutPctInSum(total.getBuyoutPctInSum() + r.getBuyoutPctInSum());
        }
        total.setReturnsPctQnt((double)total.getReturnsQuantity() / (double)total.getSalesQuantity() * 100);
        total.setReturnsPctSum((double) (total.getReturnSum() / total.getSalesSum() * 100));
        total.setBuyoutPctInQnt((double) total.getSalesQuantity() / (double) total.getOrdersQuantity() * 100);
        total.setBuyoutPctInSum((double) total.getSalesSum() / total.getOrdersSum() * 100);

        list.add(total);
        return list;
    }

    /**
     * Детальный отчет за месяц: заказы, продажи, возвраты (для Google Charts)
     * @return
     */
    @Override
    public List<List<Object>> getDetailedReportByMonthInObjects() {
        List<List<Object>> lists = new ArrayList<>();
        List<DetailedReportByMonthDto> report = getDetailedReportByMonth("МАРТ");  // TODO временно
        for (DetailedReportByMonthDto r : report) {
            lists.add(new ArrayList<>(Arrays.asList(r.getName(),
                    r.getOrdersQuantity(),
                    r.getOrdersSum(),
                    r.getSalesQuantity(),
                    r.getSalesSum(),
                    r.getReturnsQuantity(),
                    r.getReturnSum(),
                    r.getReturnsPctQnt(),
                    r.getReturnsPctSum(),
                    r.getBuyoutPctInQnt(),
                    r.getBuyoutPctInSum())));
        }
        return lists;
    }

    @Override
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    private static List<LocalDate> getDatesList(LocalDate dateFrom, LocalDate dateTo) {
        return dateFrom.datesUntil(dateTo.plusDays(1))
                .collect(Collectors.toList());
    }
}

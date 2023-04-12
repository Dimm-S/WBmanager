package com.dimm.wbmanager.analytics;

import com.dimm.wbmanager.analytics.dto.AmountByMonthDto;
import com.dimm.wbmanager.analytics.dto.DetailedReportByMonthDto;
import com.dimm.wbmanager.analytics.dto.OrdersAndSalesByDateDto;
import com.dimm.wbmanager.analytics.dto.OrdersAndSalesForDashbordDto;
import com.dimm.wbmanager.sale.SaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/analytics")
public class AnalyticsController {
    private final AnalyticsService analyticsService;
    private final SaleService saleService;

    @GetMapping("/amount")
    public String getAmountByMonth(Model model) {
        log.info("Запрошен энтпойнт GET:/analytics/amount");
        List<AmountByMonthDto> amountList = analyticsService.getAmountByMonthForTable();
        model.addAttribute("amount", amountList);
        return "amount";
    }

    @GetMapping("/amountchart")
    public String getAmountChartByMonth(Model model) {
        log.info("Запрошен энтпойнт GET:/analytics/amountchart");
        List<List<Object>> amountList = analyticsService.getAmountByMonthForChart();
        List<List<Object>> saleList = saleService.getSalesByDays();
        List<OrdersAndSalesForDashbordDto> ordersAndSales = analyticsService.getOrdersAndSalesAndReturnsByDates();

        model.addAttribute("amountchart", amountList);
        model.addAttribute("saleschart", saleList);
        model.addAttribute("orsales", ordersAndSales);

        return "amountchart";
    }

    @GetMapping("/daily")
    public String getOrdersAndSalesAndReturnsByDates(Model model) {
        log.info("Запрошен энтпойнт GET:/analytics/daily");
        List<OrdersAndSalesForDashbordDto> ordersAndSales = analyticsService.getOrdersAndSalesAndReturnsByDates();
        model.addAttribute("orsales", ordersAndSales);
        return "daily";
    }

    @GetMapping("/daily/details")
    public String getOrdersAndSalesAndReturnsByName(@RequestParam String date, Model model) {
        log.info("Запрошен энтпойнт GET:/analytics/daily/details на дату {}", date);
        List<OrdersAndSalesByDateDto> ordersAndSalesByDate = analyticsService.getOrdersAndSalesByDate(date);
        model.addAttribute("details", ordersAndSalesByDate);
        return "details";
    }

    /**
     * Таблица за месяц с заказами, продажами, возвратами... по наименованиям товаров
     * @return
     */
    @GetMapping("/month")
    public String getMonthByItems(Model model) {
        log.info("Запрошен энтпойнт GET:/analytics/month");
        List<DetailedReportByMonthDto> detailedReportByMonthDtoList = analyticsService.getDetailedReportByMonth();
        model.addAttribute("detailedmonth", detailedReportByMonthDtoList);
        return "month";
    }

    @GetMapping("/monthgoogletable")
    public String getMonthByItems2(Model model) {
        log.info("Запрошен энтпойнт GET:/analytics/monthGoo");
        List<List<Object>> detailedReportByMonthDtoList = analyticsService.getDetailedReportByMonthInObjects();
        model.addAttribute("detailedmonth", detailedReportByMonthDtoList);
        return "monthgoogletable";
    }
}

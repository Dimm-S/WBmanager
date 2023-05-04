package com.dimm.wbmanager.analytics;

import com.dimm.wbmanager.analytics.dto.AmountByMonthDto;
import com.dimm.wbmanager.analytics.dto.DetailedReportByMonthDto;
import com.dimm.wbmanager.analytics.dto.OrdersAndSalesByDateDto;
import com.dimm.wbmanager.analytics.dto.OrdersSalesReturnsForPayDto;
import com.dimm.wbmanager.item.Item;
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

    /**
     * Дашборд
     *
     * @param model
     * @return
     */
    @GetMapping("/amountchart")
    public String getAmountChartByMonth(Model model) {
        log.info("Запрошен энтпойнт GET:/analytics/amountchart");
        List<List<Object>> amountList = analyticsService.getAmountByMonthForChart();
        List<List<Object>> saleList = saleService.getSalesByDays();
        List<OrdersSalesReturnsForPayDto> ordersAndSales = analyticsService.getOrdersAndSalesAndReturnsByDates();
        List<String> unidBarcodes = analyticsService.getUnindItemsBarcodes();

        model.addAttribute("amountchart", amountList);
        model.addAttribute("saleschart", saleList); //TODO отключено
        model.addAttribute("orsales", ordersAndSales);
        model.addAttribute("barcodes", unidBarcodes);

        return "amountchart";
    }

    /**
     * Таблица с заказами, продажами, возвратами за последние n дней (группировка по датам)
     *
     * @param model
     * @return
     */
    @GetMapping("/daily")
    public String getOrdersAndSalesAndReturnsByDates(Model model) {
        log.info("Запрошен энтпойнт GET:/analytics/daily");
        List<OrdersSalesReturnsForPayDto> ordersAndSales = analyticsService.getOrdersAndSalesAndReturnsByDates();
        model.addAttribute("orsales", ordersAndSales);
        return "daily";
    }

    /**
     * Таблица с заказами, продажами, возвратами за указанный месяц (группировка по датам)
     *
     * @param month
     * @param model
     * @return
     */
    @GetMapping("/dailymonth")
    public String getOrdersAndSalesAndReturnsForMonth(@RequestParam String month, Model model) {
        log.info("Запрошен энтпойнт GET:/analytics/daily");
        List<OrdersSalesReturnsForPayDto> ordersAndSales = analyticsService.getOrdersSalesReturnsForPayByMonth(month);
        model.addAttribute("orsales", ordersAndSales);
        return "dailymonth";
    }

    /**
     * Таблица с заказами, продажами, возвратами за указанную дату (группировка по наименованиям товаров)
     *
     * @param date
     * @param model
     * @return
     */
    @GetMapping("/daily/details")
    public String getOrdersAndSalesAndReturnsByName(@RequestParam String date, Model model) {
        log.info("Запрошен энтпойнт GET:/analytics/daily/details на дату {}", date);
        List<OrdersAndSalesByDateDto> ordersAndSalesByDate = analyticsService.getOrdersAndSalesByDate(date);
        model.addAttribute("details", ordersAndSalesByDate);
        return "details";
    }

    /**
     * Таблица за месяц с заказами, продажами, возвратами... по наименованиям товаров
     *
     * @param month название месяца
     * @param model
     * @return
     */
    @GetMapping("/month")
    public String getMonthByItems(@RequestParam(name = "month", required = true) String month, Model model) {
        log.info("Запрошен энтпойнт GET:/analytics/month");
        List<DetailedReportByMonthDto> detailedReportByMonthDtoList = analyticsService.getDetailedReportByMonth(month);
        model.addAttribute("detailedmonth", detailedReportByMonthDtoList);
        model.addAttribute("monthname", month);
        return "month";
    }

    /** //TODO отключено!
     * Таблица за месяц с заказами, продажами, возвратами... по наименованиям товаров (таблица GoogleCharts)
     *
     * @param model
     * @return
     */
    @GetMapping("/monthgoogletable")
    public String getMonthByItems2(Model model) {
        log.info("Запрошен энтпойнт GET:/analytics/monthGoo");
        List<List<Object>> detailedReportByMonthDtoList = analyticsService.getDetailedReportByMonthInObjects();
        model.addAttribute("detailedmonth", detailedReportByMonthDtoList);
        return "monthgoogletable";
    }

    @GetMapping("/items")
    public String getAllItems(Model model) {
        log.info("Запрошен энтпойнт GET:/analytics/items");
        List<Item> items = analyticsService.getAllItems();
        model.addAttribute("items", items);
        return "items";
    }

    @GetMapping("/brands")
    public String getAllBrands(Model model) {
        log.info("Запрошен энтпойнт GET:/analytics/brands");
        model.addAttribute("underconstruction");
        return "underconstruction";
    }

}

package com.dimm.wbmanager.reportdetailbyperiod;

import com.dimm.wbmanager.analytics.dto.AmountByMonthDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportDetailByPeriodRepository extends JpaRepository <ReportDetailByPeriod, Long> {
    /**
     * Выручка помесячно
     * @return
     */
    @Query(nativeQuery = true, value =
            "select EXTRACT(MONTH FROM sale_dt) as month, " +
                    "extract(year from sale_dt) as year, " +
                    "sum(retail_amount) as amount, " +
                    "sum(ppvz_vw) as commission " +
            "from reportdetailbyperiod " +
            "where quantity = 1 AND supplier_oper_name = 'Продажа' " +
            "group by quantity, month, year " +
            "order by year asc, month asc")
    List<Object[]> getAmountByMonth();

    /**
     * Возвраты помесячно
     * @return
     */
    @Query(nativeQuery = true, value =
            "select EXTRACT(MONTH FROM sale_dt) as month, " +
            "extract(year from sale_dt) as year, " +
            "sum(retail_amount) as amount, " +
            "sum(ppvz_vw) as commission " +
            "from reportdetailbyperiod " +
            "where quantity = 1 AND supplier_oper_name = 'Возврат' " +
            "group by quantity, month, year " +
            "order by year asc, month asc")
    List<Object[]> getReturns();

    /**
     * Продажи и возвраты за месяц с группировкой по наименованиям товаров
     * @return
     */
    @Query(nativeQuery = true, value =
            "select name, " +
            "sum(case when supplier_oper_name = 'Продажа' then 1 else 0 end) as sales_count," +
            "sum(case when supplier_oper_name = 'Продажа' then retail_amount else 0 end) as sales_sum, " +
            "sum(case when supplier_oper_name = 'Возврат' then 1 else 0 end) as returns_count, " +
            "sum(case when supplier_oper_name = 'Возврат' then retail_amount else 0 end) as returns " +
            "from reportdetailbyperiod as r " +
            "join items as i on r.barcode = i.barcode " +
            "where EXTRACT(MONTH FROM sale_dt) = 3 and quantity = 1 " +
            "group by name")
    List<List<Object[]>> getMonthSalesAndBuybacksByItems();
}

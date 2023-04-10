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

//    @Query(value = "SELECT new com.dimm.wbmanager.analytics.dto.AmountByMonthDto(rep.saleDt, sum(rep.retailAmount), sum(rep.ppvzVw)) " +
//            "FROM ReportDetailByPeriod as rep " +
//            "WHERE rep.quantity = 1 " +
//            "GROUP BY rep.quantity, EXTRACT(MONTH from rep.saleDt) " +
//            "ORDER BY EXTRACT(MONTH from rep.saleDt) asc")
//    List<AmountByMonthDto> getAmountByMonth();
}

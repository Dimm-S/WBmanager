package com.dimm.wbmanager.sale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(nativeQuery = true, value =
            "SELECT EXTRACT(DAY  FROM order_date) as day, " +
            "sum(price_with_disc) " +
            "FROM sales " +
                    "WHERE EXTRACT(MONTH  FROM order_date) = 1.0 " +
                    "GROUP BY day " +
                    "ORDER BY day")
    List<Object[]> getSalesByDays();

    @Query(nativeQuery = true, value =
        "select cast(order_date as date) as date, count(price_with_disc), sum(price_with_disc)\n" +
                "from sales\n" +
                "where sale_id like 'S%' and cast(order_date as date) between '2023-01-16' and '2023-01-22'\n" +
                "group by cast(order_date as date)\n" +
                "order by date")
    List<List<Object[]>> getSalesAndSum();

    @Query(nativeQuery = true, value =
            "select cast(order_date as date) as date, count(price_with_disc), sum(price_with_disc)\n" +
                    "from sales\n" +
                    "where sale_id like 'R%' and cast(order_date as date) between '2023-01-16' and '2023-01-22'\n" +
                    "group by cast(order_date as date)\n" +
                    "order by date")
    List<List<Object[]>> getReturnsAndSum();

    @Query(nativeQuery = true, value =
            "select cast(order_date as date) as date, sum(for_pay)\n" +
                    "from sales\n" +
                    "where cast(order_date as date) between '2023-01-16' and '2023-01-22'\n" +
                    "group by cast(order_date as date)\n" +
                    "order by date")
    List<List<Object[]>> getForPay();

    @Query(nativeQuery = true, value =
            "select name, " +
            "sum(case when sale_id like 'S%' then 1 else 0 end) as sales_count, " +
            "sum(case when sale_id like 'S%' then price_with_disc else 0 end) as sales_sum, " +
            "sum(case when sale_id like 'R%' then 1 else 0 end) as returns_count, " +
            "sum(case when sale_id like 'R%' then price_with_disc else 0 end) as returns_sum, " +
            "sum(for_pay) as for_pay " +
            "from sales as s " +
            "join items as i on s.barcode = i.barcode " +
                    "where cast(order_date as date) = ?1 " +
                    "group by name " +
                    "order by sales_count desc")
    List<List<Object[]>> getSalesAndSumAndReturnsAndForPayByDate(LocalDate date);
}
package com.dimm.wbmanager.sale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    //TODO переделять с явного указания месяца на последние n дней
    @Query(nativeQuery = true, value =
            "SELECT EXTRACT(DAY  FROM order_date) AS day, " +
                    "SUM(price_with_disc) " +
                    "FROM sales " +
                    "WHERE EXTRACT(MONTH  FROM order_date) = 3.0 " +
                    "GROUP BY day " +
                    "ORDER BY day")
    List<Object[]> getSalesByDays();

    @Query(nativeQuery = true, value =
            "SELECT CAST(order_date AS date) AS date, COUNT(price_with_disc), SUM(price_with_disc)\n" +
                    "FROM sales\n" +
                    "WHERE sale_id LIKE 'S%' AND CAST(order_date AS date) BETWEEN ?1 AND ?2\n" +
                    "GROUP BY CAST(order_date AS date)\n" +
                    "ORDER BY date")
    List<List<Object[]>> getSalesAndSum(LocalDate from, LocalDate to);

    @Query(nativeQuery = true, value =
            "SELECT CAST(order_date AS date) AS date, COUNT(price_with_disc), SUM(price_with_disc)\n" +
                    "FROM sales\n" +
                    "WHERE sale_id LIKE 'R%' AND CAST(order_date AS date) BETWEEN ?1 AND ?2\n" +
                    "GROUP BY CAST(order_date AS date)\n" +
                    "ORDER BY date")
    List<List<Object[]>> getReturnsAndSum(LocalDate from, LocalDate to);

    @Query(nativeQuery = true, value =
            "SELECT CAST(order_date AS date) AS date, SUM(for_pay)\n" +
                    "FROM sales\n" +
                    "WHERE CAST(order_date AS date) BETWEEN ?1 AND ?2\n" +
                    "GROUP BY CAST(order_date AS date)\n" +
                    "ORDER BY date")
    List<List<Object[]>> getForPay(LocalDate from, LocalDate to);

    @Query(nativeQuery = true, value =
            "SELECT name, " +
                    "SUM(CASE WHEN sale_id LIKE 'S%' THEN 1 ELSE 0 END) AS sales_count, " +
                    "SUM(CASE WHEN sale_id LIKE 'S%' THEN price_with_disc ELSE 0 END) AS sales_sum, " +
                    "SUM(CASE WHEN sale_id LIKE 'R%' THEN 1 ELSE 0 END) AS returns_count, " +
                    "SUM(CASE WHEN sale_id LIKE 'R%' THEN price_with_disc ELSE 0 END) AS returns_sum, " +
                    "SUM(for_pay) AS for_pay " +
                    "FROM sales AS s " +
                    "JOIN items AS i ON s.barcode = i.barcode " +
                    "WHERE CAST(order_date AS date) = ?1 " +
                    "GROUP BY name " +
                    "ORDER BY sales_count DESC")
    List<List<Object[]>> getSalesAndSumAndReturnsAndForPayByDate(LocalDate date);

    @Query(nativeQuery = true, value = "SELECT * " +
            "FROM sales " +
            "ORDER BY id DESC LIMIT 1")
    Sale getLast();

    @Query(nativeQuery = true, value = "SELECT * " +
            "FROM sales " +
            "WHERE odid = ?1 AND sale_id = ?2")
    Sale findByOdidAndSaleId(Long odid, String sale_id);
}

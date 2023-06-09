package com.dimm.wbmanager.reportdetailbyperiod;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportDetailByPeriodRepository extends JpaRepository<ReportDetailByPeriod, Long> {
    /**
     * Выручка помесячно
     *
     * @return
     */
    @Query(nativeQuery = true, value =
            "SELECT EXTRACT(MONTH FROM sale_dt) AS MONTH, " +
                    "EXTRACT(YEAR FROM sale_dt) AS YEAR, " +
                    "SUM(retail_amount) AS amount, " +
                    "SUM(ppvz_vw) AS commission " +
                    "FROM reportdetailbyperiod " +
                    "WHERE quantity = 1 AND supplier_oper_name = 'Продажа' " +
                    "GROUP BY quantity, MONTH, YEAR " +
                    "ORDER BY YEAR ASC, MONTH ASC")
    List<Object[]> getAmountByMonth();

    /**
     * Возвраты помесячно
     *
     * @return
     */
    @Query(nativeQuery = true, value =
            "SELECT EXTRACT(MONTH FROM sale_dt) AS MONTH, " +
                    "EXTRACT(YEAR FROM sale_dt) AS YEAR, " +
                    "SUM(retail_amount) AS amount, " +
                    "SUM(ppvz_vw) AS commission " +
                    "FROM reportdetailbyperiod " +
                    "WHERE quantity = 1 AND supplier_oper_name = 'Возврат' " +
                    "GROUP BY quantity, MONTH, YEAR " +
                    "ORDER BY YEAR ASC, MONTH ASC")
    List<Object[]> getReturns();

    /**
     * Продажи и возвраты за месяц с группировкой по наименованиям товаров
     *
     * @return
     */
    @Query(nativeQuery = true, value =
            "SELECT name, " +
                    "SUM(CASE WHEN supplier_oper_name = 'Продажа' THEN 1 ELSE 0 END) AS sales_count," +
                    "SUM(CASE WHEN supplier_oper_name = 'Продажа' THEN retail_amount ELSE 0 END) AS sales_sum, " +
                    "SUM(CASE WHEN supplier_oper_name = 'Возврат' THEN 1 ELSE 0 END) AS returns_count, " +
                    "SUM(CASE WHEN supplier_oper_name = 'Возврат' THEN retail_amount ELSE 0 END) AS returns " +
                    "FROM reportdetailbyperiod AS r " +
                    "JOIN items AS i ON r.barcode = i.barcode " +
                    "WHERE EXTRACT(MONTH FROM sale_dt) = ?1 AND quantity = 1 " +
                    "AND srid NOT IN (SELECT * FROM selfbuyouts) " +
                    "GROUP BY name")
    List<List<Object[]>> getMonthSalesAndBuybacksByItems(Integer month);

    /**
     * Продажи и возвараты общие помесячно
     */
    @Query(nativeQuery = true, value =
            "SELECT EXTRACT(MONTH FROM sale_dt) AS MONTH, " +
                    "EXTRACT(YEAR FROM sale_dt) AS YEAR, " +
                    "SUM(CASE WHEN supplier_oper_name = 'Продажа' THEN 1 ELSE 0 END) AS sales_count, " +
                    "SUM(CASE WHEN supplier_oper_name = 'Продажа' THEN retail_amount ELSE 0 END) AS sales_sum, " +
                    "SUM(CASE WHEN supplier_oper_name = 'Возврат' THEN 1 ELSE 0 END) AS returns_count, " +
                    "SUM(CASE WHEN supplier_oper_name = 'Возврат' THEN retail_amount ELSE 0 END) AS returns_sum " +
                    "FROM reportdetailbyperiod as r " +
                    "JOIN items AS i ON r.barcode = i.barcode " +
                    "WHERE quantity = 1 AND srid NOT IN (SELECT * FROM selfbuyouts) " +
                    "AND sale_dt >= '2022-10-01 00:00:00.000000' " + //TODO несоответствие периодов в базах ordrs и rdbp
                    "GROUP BY MONTH, YEAR " +
                    "ORDER BY YEAR ASC, MONTH ASC")
    List<Object[]> getSalesAndReturnsByMonths();

    /**
     * Продажи и возвараты товара помесячно
     */
    @Query(nativeQuery = true, value =
            "SELECT EXTRACT(MONTH FROM sale_dt) AS MONTH, " +
                    "EXTRACT(YEAR FROM sale_dt) AS YEAR, " +
                    "SUM(CASE WHEN supplier_oper_name = 'Продажа' THEN 1 ELSE 0 END) AS sales_count, " +
                    "SUM(CASE WHEN supplier_oper_name = 'Продажа' THEN retail_amount ELSE 0 END) AS sales_sum, " +
                    "SUM(CASE WHEN supplier_oper_name = 'Возврат' THEN 1 ELSE 0 END) AS returns_count, " +
                    "SUM(CASE WHEN supplier_oper_name = 'Возврат' THEN retail_amount ELSE 0 END) AS returns_sum " +
                    "FROM reportdetailbyperiod as r " +
                    "JOIN items AS i ON r.barcode = i.barcode " +
                    "WHERE name LIKE ?1 AND quantity = 1 AND srid NOT IN (SELECT * FROM selfbuyouts) " +
                    "GROUP BY MONTH, YEAR " +
                    "ORDER BY YEAR ASC, MONTH ASC")
    List<Object[]> getItemSalesAndReturnsByMonths(String item);

    /**
     * Продажи и возвараты по бренду помесячно
     */
    @Query(nativeQuery = true, value =
            "SELECT EXTRACT(MONTH FROM sale_dt) AS MONTH, " +
                    "EXTRACT(YEAR FROM sale_dt) AS YEAR, " +
                    "SUM(CASE WHEN supplier_oper_name = 'Продажа' THEN 1 ELSE 0 END) AS sales_count, " +
                    "SUM(CASE WHEN supplier_oper_name = 'Продажа' THEN retail_amount ELSE 0 END) AS sales_sum, " +
                    "SUM(CASE WHEN supplier_oper_name = 'Возврат' THEN 1 ELSE 0 END) AS returns_count, " +
                    "SUM(CASE WHEN supplier_oper_name = 'Возврат' THEN retail_amount ELSE 0 END) AS returns_sum " +
                    "FROM reportdetailbyperiod as r " +
                    "WHERE brand_name LIKE ?1 AND quantity = 1 AND srid NOT IN (SELECT * FROM selfbuyouts) " +
                    "GROUP BY MONTH, YEAR " +
                    "ORDER BY YEAR ASC, MONTH ASC")
    List<Object[]> getBrandSalesAndReturnsByMonths(String brand);


    /**
     * Получение последней записи в таблице
     *
     * @return
     */
    @Query(nativeQuery = true, value = "SELECT * " +
            "FROM reportdetailbyperiod " +
            "ORDER BY id DESC LIMIT 1")
    ReportDetailByPeriod getLast();

    /**
     * Продажи по брендам
     * @return
     */
    @Query(nativeQuery = true, value = "SELECT " +
            "brand_name, SUM(retail_amount) AS amount " +
            "FROM public.reportdetailbyperiod " +
            "WHERE quantity = 1 AND supplier_oper_name = 'Продажа' " +
            "GROUP BY brand_name")
    List<Object[]> getBrandsDistr();

    /**
     * Продажи по товарам
     * @return
     */
    @Query(nativeQuery = true, value = "SELECT " +
            "name, SUM(retail_amount) AS amount " +
            "FROM public.reportdetailbyperiod as r " +
            "JOIN items AS i ON r.barcode = i.barcode " +
            "WHERE quantity = 1 AND supplier_oper_name = 'Продажа' " +
            "GROUP BY name " +
            "ORDER BY amount DESC")
    List<Object[]> getTopItems();
}

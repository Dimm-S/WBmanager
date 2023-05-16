package com.dimm.wbmanager.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    //todo Несоответствие типов нужному ДТО
//    @Query(value = "SELECT new com.dimm.wbmanager.order.dto.OrdersQuantityAndSumByDateDto(" +
//            "cast(ord.date as date), " +
//            "count(ord.totalPrice), " +
//            "sum(ord.totalPrice * ord.discountPercent / 100)) " +
//            "from Order as ord " +
//            "where cast(ord.date as date) between cast('2023-01-16' as date) and cast('2023-01-22' as date) " +
//            "group by cast(ord.date as date) " +
//            "order by cast(ord.date as date)")
//    List<OrdersQuantityAndSumByDateDto> getOrdersAndSum();

    /**
     * Количество заказов полное (без учёта отмен) и общая стоимость этих заказов
     * (цена за минусом скидки),
     * за указанные даты с группировкой по датам
     */
    @Query(nativeQuery = true, value = "SELECT " +
            "CAST(order_date AS date) AS date, " +
            "COUNT(total_price), SUM(total_price * (100 - discount_percent) / 100) " +
            "FROM orders " +
            "WHERE CAST(order_date AS date) BETWEEN ?1 AND ?2 " +
            "GROUP BY CAST(order_date AS date) " +
            "ORDER BY date")
    List<List<Object[]>> getOrdersAndSum(LocalDate from, LocalDate to);

    /**
     * Количество и сумма заказов за указанную дату с группировкой по наименованию
     *
     * @param date дата в формате YYYY-MM-DD
     * @return
     */
    @Query(nativeQuery = true, value = "SELECT " +
            "name, " +
            "COUNT(total_price), SUM(total_price * (100 - discount_percent) / 100) " +
            "FROM orders AS o " +
            "JOIN items i ON i.barcode = o.barcode " +
            "WHERE CAST(order_date AS date) = ?1 " +
            "GROUP BY name")
    List<List<Object[]>> getOrdersAndSumByDate(LocalDate date);

    /**
     * Количество и сумма заказов за указанный месяц с группировкой по наименованию товаров
     * @param month месяц
     * @return
     */
    @Query(nativeQuery = true, value =
            "SELECT name, COUNT(total_price), SUM(total_price * (100 - discount_percent) / 100) " +
                    "FROM orders AS o " +
                    "JOIN items AS i ON o.barcode = i.barcode " +
                    "WHERE EXTRACT(MONTH FROM order_date) = ?1 " +
                    "GROUP BY name")
    List<List<Object[]>> getMonthOrdersAndSumByItems(Integer month);

    /**
     * Количество и сумма заказов конкретного наименования товаров помесячно
     */
    @Query(nativeQuery = true, value =
            "SELECT name, " +
                    "EXTRACT(MONTH FROM order_date) AS MONTH,\n" +
                    "EXTRACT(YEAR FROM order_date) AS YEAR, " +
                    "COUNT(total_price), " +
                    "SUM(total_price * (100 - discount_percent) / 100) " +
                    "FROM orders AS o " +
                    "JOIN items AS i ON o.barcode = i.barcode " +
                    "WHERE name LIKE ?1 AND srid NOT IN (SELECT * FROM selfbuyouts) " +
                    "GROUP BY name, MONTH, YEAR " +
                    "ORDER BY YEAR ASC, MONTH ASC")
   List<Object[]> getItemOrdersAndSumByMonths(String item);

    /**
     * Получение последней записи в таблице
     *
     * @return
     */
    @Query(nativeQuery = true, value = "SELECT * " +
            "FROM orders " +
            "ORDER BY id DESC LIMIT 1")
    Order getLast();

    /**
     * Поиск по odid - уникальному идентификатору позиции заказа
     *
     * @param odid
     * @return
     */
    @Query(nativeQuery = true, value = "SELECT * " +
            "FROM orders " +
            "WHERE odid = ?1")
    Order findByOdid(Long odid);
}

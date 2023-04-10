package com.dimm.wbmanager.order;

import com.dimm.wbmanager.order.dto.OrdersQuantityAndSumByDateDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository <Order, Long> {

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

    /** Количество заказов полное (без учёта отмен) и общая стоимость этих заказов (цена за минусом скидки),
    за указанные даты */
    @Query(nativeQuery = true, value = "select " +
            "cast(order_date as date) as date, " +
            "count(total_price), sum(total_price * (100 - discount_percent) / 100) " +
            "from orders " +
            "where cast(order_date as date) between ?1 and ?2 " +
            "group by cast(order_date as date) " +
            "order by date")
    List<List<Object[]>> getOrdersAndSum(LocalDate from, LocalDate to);

    @Query(nativeQuery = true, value = "select " +
            "name, " +
            "count(total_price), sum(total_price * (100 - discount_percent) / 100) " +
            "from orders as o " +
            "JOIN items i ON i.barcode = o.barcode " +
            "where cast(order_date as date) = ?1 " +
            "group by name")
    List<List<Object[]>> getOrdersAndSumByDate(LocalDate date);

    @Query(nativeQuery = true, value = "SELECT * " +
            "FROM orders " +
            "ORDER BY id DESC LIMIT 1")
    Order getLast();

    @Query(nativeQuery = true, value = "SELECT * " +
            "FROM orders " +
            "WHERE odid = ?1")
    Order findByOdid(Long odid);
}

package com.dimm.wbmanager.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersQuantityAndSumByDateDto {
    private Date date;
    private Integer ordersQuantity;
    private Double ordersSum;
}

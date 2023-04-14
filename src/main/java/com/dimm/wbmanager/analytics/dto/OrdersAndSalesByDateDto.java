package com.dimm.wbmanager.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersAndSalesByDateDto {
    private String name;
    private Integer ordersQuantity;
    private Double ordersSum;
    private Integer salesQuantity;
    private Float salesSum;
    private Integer returnsQuantity;
    private Float returnSum;
    private Float forPay;
}

package com.dimm.wbmanager.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersAndSalesByDateDto {
    private String name;
    Integer ordersQuantity;
    Double ordersSum;
    Integer salesQuantity;
    Float salesSum;
    Integer returnsQuantity;
    Float returnSum;
    private Float forPay;
}

package com.dimm.wbmanager.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersAndSalesForDashbordDto {
    private LocalDate date;
    private Orders orders;
    private Sales sales;
    private Returns returns;
    private Float forPay;

    @Data
    @AllArgsConstructor
    public static class Orders {
        Integer ordersQuantity;
        Double ordersSum;
    }

    @Data
    @AllArgsConstructor
    public static class Sales {
        Integer salesQuantity;
        Float salesSum;
    }

    @Data
    @AllArgsConstructor
    public static class Returns {
        Integer returnsQuantity;
        Float returnSum;
    }
}

package com.dimm.wbmanager.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailedReportByMonthDto {
    private String name;
    Integer ordersQuantity;
    Double ordersSum;
    Integer salesQuantity;
    Float salesSum;
    Integer returnsQuantity;
    Float returnSum;
    Double returnsPctQnt;
    Double returnsPctSum;
    Double buyoutPctInQnt;
    Double buyoutPctInSum;
}

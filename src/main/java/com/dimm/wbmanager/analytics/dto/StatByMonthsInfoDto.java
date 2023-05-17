package com.dimm.wbmanager.analytics.dto;

import com.dimm.wbmanager.Month;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatByMonthsInfoDto {
    private String name;
    private Month month;
    private Integer year;
    private Integer ordersQuantity;
    private Double ordersSum;
    private Integer salesQuantity;
    private Float salesSum;
    private Integer returnsQuantity;
    private Float returnSum;
    private Double returnsPctQnt;
    private Double buyoutPctInQnt;
}

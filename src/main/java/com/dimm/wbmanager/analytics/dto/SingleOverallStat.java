package com.dimm.wbmanager.analytics.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingleOverallStat {
    private Integer salesQuantity;
    private Double salesSum;
    private Double returnsPctQnt;
    private Double buyoutPctInQnt;
}

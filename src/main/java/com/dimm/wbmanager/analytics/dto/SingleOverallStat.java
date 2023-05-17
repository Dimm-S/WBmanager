package com.dimm.wbmanager.analytics.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingleOverallStat {
    private Double salesSum;
    private Double returnsPctQnt;
    private Double buyoutPctInQnt;
}

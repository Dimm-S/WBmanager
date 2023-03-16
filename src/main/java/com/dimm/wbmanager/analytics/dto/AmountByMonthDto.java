package com.dimm.wbmanager.analytics.dto;

import com.dimm.wbmanager.Month;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmountByMonthDto {
    private Month month;
    private Float amount;
    private Float commission;
}

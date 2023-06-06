package com.dimm.wbmanager.dds.dto;

import com.dimm.wbmanager.Month;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DdsSimpleDto {
    private Month month;
    private Integer year;
    private Double operSum;
    private List<Double> operItemsSum;
    private Double investSum;
    private List<Double> investItemsSum;
    private Double finSum;
    private List<Double> finItemsSum;
    private Double sumDelta;
}



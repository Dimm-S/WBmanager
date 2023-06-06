package com.dimm.wbmanager.dds.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DdsOperationDto {
    private LocalDate date;
    private String item;
    private Double sum;
    private String account;
    private String subject;
    private String description;
}

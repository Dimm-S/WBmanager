package com.dimm.wbmanager.dds.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DdsNewOperationDto {
    @NotNull
    private String date;
    @NotNull
    private Integer itemId;
    @NotNull
    private Double sum;
    @NotNull
    private Integer accountId;
    private String subject;
    private String description;
}

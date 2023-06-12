package com.dimm.wbmanager.dds.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DdsNewItemDto {
    @NotNull
    private String item;
    @NotNull
    private String description;
    @NotNull
    private Integer itemsGroup;
    @NotNull
    private Boolean isIncoming;
    @NotNull
    private Boolean isInside;
    @NotNull
    private String type;
    private Integer type1;
    private Integer type2;
}

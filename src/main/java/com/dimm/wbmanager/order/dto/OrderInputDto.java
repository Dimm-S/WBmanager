package com.dimm.wbmanager.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInputDto {
    private String date;
    private String lastChangeDate;
    private String supplierArticle;
    private String techSize;
    private String barcode;
    private Float totalPrice;
    private Integer discountPercent;
    private String warehouseName;
    private String oblast;
    private Long incomeID;
    private Long odid;
    private Long nmId;
    private String subject;
    private String category;
    private String brand;
    private Boolean isCancel;
    private String cancel_dt;
    @JsonProperty("gNumber")
    private String gNumber;
    private String sticker;
    private String srid;
}

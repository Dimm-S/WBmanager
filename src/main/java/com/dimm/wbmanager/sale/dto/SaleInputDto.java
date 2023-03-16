package com.dimm.wbmanager.sale.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleInputDto {
    @JsonProperty("gNumber")
    private String gNumber;
    private String date;
    private String lastChangeDate;
    private String supplierArticle;
    private String techSize;
    private String barcode;
    private Float totalPrice;
    private Integer discountPercent;
    private String isSupply;
    private String isRealization;
    private Float promoCodeDiscount;
    private String warehouseName;
//    @JsonProperty("countryName")
    private String countryName;
//    @JsonProperty("oblast")
    private String oblastOkrugName;
//    @JsonProperty("regionName")
    private String regionName;
    private Long incomeID;
//    @JsonProperty("saleID")
    private String saleID;
    private Long odid;
    private String spp;
    private String forPay;
    private String finishedPrice;
    private String priceWithDisc;
    private Long nmId;
    private String subject;
    private String category;
    private String brand;
//    @JsonProperty("IsStorno")
    private Integer isStorno;
    private String sticker;
    private String srid;
}

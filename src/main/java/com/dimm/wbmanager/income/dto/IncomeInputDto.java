package com.dimm.wbmanager.income.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeInputDto {
    private Long incomeId;
    private String number;
    private String date;
    private String lastChangeDate;
    private String supplierArticle;
    private String techSize;
    private String barcode;
    private Integer quantity;
    private Float totalPrice;
    private String dateClose;
    private String warehouseName;
    private Integer nmId;
    private String status;
}

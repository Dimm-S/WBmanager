package com.dimm.wbmanager.income;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "incomes")
public class Income {
    @Column(name = "incomeid")
    private Long incomeId;
    @Column
    private String number;
    @Column
    private LocalDateTime date;
    @Column(name = "lastchangedate")
    private LocalDateTime lastChangeDate;
    @Column(name = "supplierarticle")
    private String supplierArticle;
    @Column(name = "techsize")
    private String techSize;
    @Column
    private String barcode;
    @Column
    private Integer quantity;
    @Column(name = "totalprice")
    private Float totalPrice;
    @Column(name = "dateclose")
    private LocalDateTime dateClose;
    @Column(name = "warehousename")
    private String warehouseName;
    @Column(name = "nmid")
    private Integer nmId;
    @Column
    private String status;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}

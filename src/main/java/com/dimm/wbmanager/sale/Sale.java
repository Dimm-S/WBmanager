package com.dimm.wbmanager.sale;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "gnumber")
    private String gNumber;
    @Column(name = "order_date")
    private LocalDateTime date;
    @Column(name = "last_change_date")
    private LocalDateTime lastChangeDate;
    @Column(name = "supplier_article")
    private String supplierArticle;
    @Column(name = "tech_size")
    private String techSize;
    @Column
    private String barcode;
    @Column(name = "total_price")
    private Float totalPrice;
    @Column(name = "discount_percent")
    private Integer discountPercent;
    @Column(name = "is_supply")
    private Boolean isSupply;
    @Column(name = "is_realisation")
    private Boolean isRealization;
    @Column(name = "promo_code_discount")
    private Float promoCodeDiscount;
    @Column(name = "warehouse_name")
    private String warehouseName;
    @Column(name = "country_name")
    private String countryName;
    @Column(name = "oblast_okrug_name")
    private String oblastOkrugName;
    @Column(name = "region_name")
    private String regionName;
    @Column(name = "income_id")
    private Long incomeID;
    @Column(name = "sale_id")
    private String saleID;
    @Column
    private Long odid;
    @Column
    private Float spp;
    @Column(name = "for_pay")
    private Float forPay;
    @Column(name = "finished_price")
    private Float finishedPrice;
    @Column(name = "price_with_disc")
    private Float priceWithDisc;
    @Column(name = "nm_id")
    private Long nmId;
    @Column
    private String subject;
    @Column
    private String category;
    @Column
    private String brand;
    @Column(name = "is_storno")
    private Integer isStorno;
    @Column
    private String sticker;
    @Column
    private String srid;
}

package com.dimm.wbmanager.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "g_number")
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
    @Column(name = "warehouse_name")
    private String warehouseName;
    @Column
    private String oblast;
    @Column(name = "income_id")
    private Long incomeID;
    @Column
    private Long odid;
    @Column(name = "nm_id")
    private Long nmId;
    @Column
    private String subject;
    @Column
    private String category;
    @Column
    private String brand;
    @Column(name = "is_cancel")
    private Boolean isCancel;
    @Column
    private LocalDateTime cancel_dt;
    @Column
    private String sticker;
    @Column
    private String srid;
}

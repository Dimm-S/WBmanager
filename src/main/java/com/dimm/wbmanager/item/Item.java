package com.dimm.wbmanager.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "items")
public class Item {
    @Id
    private String barcode;
    @Column
    private String brand;
    @Column(name = "supplier_article")
    private String supplierArticle;
    @Column(name = "tech_size")
    private String techSize;
    @Column(name = "nm_id")
    private Long nmId;
    @Column
    private String name;
}

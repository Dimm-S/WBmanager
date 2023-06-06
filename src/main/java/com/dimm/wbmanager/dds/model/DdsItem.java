package com.dimm.wbmanager.dds.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dds_items")
public class DdsItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "item")
    private String item;
    @Column(name = "description")
    private String description;
    @Column(name = "items_group")
    private Integer itemsGroup;
    @Column(name = "is_incoming")
    private Boolean isIncoming;
    @Column(name = "is_inside")
    private Boolean isInside;
    @Column(name = "type")
    private String type;
    @Column(name = "mun_1")
    private Integer type1;
    @Column(name = "mun_2")
    private Integer type2;
}

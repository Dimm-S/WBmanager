package com.dimm.wbmanager.dds.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dds_operations")
public class DdsOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "oper_date")
    private LocalDate operDate;
    @Column(name = "dds_item")
    private Integer ddsItem;
    @Column(name = "sum")
    private Double sum;
    @Column(name = "account")
    private Integer account;
    @Column(name = "subject")
    private String subject;
    @Column(name = "description")
    private String description;
}

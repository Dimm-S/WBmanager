package com.dimm.wbmanager.updatestat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "updatestat")
public class UpdateStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  //номер записи
    @Column
    LocalDateTime time; //время
    @Column(name = "table_name")
    String tableName;  //имя таблицы
    @Column(name = "is_update")
    Boolean isUpdate; //обновление записи
    @Column
    Long odid;  //odid
}

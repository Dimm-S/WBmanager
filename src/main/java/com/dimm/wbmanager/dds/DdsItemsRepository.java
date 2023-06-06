package com.dimm.wbmanager.dds;

import com.dimm.wbmanager.dds.model.DdsItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DdsItemsRepository extends JpaRepository<DdsItem, Long> {
    @Query(nativeQuery = true, value = "SELECT * " +
            "FROM dds_items " +
            "WHERE type = 'oper' " +
            "ORDER BY item;")
    List<DdsItem> getDdsItemsByType();

    @Query(nativeQuery = true, value = "SELECT item " +
            "FROM dds_items " +
            "WHERE type = ?1 " +
            "ORDER BY item;")
    List<String> getDdsItemsNamesByType(String type);

    @Query(nativeQuery = true, value = "SELECT * " +
            "FROM dds_items " +
            "ORDER BY item;")
    List<DdsItem> getAll();
}

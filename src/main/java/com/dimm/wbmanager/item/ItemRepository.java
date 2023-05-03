package com.dimm.wbmanager.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository <Item, String> {

    @Query(nativeQuery = true, value = "SELECT * " +
            "FROM postgres.public.items " +
            "WHERE name = 'Unidentified item'")
    List<Item> getUnidItems();
}

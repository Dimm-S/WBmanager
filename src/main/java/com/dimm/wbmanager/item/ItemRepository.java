package com.dimm.wbmanager.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ItemRepository extends JpaRepository <Item, String> {

    @Query(nativeQuery = true, value = "SELECT * " +
            "FROM postgres.public.items " +
            "WHERE name = 'Unidentified item'")
    List<Item> getUnidItems();

    @Query(nativeQuery = true, value =
            "SELECT DISTINCT name from postgres.public.items WHERE items.name != '' ORDER BY name ASC")
    List<String> getAllItemNames();


    @Query(nativeQuery = true, value =
    "SELECT DISTINCT brand from postgres.public.items WHERE brand != '' ORDER BY brand ASC ")
    List<String> getAllBrands();
}

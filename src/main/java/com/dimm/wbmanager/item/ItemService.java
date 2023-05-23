package com.dimm.wbmanager.item;

import java.util.List;

public interface ItemService {

    void saveNewItem(Item item);

    List<String> getAllItemNames();

    List<String> getAllBrands();

    List<Item> getUnidItems();
}

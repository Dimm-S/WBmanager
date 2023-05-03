package com.dimm.wbmanager.item;

import java.util.List;

public interface ItemService {

    void saveNewItem(Item item);

    List<Item> getAllItems();

    List<Item> getUnidItems();
}

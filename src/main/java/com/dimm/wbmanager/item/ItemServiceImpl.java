package com.dimm.wbmanager.item;

import com.dimm.wbmanager.updatestat.UpdateStatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UpdateStatService statService;

    /**
     * Добавление нового товара
     */
    @Override
    public void saveNewItem(Item item) {
        itemRepository.save(item);
        statService.addRecord("items", false, 0L);
    }

    /**
     * Получение списка товаров
     * @return List of Items
     */
    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    /**
     * Получение списка товаров без наименования
     * @return List товаров с наименованием Unidentified item
     */
    @Override
    public List<Item> getUnidItems() {
        return itemRepository.getUnidItems();
    }
}

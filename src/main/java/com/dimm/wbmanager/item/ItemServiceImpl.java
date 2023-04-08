package com.dimm.wbmanager.item;

import com.dimm.wbmanager.updatestat.UpdateStatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UpdateStatService statService;

    @Override
    public void saveNewItem(Item item) {
        itemRepository.save(item);
        statService.addRecord("items", false, 0L);
    }
}

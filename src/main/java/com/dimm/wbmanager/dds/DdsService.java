package com.dimm.wbmanager.dds;

import com.dimm.wbmanager.dds.dto.DdsNewItemDto;
import com.dimm.wbmanager.dds.dto.DdsNewOperationDto;
import com.dimm.wbmanager.dds.dto.DdsOperationDto;
import com.dimm.wbmanager.dds.dto.DdsSimpleDto;
import com.dimm.wbmanager.dds.model.DdsAccount;
import com.dimm.wbmanager.dds.model.DdsItem;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public interface DdsService {
    List<DdsSimpleDto> getDds();

    List<String> getItemsNames(String type);

    HashMap<Long, String> getAllItemsNames();

    HashMap<Long, String> getAllGroupsNames();

    List<DdsItem> getAllItems();

    List<DdsAccount> getAllAccountsNames();

    List<DdsOperationDto> getAllOperations(String from, String to);

    void createOperation(DdsNewOperationDto operationDto);

    void createItem(DdsNewItemDto itemDto);


}

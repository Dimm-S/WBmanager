package com.dimm.wbmanager.dds;

import com.dimm.wbmanager.dds.dto.DdsNewOperationDto;
import com.dimm.wbmanager.dds.dto.DdsOperationDto;
import com.dimm.wbmanager.dds.dto.DdsSimpleDto;
import com.dimm.wbmanager.dds.model.DdsAccount;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public interface DdsService {
    List<DdsSimpleDto> getDds();

    List<String> getItemsNames(String type);

    HashMap<Long, String> getAllItemsNames();

    List<DdsAccount> getAllAccountsNames();

    List<DdsOperationDto> getAllOperations(String from, String to);

    void createOperation(DdsNewOperationDto operationDto);
}

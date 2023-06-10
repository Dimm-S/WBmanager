package com.dimm.wbmanager.dds;

import com.dimm.wbmanager.Month;
import com.dimm.wbmanager.dds.dto.DdsNewOperationDto;
import com.dimm.wbmanager.dds.dto.DdsOperationDto;
import com.dimm.wbmanager.dds.dto.DdsSimpleDto;
import com.dimm.wbmanager.dds.model.DdsAccount;
import com.dimm.wbmanager.dds.model.DdsItem;
import com.dimm.wbmanager.dds.model.DdsOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class DdsServiceImpl implements DdsService {
    private final DdsOperationsRepository operationsRepository;
    private final DdsItemsRepository itemsRepository;
    private final DdsAccountRepository accountRepository;

    @Override
    public List<DdsSimpleDto> getDds() {
        List<Object[]> dds = operationsRepository.getDds();

        List<DdsSimpleDto> simpleDds = new ArrayList<>();
        for (Object[] d : dds) {
            DdsSimpleDto dto = new DdsSimpleDto();
            dto.setMonth(Month.values()[Integer.parseInt(String.valueOf(d[0]), 0,
                    (String.valueOf(d[0]).length()) - 2, 10) - 1]);
            dto.setYear(((Double) d[1]).intValue());
            dto.setOperSum((Double) d[2]);
            dto.setOperItemsSum(operationsRepository.getDdsByItemsForPeriod(
                    dto.getMonth().ordinal() + 1, dto.getYear(), "oper"));
            dto.setInvestSum((Double) d[3]);
            dto.setInvestItemsSum(operationsRepository.getDdsByItemsForPeriod(
                    dto.getMonth().ordinal() + 1, dto.getYear(), "invest"));
            dto.setFinSum((Double) d[4]);
            dto.setFinItemsSum(operationsRepository.getDdsByItemsForPeriod(
                    dto.getMonth().ordinal() + 1, dto.getYear(), "fin"));
            dto.setSumDelta((Double) d[2] + (Double) d[3] + (Double) d[4]);
            simpleDds.add(dto);
        }

        return simpleDds;
    }

    @Override
    public List<String> getItemsNames(String type) {
        return itemsRepository.getDdsItemsNamesByType(type);
    }

    @Override
    public HashMap<Long, String> getAllItemsNames() {
        List<DdsItem> ddsItemList = itemsRepository.getAll();
        HashMap<Long, String> map = new HashMap<>();
        for (DdsItem item : ddsItemList) {
            map.put(item.getId(), item.getItem());
        }
        return map;
    }

    @Override
    public List<DdsAccount> getAllAccountsNames() {
        return accountRepository.getAll();
    }

    @Override
    public List<DdsOperationDto> getAllOperations(String from, String to) {
        return operationsRepository.getAllOperations(LocalDate.parse(from), LocalDate.parse(to));
    }

    @Override
    public void createOperation(DdsNewOperationDto operationDto) {
        operationsRepository.saveAndFlush(new DdsOperation(
                null,
                LocalDate.parse(operationDto.getDate()),
                operationDto.getItemId(),
                operationDto.getSum(),
                operationDto.getAccountId(),
                operationDto.getSubject(),
                operationDto.getDescription()));
    }
}

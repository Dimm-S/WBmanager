package com.dimm.wbmanager.updatestat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UpdateStatServiceImpl implements UpdateStatService {
    private final UpdateStatRepository updateStatRepository;

    @Override
    public void addRecord(String tableName, Boolean isUpdate, Long odid) {
        UpdateStat record = new UpdateStat(
                null,
                LocalDateTime.now(),
                tableName,
                isUpdate,
                odid
        );

        updateStatRepository.save(record);
    }
}

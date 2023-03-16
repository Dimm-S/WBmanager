package com.dimm.wbmanager.income;

import com.dimm.wbmanager.income.dto.IncomeInputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class IncomeMapper {

    public Income mapInputToIncome(IncomeInputDto dto) {
        return new Income(
                dto.getIncomeId(),
                dto.getNumber(),
                LocalDateTime.parse(dto.getDate()),
                LocalDateTime.parse(dto.getLastChangeDate()),
                dto.getSupplierArticle(),
                dto.getTechSize(),
                dto.getBarcode(),
                dto.getQuantity(),
                dto.getTotalPrice(),
                LocalDateTime.parse(dto.getDateClose()),
                dto.getWarehouseName(),
                dto.getNmId(),
                dto.getStatus(),
                null
        );
    }
}

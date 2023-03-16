package com.dimm.wbmanager.order;

import com.dimm.wbmanager.order.dto.OrderInputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderMapper {

    public Order mapInputToOrder(OrderInputDto dto) {
        return new Order(
                null,
                dto.getGNumber(),
                LocalDateTime.parse(dto.getDate()),
                LocalDateTime.parse(dto.getLastChangeDate()),
                dto.getSupplierArticle(),
                dto.getTechSize(),
                dto.getBarcode(),
                dto.getTotalPrice(),
                dto.getDiscountPercent(),
                dto.getWarehouseName(),
                dto.getOblast(),
                dto.getIncomeID(),
                dto.getOdid(),
                dto.getNmId(),
                dto.getSubject(),
                dto.getCategory(),
                dto.getBrand(),
                dto.getIsCancel(),
                LocalDateTime.parse(dto.getCancel_dt()),
                dto.getSticker(),
                dto.getSrid()
        );
    }
}

package com.dimm.wbmanager.sale;

import com.dimm.wbmanager.sale.dto.SaleInputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleMapper {

    public Sale mapInputToSale(SaleInputDto dto) {

        return new Sale(
                null,
                dto.getGNumber(),
                LocalDateTime.parse(dto.getDate()),
                LocalDateTime.parse(dto.getLastChangeDate()),
                dto.getSupplierArticle(),
                dto.getTechSize(),
                dto.getBarcode(),
                dto.getTotalPrice(),
                dto.getDiscountPercent(),
                Boolean.parseBoolean(dto.getIsSupply()),
                Boolean.parseBoolean(dto.getIsRealization()),
                dto.getPromoCodeDiscount(),
                dto.getWarehouseName(),
                dto.getCountryName(),
                dto.getOblastOkrugName(),
                dto.getRegionName(),
                dto.getIncomeID(),
                dto.getSaleID(),
                dto.getOdid(),
                Float.parseFloat(dto.getSpp()),
                Float.parseFloat(dto.getForPay()),
                Float.parseFloat(dto.getFinishedPrice()),
                Float.parseFloat(dto.getPriceWithDisc()),
                dto.getNmId(),
                dto.getSubject(),
                dto.getCategory(),
                dto.getBrand(),
                dto.getIsStorno(),
                dto.getSticker(),
                dto.getSrid()
        );
    }

    public List<Object> mapArrayToList(Object[] object) {
        List<Object> list = new ArrayList<>();
        list.add(object[0].toString().substring(0, object[0].toString().length() - 2));
        list.add(object[1]);
        return list;
    }
}

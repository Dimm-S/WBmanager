package com.dimm.wbmanager.reportdetailbyperiod;

import com.dimm.wbmanager.reportdetailbyperiod.dto.ReportDetailByPeriodInputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportDetailByPeriodMapper {
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;

    public ReportDetailByPeriod mapInputToReport(ReportDetailByPeriodInputDto dto) {
        LocalDateTime createDate;
        LocalDateTime orderDate;
        LocalDateTime saleDate;

        if (dto.getCreate_dt() != null) {
            createDate = LocalDateTime.parse(dto.getCreate_dt(), formatter);
        } else {
            createDate = null;
        }

        if (dto.getOrder_dt() != null) {
            orderDate = LocalDateTime.parse(dto.getOrder_dt(), formatter);
        } else {
            orderDate = null;
        }

        if (dto.getSale_dt() != null) {
            saleDate = LocalDateTime.parse(dto.getSale_dt(), formatter);
        } else {
            saleDate = null;
        }

        return new ReportDetailByPeriod(
                dto.getRealizationreport_id(),
                LocalDateTime.parse(dto.getDate_from(), formatter),
                LocalDateTime.parse(dto.getDate_to(), formatter),
                createDate,
                dto.getSuppliercontract_code(),
                dto.getRrd_id(),
                dto.getGi_id(),
                dto.getSubject_name(),
                dto.getNm_id(),
                dto.getBrand_name(),
                dto.getSa_name(),
                dto.getTs_name(),
                dto.getBarcode(),
                dto.getDoc_type_name(),
                dto.getQuantity(),
                dto.getRetail_price(),
                dto.getRetail_amount(),
                dto.getSale_percent(),
                dto.getCommission_percent(),
                dto.getOffice_name(),
                dto.getSupplier_oper_name(),
                orderDate,
                saleDate,
                LocalDateTime.parse(dto.getRr_dt(), formatter),
                dto.getShk_id(),
                dto.getRetail_price_withdisc_rub(),
                dto.getDelivery_amount(),
                dto.getReturn_amount(),
                dto.getDelivery_rub(),
                dto.getGi_box_type_name(),
                dto.getProduct_discount_for_report(),
                dto.getSupplier_promo(),
                dto.getRid(),
                dto.getPpvz_spp_prc(),
                dto.getPpvz_kvw_prc_base(),
                dto.getPpvz_kvw_prc(),
                dto.getSup_rating_prc_up(),
                dto.getIs_kgvp_v2(),
                dto.getPpvz_sales_commission(),
                dto.getPpvz_for_pay(),
                dto.getPpvz_reward(),
                dto.getAcquiring_fee(),
                dto.getAcquiring_bank(),
                dto.getPpvz_vw(),
                dto.getPpvz_vw_nds(),
                dto.getPpvz_office_id(),
                dto.getPpvz_office_name(),
                dto.getPpvz_supplier_id(),
                dto.getPpvz_supplier_name(),
                dto.getPpvz_inn(),
                dto.getDeclaration_number(),
                dto.getBonus_type_name(),
                dto.getSticker_id(),
                dto.getSite_country(),
                dto.getPenalty(),
                dto.getAdditional_payment(),
                dto.getSrid(),
                null
        );
    }

    public List<Object> mapArrayToList(Object[] object) {
        List<Object> list = new ArrayList<>();
        list.add(object[0]);
        list.add(object[1]);
        return list;
    }
}

package com.dimm.wbmanager.analytics;

import com.dimm.wbmanager.Month;
import com.dimm.wbmanager.analytics.dto.AmountByMonthDto;
import com.dimm.wbmanager.analytics.dto.OrdersAndSalesByDateDto;
import com.dimm.wbmanager.analytics.dto.OrdersAndSalesForDashbordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AnalyticsMapper {

    public AmountByMonthDto mapToAmountByMonthFromObject(Object[] object) {
        return new AmountByMonthDto(
                Month.values()[Integer.parseInt(String.valueOf(object[0]), 0,
                        (String.valueOf(object[0]).length()) - 2, 10) - 1],
                (Float) object[2],
                (Float) object[3]
        );
    }

    public List<Object> mapArrayToList(Object[] object) {
        List<Object> list = new ArrayList<>();
        list.add(String.valueOf(Month.values()[Integer.parseInt(String.valueOf(object[0]), 0,
                (String.valueOf(object[0]).length()) - 2, 10) - 1]));
        list.add(object[2]);
        list.add(object[3]);
        return list;
    }

    public List<OrdersAndSalesForDashbordDto> mapForDayliTable(
            List<LocalDate> datesList,
            List<List<Object[]>> orders,
            List<List<Object[]>> sales,
            List<List<Object[]>> returns,
            List<List<Object[]>> forPay) {

        List<OrdersAndSalesForDashbordDto> list = new ArrayList<>();
        for (LocalDate date : datesList) {
            OrdersAndSalesForDashbordDto dto = new OrdersAndSalesForDashbordDto();
            dto.setDate(date);
            for (List<Object[]> o : orders) {
                if (Objects.equals(o.get(0)[0].toString(), date.toString())) {
                    dto.setOrders(new OrdersAndSalesForDashbordDto.Orders(
                            ((BigInteger) o.get(1)[0]).intValue(), (Double) o.get(2)[0]));
                }
            }
            if (dto.getOrders() == null) {
                dto.setOrders(new OrdersAndSalesForDashbordDto.Orders(0, 0.0));
            }

            for (List<Object[]> s : sales) {
                if (Objects.equals(s.get(0)[0].toString(), date.toString())) {
                    dto.setSales(new OrdersAndSalesForDashbordDto.Sales(
                            ((BigInteger) s.get(1)[0]).intValue(), (Float) s.get(2)[0]));
                }
            }
            if (dto.getSales() == null) {
                dto.setSales(new OrdersAndSalesForDashbordDto.Sales(0, 0.0F));
            }

            for (List<Object[]> r : returns) {
                if (Objects.equals(r.get(0)[0].toString(), date.toString())) {
                    dto.setReturns(new OrdersAndSalesForDashbordDto.Returns(
                            ((BigInteger) r.get(1)[0]).intValue(), (Float) r.get(2)[0]));
                }
            }
            if (dto.getReturns() == null) {
                dto.setReturns(new OrdersAndSalesForDashbordDto.Returns(0, 0.0F));
            }

            for (List<Object[]> f : forPay) {
                if (Objects.equals(f.get(0)[0].toString(), date.toString())) {
                    dto.setForPay((Float) f.get(1)[0]);
                }
            }
            if (dto.getForPay() == null) {
                dto.setForPay(0.0F);
            }

            list.add(dto);
        }
        return list;
    }

    public List<OrdersAndSalesByDateDto> mapToDetails(List<List<Object[]>> orders,
                                                      List<List<Object[]>> salesAndReturnsAndForPay) {
        Set<String> names = new HashSet<>();
        for (List<Object[]> o : orders) {
            names.add((String) o.get(0)[0]);
        }
        for (List<Object[]> s : salesAndReturnsAndForPay) {
            names.add((String) s.get(0)[0]);
        }

        List<OrdersAndSalesByDateDto> list = new ArrayList<>();
        for (String n : names) {
            OrdersAndSalesByDateDto dto = new OrdersAndSalesByDateDto();
            dto.setName(n);

            for (List<Object[]> o : orders) {
                if (Objects.equals(o.get(0)[0].toString(), n)) {
                    dto.setOrdersQuantity(((BigInteger) o.get(1)[0]).intValue());
                    dto.setOrdersSum((Double) o.get(2)[0]);
                }
            }
            if (dto.getOrdersQuantity() == null) {
                dto.setOrdersQuantity(0);
            }
            if (dto.getOrdersSum() == null) {
                dto.setOrdersSum(0.0);
            }

            for (List<Object[]> s : salesAndReturnsAndForPay) {
                if (Objects.equals(s.get(0)[0].toString(), n)) {
                    dto.setSalesQuantity(((BigInteger) s.get(1)[0]).intValue());
                    dto.setSalesSum((Float) s.get(2)[0]);
                    dto.setReturnsQuantity(((BigInteger) s.get(3)[0]).intValue());
                    dto.setReturnSum((Float) s.get(4)[0]);
                    dto.setForPay((Float) s.get(5)[0]);
                }
            }
            if (dto.getSalesQuantity() == null) {
                dto.setSalesQuantity(0);
            }
            if (dto.getSalesSum() == null) {
                dto.setSalesSum(0.0F);
            }
            if (dto.getReturnsQuantity() == null) {
                dto.setReturnsQuantity(0);
            }
            if (dto.getReturnSum() == null) {
                dto.setReturnSum(0.0F);
            }
            if (dto.getForPay() == null) {
                dto.setForPay(0.0F);
            }
            list.add(dto);
        }
        return list;
    }
}

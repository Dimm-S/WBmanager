package com.dimm.wbmanager.analytics;

import com.dimm.wbmanager.Month;
import com.dimm.wbmanager.analytics.dto.*;
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
        list.add(object[4]);
        return list;
    }

    public List<OrdersSalesReturnsForPayDto> mapForDayliTable(
            List<LocalDate> datesList,
            List<List<Object[]>> orders,
            List<List<Object[]>> sales,
            List<List<Object[]>> returns,
            List<List<Object[]>> forPay) {

        List<OrdersSalesReturnsForPayDto> list = new ArrayList<>();
        for (LocalDate date : datesList) {
            OrdersSalesReturnsForPayDto dto = new OrdersSalesReturnsForPayDto();
            dto.setDate(date);
            for (List<Object[]> o : orders) {
                if (Objects.equals(o.get(0)[0].toString(), date.toString())) {
                    dto.setOrders(new OrdersSalesReturnsForPayDto.Orders(
                            ((BigInteger) o.get(1)[0]).intValue(), (Double) o.get(2)[0]));
                }
            }
            if (dto.getOrders() == null) {
                dto.setOrders(new OrdersSalesReturnsForPayDto.Orders(0, 0.0));
            }

            for (List<Object[]> s : sales) {
                if (Objects.equals(s.get(0)[0].toString(), date.toString())) {
                    dto.setSales(new OrdersSalesReturnsForPayDto.Sales(
                            ((BigInteger) s.get(1)[0]).intValue(), (Float) s.get(2)[0]));
                }
            }
            if (dto.getSales() == null) {
                dto.setSales(new OrdersSalesReturnsForPayDto.Sales(0, 0.0F));
            }

            for (List<Object[]> r : returns) {
                if (Objects.equals(r.get(0)[0].toString(), date.toString())) {
                    dto.setReturns(new OrdersSalesReturnsForPayDto.Returns(
                            ((BigInteger) r.get(1)[0]).intValue(), (Float) r.get(2)[0]));
                }
            }
            if (dto.getReturns() == null) {
                dto.setReturns(new OrdersSalesReturnsForPayDto.Returns(0, 0.0F));
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
        /* Собираем наименования товаров из двух списков в один хэшсет */
        Set<String> names = compileNamesToSet(orders, salesAndReturnsAndForPay);

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

    public List<MonthDetailedReportDto> mapToDetailedMonthDto(List<List<Object[]>> orders,
                                                              List<List<Object[]>> salesAndBuybacks) {

        /* Собираем наименования товаров из двух списков в один хэшсет */
        Set<String> names = compileNamesToSet(orders, salesAndBuybacks);

        List<MonthDetailedReportDto> list = new ArrayList<>();

        for (String n : names) {
            MonthDetailedReportDto dto = new MonthDetailedReportDto();
            dto.setName(n);

            /* Обработка списка заказов */
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

            /* Обработка списка продаж и возвратов */
            for (List<Object[]> s : salesAndBuybacks) {
                if (Objects.equals(s.get(0)[0].toString(), n)) {
                    dto.setSalesQuantity(((BigInteger) s.get(1)[0]).intValue());
                    dto.setSalesSum((Float) s.get(2)[0]);
                    dto.setReturnsQuantity(((BigInteger) s.get(3)[0]).intValue());
                    dto.setReturnSum((Float) s.get(4)[0]);
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

            /* Расчёт процентов */
            if (dto.getSalesQuantity() != 0) {
                dto.setReturnsPctQnt(((double)dto.getReturnsQuantity() / (double) dto.getSalesQuantity() * 100));
            } else {
                dto.setReturnsPctQnt(100D);
            }

            if (dto.getSalesSum() != 0) {
                dto.setReturnsPctSum((double) (dto.getReturnSum() / dto.getSalesSum() * 100));
            } else {
                dto.setReturnsPctSum(100D);
            }

            if (dto.getOrdersQuantity() != 0) {
                dto.setBuyoutPctInQnt((double) dto.getSalesQuantity() / (double) dto.getOrdersQuantity() * 100);
            } else {
                dto.setBuyoutPctInQnt(0D);
            }

            if (dto.getOrdersSum() != 0) {
                dto.setBuyoutPctInSum((double) dto.getSalesSum() / dto.getOrdersSum() * 100);
            } else {
                dto.setBuyoutPctInSum(0D);
            }

            list.add(dto);
        }

        return list;
    }

    public List<StatByMonthsInfoDto> mapToOverallStat(List<Object[]> orders, List<Object[]> salesAndReturns) {
        List<StatByMonthsInfoDto> overallStat = new ArrayList<>();

        for (int i = 0; i < orders.size(); i++) {
            StatByMonthsInfoDto dto = new StatByMonthsInfoDto();
            dto.setName("OVERALL");
            dto.setMonth(Month.values()[Integer.parseInt(String.valueOf(orders.get(i)[0]), 0,
                    (String.valueOf(orders.get(i)[0]).length()) - 2, 10) - 1]);
            dto.setYear(((Double) orders.get(i)[1]).intValue());
            dto.setOrdersQuantity(((BigInteger) orders.get(i)[2]).intValue());
            dto.setOrdersSum((Double) orders.get(i)[3]);
            dto.setSalesQuantity(((BigInteger) salesAndReturns.get(i)[2]).intValue());
            dto.setSalesSum((Float) salesAndReturns.get(i)[3]);
            dto.setReturnsQuantity(((BigInteger) salesAndReturns.get(i)[4]).intValue());
            dto.setReturnSum((Float) salesAndReturns.get(i)[5]);
            if (overallStat.size() == 0) {
                dto.setReturnsPctQnt((double) dto.getReturnsQuantity() / (double) dto.getSalesQuantity() * 100);
                dto.setBuyoutPctInQnt((double) dto.getSalesQuantity() / (double) dto.getOrdersQuantity() * 100);
            } else {
                dto.setReturnsPctQnt(
                        (overallStat.stream().mapToDouble(StatByMonthsInfoDto::getReturnsQuantity).sum() +
                                (double) dto.getReturnsQuantity()) /
                                (overallStat.stream().mapToDouble(StatByMonthsInfoDto::getSalesQuantity).sum() +
                                        (double) dto.getSalesQuantity()) * 100);
                dto.setBuyoutPctInQnt(
                        (overallStat.stream().mapToDouble(StatByMonthsInfoDto::getSalesQuantity).sum() +
                                (double) dto.getSalesQuantity()) /
                                (overallStat.stream().mapToDouble(StatByMonthsInfoDto::getOrdersQuantity).sum() +
                                        (double) dto.getOrdersQuantity()) * 100);
            }

            overallStat.add(dto);
        }
        return overallStat;
    }


    public List<StatByMonthsInfoDto> mapToItemStat(List<Object[]> orders, List<Object[]> salesAndReturns) {
        List<StatByMonthsInfoDto> itemStat = new ArrayList<>();

        for (int i = 0; i < orders.size(); i++) {
            StatByMonthsInfoDto dto = new StatByMonthsInfoDto();
            dto.setName((String) orders.get(i)[0]);
            dto.setMonth(Month.values()[Integer.parseInt(String.valueOf(orders.get(i)[1]), 0,
                    (String.valueOf(orders.get(i)[1]).length()) - 2, 10) - 1]);
            dto.setYear(((Double) orders.get(i)[2]).intValue());
            dto.setOrdersQuantity(((BigInteger) orders.get(i)[3]).intValue());
            dto.setOrdersSum((Double) orders.get(i)[4]);
            dto.setSalesQuantity(((BigInteger) salesAndReturns.get(i)[2]).intValue());
            dto.setSalesSum((Float) salesAndReturns.get(i)[3]);
            dto.setReturnsQuantity(((BigInteger) salesAndReturns.get(i)[4]).intValue());
            dto.setReturnSum((Float) salesAndReturns.get(i)[5]);
            if (itemStat.size() == 0) {
                dto.setReturnsPctQnt((double) dto.getReturnsQuantity() / (double) dto.getSalesQuantity() * 100);
                dto.setBuyoutPctInQnt((double) dto.getSalesQuantity() / (double) dto.getOrdersQuantity() * 100);
            } else {
                dto.setReturnsPctQnt(
                        (itemStat.stream().mapToDouble(StatByMonthsInfoDto::getReturnsQuantity).sum() +
                                (double) dto.getReturnsQuantity()) /
                                (itemStat.stream().mapToDouble(StatByMonthsInfoDto::getSalesQuantity).sum() +
                                        (double) dto.getSalesQuantity()) * 100);
                dto.setBuyoutPctInQnt(
                        (itemStat.stream().mapToDouble(StatByMonthsInfoDto::getSalesQuantity).sum() +
                                (double) dto.getSalesQuantity()) /
                                (itemStat.stream().mapToDouble(StatByMonthsInfoDto::getOrdersQuantity).sum() +
                                        (double) dto.getOrdersQuantity()) * 100);
            }

            itemStat.add(dto);
        }
        return itemStat;
    }

    private static Set<String> compileNamesToSet(List<List<Object[]>> list1,
                                                 List<List<Object[]>> list2) {
        Set<String> names = new HashSet<>();
        for (List<Object[]> o : list1) {
            names.add((String) o.get(0)[0]);
        }
        for (List<Object[]> s : list2) {
            names.add((String) s.get(0)[0]);
        }

        return names;
    }
}

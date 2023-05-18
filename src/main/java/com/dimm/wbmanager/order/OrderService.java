package com.dimm.wbmanager.order;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    void updateTable() throws IOException, InterruptedException;

    List<List<Object[]>> getOrdersAndSum(LocalDate from, LocalDate to);

    List<List<Object[]>> getOrdersAndSumByDate(String date);

    List<Object[]> getOrdersAndSumByMonths();

    List<List<Object[]>> getMonthOrdersAndSumByItems(String month);

    List<Object[]> getItemOrdersAndSumByMonths(String item);

    List<Object[]> getBrandOrdersAndSumByMonths(String brand);
}

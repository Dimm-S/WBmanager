package com.dimm.wbmanager.sale;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface SaleService {
    void updateTable() throws IOException, InterruptedException;

    List<List<Object>> getSalesByDays();

    List<List<Object[]>> getSalesAndSum(LocalDate from, LocalDate to);

    List<List<Object[]>> getReturnsAndSum(LocalDate from, LocalDate to);

    List<List<Object[]>> getForPay(LocalDate from, LocalDate to);

    List<List<Object[]>> getSalesAndSumAndReturnsAndForPayByDate(String date);
}

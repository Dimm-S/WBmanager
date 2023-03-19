package com.dimm.wbmanager.order;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    void updateTable() throws IOException, InterruptedException;

    List<List<Object[]>> getOrdersAndSum();

    List<List<Object[]>> getOrdersAndSumByDate(String date);
}
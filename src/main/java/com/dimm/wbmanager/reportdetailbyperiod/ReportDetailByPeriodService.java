package com.dimm.wbmanager.reportdetailbyperiod;

import com.dimm.wbmanager.analytics.dto.AmountByMonthDto;

import java.io.IOException;
import java.util.List;

public interface ReportDetailByPeriodService {

    void updateTable() throws IOException, InterruptedException;

    List<AmountByMonthDto> getAmountByMonthForTable();

    List<List<Object>> getAmountByMonthForChart();
}
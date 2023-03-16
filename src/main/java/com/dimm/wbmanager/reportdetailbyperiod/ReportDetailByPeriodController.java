package com.dimm.wbmanager.reportdetailbyperiod;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/reportdetailbyperiod")
public class ReportDetailByPeriodController {
    private final ReportDetailByPeriodService reportDetailByPeriodService;

    @GetMapping
    public void updateTable() throws IOException, InterruptedException {
        reportDetailByPeriodService.updateTable();
    }
}

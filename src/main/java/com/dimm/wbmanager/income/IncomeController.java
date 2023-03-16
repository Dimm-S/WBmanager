package com.dimm.wbmanager.income;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/incomes")
public class IncomeController {
    private final IncomeService incomeService;

    @GetMapping
    public void updateTable() throws IOException, InterruptedException {
        incomeService.updateTable();
    }
}

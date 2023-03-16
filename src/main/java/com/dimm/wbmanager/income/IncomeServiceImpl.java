package com.dimm.wbmanager.income;

import com.dimm.wbmanager.income.dto.IncomeInputDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IncomeServiceImpl implements IncomeService {
    private final IncomeRepository incomeRepository;
    private final IncomeMapper incomeMapper;

    @Override
    public void updateTable() throws IOException {

        final RestTemplate restTemplate = new RestTemplate();
        final String incomes = restTemplate.getForObject
                ("https://suppliers-stats.wildberries.ru/api/v1/supplier/incomes" +
                        "?dateFrom=2022-09-01&key=Yjk0ZDQyZjYtMDBkNy00YjcwLTk4NDItNzZhMGJiNjk2Nzdm", String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, IncomeInputDto.class); //описание типа коллекции, в которую надо преобразовать String
        List<IncomeInputDto> incomesDtoList = objectMapper.readValue(incomes, listType); // маппинг String в лист объектов

        List<Income> incomesList = incomesDtoList.stream()
                .map(incomeMapper::mapInputToIncome)
                .collect(Collectors.toList());

        incomeRepository.saveAll(incomesList);
    }
}

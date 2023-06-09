package com.dimm.wbmanager.sale;

import com.dimm.wbmanager.item.Item;
import com.dimm.wbmanager.item.ItemService;
import com.dimm.wbmanager.sale.dto.SaleInputDto;
import com.dimm.wbmanager.updatestat.UpdateStatService;
import com.dimm.wbmanager.utils.Token;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;
    private final UpdateStatService updateStatService;
    private final ItemService itemService;
    private final Token token;

    @Override
    public void updateTable() throws IOException, InterruptedException {
        final RestTemplate restTemplate = new RestTemplate();
        final String sales = restTemplate.getForObject
                ("https://suppliers-stats.wildberries.ru/api/v1/supplier/sales" +
                        "?dateFrom=2022-09-01&key=Yjk0ZDQyZjYtMDBkNy00YjcwLTk4NDItNzZhMGJiNjk2Nzdm", String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, SaleInputDto.class); //описание типа коллекции, в которую надо преобразовать String
        List<SaleInputDto> salesDtoList = objectMapper.readValue(sales, listType); // маппинг String в лист объектов

        List<Sale> ordersList = salesDtoList.stream()
                .map(saleMapper::mapInputToSale)
                .collect(Collectors.toList());

        saleRepository.saveAll(ordersList);
    }

    @Override
    public List<List<Object>> getSalesByDays() {
        List<Object[]> salesByDay = saleRepository.getSalesByDays();
        List<List<Object>> salesList = new ArrayList<>();
        for (Object[] l : salesByDay) {
            salesList.add(saleMapper.mapArrayToList(l));
        }
        return salesList;
    }

    @Override
    public List<List<Object[]>> getSalesAndSum(LocalDate from, LocalDate to) {
        return saleRepository.getSalesAndSum(from, to);
    }

    @Override
    public List<List<Object[]>> getReturnsAndSum(LocalDate from, LocalDate to) {
        return saleRepository.getReturnsAndSum(from, to);
    }

    @Override
    public List<List<Object[]>> getForPay(LocalDate from, LocalDate to) {
        return saleRepository.getForPay(from, to);
    }

    @Override
    public List<List<Object[]>> getSalesAndSumAndReturnsAndForPayByDate(String date) {
        return saleRepository.getSalesAndSumAndReturnsAndForPayByDate(LocalDate.parse(date));
    }

    /**
     * Автообновление таблицы sales (каждые полчаса)
     */
    @Scheduled(initialDelay = 180000, fixedDelay = 1800000)
    public void salesAutoUpdate() {
        log.info("Выполняется обновление продаж");
        Sale lastSale = saleRepository.getLast();
        LocalDateTime date;
        try {
            date = lastSale.getLastChangeDate();
        } catch (NullPointerException exception) {
            date = LocalDateTime.parse("2022-01-01T00:00:00");
        }

        log.info("Последняя доступная дата {} ", date);

        WebClient client = WebClient.builder()
                .baseUrl("https://statistics-api.wildberries.ru/api/v1/supplier/sales?dateFrom=" + date.plusSeconds(1))
                .defaultHeader("Authorization", token.getToken())
                .defaultHeader("Content-Type", "application/json")
                .build();

        Flux<SaleInputDto> sales = client
                .get()
                .retrieve()
                .bodyToFlux(SaleInputDto.class);

        List<SaleInputDto> salesDtoList = sales.collectList().block();

        if (salesDtoList == null) {
            log.info("Нет новых записей");
        } else {
            for (SaleInputDto dto : salesDtoList) {
                if (checkSaleRec(dto.getOdid(), dto.getSaleID())) { //если запись с таким odid и saleId уже есть, то её необходимо обновить (через удаление)
                    log.info("Обновление записи {}", dto.getOdid());
                    saleRepository.deleteById(
                            saleRepository.findByOdidAndSaleId(dto.getOdid(), dto.getSaleID()).getId()); //удаление записи с существующим odid
                    saleRepository.save(saleMapper.mapInputToSale(dto)); //добавление новой записи
                    updateStatService.addRecord("sales", true, dto.getOdid()); //запись в статистику
                } else {
                    try {
                        saleRepository.save(saleMapper.mapInputToSale(dto)); //добавление новой записи
                        log.info("Добавлена новая запись");
                        updateStatService.addRecord("sales", false, dto.getOdid()); //запись в статистику
                    } catch (DataIntegrityViolationException exception) {
                        itemService.saveNewItem(new Item(dto.getBarcode(), dto.getBrand(), dto.getSupplierArticle(),
                                dto.getTechSize(), dto.getNmId(), "Unidentified item"));
                        log.info("Добавлен новый товар, требуется корректировка");
                        saleRepository.save(saleMapper.mapInputToSale(dto)); //добавление новой записи
                        log.info("Добавлена новая запись");
                        updateStatService.addRecord("sales", false, dto.getOdid()); //запись в статистику
                    }
                }
            }
        }
    }

    /**
     * Проверка существования заказа
     *
     * @param odid   уникальный идентификатор позиции заказа
     * @param saleId Уникальный идентификатор продажи/возврата
     * @return true или false
     */
    public Boolean checkSaleRec(Long odid, String saleId) {
        try {
            Sale sale = saleRepository.findByOdidAndSaleId(odid, saleId);
            return sale.getOdid() == odid && sale.getSaleID().equals(saleId) && !saleId.equals("0");
        } catch (NullPointerException exception) {
            return false;
        }
    }
}

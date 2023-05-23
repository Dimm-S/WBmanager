package com.dimm.wbmanager.reportdetailbyperiod.dto;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDetailByPeriodInputDto {
    private Integer realizationreport_id; //Номер отчёта
    private String date_from;
    private String date_to;
    private String create_dt;
    private String suppliercontract_code; //Договор
    private Long rrd_id; //Номер строки, по идее уникальный, поэтому ключ
    private Long gi_id;  //Номер поставки
    private String subject_name;  //Предмет
    private Long nm_id;  //Артикул
    private String brand_name;  //Бренд
    private String sa_name;  //Артикул поставщика
    private String ts_name;  //Размер
    private String barcode;  //Бар-код
    private String  doc_type_name;  //Тип документа
    private Integer quantity;  //Количество
    private Float retail_price; //Цена розничная
    private Float retail_amount; //Сумма продаж (возвратов)
    private Integer sale_percent;  //Согласованная скидка
    private Float commission_percent; //Процент комиссии
    private String office_name;  //Склад
    private String supplier_oper_name; //Обоснование для оплаты
    private String order_dt;  //Дата заказа. Присылается с явным указанием часового пояса.
    private String sale_dt;  //Дата продажи. Присылается с явным указанием часового пояса.
    private String rr_dt;  //Дата операции. Присылается с явным указанием часового пояса.
    private Long shk_id;  //Штрих-код
    private Float retail_price_withdisc_rub;  //Цена розничная с учетом согласованной скидки
    private Integer delivery_amount;  //Количество доставок
    private Integer return_amount;  //Количество возвратов
    private Float delivery_rub;  //Стоимость логистики
    private String gi_box_type_name;  //Тип коробов
    private Float product_discount_for_report;  //Согласованный продуктовый дисконт
    private Float supplier_promo;  //Промокод
    private Long rid;  //Уникальный идентификатор позиции заказа
    private Float ppvz_spp_prc;  //Скидка постоянного покупателя
    private Float ppvz_kvw_prc_base;  //Размер кВВ без НДС, % базовый
    private Float ppvz_kvw_prc;  //Итоговый кВВ без НДС, %
    private Float sup_rating_prc_up; //Размер снижения кВВ из-за рейтинга, %
    private Float is_kgvp_v2; //Размер снижения кВВ из-за акции, %
    private Float ppvz_sales_commission;  //Вознаграждение с продаж до вычета услуг поверенного, без НДС
    private Float ppvz_for_pay;  //К перечислению продавцу за реализованный товар
    private Float ppvz_reward;  //Возмещение за выдачу и возврат товаров на ПВЗ
    private Float acquiring_fee;  //Возмещение расходов по эквайрингу. Расходы WB на услуги эквайринга: вычитаются из вознаграждения WB и не влияют на доход продавца.
    private String acquiring_bank;  //Наименование банка, предоставляющего услуги эквайринга
    private Float ppvz_vw;  //Вознаграждение WB без НДС
    private Float ppvz_vw_nds;  //НДС с вознаграждения WB
    private Integer ppvz_office_id;  //Номер офиса
    private String ppvz_office_name;  //Наименование офиса доставки
    private Integer ppvz_supplier_id;  //Номер партнера
    private String ppvz_supplier_name;  //Партнер
    private String ppvz_inn;  //ИНН партнера
    private String declaration_number;  //Номер таможенной декларации
    private String bonus_type_name;  //Обоснование штрафов и доплат. Поле будет в ответе, если заполнены(о) поля penalty или additional_payment.
    private String sticker_id;  //Цифровое значение стикера, который клеится на товар в процессе сборки заказа по системе Маркетплейс.
    private String site_country;  //Страна продажи
    private Float penalty;  //Штрафы
    private Float additional_payment;  //Штрафы
    private String srid;
}

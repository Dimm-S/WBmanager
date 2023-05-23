package com.dimm.wbmanager.reportdetailbyperiod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reportdetailbyperiod")
public class ReportDetailByPeriod {
    @Column(name = "realizationreport_id")
    private Integer realizationreportId; //Номер отчёта
    @Column(name = "date_from")
    private LocalDateTime dateFrom;
    @Column(name = "date_to")
    private LocalDateTime dateTo;
    @Column(name = "create_dt")
    private LocalDateTime createDt;
    @Column(name = "suppliercontract_code")
    private String suppliercontractCode; //Договор
    @Column(name = "rrd_id")
    private Long rrdId; //Номер строки, по идее уникальный, поэтому ключ
    @Column(name = "gi_id")
    private Long giId;  //Номер поставки
    @Column(name = "subject_name")
    private String subjectName;  //Предмет
    @Column(name = "nm_id")
    private Long nmId;  //Артикул
    @Column(name = "brand_name")
    private String brandName;  //Бренд
    @Column(name = "sa_name")
    private String saName;  //Артикул поставщика
    @Column(name = "ts_name")
    private String tsName;  //Размер
    @Column
    private String barcode;  //Бар-код
    @Column(name = "doc_type_name")
    private String  docTypeName;  //Тип документа
    @Column
    private Integer quantity;  //Количество
    @Column(name = "retail_price")
    private Float retailPrice; //Цена розничная
    @Column(name = "retail_amount")
    private Float retailAmount; //Сумма продаж (возвратов)
    @Column(name = "sale_percent")
    private Integer salePercent;  //Согласованная скидка
    @Column(name = "commission_percent")
    private Float commissionPercent; //Процент комиссии
    @Column(name = "office_name")
    private String officeName;  //Склад
    @Column(name = "supplier_oper_name")
    private String supplierOperName; //Обоснование для оплаты
    @Column(name = "order_dt")
    private LocalDateTime orderDt;  //Дата заказа. Присылается с явным указанием часового пояса.
    @Column(name = "sale_dt")
    private LocalDateTime saleDt;  //Дата продажи. Присылается с явным указанием часового пояса.
    @Column(name = "rr_dt")
    private LocalDateTime rrDt;  //Дата операции. Присылается с явным указанием часового пояса.
    @Column(name = "shk_id")
    private Long shkId;  //Штрих-код
    @Column(name = "retail_price_withdisc_rub")
    private Float retailPriceWithdiscRub;  //Цена розничная с учетом согласованной скидки
    @Column(name = "delivery_amount")
    private Integer deliveryAmount;  //Количество доставок
    @Column(name = "return_amount")
    private Integer returnAmount;  //Количество возвратов
    @Column(name = "delivery_rub")
    private Float deliveryRub;  //Стоимость логистики
    @Column(name = "gi_box_type_name")
    private String giBoxTypeName;  //Тип коробов
    @Column(name = "product_discount_for_report")
    private Float productDiscountForReport;  //Согласованный продуктовый дисконт
    @Column(name = "supplier_promo")
    private Float supplierPromo;  //Промокод
    @Column
    private Long rid;  //Уникальный идентификатор позиции заказа
    @Column(name = "ppvz_spp_prc")
    private Float ppvzSppPrc;  //Скидка постоянного покупателя
    @Column(name = "ppvz_kvw_prc_base")
    private Float ppvzKvwPrcBase;  //Размер кВВ без НДС, % базовый
    @Column(name = "ppvz_kvw_prc")
    private Float ppvzKvwPrc;  //Итоговый кВВ без НДС, %
    @Column(name = "sup_rating_prc_up")
    private Float supRatingPrcUp; //Размер снижения кВВ из-за рейтинга, %
    @Column(name = "is_kgvp_v2")
    private Float isKgvpV2; //Размер снижения кВВ из-за акции, %
    @Column(name = "ppvz_sales_commission")
    private Float ppvzSalesCommission;  //Вознаграждение с продаж до вычета услуг поверенного, без НДС
    @Column(name = "ppvz_for_pay")
    private Float ppvzForPay;  //К перечислению продавцу за реализованный товар
    @Column(name = "ppvz_reward")
    private Float ppvzReward;  //Возмещение за выдачу и возврат товаров на ПВЗ
    @Column(name = "acquiring_fee")
    private Float acquiringFee;  //Возмещение расходов по эквайрингу. Расходы WB на услуги эквайринга: вычитаются из вознаграждения WB и не влияют на доход продавца.
    @Column(name = "acquiring_bank")
    private String acquiringBank;  //Наименование банка, предоставляющего услуги эквайринга
    @Column(name = "ppvz_vw")
    private Float ppvzVw;  //Вознаграждение WB без НДС
    @Column(name = "ppvz_vw_nds")
    private Float ppvzVwNds;  //НДС с вознаграждения WB
    @Column(name = "ppvz_office_id")
    private Integer ppvzOfficeId;  //Номер офиса
    @Column(name = "ppvz_office_name")
    private String ppvzOfficeName;  //Наименование офиса доставки
    @Column(name = "ppvz_supplier_id")
    private Integer ppvzSupplierId;  //Номер партнера
    @Column(name = "ppvz_supplier_name")
    private String ppvzSupplierName;  //Партнер
    @Column(name = "ppvz_inn")
    private String ppvzInn;  //ИНН партнера
    @Column(name = "declaration_number")
    private String declarationNumber;  //Номер таможенной декларации
    @Column(name = "bonus_type_name")
    private String bonusTypeName;  //Обоснование штрафов и доплат. Поле будет в ответе, если заполнены(о) поля penalty или additional_payment.
    @Column(name = "sticker_id")
    private String stickerId;  //Цифровое значение стикера, который клеится на товар в процессе сборки заказа по системе Маркетплейс.
    @Column(name = "site_country")
    private String siteCountry;  //Страна продажи
    @Column
    private Float penalty;  //Штрафы
    @Column(name = "additional_payment")
    private Float additionalPayment;  //Штрафы
    @Column
    private String srid;  //Уникальный идентификатор заказа, функционально аналогичный odid/rid.
                          // Данный параметр введен в июле''22 и в течение переходного периода может быть заполнен не во всех ответах.
                          // Примечание для работающих по системе Маркетплейс: srid равен rid в ответе на метод GET /api/v2/orders.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}

CREATE TABLE IF NOT EXISTS items
(
    barcode          varchar(30) NOT NULL
        PRIMARY KEY,
    brand            varchar(50),
    supplier_article varchar(75),
    tech_size        varchar(30),
    nm_id            integer     NOT NULL,
    name             varchar(500)
);

ALTER TABLE items
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS reportdetailbyperiod
(
    realizationreport_id        integer,
    date_from                   timestamp,
    date_to                     timestamp,
    create_dt                   timestamp,
    suppliercontract_code       varchar,
    rrd_id                      bigint,
    gi_id                       bigint,
    subject_name                varchar(100),
    nm_id                       bigint,
    brand_name                  varchar(100),
    sa_name                     varchar(100),
    ts_name                     varchar(100),
    barcode                     varchar(100),
    doc_type_name               varchar(100),
    quantity                    integer,
    retail_price                real,
    retail_amount               real,
    sale_percent                integer,
    commission_percent          real,
    office_name                 varchar(100),
    supplier_oper_name          varchar(100),
    order_dt                    timestamp,
    sale_dt                     timestamp,
    rr_dt                       timestamp,
    shk_id                      bigint,
    retail_price_withdisc_rub   real,
    delivery_amount             integer,
    return_amount               integer,
    delivery_rub                real,
    gi_box_type_name            varchar(100),
    product_discount_for_report real,
    supplier_promo              real,
    rid                         bigint,
    ppvz_spp_prc                real,
    ppvz_kvw_prc_base           real,
    ppvz_kvw_prc                real,
    ppvz_sales_commission       real,
    ppvz_for_pay                real,
    ppvz_reward                 real,
    acquiring_fee               real,
    acquiring_bank              varchar(100),
    ppvz_vw                     real,
    ppvz_vw_nds                 real,
    ppvz_office_id              integer,
    ppvz_office_name            varchar(100),
    ppvz_supplier_id            integer,
    ppvz_supplier_name          varchar(100),
    ppvz_inn                    varchar(50),
    declaration_number          varchar(100),
    bonus_type_name             varchar(100),
    sticker_id                  varchar(100),
    site_country                varchar(100),
    penalty                     real,
    additional_payment          real,
    srid                        varchar(100),
    id                          serial
        CONSTRAINT pk_reportdetail
            PRIMARY KEY
);

COMMENT ON COLUMN reportdetailbyperiod.realizationreport_id IS 'Номер отчета';

COMMENT ON COLUMN reportdetailbyperiod.date_from IS 'Дата начала отчетного периода';

COMMENT ON COLUMN reportdetailbyperiod.date_to IS 'Дата конца отчетного периода';

COMMENT ON COLUMN reportdetailbyperiod.create_dt IS 'Дата формирования отчёта';

COMMENT ON COLUMN reportdetailbyperiod.suppliercontract_code IS 'Договор';

COMMENT ON COLUMN reportdetailbyperiod.rrd_id IS 'Номер строки';

COMMENT ON COLUMN reportdetailbyperiod.gi_id IS 'Номер поставки';

COMMENT ON COLUMN reportdetailbyperiod.subject_name IS 'Предмет';

COMMENT ON COLUMN reportdetailbyperiod.nm_id IS 'Артикул';

COMMENT ON COLUMN reportdetailbyperiod.brand_name IS 'Бренд';

COMMENT ON COLUMN reportdetailbyperiod.sa_name IS 'Артикул поставщика';

COMMENT ON COLUMN reportdetailbyperiod.ts_name IS 'Размер';

COMMENT ON COLUMN reportdetailbyperiod.barcode IS 'Бар-код';

COMMENT ON COLUMN reportdetailbyperiod.doc_type_name IS 'Тип документа';

COMMENT ON COLUMN reportdetailbyperiod.quantity IS 'Количество';

COMMENT ON COLUMN reportdetailbyperiod.retail_price IS 'Цена розничная';

COMMENT ON COLUMN reportdetailbyperiod.retail_amount IS 'Сумма продаж (возвратов)';

COMMENT ON COLUMN reportdetailbyperiod.sale_percent IS 'Согласованная скидка';

COMMENT ON COLUMN reportdetailbyperiod.commission_percent IS 'Процент комиссии';

COMMENT ON COLUMN reportdetailbyperiod.office_name IS 'Склад';

COMMENT ON COLUMN reportdetailbyperiod.supplier_oper_name IS 'Обоснование для оплаты';

COMMENT ON COLUMN reportdetailbyperiod.order_dt IS 'Дата заказа. Присылается с явным указанием часового пояса.';

COMMENT ON COLUMN reportdetailbyperiod.sale_dt IS 'Дата продажи. Присылается с явным указанием часового пояса.';

COMMENT ON COLUMN reportdetailbyperiod.rr_dt IS 'Дата операции. Присылается с явным указанием часового пояса.';

COMMENT ON COLUMN reportdetailbyperiod.shk_id IS 'Штрих-код';

COMMENT ON COLUMN reportdetailbyperiod.retail_price_withdisc_rub IS 'Цена розничная с учетом согласованной скидки';

COMMENT ON COLUMN reportdetailbyperiod.delivery_amount IS 'Количество доставок';

COMMENT ON COLUMN reportdetailbyperiod.return_amount IS 'Количество возвратов';

COMMENT ON COLUMN reportdetailbyperiod.delivery_rub IS 'Стоимость логистики';

COMMENT ON COLUMN reportdetailbyperiod.gi_box_type_name IS 'Тип коробов';

COMMENT ON COLUMN reportdetailbyperiod.product_discount_for_report IS 'Согласованный продуктовый дисконт';

COMMENT ON COLUMN reportdetailbyperiod.supplier_promo IS 'Промокод';

COMMENT ON COLUMN reportdetailbyperiod.rid IS 'Уникальный идентификатор позиции заказа';

COMMENT ON COLUMN reportdetailbyperiod.ppvz_spp_prc IS 'Скидка постоянного покупателя';

COMMENT ON COLUMN reportdetailbyperiod.ppvz_kvw_prc_base IS 'Размер кВВ без НДС, % базовый';

COMMENT ON COLUMN reportdetailbyperiod.ppvz_kvw_prc IS 'Итоговый кВВ без НДС, %';

COMMENT ON COLUMN reportdetailbyperiod.ppvz_sales_commission IS 'Вознаграждение с продаж до вычета услуг поверенного, без НДС';

COMMENT ON COLUMN reportdetailbyperiod.ppvz_for_pay IS 'К перечислению продавцу за реализованный товар';

COMMENT ON COLUMN reportdetailbyperiod.ppvz_reward IS 'Возмещение за выдачу и возврат товаров на ПВЗ';

COMMENT ON COLUMN reportdetailbyperiod.acquiring_fee IS 'Возмещение расходов по эквайрингу. Расходы WB на услуги эквайринга: вычитаются из вознаграждения WB и не влияют на доход продавца.';

COMMENT ON COLUMN reportdetailbyperiod.acquiring_bank IS 'Наименование банка, предоставляющего услуги эквайринга';

COMMENT ON COLUMN reportdetailbyperiod.ppvz_vw IS 'Вознаграждение WB без НДС';

COMMENT ON COLUMN reportdetailbyperiod.ppvz_vw_nds IS 'НДС с вознаграждения WB';

COMMENT ON COLUMN reportdetailbyperiod.ppvz_office_id IS 'Номер офиса';

COMMENT ON COLUMN reportdetailbyperiod.ppvz_office_name IS 'Наименование офиса доставки';

COMMENT ON COLUMN reportdetailbyperiod.ppvz_supplier_id IS 'Номер партнера';

COMMENT ON COLUMN reportdetailbyperiod.ppvz_supplier_name IS 'Партнер';

COMMENT ON COLUMN reportdetailbyperiod.ppvz_inn IS 'ИНН партнера';

COMMENT ON COLUMN reportdetailbyperiod.declaration_number IS 'Номер таможенной декларации';

COMMENT ON COLUMN reportdetailbyperiod.bonus_type_name IS 'Обоснование штрафов и доплат. Поле будет в ответе, если заполнены(о) поля penalty или additional_payment.';

COMMENT ON COLUMN reportdetailbyperiod.sticker_id IS 'Цифровое значение стикера, который клеится на товар в процессе сборки заказа по системе Маркетплейс.';

COMMENT ON COLUMN reportdetailbyperiod.site_country IS 'Страна продажи';

COMMENT ON COLUMN reportdetailbyperiod.penalty IS 'Штрафы';

COMMENT ON COLUMN reportdetailbyperiod.additional_payment IS 'Доплаты';

COMMENT ON COLUMN reportdetailbyperiod.srid IS 'Уникальный идентификатор заказа, функционально аналогичный odid/rid. Данный параметр введен в июле''22 и в течение переходного периода может быть заполнен не во всех ответах. Примечание для работающих по системе Маркетплейс: srid равен rid в ответе на метод GET /api/v2/orders.';

ALTER TABLE reportdetailbyperiod
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS incomes
(
    incomeid        integer,
    number          varchar(40),
    date            timestamp,
    lastchangedate  timestamp,
    supplierarticle varchar(75),
    techsize        varchar(30),
    barcode         varchar(30),
    quantity        integer,
    totalprice      real,
    dateclose       timestamp,
    warehousename   varchar(50),
    nmid            integer,
    status          varchar(50),
    id              serial
        CONSTRAINT pk_income
            PRIMARY KEY
);

COMMENT ON COLUMN incomes.incomeid IS 'Номер поставки';

COMMENT ON COLUMN incomes.number IS 'Номер УПД';

COMMENT ON COLUMN incomes.date IS 'Дата поступления. Если часовой пояс не указан, то берется Московское время UTC+3.';

COMMENT ON COLUMN incomes.lastchangedate IS 'Дата и время обновления информации в сервисе. Это поле соответствует параметру dateFrom в запросе. Если часовой пояс не указан, то берется Московское время UTC+3.';

COMMENT ON COLUMN incomes.supplierarticle IS 'Артикул поставщика';

COMMENT ON COLUMN incomes.techsize IS 'Размер';

COMMENT ON COLUMN incomes.barcode IS 'Бар-код';

COMMENT ON COLUMN incomes.quantity IS 'Количество';

COMMENT ON COLUMN incomes.totalprice IS 'Цена из УПД';

COMMENT ON COLUMN incomes.dateclose IS 'Дата принятия (закрытия) в WB. Если часовой пояс не указан, то берется Московское время UTC+3.';

COMMENT ON COLUMN incomes.warehousename IS 'Название склада';

COMMENT ON COLUMN incomes.nmid IS 'Код WB';

COMMENT ON COLUMN incomes.status IS 'Value: "Принято" Текущий статус поставки';

ALTER TABLE incomes
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS sales
(
    id                  serial
        CONSTRAINT pk_sales
            PRIMARY KEY,
    gnumber             varchar(50),
    order_date          timestamp,
    last_change_date    timestamp,
    supplier_article    varchar(75),
    tech_size           varchar(30),
    barcode             varchar(30),
    total_price         real,
    discount_percent    integer,
    is_supply           boolean,
    is_realisation      boolean,
    promo_code_discount real,
    warehouse_name      varchar(50),
    country_name        varchar(200),
    oblast_okrug_name   varchar(200),
    region_name         varchar(200),
    income_id           integer,
    sale_id             varchar(15),
    odid                bigint,
    spp                 real,
    for_pay             real,
    finished_price      real,
    price_with_disc     real,
    nm_id               bigint,
    subject             varchar(50),
    category            varchar(50),
    brand               varchar(50),
    is_storno           integer,
    sticker             varchar,
    srid                varchar
);

COMMENT ON COLUMN sales.supplier_article IS 'Артикул поставщика';

COMMENT ON COLUMN sales.tech_size IS 'Размер';

COMMENT ON COLUMN sales.barcode IS 'Бар-код';

COMMENT ON COLUMN sales.total_price IS 'Цена до согласованной скидки/промо/спп. Для получения цены со скидкой можно воспользоваться формулой priceWithDiscount = totalPrice * (1 - discountPercent/100)';

COMMENT ON COLUMN sales.discount_percent IS 'Согласованный итоговый дисконт';

COMMENT ON COLUMN sales.is_supply IS 'Договор поставки';

COMMENT ON COLUMN sales.is_realisation IS 'Договор реализации';

COMMENT ON COLUMN sales.promo_code_discount IS 'Скидка по промокоду';

COMMENT ON COLUMN sales.warehouse_name IS 'Название склада отгрузки';

COMMENT ON COLUMN sales.country_name IS 'Страна';

COMMENT ON COLUMN sales.oblast_okrug_name IS 'Округ';

COMMENT ON COLUMN sales.region_name IS 'Регион';

COMMENT ON COLUMN sales.income_id IS 'Номер поставки (от продавца на склад)';

COMMENT ON COLUMN sales.sale_id IS 'Уникальный идентификатор продажи/возврата.

SXXXXXXXXXX — продажа
RXXXXXXXXXX — возврат
DXXXXXXXXXXX — доплата
AXXXXXXXXX – сторно продаж (все значения полей как у продажи, но поля с суммами и кол-вом с минусом как в возврате)
BXXXXXXXXX - сторно возврата (все значения полей как у возврата, но поля с суммами и кол-вом с плюсом, в противоположность возврату)';

COMMENT ON COLUMN sales.odid IS 'Уникальный идентификатор позиции заказа. Может использоваться для поиска соответствия между заказами и продажами.';

COMMENT ON COLUMN sales.spp IS 'Согласованная скидка постоянного покупателя';

COMMENT ON COLUMN sales.for_pay IS 'К перечислению поставщику';

COMMENT ON COLUMN sales.finished_price IS 'Фактическая цена заказа с учетом всех скидок';

COMMENT ON COLUMN sales.price_with_disc IS 'Цена, от которой считается вознаграждение поставщика forpay (с учетом всех согласованных скидок)';

COMMENT ON COLUMN sales.nm_id IS 'Код WB';

COMMENT ON COLUMN sales.subject IS 'Предмет';

COMMENT ON COLUMN sales.category IS 'Категория';

COMMENT ON COLUMN sales.brand IS 'Бренд';

COMMENT ON COLUMN sales.is_storno IS 'Для сторно-операций 1, для остальных 0';

COMMENT ON COLUMN sales.sticker IS 'Цифровое значение стикера, который клеится на товар в процессе сборки заказа по системе Маркетплейс.';

COMMENT ON COLUMN sales.srid IS 'Уникальный идентификатор заказа, функционально аналогичный odid/rid. Данный параметр введен в июле''22 и в течение переходного периода может быть заполнен не во всех ответах. Примечание для работающих по системе Маркетплейс: srid равен rid в ответе на метод GET /api/v2/orders.';

ALTER TABLE sales
    OWNER TO postgres;



CREATE TABLE IF NOT EXISTS orders
(
    id               serial
        CONSTRAINT pk_orders
            PRIMARY KEY,
    g_number         varchar(50),
    order_date       timestamp,
    last_change_date timestamp,
    supplier_article varchar(75),
    tech_size        varchar(30),
    barcode          varchar(30),
--         CONSTRAINT fk_items
--             REFERENCES items,
    total_price      real,
    discount_percent integer,
    warehouse_name   varchar(50),
    oblast           varchar(200),
    income_id        bigint,
    odid             bigint,
    nm_id            integer,
    subject          varchar(50),
    category         varchar(50),
    brand            varchar(50),
    is_cancel        boolean,
    cancel_dt        timestamp,
    sticker          varchar,
    srid             varchar
);

COMMENT ON COLUMN orders.order_date IS 'Дата и время заказа. Это поле соответствует параметру dateFrom в запросе, если параметр flag=1. Если часовой пояс не указан, то берется Московское время UTC+3.';

COMMENT ON COLUMN orders.last_change_date IS 'Дата и время обновления информации в сервисе. Это поле соответствует параметру dateFrom в запросе, если параметр flag=0 или не указан. Если часовой пояс не указан, то берется Московское время UTC+3.';

COMMENT ON COLUMN orders.supplier_article IS 'Артикул поставщика';

COMMENT ON COLUMN orders.tech_size IS 'Размер';

COMMENT ON COLUMN orders.barcode IS 'Бар-код';

-- COMMENT ON CONSTRAINT fk_items ON orders IS 'Ссылается на поле barcode таблицы items';

COMMENT ON COLUMN orders.total_price IS 'Цена до согласованной итоговой скидки/промо/спп. Для получения цены со скидкой можно воспользоваться формулой priceWithDiscount = totalPrice * (1 - discountPercent/100)';

COMMENT ON COLUMN orders.discount_percent IS 'Согласованный итоговый дисконт. Будучи примененным к totalPrice, даёт сумму к оплате.';

COMMENT ON COLUMN orders.warehouse_name IS 'Название склада отгрузки';

COMMENT ON COLUMN orders.oblast IS 'Область';

COMMENT ON COLUMN orders.income_id IS 'Номер поставки (от продавца на склад)';

COMMENT ON COLUMN orders.odid IS 'Уникальный идентификатор позиции заказа. Может использоваться для поиска соответствия между заказами и продажами.';

COMMENT ON COLUMN orders.nm_id IS 'Код WB';

COMMENT ON COLUMN orders.subject IS 'Предмет';

COMMENT ON COLUMN orders.category IS 'Категория';

COMMENT ON COLUMN orders.brand IS 'Бренд';

COMMENT ON COLUMN orders.is_cancel IS 'Отмена заказа. true - заказ отменен до оплаты.';

COMMENT ON COLUMN orders.cancel_dt IS 'Дата и время отмены заказа. Если заказ не был отменен, то "0001-01-01T00:00:00". Если часовой пояс не указан, то берется Московское время UTC+3.';

COMMENT ON COLUMN orders.sticker IS 'Цифровое значение стикера, который клеится на товар в процессе сборки заказа по системе Маркетплейс.';

COMMENT ON COLUMN orders.srid IS 'Уникальный идентификатор заказа, функционально аналогичный odid/rid. Данный параметр введен в июле''22 и в течение переходного периода может быть заполнен не во всех ответах. Примечание для работающих по системе Маркетплейс: srid равен rid в ответе на метод GET /api/v2/orders.';

ALTER TABLE orders
    OWNER TO postgres;

CREATE INDEX IF NOT EXISTS fki_fk_items
    ON orders (barcode);

CREATE TABLE IF NOT EXISTS updatestat
(
    id         serial
        CONSTRAINT pk_update_stat
            PRIMARY KEY,
    time       timestamp WITH TIME ZONE NOT NULL,
    table_name varchar(20)              NOT NULL,
    is_update  boolean                  NOT NULL,
    odid       bigint                   NOT NULL
);

COMMENT ON TABLE updatestat IS 'Лог добавления или обновления записей в таблицы orders и sales';

ALTER TABLE updatestat
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS selfbuyouts
(
    srid varchar(100) NOT NULL
        PRIMARY KEY
);

ALTER TABLE selfbuyouts
    OWNER TO postgres;



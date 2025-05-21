DROP DATABASE IF EXISTS paydeuk_server_card;
CREATE DATABASE paydeuk_server_card;
USE paydeuk_server_card;

-- TABLES
CREATE TABLE merchant
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY                                            NOT NULL,
    name       VARCHAR(20)                                                                  NOT NULL,
    category   ENUM ('CULTURE', 'FOOD_BEVERAGE', 'SHOPPING', 'SUBSCRIBE', 'TRANSPORTATION') NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE benefit
(
    id                        BIGINT AUTO_INCREMENT PRIMARY KEY      NOT NULL,
    description               VARCHAR(100),
    title                     VARCHAR(100),
    benefit_type              ENUM ('DISCOUNT', 'POINT', 'CASHBACK') NOT NULL,
    has_additional_conditions BOOLEAN                                NOT NULL,
    merchant_id               BIGINT,
    created_at                TIMESTAMP,
    updated_at                TIMESTAMP,
    FOREIGN KEY (merchant_id) REFERENCES merchant (id)
);

CREATE TABLE users
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name         VARCHAR(10)                       NOT NULL,
    phone_number VARCHAR(20)                       NOT NULL,
    birth_date   VARCHAR(20)                       NOT NULL,
    created_at   TIMESTAMP,
    updated_at   TIMESTAMP
);

CREATE TABLE paydeuk_registered_card
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    card_token VARCHAR(30)                       NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE card
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY                          NOT NULL,
    name       VARCHAR(30)                                                NOT NULL,
    type       ENUM ('CREDIT', 'DEBIT')                                   NOT NULL,
    image_url  VARCHAR(255)                                               NOT NULL,
    annual_fee BIGINT                                                     NOT NULL,
    company    ENUM ('KOOKMIN', 'HYUNDAI', 'WOORI', 'SHINHAN', 'SAMSUNG') NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE issued_card
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    user_id          BIGINT                            NOT NULL,
    card_id          BIGINT                            NOT NULL,
    card_number      VARCHAR(20)                       NOT NULL,
    cvc              VARCHAR(3)                        NOT NULL,
    expiration_year  VARCHAR(4)                        NOT NULL,
    expiration_month VARCHAR(2)                        NOT NULL,
    card_password    VARCHAR(60)                       NOT NULL,
    created_at       TIMESTAMP,
    updated_at       TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (card_id) REFERENCES card (id)
);

CREATE TABLE issued_card_token
(
    id                         BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    issued_card_id             BIGINT                            NOT NULL,
    payment_service            ENUM ('PAYDEUK')                  NOT NULL,
    paydeuk_registered_card_id BIGINT                            NOT NULL,
    created_at                 TIMESTAMP                         NOT NULL,
    updated_at                 TIMESTAMP                         NOT NULL,
    FOREIGN KEY (issued_card_id) REFERENCES issued_card (id),
    FOREIGN KEY (paydeuk_registered_card_id) REFERENCES paydeuk_registered_card (id)
);

CREATE TABLE spending_range
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    min_spending BIGINT,
    max_spending BIGINT
);

CREATE TABLE benefit_condition
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY                                                                                            NOT NULL,
    benefit_id         BIGINT                                                                                                                       NOT NULL,
    spending_range_id  BIGINT,
    value              BIGINT                                                                                                                       NOT NULL,
    condition_category ENUM ('PER_TRANSACTION_LIMIT', 'DAILY_LIMIT_COUNT', 'MONTHLY_LIMIT_COUNT', 'DAILY_DISCOUNT_LIMIT', 'MONTHLY_DISCOUNT_LIMIT') NOT NULL,
    created_at         TIMESTAMP,
    updated_at         TIMESTAMP,
    FOREIGN KEY (benefit_id) REFERENCES benefit (id),
    FOREIGN KEY (spending_range_id) REFERENCES spending_range (id)
);

CREATE TABLE discount_rate
(
    id                               BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    benefit_id                       BIGINT                            NOT NULL,
    previous_month_spending_range_id BIGINT                            NOT NULL,
    category                         ENUM ('RATE', 'AMOUNT')           NOT NULL,
    discount_amount                  FLOAT                             NOT NULL,
    created_at                       TIMESTAMP,
    updated_at                       TIMESTAMP,
    FOREIGN KEY (benefit_id) REFERENCES benefit (id),
    FOREIGN KEY (previous_month_spending_range_id) REFERENCES spending_range (id)
);

CREATE TABLE card_benefit
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    card_id    BIGINT                            NOT NULL,
    benefit_id BIGINT                            NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (card_id) REFERENCES card (id),
    FOREIGN KEY (benefit_id) REFERENCES benefit (id)
);

CREATE TABLE benefit_usage_count
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    issued_card_id BIGINT                            NOT NULL,
    condition_id   BIGINT                            NOT NULL,
    value          INT,
    created_at     TIMESTAMP,
    updated_at     TIMESTAMP,
    FOREIGN KEY (issued_card_id) REFERENCES issued_card (id),
    FOREIGN KEY (condition_id) REFERENCES benefit_condition (id)
);

CREATE TABLE previous_month_spending
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    issued_card_id BIGINT                            NOT NULL,
    value          INT                               NOT NULL,
    created_at     TIMESTAMP,
    updated_at     TIMESTAMP,
    FOREIGN KEY (issued_card_id) REFERENCES issued_card (id)
);

-- Foreign Key Relations (Additional)
-- Merchant -> Benefit
-- Benefit -> Card_Benefit
-- Benefit -> Condition
-- Discount_Rate -> Benefit
-- Condition -> Previous_Month_Spending_Range
-- Card_Benefit -> Card
-- Card_Benefit -> Benefit
-- Issued_Card -> Customer
-- Issued_Card -> Card
-- Issued_Card_Token -> Issued_Card
-- Issued_Card_Token -> Paydeuk_Registered_Card
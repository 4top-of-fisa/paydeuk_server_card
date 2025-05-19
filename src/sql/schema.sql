DROP DATABASE IF EXISTS paydeuk_server_card;
CREATE DATABASE paydeuk_server_card;
USE paydeuk_server_card;



-- Tables

CREATE TABLE merchant (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(20),
                          category ENUM ('CULTURE', 'FOOD_BEVERAGE', 'SHOPPING', 'SUBSCRIBE', 'TRANSPORTATION'),
                          created_at TIMESTAMP,
                          updated_at TIMESTAMP
);
CREATE TABLE benefit (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         benefit_name VARCHAR(100),
                         benefit_type ENUM('DISCOUNT', 'POINTS', 'CASHBACK'),
                         merchant_id BIGINT,
                         has_additional_conditions BOOLEAN,
                         created_at TIMESTAMP,
                         updated_at TIMESTAMP,
                         FOREIGN KEY (merchant_id) REFERENCES merchant(id)
);

CREATE TABLE customer (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(10),
                          phone_number VARCHAR(20),
                          birthdate VARCHAR(20),
                          created_at TIMESTAMP,
                          updated_at TIMESTAMP
);

CREATE TABLE paydeuk_registered_card (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         card_token VARCHAR(100),
                                         created_at TIMESTAMP,
                                         updated_at TIMESTAMP
);
CREATE TABLE card (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(30),
                      type ENUM ('CREDIT', 'DEBIT'),
                      image_url VARCHAR(255),
                      annual_fee BIGINT,
                      card_company ENUM('KB', 'HANA', 'WOORI', 'SHINHAN', 'SAMSUNG'),
                      created_at        TIMESTAMP,
                      updated_at        TIMESTAMP
);

CREATE TABLE issued_card (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             user_id BIGINT,
                             card_id BIGINT,
                             card_number VARCHAR(20),
                             cvc VARCHAR(3),
                             expiration_year VARCHAR(2),
                             expiration_month VARCHAR(2),
                             card_password INT,
                             created_at        TIMESTAMP,
                             updated_at        TIMESTAMP,
                             FOREIGN KEY (user_id) REFERENCES customer(id),
                             FOREIGN KEY (card_id) REFERENCES card(id)
);
CREATE TABLE issued_card_token (
                                   id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   issued_card_id BIGINT,
                                   payment_service ENUM ('PAYDEUK'),
                                   paydeuk_registered_card_id BIGINT,
                                   created_at        TIMESTAMP,
                                   updated_at        TIMESTAMP,
                                   FOREIGN KEY (issued_card_id) REFERENCES issued_card(id),
                                   FOREIGN KEY (paydeuk_registered_card_id) REFERENCES paydeuk_registered_card(id)
);



CREATE TABLE previous_month_spending_range (
                                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                               min_spending BIGINT,
                                               max_spending BIGINT,
                                               created_at        TIMESTAMP,
                                               updated_at        TIMESTAMP
);

CREATE TABLE benefit_condition (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           benefit_id BIGINT,
                           condition_category ENUM('PER_TRANSACTION_LIMIT', 'DAILY_LIMIT_COUNT', 'MONTHLY_LIMIT_COUNT', 'DAILY_DISCOUNT_LIMIT', 'MONTHLY_DISCOUNT_LIMIT'),
                           previous_month_spending_range_id BIGINT,
                           value_type ENUM('COUNT', 'AMOUNT'),
                           value BIGINT,
                           created_at        TIMESTAMP,
                           updated_at        TIMESTAMP,
                           FOREIGN KEY (benefit_id) REFERENCES benefit(id),
                           FOREIGN KEY (previous_month_spending_range_id) REFERENCES previous_month_spending_range(id)
);

CREATE TABLE discount_rate (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               benefit_id BIGINT,
                               previous_month_spending_range_id BIGINT,
                               discount_apply_type ENUM('RATE', 'AMOUNT'),
                               discount_amount BIGINT,
                               created_at        TIMESTAMP,
                               updated_at        TIMESTAMP,
                               FOREIGN KEY (benefit_id) REFERENCES benefit(id),
                               FOREIGN KEY (previous_month_spending_range_id) REFERENCES previous_month_spending_range(id)
);



CREATE TABLE card_benefit (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              card_id BIGINT,
                              benefit_id BIGINT,
                              created_at        TIMESTAMP,
                              updated_at        TIMESTAMP,
                              FOREIGN KEY (card_id) REFERENCES card(id),
                              FOREIGN KEY (benefit_id) REFERENCES benefit(id)
);

CREATE TABLE benefit_usage_count (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     issued_card_id BIGINT,
                                     condition_id BIGINT,
                                     value INT,
                                     created_at        TIMESTAMP,
                                     updated_at        TIMESTAMP,
                                     FOREIGN KEY (issued_card_id) REFERENCES issued_card(id),
                                     FOREIGN KEY (condition_id) REFERENCES benefit_condition(id)
);

CREATE TABLE previous_month_spending (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         issued_card_id BIGINT,
                                         value INT,
                                         created_at        TIMESTAMP,
                                         updated_at        TIMESTAMP,
                                         FOREIGN KEY (issued_card_id) REFERENCES issued_card(id)
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
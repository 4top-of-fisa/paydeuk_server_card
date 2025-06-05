-- MERCHANT TABLE (가맹점)
INSERT INTO merchant (name, category, created_at, updated_at)
VALUES ('스타벅스', 'FOOD_BEVERAGE', NOW(), NOW()),  -- 1
       ('마켓컬리', 'FOOD_BEVERAGE', NOW(), NOW()),  -- 2
       ('배달의민족', 'FOOD_BEVERAGE', NOW(), NOW()), -- 3
       ('넷플릭스', 'SUBSCRIBE', NOW(), NOW()),      -- 4
       ('멜론', 'SUBSCRIBE', NOW(), NOW()),        -- 5
       ('네이버플러스멤버십', 'SUBSCRIBE', NOW(), NOW()), -- 6
       ('쿠팡', 'SHOPPING', NOW(), NOW()),         -- 7
       ('무신사', 'SHOPPING', NOW(), NOW()),        -- 8
       ('이마트몰', 'SHOPPING', NOW(), NOW()),       -- 9
       ('오늘의집', 'SHOPPING', NOW(), NOW()),       -- 10
       ('CGV', 'CULTURE', NOW(), NOW()),         -- 11
       ('교보문고', 'CULTURE', NOW(), NOW()),        -- 12
       ('롯데월드', 'CULTURE', NOW(), NOW()),        -- 13
       ('KTX', 'TRANSPORTATION', NOW(), NOW()),  -- 14
       ('고속버스', 'TRANSPORTATION', NOW(), NOW()), -- 15
       ('항공', 'TRANSPORTATION', NOW(), NOW());
-- 16

-- USERS TABLE (고객)
INSERT INTO users (name, phone_number, birth_date, created_at, updated_at)
VALUES ('일반유저', '010-1234-5678', '1990.01.01', NOW(), NOW()), -- 만료
       ('김성준', '010-7683-0198', '1998.10.22', NOW(), NOW()),
       ('이원빈', '010-3339-9037', '2000.05.12', NOW(), NOW()),
       ('임지섭', '010-3164-6358', '1999.09.01', NOW(), NOW()),
       ('차승훈', '010-9190-5047', '1999.02.13', NOW(), NOW()),
       ('황혜영', '010-9711-3531', '1999.10.04', NOW(), NOW());

-- CARD TABLE (카드)
INSERT INTO card (name, type, image_url, annual_fee, company, created_at, updated_at)
VALUES ('현대카드 M', 'CREDIT', 'https://paydeuk-s3-bucket.s3.ap-northeast-2.amazonaws.com/hyundaiM.png', 30000, 'HYUNDAI',
        NOW(), NOW()),
       ('신한카드 Mr.Life', 'CREDIT', 'https://paydeuk-s3-bucket.s3.ap-northeast-2.amazonaws.com/shinhanMrLife.png', 15000,
        'SHINHAN', NOW(), NOW()),
       ('KB국민 다담카드', 'CREDIT', 'https://paydeuk-s3-bucket.s3.ap-northeast-2.amazonaws.com/kbDadam.png', 15000,
        'KOOKMIN', NOW(), NOW()),
       ('삼성카드 taptap O', 'CREDIT', 'https://paydeuk-s3-bucket.s3.ap-northeast-2.amazonaws.com/samsungTaptap.png', 10000,
        'SAMSUNG', NOW(), NOW()),
       ('DA카드의정석 II', 'CREDIT', 'https://paydeuk-s3-bucket.s3.ap-northeast-2.amazonaws.com/da2.png', 15000, 'WOORI',
        NOW(), NOW());


-- PAYDUEK_REGISTERED_CARD TABLE (페이득 등록 카드)
INSERT INTO paydeuk_registered_card (card_token, created_at, updated_at)
VALUES ('hyundai_m_test', NOW(), NOW()),
       ('hyundai_m_sungjun', NOW(), NOW()),
       ('shinhan_mr_life_sungjun', NOW(), NOW()),
       ('hyundai_m_wonbin', NOW(), NOW()),
       ('shinhan_mr_life_wonbin', NOW(), NOW()),
       ('hyundai_m_jisub', NOW(), NOW()),
       ('shinhan_mr_life_jisub', NOW(), NOW()),
       ('hyundai_m_seunghoon', NOW(), NOW()),
       ('shinhan_mr_life_seunghoon', NOW(), NOW()),
       ('hyundai_m_hyeyeong', NOW(), NOW()),
       ('shinhan_mr_life_hyeyeong', NOW(), NOW()),
       ('mock_token', NOW(), NOW()),
       ('mock_token2', NOW(), NOW()),
       ('mock_token3', NOW(), NOW());

-- ISSUED_CARD TABLE (발급 카드)
INSERT INTO issued_card(user_id, card_id, card_number, cvc, expiration_year, expiration_month, card_password,
                        created_at, updated_at)
VALUES (1, 1, '1111222233334440', '123', '2026', '12', '00', NOW(), NOW()), -- 테스트 현대카드 M (유효기간 만료 테스트)
       (2, 1, '1111222233334441', '123', '2026', '12', '00', NOW(), NOW()), -- 성준
       (2, 2, '1111222233334442', '123', '2026', '12', '00', NOW(), NOW()), -- 성준
       (3, 1, '1111222233334443', '123', '2026', '12', '00', NOW(), NOW()), -- 원빈
       (3, 2, '1111222233334444', '123', '2026', '12', '00', NOW(), NOW()), -- 원빈
       (4, 1, '1111222233334445', '123', '2026', '12', '00', NOW(), NOW()), -- 지섭
       (4, 2, '1111222233334446', '123', '2026', '12', '00', NOW(), NOW()), -- 지섭
       (5, 1, '1111222233334447', '123', '2026', '12', '00', NOW(), NOW()), -- 승훈
       (5, 2, '1111222233334448', '123', '2026', '12', '00', NOW(), NOW()), -- 승훈
       (6, 1, '1111222233334449', '123', '2026', '12', '00', NOW(), NOW()), -- 혜영
       (6, 2, '1111222233334410', '123', '2026', '12', '00', NOW(), NOW()), -- 혜영
       (6, 3, '1111222233334411', '123', '2026', '12', '00', NOW(), NOW()),
       (6, 4, '1111222233334412', '123', '2026', '12', '00', NOW(), NOW()),
       (6, 5, '1111222233334413', '123', '2026', '12', '00', NOW(), NOW());

-- ISSUED_CARD_TOKEN TABLE (발급 카드 토큰)
INSERT INTO issued_card_token (issued_card_id, payment_service, paydeuk_registered_card_id, created_at, updated_at)
VALUES (1, 'PAYDEUK', 1, NOW(), NOW()),   -- 현대카드 M of 테스트
       (2, 'PAYDEUK', 2, NOW(), NOW()),   -- 현대카드 M of 성준
       (3, 'PAYDEUK', 3, NOW(), NOW()),   -- 신한카드 Mr.Life of 성준
       (4, 'PAYDEUK', 4, NOW(), NOW()),   -- 현대카드 M of 원빈
       (5, 'PAYDEUK', 5, NOW(), NOW()),   -- 신한카드 Mr.Life of 원빈
       (6, 'PAYDEUK', 6, NOW(), NOW()),   -- 현대카드 M of 지섭
       (7, 'PAYDEUK', 7, NOW(), NOW()),   -- 신한카드 Mr.Life of 지섭
       (8, 'PAYDEUK', 8, NOW(), NOW()),   -- 현대카드 M of 승훈
       (9, 'PAYDEUK', 9, NOW(), NOW()),   -- 신한카드 Mr.Life of 승훈
       (10, 'PAYDEUK', 10, NOW(), NOW()), -- 현대카드 M of 혜영
       (11, 'PAYDEUK', 11, NOW(), NOW()), -- 신한카드 Mr.Life of 혜영
       (12, 'PAYDEUK', 12, NOW(), NOW()),
       (13, 'PAYDEUK', 13, NOW(), NOW()),
       (14, 'PAYDEUK', 14, NOW(), NOW());


-- SPENDING_RANGE TABLE (실적 범위)
INSERT INTO spending_range (min_spending, max_spending)
VALUES (500000, NULL),
       (1000000, NULL),
       (300000, 500000),
       (500000, 1000000),
       (300000, NULL),

       (300000, 499999),
       (500000, 999999),
       (1000000, NULL),
       (400000, null);

-- BENEFIT TABLE (혜택)
INSERT INTO benefit (title, description, benefit_type, has_additional_conditions, merchant_id, created_at, updated_at)
VALUES ('기본혜택', '국내외 가맹점 1.5% M포인트 적립', 'POINT', 1, NULL, NOW(), NOW()),           -- 현대카드 M / 전 가맹점 (1)
       ('추가혜택', '컬리 5% M포인트 적립', 'POINT', 1, 2, NOW(), NOW()),                     -- 현대카드 M / 컬리 (2)
       ('추가혜택', '쿠팡 5% M포인트 적립', 'POINT', 1, 7, NOW(), NOW()),                     -- 현대카드 M / 쿠팡 (3)
       ('추가혜택', '이마트 5% M포인트 적립', 'POINT', 1, 9, NOW(), NOW()),                    -- 현대카드 M / 이마트 (4)
       ('TIME 할인 서비스 ', '스타벅스 10% 할인', 'DISCOUNT', 1, 1, NOW(), NOW()),            -- 신한카드 Mr.Life / 스타벅스 (5)

       ('TIME 할인 서비스 ', '쿠팡 10% 할인', 'DISCOUNT', 1, 7, NOW(), NOW()),              -- 신한카드 Mr.Life / 쿠팡 (6)
       ('TIME 할인 서비스 ', '이마트 10% 할인', 'DISCOUNT', 1, 9, NOW(), NOW()),             -- 신한카드 Mr.Life / 이마트 (7)
       ('온라인 쇼핑 7% 할인', '쿠팡에서 7% 할인', 'DISCOUNT', 1, 7, NOW(), NOW()),             -- id=8 card_id = 3 (KB국민 다담카드)
       ('교통 5% 할인', 'KTX 및 고속버스 5% 할인', 'DISCOUNT', 1, 14, NOW(), NOW()),          -- id=9 card_id = 3 (KB국민 다담카드)
       ('문화생활 10% 적립', 'CGV, 롯데월드 이용 시 10% 포인트 적립', 'POINT', 1, 11, NOW(), NOW()), -- id=10 card_id = 4 (삼성카드 taptap O)
       ('배달앱 3% 할인', '배달의민족 결제 시 3% 즉시 할인', 'DISCOUNT', 1, 3, NOW(), NOW());
-- id=11 card_id = 5 (DA카드의정석 II)


-- BENEFIT_CONDITION TABLE
INSERT INTO benefit_condition (benefit_id, spending_range_id, value, condition_category, created_at, updated_at)
VALUES (2, NULL, 10000, 'MONTHLY_DISCOUNT_LIMIT', NOW(), NOW()), -- 현대카드 M 추가혜택 컬리
       (3, NULL, 10000, 'MONTHLY_DISCOUNT_LIMIT', NOW(), NOW()), -- 현대카드 M 추가혜택 쿠팡
       (4, NULL, 10000, 'MONTHLY_DISCOUNT_LIMIT', NOW(), NOW()), -- 현대카드 M 추가혜택 이마트
       (5, NULL, 1000, 'PER_TRANSACTION_LIMIT', NOW(), NOW()),   -- 신한카드 Mr.Life TIME 할인 스타벅스
       (5, NULL, 1, 'DAILY_LIMIT_COUNT', NOW(), NOW()),          -- 신한카드 Mr.Life TIME 할인 스타벅스
       (5, NULL, 10, 'MONTHLY_LIMIT_COUNT', NOW(), NOW()),       -- 신한카드 Mr.Life TIME 할인 스타벅스
       (5, 3, 10000, 'MONTHLY_DISCOUNT_LIMIT', NOW(), NOW()),    -- 신한카드 Mr.Life TIME 할인 스타벅스
       (5, 4, 20000, 'MONTHLY_DISCOUNT_LIMIT', NOW(), NOW()),    -- 신한카드 Mr.Life TIME 할인 스타벅스
       (5, 2, 30000, 'MONTHLY_DISCOUNT_LIMIT', NOW(), NOW()),    -- 신한카드 Mr.Life TIME 할인 스타벅스
       (6, NULL, 1000, 'PER_TRANSACTION_LIMIT', NOW(), NOW()),   -- 신한카드 Mr.Life TIME 할인 쿠팡

       (6, NULL, 1, 'DAILY_LIMIT_COUNT', NOW(), NOW()),          -- 신한카드 Mr.Life TIME 할인 쿠팡
       (6, NULL, 10, 'MONTHLY_LIMIT_COUNT', NOW(), NOW()),       -- 신한카드 Mr.Life TIME 할인 쿠팡
       (6, 3, 10000, 'MONTHLY_DISCOUNT_LIMIT', NOW(), NOW()),    -- 신한카드 Mr.Life TIME 할인 쿠팡
       (6, 4, 20000, 'MONTHLY_DISCOUNT_LIMIT', NOW(), NOW()),    -- 신한카드 Mr.Life TIME 할인 쿠팡
       (6, 2, 30000, 'MONTHLY_DISCOUNT_LIMIT', NOW(), NOW()),    -- 신한카드 Mr.Life TIME 할인 쿠팡
       (7, NULL, 5000, 'PER_TRANSACTION_LIMIT', NOW(), NOW()),   -- 신한카드 Mr.Life TIME 할인 이마트
       (7, NULL, 1, 'DAILY_LIMIT_COUNT', NOW(), NOW()),          -- 신한카드 Mr.Life TIME 할인 이마트
       (7, 3, 3000, 'MONTHLY_DISCOUNT_LIMIT', NOW(), NOW()),     -- 신한카드 Mr.Life TIME 할인 이마트
       (7, 4, 5000, 'MONTHLY_DISCOUNT_LIMIT', NOW(), NOW()),     -- 신한카드 Mr.Life TIME 할인 이마트
       (7, 2, 10000, 'MONTHLY_DISCOUNT_LIMIT', NOW(), NOW()),    -- 신한카드 Mr.Life TIME 할인 이마트

       (8, NULL, 3000, 'PER_TRANSACTION_LIMIT', NOW(), NOW()),   -- KB국민 다담카드 쿠팡
       (8, NULL, 2, 'DAILY_LIMIT_COUNT', NOW(), NOW()),          -- KB국민 다담카드 쿠팡
       (8, NULL, 10, 'MONTHLY_LIMIT_COUNT', NOW(), NOW()),       -- KB국민 다담카드 쿠팡
       (8, 7, 10000, 'MONTHLY_DISCOUNT_LIMIT', NOW(), NOW()),    -- KB국민 다담카드 쿠팡
       (8, 8, 20000, 'MONTHLY_DISCOUNT_LIMIT', NOW(), NOW()),    -- KB국민 다담카드 쿠팡
       (8, 9, 30000, 'MONTHLY_DISCOUNT_LIMIT', NOW(), NOW()),    -- KB국민 다담카드 쿠팡
       (9, NULL, 2000, 'PER_TRANSACTION_LIMIT', NOW(), NOW()),-- 월 최대 15회
       (9, NULL, 15, 'MONTHLY_LIMIT_COUNT', NOW(), NOW()),-- 실적에 따른 월 할인 한도
       (9, 7, 7000, 'MONTHLY_DISCOUNT_LIMIT', NOW(), NOW()),
       (9, 8, 10000, 'MONTHLY_DISCOUNT_LIMIT', NOW(), NOW()),

       (9, 9, 15000, 'MONTHLY_DISCOUNT_LIMIT', NOW(), NOW()),    -- 단건 최대 할인: 1500원
       (10, NULL, 2000, 'PER_TRANSACTION_LIMIT', NOW(), NOW()),
       (10, 6, 1, 'DAILY_LIMIT_COUNT', NOW(), NOW()),-- 하루 1회까지만 적립
       (10, 7, 2, 'DAILY_LIMIT_COUNT', NOW(), NOW()),
       (10, 8, 3, 'DAILY_LIMIT_COUNT', NOW(), NOW()),
       (10, null, 5, 'MONTHLY_LIMIT_COUNT', NOW(), NOW()),
       (10, 7, 10000, 'MONTHLY_DISCOUNT_LIMIT', NOW(), NOW()),
       (10, 8, 15000, 'MONTHLY_DISCOUNT_LIMIT', NOW(), NOW()),
       (10, 9, 20000, 'MONTHLY_DISCOUNT_LIMIT', NOW(), NOW()),
       (11, NULL, 1500, 'PER_TRANSACTION_LIMIT', NOW(), NOW()),-- 하루 1회

       (11, NULL, 1, 'DAILY_LIMIT_COUNT', NOW(), NOW()),-- 실적에 따른 월 할인 한도
       (11, 6, 3000, 'MONTHLY_DISCOUNT_LIMIT', NOW(), NOW()),
       (11, 7, 5000, 'MONTHLY_DISCOUNT_LIMIT', NOW(), NOW()),
       (11, 8, 8000, 'MONTHLY_DISCOUNT_LIMIT', NOW(), NOW()),
       (11, 9, 12000, 'MONTHLY_DISCOUNT_LIMIT', NOW(), NOW());


-- DISCOUNT_RATE TABLE (할인율)
INSERT INTO discount_rate (benefit_id, spending_range_id, apply_type, amount)
VALUES (1, 1, 'RATE', 1.5),   -- 현대카드 M 기본혜택 전가맹점
       (2, 2, 'RATE', 5),     -- 현대카드 M 추가혜택 컬리
       (3, 2, 'RATE', 5),     -- 현대카드 M 추가혜택 쿠팡
       (4, 2, 'RATE', 5),     -- 현대카드 M 추가혜택 이마트
       (5, 5, 'RATE', 10),    -- 신한카드 Mr.Life TIME 할인 스타벅스
       (6, 5, 'RATE', 10),    -- 신한카드 Mr.Life TIME 할인 쿠팡
       (7, 5, 'RATE', 10),    -- 신한카드 Mr.Life TIME 할인 이마트
       (8, 2, 'RATE', 7.0),   -- benefit_id 8 (쿠팡 7% 할인, 다담카드)
       (9, 3, 'RATE', 5.0),   -- benefit_id 9 (교통 5% 할인, 다담카드)
       (10, 5, 'RATE', 10.0), -- benefit_id 10 (문화생활 10% 적립, 삼성카드)
       (11, 2, 'RATE', 3.0);
-- benefit_id 11 (배달앱 3% 할인, 우리카드)


-- CARD_BENEFIT TABLE
INSERT INTO card_benefit (card_id, benefit_id, created_at, updated_at)
VALUES (1, 1, NOW(), NOW()),  -- 현대카드 M - 기본 혜택
       (1, 2, NOW(), NOW()),  -- 현대카드 M - 추가혜택 (컬리)
       (1, 3, NOW(), NOW()),  -- 현대카드 M - 추가혜택 (쿠팡)
       (1, 4, NOW(), NOW()),  -- 현대카드 M - 추가혜택 (이마트)
       (2, 5, NOW(), NOW()),  -- 신한카드 Mr.Life - TIME 할인 (스타벅스)
       (2, 6, NOW(), NOW()),  -- 신한카드 Mr.Life - TIME 할인 (쿠팡)
       (2, 7, NOW(), NOW()),  -- 신한카드 Mr.Life - TIME 할인 (이마트)
       (3, 8, NOW(), NOW()),  -- KB국민 다담카드 - 쿠팡 할인
       (3, 9, NOW(), NOW()),  -- KB국민 다담카드 - 교통 할인
       (4, 10, NOW(), NOW()), -- 삼성카드 taptap O - 문화생활 적립
       (5, 11, NOW(), NOW());
-- DA카드의정석 II - 배달 할인


-- BENEFIT_USAGE_COUNT TABLE (카드별 혜택 사용 횟수)
INSERT INTO benefit_usage_count (issued_card_id, condition_id, value, created_at, updated_at)
VALUES (1, 1, 2, NOW(), NOW());
-- 테스트 / 현대카드 M / 일일 사용 횟수

INSERT INTO benefit_usage_count (issued_card_id, condition_id, value, created_at, updated_at)
VALUES (3, 17, 2, NOW(), NOW()),     -- 예: 일 2회 사용
       (3, 19, 4000, NOW(), NOW()),  -- 예: 월 할인 4000원 사용
       -- mock_token (issued_card_id = 12), 쿠팡 할인 (benefit_id = 8)
       (12, 31, 1, NOW(), NOW()),    -- DAILY_LIMIT_COUNT
       (12, 32, 2, NOW(), NOW()),    -- MONTHLY_LIMIT_COUNT
       (12, 33, 7000, NOW(), NOW()), -- MONTHLY_DISCOUNT_LIMIT (spending_range_id = 7)

-- mock_token2 (issued_card_id = 13), 교통 할인 (benefit_id = 9)
       (13, 36, 5, NOW(), NOW()),    -- MONTHLY_LIMIT_COUNT
       (13, 37, 8000, NOW(), NOW()), -- MONTHLY_DISCOUNT_LIMIT (spending_range_id = 8)

-- mock_token3 (issued_card_id = 14), 문화 적립 & 배달 할인 (benefit_id = 10, 11)
       (14, 40, 1, NOW(), NOW()),    -- DAILY_LIMIT_COUNT (문화 적립)
       (14, 41, 4000, NOW(), NOW()), -- MONTHLY_DISCOUNT_LIMIT (spending_range_id = 7, 문화 적립)
       (14, 44, 1, NOW(), NOW()),    -- PER_TRANSACTION_LIMIT (배달 할인)
       (14, 45, 2500, NOW(), NOW());
-- MONTHLY_DISCOUNT_LIMIT (spending_range_id = 7, 배달 할인)


-- PREVIOUS_MONTH_SPENDING TABLE (카드별 전월 실적)
INSERT INTO previous_month_spending (issued_card_id, value, created_at, updated_at)
VALUES (1, 120000, NOW(), NOW()),  -- 현대카드 M of 테스트
       (12, 500000, NOW(), NOW()), -- mock_token (spending_range_id = 7)
       (13, 700000, NOW(), NOW()), -- mock_token2 (spending_range_id = 8)
       (14, 500000, NOW(), NOW()), -- mock_token3 (spending_range_id = 7)
       (2, 1000000, NOW(), NOW()); -- mock_token3 (spending_range_id = 7)
-- admin123
-- 관리자 + 트레이너
INSERT INTO app_user (email, password, name, role) VALUES
                                                       ('admin@gym.com', '$2a$10$tN6Iw/rHxPvSgZ0glXW94OWMcDptwnkPUwa7LAc1yOD3lvoUdBKMC', '관리자', 'ADMIN'),
                                                       ('trainer1@gym.com', 'trainer123', '트레이너 김', 'TRAINER'),
                                                       ('trainer2@gym.com', 'trainer123', '트레이너 이', 'TRAINER');

INSERT INTO trainer (trainerId, birthDate, gender, phone, payPerSession, careerYears,baseSalary) VALUES
                                                                                          (2, DATE '1990-03-15', '남성', '010-1111-2222', 30000, 5,200000),
                                                                                          (3, DATE '1988-07-22', '여성', '010-3333-4444', 35000, 7, 200000);

-- 회원
INSERT INTO member (name, phone, birthDate, gender, status, memo) VALUES
                                                                      ('홍길동', '010-5555-6666', DATE '1995-01-20', '여성', 'INACTIVE', ''),
                                                                      ('김영희', '010-7777-8888', DATE '1987-05-10', '남성', 'ACTIVE', ''),
                                                                      ('고 웅', '010-3124-8888', DATE '1987-05-10', '남성', 'INACTIVE', ''),
                                                                      ('이동재', '010-2352-8888', DATE '1987-05-10', '남성', 'ACTIVE', ''),
                                                                      ('안태형', '010-6434-8888', DATE '1987-05-10', '여성', 'ACTIVE', ''),
                                                                      ('최준혁', '010-4322-8888', DATE '1987-05-10', '여성', 'ACTIVE', ''),
                                                                      ('박철수', '010-9999-0000', DATE '1992-09-30', '여성', 'ACTIVE', ''),
                                                                      ('임정섭', '010-4322-8878', DATE '1987-05-10', '남성', 'ACTIVE', ''),
                                                                      ('손민석', '010-9999-0454', DATE '1992-09-30', '남성', 'ACTIVE', '');

-- 상품
INSERT INTO product (productId,name, type, duration, count, price, description, isActive) VALUES
                                                                                    (1,'1개월 회원권', 'MEMBERSHIP', 30, NULL, 100000, '1개월 기간제 회원권', TRUE),
                                                                                    (2,'6개월 회원권', 'MEMBERSHIP', 180, NULL, 500000, '6개월 기간제 회원권', TRUE),
                                                                                    (4,'12개월 회원권', 'MEMBERSHIP',360 , NULL, 800000, '6개월 기간제 회원권', TRUE),
                                                                                    (3,'10회 PT', 'PT', 30, 10, 500000, '30일간 10회 PT', TRUE),
                                                                                    (5,'20회 PT', 'PT', 60, 10, 800000, '60일간 10회 PT', TRUE),
                                                                                    (6,'30회 PT', 'PT', 90, 10, 1200000, '90일간 10회 PT', TRUE);

-- membership 결제 및 등록
INSERT INTO payment (memberId, productId, amount, paidAt, method) VALUES
                                                                      (1, 1, 100000, DATE '2025-02-01', 'CARD'),
                                                                      (2, 2, 500000, DATE '2025-03-01', 'CASH'),
                                                                      (3, 1, 100000, DATE '2025-04-01', 'CARD');


-- PT 패키지 → subscription
INSERT INTO subscription
(memberId, productId, productType, trainerId, paymentId, startDate, endDate, totalCount, remainingCount, price, isActive, created_at) VALUES
                                                                                                                                          (4, 3, 'PT', 2, 4, DATE '2025-02-05', DATE '2025-10-05', 10, 10, 500000, TRUE, TIMESTAMP '2025-02-05 09:11:52.167781'),
                                                                                                                                          (5, 3, 'PT', 2, 5, DATE '2025-02-05', DATE '2025-10-05', 10, 10, 500000, TRUE, TIMESTAMP '2025-02-05 09:11:52.167781'),
                                                                                                                                          (6, 3, 'PT', 2, 6, DATE '2025-02-05', DATE '2025-10-05', 10, 10, 500000, TRUE, TIMESTAMP '2025-02-05 09:11:52.167781'),
                                                                                                                                          (7, 3, 'PT', 3, 7, DATE '2025-02-05', DATE '2025-10-05', 10, 10, 500000, TRUE, TIMESTAMP '2025-02-05 09:11:52.167781'),
                                                                                                                                          (8, 3, 'PT', 3, 8, DATE '2025-03-10', DATE '2025-10-10', 10, 10, 500000, TRUE, TIMESTAMP '2025-03-10 09:11:52.167781'),
                                                                                                                                          (9, 3, 'PT', 2, 9, DATE '2025-04-15', DATE '2025-10-15', 10, 10, 500000, TRUE, TIMESTAMP '2025-04-15 09:11:52.167781');

-- 회원권 → subscription (trainerId, totalCount, remainingCount = NULL)
INSERT INTO subscription
(memberId, productId, productType, trainerId, paymentId, startDate, endDate, totalCount, remainingCount, price, isActive, created_at) VALUES
                                                                                                                                          (1, 1, 'MEMBERSHIP', NULL, 1, DATE '2025-02-01', DATE '2025-03-02', NULL, NULL, 100000, FALSE, CURRENT_TIMESTAMP),
                                                                                                                                          (2, 2, 'MEMBERSHIP', NULL, 2, DATE '2025-03-01', DATE '2025-09-01', NULL, NULL, 500000, TRUE, CURRENT_TIMESTAMP),
                                                                                                                                          (3, 1, 'MEMBERSHIP', NULL, 3, DATE '2025-04-01', DATE '2025-05-01', NULL, NULL, 100000, FALSE, CURRENT_TIMESTAMP);



-- PT 패키지 결제 및 등록
INSERT INTO payment (memberId, productId, amount, paidAt, method) VALUES
                                                                      (4, 3, 500000, DATE '2025-02-05', 'CARD'),
                                                                      (5, 3, 500000, DATE '2025-03-10', 'CARD'),
                                                                      (6, 3, 500000, DATE '2025-04-15', 'CASH'),
                                                                      (7, 3, 500000, DATE '2025-05-12', 'CARD'),
                                                                      (8, 3, 500000, DATE '2025-06-20', 'CARD'),
                                                                      (9, 3, 500000, DATE '2025-07-05', 'CARD');

-- INSERT INTO membership (memberId, productId, paymentId, startDate, endDate, price, isActive) VALUES
--                                                                                                  (1, 1, 1, DATE '2025-02-01', DATE '2025-03-02', 100000, FALSE),
--                                                                                                  (2, 2, 2, DATE '2025-03-01', DATE '2025-09-01', 500000, TRUE),
--                                                                                                  (3, 1, 3, DATE '2025-04-01', DATE '2025-05-01', 100000, FALSE);

-- INSERT INTO pt_package (memberId, trainerId, productId, paymentId, startDate, endDate, totalCount, remainingCount, price, isActive, created_at) VALUES
--                                                                                                                                         (4, 2, 3, 4, DATE '2025-02-05', DATE '2025-10-05', 10, 10, 500000, TRUE,TIMESTAMP '2025-02-05 09:11:52.167781'),
--                                                                                                                                         (5, 2, 3, 5, DATE '2025-02-05', DATE '2025-10-05', 10, 10, 500000, TRUE,TIMESTAMP '2025-02-05 09:11:52.167781'),
--                                                                                                                                         (6, 2, 3, 6, DATE '2025-02-05', DATE '2025-10-05', 10, 10, 500000, TRUE,TIMESTAMP '2025-02-05 09:11:52.167781'),
--                                                                                                                                         (7, 3, 3, 7, DATE '2025-02-05', DATE '2025-10-05', 10, 10, 500000, TRUE,TIMESTAMP '2025-02-05 09:11:52.167781'),
--                                                                                                                                         (8, 3, 3, 8, DATE '2025-03-10', DATE '2025-10-10', 10, 10, 500000, TRUE,TIMESTAMP '2025-03-10 09:11:52.167781'),
--                                                                                                                                         (9, 2, 3, 9, DATE '2025-04-15', DATE '2025-10-15', 10, 10, 500000, TRUE,TIMESTAMP '2025-04-15 09:11:52.167781');


-- -- PT 세션 (각 패키지별로 1~2회 출석)
-- INSERT INTO pt_session (packageId, trainerId, memberId, sessionDate, sessionTime) VALUES
--                                                                                       (1, 2, 1, DATE '2025-02-10', TIME '10:00:00'),
--                                                                                       (1, 2, 1, DATE '2025-02-15', TIME '10:00:00'),
--                                                                                       (2, 3, 2, DATE '2025-03-15', TIME '11:00:00'),
--                                                                                       (2, 3, 2, DATE '2025-03-20', TIME '11:00:00'),
--                                                                                       (3, 2, 3, DATE '2025-04-20', TIME '12:00:00'),
--                                                                                       (4, 3, 1, DATE '2025-05-20', TIME '13:00:00'),
--                                                                                       (5, 2, 2, DATE '2025-06-25', TIME '14:00:00'),
--                                                                                       (6, 3, 3, DATE '2025-07-10', TIME '15:00:00');

-- --
-- app_user (관리자/트레이너)
INSERT INTO app_user (userId, email, password, name, role, created_at) VALUES
                                                                           (1, 'trainer1@gym.com', 'password', '트레이너 A', 'TRAINER', CURRENT_TIMESTAMP),
                                                                           (2, 'trainer2@gym.com', 'password', '트레이너 B', 'TRAINER', CURRENT_TIMESTAMP),
                                                                           (3, 'admin@gym.com', 'password', '관리자', 'ADMIN', CURRENT_TIMESTAMP);

-- trainer 테이블에 트레이너 정보 추가
INSERT INTO trainer (trainerId, birthDate, gender, phone, payPerSession, baseSalary, careerYears, created_at)
VALUES
    (1, '1990-01-15', '남성', '010-1111-2222', 40000, 1000000, 5, CURRENT_TIMESTAMP),
    (2, '1992-05-20', '여성', '010-3333-4444', 35000, 900000, 3, CURRENT_TIMESTAMP);
--
-- --
-- member
INSERT INTO member (memberId, name, phone, birthDate, gender, status, memo, created_at) VALUES
                                                                                            (1, '홍길동', '010-1234-5678', '1990-01-01', '남성', 'INACTIVE', '', CURRENT_TIMESTAMP),
                                                                                            (2, '김영희', '010-2345-2312', '1992-02-02', '남성', 'INACTIVE', '', CURRENT_TIMESTAMP),
                                                                                            (3, '이동재', '010-2121-1421', '1992-02-02', '남성', 'INACTIVE', '', CURRENT_TIMESTAMP),
                                                                                            (4, '안태형', '010-3214-1353', '1992-02-02', '남성', 'INACTIVE', '', CURRENT_TIMESTAMP),
                                                                                            (5, '홍희범', '010-3513-2532', '1992-02-02', '남성', 'INACTIVE', '', CURRENT_TIMESTAMP),
                                                                                            (6, '이철수', '010-3456-2352', '1991-03-03', '남성', 'INACTIVE', '', CURRENT_TIMESTAMP),
                                                                                            (7, '이동재', '010-2121-1235', '1992-02-02', '남성', 'INACTIVE', '', CURRENT_TIMESTAMP),
                                                                                            (8, '안태형', '010-3214-6432', '1992-02-02', '남성', 'INACTIVE', '', CURRENT_TIMESTAMP),
                                                                                            (9, '홍희범', '010-3513-6424', '1992-02-02', '남성', 'INACTIVE', '', CURRENT_TIMESTAMP),
                                                                                            (10, '이철수', '010-3456-1424', '1991-03-03', '남성', 'INACTIVE', '', CURRENT_TIMESTAMP),
                                                                                            (11, '이철수', '010-3456-1633', '1991-03-03', '남성', 'INACTIVE', '', CURRENT_TIMESTAMP);
--
--
-- product
INSERT INTO product (productId, name, type, duration, count, price, description, isActive, createdAt) VALUES
                                                                                                          (1, '1개월 회원권', 'MEMBERSHIP', 30, NULL, 100000, '1개월 이용권', TRUE, CURRENT_TIMESTAMP),
                                                                                                          (2, '3개월 회원권', 'MEMBERSHIP', 90, NULL, 150000, '1개월 이용권', TRUE, CURRENT_TIMESTAMP),
                                                                                                          (3, '10회 PT', 'PT', 90, 10, 500000, 'PT 10회 패키지', TRUE, CURRENT_TIMESTAMP);

-- membership
INSERT INTO membership (membershipId, memberId, productId, paymentId, startDate, endDate, price, isActive, created_at) VALUES
    (1, 1, 1, NULL, CURRENT_DATE, DATEADD('DAY', 30, CURRENT_DATE), 100000,  TRUE, CURRENT_TIMESTAMP),
    (2, 2, 1, NULL, CURRENT_DATE, DATEADD('DAY', 30, CURRENT_DATE), 100000, TRUE, CURRENT_TIMESTAMP),
    (3, 3, 1, NULL, CURRENT_DATE, DATEADD('DAY', 30, CURRENT_DATE), 100000,  TRUE, CURRENT_TIMESTAMP),
    (4, 4, 1, NULL, CURRENT_DATE, DATEADD('DAY', 30, CURRENT_DATE), 100000,  TRUE, CURRENT_TIMESTAMP),
    (5, 5, 1, NULL, CURRENT_DATE, DATEADD('DAY', 30, CURRENT_DATE), 100000,  TRUE, CURRENT_TIMESTAMP),
    (6, 6, 1, NULL, CURRENT_DATE, DATEADD('DAY', 30, CURRENT_DATE), 100000, TRUE, CURRENT_TIMESTAMP),
    (7, 7, 1, NULL, CURRENT_DATE, DATEADD('DAY', 30, CURRENT_DATE), 100000,  TRUE, CURRENT_TIMESTAMP),
    (8, 8, 1, NULL, CURRENT_DATE, DATEADD('DAY', 30, CURRENT_DATE), 100000,  TRUE, CURRENT_TIMESTAMP),
    (9, 9, 1, NULL, CURRENT_DATE, DATEADD('DAY', 30, CURRENT_DATE), 100000, TRUE, CURRENT_TIMESTAMP),
    (10, 10, 1, NULL, CURRENT_DATE, DATEADD('DAY', 30, CURRENT_DATE), 100000,  TRUE, CURRENT_TIMESTAMP),
    (11, 11, 1, NULL, CURRENT_DATE, DATEADD('DAY', 30, CURRENT_DATE), 100000, TRUE, CURRENT_TIMESTAMP);

--
-- -- pt_package
-- INSERT INTO pt_package (packageId, memberId, trainerId, productId, paymentId, startDate, endDate, totalCount, remainingCount, price, memo, isActive, created_at) VALUES
--     (1, 2, 1, 2, NULL, CURRENT_DATE, DATEADD('DAY', 90, CURRENT_DATE), 10, 10, 500000, '', TRUE, CURRENT_TIMESTAMP),
--     (2, 3, 2, 2, NULL, CURRENT_DATE, DATEADD('DAY', 90, CURRENT_DATE), 10, 10, 500000, '', TRUE, CURRENT_TIMESTAMP),
--     (3, 3, 2, 2, NULL, CURRENT_DATE, DATEADD('DAY', 90, CURRENT_DATE), 10, 10, 500000, '', TRUE, CURRENT_TIMESTAMP);
-- --
-- -- -- pt_session
-- INSERT INTO pt_session (sessionId, packageId, trainerId, memberId, sessionDate, sessionTime, created_at) VALUES
--                                                                                                              (1, 1, 1, 2, CURRENT_DATE, '10:00:00', CURRENT_TIMESTAMP),
--                                                                                                              (2, 1, 2, 3, CURRENT_DATE, '11:00:00', CURRENT_TIMESTAMP),
--                                                                                                              (3, 1, 1, 2, CURRENT_DATE, '12:00:00', CURRENT_TIMESTAMP),
--                                                                                                              (4, 1, 1, 2, CURRENT_DATE, '13:00:00', CURRENT_TIMESTAMP),
--                                                                                                              (5, 1, 1, 2, CURRENT_DATE, '15:00:00', CURRENT_TIMESTAMP),
--                                                                                                              (6, 1, 1, 2, CURRENT_DATE, '14:00:00', CURRENT_TIMESTAMP);

--payment
-- INSERT INTO payment (paymentId,
--                      memberId,
--                      productId,
--                      amount,
--                      paidAt,
--                      method) VALUES (1, 1, 1,100000,CURRENT_TIMESTAMP,'CARD')

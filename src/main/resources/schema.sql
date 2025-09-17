-- 사용자 테이블 (관리자, 트레이너 통합)
CREATE TABLE app_user (
                          userId BIGINT AUTO_INCREMENT PRIMARY KEY,
                          email VARCHAR(100) NOT NULL UNIQUE,
                          password VARCHAR(255) NOT NULL,
                          name VARCHAR(50),
                          role VARCHAR(20) NOT NULL, -- ADMIN, TRAINER
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 회원 테이블
CREATE TABLE member (
                        memberId BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(50) NOT NULL ,
                        phone VARCHAR(20) NOT NULL UNIQUE,
                        birthDate DATE NOT NULL,
                        gender VARCHAR(10) NOT NULL,
                        status VARCHAR(20), -- ACTIVE, INACTIVE
                        memo VARCHAR(255),
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE product (
                         productId BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(50) NOT NULL,             -- 상품 이름 (예: 1개월 회원권, 10회 PT)
                         type VARCHAR(20) NOT NULL,             -- 'MEMBERSHIP' 또는 'PT'
                         duration INT,                          -- 사용기간 (일) / PT도 유효기간 있음
                         count INT,                             -- PT의 경우 횟수 / 일반권은 NULL
                         price INT NOT NULL,                    -- 현재 판매 가격
                         description VARCHAR(255),              -- 설명
                         isActive BOOLEAN DEFAULT TRUE,        -- 판매 중 여부
                         createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE trainer (
                         trainerId BIGINT PRIMARY KEY,         -- == app_user.userId
                         birthDate DATE,
                         gender VARCHAR(10) NOT NULL,
                         phone VARCHAR(20),
                         payPerSession INT DEFAULT 30000,      -- PT 1회 수당
                         baseSalary INT DEFAULT 0,             -- 월급제일 경우 기본급
                         careerYears INT,                      -- 경력 (연수)
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         FOREIGN KEY (trainerId) REFERENCES app_user(userId) ON DELETE CASCADE
);

-- 💳 결제 테이블 (이용권 또는 PT 패키지 결제 정보 기록)
CREATE TABLE payment (
                         paymentId BIGINT AUTO_INCREMENT PRIMARY KEY,
                         memberId BIGINT NOT NULL,
                         productId BIGINT,
                         amount INT NOT NULL,
                         paidAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         method VARCHAR(50), -- 예: CARD, CASH, BANK_TRANSFER
                         FOREIGN KEY (memberId) REFERENCES member(memberId) ON DELETE CASCADE,
                         FOREIGN KEY (productId) REFERENCES product(productId) ON DELETE SET NULL
);

CREATE TABLE subscription (
                              subscriptionId BIGINT AUTO_INCREMENT PRIMARY KEY,
                              memberId BIGINT NOT NULL,
                              productId BIGINT NOT NULL,
                              productType VARCHAR(20) NOT NULL,   -- 'MEMBERSHIP' 또는 'PT'
                              trainerId BIGINT,              -- PT일 때만 사용
                              paymentId BIGINT,
                              startDate DATE NOT NULL,
                              endDate DATE NOT NULL,
                              totalCount INT,                -- PT 전용
                              remainingCount INT,            -- PT 전용
                              price INT NOT NULL,
                              isActive BOOLEAN DEFAULT TRUE,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              FOREIGN KEY (memberId) REFERENCES member(memberId) ON DELETE CASCADE,
                              FOREIGN KEY (trainerId) REFERENCES app_user(userId) ON DELETE SET NULL,
                              FOREIGN KEY (productId) REFERENCES product(productId) ON DELETE SET NULL
);
CREATE TABLE refund (
                        refundId BIGINT AUTO_INCREMENT PRIMARY KEY,
                        paymentId BIGINT NOT NULL,        -- 어떤 결제를 환불했는지
                        amount INT NOT NULL,              -- 환불 금액
                        reason VARCHAR(255),              -- 환불 사유 (중도해지, 상품변경 등)
                        refundDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (paymentId) REFERENCES payment(paymentId)
);

-- PT 세션 테이블
CREATE TABLE pt_session (
                            sessionId BIGINT AUTO_INCREMENT PRIMARY KEY,
                            subscriptionId BIGINT NOT NULL,
                            trainerId BIGINT,
                            memberId BIGINT NOT NULL,
                            sessionDate DATE NOT NULL,
                            sessionTime TIME NOT NULL,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            status VARCHAR(10) NOT NULL DEFAULT 'BOOKED',
                            batchId varchar(36),
                            FOREIGN KEY (subscriptionId) REFERENCES subscription(subscriptionId) ON DELETE CASCADE,
                            FOREIGN KEY (trainerId) REFERENCES app_user(userId) ON DELETE SET NULL,
                            FOREIGN KEY (memberId) REFERENCES member(memberId) ON DELETE CASCADE
);

-- 출석 테이블
CREATE TABLE attendance (
                            attendanceId BIGINT AUTO_INCREMENT PRIMARY KEY,
                            memberId BIGINT NOT NULL,
                            sessionId BIGINT NOT NULL,
                            attendedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            status  VARCHAR(10) NOT NULL DEFAULT 'ATTENDED',
                            FOREIGN KEY (memberId) REFERENCES member(memberId) ON DELETE CASCADE,
                            FOREIGN KEY (sessionId) REFERENCES pt_session(sessionId) ON DELETE CASCADE,
                            UNIQUE (memberId, sessionId)
);
-- 사용자 테이블 (관리자, 트레이너 통합)
CREATE TABLE app_user (
                          userId BIGINT AUTO_INCREMENT PRIMARY KEY,
                          email VARCHAR(100) NOT NULL UNIQUE,
                          password VARCHAR(255) NOT NULL,
                          name VARCHAR(50),
                          phone VARCHAR(20),
                          role VARCHAR(20) NOT NULL, -- ADMIN, TRAINER
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 회원 테이블
CREATE TABLE member (
                        memberId BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(50) NOT NULL,
                        phone VARCHAR(20) NOT NULL,
                        birthDate DATE NOT NULL,
                        gender VARCHAR(10) NOT NULL,
                        status VARCHAR(20), -- ACTIVE, INACTIVE
                        memo VARCHAR(255),
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 출석 테이블
CREATE TABLE attendance (
                            attendanceId BIGINT AUTO_INCREMENT PRIMARY KEY,
                            memberId BIGINT NOT NULL,
                            attendedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            FOREIGN KEY (memberId) REFERENCES member(memberId) ON DELETE CASCADE
);

-- 이용권 테이블
CREATE TABLE membership (
                            membershipId BIGINT AUTO_INCREMENT PRIMARY KEY,
                            memberId BIGINT NOT NULL,
                            startDate DATE NOT NULL,
                            endDate DATE NOT NULL,
                            price INT NOT NULL,
                            memo VARCHAR(255),
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            FOREIGN KEY (memberId) REFERENCES member(memberId) ON DELETE CASCADE
);

-- PT 패키지 테이블
CREATE TABLE pt_package (
                            packageId BIGINT AUTO_INCREMENT PRIMARY KEY,
                            memberId BIGINT NOT NULL,
                            trainerId BIGINT NOT NULL,
                            totalCount INT NOT NULL,
                            remainingCount INT NOT NULL,
                            startDate DATE NOT NULL,
                            endDate DATE NOT NULL,
                            price INT NOT NULL,
                            memo VARCHAR(255),
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            FOREIGN KEY (memberId) REFERENCES member(memberId) ON DELETE CASCADE,
                            FOREIGN KEY (trainerId) REFERENCES app_user(userId) ON DELETE CASCADE
);

-- PT 세션 테이블
CREATE TABLE pt_session (
                            sessionId BIGINT AUTO_INCREMENT PRIMARY KEY,
                            packageId BIGINT NOT NULL,
                            trainerId BIGINT NOT NULL,
                            memberId BIGINT NOT NULL,
                            sessionDate DATE NOT NULL,
                            sessionTime TIME NOT NULL,
                            memo VARCHAR(255),
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            FOREIGN KEY (packageId) REFERENCES pt_package(packageId) ON DELETE CASCADE,
                            FOREIGN KEY (trainerId) REFERENCES app_user(userId),
                            FOREIGN KEY (memberId) REFERENCES member(memberId)
);

-- 💳 결제 테이블 (이용권 또는 PT 패키지 결제 정보 기록)
CREATE TABLE payment (
                         paymentId BIGINT AUTO_INCREMENT PRIMARY KEY,
                         memberId BIGINT NOT NULL,
                         membershipId BIGINT,
                         packageId BIGINT,
                         amount INT NOT NULL,
                         paidAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         method VARCHAR(50), -- 예: CARD, CASH, BANK_TRANSFER
                         memo VARCHAR(255),
                         FOREIGN KEY (memberId) REFERENCES member(memberId) ON DELETE CASCADE,
                         FOREIGN KEY (membershipId) REFERENCES membership(membershipId) ON DELETE SET NULL,
                         FOREIGN KEY (packageId) REFERENCES pt_package(packageId) ON DELETE SET NULL
);
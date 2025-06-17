INSERT INTO app_user (userId, email, password, name, phone, role, created_at)
VALUES (1, 'admin@gym.com', '$2a$10$TOvks5HCMt82LuEc9QXxMOwgPud/TxPKUCIlmcqGpoP/NFTPbsyia', '관리자', '010-0000-0000', 'ADMIN', CURRENT_TIMESTAMP);

-- ✅ 삽입된 계정 정보:
-- 이메일: admin@gym.com
--
-- 비밀번호: 1234 (BCrypt 암호화됨)
--
-- 역할: ADMIN
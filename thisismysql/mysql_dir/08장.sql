# 8장 테이블과 뷰

DROP DATABASE tabledb;
CREATE DATABASE tabledb;

USE tabledb;
DROP TABLE IF EXISTS buytbl, usertbl;
CREATE TABLE usertbl -- 회원 테이블
(
	userID CHAR(8) NOT NULL PRIMARY KEY, -- 사용자 아이디
    name VARCHAR(10) NOT NULL, -- 이름
    birthYear INT NOT NULL, -- 출생년도
    addr CHAR(2) NOT NULL, -- 지역경기, 서울, 경남 등으로 글자만 입력
    mobile1 CHAR(3) NULL, -- 휴대폰의 국번(011, 016, 017, 018, 019, 010 등)
    mobile2 CHAR(8) NULL, -- 휴대폰의 나머지 전화번호(하이폰 제외)
    height SMALLINT NULL, -- 신장
    mDate DATE NULL -- 회원 가입일
);

CREATE TABLE buytbl
(
	num INT AUTO_INCREMENT NOT NULL PRIMARY KEY, -- 순번(PK)
    userid CHAR(8) NOT NULL, -- 아이디(FK)
    prodName CHAR(6) NOT NULL, -- 물품명
    groupName CHAR(4) NULL, -- 분류
    price INT NOT NULL, -- 단가
    amount SMALLINT NOT NULL, -- 수량
    FOREIGN KEY (userid) REFERENCES usertbl(userID)
);

INSERT INTO usertbl VALUES ('LSG', '이승기', 1987, '서울', '011', '11111111', 182, '2008-8-8'),
('KBS', '김범수', 1979, '경남', '011', '22222222', 173, '2012-4-4'),
('KKH', '김경호', 1971, '전남', '019', '33333333', 177, '2007-7-7'),
('JYP', '조용필', 1970, '전남', '010', '44444444', 177, '2007-7-7');

INSERT INTO buytbl VALUES (NULL, 'KBS', '운동화', NULL, 30, 2),
(NULL, 'KBS', '노트북', '전자', 1000, 1),
(NULL, 'JYP', '모니터', '전자', 200, 1);

# 2. 제약조건
USE tableDB;
DROP TABLE IF EXISTS buytbl, usertbl;
CREATE TABLE usertbl
(
	userID CHAR(8) NOT NULL PRIMARY KEY,
    name VARCHAR(10) NOT NULL,
    birthYEAR INT NOT NULL
);
DESCRIBE usertbl;
DESC usertbl;

DROP TABLE IF EXISTS usertbl;
CREATE TABLE usertbl
(
	userID CHAR(8) NOT NULL,
    name VARCHAR(10) NOT NULL,
    birthYear INT NOT NULL,
    CONSTRAINT PRIMARY KEY PK_usertbl_userID (userID)
);

DROP TABLE IF EXISTS usertbl;
CREATE TABLE usertbl
(
	userID CHAR(8) NOT NULL,
    name VARCHAR(10) NOT NULL,
    birthYear INT NOT NULL
);
ALTER TABLE usertbl
	ADD CONSTRAINT PK_usertbl_userID
		PRIMARY KEY (userID);

DROP TABLE IF EXISTS prodtbl;
CREATE TABLE prodtbl
(
	prodCode CHAR(3) NOT NULL,
    prodID CHAR(4) NOT NULL,
    prodDate DATETIME NOT NULL,
    prodCur CHAR(10) NULL
);
ALTER TABLE prodtbl
	ADD CONSTRAINT PK_prodtbl_prodCode_prodID
		PRIMARY KEY (prodCode, prodID);

DROP TABLE IF EXISTS prodtbl;
CREATE TABLE prodtbl
(
	prodCode CHAR(3) NOT NULL,
    prodID CHAR(4) NOT NULL,
    prodDate DATETIME NOT NULL,
    prodCur CHAR(10) NULL,
    CONSTRAINT PK_prodtbl_prodCode_prodID PRIMARY KEY (prodCode, prodID)
);
SHOW INDEX FROM prodtbl;

# 외래키 제약 조건
DROP TABLE IF EXISTS buytbl, usertbl;
CREATE TABLE usertbl
(
	userID CHAR(8) NOT NULL PRIMARY KEY,
    name VARCHAR(10) NOT NULL,
    birthYear INT NOT NULL
);
CREATE TABLE buytbl
(
	num INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    userID CHAR(8) NOT NULL,
    prodName CHAR(6) NOT NULL,
    FOREIGN KEY(userID) REFERENCES usertbl(userID)
);

DROP TABLE IF EXISTS buytbl;
CREATE TABLE buytbl
(
	num INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    userID CHAR(8) NOT NULL,
    prodName CHAR(6) NOT NULL,
    CONSTRAINT FK_usertbl_buytbl FOREIGN KEY (userID) REFERENCES usertbl(userID)
);

DROP TABLE IF EXISTS buytbl;
CREATE TABLE buytbl
(
	num INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    userID CHAR(8) NOT NULL,
    prodName CHAR(6) NOT NULL
);
ALTER TABLE buytbl
	ADD CONSTRAINT FK_usertbl_buytbl
		FOREIGN KEY (userID) REFERENCES usertbl(userID);
        
ALTER TABLE buytbl
	DROP FOREIGN KEY FK_usertbl_buytbl; -- 외래키 제거
ALTER TABLE buytbl
	ADD CONSTRAINT FK_usertbl_buytbl
		FOREIGN KEY (userID) REFERENCES usertbl(userID)
        ON UPDATE CASCADE;

USE tableDB;
DROP TABLE IF EXISTS buytbl, usertbl;
CREATE TABLE usertbl
(
	userID CHAR(8) NOT NULL PRIMARY KEY,
    name VARCHAR(10) NOT NULL,
    birthYear INT NOT NULL,
    email CHAR(30) NULL UNIQUE
);
DROP TABLE IF EXISTS usertbl;
CREATE TABLE usertbl
(
	userID CHAR(8) NOT NULL PRIMARY KEY,
    name VARCHAR(10) NOT NULL,
    birthYear INT NOT NULL,
    email CHAR(30) NULL,
    CONSTRAINT AK_email UNIQUE(email)
);

# CHECK 제약 조건
-- 출생년도가 1900년 이후 그리고 2023년 이전, 이름은 반드시 넣어야 함
DROP TABLE IF EXISTS usertbl;
CREATE TABLE usertbl
(
	userID CHAR(8) PRIMARY KEY,
    name VARCHAR(10),
    birthYear INT CHECK (birthYear >= 1900 AND birthYear <= 2023),
    mobile1 CHAR(3) NULL,
    CONSTRAINT CK_name CHECK(name IS NOT NULL)
);
-- 휴대폰 국번 체크
ALTER TABLE usertbl
	ADD CONSTRAINT CK_mobile1
		CHECK (mobile1 IN ('010', '011', '016', '017', '018', '019'));

# DEFAULT 정의
DROP TABLE IF EXISTS usertbl;
CREATE TABLE usertbl
(
	userID 		CHAR(8) NOT NULL PRIMARY KEY,
    name 		VARCHAR(10) NOT NULL,
    birthYear 	INT NOT NULL DEFAULT -1,
    addr 		CHAR(2) NOT NULL DEFAULT '서울',
    mobile1 	CHAR(3) NULL,
    mobile2 	CHAR(8) NULL,
    height 		SMALLINT NULL DEFAULT 170,
    mDate 		DATE NULL
);

DROP TABLE IF EXISTS usertbl;
CREATE TABLE usertbl
(
	userID 		CHAR(8) NOT NULL PRIMARY KEY,
    name 		VARCHAR(10) NOT NULL,
    birthYear 	INT NOT NULL,
    addr 		CHAR(2) NOT NULL,
    mobile1 	CHAR(3) NULL,
    mobile2 	CHAR(8) NULL,
    height 		SMALLINT NULL,
    mDate 		DATE NULL
);
ALTER TABLE usertbl
	ALTER COLUMN birthYear SET DEFAULT -1;
ALTER TABLE usertbl
	ALTER COLUMN addr SET DEFAULT '서울';
ALTER TABLE usertbl
	ALTER COLUMN height SET DEFAULT 170;

-- default문은 DEFAULT로 설정된 값을 자동 입력합니다.
INSERT INTO usertbl VALUES('LHL', '이혜리', default, default, '011', '1234567', default, '2023-12-12');

-- 열 이름이 명시되지 ㅇ낳으면 DEFAULT로 설정된 값을 자동 입력합니다.
INSERT INTO usertbl(userID, name) VALUES('KAY', '김아영');

-- 값이 직접 명기되면 DEFAULT로 설정된 값은 무시된다.
INSERT INTO usertbl VALUES('KYH', '김용환', 1970, '경기', '011', '1234567', 180, '2023-12-12');

# 3. 테이블 압축
CREATE DATABASE IF NOT EXISTS compressDB;
USE compressDB;
CREATE TABLE normaltbl(
	emp_no INT,
    first_name VARCHAR(14)
);
CREATE TABLE compresstbl(
	emp_no INT,
    first_name VARCHAR(14)
) ROW_FORMAT = COMPRESSED;

INSERT INTO normaltbl SELECT emp_no, first_name FROM employees.employees;
INSERT INTO compresstbl SELECT emp_no, first_name FROM employees.employees;

SHOW TABLE STATUS FROM compressDB;

DROP DATABASE IF EXISTS compressDB;

# 4. 임시 테이블
USE employees;
CREATE TEMPORARY TABLE IF NOT EXISTS temptbl (id INT, name CHAR(5));
CREATE TEMPORARY TABLE IF NOT EXISTS employees (id INT, name CHAR(5));
DESC temptbl;
DESC employees;

INSERT INTO temptbl VALUES(1, 'This');
INSERT INTO employees VALUES(2, 'MySQL');
SELECT * FROM temptbl;
SELECT * FROM employees;

# 6. 테이블 수정
# 열추가
use tabledb;
ALTER TABLE usertbl
	ADD homepage VARCHAR(30) -- 열 추가
		DEFAULT 'http://www.hanbit.co.kr' -- 디폴트 값
		NULL; -- NULL 허용

# 열 삭제
ALTER TABLE usertbl
	DROP COLUMN mobile1;

# 열의 이름 및 데이터 형식 변경
ALTER TABLE usertbl
	CHANGE COLUMN name uName VARCHAR(20) NULL;

# 열 제약 조건 추가 및 삭제
ALTER TABLE usertbl
	DROP PRIMARY KEY;

-- ALTER TABLE usertbl
-- 	DROP FOREIGN KEY buytbl_ibfk_1;

# 실습
DROP DATABASE tabledb;
CREATE DATABASE tabledb;
USE tabledb;
DROP TABLE IF EXISTS buytbl, usertbl;
CREATE TABLE usertbl -- 회원 테이블
(
	userID CHAR(8) NOT NULL PRIMARY KEY, -- 사용자 아이디
    name VARCHAR(10), -- 이름
    birthYear INT CHECK ((birthYear >= 1900 AND birthYear <= 2023) AND (birthYear IS NOT NULL)), -- 출생년도
    addr CHAR(2), -- 지역경기, 서울, 경남 등으로 글자만 입력
    mobile1 CHAR(3), -- 휴대폰의 국번(011, 016, 017, 018, 019, 010 등)
    mobile2 CHAR(8), -- 휴대폰의 나머지 전화번호(하이폰 제외)
    height SMALLINT, -- 신장
    mDate DATE -- 회원 가입일
);

CREATE TABLE buytbl
(
	num INT AUTO_INCREMENT PRIMARY KEY, -- 순번(PK)
    userid CHAR(8), -- 아이디(FK)
    prodName CHAR(6), -- 물품명
    groupName CHAR(4), -- 분류
    price INT, -- 단가
    amount SMALLINT, -- 수량
    FOREIGN KEY (userid) REFERENCES usertbl(userID)
);

INSERT INTO usertbl VALUES ('LSG', '이승기', 1987, '서울', '011', '11111111', 182, '2008-8-8'),
('KBS', '김범수', 1979, '경남', '011', '22222222', 173, '2012-4-4'),
('KKH', '김경호', 1971, '전남', '019', '33333333', 177, '2007-7-7'),
('JYP', '조용필', 1970, '전남', '010', '44444444', 177, '2007-7-7');

INSERT INTO buytbl VALUES (NULL, 'KBS', '운동화', NULL, 30, 2),
(NULL, 'KBS', '노트북', '전자', 1000, 1),
(NULL, 'JYP', '모니터', '전자', 200, 1);

# 2. 뷰
# 1. 뷰의 개념
USE tabledb;
CREATE TABLE v_usertbl
AS 
	SELECT userid, name, addr FROM usertbl;

SELECT * FROM v_usertbl;

CREATE VIEW v_userbuytbl
AS
	SELECT U.userid, U.name, B.prodName, U.addr, CONCAT(U.mobile1, U.mobile2) AS '연락처'
    FROM usertbl U
		INNER JOIN buytbl B
		ON U.userid = B.userid;

SELECT * FROM v_userbuytbl WHERE name = '김범수';


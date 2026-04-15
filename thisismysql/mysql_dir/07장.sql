SELECT CAST('2020-10-19 12:35:29.123' AS DATE) AS 'DATE'; # 2020-10-19
SELECT CAST('2020-10-19 12:35:29.123' AS TIME) AS 'TIME'; # 12:35:29
SELECT CAST('2020-10-19 12:35:29.123' AS DATETIME) AS 'DATETIME'; # 2020-10-19 12:35:29

# 변수 사용
USE sqldb;
SET @myVar1 = 5;
SET @myVar2 = 3;
SET @myVar3 = 4.25;
SET @myVar4 = '가수 이름==>';
SELECT @myVar1;
SELECT @myVar2 + @myVar3;
SELECT @myVar4, Name FROM usertbl WHERE height > 100;

SET @myVar1 = 3;
PREPARE myQuery FROM 'SELECT Name, height FROM usertbl ORDER BY height LIMIT ?';
EXECUTE myQuery USING @myVar1;

# 데이터 형식 변환 함수
use sqldb;
SELECT AVG(amount) AS '평균 구매 개수' FROM buytbl;
SELECT CAST(AVG(amount) AS SIGNED INTEGER) AS '평균 구매 개수' FROM buytbl;
SELECT CONVERT(AVG(amount), SIGNED INTEGER) AS '평균 구매 개수' FROM buytbl;

SELECT CAST('2020$12$12' AS DATE);
SELECT CAST('2020/12/12' AS DATE);
SELECT CAST('2020%12%12' AS DATE);
SELECT CAST('2020@12@12' AS DATE);

SELECT num, CONCAT(CAST(price AS CHAR(10)), 'X', CAST(amount AS CHAR(4)), '=') AS '단가*수량',
price * amount AS '구매액'
FROM buytbl;

# 암시적인 형변환
SELECT '100' + '200'; # 300
SELECT CONCAT('100', '200'); # 100200
SELECT CONCAT(100, '200'); # 100200
SELECT 1 > '2mega'; # 1 > 2 => 0
SELECT 3 > '2MEGA'; # 3 > 2 => 1
SELECT 0 = 'mega2'; # 0 = 0 => 1

# IF(수식, 참, 거짓)
SELECT IF(100 > 200, '참', '거짓'); # 거짓

# IFNULL(수식1, 수식2)
# 수식1이 NULL이면 수직2 실행
# 수직1이 NULL이 아니면 수식1 실행
SELECT IFNULL(NULL, '널입니다.'); # 널입니다.
SELECT IFNULL(100, '널입니다.'); # 100

# NULLIF(수식1, 수식2)
# 수식1 = 수식2이면 NULL 반환, 다르면 수식1 반환
SELECT NULLIF(100, 100); # NULL
SELECT NULLIF(200, 100); # 200

# CASE ~ WHEN ~ ELSE ~ END
# SWITH문 또는 조건문
SELECT CASE 10
WHEN 1 THEN '일'
WHEN 5 THEN '오'
WHEN 10 THEN '십'
ELSE '모름'
END AS 'CASE연습'; # 십

# ASCII(아스키코드), CHAR(숫자)
# ASCII : 아스키코드에 대한 숫자를 반환
# CHAR : 숫자에 대한 문자를 반환
SELECT ASCII('A'), CHAR(65); # 65 A

# BIT_LENGTH(문자열), CHAR_LENGTH(문자열), LENGTH(문자열)
# BIT_LENGTH : 문자열의 비트 수를 반환
# CHAR_LENGTH : 문자열의 개수 반환
# LENGTH : 문자열의 BYTE 개수 반환 # 영문 3BYTE, 한글 9BYTE
SELECT BIT_LENGTH('abc'); # 24
SELECT CHAR_LENGTH('abc'); # 3
SELECT LENGTH('abc'); # 3
SELECT BIT_LENGTH('가나다'); # 72
SELECT CHAR_LENGTH('가나다'); # 3
SELECT LENGTH('가나다'); # 9

# CONCAT(문자열1, 문자열2, ...), CONCAT_WS(구분자, 문자열1, 문자열2, ...)
# 문자열을 연결해줍니다.
SELECT CONCAT('HELLO', ' WORLD'); # HELLO WORLD
SELECT CONCAT_WS(',', '1','2','3'); # 1,2,3

# ELT(위치, 문자열1, 문자열2...) FIELD(찾을 문자열, 문자열1, 문자열2, ...)
# FIND_IN_SET(찾을 문자열, 문자열 리스트), INSTR(기준 문자열, 부분 문자열)
# LOCATE(부분 문자열, 기준 문자열)

# ELT : 위치 번째에 해당하는 문자열 반환
SELECT ELT(2, '하나', '둘', '셋'); # 둘

# FIELD : 찾을 문자열의 위치를 찾아서 반환
SELECT FIELD('둘', '하나', '둘', '셋'); # 2

# FIND_IN_SET : 찾을 문자열을 문자열 리스트에서 찾아서 반환
SELECT FIND_IN_SET('둘', '하나,둘,셋'); # 2

# INSTR : 기준 문자열에서 부분 문자열 찾아서 그 시작 위치를 반환
SELECT INSTR('하나둘셋', '둘'); # 3

# LOCATE : INSTR과 동일하지만 파라미터의 순서가 반대입니다
SELECT LOCATE('둘', '하나둘셋'); # 3

# FORMAT(숫자, 소수점 자릿수)
# 숫자를 소수점 아래 자릿수까지 표현합니다. 또한 1000단위마다 콤마를 표시한다
SELECT FORMAT(123456.123456, 4); # 123,456.1235

# BIN(숫자), HEX(숫자), OCT(숫자)
# 2진수, 16진수, 8진수의 값을 반환한다
SELECT BIN(31), HEX(31), OCT(31); # 11111 1F 37

# INSERT(기준문자열, 위치, 길이, 삽입할 문자열)
# 기준 문자열의 위치부터 길이만큼을 지우고 삽입할 문자열을 끼워넣는다.
SELECT INSERT('abcdefghi', 3, 4, '@@@@'); # ab@@@@ghi
SELECT INSERT('abcdefghi', 3, 2, '@@@@'); # ab@@@@efghi

# LEFT(문자열, 길이), RIGHT(문자열, 길이)
# 왼쪽 도는 오른쪽에서 문자열의 길이만큼 반환합니다.
SELECT LEFT('abcdefghi', 3); # abc
SELECT RIGHT('abcdefghi', 3); # ghi

# UPPER(문자열), LOWER(문자열)
# 소문자를 대문자로, 대문자를 소문자로 변경합니다.
SELECT UPPER('abcdEFGH'); # ABCDEFGH
SELECT LOWER('abcdEFGH'); # abcdefgh

# LPAD(문자열, 길이, 채울 문자열), RPAD(문자열, 길이, 채울 문자열)
# 문자열을 길이만큼 늘린후에 빈곳을 채울 문자열로 채운다.
SELECT LPAD('이것이', 5, '##'); # ##이것이
SELECT RPAD('이것이', 5, '##'); # 이것이##



 

# INNSER JOIN
# 3	JYP	모니터	전자	200	1	JYP	조용필	1950	경기	011	4444444	166	2009-04-04
USE sqldb;
SELECT * 
FROM buytbl 
	INNER JOIN usertbl 
    ON buytbl.userID = usertbl.userID
WHERE buytbl.userID = 'JYP';

USE sqldb;
explain
SELECT * FROM buytbl INNER JOIN usertbl ON buytbl.userID = usertbl.userID
ORDER BY num; # MySQL 8.0.17 버전 이후에서는 num을 넣지 않으면 userID를 기준으로 정려합니다.

SELECT buytbl.userID, usertbl.name, buytbl.prodName, usertbl.addr, CONCAT(usertbl.mobile1, usertbl.mobile2) AS '연락처'
FROM buytbl INNER JOIN usertbl ON buytbl.userID = usertbl.userID
ORDER BY buytbl.num;

# 조인하는 테이블에 별칭 부여하여 코드 줄이기
SELECT B.userID, U.name, B.prodName, U.addr, CONCAT(U.mobile1, U.mobile2) AS '연락처'
FROM buytbl B INNER JOIN usertbl U ON B.userID = U.userID
ORDER BY B.num;

# 회원 테이블을 기준으로 아이디가 JYP인 사람의 구매한 물건을 출력하세요.
SELECT U.userID, U.name, B.prodName, U.addr, CONCAT(U.mobile1, U.mobile2) AS '연락처'
FROM usertbl U INNER JOIN buytbl B ON U.userID = B.userID
WHERE U.userID = 'JYP';

# 전체 회원들이 구매한 목록을 출력합니다. 단, userID 순으로 정렬하세요.
SELECT U.userID, U.name, B.prodName, U.addr, CONCAT(U.mobile1, U.mobile2) AS '연락처'
FROM usertbl U INNER JOIN buytbl B ON U.userID = B.userID
ORDER BY U.userID;

# 한번이라도 물건을 구매한 적이 있는 사람들의 있는 userID, name, addr를 출력하세요.
SELECT DISTINCT U.userID, U.name, U.addr
FROM usertbl U INNER JOIN buytbl B ON U.userID = B.userID;

# EXIST 문을 사용하여 한번이라도 물건을 구매한 적이 있는 사람들의 있는 userID, name, addr를 출력하세요.
SELECT DISTINCT U.userID, U.name, U.addr
FROM usertbl U
WHERE EXISTS(SELECT * FROM buytbl B WHERE U.userID = B.userID);

USE sqldb;
CREATE TABLE stdTbl(
	stdName VARCHAR(255) NOT NULL PRIMARY KEY,
    addr VARCHAR(255) NOT NULL
);

CREATE TABLE clubTbl(
	clubName VARCHAR(255) NOT NULL PRIMARY KEY,
    roomNo VARCHAR(255) NOT NULL
);

CREATE TABLE stdclubTbl(
	num int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    stdName VARCHAR(255) NOT NULL,
    clubName VARCHAR(255) NOT NULL,
    FOREIGN KEY(stdName) REFERENCES stdTbl(stdName),
    FOREIGN KEY(clubName) REFERENCES clubTbl(clubName)
);
COMMIT;
INSERT INTO stdtbl VALUES('김범수', '경남'), ('성시경', '서울'), ('조용필', '경기'), ('은지원', '경북'), ('비비킴', '서울');
INSERT INTO clubTbl VALUES('수영', '101호'), ('바둑', '102호'), ('축구', '103호'), ('봉사', '104호');
INSERT INTO stdclubTbl 
VALUES(NULL, '김범수', '바둑'), 
(NULL, '김범수', '축구'), 
(NULL, '조용필', '축구'), 
(NULL, '은지원', '축구'), 
(NULL, '은지원', '봉사'), 
(NULL, '비비킴', '봉사');

SELECT S.stdName, S.addr, SC.clubName, C.roomNo
FROM stdtbl S INNER JOIN stdClubTbl SC ON S.stdName = SC.stdName
INNER JOIN clubTbl C ON SC.clubName = C.clubName
ORDER BY S.stdName;

# 동아리를 기준으로 가입한 학생의 목록을 출력하세요.
SELECT C.clubName, C.roomNO, S.stdName, S.addr
FROM clubTbl C INNER JOIN stdClubTbl SC ON C.clubName = SC.clubName
INNER JOIN stdTbl S ON SC.stdName = S.stdName
ORDER BY C.clubName;

# OUTER JOIN
# 전체 회원의 구매 기록을 출력합니다. 단, 구매 기록이 없는 회원도 출력되어야 합니다.
SELECT U.userID, U.name, B.prodName, U.addr, CONCAT(U.mobile1, U.mobile2) AS '연락처'
FROM userTbl U LEFT OUTER JOIN buytbl B ON U.userID = B.userID
ORDER BY U.userID;

# RIGHT OUTER JOIN 사용시 위 쿼리문과 동일한 결과를 얻기 위해서는 userTbl과 buyTbl의 순서를 바꿔줍니다.
SELECT U.userID, U.name, B.prodName, U.addr, CONCAT(U.mobile1, U.mobile2) AS '연락처'
FROM buyTbl B RIGHT OUTER JOIN userTbl U ON B.userID = U.userID
ORDER BY U.userID;

# 한번도 구매한 적이 없는 유령 회원의 목록을 출력합니다.
SELECT U.userID, U.name, B.prodName, U.addr, CONCAT(U.mobile1, U.mobile2) AS '연락처'
FROM userTbl U LEFT OUTER JOIN buytbl B ON U.userID = B.userID
WHERE B.prodName IS NULL
ORDER BY U.userID;

# 동아리에 가입하지 않은 학생을 출력합니다.
SELECT S.stdName, S.addr, SC.clubName, C.roomNo
FROM stdtbl S 
LEFT OUTER JOIN stdClubTbl SC ON S.stdName = SC.stdName
LEFT OUTER JOIN clubTbl C ON SC.clubName = C.clubName
ORDER BY S.stdName;

# 동아리를 기준으로 가입한 학생을 출력한다. 단, 가입 학생이 하나도 없는 동아리도 출력하게 한다.
SELECT C.clubName, C.roomNO, S.stdName, S.addr
FROM stdtbl S
LEFT OUTER JOIN stdclubTbl SC ON SC.stdName = S.stdName
RIGHT OUTER JOIN clubTbl C ON SC.clubName = C.clubName
ORDER BY C.clubName;

# 동아리에 가입하지 않은 학생도 출력되고, 학생이 한명도 없는 동아리도 출력한다.
SELECT S.stdName, S.addr, C.clubName, C.roomNo
FROM stdtbl S 
LEFT OUTER JOIN stdClubTbl SC ON S.stdName = SC.stdName
LEFT OUTER JOIN clubTbl C ON SC.clubName = C.clubName
UNION
SELECT S.stdName, S.addr, C.clubName, C.roomNo
FROM stdtbl S
LEFT OUTER JOIN stdclubTbl SC ON SC.stdName = S.stdName
RIGHT OUTER JOIN clubTbl C ON SC.clubName = C.clubName;

# CROSS JOIN
use employees;
SELECT count(*) AS '데이터개수'
FROM employees CROSS JOIN titles; # 30만건 * 40만건 = 12억건

# SELF JOIN
use sqldb;
CREATE TABLE empTbl(
	emp CHAR(3),
    manager CHAR(3),
    empTel VARCHAR(8)
);

INSERT INTO empTbl VALUES
('나사장', NULL, '0000'),
('김재무', '나사장', '2222'),
('김부장', '김재무', '2222-1'),
('이부장', '김재무', '2222-2'),
('우대리', '이부장', '2222-2-1'),
('지사원', '이부장', '2222-2-2'),
('이영업', '나사장', '1111'),
('한과장', '이영업', '1111-1'),
('최정보', '나사장', '3333'),
('윤차장', '최정보', '3333-1'),
('이주임', '윤차장', '3333-1-1');
COMMIT;

SELECT A.emp AS '부하직원', B.emp AS '직속상관', B.empTel AS '직속상관연락처'
FROM empTbl A INNER JOIN empTbl B ON A.manager = B.emp
WHERE A.emp = '우대리';

# UNION
USE sqldb;
SELECT stdName, addr FROM stdtbl
UNION
SELECT clubName, roomNo FROM clubtbl;

USE sqldb;
SELECT stdName, addr FROM stdtbl
UNION ALL
SELECT clubName, roomNo FROM clubtbl;

# NOT IN, IN
SELECT name, CONCAT(mobile1, mobile2) AS '전화번호'
FROM usertbl
WHERE name NOT IN (SELECT name FROM usertbl WHERE mobile1 IS NULL);

SELECT name, CONCAT(mobile1, mobile2) AS '전화번호'
FROM usertbl
WHERE name IN (SELECT name FROM usertbl WHERE mobile1 IS NULL);

# SQL 프로그래밍
# IF...ELSE
DROP PROCEDURE IF EXISTS ifProc; -- 기존에 만든적이 있다면 삭제합니다.
DELIMITER $$
CREATE PROCEDURE ifProc()
BEGIN
	DECLARE var1 INT; -- var1 변수 선언
    SET var1 = 100; -- 변수에 값 대입
    
    IF var1 = 100 THEN -- 만약 @var1이 100이라면,
		SELECT '100입니다.';
	ELSE
		SELECT '100이 아닙니다.';
    END IF;
END $$
DELIMITER ;
CALL ifProc();

DROP PROCEDURE IF EXISTS ifProc2;
USE employees;

DELIMITER $$
CREATE PROCEDURE ifProc2()
BEGIN
	DECLARE hireDate DATE; -- 입사일
    DECLARE curDATE DATE; -- 오늘
    DECLARE days INT; -- 근무한 일수
    
    SELECT hire_date INTO hireDATE -- hire_date열의 결과를 hireDATE에 대입
    FROM employees.employees
    WHERE emp_no = 10001;
    
    SET curDATE = CURRENT_DATE(); -- 현재 날짜
    SET days = DATEDIFF(curDATE, hireDATE); -- 날자의 차이, 일 단위
    
    IF(days / 365) >= 5 THEN
		SELECT CONCAT('입사한지 ', days, '일이나 지났습니다. 축하합니다.!');
    ELSE
		SELECT '입사한지 ' + days + '일 밖에 안되었네요. 열심히 일하세요.!';
    END IF;
END $$
DELIMITER ;
CALL ifProc2();

DROP PROCEDURE IF EXISTS ifProc3;
DELIMITER $$
CREATE PROCEDURE ifProc3()
BEGIN
	DECLARE point INT;
    DECLARE credit CHAR(1);
    SET point = 77;
    
    IF point >=90 THEN
		SET credit = 'A';
	ELSEIF point >= 80 THEN
		SET credit = 'B';
	ELSEIF point >= 70 THEN
		SET credit = 'C';
	ELSEIF point >= 60 THEN
		SET credit = 'D';
	ELSE
		SET credit = 'F';
	END IF;
    
    SELECT CONCAT('취득 점수=',point, '학점=',credit);
END $$
DELIMITER ;
CALL ifProc3();

DROP PROCEDURE IF EXISTS caseProc;
DELIMITER $$
CREATE PROCEDURE caseProc()
BEGIN
	DECLARE point INT;
    DECLARE credit CHAR(1);
    SET point = 77;
    
    CASE
		WHEN point >= 90 THEN
			SET credit = 'A';
		WHEN point >= 80 THEN
			SET credit = 'B';
		WHEN point >= 70 THEN
			SET credit = 'C';
		WHEN point >= 60 THEN
			SET credit = 'D';
		ELSE
			SET credit = 'F';
	END CASE;
    
    SELECT CONCAT('취득 점수=',point, '학점=',credit);
END $$
DELIMITER ;
CALL caseProc();

# 실습7
USE sqldb;
SELECT U.userID, U.name, SUM(price * amount)AS '총구매액',
	CASE
		WHEN (SUM(price * amount) >= 1500) THEN '최우수고객'
        WHEN (SUM(price * amount) >= 1000) THEN '우수고객'
        WHEN (SUM(price * amount) >= 1) THEN '일반고객'
        ELSE '유령고객'
    END AS '고객등급'

FROM buytbl B RIGHT OUTER JOIN usertbl U ON B.userID = U.userID
GROUP BY U.userID, U.name
ORDER BY SUM(price * amount) DESC;

# WHILE과 ITERATE/LEAVE
DROP PROCEDURE IF EXISTS whileProc;
DELIMITER $$
CREATE PROCEDURE whileProc()
BEGIN
	DECLARE i INT; -- 1에서 100까지 증가할 변수
    DECLARE hap INT; -- 더한 값을 누적할 변수
    
    SET i = 1;
    SET hap = 0;
    
	WHILE(i <= 100) DO
		SET hap = hap + i;
        SET i = i + 1;
    END WHILE;
    
    SELECT hap;
END $$
DELIMITER ;
CALL whileProc();

DROP PROCEDURE IF EXISTS whileProc2;
DELIMITER $$
CREATE PROCEDURE whileProc2()
BEGIN
	DECLARE i INT; -- 1에서 100까지 증가할 변수
    DECLARE hap INT; -- 더한 값을 누적할 변수
    
    SET i = 1;
    SET hap = 0;
    
	myWhile: WHILE(i <= 100) DO
		IF (i % 7 = 0) THEN
			SET i = i + 1;
            ITERATE myWhile; -- 지정한 label문으로 가서 계속 진행
		END IF;
        
		SET hap = hap + i;
        
        IF (hap > 1000) THEN
			LEAVE myWhile; -- 지정한 label문을 떠남, 즉 while문 종료
        END IF;
        
        SET i = i + 1;
    END WHILE;
    
    SELECT hap;
END $$
DELIMITER ;
CALL whileProc2();

DROP PROCEDURE IF EXISTS whileProc3;
DELIMITER $$
CREATE PROCEDURE whileProc3()
BEGIN
	DECLARE i INT; -- 1에서 100까지 증가할 변수
    DECLARE hap INT; -- 더한 값을 누적할 변수
    
    SET i = 1;
    SET hap = 0;
    
	myWhile: WHILE(i <= 1000) DO
		IF (i % 3 = 0 OR i % 8 = 0) THEN
			SET hap = hap + i;
			SET i = i + 1;
            ITERATE myWhile; -- 지정한 label문으로 가서 계속 진행
		END IF;
        SET i = i + 1;
    END WHILE;
    
    SELECT hap;
END $$
DELIMITER ;
CALL whileProc3();

# 오류 처리
DROP PROCEDURE IF EXISTS errorProc;
DELIMITER $$
CREATE PROCEDURE errorProc()
BEGIN
	DECLARE CONTINUE HANDLER FOR 1146 SELECT '테이블이 없습니다.' AS '메시지';
    SELECT * FROM noTable; -- noTable은 없습니다.
END $$
DELIMITER ;
CALL errorProc();

DROP PROCEDURE IF EXISTS errorProc2;
DELIMITER $$
CREATE PROCEDURE errorProc2()
BEGIN
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
    BEGIN
		SHOW ERRORS; -- 오류 메시지를 보여줍니다.
        SELECT '오류가 발생했습니다. 작업은 취소합니다.' AS '메시지';
        ROLLBACK; -- 오류 발생시 작업을 롤백시킵니다.
    END;
    
    INSERT INTO usertbl VALUES('LSG', '이상구', 1988, '서울', NULL, NULL, 170, CURRENT_DATE()); -- 중복되는 아이디이므로 오류가 발생합니다.
END $$
DELIMITER ;
CALL errorProc2();



# 동적 SQL
USE sqldb;
PREPARE myQuery FROM 'SELECT * FROM usertbl WHERE userID = "EJW"';
EXECUTE myQuery;
DEALLOCATE PREPARE myQuery;

USE sqldb;
DROP TABLE IF EXISTS myTable;
CREATE TABLE myTable(
	id INT AUTO_INCREMENT PRIMARY KEY,
    mDate DATETIME
);
SET @curDATE = CURRENT_TIMESTAMP();

PREPARE myQuery FROM 'INSERT INTO myTable VALUES(NULL, ?)';
EXECUTE myQuery USING @curDATE;
DEALLOCATE PREPARE myQuery;

SELECT * FROM myTable;


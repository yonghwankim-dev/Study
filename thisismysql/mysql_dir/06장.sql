CREATE DATABASE sqldb;
USE sqldb;
create TABLE usertbl -- 회원 테이블
(
	userID		CHAR(8) NOT NULL PRIMARY KEY, -- 사용자 아이디(PK)
    name		VARCHAR(10) NOT NULL, -- 이름
    birthYear 	INT NOT NULL, -- 출생년도
    addr		CHAR(2) NOT NULL, -- 지역경기, 서울, 경남 식으로 2글자만 입력
    mobile1		CHAR(3), -- 휴대폰의 국번011, 016, 017, 018, 019, 010 등
    mobile2 	CHAR(8), -- 휴대폰의 나머지 전화번호(하이폰 제외)
    height		SMALLINT, -- 키
    mDate		DATE -- 회원 가입일
);

create TABLE buytbl -- 회원 구매 테이블(Buy Table의 약자)
(
	num	INT AUTO_INCREMENT NOT NULL PRIMARY KEY, -- 순번(PK)
    prodName CHAR(6) NOT NULL, -- 물품명
    groupName CHAR(4), -- 분류
    price INT NOT NULL, -- 단가
    amount SMALLINT NOT NULL, -- 수량
    userID CHAR(8) NOT NULL, -- 아이디(FK)
    FOREIGN KEY (userID) REFERENCES usertbl(userID)
);

insert into usertbl values('LSG', '이승기', 1987, '서울', '011', '1111111', 182, '2008-8-8');
insert into usertbl values('KBS', '김범수', 1979, '경남', '011', '2222222', 173, '2012-4-4');
insert into usertbl values('KKH', '김경호', 1971, '전남', '019', '3333333', 177, '2007-7-7');
insert into usertbl values('JYP', '조용필', 1950, '경기', '011', '4444444', 166, '2009-4-4');
insert into usertbl values('SSK', '성시경', 1979, '서울', null  , null      , 186, '2013-12-12');
insert into usertbl values('LJB', '임재범', 1963, '서울', '016', '6666666', 182, '2009-9-9');
insert into usertbl values('YJS', '윤종신', 1969, '경남', null  , null      , 170, '2005-5-5');
insert into usertbl values('EJW', '은지원', 1972, '경북', '011', '8888888', 174, '2014-3-3');
insert into usertbl values('JKW', '조관우', 1965, '경기', '018', '9999999', 172, '2010-10-10');
insert into usertbl values('BBK', '바비킴', 1973, '서울', '010', '0000000', 176, '2013-5-5');
insert into buytbl values(null, 'KBS', '운동화', null   , 30,   2);
insert into buytbl values(null, 'KBS', '노트북', '전자', 1000, 1);
insert into buytbl values(null, 'JYP', '모니터', '전자', 200,  1);
insert into buytbl values(null, 'BBK', '모니터', '전자', 200,  5);
insert into buytbl values(null, 'KBS', '청바지', '의류', 50,   3);
insert into buytbl values(null, 'BBK', '메모리', '전자', 80,  10);
insert into buytbl values(null, 'SSK', '책'    , '서적', 15,   5);
insert into buytbl values(null, 'EJW', '책'    , '서적', 15,   2);
insert into buytbl values(null, 'EJW', '청바지', '의류', 50,   1);
insert into buytbl values(null, 'BBK', '운동화', null   , 30,   2);
insert into buytbl values(null, 'EJW', '책'    , '서적', 15,   1);
insert into buytbl values(null, 'BBK', '운동화', null   , 30,   2);

USE sqldb;
select * from usertbl;

select * from usertbl where name='김경호';

select * from usertbl where height >=182 and birthYear >= 1970;

select * from usertbl where height >=182 or birthYear >= 1970;

select * from usertbl where height between 180 and 183;

select * from usertbl where addr in ('경남', '전남', '경북');

select * from usertbl where name like '김%';

select * from usertbl where name like '_종신';

select * from usertbl where height > 177;
select * from usertbl where height > (select height from usertbl where Name = '김경호');

select * from usertbl where height > (select height from usertbl where addr = '경남');
select * from usertbl where height > any (select height from usertbl where addr = '경남');
select * from usertbl where height > all (select height from usertbl where addr = '경남');
select * from usertbl where height = any (select height from usertbl where addr = '경남');
select * from usertbl where height in (select height from usertbl where addr = '경남');

select * from usertbl order by mDate;
select * from usertbl order by mDate desc;
select * from usertbl order by height desc, name asc;

select addr from usertbl;
select addr from usertbl order by addr;
select distinct addr from usertbl;

use employees;
select * from employees order by hire_date;
select emp_no, hire_date from employees order by hire_date LIMIT 5;
select emp_no, hire_date from employees order by hire_date LIMIT 0, 5;
select emp_no, hire_date from employees order by hire_date LIMIT 5 OFFSET 0;

use sqldb;
create TABLE buytbl2 (select * from buytbl);
select * from buytbl2;

create TABLE buytbl3 (select userID, prodName from buytbl);
select * from buytbl3;

use sqldb;
select userID, amount from buytbl order by userID;
select userID as '사용자 아이디', sum(amount) AS '총 구매 개수' FROM buytbl GROUP BY userID ORDER BY userID;
select userID as '사용자 아이디', sum(amount * price) AS '총 구매액' FROM buytbl GROUP BY userID ORDER BY userID;

use sqldb;
select avg(amount) as '평균 구매 개수' FROM buytbl;

select userId, avg(amount) as '평균 구매 개수' FROM buytbl group by userID;

select name, max(height), min(height) from usertbl;
select name, max(height), min(height) from usertbl group by name;

select name, height
from usertbl
where height = (select max(height) from usertbl) or
height = (select min(height) from usertbl);

select count(*) as '휴대폰이 있는 사용자' FROM usertbl;
select count(mobile1) as '휴대폰이 있는 사용자' FROM usertbl;

use sqldb;
select userID as '사용자', SUM(amount * price) as '총 구매금액'
FROM buytbl
GROUP BY userID
HAVING SUM(amount * price) > 1000
ORDER BY SUM(price * amount);

select num, groupName, sum(price * amount) as '비용'
FROM buytbl
group by groupName, num
with ROLLUP;

select groupName, sum(price * amount) as '비용'
FROM buytbl
group by groupName
with ROLLUP;

USE sqldb;
create TABLE testTbl1 (id int, userName char(3), age int);
insert into testTbl1 values (1, '홍길동', 25);

insert into testTbl1(id, userName) values(2, '설현');
insert into testTbl1(userName, age, id) values('하니', 20, 3);

USE sqldb;
create TABLE testTbl2(
	id int AUTO_INCREMENT PRIMARY KEY,
    userName char(3),
    age int
);
insert into testTbl2 values (null, '지민', 25);
insert into testTbl2 values (null, '유니', 22);
insert into testTbl2 values (null, '유경', 21);
select * from testTbl2;

select LAST_INSERT_ID() from DUAL;

alter table testTbl2 AUTO_INCREMENT=100;
insert into testTbl2 values (null, '찬미', 23);
select * from testTbl2;

USE sqldb;
create TABLE testTbl3(
	id int AUTO_INCREMENT PRIMARY KEY,
    userName char(3),
    age int
);
alter table testTbl3 AUTO_INCREMENT=1000;
SET @@auto_increment_increment=3;
insert into testTbl3 values (null, '지민', 25);
insert into testTbl3 values (null, '유니', 22);
insert into testTbl3 values (null, '유경', 21);
select * from testTbl3;

USE sqldb;
create TABLE testTbl4 (
	id int,
    Fname varchar(50),
    Lname varchar(50)
);
insert into testTbl4
select emp_no, first_name, last_name
from employees.employees;

USE sqldb;
create TABLE testTbl5 (select emp_no, first_name, last_name from employees.employees);

-- USE sqldb;
-- UPDATE testTbl4 SET Lname = '없음' WHERE Fname = 'Kyoichi';
-- UPDATE buytbl SET price = price * 1.5;

USE sqldb;
-- DELETE FROM testTbl4 WHERE Fname = 'Aamer';
delete from testTbl4 where Fname = 'Aamer' LIMIT 5;

USE sqldb;
create TABLE bigTbl1 (select * from employees.employees);
create TABLE bigTbl2 (select * from employees.employees);
create TABLE bigTbl3 (select * from employees.employees);

set sql_safe_updates=0;
delete from bigTbl1;
drop table bigTbl2;
truncate table bigTbl3;

USE sqldb;
create TABLE memberTBL (select userID, name, addr from usertbl LIMIT 3);
alter table memberTBL add CONSTRAINT pk_memberTBL PRIMARY KEY (userID); -- PK를 저장함
select * from memberTBL;

insert into memberTBL values('BBK', '비비코', '미국'); -- 중복
insert into memberTBL values('SJH', '서장훈', '서울');
insert into memberTBL values('HJY', '현주엽', '경기');

insert IGNORE INTO memberTBL VALUES('BBK', '비비코', '미국');
insert IGNORE INTO memberTBL VALUES('SJH', '서장훈', '서울');
insert IGNORE INTO memberTBL VALUES('HJY', '현주엽', '경기');
select * from memberTBL;

insert into memberTBL values('BBK', '비비코', '미국')
ON DUPLICATE KEY update name='비비코', addr='미국';
insert into memberTBL values('DJM', '동짜몽', '일본')
ON DUPLICATE KEY update name='동짜몽', addr='일본';
select * from memberTBL;

select userID as '사용자', SUM(price * amount) AS '총구매액'
FROM buyTBL GROUP BY userID;

with abc(userid, total)
as
(select userID, sum(price * amount)
from buyTBL group by userID)
select * from abc order by total desc;

select addr, max(height) from userTbl group by addr;

with cte_userTBL(addr, maxHeight)
as
(select addr, max(height) from userTbl group by addr)
select avg(maxHeight*1.0) as '각 지역별 최고키의 평균' FROM cte_userTBL;

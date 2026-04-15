USE sqldb;
CREATE TABLE tbl1
(
	a INT PRIMARY KEY,
    b INT,
    c INT
);
SHOW INDEX FROM tbl1;
delete from tbl1;

CREATE TABLE tbl2
(
	a INT PRIMARY KEY,
    b INT UNIQUE,
    c INT UNIQUE
);
SHOW INDEX FROM tbl2;

CREATE TABLE tbl3
(
	a INT UNIQUE,
    b INT UNIQUE,
    c INT UNIQUE,
    d INT
);
SHOW INDEX FROM tbl3;

CREATE TABLE tbl4
(
	a INT UNIQUE NOT NULL,
    b INT UNIQUE,
    c INT UNIQUE,
    d INT
);
SHOW INDEX FROM tbl4;

CREATE TABLE tbl5
(
	a INT UNIQUE NOT NULL,
    b INT UNIQUE,
    c INT UNIQUE,
    d INT PRIMARY KEY
);
SHOW INDEX FROM tbl5;

CREATE DATABASE IF NOT EXISTS testdb;
USE testdb;
DROP TABLE IF EXISTS usertbl;
CREATE TABLE usertbl
(
	userID CHAR(8) NOT NULL PRIMARY KEY,
	name VARCHAR(10) NOT NULL,
	birthYear INT NOT NULL,
	addr NCHAR(2) NOT NULL
);

INSERT INTO usertbl VALUES('LSG', '이승기', 1987, '서울');
INSERT INTO usertbl VALUES('KBS', '김범수', 1979, '경남');
INSERT INTO usertbl VALUES('KKH', '김경호', 1971, '전남');
INSERT INTO usertbl VALUES('JYP', '조용필', 1950, '경기');
INSERT INTO usertbl VALUES('SSK', '성시경', 1979, '서울');
SELECT * FROM usertbl;

ALTER TABLE usertbl DROP PRIMARY KEY;
ALTER TABLE usertbl ADD CONSTRAINT pk_name PRIMARY KEY(name);
SELECT * FROM usertbl;

create database if not exists testdb;
use testdb;
drop table if exists clustertbl;
create table clustertbl
(
	userID char(8),
    name varchar(10)
);
insert into clustertbl values
('LSG', '이승기'),
('KBS', '김범수'),
('KKH', '김경호'),
('JYP', '조용필'),
('SSK', '성시경'),
('LJB', '임재범'),
('YJS', '윤종신'),
('EJW', '은지원'),
('JKW', '조관우'),
('BBK', '바비킴');
show variables like 'innodb_page_size'; # 16384 = 16KB

select * from clustertbl;

alter table clustertbl add constraint PK_clustertbl_userID primary key (userID);
select * from clustertbl;

create database if not exists testdb;
use testdb;
drop table if exists mixedtbl;
create table mixedtbl
(
	userID char(8) not null,
    name varchar(10) not null,
    addr char(2)
);
insert into mixedtbl values
('LSG', '이승기', '서울'),
('KBS', '김범수', '경남'),
('KKH', '김경호', '전남'),
('JYP', '조용필', '경기'),
('SSK', '성시경', '서울'),
('LJB', '임재범', '서울'),
('YJS', '윤종신', '경남'),
('EJW', '은지원', '경북'),
('JKW', '조관우', '경기'),
('BBK', '바비킴', '서울');

alter table mixedtbl
add constraint pk_mixedtbl_userid primary key (userID);

alter table mixedtbl
add constraint uk_mixedtbl_name unique (name);

use sqldb;
select * from usertbl;

use sqldb;
show index from usertbl;

show table status like 'usertbl';

create index idx_usertbl_addr on usertbl (addr);
show index from usertbl;
show table status like 'usertbl';

analyze table usertbl;
show table status like 'usertbl';

create unique index idx_usertbl_birthYear
on usertbl (birthYear);

create unique index idx_usertbl_name
on usertbl (name);
show index from usertbl;

insert into usertbl values('GPS', '김범수', 1983, '미국', NULL, NULL, 162, NULL);

create index idx_usertbl_name_birthYear
on usertbl (name, birthYear);
drop index idx_usertbl_name on usertbl;
show index from usertbl;

select * from usertbl where name = '윤종신' and birthYear = '1969';

create index idx_usertbl_mobile1
on usertbl (mobile1);

select * from usertbl where mobile1 = '011';

show index from usertbl;

drop index idx_usertbl_addr on usertbl;
drop index idx_usertbl_name_birthYear on usertbl;
drop index idx_usertbl_mobile1 on usertbl;

create database if not exists indexdb;
use indexdb;
select count(*) from employees.employees;

create table emp select * from employees.employees order by rand();
create table emp_c select * from employees.employees order by rand();
create table emp_Se select * from employees.employees order by rand();

select * from emp limit 5;
select * from emp_c limit 5;
select * from emp_Se limit 5;

show table status;

alter table emp_c add primary key (emp_no);
alter table emp_Se add index idx_emp_no (emp_no);
select * from emp limit 5;
select * from emp_c limit 5;
select * from emp_Se limit 5;

analyze table emp, emp_c, emp_Se;

show index from emp;
show index from emp_c;
show index from emp_Se;
show table status;

use indexdb;
show global status like 'Innodb_pages_read';
select * from emp where emp_no = 100000;
show global status like 'Innodb_pages_read';

show global status like 'Innodb_pages_read';
select * from emp_c where emp_no = 100000;
show global status like 'Innodb_pages_read';

show global status like 'Innodb_pages_read';
select * from emp_Se where emp_no = 100000;
show global status like 'Innodb_pages_read';

use indexdb;
show global status like 'Innodb_pages_read';
select * from emp where emp_no < 11000;
show global status like 'Innodb_pages_read';

show global status like 'Innodb_pages_read';
select * from emp_c where emp_no < 11000;
show global status like 'Innodb_pages_read';

show global status like 'Innodb_pages_read';
select * from emp_c limit 1000000;
show global status like 'Innodb_pages_read';

show global status like 'Innodb_pages_read';
select * from emp_Se where emp_no < 11000;
show global status like 'Innodb_pages_read';

show global status like 'Innodb_pages_read';
explain select * from emp_Se ignore index(idx_emp_no) where emp_no < 11000;
show global status like 'Innodb_pages_read';

show global status like 'Innodb_pages_read';
select * from emp_c where emp_no*1 = 100000;
show global status like 'Innodb_pages_read';

select * from emp_c where emp_no = 100000 / 1;

select * from emp;
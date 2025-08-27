
## SQL 생성해보기 - MYSQL
SQL 서비스로 이동, "인스턴스 만들기" 버튼을 클릭하여 인스턴스를 생성합니다.
![](imgs/Pasted%20image%2020250825153105.png)
![](imgs/Pasted%20image%2020250825155131.png)

데이터베이스 엔진 선택
- MySQL 선택
![](imgs/Pasted%20image%2020250825155207.png)

MySQL 인스턴스 생성
![](imgs/Pasted%20image%2020250825155611.png)
![](imgs/Pasted%20image%2020250825155621.png)

![](imgs/Pasted%20image%2020250825160603.png)

![](imgs/Pasted%20image%2020250825160710.png)

위 설정에서 "연결 설정" 버튼을 클릭합니다.
- API 사용 설정 클릭
![](imgs/Pasted%20image%2020250825160753.png)

![](imgs/Pasted%20image%2020250825162406.png)

![](imgs/Pasted%20image%2020250825162415.png)

VPC 네트워크의 비공개 서비스 액세스에서 생성확인
![](imgs/Pasted%20image%2020250825162723.png)

승인된 네트워크 새 네트워크 추가
- 모든 IP 주소가 인스턴스에 액세스 될 수 있음
- 회사 또는 개인 집에서라면 개인 IP를 사용하는 것을 권장
- 해당 IP는 테스트를 위해서 다음과 같이 추가함
![](imgs/Pasted%20image%2020250825162834.png)

데이터 보호에서 자동 일일 백업 해제
- 테스트용이기 때문에 일일 백업하지 않음
![](imgs/Pasted%20image%2020250825163011.png)

![](imgs/Pasted%20image%2020250825163055.png)

MySQL 인스턴스 생성 확인
![](imgs/Pasted%20image%2020250825163804.png)

MySQL 인스턴스 접속
```shell
mysql -uroot -p -h 34.63.62.191
```
![](imgs/Pasted%20image%2020250825163920.png)
- 위 실행 결과를 보면 정상적으로 접속되었음

## 데이터베이스 만들어보기
### 데이터베이스 생성
데이터베이스를 생성하기 위해서 SQL -> 데이터베이스 메뉴로 접근합니다.
![](imgs/Pasted%20image%2020250826113516.png)

데이터베이스 만들기 버튼 클릭
![](imgs/Pasted%20image%2020250826113935.png)

데이터베이스 생성 정보 입력
![](imgs/Pasted%20image%2020250826114257.png)
- utf8mb4를 사용하면 한영문자 및 추가적인 특수기호 입력 가능

test 데이터베이스 생성 확인
![](imgs/Pasted%20image%2020250826115657.png)

### 데이터베이스 사용자 생성
SQL -> 사용자 메뉴로 접근
![](imgs/Pasted%20image%2020250826115732.png)

현재는 root 계정만이 존재합니다. 사용자 계정 추가 버튼을 클릭합니다.
![](imgs/Pasted%20image%2020250826115741.png)

사용자 생성 정보 입력
![](imgs/Pasted%20image%2020250826120559.png)

데이터베이스 사용자 생성 확인
![](imgs/Pasted%20image%2020250826120909.png)

Cloud Shell을 통해서 접근
- 데이터베이스 조회 결과 test 스키마가 존재하는 것을 볼수 있음
```shell
mysql -utester -p{password} -h 34.63.62.191
```
![](imgs/Pasted%20image%2020250826121345.png)

test 스키마 데이터베이스 사용
```mysql
use test
```

테이블 조회
- 아무것도 없음
```mysql
show tables;
```
![](imgs/Pasted%20image%2020250826121551.png)

## DB 데이터 이관해보기
DB 데이터 이관 실습을 위해서 다음과 같이 진행됨
- Docker가 미리 설치되어 있는 Cloud Shell을 이용하여 MySQL 컨테이너 생성 및 데이터 삽입
- 컨테이너에서 Cloud SQL에 있는 test 데이터베이스로 DB 데이터 이관함
- dump 추출후 dump를 Google Cloud Storage에 저장후 데이터 이관 작업을 진행함

### docker-compose 작성 및 실행

docker-compose로 mysql 실행하기
```yaml
# docker-compose.yml
version: "3"
services:
  local-db:
    image: mysql:8.0  # 이미지
    container_name: local-db
    restart: always
    ports:
      - 13306:3306
    environment:
      MYSQL_ROOT_PASSWORD: password1234@
      TZ: Asia/Seoul
    volumes:
      - /home/fineants_co_2024/volume:/var/lib/mysql
    command:
      - --character-set-server=utf8mb4 # 인코딩
      - --collation-server=utf8mb4_unicode_ci
```

docker-compose 실행
```shell
docker-compose up -d
```


mysql 컨테이너 생성 확인
![](imgs/Pasted%20image%2020250826125816.png)


### 데이터베이스 생성 및 샘플 데이터 삽입

mysql 컨테이너 접속
```shell
docker exec -it local-db mysql -uroot -ppassword1234@
```
![](imgs/Pasted%20image%2020250826125858.png)

데이터베이스 스키마 생성
```mysql
create database test;
```
![](imgs/Pasted%20image%2020250826125921.png)

테이블 생성
```mysql
use test
create table user( 
	id bigint auto_increment primary key, 
	name varchar(255) null 
);
```
![](imgs/Pasted%20image%2020250826130020.png)

샘플 데이터 삽입
```mysql
insert into user (id,name) values(1, "test1"); 
insert into user (id,name) values(2, "test2"); 
insert into user (id,name) values(3, "test3");
```
![](imgs/Pasted%20image%2020250826130056.png)

mysql 쉘 종료
```msqyl
exit
```
![](imgs/Pasted%20image%2020250826130606.png)

### SQL 덤프 생성하기
Cloud Shell에서 MySQL 컨테이너에서 실행중인 test 스키마에 대한 MySQL 덤프를 생성합니다.
- 실행 결과를 보면 test.sql 파일이 생성됨
- 우리는 test.sql 덤프 파일을 추출하여 Cloud SQL에 실행하도록 합니다.
```shell
docker exec local-db sh -c 'exec mysqldump -uroot -ppassword1234@ test ' > $HOME/test.sql
```
![](imgs/Pasted%20image%2020250826130740.png)

### SQL 덤프 파일 추출하기
Cloud Shell에서 더보기란을 통하여 다운로드 버튼을 클릭합니다.
![](imgs/Pasted%20image%2020250826132028.png)

test.sql 덤프 파일 다운로드
![](imgs/Pasted%20image%2020250826132046.png)


Cloud Stroage 서비스 이동
![](imgs/Pasted%20image%2020250826132753.png)

버킷 메뉴로 이동, "만들기" 버튼을 클릭하여 버킷을 생성합니다.
![](imgs/Pasted%20image%2020250826132806.png)

버킷 생성 정보 입력
![](imgs/Pasted%20image%2020250826133624.png)

![](imgs/Pasted%20image%2020250826133644.png)

![](imgs/Pasted%20image%2020250826133743.png)

![](imgs/Pasted%20image%2020250826133802.png)

![](imgs/Pasted%20image%2020250826133815.png)

버킷 생성 확인
![](imgs/Pasted%20image%2020250826133827.png)

버킷에 MySQL 덤프 파일 업로드
![](imgs/Pasted%20image%2020250826133907.png)

### Cloud SQL에서 덤프 파일을 이용한 데이터 이관하기
Cloud SQL 서비스로 이동합니다. gcp-share-mysql-01 인스턴스 상세 페이지로 이동합니다.
![](imgs/Pasted%20image%2020250826134614.png)

가져오기 버튼을 클릭
![](imgs/Pasted%20image%2020250826134651.png)

 데이터 가져오기 정보 입력
 - 소스 파일 선택시 버킷에 있는 test.sql 파일을 선택
 - 스키마 선택시 test 스키마 데이터베이스 선택
 ![](imgs/Pasted%20image%2020250826134755.png)

gcp-share-mysql-01 데이터베이스 접속
```shell
mysql -utester -p{password} -h 34.63.62.191
```

user 테이블의 데이터 확인
- 실행 결과를 보면 정상적으로 3개의 샘플 데이터가 삽입된 것을 볼수 있음
```mysql
use test
show tables;
select * from user;
```
![](imgs/Pasted%20image%2020250826135032.png)



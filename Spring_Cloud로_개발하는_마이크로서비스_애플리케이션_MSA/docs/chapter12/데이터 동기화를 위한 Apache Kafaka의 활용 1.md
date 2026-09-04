
## 섹션 소개
- Kafka 개요
- Kafka 설치
- Kafka Producer/Consumer
- Kafka Connect

## Apache Kafka
### Apache Kafka는 무엇인가?
- Apache Software Foundation의 스칼라(Scalar) 언어로 된 오픈 소스 메시지 브로커 프로젝트
	- Open Source Message Broker Project
- Linked-In에서 개발, 2011년 오픈 소스화
- 실시간 데이터 피드를 관리하기 위해 통일된 높은 처리량, 낮은 지연 시간을 지닌 플랫폼 제공
- 다양한 테크 기업들에서 사용하고 있음

Apache Kafka가 나오기 이전 아키텍처 모습
![](../imgs/Pasted%20image%2020251112154154.png)
- End-to-End 연결 방식의 아키텍처
- 데이터 연동의 복잡성 증가 (HW, OS, 장애 등)
- 서로 다른 데이터 파이프라인(Pipeline) 연결 구조
- 확장이 어려운 구조

MySQL, Oracle과 같은 데이터 저장소가 Hadoop, Search Engine과 같은 시스템에 데이터를 맞추어 전달합니다.

Kafka 탄생 배경
- 모든 시스템으로 데이터를 실시간으로 전송하여 처리할 수 있는 시스템이 요구됨
- 데이터가 많아져도 확장이 용이한 시스템이 요구됨

카프카를 도입하여 데이터 저장소에서 카프카로 데이터 전송하면 Kafka에서 시스템으로 데이터를 맞추어 전송한다.
데이터 저장소 및 데이터를 전송받는 시스템에서는 누가 보내는지, 누가 받는지 신경쓰지 않아도 됩니다.
![](../imgs/Pasted%20image%2020251112154731.png)

Kafka 도입시 효과
- Producer/Consumer 분리
- 메시지를 여러 Consumer에게 허용
- 높은 처리량을 위한 메시지 최적화
- Scale-out 가능
- Eco-System

### Kafka Broker
- Kafka Broker는 실행된 Kafka 애플리케이션 서버
- 3대 이상의 브로커로 구성된 Broker Cluster를 구성하는 것을 권장함
- Zookeeper 연동
	- 브로커들의 리더
	- 역할: 메타 데이터(Brodker ID, Controller ID 등) 저장
	- Controller 정보 저장
- n개 Brodker 중 1대는 Controller 기능을 수행함
	- Controller 역할
		- 각 Brodker에게 담당 파티션 할당 수행
		- Broker 정상 동작 모니터링 관리

![](../imgs/Pasted%20image%2020251112155707.png)

## Apache Kafka 설치
### docker 기반 kafka 설치
docker 이미지 기반으로 kafka 설치
- https://hub.docker.com/layers/apache/kafka/4.0.0/images/sha256-01b9a4030e54c6068e66eb3ba4cb82c0d89238629ef1c30d79b86036bf89b1b7

kafka broker 시작
```shell
docker run -d --name broker -p 9092:9092 apache/kafka:latest
```

brodker 컨테이너에 쉘 접속
```shell
docker exec --workdir /opt/kafka/bin/ -it broker sh
```


> [!NOTE] Kafka Zookeepr Deprecated
> Kafka 3.5부터 Zookeeper는 Deprecated되었습니다. 그리고 4.0 이후부터는 완전히 제거되었습니다. Zookeeper를 제거하고 Kafka에 메타데이터를 저장해서 전체 아키텍처가 단순해지도록 함. 이를 통해 관리 및 모니터링을 더 쉽게 만들어 진다고 합니다. 따라서 메타 데이터 관리를 위해서 Kafka Raft가 나오고 이를 통해 Zookeeper보다 효율적으로 관리합니다.

### MacOS kafka 설치
kafka 설치 링크
- https://dlcdn.apache.org/kafka/4.1.1/kafka_2.13-4.1.1.tgz

kafka 설치
```shell
curl -O https://dlcdn.apache.org/kafka/4.1.1/kafka_2.13-4.1.1.tgz
tar -xvf kafka_2.13-4.1.1.taz
cd kafka_2.13-4.1.1
ls
```
![](../imgs/Pasted%20image%2020251116152706.png)

클러스터 ID 생성
```shell
KAFKA_CLUSTER_ID=$($KAFKA_HOME/bin/kafka-storage.sh random-uuid)
echo $KAFKA_CLUSTER_ID
```
![](../imgs/Pasted%20image%2020251116154641.png)

log.dirs 포맷(format)
server.properties 안에는 다음 속성이 있어야 합니다.
- `process.roles=broker,controller`
- `controller.quorum.voters=1@localhost:9093`
	- Kafka 클러스터의 메타데이터를 관리할 컨트롤러 노드 목록
	- nodeId@host:port
- `node.id=1`
- `log.dirs=/tmp/kraft-combined-logs`

```shell
$KAFKA_HOME/bin/kafka-storage.sh format -t $KAFKA_CLUSTER_ID -c $KAFKA_HOME/config/server.properties
```
![](../imgs/Pasted%20image%2020251116155354.png)

행
```shell
$KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server.properties
```

kafka 실행 확인
```shell
nc -zv localhost 9092
```
![](../imgs/Pasted%20image%2020251116155643.png)
## Apache Kafka 사용 - Producer/Consumer
### Kafka Client
- Kafaka와 데이터를 주고받기 위해 사용하는 자바 라이브러리
	- https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients
- Producer, Consumer, Admin, Stream 등 Kafka 관련 API 제공
- 다양한 서드 파티 라이브러리가 존재 : C/C++, Node.js, Python, .NET 등
	- https://cwiki.apache.org/confluence/display/KAFKA/Clients

### Kafka Server 실행
kafka 서버 실행
```shell
$KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server.properties
```

topic 생성
```shell
$KAFKA_HOME/bin/kafka-topics.sh --create --topic quickstart-events --bootstrap-server localhost:9092 --partitions 1
```
![](../imgs/Pasted%20image%2020251116155845.png)
![](../imgs/Pasted%20image%2020251113125212.png)
- partitons : 토픽을 몇개의 분산 저장소(파티션)으로 쪼개서 개설할 것인가에 대한 옵션
	- 예를 들어 partitions 옵션의 값이 1개이면 1차선 도로이고 4개이면 4차선 도로가 된다. 파티션 개수를 늘리면 데이터를 동시에 처리할 수 있는 통로를 넓혀서 병렬 처리 성능을 높힘
	- 파티션을 여러개 지정하면 카프카가 이 파티션들을 여러 서버에 골고루 분산시켜 저장함

topic 목록 확인
```shell
$KAFKA_HOME/bin/kafka-topics.sh --bootstrap-server localhost:9092 --list
```
![](../imgs/Pasted%20image%2020251116155906.png)

topic 정보 확인
```shell
$KAFKA_HOME/bin/kafka-topics.sh --describe --topic quickstart-events --bootstrap-server localhost:9092
```
![](../imgs/Pasted%20image%2020251116155938.png)
- Topic : 토픽의 이름
- TopicId : 토픽의 식별자 값
- PartitionCount : 토픽의 파티션 개수
- ReplicationFactor : 데이터 유실을 막기 위해서 토픽의 파티션들을 몇개의 카프카 서버에 복제하여 보관할 것인가에 대한 숫자
	- 예를 들어 값이 3이면 토픽을 총 3개의 복제본을 서로 다른 카프카 서버에 쪼개서 저장함
- Configs
	- `min.insync.replicas=1` : 이 옵션은 프로듀서가 데이터를 보낼때, "완벽하게 복제가 완료되었다"고 인정할 수 있는 최소한의 서버 개수입니다. 즉, 1개의 서버에 데이터를 무사히 저장했다면 다른 서버들이 복제를 성공했는지 실패햇는지 확인하지 않고 저장 성공이라는 신호를 보냅니다.
	- `segment.bytes` : 카프카 저장용 로그 파일 하나당  최대 크기
		- 현재 결과에서는 1GB 크키가 로그 파일 하나의 최대 크기값 입니다.

### Kafka Producer / Consumer 테스트
메시지 생산
```shell
$KAFKA_HOME/bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic quickstart-events
> Hello, World!
> Hi, there.
```
- `--broker-list` 옵션은 deprecated 되었음. 대체제로 `--bootstrap-server` 사용해야함![](../imgs/Pasted%20image%2020251116160024.png)

메시지 소비
```shell
$KAFKA_HOME/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic quickstart-events --from-beginning
Hello, World!
Hi, there.
```
- `--from-beginning` : 이 옵션은 이 토픽에 저장되었던 데이터(가장 오래된 데이터)부터 누락없이 전부 다 읽어오는 옵션입니다.
	- 이 옵션을 설정하지 않으면 consumer를 실행한 이후의 들어오는 데이터만 실시간으로 받습니다.




메시지 소비 실행 결과를 보면 Producer가 보낸 메시지를 수신받은 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251116160053.png)

다음 실행 결과는 두번째 Consumer를 실행한 결과입니다.
- `--from-beginning` 옵션 설정 덕분에 과거의 메시지도 가져올 수 있습니다.
![](../imgs/Pasted%20image%2020251116160123.png)

다음 실행 결과를 보면 producer가 새로운 메시지를 송신하면 2개의 consumer가 메시지를 수신하는 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251116160153.png)

## Apache Kafka 사용 - Kafka Connect
Kafka Connect 기능
- Kafka Connect를 통해 Data를 Import/Export 가능
- 코드 없이 Configuration으로 데이터를 이동
- Standalone mode, Distribution mode 지원
	- RESTful API를 통해 지원
	- Stream 또는 Batch 형태로 데이터 전송 가능
	- 커스텀 Connector를 통한 다양한 플러그인 제공(File, S3, Hive, MySQL, etc...)


데이터를 가져오는 쪽을 Kafka Connect Source(Import), 데이터를 보내는 쪽을 Kafka Connect Sink(Export)라고 합니다.
다음 그림을 보면 Source System의 데이터를 Kafka Connect Source를 이용해서 가져오고 Kafka Connect Sink를 이용해서 목표로하는 시스템에 데이터를 전송한다.
![](../imgs/Pasted%20image%2020251113134237.png)

### MariaDB 설치 - MacOS
```shell
brew install mariadb
# db 실행
mysql.server start
# db 중지
mysql.server stop
# db 상태 확인
mysql.server status

# db 접속
mysql -uroot

# 데이터베이스 생성
mysql > create database mydb;
```

### docker를 이용한 mariadb 실행
mariadb container 실행
```console
docker run -d \
  --name my-mariadb \
  -e MARIADB_ROOT_PASSWORD=test1234 \
  -p 3306:3306 \
  mariadb:latest
```

mariadb container 쉘 접속
```shell
docker exec -it my-mariadb sh
```

root 계정 접속
```shell
mariadb -u root -p
> test1234
```
![](../imgs/Pasted%20image%2020251113141913.png)

mydb 데이터베이스 생성
```shell
mariadb > create database mydb;
```
![](../imgs/Pasted%20image%2020251113143409.png)

## Orders Mircroservice에서 MariaDB 연동
MariaDB Client로 Order Mircroservice의 H2 Console을 사용합니다.

order-service 프로젝트에 의존성 추가
```xml
<dependency>  
    <groupId>org.mariadb.jdbc</groupId>  
    <artifactId>mariadb-java-client</artifactId>  
    <version>3.5.6</version>  
</dependency>
```

order-service를 실행한 다음에 h2-console을 통하여 mariadb에 접속해봅니다.
- 실행 결과를 보면 성공적으로 접속한 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251113143438.png)

mydb 스키마에 테이블 생성
```sql
create table users(
	id int auto_increment primary key,
	user_id varchar(20) not null,
	pwd varchar(20) not null,
	name varchar(20) not null,
	created_at datetime default NOW()
);
```
![](../imgs/Pasted%20image%2020251113143504.png)

## Kafka Connect 설치
### Kafka Connect 설치 - MacOS
Kafka Connect 설치
```
curl -O https://packages.confluent.io/archive/8.1/confluent-8.1.0.tar.gz
tar xvf confluent-8.1.0.tar.gz
cd $KAFKA_CONNECT_HOME
```

Kafka Connect 설정 (기본으로 사용)
- 해당 경로의 프로퍼티 파일은 기본적으로 존재하지 않습니다. 만약 사용한다면 해당 경로로 직접 생성해서 사용해야 합니다.
- `$KAFKA_HOME`/config/connect-distributed.properties

Kafka Connect 실행
- Kafka Connect 실행전에 카프카 서버와 topic(quickstart-events)을 미리 생성해두었습니다.
```shell
$KAFKA_CONNECT_HOME/bin/connect-distributed $KAFKA_CONNECT_HOME/etc/kafka/connect-distributed.properties
```

Topic 목록 확인
- `$KAFKA_HOME/bin/kafka-topics.sh --bootstrap-server localhost:9092 --list`
- 다음 실행 결과를 보면 kafka connect 관련 topic이 자동으로 생성된 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251117105730.png)

### JDBC Connector 설정 - MacOS
**JDBC Connector 설치**
- https://www.confluent.io/hub/confluentinc/kafka-connect-jdbc
	- confluentinc-kafka-connect-jdbc-10.9.0.zip 다운로드

**Kafka Connector 플러그인 경로 설정**
`$KAFKA_CONNECT_HOME/etc/kafka/connect-distributed.properties` 파일 마지막 아래 plugin 정보 추가
- plugin.path=\[confluentinc-kafka-connect-jdbc-10.9.0 폴더\]
```
plugin.path=/Users/yonghwankim/Documents/tool/kafka/confluentinc-kafka-connect-jdbc-10.9.0/lib
```
![](../imgs/Pasted%20image%2020251117110843.png)

JdbcSourceConnector에서 MariaDB를 사용하기 위해서는 maraidb 드라이버가 필요합니다. maven 저장소에서 mariadb-java-client.jar 파일을 찾도록 하겠습니다.
- 실행 결과를 보면 mariadb-java-client-3.5.6.jar 파일이 존재합니다.
- 해당 jar 파일을 복사하여 Kafka Connect의 카프카 디렉토리에 붙여넣습니다.
```shell
cd ~/.m2/repository/org/mariadb/jdbc/mariadb-java-client/3.5.6/
ls
```
![](../imgs/Pasted%20image%2020251117111701.png)

**JdbcSourceConnector에서 MariaDB 사용하기 위해 mariadb 드라이버 복사**
- 현재 디렉토리는 `~/.m2/repository/org/mariadb/jdbc/mariadb-java-client/3.5.6/` 경로의 디렉토리에 위치한다고 가정합니다.
```shell
cp mariadb-java-client-3.5.6.jar ~/Documents/tool/kafka/confluent-8.1.0/share/java/kafka
```

## Kafka Source Connect 사용
### Kafka Source Connect 테스트
Kafka Source Connect 추가(mariadb)
```shell
echo '
{
  "name": "my-source-connect",
  "config": {
    "connector.class": "io.confluent.connect.jdbc.JdbcSourceConnector",
    "connection.url": "jdbc:mariadb://localhost:3306/mydb",
    "connection.user": "root",
    "connection.password": "test1234",
    "mode": "incrementing",
    "incrementing.column.name": "id",
    "table.whitelist": "mydb.users",
    "topic.prefix": "my_topic_",
    "tasks.max": "1"
  }
}
' | curl -X POST -d @- http://localhost:8083/connectors --header 'content-Type:application/json'
```
- `@-` 는 파일 대신 **표준 입력(STDIN)** 을 의미합니다.
- echo 명령어를 이용하여 출력한 json 데이터를 HTTP Request Body의 입력으로 사용한다는 의미
- 8083 포트 서버는 Kafka Connect 서버의 포트번호입니다.
- POST /connectors 엔드포인트에 의해서 새로운 connector를 등록합니다.
- Kafka Connect **JDBC Source Connector** 설정
	- name : 생성할 Connector의 이름
	- config : Kafka Connect가 사용할 설정 값
	- connector.class : 어떤 Connector를 사용할지 명시
		- JDBC Source Connector -> RDB -> Kafka 방향으로 데이터를 가져오는 Connector
	- connection.url : JDBC 연결 URL
	- connection.user : DB의 사용자 계정 이름
	- connection.password : DB의 사용자 비밀번호
	- mode : DB에서 Kafka로 데이터를 어떻게 동기화할지 선택
		- incrementing : 증가하는 PK 컬럼(id같은 정수 증가 컬럼)을 기준으로 신규 데이터만 읽음
			- 예를 들어 기존 데이터=1~10이고 신규 데이터=11이 삽입되면 11 데이터만 kafka로 전송됩니다.
	- incrementing.column.name
		- `"mode": "incrementing"` 일 때 필수
		- **증가하는 PK 컬럼** 지정 (Auto Increment 형태)
	- table.whitelist
		- 어떤 테이블을 읽을 것인지 지정
		- 여러 테이블 가능: `"table.whitelist": "mydb.users,mydb.orders"`
		- users와 같은 테이블명은 다른 스키마에도 존재하기 때문에 스키마 이름(mydb)를 붙여주어야함
	- topic.prefix
		- Kafka에 데이터를 보낼 때 생성할 topic 이름 앞에 prefix를 붙임
		- users 테이블면 my_topic_users가 됨
	- tasks.max
		- 병렬 실행할 태스크(task) 수

Kafka Connector 등록 결과 확인
![](../imgs/Pasted%20image%2020251117125738.png)

Kafka Connect 목록 확인
```shell
curl http://localhost:8083/connectors
```
![](../imgs/Pasted%20image%2020251117120939.png)

Kafka Connect 확인
- 실행 결과를 보면 my-source-connect가 정상적으로 동작하는 것을 볼수 있음
```shell
curl http://localhost:8083/connectors/my-source-connect/status
```
![](../imgs/Pasted%20image%2020251117131429.png)

users 테이블에 데이터 삽입
```shell
insert into users(user_id, pwd, name) values('user1', 'test1111', 'john');
```
![](../imgs/Pasted%20image%2020251117132545.png)

topic 목록 확인
- 실행 결과를 보면 "my_topic_users" 토픽이 생성된 것을 볼수 있습니다.
```shell
$KAFKA_HOME/bin/kafka-topics.sh --bootstrap-server localhost:9092 --list
```
![](../imgs/Pasted%20image%2020251117132618.png)

my_topic_users Topic 구독하기
```shell
$KAFKA_HOME/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic my_topic_users --from-beginning
```
![](../imgs/Pasted%20image%2020251117133152.png)
```json
{
  "schema": {
    "type": "struct",
    "fields": [
      {
        "type": "int32",
        "optional": false,
        "field": "id"
      },
      {
        "type": "string",
        "optional": false,
        "field": "user_id"
      },
      {
        "type": "string",
        "optional": false,
        "field": "pwd"
      },
      {
        "type": "string",
        "optional": false,
        "field": "name"
      },
      {
        "type": "int64",
        "optional": true,
        "name": "org.apache.kafka.connect.data.Timestamp",
        "version": 1,
        "field": "created_at"
      }
    ],
    "optional": false,
    "name": "users"
  },
  "payload": {
    "id": 1,
    "user_id": "user1",
    "pwd": "test1111",
    "name": "john",
    "created_at": 1763353540000
  }
}
```

위 실행 결과를 보면 json 데이터로 유저가 생성되었다는 메시지를 소비한것을 볼수 있습니다. 이렇게 구독하여 받은 메시지를 이용하여 Kafka Connect Sink를 이용해서 다른 시스템에 내보낼수 있습니다.

## Kafka Sink Connect 사용
### Kafka Sink Connect 테스트
Kafka Sink Connect 추가 (MariaDB)
- 다음 명령어를 수행하여 "my-sink-connect"라는 이름의 Kafka Sink Connect를 생성합니다.
- 해당 sink는 my_topic_users topic에 연결하여 해당 토픽에 메시지가 들어오면 my_topic_users 테이블에 데이터가 추가됩니다.
```shell
echo '
{
  "name": "my-sink-connect",
  "config": {
    "connector.class": "io.confluent.connect.jdbc.JdbcSinkConnector",
    "connection.url": "jdbc:mariadb://localhost:3306/mydb",
    "connection.user": "root",
    "connection.password": "test1234",
    "value.converter": "org.apache.kafka.connect.json.JsonConverter",
	"value.converter.schemas.enable": true,
    "auto.create":"true",
	"auto.evolve":"true",
	"delete.enabled":"false",
	"tasks.max":"1",
	"topics":"my_topic_users"
  }
}
' | curl -X POST -d @- http://localhost:8083/connectors --header 'content-Type:application/json'
```
![](../imgs/Pasted%20image%2020251117135409.png)

my-sink-connect 옵션 분석
- auto.create : Sink 테이블이 존재하지 않으면 자동 생성
	- 테이블 이름은 topic 이름(`my_topic_users`) 기반으로 생성됨
- auto.evolve : 테이블 스키마 자동 변경 허용
	- Kafka 메시지 필드가 기존 DB 테이블 컬럼에 없으면, 컬럼을 추가
	- 기존 데이터는 유지됨
- delete.enabled : Kafka 메시지의 **DELETE 이벤트 처리 여부**
	- `true`이면 Kafka 메시지에서 tombstone(삭제 신호)를 받으면 DB에서 해당 행 삭제
	- 지금은 false → 삭제하지 않음
- tasks.max : Connector가 **병렬로 실행할 task 수**
- topics : **Sink 대상 topic** 지정
	- Kafka topic `my_topic_users`의 메시지를 읽어서 DB에 쓰게 됨

my_topic_users 테이블 확인
- 이전 실습으로 인해서 한개의 데이터가 추가되었음
![](../imgs/Pasted%20image%2020251117135826.png)

my_topic_users 테이블 데이터 확인
- 실행 결과를 보면 정상적으로 user1 사용자가 추가(export)된 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251117135852.png)

kafka producer를 이용해서 kafka topic에 데이터 직접 전송
- kafka-console-producer에서 데이터 전송 -> topic에 추가 -> MariaDB에 추가

데이터는 다음과 같습니다.
```json
{"schema":{"type":"struct","fields":[{"type":"int32","optional":false,"field":"id"},{"type":"string","optional":false,"field":"user_id"},{"type":"string","optional":false,"field":"pwd"},{"type":"string","optional":false,"field":"name"},{"type":"int64","optional":true,"name":"org.apache.kafka.connect.data.Timestamp","version":1,"field":"created_at"}],"optional":false,"name":"users"},"payload":{"id":2,"user_id":"user2","pwd":"test2222","name":"bob","created_at":1763353540000}}
```

명령어는 다음과 같습니다.
```shell
$KAFKA_HOME/bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic my_topic_users
```


> [!NOTE] topic 제거
> `$KAFKA_HOME/bin/kafka-topics.sh --delete --topic my_topic_users --bootstrap-server localhost:9092`
`

실행 결과 확인
- console-producer를 통해서 직접적으로 json 데이터를 넣어도 성공적으로 데이터가 추가된 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251117144344.png)

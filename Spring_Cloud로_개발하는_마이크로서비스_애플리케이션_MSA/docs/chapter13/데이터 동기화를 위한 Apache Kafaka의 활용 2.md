
## Orders Microservice와 Catalogs Microservice에 Kafka Topic의 적용
### 데이터 동기화 1 - Orders -> Catalogs
- Orders Service에 요청된 주문의 수량 정보를 Catalogs Service에 반영
- Orders Service에서 Kafka Topic으로 메시지 전송 -> Producer
- Catalogs Service에서 Kafka Topic에 전송된 메시지 취득 -> Consumer

위 설명에 해당하는 데이터 동기화 과정이 다음 그림의 4번 과정입니다. 다음 그림을 보면 사용자가 상품 주문을 보면 Order Service에서는 주문 데이터를 추가하고 상품 주문시 요청한 상품의 개수만큼 Catalog Service에도 재고수량을 업데이트해주어야 합니다.
서로 다른 마이크로서비스에서 재고 수량을 업데이트하기 위해서 Kafka를 사용하여 업데이트하고자 합니다.
![](imgs/Pasted%20image%2020251029145411.png)

#### Catalogs Service 수정
- spring-kafka 의존성 라이브러리 추가
- ConsumerFactory 스프링 빈 등록
	- bootstrap 서버 설정 추가
	- 그룹 아이디 설정 추가
	- key-value 역직렬화 클래스 추가
- ConcurrentKafkaListenerContainerFactory 스프링 빈 등록
	- ConusmerFactory 객체 추가를 팩토리 객체에 추가

의존성 추가
```xml
<dependency>  
    <groupId>org.springframework.kafka</groupId>  
    <artifactId>spring-kafka</artifactId>  
</dependency>
```

설정 클래스 추가
- bootstrap 서버 주소나 group id 설정 값들은 config service에 등록하여 효율화시킬 수 있음
```java
@Configuration  
@EnableKafka  
public class KafkaConsumerConfig {  
    @Bean  
    public ConsumerFactory<String, String> consumerFactory(){  
       Map<String, Object> properties = new LinkedHashMap<>();  
       // kafka container host  
       properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");  
       properties.put(ConsumerConfig.GROUP_ID_CONFIG, "consumerGroupId");  
       properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);  
       properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);  
       return new DefaultKafkaConsumerFactory<>(properties);  
    }  
  
    @Bean  
    public ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory(){  
       ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory =  
          new ConcurrentKafkaListenerContainerFactory<>();  
       kafkaListenerContainerFactory.setConsumerFactory(consumerFactory());  
       return kafkaListenerContainerFactory;  
    }  
}
```

KafkaConumser 서비스 클래스 구현
![](imgs/Pasted%20image%2020251117171021.png)

#### Orders Service 수정
- spring kafka 의존성 추가
- Kafka Producer 관련 설정 추가
	- ProducerFactory 스프링 빈 등록
	- KafkaTemplate 스프링 빈 추가

의존성 추가
```xml
<!-- kafka -->  
<dependency>  
    <groupId>org.springframework.kafka</groupId>  
    <artifactId>spring-kafka</artifactId>  
</dependency>
```

설정 클래스 추가
![](imgs/Pasted%20image%2020251118115803.png)

KafkaProducer 클래스 구현
![](imgs/Pasted%20image%2020251118121705.png)

OrderController 수정
- 상품 주문시 kafka에 메시지 전송
![](imgs/Pasted%20image%2020251118121808.png)

Order Service의 Kafka Producer 테스트
- 실행 결과를 보면 CATALOG-001 상품에 대해서 20개 상품 주문합니다.
![](imgs/Pasted%20image%2020251118123655.png)

Order-Service의 로그를 보면 KafkaProducer가 정상적으로 OrderDto 데이터를 json으로 직렬화하여 전송한 것을 볼수 있습니다.
![](imgs/Pasted%20image%2020251118123753.png)

Catalog-Service의 상풍 목록 조회
- CATALOG-001 상품의 stock 값을 보면 100에서 80으로 감소한 것을 볼수 있습니다.
- 이 결과로 인해서 상품 주문시 정상적으로 재고가 업데이트된 것을 알수 있습니다.
![](imgs/Pasted%20image%2020251118123731.png)

Catalog Service의 로그를 보면 OrderDto 메시지를 받은 것을 볼수 있습니다.
![](imgs/Pasted%20image%2020251118124844.png)

그리고 쿼리 로그를 보면 stock 값을 업데이트하는 것을 볼수 있습니다.
![](imgs/Pasted%20image%2020251118124947.png)

## Multiple Orders Mircoservice 사용에 대한 데이터 동기화 문제
### Multiple Orders Service에서의 데이터 동기화
Orders Service 2개 기동
- Users의 요청 분산 처리
- Orders 데이터도 분산 저장 -> 동기화 문제 발생

UserService와 Order Service가 서로 RestTemplate 또는 FeignClient를 이용하여 통신한다고 가정합니다. 그리고 Order Service 마이크로서비스들은 각각의 데이터베이스를 가진다고 가정합니다.
![](imgs/Pasted%20image%2020251118131933.png)

위 그림을 기반으로 사용자가 상품 주문을 하면 분산 처리에 의해서 두 Order Service의 데이터베이스중 하나에 상품 주문 정보가 저장될 것입니다.

현재 Order Service 마이크로서비스 서버가 2개 기동된 상태에서 60001 서버의 데이터베이스에 상품 정보가 1개 저장되어 있는 상태입니다.
![](imgs/Pasted%20image%2020251118132909.png)

반면 60002 서버의 데이터베이스의 orders 테이블에는 데이터가 하나도 없는 상태입니다.
![](imgs/Pasted%20image%2020251118132941.png)

위와 같은 상태에서 Catalog Service를 잠시 정지한 상태에서 다시 여러개의 주문을 해보겠습니다.
- CATALOG-002 12개 상품 주문
- CATALOG-003 13개 상품 주문
- CATALOG-004 14개 상품 주문
- CATALOG-005 15개 상품 주문
![](imgs/Pasted%20image%2020251118133349.png)
![](imgs/Pasted%20image%2020251118133437.png)
![](imgs/Pasted%20image%2020251118133451.png)
![](imgs/Pasted%20image%2020251118133510.png)

위와 같이 총 5개의 주문 데이터(CATALOG-001 포함)가 2개의 데이터베이스에 어떻게 저장되어 있는지 확인합니다.
- order service 60001 : CATALOG-001, CATALOG-002, CATALOG-004 상품 주문 데이터 저장함
- order service 60002 : CATALOG-003, CATALOG-005 상품 주문 데이터 저장함
![](imgs/Pasted%20image%2020251118133609.png)
![](imgs/Pasted%20image%2020251118133645.png)

위와 같이 5개의 주문 데이터가 각각 분산 저장되어 있는 상태에서 사용자의 주문 상품 목록을 조회해봅니다.
- 실행 결과를 보면 5개의 주문 데이터가 아니라 3개의 데이터만 조회된 것을 볼수 있습니다.
![](imgs/Pasted%20image%2020251118134804.png)

위 실습을 통해서 발생한 문제점은 여러개의 Order Service와 각가의 데이터베이스가 존재하는 상태에서 사용자의 상품 주문 요청시 분산 처리되서 상품 주문 데이터 또한 분산 저장된다는 점입니다. 이렇게 분산 저장되면 사용자가 상품 목록 조회시 상품 주문 데이터 조회 또한 분산 조회되어 특정 1개의 데이터베이스의 상품 주문 데이터만 조회된다는 문제점이 존재합니다. 즉, 상품 주문 데이터에 대한 **데이터 동기화 문제**가 발생합니다.

위와 같은 데이터 동기화 문제를 해결하기 위한 한 방법으로 **Kafka Messaing Server를 이용한 방법**이 있습니다.

## Kafka Connect를 활용한 단일 데이터베이스를 사용
데이터 동기화 문제 해결 방향
- Orders Service에 요청된 주문 정보를 DB가 아니라 Kafka Topic으로 전송함
- Kafka Topic에 설정된 **Kafka Sink Connect**를 사용해 단일 DB에 저장하여 **데이터 동기화**합니다.

![](imgs/Pasted%20image%2020251118141118.png)

Orders Service의 JPA 데이터베이스 교체
- H2 DB -> Maria DB
- 교체한 mariaDB에 미리 orders 테이블을 미리 생성해둡니다.

```mysql
create table orders(
	id int auto_increment primary key,
	product_id varchar(20) not null,
	qty int default 0,
	unit_price int default 0,
	total_price int default 0,
	user_id varchar(50) not null,
	order_id varchar(50) not null,
	created_at datetime default NOW()
);
```
![](imgs/Pasted%20image%2020251118144053.png)

order-service의 datasource 설정 변경
- 기존 h2 jdbc url에서 mariadb URL로 변경합니다.
![](imgs/Pasted%20image%2020251118144816.png)

상품 주문후 데이터베이스의 데이터 확인
- 상품 주문후 실제로 mariadb 데이터베이스에 상품 주문 정보가 저장되는지 확인합니다.
- 실행 결과를 보면 정상적으로 데이터가 저장되는 것을 볼수 있습니다.
![](imgs/Pasted%20image%2020251118144938.png)
![](imgs/Pasted%20image%2020251118145041.png)

## Orders Microservice 수정 - Orders Kafka Topic
Order Service의 Controller 수정
- 기존 jpa를 이용하여 데이터베이스에 저장하는 것이 아닌 Kafka에 메시지를 전송하여 데이터를 동기화하도록 함
- 기존 orderId와 totalPrice는 orderService 내부에서 계산하여 저장하였기 때문에 kafka에 전송하기 전에 orderDto에 orderId와 totalPrice를 계산하여 설정해둡니다.

![](imgs/Pasted%20image%2020251118160128.png)

Order Service의 Producer에서 발생하기 위한 메시지 등록
- orderDto 데이터를 json으로 직렬화 하는 것이 아니라 다음과 같이 메시지 형식을 맞추어 변환하는 것이 필요함
- schema 프로퍼티는 저장되는 데이터베이스 스키마 정보를 넣습니다.
- schema.fields 부분은 테이블의 컬럼 정보를 명시합니다.
- payload 프로퍼티는 실제 저장되는 데이터 정보를 명시합니다.
```json
{
  "schema": {
    "type": "struct",
    "optional": false,
    "name": "orders",
    "fields": [
      { "field": "order_id", "type": "string", "optional": true },
      { "field": "user_id", "type": "string", "optional": true },
      { "field": "product_id", "type": "string", "optional": true },
      { "field": "qty", "type": "int32", "optional": true },
      { "field": "total_price", "type": "int32", "optional": true },
      { "field": "unit_price", "type": "int32", "optional": true }
    ]
  },

  "payload": {
    "order_id": "ORD-20250218-001",
    "user_id": "boaijwefoaiwejf-awefoi123-123",
    "product_id": "CATALOG-001",
    "qty": 5,
    "total_price": 6000,
    "unit_price": 1200,
  }
}
```


KafkaOrderDto 클래스 구현
![](imgs/Pasted%20image%2020251118153306.png)
![](imgs/Pasted%20image%2020251118153315.png)
![](imgs/Pasted%20image%2020251118153321.png)
![](imgs/Pasted%20image%2020251118153325.png)

Order Service의 OrderProducer 생성
- kafkaTmplate를 이용하여 특정 topic에 주문 데이터 메시지를 전달합니다.
![](imgs/Pasted%20image%2020251118153749.png)

OrderProducer 클래스의 send 메서드 구현
- Payload 데이터 생성
- kafkaOrderDto 객체를 json 데이터로 직렬화
- kafkaTemplate를 이용하여 특정 topic에 메시지 전달
![](imgs/Pasted%20image%2020251118155009.png)

Order Service를 위한 Kafka Sink Connect 추가
Order Sink Connect Request Body
```json
{
  "name": "my-order-sink-connect",
  "config": {
    "connector.class": "io.confluent.connect.jdbc.JdbcSinkConnector",
    "connection.url": "jdbc:mariadb://localhost:3306/mydb",
    "connection.user": "root",
    "connection.password": "test1234",
    "auto.create":"true",
	"auto.evolve":"true",
	"delete.enabled":"false",
	"tasks.max":"1",
	"topics":"orders"
  }
}
```

![](imgs/Pasted%20image%2020251118155857.png)

my-order-sink-connect 생성확인
![](imgs/Pasted%20image%2020251118160013.png)

## Kafka를 활용한 데이터 동기화 테스트
주문 테스트
- 2개의 order service 서버 기동
![](imgs/Pasted%20image%2020251118160633.png)

orders topic conusmer 실행
- order 메시지 데이터가 정상적으로 topic에 들어왔는지 확인하기 위한 용도
```shell
$KAFKA_HOME/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic orders --from-beginning
```

Catalog 테이블의 데이터 상황
![](imgs/Pasted%20image%2020251118161250.png)

상품 주문 테스트
![](imgs/Pasted%20image%2020251118162212.png)
![](imgs/Pasted%20image%2020251118162254.png)
![](imgs/Pasted%20image%2020251118162314.png)


메시지 확인
![](imgs/Pasted%20image%2020251118162221.png)
![](imgs/Pasted%20image%2020251118162320.png)

Order Service 로그 확인
다음 실행 결과는 60002 포트의 order service 로깅입니다. 2개의 상품 주문 데이터를 처리하였습니다.
![](imgs/Pasted%20image%2020251118162416.png)

60001 포트의 order service 서버는 1개의 상품 주문을 처리하였습니다.
![](imgs/Pasted%20image%2020251118162454.png)

데이터베이스 확인
- my-order-sink-connect Connector가 topic의 주문들을 받아서 mariadb 데이터베이스의 orders 테이블에 3개의 데이터를 추가했는지 확인합니다. 
- 다음 결과는 topic, my-order-sink-connect를 제거하고 새로 생성한 다음에 실행한 결과
- 핵심은 여러개의 order-service에서 분산 처리해도 kafka와 kafka sink connect를 이용하여 단일 DB에 주문 데이터 추가한다는 것
![](imgs/Pasted%20image%2020251118163604.png)


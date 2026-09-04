
## 1. Eureka Service Discovery

원격 저장소 다운로드 및 실습 브랜치 변경
```shell
git clone https://github.com/joneconsulting/toy-msa.git
cd toy-msa
git fetch origin
git switch springboot3.2
```

service-discovery 리소스 파일 작성 (application.yml)
```yaml
server:  
  port: 8761  
  
spring:  
  application:  
    name: discoveryservice  
  
eureka:  
  client:  
    register-with-eureka: false  
    fetch-registry: false  
  
---  
  
spring:  
  config:  
    activate:  
      on-profile: eureka2  
  
server:  
  port: 8762  
  
eureka:  
  client:  
    serviceUrl:  
      defaultZone: http://gim-yonghwan-ui-MacBookAir.local:8763/eureka/  
  instance:  
    hostname: localhost  
  
---  
  
spring:  
  config:  
    activate:  
      on-profile: eureka3  
  
server:  
  port: 8763  
  
eureka:  
  client:  
    serviceUrl:  
      defaultZone: http://localhost:8762/eureka/  
  instance:  
    hostname: gim-yonghwan-ui-MacBookAir.local
```
- Profile eureka2, eureka3으로 설정하여 2개의 유레카 서버를 실행후 한쪽이 종료되어도 한쪽이 실행되어 서비스를 제공하는것을 목표로합니다.
- eureka2, eureka3 프로파일에 해당하는 서비스 URL은 서로의 호스트 주소를 작성합니다.

**Profile=eureka2 서버 구성**
![](../imgs/Pasted%20image%2020260818121831.png)

**Profile=eureka3 서버 구성**
![](../imgs/Pasted%20image%2020260818121843.png)

 base-service 모듈 생성
 ![](../imgs/Pasted%20image%2020260818122017.png)
 ![](../imgs/Pasted%20image%2020260818122034.png)

**base-service 리소스 설정(application.yml)**
- 연결할 eureka 서버 설정시 eureka2, eureka3 서버에 해당하는 호스트 주소를 설정합니다.
```yaml
server:  
  port: 8080  
  
spring:  
  application:  
    name: base-service  
  
eureka:  
  client:  
    register-with-eurkea: true  
    fetch-registry: true  
    service-url:  
#      defaultZone: http://localhost:8761/eureka  
      defaultZone: http://localhost:8762/eureka, http://gim-yonghwan-ui-MacBookAir.local:8763/eureka
```

실행 결과
- eureka2, eureka3, base-service를 실행합니다.

eureka2 서버의 실행 결과를 보면 DS Replicas의 결과로 eureka3의 호스트 주소가 추가되었습니다. 그리고 base-service가 등록되었습니다.
![](../imgs/Pasted%20image%2020260818122212.png)

eureka3 서버의 실행 결과를 보면 DS Replicas의 결과로 eureka2의 호스트 주소가 추가되었습니다. 해당 결과 또한 base-service가 등록되었습니다.
![](../imgs/Pasted%20image%2020260818122259.png)

## 2. Configuration Service
config-service
- Spring Boot 3.3
- Spring Cloud 2023

부트스트랩 설정(bootstrap.yml)
```yaml
encrypt:  
#      key: abcdefghijklmnopqrstuvwxyz0123456789  
    key-store:  
        location: file://${user.home}/Documents/temp-project/toy-msa/config-service/apiEncryptionKey.jks  
#        location: file:/apiEncryptionKey.jks  
        password: test1234  
        alias: apiEncryptionKey
```

애플리케이션 설정(application.yml)
```yaml
server:  
  port: 8888  
  
spring:  
#  profiles:  
#    active: native  
  application:  
    name: config-service  
  cloud:  
    config:  
      server:  
        native:  
          search-locations: file://${user.home}/Documents/native-file-repo  
        git: #default  
          uri: https://github.com/yonghwankim-dev/spring-cloud-config.git  
          default-label: master  
#          username: <github-id>  
#          password: <gihub-accessToken>  
        bootstrap: true  
#  rabbitmq:  
#    host: 127.0.0.1  
#    port: 5672  
#    username: guest  
#    password: guest  
  
management:  
  endpoints:  
    web:  
      exposure:  
        include: health, busrefresh, refresh, metrics
```

실행 결과
![](../imgs/Pasted%20image%2020260818135203.png)

## 3. First-Service, Second-Service
**first-service**
- Spring Boot 3.3
- Spring Cloud 2023

애플리케이션 설정(application.yml)
```yml
server:  
  port: 8081
spring:  
  application:  
    name: my-first-service  
  
eureka:  
  client:  
    register-with-eureka: true  
    fetch-registry: true  
    service-url:  
      defaultZone: http://localhost:8761/eureka  
#      defaultZone: http://localhost:8762/eureka, http://kubernetes.docker.internal:8763/eureka  
  instance:  
    instance-id: ${spring.cloud.client.ip-address}:${spring.application.instance_id:${random.value}}  
    prefer-ip-address: true
```

first-service에 welcome API 요청
![](../imgs/Pasted%20image%2020260818143744.png)

first-service에 check API 요청
![](../imgs/Pasted%20image%2020260818143900.png)

first-service에 메시지 API 요청
- HTTP 요청시 first-request에 값을 넣어서 요청
![](../imgs/Pasted%20image%2020260818144254.png)


**second-service**
- Spring Boot 3.3
- Spring Cloud 2023

애플리케이션 설정(application.yml)
```yaml
server:  
  port: 8082  
  
spring:  
  application:  
    name: my-second-service  
  
eureka:  
  client:  
    register-with-eureka: true  
    fetch-registry: true  
    service-url:  
      defaultZone: http://localhost:8761/eureka  
  instance:  
    instance-id: ${spring.cloud.client.ip-address}:${spring.application.instance_id:${random.value}}
```

실행결과
- 실행 결과는 first-service와 방식이 동일함


## 4. APIGateway Service
###  코드 기반 라우팅 및 필터 설정하기
 - `/first-service/**` 경로로 요청시 헤더에 `first-request` 헤더 및 값 추가, 응답 헤더에 `first-response` 헤더 및 값 추가
 - `/second-service/**` 경로로 요청시 헤더에 second-request 헤더 및 값 추가, 응답 헤더에 `second-response` 헤더 및 값 추가
 ```java
 @Configuration  
public class FilterConfig {  
    Environment env;  
  
    public FilterConfig(Environment env) {  
        this.env = env;  
    }  
  
   @Bean  
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder, AuthorizationHeaderFilter myfilter) {  
  
        return builder.routes()  
                .route(r -> r.path("/first-service/**")  
                            .filters(f -> f.addRequestHeader("first-request", "first-request-header-by-java")  
                                           .addResponseHeader("first-response", "first-response-header-from-java")  
                            )  
                            .uri("http://localhost:8081"))  
                .route(r -> r.path("/second-service/**")  
                            .filters(f -> f.addRequestHeader("second-request", "second-request-header-by-java")  
                                    .addResponseHeader("second-response", "second-response-header-from-java"))  
                            .uri("http://localhost:8082"))  
                .build();  
    }  
  
}
 ```

application-simple.yaml 설정
- 8000번 포트로 고정하여 HTTP 요청하도록 함
- apigateway-service 실행시 simple 프로파일로 설정하여 실행함
```yaml
server:  
  port: 8000  
  
eureka:  
  client:  
    register-with-eureka: true  
    fetch-registry: true  
    service-url:  
      defaultZone: http://localhost:8761/eureka  
  
spring:  
  config:  
    activate:  
      on-profile: simple  
  application:  
    name: apigateway-service    
management:  
  endpoints:  
    web:  
      exposure:  
        include: refresh, health, beans, httpexchanges, busrefresh, info, metrics, prometheus
```

실행 결과
다음 결과는 `http://localhost:8000/second-service/message` 경로로 요청한 실행 결과입니다. 요청 헤더를 보면 second-request 헤더가 보이지 않고, 응답 헤더에는 `second-response` 헤더가 포함되어 있는 것을 볼수 있습니다. `second-request` 헤더가 포함되지 않은 이유는 클라이언트는 명시적으로 포함하지 않고 apigatew-way가 필터단에서 추가하였기 때문입니다.
![](../imgs/Pasted%20image%2020260820150114.png)

### 프로퍼티 파일 기반 라우트 및 필터 설정하기
Spring Bean 코드가 아닌 yaml 파일 기반으로 apigateway-service의 라우팅 및 필터를 추가하는 방법도 있습니다.
`application-simple.yml`
- GlobalFilter를 추가하여 어떤 요청이 오든 해당 필터를 실행시킵니다.
- `first-service/**` 로 시작하는 요청이 오면 요청헤더와 응답 헤더에 값을 추가하고 추가적으로 CustomFilter를 실행시킵니다.
- `second-service/**`로 시작하는 요청이 오면 요청 헤더와 응답 헤더에 값을 추가하고 추가적으로 CustomFilter, LoggingFilter를 실행시킵니다.
- CustomFilter와 LoggingFilter 사이에 우선순위가 명시적으로 설정되어 있지 않기 때문에 실행시 CustomFilter가 먼저 실행됩니다.
```yaml
server:  
  port: 8000  
  
eureka:  
  client:  
    register-with-eureka: true  
    fetch-registry: true  
    service-url:  
      defaultZone: http://localhost:8761/eureka  
  
spring:  
  config:  
    activate:  
      on-profile: simple  
  application:  
    name: apigateway-service  
  cloud:  
    gateway:  
      default-filters:  
        - name: GlobalFilter  
          args:  
            baseMessage: Spring Cloud Gateway Global Filter  
            preLogger: true  
            postLogger: true  
      routes:  
        - id: first-service  
          uri: lb://MY-FIRST-SERVICE  
          predicates:  
            - Path=/first-service/**  
          filters:  
            - AddRequestHeader=first-request, first-request-header-by-yaml  
            - AddResponseHeader=first-response, first-response-header-from-yaml  
            - CustomFilter  
        - id: second-service  
          uri: lb://MY-SECOND-SERVICE  
          predicates:  
            - Path=/second-service/**  
          filters:  
            - AddRequestHeader=second-request, second-request-header-by-yaml  
            - AddResponseHeader=second-response, second-response-header-from-yaml  
            - name: CustomFilter  
            - name: LoggingFilter  
              args:  
                baseMessage: Hi , there.  
                preLogger: true  
                postLogger: true  
  
#token:  
#  secret: user_token  
  
management:  
  endpoints:  
    web:  
      exposure:  
        include: refresh, health, beans, httpexchanges, busrefresh, info, metrics, prometheus
```

`GlobalFilter.java`
```java
@Component  
@Slf4j  
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {  
    public GlobalFilter() {  
        super(Config.class);  
    }  
  
    @Override  
    public GatewayFilter apply(Config config) {  
        return ((exchange, chain) -> {  
            ServerHttpRequest request = exchange.getRequest();  
            ServerHttpResponse response = exchange.getResponse();  
  
            log.info("Global Filter baseMessage: {}, {}", config.getBaseMessage(), request.getRemoteAddress());  
            if (config.isPreLogger()) {  
                log.info("Global Filter Start: request id -> {}", request.getId());  
            }  
            return chain.filter(exchange).then(Mono.fromRunnable(()->{  
                if (config.isPostLogger()) {  
                    log.info("Global Filter End: response code -> {}", response.getStatusCode());  
                }  
            }));  
        });  
    }  
  
    @Data  
    public static class Config {  
        private String baseMessage;  
        private boolean preLogger;  
        private boolean postLogger;  
    }  
}
```

`CustomFilter.java`
```java
@Component  
@Slf4j  
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {  
    public CustomFilter() {  
        super(Config.class);  
    }  
  
    @Override  
    public GatewayFilter apply(Config config) {  
        // Custom Pre Filter  
        return (exchange, chain) -> {  
            ServerHttpRequest request = exchange.getRequest();  
            ServerHttpResponse response = exchange.getResponse();  
  
            log.info("Custom PRE filter: request id -> {}", request.getId());  
  
            // Custom Post Filter  
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {  
                log.info("Custom POST filter: response code -> {}", response.getStatusCode());  
            }));  
        };  
    }  
  
    public static class Config {  
        // Put the configuration properties  
    }  
}
```

`LoggingFilter.java`
```java
@Component  
@Slf4j  
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {  
    public LoggingFilter() {  
        super(Config.class);  
    }  
  
   @Override  
   public GatewayFilter apply(Config config) {  
       return (exchange, chain) -> {  
           ServerHttpRequest request = exchange.getRequest();  
           ServerHttpResponse response = exchange.getResponse();  
  
           log.info("Logging PRE filter: request id -> {}", request.getId());  
  
           // Custom Post Filter  
           return chain.filter(exchange).then(Mono.fromRunnable(() -> {  
               log.info("Logging POST filter: response code -> {}", response.getStatusCode());  
           }));  
       };  
   }  
  
    @Data  
    public static class Config {  
        private String baseMessage;  
        private boolean preLogger;  
        private boolean postLogger;  
    }  
}
```

실행 결과
응답 헤더의 Second-Response 헤더값을 보면 "second-response-header-from-yaml" 값으로써 해당 값이 프로퍼티에 설정된 값과 일치합니다.
![](../imgs/Pasted%20image%2020260820151904.png)

필터 실행 결과를 ㅂ면 Global -> Custom -> Logging 순으로 필터가 동작합니다.
![](../imgs/Pasted%20image%2020260820151824.png)

### LoggingFilter의 우선순위 변경하기
이전 예제를 보면 필터의 실행 순서가 Global->Custom->Logging 필터순인것을 볼수 있다. 하지만 필터의 설정에 따라서 우선순위를 변경할 수 있다.
이번에는 LoggingFilter의 순서를 제일 먼저(최상위)로 변경해보겠습니다.

필터 설정시 `Ordered.HIGHEST_PRECEDENCE`  매개변수를 추가하여 가장 먼저 실행되도록 합니다.
```java
@Component  
@Slf4j  
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {  
    public LoggingFilter() {  
        super(Config.class);  
    }  
  
    /* 우선 순위를 갖는 Filter 적용 */    @Override  
    public GatewayFilter apply(Config config) {  
        GatewayFilter filter = new OrderedGatewayFilter((exchange, chain) -> {  
            ServerHttpRequest request = exchange.getRequest();  
            ServerHttpResponse response = exchange.getResponse();  
  
            log.info("Logging Filter baseMessage: {}", config.getBaseMessage());  
            if (config.isPreLogger()) {  
                log.info("Logging PRE Filter: request id -> {}", request.getId());  
            }  
            return chain.filter(exchange).then(Mono.fromRunnable(()->{  
                if (config.isPostLogger()) {  
                    log.info("Logging POST Filter: response code -> {}", response.getStatusCode());  
                }  
            }));  
        }, Ordered.HIGHEST_PRECEDENCE);  
  
        return filter;  
    }  
  
    @Data  
    public static class Config {  
        private String baseMessage;  
        private boolean preLogger;  
        private boolean postLogger;  
    }  
}
```

실행 결과
동일하게 `/second-service/message` 경로로 HTTP 요청합니다. 실행 결과를 보면 Logging -> Global -> Custom 필터 순으로 실행하여 Logging Filter가 최상위로 실행되었습니다.
![](../imgs/Pasted%20image%2020260820152759.png)

## 5. Kafka 연동
사전조건
- docker 설치
- Apache Kafka 4.0 기준으로 실습함

	
docker 기반 Kafka Broker 실행
```shell
docker run -d --name broker -p 9092:9092 apache/kafka:4.0.0
```

brodker 컨테이너에 쉘 접속
```shell
docker exec --workdir /opt/kafka/bin/ -it broker sh
```


topic 생성
```shell
$KAFKA_HOME/bin/kafka-topics.sh --create --topic quickstart-events --bootstrap-server localhost:9092 --partitions 1
```
![](../imgs/Pasted%20image%2020260820161233.png)

topic 생성 확인
```shell
$KAFKA_HOME/bin/kafka-topics.sh --bootstrap-server localhost:9092 --list
```
![](../imgs/Pasted%20image%2020260820161333.png)

메시지 생산
- quickstart-events topic에 메시지를 전송해봅니다.
```shell
$KAFKA_HOME/bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic quickstart-events
> hello world!
```

메시지 소비
```shell
$KAFKA_HOME/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic quickstart-events --from-beginning
```
![](../imgs/Pasted%20image%2020260820161642.png)

topic 삭제
```shell
$KAFKA_HOME/bin/kafka-topics.sh --bootstrap-server localhost:9092 --delete --topic quickstart-events
```
![](../imgs/Pasted%20image%2020260820162044.png)

## 6. Order Service
Kafka 브로커 실행
```shell
docker run -d --name broker -p 9092:9092 apache/kafka:4.0.0
```

kafka 실행 확인
```shell
docker ps -a | grep kafka
```
![](../imgs/Pasted%20image%2020260821133249.png)

kafka 접속
```shell
docker exec -it broker bash
```

KAFKA_HOME 환경 변수 설정
```shell
export KAFKA_HOME=/opt/kafka
```

현재 topic 리스트 출력
```shell
$KAFKA_HOME/bin/kafka-topics.sh --list --bootstrap-server localhost:9092
```

카프카 생산자 설정 클래스 수정
- 부트스트랩 서버의 호스트 주소 설정을 "localhost:9092"로 변경합니다.
```java
@EnableKafka  
@Configuration  
public class KafkaProducerConfig {  
    @Bean  
    public ProducerFactory<String, String> producerFactory() {  
        Map<String, Object> properties = new HashMap<>();  
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");  
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);  
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);  
  
        return new DefaultKafkaProducerFactory<>(properties);  
    }  
  
    @Bean  
    public KafkaTemplate<String, String> kafkaTemplate() {  
        return new KafkaTemplate<>(producerFactory());  
    }  
}
```


상품 주문 테스트
![](../imgs/Pasted%20image%2020260825110327.png)

실행 결과
- 카프카 `example-catalog-topic` topic을 확인하여 주문 정보가 제대로 전달되었는지 확인한다.
```bash
$KAFKA_HOME/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic example-catalog-topic --from-beginning
```
![](../imgs/Pasted%20image%2020260825110721.png)

실행 결과를 보면 제대로 상품 주문 이벤트가 전달된것을 확인할 수 있습니다.

## 7. Catalog Service
카프카 소비자 설정 수정
- BOOTSTRAP_SERVERS_CONFIG 키에 대한 호스트 값을 "localhost:9092"로 변경하기
```java
@EnableKafka  
@Configuration  
public class KafkaConsumerConfig {  
    @Bean  
    public ConsumerFactory<String, String> consumerFactory() {  
        Map<String, Object> properties = new HashMap<>();  
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");  
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "consumerGroupId");  
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");  
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);  
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);  
  
        return new DefaultKafkaConsumerFactory<>(properties);  
    }  
  
    @Bean  
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {  
        ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory  
                = new ConcurrentKafkaListenerContainerFactory<>();  
        kafkaListenerContainerFactory.setConsumerFactory(consumerFactory());  
  
        return kafkaListenerContainerFactory;  
    }  
}
```

카탈로그 서비스 실행
```java
java -jar ./target/catalog-service-1.0.jar
```

상품 주문 테스트
- order-service에게 1번 카탈로그 상품 5개를 주문합니다.
![](../imgs/Pasted%20image%2020260825120110.png)

상품 재고 감소 확인
- CATALOG-001 상품의 STOCK 컬럼의 값을 보면 기존 100개에서 95개로 감소한 것을 확인할 수 있다.
![](../imgs/Pasted%20image%2020260825120221.png)

## 8. Apigateway Service
application.yml
- `GET /user-service/**` 경로로 요청시 쿠키 제거, user-service 접두사 경로 제거, 인가 헤더 필터를 작동시키도록 함
```yaml
server:  
  port: 8000  
  
eureka:  
  client:  
    register-with-eureka: true  
    fetch-registry: true  
    service-url:  
      defaultZone: http://localhost:8761/eureka  
  
spring:  
  config:  
    activate:  
      on-profile: default  
  application:  
    name: apigateway-service  
  rabbitmq:  
    host: 127.0.0.1  
    port: 5672  
    username: guest  
    password: guest  
    listener:  
      simple:  
        prefetch: 5  
  cloud:  
    gateway:  
      default-filters:  
        - name: GlobalFilter  
          args:  
            baseMessage: Spring Cloud Gateway Global Filter  
            preLogger: true  
            postLogger: true  
      routes:  
        - id: user-service  
          uri: lb://USER-SERVICE  
          predicates:  
            - Path=/user-service/login  
            - Method=POST  
          filters:  
            - RemoveRequestHeader=Cookie  
            - RewritePath=/user-service/(?<segment>.*), /$\{segment}  
        - id: user-service  
          uri: lb://USER-SERVICE  
          predicates:  
            - Path=/user-service/users  
            - Method=POST  
          filters:  
            - RemoveRequestHeader=Cookie  
            - RewritePath=/user-service/(?<segment>.*), /$\{segment}  
        - id: user-service  
          uri: lb://USER-SERVICE  
          predicates:  
            - Path=/user-service/actuator/**  
            - Method=GET,POST  
          filters:  
            - RemoveRequestHeader=Cookie  
            - RewritePath=/user-service/(?<segment>.*), /$\{segment}  
        - id: user-service  
          uri: lb://USER-SERVICE  
          predicates:  
            - Path=/user-service/**  
            - Method=GET  
          filters:  
            - RemoveRequestHeader=Cookie  
            - RewritePath=/user-service/(?<segment>.*), /$\{segment}  
            - AuthorizationHeaderFilter  
        - id: catalog-service  
          uri: lb://CATALOG-SERVICE  
          predicates:  
            - Path=/catalog-service/**  
        - id: order-service  
          uri: lb://ORDER-SERVICE  
          predicates:  
            - Path=/order-service/actuator/**  
            - Method=GET,POST  
          filters:  
            - RemoveRequestHeader=Cookie  
            - RewritePath=/order-service/(?<segment>.*), /$\{segment}  
        - id: order-service  
          uri: lb://ORDER-SERVICE  
          predicates:  
            - Path=/order-service/**  
        - id: first-service  
          uri: lb://MY-FIRST-SERVICE  
          predicates:  
            - Path=/first-service/**  
          filters:  
            - AddRequestHeader=first-request, first-request-header-by-yaml  
            - AddResponseHeader=first-response, first-response-header-from-yaml  
            - CustomFilter  
        - id: second-service  
          uri: lb://MY-SECOND-SERVICE  
          predicates:  
            - Path=/second-service/**  
          filters:  
            - AddRequestHeader=second-request, second-request-header-by-yaml  
            - AddResponseHeader=second-response, second-response-header-from-yaml  
            - name: CustomFilter  
            - name: LoggingFilter  
              args:  
                baseMessage: Hi, there.  
                preLogger: true  
                postLogger: true  
  
#token:  
#  secret: user_token  
  
management:  
  endpoints:  
    web:  
      exposure:  
        include: refresh, health, beans, httpexchanges, busrefresh, info, metrics, prometheus
```

실행
```sh
java -jar ./target/apigateway-service-1.0.jar
```


## 8. User Service
핵심 비즈니스 로직
```java
    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null)
            throw new UsernameNotFoundException("User not found");

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        log.info("Before call orders microservice");
        List<ResponseOrder> ordersList = new ArrayList<>();
        
        /* Using a feign client */
        /* #2 Feign exception handling */
       try {
           ordersList = orderServiceClient.getOrders(userId);
       } catch (FeignException ex) {
           log.error(ex.getMessage());
       }
        userDto.setOrders(ordersList);

        log.info("After called orders microservice");

        return userDto;
    }
```

실행 결과
![](../imgs/Pasted%20image%2020260828121129.png)

## 9. Distributed Tracing
user-service, order-service 의존성 추가
- `micrometer-observation`
	- 애플리케이션의 동작(메서드 실행, HTTP 요청 처리 등)을 하나의 추상화된 API(`Observation`)로 관찰할 수 있게 해주는 핵심 라이브러리
	- 단 한번의 측정 포인트 설정으로 메트릭(Metric) 수집과 트레이싱(Tracing)을 동시에 처리할 수 있도록 연결해주는 표준 창구 역할을 수행함
- `micrometer-tracing-bridge-brave`
	- Micrometer의 추상화된 트레이싱 인터페이스를 실제 트레이싱 엔진인 Brave(Zipkin 프로젝트의 트레이서 라이브러리)로 연결해줍니다.
	- 고유한 요청 식별자인 **Trace ID**와 **Span ID**를 생성, 전파(Propagation), 관리하는 역할을 수행함.
		- SLF4J/Logback 등과 연동되어 로그에 TraceID를 자동으로 남겨준다.
- `brave-instrumentation-spring-web`
	- Srping Web(RestTemplate 등) 요청/응답 추적 자동화
	- Spring Web 기반의 HTTP 요청/응답 과정에 트레이스 정보(Trace Header: `b3` 등)를 인터셉터 형태로 자동으로 주입 및 추출(Instrumentation)
	- 서블릿 요청 처리 시간, HTTP URL, 응답 상태 코드 등의 메타데이터를 추적 데이터에 자동으로 포함시킴.
- `zipkin-reporter-brave`
	- 생성된 트레이스 데이터를 Zipkin 서버로 전송하는 라이브러리
	- Brave 트레이서가 수집한 Trace/Span 데이터를 Zipkin(분산 트레이싱 시각화 서버)이 이해할 수 있는 형식으로 수집 및 변환하여, 네트워크(HTTP/Kafka 등)를 통해서 Zipkin 서버로 전송(report)해주는 리포터 라이브러리
```xml
<dependency>  
    <groupId>io.micrometer</groupId>  
    <artifactId>micrometer-observation</artifactId>  
</dependency>  
<dependency>  
    <groupId>io.micrometer</groupId>  
    <artifactId>micrometer-tracing-bridge-brave</artifactId>  
</dependency>  
<dependency>  
    <groupId>io.zipkin.brave</groupId>  
    <artifactId>brave-instrumentation-spring-web</artifactId>  
</dependency>  
<dependency>  
    <groupId>io.zipkin.reporter2</groupId>  
    <artifactId>zipkin-reporter-brave</artifactId>  
</dependency>
```

user-service, order-service의 zipkin 관련 프로퍼티 설정
```yaml
spring:  
  zipkin:  
    base-url: http://localhost:9411  
    enabled: true
management:  
  tracing:  
    sampling:  
      probability: 1.0  
    propagation:  
      consume: B3  
      produce: B3  
  zipkin:  
    tracing:  
      endpoint: http://localhost:9411/api/v2/spans
```

zipkin docker-compose 파일 설정
- 트레이싱 정보를 mysql 데이터베이스에 저장하고자 합니다.
- `initdb.d` 디렉토리를 볼륨 설정해서 해당 디렉토리에 들어간 스크립트를 초기화 실행합니다. (테이블 초기 생성)
```yaml
version: '3'
services:
  zipkin:
    image: openzipkin/zipkin
    ports:
      - "9411:9411"
    environment:
      - STORAGE_TYPE=mysql
      - MYSQL_DB=zipkin
      - MYSQL_USER=zipkin
      - MYSQL_PASS=zipkin
      - MYSQL_HOST=mysql
  mysql:
    image: mysql:8.0.35
    platform: linux/arm64
    volumes:
      - ./initdb.d:/docker-entrypoint-initdb.d
    environment:
      MYSQL_DATABASE: zipkin
      MYSQL_USER: zipkin
      MYSQL_PASSWORD: zipkin
      MYSQL_ROOT_PASSWORD: root
    ports:
      - "3306:3306"
```

zipkin docker-compose 실행
```shell
docker compose up -d
```
![](../imgs/Pasted%20image%2020260828135739.png)


zipkin mysql 데이터베이스 테이블 생성 확인
```shell
docker exec -it zipkin-mysql-1 bash
mysql -u zipkin -p 
show databases;
```
![](../imgs/Pasted%20image%2020260828140033.png)

zipkin 관련 테이블 생성 확인
- 실행 결과를 보면 정상적으로 3개의 테이블이 생성됨
```mysql
use zipkin;
show tables;
```
![](../imgs/Pasted%20image%2020260828140059.png)


zipkin 서버 접속
- `http://localhost:9411` 접속하여 확인
![](../imgs/Pasted%20image%2020260828142716.png)

**사용자의 정보 상세 조회 및 주문 목록 조회 실행 결과**
user-service의 로그를 보면 `traceId=...a1c1`이고 `spanId=...116d` 입니다.
![](../imgs/Pasted%20image%2020260828151648.png)

order-service의 로그를 보면 `traceId=...a1c1`이고 `spanId=df59 입니다. 
![](../imgs/Pasted%20image%2020260828151821.png)

위 실행 결과를 분석하면 traceId는 동일하고 spanId가 서로 다른 것을 확인할 수 있습니다.


zipkin 서버를 이용하여 traceID 기반 요청/응답 조회
- 예를 들어 사용자 상세 정보 조회 및 주문 목록 요청에 대한 `traceId=6a912a40e3e23ebd7e0d041bfd1aab76`를 이용하여 검색합니다.
- 실행 결과를 보면 order-service 서비스에 주문 목록을 요청한 것을 확인할 수 있습니다. 또한 해당 spanID가 `...6e27`인것도 알수 있습니다.
![](../imgs/Pasted%20image%2020260828152857.png)

## 10. Swagger API (OAS 3.0)
의존성 추가
```xml
<!-- swagger oas -->  
<dependency>  
    <groupId>org.springdoc</groupId>  
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>  
    <version>2.2.0</version>  
</dependency>
```

SwaggerConfig
- 도움말 페이지를 설명하기 위한 설정입니다.
- 클라이언트에게 전달할 엔드포인트 정보를 등록합니다.
```java
@OpenAPIDefinition(  
        info = @Info(title = "User Service API specifications for MSA",  
                     description = "User Service API specifications with spring boot 3.2 + spring cloud.",  
                     version ="v1.0.0")  
)  
@Configuration  
@RequiredArgsConstructor  
public class SwaggerConfig {  
    @Bean  
    public GroupedOpenApi customTestOpenAPI() {  
        String[] paths = {"/users/**", "/welcome", "/health-check"};  
  
        return GroupedOpenApi.builder()  
                .group("일반 사용자 관리를 위한 User 도메인에 대한 API")  
                .pathsToMatch(paths)  
                .build();  
    }  
}
```

UserController에 태그 추가하기
```java
@RestController  
@RequestMapping("/")  
@Tag(name = "user-controller", description = "일반 사용자 서비스를 위한 컨트롤러입니다.")  
public class UserController {
	//...
}
```

UserController API에 `@Operation` 추가하기
- `@Operation`은 API 컨트롤러에서 각각의 HTTP 요청 엔드포인트를 설명하는데 사용되는 스웨거 및 오픈 API 문서의 요소입니다.
```java
@Operation(summary = "Health check API", description = "Health check를 위한 API (포트 및 Token Secret 정보 확인 가능)")
    @GetMapping("/health-check")
    @Timed(value="users.status", longTask = true)
    public String status() {
        // ...
    }
```

`@ApiResponse`를 추가하여 HTTP Response에 대한 설명 추가하기
- 각 응답코드별 설명을 추가합니다.
```java
	@Operation(summary = "사용자 정보 상세조회 API", description = "사용자에 대한 상세 정보조회를 위한 API (사용자 정보 + 주문 내역 확인)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized (인증 실패 오류)"),
            @ApiResponse(responseCode = "403", description = "Forbidden (권한이 없는 페이지에 엑세스)"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND (회원 정보가 없을 겨우)"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR"),
    }
    )
    @GetMapping("/users/{userId}")
    public ResponseEntity getUser(@PathVariable("userId") String userId) {
        // ...
    }
```

HTTP Request 객체 구조 설명 추가하기 (`@Schema`)
- 클래스 및 필드에 `@Schema` 애노테이션을 추가하여 제목 및 설명을 추가합니다.
```java
@Data
@AllArgsConstructor
@Schema(description = "A requested user object for user add")
public class RequestUser {
    @Schema(title = "사용자 Email", description = "사용자 ID로 사용되는 Email 정보로써 로그인 시 사용")
    @NotNull(message = "Email cannot be null")
    @Size(min = 2, message = "Email not be less than two characters")
    @Email
    private String email;

    @Schema(title = "사용자 이름", description = "사용자 이름")
    @NotNull(message = "Name cannot be null")
    @Size(min = 2, message = "Name not be less than two characters")
    private String name;

    @Schema(title = "사용자 암호", description = "로그인 시 사용되는 사용자 암호")
    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password must be equal or grater than 8 characters")
    private String pwd;
}

```

WebSecurity 설정에 스웨거 API 경로 추가
```java
http.authorizeHttpRequests((authz) -> authz
                        // ...
                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/swagger-resources/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
//                        .requestMatchers("/**").access(this::hasIpAddress)
                        .anyRequest().authenticated()
                )
                .authenticationManager(authenticationManager)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
```

실행 테스트
- http://localhost:60000/swagger-ui/index.html 접속
![](../imgs/Pasted%20image%2020260902120729.png)

## 11. Kubernetes 배포 1
Inner Architecture
- user-service
- order-service
- catalog-service

Outer Architecture
- Kafka
- config-service
- apigateway-service
- service-discovery

Kubernetes Inner Architecture 전환되는것
- user-service / order-service / catalog-service => deployment / pod

Kubernetes Outer Architecture 전환되는것
- service-discovery => service
- apigateway-service => service / ingress
- config-service => configmap / secret
- kafka => kafka
	- 해당 서비스는 대신할 수 없기 때문에 그대로 사용함

Kubernetes 배포 형태
![](../imgs/Pasted%20image%2020260902123210.png)

### Kafka Broker
도커 컨테이너 기반으로 실행할 카프카의 환경 변수 및 컨테이너 설정은 다음과 같습니다.

docker-compose-kafka.yml
- `192.168.65.4` IP 주소는 docker-desktop Internal IP 주소입니다.
	- `kubectl describe node docker-desktop | grep InternalIP`
```yaml
services:  
  broker:  
    image: apache/kafka:3.7.0  
    hostname: broker  
    container_name: broker  
    ports:  
      - '9092:9092'  
    environment:  
      KAFKA_NODE_ID: 1  
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: 'CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT'  
      KAFKA_ADVERTISED_LISTENERS: 'PLAINTEXT_HOST://192.168.65.4:9092,PLAINTEXT://broker:19092'  
      KAFKA_PROCESS_ROLES: 'broker,controller'  
      KAFKA_CONTROLLER_QUORUM_VOTERS: '1@broker:29093'
      KAFKA_LISTENERS: 'CONTROLLER://:29093,PLAINTEXT_HOST://:9092,PLAINTEXT://:19092'
      KAFKA_INTER_BROKER_LISTENER_NAME: 'PLAINTEXT'  
      KAFKA_CONTROLLER_LISTENER_NAMES: 'CONTROLLER'  
      CLUSTER_ID: '4L6g3nShT-eMCtK--X86sw'  
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1  
      KAFKA_GROUP_INITIAL_REBALANCE_DELAY_MS: 0  
      KAFKA_TRANSACTION_STATE_LOG_MIN_ISR: 1  
      KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: 1  
      KAFKA_LOG_DIRS: '/tmp/kraft-combined-logs'
```


`KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: 'CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT'` 의미
- CONTROLLER : 보안 프로포콜 PLAINTEXT, Kafka 3.x 이상 KRaft 모드에서 컨트롤러 노드간 메타데이터 동기화 통신시 암호화되지 않은 평문 통신을 사용합니다.
- PLAINTEXT : 보안 프로토콜 PLAINTEXT, Docker 내부 네트워크 안에서 다른 컨테이너들과 통신할때 사용할 기본 리스너
- PLAINTEXT_HOST : 보안 프로토콜 PLAINTEXT, Docker 외부(Host PC, 로컬 개발 환경)에서 `localhost:포트`로 접근하는 클라이언트를 위한 리스너입니다.

KAFKA_ADVERTISED_LISTENERS: 'PLAINTEXT_HOST://192.168.65.4:9092,PLAINTEXT://broker:19092'
- 클라이언트가 카프카 브로커에 최초 연결을 시도한 후 "실제 메시지를 주고받을때 접속해야 하는 브로커의 최종 주소"를 알려주는 안내판 역할입니다.
- `PLAINTEXT_HOST://192.168.65.4:9092`
	- PLAINTEXT_HOST 리스너로 접속을 시도한 클라이언트에게 "앞으로 이 브로커와 통신할때는 IP `192.168.65.4:9092`로 계속 요청하라"라고 전달합니다.
	- Docker 외부의 호스트 PC, 로컬 개발 환경, 외부 네트워크에 위치한 외부 마이크로서비스가 카프카에 접근할때 사용함
- `PLAINTEXT://broker:19092`
	- PLAINTEXT 리스너로 접속을 시도한 컨테이너에게 "앞으로 이 브로커와 통신할때는 Docker 내부 DNS 이름인 `broker`와 `19092` 포트로 접근해라"라고 전달합니다.
	- 주요 용도는 동일한 docker-compose.yml 내부 네트워크(bridge)에 떠있는 다른 컨테이너들이 카프카와 통신할때 사용합니다.

KAFKA_PROCESS_ROLES: 'broker,controller'
- kafka 3.0이상에서 zooKeeper 없이 작동하는 KRaft(Kafka Raft Metadata Mode) 아키텍처 설정
- 해당 카프카 노드가 메시지 입출력을 담당하는 'Broker' 역할과 클러스터 메타데이터 관리 및 조율을 담당하는 'Controller' 역할을 동시에 수행하도록 지정하는 구문

KAFKA_CONTROLLER_QUORUM_VOTERS: '1@broker:29093'
- 주키퍼(ZooKeeper) 없이 작동하는 KRaft(Kafka Raft) 모드에서 클러스터의 메타데이터 상태를 합의하고 관리할 컨트롤러 노드들의 목록을 지정하는 환경변수
- `1@broker:29093`
	- `1` (Node ID) : 투표권을 가진 컨트롤러 노드의 고유 ID입니다. 해당 카프카 컨테이너에 설정된 `KAFKA_NODE_ID: 1`값과 일치해야 합니다.
	- `broker`(Host/Domain): 해당 컨트롤러 노드가 실행중인 호스트 주소(또는 도커 컨테이너 서비스 이름)입니다.
	- `29093` (Port) : 컨트롤러 노드간 메타데이터 동기화 및 투표 통신에 사용하는 전용 포트

KAFKA_LISTENERS: 'CONTROLLER://:29093,PLAINTEXT_HOST://:9092,PLAINTEXT://:19092
- 카프카 브로커가 네트워크 요청을 바인딩하여 수신 대기할 IP주소와 포트를 정의하는 설정
- 호스트 IP를 명시하지 않고 `:포트`형태로 작성하면, 카프카는 `0.0.0.0`(모든 네트워크 인터페이스)에 바인딩됩니다.
- 컨테이너 내부의 localhost뿐만 아니라 Docker의 가상 Bridge IP, 외부 네트워크 인터페이스 등 해당 컨테이너로 들어오는 모든 IP의 요청을 수신하겠다는 의미
- 리스너
	- `CONTROLLER://:29093` : KRaft 모드 노드 간 메타데이터 동기화 및 투표 요청 수신
	- PLAINTEXT_HOST://://9092 : 호스트 PC 등 외부에서 들어오는 트래픽 수신
	- PLAINTEXT://:/19092 : 도커 내부 네트워크의 다른 컨테이너 트래픽 수신

KAFKA_INTER_BROKER_LISTENER_NAME: 'PLAINTEXT'
- 카프카 클러스터 내부에서 브로커(Broker) 노드끼리 통신할때 어떤 리스너(Listener) 채널을 사용할지 지정하는 설정
	- 카프카 브로커들은 클라이언트 요청 처리외에도 파티션 데이터 복제(Replication), 리더-팔로워 상태 관리, 내부 헬스체크 등을 위해 서로 통신합니다. 이때 외부 클라이언트용 채널과 분리된 내부 전용 통신 창구를 지정하는 구문

KAFKA_CONTROLLER_LISTENER_NAMES: 'CONTROLLER'
- 주키퍼 없이 작동하는 KRaft 모드에서 컨트롤러 노드들과 통신할때 사용할 전용 리스너의 이름을 지정하는 설정
- 브로커 노드들이 클러스터 메타데이터(파티션 리더 변경, 토픽 생성/삭제 정보 등)를 컨트롤러에게 요청하거나 수신할때 지정된 이 리스너 채널을 통해 통신하게 됩니다.

KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
- 카프카의 내부 시스템 토픽인 `__consumer_offsets` 토픽의 복제본 개수를 1개로 설정하는 옵션
- `__consumer_offsets` 토픽은 각 컨슈머 그룹이 파티션의 어디까지 메시지를 읽었는지 기록하는 매우 중요한 내부 토픽


KAFKA_GROUP_INITIAL_REBALANCE_DELAY_MS: 0
- 새로운 컨슈머가 컨슈머 그룹에 참여할때 파티션 재할당을 시작하기전 대기하는 시간을 0밀리초로 설정

KAFKA_TRANSACTION_STATE_LOG_MIN_ISR: 1
- 카프카에서 트랜잭션 상태 로그(`__transaction_state`) 파티션에 커밋을 성공시키기 위해 최소한 동기화되어 있어야 하는 브로커 개수(In-Sync Replicas)를 1개로 설정하는 옵션
- 단일 브로커 환경에서 Exactly-Once(정확히 한번) 트랜잭션 기능을 사용할 때 발생하는 부팅/커밋 에러를 방지하기 위한 핵심 개발용 옵션입니다.
- 기본값 : 2
	- 브로커가 1대인 경우에는 1로 설정해야함
	- 브로커가 1대인 상태에서 기본값(2)로 설정하면 트랜잭션을 사용하는 프로듀서/컨슈머 사용시 `NotEnoughReplicasException` 에러가 발생해서 트랜잭션 처리가 중단됨

KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: 1
- 카프카 내부의 트랜잭션 상태 로그(`__transaction_state`)의 복제본(Replication Factor) 수를 1개로 설정하는 옵션
- 카프카 프로듀서가 `read_committed`나 Exactly-Once(정확히 한번 delivery) 트랜잭션 보장을 사용할때 그 진행 상태를 기록하는 내부 토픽의 백업 개수를 정의합니다.

KAFKA_LOG_DIRS: '/tmp/kraft-combined-logs'
- 카프카 브로커가 카프카의 **실제 메시지 데이터, 파티션 로그, 그리고 메타데이터 파일**들을 저장할 컨테이너 내부의 디렉토리 경로를 지정하는 설정

#### docker-desktop 노드의 내부 IP 확인하기
k8s의 서비스들이 docker-desktop 위에서 실행중인 카프카와 통신하기 위해서는 docker-desktop의 내부 IP(Internal IP)가 필요합니다. 다음과 같은 명령어를 통해서 내부 IP를 확인할 수 있습니다.
```bash
kubectl describe node docker-desktop | grep InternalIP
```

#### k8s ConfigMap
**k8s/configmap.yml**
- user-service, order-service는 카프카에 대한 정보가 필요한데 이때 `bootstrap-servers` 정보(카프카 정보)를 참조합니다.
- `192.168.65.4` IP 주소는 docker-desktop 노드의 내부 IP(InternalIP) 주소입니다.
```yml
apiVersion: v1  
kind: ConfigMap  
metadata:  
  name: msa-k8s-configmap  
data:  
  gateway_ip: "192.168.65.4"  
  token_expiration_time: "86400000"  
  token_secret: "APPLE_YOUR_SECRETKEY_20240522_FOR_SPRING_CLOUD_LOGIN_#1"  
  order-service-url: "http://order-service:10000"  
  bootstrap-servers: "192.168.65.4:9092"
```

#### k8s ConfigMap & Deployment
user-service 서비스 배포시 k8s의 configmap의 설정을 참조합니다. 설정은 다음과 같습니다.

**k8s/user-deploy.yml**
```yml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: user-deploy
spec:
  selector:
    matchLabels:
      app: user-app
  replicas: 1
  template:
    metadata:
      labels:
        app: user-app
    spec:
      containers:
        - name: user-service
          image: nemo1107/user-service:k8s_v1.1
          imagePullPolicy: Always
          ports:
            - containerPort: 60000
              protocol: TCP
          resources:
            requests:
              cpu: 500m
              memory: 1000Mi
          env:
            - name: GATEWAY_IP
              valueFrom:
                configMapKeyRef:
                  name: msa-k8s-configmap
                  key: gateway_ip
            - name: TOKEN_EXPIRATION_TIME
              valueFrom:
                configMapKeyRef:
                  name: msa-k8s-configmap
                  key: token_expiration_time
            - name: TOKEN_SECRET
              valueFrom:
                configMapKeyRef:
                  name: msa-k8s-configmap
                  key: token_secret
            - name: ORDER-SERVICE-URL
              valueFrom:
                configMapKeyRef:
                  name: msa-k8s-configmap
                  key: order-service-url
---
apiVersion: v1
kind: Service
metadata:
  name: user-service
spec:
  type: NodePort
  selector:
    app: user-app
  ports:
    - protocol: TCP
      port: 60000
      targetPort: 60000
      nodePort: 30001
```

#### k8s Deployement & application.yml
user-service의 appliccation.yml 파일 내용에 다음과 같이 추가됩니다.
application.yml 파일에서 참조하는 해당 환경변수들은 k8s configmap의 설정을 최종적으로 참조하게 됩니다. 기존 방식으로는 spring config server로부터 설정을 참조하였습니다.
```yml
order-service-url: ${ORDER-SERVICE-URL}  
  
gateway:  
  ip: ${GATEWAY_IP}  
  
token:  
  expiration_time: ${TOKEN_EXPIRATION_TIME}  
  secret: ${TOKEN_SECRET}
```

#### user-service의 기존 application.yml 설정과 k8s 도입으로 인한 설정 변경
user-service.yml 파일의 기존 설정과 변경된 설정은 다음과 같습니다.

**기존 application.yml 설정**
기존  설정 같은 경우에는 spring service discovery 서비스를 사용했기 때문에 유레카에 대한 설정 정보가 들어있었습니다.
```yml
eureka:  
  instance:  
    instance-id: ${spring.application.name}:${spring.application.instance_id:${random.value}}  
    service-url:  
      defaultZone: http://127.0.0.1:8761/eureka  
    fetch-registry: true  
    register-with-eureka: true
```

**k8s 도입으로 인한 변경된 application.yml 설정**
- k8s의 Service 도입으로 인해서 Service가 Service Discovery 역할을 수행할 예정이기 때문에 다음 설정을 주석 처리합니다.
```yml
#eureka:  
#  instance:  
#    instance-id: ${spring.application.name}:${spring.application.instance_id:${random.value}}  
#  #    prefer-ip-address: true  
#  #    ip-address: ${server.address}  
#  client:  
#    service-url:  
#      defaultZone: http://127.0.0.1:8761/eureka  
#    fetch-registry: true  
#    register-with-eureka: true
```

#### User-Service 코드 변경
현재 user-service가 다른 서비스에 요청하기 위해서 OpenFeign을 사용하고 있습니다. 이때 order-service에 요청하기 위해서 OrderServiceClient에 url 옵션을 추가해야 합니다. 다음 코드에서 참조하는 `order-service-url` 환경변수는 application.yml에 있는 키를 참조합니다. 그리고 application.yml 파일에 있는 키값은 최종적으로 k8s의 configmap의 값(`http://order-service:10000`)을 최종적으로 참조하게 됩니다.
```java
@FeignClient(name="order-service", configuration = FeignErrorDecoder.class, url = "${order-service-url}")  
public interface OrderServiceClient {  
  
    @GetMapping("/order-service/{userId}/orders")  
    List<ResponseOrder> getOrders(@PathVariable String userId);  
}
```



#### user-service WebSecurity 설정 변경
user-service의 `/**` 경로로 접근하기 위해서는 특정 IP만 접근하도록 설정합니다.
그래서 IP 주소를 k8s 노드의 IP 정보(docke-desktop InternalIP 정보)로 변경합니다.
- `hasIpAddress('192.168.65.3)`
```java
http.authorizeHttpRequests((authz) -> authz  
                        .requestMatchers(new AntPathRequestMatcher("/actuator/**")).permitAll()  
                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()  
                        .requestMatchers(new AntPathRequestMatcher("/users", "POST")).permitAll()  
                        .requestMatchers(new AntPathRequestMatcher("/welcome")).permitAll()  
                        .requestMatchers(new AntPathRequestMatcher("/health-check")).permitAll()  
                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()  
                        .requestMatchers(new AntPathRequestMatcher("/swagger-resources/**")).permitAll()  
                        .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()  
//                        .requestMatchers("/**").access(this::hasIpAddress)  
                        .requestMatchers("/**").access(  
                                new WebExpressionAuthorizationManager(  
                                        "hasIpAddress('127.0.0.1') or hasIpAddress('192.168.65.4')"))  
                        .anyRequest().authenticated()  
                )  
                .authenticationManager(authenticationManager)  
                .sessionManagement((session) -> session  
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
```

#### order-service 카프카 설정 변경
**k8s/order-deploy.yml**
```yml
apiVersion: apps/v1  
kind: Deployment  
metadata:  
  name: order-deploy  
spec:  
  selector:  
    matchLabels:  
      app: order-app  
  replicas: 1  
  template:  
    metadata:  
      labels:  
        app: order-app  
    spec:  
      containers:  
        - name: order-service  
          image: nemo1107/order-service:k8s_v1.1
          imagePullPolicy: Always  
          ports:  
            - containerPort: 10000  
              protocol: TCP  
          resources:  
            requests:  
              cpu: 500m  
              memory: 1000Mi  
          env:  
            - name: BOOTSTRAP-SERVERS  
              valueFrom:  
                configMapKeyRef:  
                  name: msa-k8s-configmap  
                  key: bootstrap-servers  
---  
apiVersion: v1  
kind: Service  
metadata:  
  name: order-service  
spec:  
  type: NodePort  
  selector:  
    app: order-app  
  ports:  
    - protocol: TCP  
      port: 10000  
      targetPort: 10000  
      nodePort: 30002
```

**order-service application.yml**
```yml
spring:  
  application:  
    name: order-service  
  kafka:  
    producer:  
      bootstrap-servers: ${BOOTSTRAP-SERVERS}
```

**KafkaProducerConfig.java**
- 환경 변수 참조를 통해서 `spring.kafka.producer.bootstra-servers` 프로퍼티를 참조하여 카프카와 통신하고자 합니다.
```java
@EnableKafka  
@Configuration  
public class KafkaProducerConfig {  
  
    @Autowired  
    private Environment env;  
  
    @Bean  
    public ProducerFactory<String, String> producerFactory() {  
        Map<String, Object> properties = new HashMap<>();  
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("spring.kafka.producer.bootstrap-servers"));  
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);  
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);  
  
        return new DefaultKafkaProducerFactory<>(properties);  
    }  
  
    @Bean  
    public KafkaTemplate<String, String> kafkaTemplate() {  
        return new KafkaTemplate<>(producerFactory());  
    }  
}
```

#### Catalog-Service 코드 변경
application.yml
```yaml
server:  
  port: 8080  
  
spring:  
  application:  
    name: catalog-service  
  kafka:  
    consumer:  
      bootstrap-servers: ${BOOTSTRAP-SERVERS:http://localhost:9092}  
      group-id: consumer_group01  
      auto-offset-reset: earliest  
  h2:  
    console:  
      enabled: true  
      settings:  
        web-allow-others: true  
      path: /h2-console  
  jpa:  
    hibernate:  
      ddl-auto: create-drop  
    show-sql: true  
    generate-ddl: true  
    database: h2  
    defer-datasource-initialization: true  
  datasource:  
    driver-class-name: org.h2.Driver  
    url: jdbc:h2:mem:testdb  
    username: sa  
    password:  
#    data: classpath:post.sql  
  
#eureka:  
#  instance:  
#    instance-id: ${spring.application.name}:${spring.application.instance_id:${random.value}}  
#  client:  
#    register-with-eureka: true  
#    fetch-registry: true  
#    service-url:  
#      defaultZone: http://127.0.0.1:8761/eureka  
  
logging:  
  level:  
    com.example.catalogservice: DEBUG
```

KafkaConsumerConfig.java
```java
@EnableKafka  
@Configuration  
public class KafkaConsumerConfig {  
  
    @Autowired  
    private Environment env;  
  
    @Bean  
    public ConsumerFactory<String, String> consumerFactory() {  
        Map<String, Object> properties = new HashMap<>();  
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("spring.kafka.consumer.bootstrap-servers"));  
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "consumerGroupId");  
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");  
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);  
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);  
  
        return new DefaultKafkaConsumerFactory<>(properties);  
    }  
  
    @Bean  
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {  
        ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory  
                = new ConcurrentKafkaListenerContainerFactory<>();  
        kafkaListenerContainerFactory.setConsumerFactory(consumerFactory());  
  
        return kafkaListenerContainerFactory;  
    }  
}
```

#### k8s Deployment
다음 설정은 user-service를 k8s 기반으로 배포하기 위한 설정입니다.
**user-deploy.yml**
```yml
apiVersion: apps/v1  
kind: Deployment  
metadata:  
  name: user-deploy  
spec:  
  selector:  
    matchLabels:  
      app: user-app  
  replicas: 1  
  template:  
    metadata:  
      labels:  
        app: user-app  
    spec:  
      containers:  
        - name: user-service  
          image: nemo1107/user-service:1.0  
          imagePullPolicy: Always  
          ports:  
            - containerPort: 60000  
              protocol: TCP  
          resources:  
            requests:  
              cpu: 500m  
              memory: 1000Mi  
          env:  
            - name: GATEWAY_IP  
              valueFrom:  
                configMapKeyRef:  
                  name: msa-k8s-configmap  
                  key: gateway_ip  
            - name: TOKEN_EXPIRATION_TIME  
              valueFrom:  
                configMapKeyRef:  
                  name: msa-k8s-configmap  
                  key: token_expiration_time  
            - name: TOKEN_SECRET  
              valueFrom:  
                configMapKeyRef:  
                  name: msa-k8s-configmap  
                  key: token_secret  
            - name: ORDER-SERVICE-URL  
              valueFrom:  
                configMapKeyRef:  
                  name: msa-k8s-configmap  
                  key: order-service-url  
---  
apiVersion: v1  
kind: Service  
metadata:  
  name: user-service  
spec:  
  type: NodePort  
  selector:  
    app: user-app  
  ports:  
    - protocol: TCP  
      port: 60000  
      targetPort: 60000  
      nodePort: 30001
```

user-service에 대한 pod 설정과 외부에서 접속하기 위한 Service 설정을 추가합니다.

**catalog-deploy.yml**
```yaml
apiVersion: apps/v1  
kind: Deployment  
metadata:  
  name: catalog-deploy  
spec:  
  selector:  
    matchLabels:  
      app: catalog-app  
  replicas: 1  
  template:  
    metadata:  
      labels:  
        app: catalog-app  
    spec:  
      containers:  
        - name: catalog-service  
          image: nemo1107/catalog-service:k8s_v1.1
          imagePullPolicy: Always  
          ports:  
            - containerPort: 8080  
              protocol: TCP  
          resources:  
            requests:  
              cpu: 500m  
              memory: 1000Mi  
          env:  
            - name: BOOTSTRAP-SERVERS  
              valueFrom:  
                configMapKeyRef:  
                  name: msa-k8s-configmap  
                  key: bootstrap-servers  
---  
apiVersion: v1  
kind: Service  
metadata:  
  name: catalog-service  
spec:  
  type: NodePort  
  selector:  
    app: catalog-app  
  ports:  
    - protocol: TCP  
      port: 8080  
      targetPort: 8080  
      nodePort: 30003
```


#### 배포
**k8s node ip address 확인**
```sh
kubectl get nodes
kubectl describe node <docker-desktop>
```

![](../imgs/Pasted%20image%2020260903140215.png)

![](../imgs/Pasted%20image%2020260903140307.png)

```shell
kubectl describe node docker-desktop | grep InternalIP
```
![](../imgs/Pasted%20image%2020260903140343.png)

**kafka 실행**
```shell
docker-compose -f docker-compose-kafka.yml up -d
```

**k8s deployment, service 실행**
```sh
kubectl apply -f k8s/configmap.yml
kubectl apply -f k8s/user-deploy.yml
kubectl apply -f k8s/order-deploy.yml
kubectl apply -f k8s/catalog-deploy.yml
```

#### 실행
kafka 실행 결과 확인
```
docker-compose -f docker-compose-kafka.yml up -d
docker-compose -f docker-compose-kafka.yml ps
```
![](../imgs/Pasted%20image%2020260903142927.png)

k8s Service 상태 확인
```shell
kubectl get svc
```
![](../imgs/Pasted%20image%2020260903153306.png)

k8s Deployment 상태 확인
```shell
kubectl get deploy
```
![](../imgs/Pasted%20image%2020260903153315.png)

k8s Pod 상태 확인
```shell
kubectl get pod
```
![](../imgs/Pasted%20image%2020260903153325.png)

k8s 실행 중지
```shell
kubectl delete -f k8s/catalog-deploy.yml
kubectl delete -f k8s/order-deploy.yml
kubectl delete -f k8s/user-deploy.yml
kubectl delete -f k8s/configmap.yml
```


#### 각 서비스별 k8s 도커 이미지 빌드 및 푸시
user-service
```shell
docker build -t nemo1107/user-service:k8s_v1.1 .
```

order-service
```shell
docker build -t nemo1107/order-service:k8s_v1.1 .
```

catalog-service
```shell
docker build -t nemo1107/catalog-service:k8s_v1.1 .
```

도커 이미지 푸시
```shell
docker push nemo1107/user-service:k8s_v1.1
docker push nemo1107/order-service:k8s_v1.1
docker push nemo1107/catalog-service:k8s_v1.1
```

#### k8s기반 서비스 실행 테스트
회원가입
- path : `POST http://localhost:30001/users`
![](../imgs/Pasted%20image%2020260903155117.png)

로그인
![](../imgs/Pasted%20image%2020260903155835.png)
![](../imgs/Pasted%20image%2020260903155846.png)

회원 목록 조회
![](../imgs/Pasted%20image%2020260903155959.png)

상품 목록 조회
![](../imgs/Pasted%20image%2020260903164820.png)

상품 서비스 헬스체크
![](../imgs/Pasted%20image%2020260903164831.png)

상품 주문
![](../imgs/Pasted%20image%2020260903164938.png)

상품 주문 조회
![](../imgs/Pasted%20image%2020260903165022.png)

회원 상세 보기 + 주문 목록 조회
![](../imgs/Pasted%20image%2020260903165144.png)

상품 주문후 줄어든 상품 목록 조회
- 실행 결과를 보면 stock의 개수가 100 -> 95로 감소된 것을 확인할 수 있다.
![](../imgs/Pasted%20image%2020260903165227.png)

**Order-Service 상품 주문 로그 확인**
![](../imgs/Pasted%20image%2020260903165924.png)

Catalog-Service 상품 주문 로그 확인
![](../imgs/Pasted%20image%2020260903170006.png)

**카프카 consumer 로그 확인**
consumer를 확인하여 상품 주문 메시지 확인합니다.
```sh
export KAFKA_HOME=/opt/kafka

$KAFKA_HOME/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic example-catalog-topic --from-beginning
```
![](../imgs/Pasted%20image%2020260903165709.png)



## 섹션 소개
- CircuitBreaker
- Resilience4j
- Distributed Tracing
- Trace ID and Span ID
- Zipkin server 활용

## CircuitBreaker와 Resilience4j
### Microservice 통신 시 연쇄 오류
회원 정보 상세 보기 및 주문 목록 조회 API를 요청해봅니다. 그리고 서버는 500 Error를 응답합니다. 이유는 Order Service에서 데이터를 가져오지 못하고 500 Error를 응답하였기 때문입니다. 
![](../imgs/Pasted%20image%2020251120121413.png)
![](../imgs/Pasted%20image%2020251120122908.png)

위 테스트에서 에러가 발생한 과정을 그림으로 표현하면 다음과 같습니다. User SErvice에서 Feign Client를 이용하여 Order Service에 요청을 보냈지만 Order Service의 인스턴스는 요청을 처리하지 못하고 500 에러를 응답합니다. User Service 또한 별도의 에러 처리가 존재하지 않기 때문에 클라이언트에게 500에러를 그대로 응답합니다.
사용자 정보 조회 및 주문 목록 조회는 Order Service만이 아니라 Catalog Service 까지 조회를 통해서 처리하는데, 만약에 Order Service가 정상적이고 Catalog Service가 이용 불가능하면 위 테스트와 동일한 500에러가 발생할 것입니다.
위와 같은 Mircoservice간에 통신 문제를 해결하기 위해서는 다른 서비스로부터 에러를 응답받으면 그 에러를 대신할 수 있는 Default 값이나 우회할 수 있는 값 또는 정상적인 데이터처럼 보여줄 수 있는 값으로 대체하는 것을 권장합니다. 예를 들어 Feign Client를 이용하여 주문 목록 조회에 실패하면 Fallback Method를 실행하여 대체할 수 있는 값으로 응답합니다.
![](../imgs/Pasted%20image%2020251120120718.png)

예를 들어 Order Service에 문제가 발생하여 대체 방법으로 빈 주문 리스트를 반환한다고 가정하면 사용자 입장에서는 주문 목록은 볼수 없어도 사용자 정보는 조회할 수 있습니다.

### CircuitBreaker
- https://martinfowler.com/bliki/CircuitBreaker.html
- 장애가 발생하는 서비스에 반복적인 호출이 되지 못하게 차단
- 특정 서비스가 정상적으로 동작하지 않은 경우 다른 기능으로 대체 수행 -> 장애 회피

다음 그림을 보면 왼쪽에는 클라이언트가 있고 오른쪽에는 서비스를 제공하는 suplier가 있습니다. 중간에 circuit breaker가 위치하여 제일 위의 시퀀스 다이어그램을 보면 정상적으로 데이터를 요청 및 응답하는 것을 볼수 있습니다.
2번째 시퀀스 다이어그램을 보면 클라이언트가 요청하고 supplier로부터 timeout 응답이 옵니다. 그리고 circuit breaker 또한 timeout을 그대로 전달합니다.
그리고 3번째 시퀀스 다이어그램을 보면 클라이언트가 동일하게 요청하고 supplier가 동일하게 timeout을 요청하면 클라이언트에게 timeout을 응답하고 circuit breaker가 trip을 발동시켜서 그 이후에 요청에 대해서는 대체 값을 응답합니다.
![](../imgs/Pasted%20image%2020251120123501.png)

Circuit Breaker Closed/Open
- Circuit Breaker Closed : 마이크로서비스간 통신이 원할함
- Circuit Breaker Open : 마이크로서비스간에 통신이 원할하지 않아서 대체 값 같은 것을 응답함
	- Circuit Breaker가 Open되기 위한 기준은 다양합니다. 예를 들어 10초동안 30번의 요청시 절반이 실패하는 경우가 있습니다. 또는 70%의 데이터가 응답하지 않은 경우
![](../imgs/Pasted%20image%2020251120124704.png)

### Spring Cloud Netflix Hystrix
의존성 추가
```xml
<dependency>  
    <groupId>org.springframework.cloud</groupId>  
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>  
</dependency>
```

활성화 애노테이션 추가
```java
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
public class PhotoappusersApplication{
}
```

application.yaml 파일 수정
- feign.hystrix 활성화
```yaml
feign:
  hystrix:
    enabled: true
```

Spring Boot Netflix Hystrix는 유지보수만 하는 라이브러리가 됨. 이를 대체하기 위해서 Resilience4j 사용을 권장함.

### Resilience4j
Resilience4j 지원 기능
- resilience4j-circuitbreaker : Cricuit breaking
- resilience4j-ratelimiter : Rate limiting
- resilience4j-bulkhead : Bulkheading
- resilience4j-retry : Automatic retrying (sync and async)
- resilience4j-timelimiter : Timeout handling
- resilience4j-cache : Result caching
- https://resilience4j.readme.io/docs/getting-started
- https://github.com/resilience4j/resilience4j

Resilience4j는  Neflix Hystrix와는 다르게 경량형이고 사용하기 쉽습니다. 그리고 Fault Tolerence Library로써 일부 컴포넌트가 실패해도 전체 서비스가 중단되지 않도록 보호해주는 라이브러리입니다. 그리고 자바8이고 함수형 프로그래밍으로 설계되어 있습니다.

**기본 설정**
```xml
<dependency>  
    <groupId>org.springframework.cloud</groupId>  
    <artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>  
</dependency>
```

UserServiceImpl 클래스 수정
- CircuitBreakerFactory 필드 주입
- CircuitBreakerFactory로 circuitBrekaer 객체 생성후 해당 객체를 이용하여 내부적으로 OrderService에 요청 및 fallback을 설정함
	- fallback 메서드 실행시 빈 리스트를 반환함

![](../imgs/Pasted%20image%2020251120135652.png)
![](../imgs/Pasted%20image%2020251120135812.png)

`Customizer<Resilience4JCircuitBreakerFactory>` 스프링 빈 생성
CircuitBreaker 설정 옵션
- failureRateThreshold
	- CircuitBreaker를 열지 결정하는 실패 비율 기준 퍼센트
	- 예를 들어 값이 4라면 100번중에 4번 실패하면 CircuitBreaker가 Open 상태가 됩니다.
	- 기본값 : 50
- waitDurationInOpenState
	- CircuitBreaker를 open 상태를 얼마나 유지할지에 대한 옵션
	- 해당 기간 이후에 half-open 상태 변경
	- 기본값 60초
- slidingWindowType
	- CircuitBreaker가 닫힐때 통화 결과를 기록하는데 사용되는 슬라이딩 창의 유형을 설정
	- 카운트 기반 또는 숫자 기반
- slidingWindowSize
	- CircuitBreaker가 닫힐때 호출 결과를 기록하는데 사용되는 슬라이딩 창의 크기 설정
	- 기본값 : 100

TimeLimitConfig 설정 옵션
- timeoutDuration
	- future supplier의 time limit을 정하는 API
	- 설정한 시간동안 응답이 없으면 실패로 간주함
	- 기본값 : 1초

코드 구현
![](../imgs/Pasted%20image%2020251120142217.png)


User-Service 테스트
- 사용자 정보 및 주문 목록 조회 요청합니다.
- Order-Service는 일부로 실행시키지 않고 에러를 유발시켜봅니다.
실행 결과를 보면 Order-Service가 실행되고 있지 않기 때문에 비어있는 주문 목록이 반환된 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251120144752.png)

로깅 결과를 보면 Order Service에 대해서 UnknownHostException 예외가 발생한 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251120144934.png)

Order-Service Controller 일부 주석 처리
- CircuitBreaker를 테스트하기 위해서 기존 kafka를 이용해서 전송하는 부분을 주석 처리하고 jpa를 사용하도록 수정
![](../imgs/Pasted%20image%2020251120145804.png)

상품 주문 테스트
- Order-Service 실행시 db를 mariadb가 아닌 h2 데이터베이스로 테스트합니다.
- 실행 결과를 보면 정상적으로 상품이 주문된 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251120151016.png)

사용자 정보 및 주문 목록 조회 테스트
- 실행 결과를 보면 정상적으로 주문 목록을 조회한 것을 볼수 있음
![](../imgs/Pasted%20image%2020251120151106.png)

사용자 정보 및 주문 목록 조회
- 이번에는 Order-Service 서버를 중지한 다음에 다시 요청해봅니다.
- 실행 결과를 보면 Order-Service로부터 주문 정보를 얻지 못했기 때문에 대체 방법인 빈 리스트를 응답합니다.
![](../imgs/Pasted%20image%2020251120151228.png)
![](../imgs/Pasted%20image%2020251120151253.png)

위 실습을 통해서 알수 있는 점은 Resilience4J를 이용해서 다른 마이크로서비스가 통신이 안되어 에러 응답이 발생하면 대체할 수 있는 방법을 제공한다는 것입니다.

## 분산 추적의 개요, Zipkin 서버 설치
### Microservice 분산 추적
Zipkin
- https://zipkin.io/
- 트위터(Twitter)에서 사용하는 분산 환경의 Timing 데이터 수집, 추적 시스템(오픈 소스)
- Google Drapper에서 발전했으며, 분산환경에서의 시스템 병목 현상 파악
- Collector, Query Service, Database, WebUI로 구성
- **Span**
	- 하나의 요청에 사용되는 작업 단위
	- 64 bit unique ID
- **Trace**
	- 트리 구조로 이루어진 Span 집합
	- 하나의 요청에 대한 같은 TraceID 발급

![](../imgs/Pasted%20image%2020251120152651.png)

### Spring Cloud Sleuth(슬루스)
- Spring Boot 애플리케이션을 Zipkin과 연동하는데 사용함
- 요청값에 Trace ID, Span ID 부여
- Trace와 Span Ids를 로그에 추가 가능
	- servlet filter
	- rest template
	- scheduled actions
	- message channels
	- feign client
![](../imgs/Pasted%20image%2020251120155006.png)

Spring Cloud Sleuth + Zipkin
다음 그림을 보면 MicroserviceA가 B에게 요청하고 B는 C에게 요청하고, C는 E에게 요청합니다. 마이크로서비스간에 각각의 요청마다 새로운 SpanID가 할당되고, 전체적인 요청에 대해서는 하나의 TraceID가 할당됩니다.
![](../imgs/Pasted%20image%2020251120160228.png)

만약 위와 같은 상태에서 Microservice A가 새로운 요청을 Microservice B에게 요청한다면 다음과 같이 분산 추적할 수 있습니다.
![](../imgs/Pasted%20image%2020251120160741.png)

### Zipkin 서버 설치
Docker를 이용한 설치 방법
- https://hub.docker.com/r/openzipkin/zipkin/
```shell
docker run -d -p 9411:9411 openzipkin/zipkin
```

Java jar 파일을 이용한 설치 방법
```shell
curl -sSL https://zipkin.io/quickstart.sh | bash -s
java -jar zipkin.jar
```

여기서는 docker를 이용하여 설치 및 실행하겠습니다.

zipkin 실행
```shell
docker run -d -p 9411:9411 openzipkin/zipkin
```
![](../imgs/Pasted%20image%2020251120161716.png)

## Spring Cloud Sleuth + Zipkin을 이용한 Microservice의 분산 추적
### User Mircroservice 수정
의존성 추가
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
    <groupId>io.zipkin.reporter2</groupId>  
    <artifactId>zipkin-reporter-brave</artifactId>  
    <version>3.5.1</version>  
</dependency>  
<dependency>  
    <groupId>io.github.openfeign</groupId>  
    <artifactId>feign-micrometer</artifactId>  
</dependency>
```

zipkin 관련 URL 연결 정보 추가
![](../imgs/Pasted%20image%2020251123151742.png)

UserServiceImpl 클래스의 메서드에 로깅 추가
- 서비스를 분산 추적하기 위해서 order service 호출 전후로 로깅을 남김
![](../imgs/Pasted%20image%2020251120164450.png)

order-service controller 수정
- order service의 로깅도 추적하기 위해서 주문 데이터 생성 전후로 로깅을 남김
- order-service에도 zipkin과 mircometer 의존성을 추가해야 한다.
![](../imgs/Pasted%20image%2020251120164735.png)

order-service 의존성 추가
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
    <groupId>io.zipkin.reporter2</groupId>  
    <artifactId>zipkin-reporter-brave</artifactId>  
    <version>3.5.1</version>  
</dependency>  
<dependency>  
    <groupId>io.github.openfeign</groupId>  
    <artifactId>feign-micrometer</artifactId>  
</dependency>
```

order-service application.yaml 파일 수정
![](../imgs/Pasted%20image%2020251123151757.png)

상품 주문 테스트
![](../imgs/Pasted%20image%2020251123151808.png)

상품 주문에 대한 로깅 결과 확인
- TraceID : `6922a66eeb952a6bbcda81d5956c07bd`
- SpanID : `bcda81d5956c07bd`
![](../imgs/Pasted%20image%2020251123151831.png)

위 TraceID=`6922a66eeb952a6bbcda81d5956c07bd`를 이용하여 Zipkin에서 검색해봅니다.
실행 결과를 보면 상품 주문에 대한 추적 정보가 출력된 것을 볼수 있습니다. 실행 결과 창을 보면 응답하기까지의 응답 시간(87.563ms)과 SpanID, 요청 URL이 출력된 것을 볼수 있습니다. 
![](../imgs/Pasted%20image%2020251123151934.png)

회원 상세 보기 + 주문 목록 조회
![](../imgs/Pasted%20image%2020251123152206.png)

User-Service 로깅 결과 확인
- UserServiceImpl TraceID : `6922a80904118fdcbdf76c64a2e3ec6a`
- UserServiceImpl SpanID : `4aca8f42b1acbf04`
- OrderServiceClient TraceID : `6922a80904118fdcbdf76c64a2e3ec6a`
- OrderServiceClient SpanID : `a0cb69787513ad45`
실행 결과를 보면 UserServiceImpl과 OrderServiceClient의 TraceID는 동일하고 SpanID는 서로 다른 것을 볼수 있습니다. 이는 OpenFeign을 사용하면서 새로운 클라이언트를 생성하였기 때문에 SpanID 또한 달라졌기 때문입니다.
![](../imgs/Pasted%20image%2020251123152224.png)
![](../imgs/Pasted%20image%2020251123152425.png)

Order-Service 주문 목록 조회 로깅 결과 확인
- OrderController TraceID : `6922a80904118fdcbdf76c64a2e3ec6a`
- OrderController SpanID : `2100636c30dbd956`
실행 결과를 보면 OrderController의 TraceID가 User-Service의 TraceID와 동일한 것을 볼수 있습니다. 이는 하나의 작업으로 보기 때문에 zipkin을 이용한 트레이스 추적이 가능합니다.
![](../imgs/Pasted%20image%2020251123152452.png)

Zipkin을 이용한 Trace 추적
위 실습에서 기록된 `6922a80904118fdcbdf76c64a2e3ec6a` 트레이스에 대한 추적을 해봅니다.
다음 실행 결과를 보면 해당 트레이스가 어떻게 추적되는지 볼수 있습니다. user-service에서 order-service로 갔다가 되돌아 온것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251123152721.png)

서비스 이름으로 분산 추적하기
다음과 같이 상단 메뉴의 "Find a trace" 메뉴를 통하여 특정한 서비스 이름을 선택하여 추적할 수 있습니다.
![](../imgs/Pasted%20image%2020251123155109.png)

다음과 같이 특정한 Trace를 선택하여 상세보기도 가능합니다.
![](../imgs/Pasted%20image%2020251123155412.png)
![](../imgs/Pasted%20image%2020251123155427.png)

Dependencies를 이용한 분산 추적
다음과 같이 서비스간에 의존성을 확인하고 Request 개수 또한 카운팅 된것을 확인할 수 있습니다.
![](../imgs/Pasted%20image%2020251123155553.png)

### 오류가 발생하는 경우 분산 추적하기
OrderController 장애 발생하도록 수정
- 주문 목록을 가져온 다음에 Exception 예외를 발생시켜서 클라이언트에 500 에러를 응답하도록 합니다.
- InterruptedException 예외는 Exception 예외 발생시 캐치하지 못함
![](../imgs/Pasted%20image%2020251123162421.png)

상품 주문 테스트
- 상품 주문시 별도의 예외 발생시 없기 때문에 성공적임
![](../imgs/Pasted%20image%2020251123162625.png)

사용자 정보 조회 및 주문 목록 조회
- User-Service에서 Order-Service에 주문 목록 조회를 요청하고 실패 응답이 오면 빈 리스트를 응답하기로 하였기 때문에 다음과 같이 빈 주문 목록이 출력된 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251123162656.png)

Order-Service 로깅 확인
실행 결과를 보면 성공적으로 ERROR 로깅이 출력된 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251123162822.png)

TraceID를 이용한 분산 추적하기
TraceID=`6922b737122f49021346514f06d49c66`를 이용하여 Zipkin에서 트레이스 추적해봅니다.
실행 결과를 보면 order-service에서 에러가 발생했음을 볼수 있습니다. 또한 예외의 종류와 에러 메시지 또한 확인할 수 있습니다.
![](../imgs/Pasted%20image%2020251123162947.png)


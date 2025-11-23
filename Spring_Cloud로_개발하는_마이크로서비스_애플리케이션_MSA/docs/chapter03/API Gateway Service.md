
## 섹션 소개
- API Gateway Service
- Netflix Ribbon & Zuul
- Spring Cloud Gateway - 기본
- Spring Cloud Gateway - Filter
- Spring Cloud Gateway - Eureka 연동
- Spring Cloud Gateway - Load Balancer

## API Gateway란?
**API Gatway 개념**
- API Gateway는 라우팅 설정을 기반으로 클라이언트의 요청을 대신 받고 서버로 전달하고, 서버의 응답을 받아서 클라이언트에게 전달하는 프록시 역할을 수행합니다.
- API Gateway를 사용하면 클라이언트는 서버의 정보를 직접적으로 알지 않아도 됩니다.
- API Gateway를 사용하면 단일 진입점을 생성 가능합니다.
- API Gateway 뒤에 마이크로서비스가 몇개가 있든 클라이언트는 API Gateway의 위치만 알고 있으면 됩니다.

**API Gateway 역할**
- 인증 및 권한 부여
- 서비스 검색 통합
- 응답 캐싱
- 정책, 회로 차단기(Circuit Breaker) 및 QoS 다시 시도
- 속도 제한
- 부하 분산
- 로깅, 추적, 상관 관계
- 헤더, 쿼리 문자열 및 요청 반환
- IP 허용 목록에 추가

### Netflix Ribbion (Deprecated)
Spring Cloud에서의 마이크로서비스 아키텍처간 통신하기 위해서 두가지 방법을 사용합니다.
1. RestTempate
2. Feign Client

**RestTemplate를 이용한 MSA간 통신**
- RestTemplate는 HTTP 프로토콜 기반으로 메서드에 적절한 URL을 포함시켜 데이터를 호출하는 방식
```java
RestTemplate restTemplate = new RestTemplate();
restTemplate.getForObject("http://localhost:8080/", User.class, 200); 
```

**Feign Client를 이용한 MSA간 통신**
- `@FeginClient` 애노테이션의 "stores"는 접속하고자 하는 도메인 이름 주소
```java
@FeignClient("stores")
public interface StoreClient{
	@RequestMapping(method = RequestMethod.GET, value="/stores")
	List<Store> getStores();
}
```

Ribbion : Ribbon은 Client Side에서 Load Balancer 역할을 수행합니다.
- 서비스 이름으로 호출
- Health Check

**Deprecated Netflix Ribbon**
여러가지 서비스가 존재한다고 가정할 때 클라이언트 사이드에서 Ribbon 서비스가 붙어서 어떤 서비스로 갈 것인지 각각 로드밸런싱 처리를 해줍니다. 이러한 Ribbon은 클라이언트 사이드의 복잡성을 추가하고 여러가지 문제가 있기 때문에 현재는 더이상 사용되지 않습니다.
그래서 Netflix Ribbon을 사용하는 것보다 Spring Cloud에서 제공하는 Cloud LoadBalancer 또는 Spring Cloud Gateway로 변경하여 사용하는 것이 좋습니다.


### Netflix Zuul 구현 (Deprecated)
백엔드에 First, Second Service 2개가 존재하고 클라이언트가 존재합니다. 그리고 API Gateway 역할을 하는 Netflix Zuul 서버가 존재한다고 가정합니다.
- 함수형 프로그래밍을 지원하지 않음
- Spring에서 지원하는 WebFlux도 지원하지 않음
- 기능 확장에 문제가 있고 에러 핸들링에도 문제가 있음
- Ribbon이 사용하지 않게됨에 따라서 그와 연동된 Zuul도 사용하지 않게됨

클라이언트가 Netflix Zuul API Gateway를 통하여 서비스에 요청을 전달합니다. Zuul 또한 API Gateway 역할로써 라우팅, API Gateway 역할을 수행합니다.

Spring Cloud Zuul은 Spring Boot 2.4부터 Maintenance 상태, 즉 Deprecated되어 있는 상태이다. 따라서 Spring Cloud Ribbon 또한 사용 불가능합니다.

Spring Cloud Zuul, Spring Cloud Ribbon을 대체하기 위해서 Spring Cloud Gateway, Spring Cloud Loadbalancer를 사용하기를 권장합니다.

## Spring Cloud Gateway 소개
### Spring Cloud Gateway Webmvc - 기본
Step1 : Dependencies
- DevTools, Eureka Discovery Client, **Gateway**
- Spring Cloud 2023 버전부터 정식적으로 MVC를 지원하게 되었습니다. 기존의 Spring MVC 기반의 애플리케이션에서 API Gateway 기능을 쉽게 붙일 수 있도록 지원하는 옵션입니다.
- 만약 일반적인 Spring MVC 애플리케이션을 사용한다면 Spring Cloud Gateway MVC 기반으로 작성하는 것이 좋음

프로젝트 정보 설정
![](../imgs/Pasted%20image%2020251026142508.png)

의존성 설정
![](../imgs/Pasted%20image%2020251026142457.png)


Step2 : application.yml 파일 설정

```yaml
spring:  
  application:  
    name: apigateway-service  
  cloud:  
    gateway:  
      server:  
        webmvc:  
          routes:  
            - id: first-service  
              uri: http://localhost:8081/  
              predicates:  
                - Path=/first-service/**  
            - id: second-service  
              uri: http://localhost:8082/  
              predicates:  
                - Path=/second-service/**  
server:  
  port: 8000  
eureka:  
  client:  
    register-with-eureka: true  
    fetch-registry: true  
    service-url:  
      defaultZone: http://localhost:8761/eureka/
```
- `/first-service` 경로로 시작하는 요청은 `http://localhost:8081` 주소로 라우팅합니다.
- `/second-service` 경로로 시작하는 요청은 `http://localhost:8082` 주소로 라우팅합니다.


## First Service, Second Service 추가
### First Service 생성
프로젝트 설정 정보 입력
![](../imgs/Pasted%20image%2020251026150714.png)

![](../imgs/Pasted%20image%2020251026150823.png)

FirstServiceController 생성
![](../imgs/Pasted%20image%2020251026151221.png)

### Second Service 생성
프로젝트 생성 정보 입력
![](../imgs/Pasted%20image%2020251026151533.png)

![](../imgs/Pasted%20image%2020251026151551.png)

SecondServiceController
![](../imgs/Pasted%20image%2020251026151816.png)

application.yml 파일 설정
![](../imgs/Pasted%20image%2020251026151831.png)

### First Service, Second Service 확인
new-toy-msa 프로젝트의 pom.xml 파일을 확인하여 등록된 모듈을 확인합니다.
![](../imgs/Pasted%20image%2020251027111615.png)
위 코드를 보면 service-discovery, user-service, first-service, second-service가 등록된 것을 볼 수 있습니다.

Step1 : **service-discovery 서버 실행**
service-discovery 서버를 실행합니다.
![](../imgs/Pasted%20image%2020251027111910.png)

실행 결과
- 아직 다른 서비스가 등록되지 않은 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251027111938.png)

Step2 : First-Service, Second-Service 실행
![](../imgs/Pasted%20image%2020251027112914.png)

실행 결과
- Eureka 서버를 통해 확인한 결과 두개의 서비스가 등록된 것을 확인함
![](../imgs/Pasted%20image%2020251027113128.png)

Intellij Services 탭을 통하여 현재 실행중인 마이크로서비스를 확인할 수 있습니다.
![](../imgs/Pasted%20image%2020251027113246.png)

Step3 : First-Service welcome 접근
웹 브라우저를 이용해서 First-Service에 접근해봅니다. 실행 결과 정상적으로 접근이 가능한 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251027113634.png)

Step4 : First-Service check 경로 접근
- First-Service 서버의 포트번호가 8081인 것을 확인할 수 있음
![](../imgs/Pasted%20image%2020251027114110.png)

Step5 : Second-Service welcome 접근
![](../imgs/Pasted%20image%2020251027114207.png)

위 실습을 수행한 다음에 API Gateway를 추가해봅니다.

## Webmvc를 위한 Spring Cloud Gateway
new-toy-msa 프로젝트를 선택 -> new -> Module 선택
![](../imgs/Pasted%20image%2020251027115404.png)

API Gateway 프로젝트 정보 입력
![](../imgs/Pasted%20image%2020251027115635.png)

의존성 추가
- Gateway는 동기 방식(WebMvc 방식)으로 지원되는 Gateway입니다.
- 비동기 방식인 경우(WebFlux 방식)에는 Reactive Gateway를 선택해야 합니다.
![](../imgs/Pasted%20image%2020251027115657.png)

프로젝트 생성 확인
![](../imgs/Pasted%20image%2020251027120058.png)

new-toy-msa 프로젝트에 apigateway-service 모듈 추가
![](../imgs/Pasted%20image%2020251027120143.png)

apigateway-service의 pom.xml 파일 수정
- version : 1.0.0
![](../imgs/Pasted%20image%2020251027150451.png)

apigateway-service의 spring cloud webmvc 의존성 라이브러리 확인
- 동기 방식의 spring mvc를 사용하는 servlet 구조임을 나타냅니다.
![](../imgs/Pasted%20image%2020251027150548.png)

apigateway-service의 application.yml 설정
- API Gateway의 라우팅 정보 설정
- 클라이언트로부터 "/first-service" 경로 이하의 요청 접근시 first-service로 라우팅합니다. 반대로 "/second-service" 경로 히아의 요청 접근시 second-service로 라우팅합니다.
```yaml
server:  
  port: 8000  
  
spring:  
  application:  
    name: apigateway-service  
  cloud:  
    gateway:  
      server:  
        webmvc:  
          routes:  
            - id: first-service  
              uri: http://localhost:8081/  
              predicates:  
                  - Path=/first-service/**  
            - id: second-service  
              uri: http://localhost:8082/  
              predicates:  
                  - Path=/second-service/**
```

apigateway-service 실행
- 실행 로그를 보면 API Gateway 서비스 서버가 Tomcat 엔진 기반으로 수행된 것을 볼수 있습니다. 이는 클라이언트의 요청 및 응답을 동기식으로 처리하는 것을 의미합니다.
- 실행한 API Gateway 서버는 8000번 포트로 실행 중입니다.
![](../imgs/Pasted%20image%2020251027151721.png)
![](../imgs/Pasted%20image%2020251027151757.png)


first-service, second-service 실행
- 두 서비스 서버를 실행하여 다음과 같은 경로로 요청하여 서비스 실행을 확인합니다.
![](../imgs/Pasted%20image%2020251027152457.png)
![](../imgs/Pasted%20image%2020251027152506.png)

apigateway-service 실행 확인
- 8000번 포트로 시작하고 first-service로 시작하는 welcome 메시지를 확인합니다.
- 실행 결과를 보면 API Gateway가 정상적으로 라우팅된 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251027152551.png)

다음 결과를 보면 8000번 포트의 second-service에 요청했을때 정상적으로 응답된 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251027152918.png)

위 실습을 통해서 알수 있는 사실은 first-service와 second-service의 정확한 IP 주소와 포트번호를 알지 않아도 클라이언트는 API Gateway로만 요청을 하면 원하는 결과를 얻을 수 있다는 점입니다.

## Webflux를 위한 Spring Cloud Gateway
의존성 추가
- DevTools, Eureka Discovery Client, **Reactive Gateway**
- 기존 apigateway-service 서비스의 의존성을 다음과 같이 변경합니다.
![](../imgs/Pasted%20image%2020251027155256.png)

apigateway-service application.yml 수정
- eureka 클라이언트 설정 정보 추가
- 라우팅 정보를 webflux에 맞게 수정
```yaml
server:  
  port: 8000  
  
spring:  
  application:  
    name: apigateway-service  
  cloud:  
    gateway:  
      server:  
        webflux:  
          routes:  
            - id: first-service  
              uri: http://localhost:8081/  
              predicates:  
                  - Path=/first-service/**  
            - id: second-service  
              uri: http://localhost:8082/  
              predicates:  
                  - Path=/second-service/**  
eureka:  
  client:  
    register-with-eureka: true  
    fetch-registry: true  
    service-url:  
      defaultZone: http://localhost:8761/eureka/
```

서버 실행
- service-discovery 서버 실행
- apigateway-service 서버 실행
- first-service, second-service 서버 실행

apigateway-service의 실행 부분을 보면 Netty 엔진 기반으로 동작 중인 것을 확인할 수 있습니다. 포트는 8000번임을 확인합니다. 클라이언트의 요청 시 비동기적 방식으로 응답합니다.
![](../imgs/Pasted%20image%2020251027160526.png)

실행 결과 확인
- Eureka 서버의 대시보드를 확인하여 등록된 서비스 확인합니다. 실행 결과 API Gateway 서비스가 등록된 것을 확인할 수 있습니다.
![](../imgs/Pasted%20image%2020251027161114.png)

- first-service의 welcome 경로 요청
![](../imgs/Pasted%20image%2020251027161412.png)

- second-service의 welcome 경로 요청
![](../imgs/Pasted%20image%2020251027161428.png)

## Spring Cloud Gateway - Filter 적용 1
### Spring Cloud Gateway Webflux - Filter

다음 그림과 같이 클라이언트와 서비스 사이에 API Gateway를 배치하여 클라이언트는 더이상 First Service, Second Service의 자세한 IP 주소 및 포트번호를 알지 않아도 되었습니다. 그저 API Gateway의 주소 및 포트번호만 알고 있으면 됩니다.
![](../imgs/Pasted%20image%2020251027162542.png)

Gateway 역할중에서 이번에는 필터(Filter)를 삽입해서 클라이언트의 요청을 조작하거나 클라이언트에 전달하는 응답값을 변경해 줄 수 있습니다.

Filter 동작 과정
클라이언트가 요청을 하게 되면 Predicate에서 어디로 라우팅할것인지 판단합니다.  Predicate와 서비스 사이에 Filter(PreFilter)를 집어넣어서 요청을 조작, 제어, 변경하는 작업을 넣을 수 있습니다. 이러한 필터를 PreFilter라고 합니다.
반대로 서비스로부터 응답이 오면 PostFilter를 거쳐서 클라이언트에게 응답을 전달할 수 있습니다. PostFilter에서 응답값을 조작할 수 있습니다.
![](../imgs/Pasted%20image%2020251027163749.png)

자바 코드를 사용하여 Filter 설정
- RouteLocator 스프링 빈을 생성합니다.
- "/first-service" 경로 이하의 요청이 들어오는 경우 Prefilter가 작동하여 요청 헤더에 f-request 헤더와 값을 추가하고 PostFilter가 작동하여 f-response 헤더와 값을 추가하도록 합니다.
- "/second-service" 경로 이하의 요청이 들어오는 경우에도 "/first-service" 경로 요청시 필터의 처리와 동일하게 진행됩니다.
- RouteSpec에 uri를 설정하여 라우팅할 주소를 입력합니다.
```java
@Configuration  
public class FilterConfig {  
    private Environment env;  
  
    public FilterConfig(Environment env) {  
       this.env = env;  
    }  
  
    @Bean  
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {  
       return builder.routes()  
          .route(r->r.path("/first-service/**")  
             .filters(f->f.addRequestHeader("f-request", "1st-request-header-by-java")  
                .addResponseHeader("f-response", "1st-response-header-from-java")  
                )  
             .uri("http://localhost:8081")  
          )  
          .route(r->r.path("/second-service/**")  
             .filters(f->f.addRequestHeader("s-request", "2st-request-header-by-java")  
                .addResponseHeader("s-response", "2st-response-header-from-java")  
             )  
             .uri("http://localhost:8082")  
          )  
          .build();  
    }  
}
```
FirstServiceController, SecondServiceController API 확인
- "/first-service/message" 경로로 요청하면 "f-request" 헤더값을 참조하여 로깅하고 메시지를 응답합니다.
- "/second-service/message" 경로로 요청하면 "s-request" 헤더값을 참조하여 로깅하고 메시지를 응답합니다.
![](../imgs/Pasted%20image%2020251027171114.png)
![](../imgs/Pasted%20image%2020251027171145.png)
실행 결과 확인
실행 결과를 보면 "1st-request-header-by-java"가 로깅된 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251027171417.png)
![](../imgs/Pasted%20image%2020251027171430.png)

응답 헤더를 보면 "f-response" 헤더와 헤더값인 "1st-response-header-from-java"가 설정되어 있는 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251027171844.png)

## Spring Cloud Gateway - Filter 적용 2

이번에는 반대로 API Gateway를 거치지 않고 서비스에 바로 요청을 해보겠습니다.
- 실행 결과를 보면 정상적인 메시지를 응답받지 못하고 First Service 서버의 로깅을 보면 "f-request" 헤더가 요구된다고 하는 것을 볼수 있습니다.
- 이는 클라이언트가 API Gateway의 필터를 거치지 않고 직접적으로 접근했기 때문입니다.
![](../imgs/Pasted%20image%2020251027171540.png)
![](../imgs/Pasted%20image%2020251027171535.png)

위와 같은 헤더 부재 문제를 해결하기 위해서는 First Service, Second Service 서버에 직접적으로 요청하는 것이 아닌 API Gateway(localhost:8000)을 통해서 요청하면 필터가 작동하여 문제가 해결되는 것을 알수 있습니다.

API Gateway를 통과하지 않고 First Service, Second Service에 직접적으로 요청할때 에러를 해결하기 위해서는 클라이언트 요청시 헤더에 "f-request", "s-request" 를 추가하고 값을 추가하여 전송하면 해결됩니다.

다음 실행 결과는 first-service에 "f-request" 헤더를 추가하여 API Gateway를 거치지 않고 직접적으로 요청한 결과입니다. 이전 실습과는 반대로 에러가 발생하지 않고 성공적으로 메시지를 응답받은 것을 볼수 있습니다. 이는 헤더를 추가하였기 때문입니다.
![](../imgs/Pasted%20image%2020251028115200.png)

다음 실행 결과를 보면 API Gateway에 First Service의 메시지를 요청한 결과입니다. 실행 결과를 보면 HTTP Response의 헤더에 "f-response" 헤더가 포함된 것을 볼수 있습니다. 이는 API Gateway의 PostFilter에 의해서 추가되었기 때문입니다.
![](../imgs/Pasted%20image%2020251029121212.png)

### Property를 활용한 API Gateway 필터 등록하기
API Gateway 프로젝트의 application.yml 설정
- spring.cloud.gateway.server.webflux.routes 프로퍼티에서 filters 프로퍼티를 추가하여 request, response에 대한 Filter를 추가합니다.
```yaml
spring:  
  application:  
    name: apigateway-service  
  cloud:  
    gateway:  
      server:  
        webflux:  
          routes:  
            - id: first-service  
              uri: http://localhost:8081/  
              predicates:  
                  - Path=/first-service/**  
              filters:  
                - AddRequestHeader=f-request, 1st-request-header-from-yaml  
                - AddResponseHeader=f-response, 1st-response-header-from-yaml  
            - id: second-service  
              uri: http://localhost:8082/  
              predicates:  
                  - Path=/second-service/**  
              filters:  
                - AddRequestHeader=s-request, 2nd-request-header-from-yaml  
                - AddResponseHeader=s-response, 2nd-response-header-from-yaml
```

실행 결과를 확인하기 전에 이전에 작성한 FilterConfig 클래스의 설정을 비활성화해야 합니다.
```java
// @Configuration  
public class FilterConfig {
//...
}
```

**실행 결과 확인**
First-Service의 메시지 실행 결과 확인합니다. 실행 결과를 보면 "f-response" 응답 헤더에 "1st-response-header-from-yaml" 값이 설정된 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251029122328.png)
![](../imgs/Pasted%20image%2020251029122556.png)

## Spring Cloud Gateway - Custom Filter 적용
### Custom Filter 생성하기
CustomFilter를 생성하기 위해서는 AbstractGatewayFilterFactory 클래스를 상속받아서 Spring Bean으로 등록해야 합니다.
- 이너 클래스인 Config 클래스에 사용자가 필요로 한 정보들을 추가하여 커스텀 필터 수행시 사용하면 됩니다.
```java
@Component  
@Slf4j  
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {  
  
    public CustomFilter() {  
       super(Config.class);  
    }  
  
    @Override  
    public GatewayFilter apply(Config config) {  
       // Custom PreFilter  
       return (exchange, chain) -> {  
          ServerHttpRequest request = exchange.getRequest();  
          ServerHttpResponse response = exchange.getResponse();  
  
          log.info("Custom PRE Filter: request id -> {}", request.getId());  
  
          // Custom Post Filter  
          return chain.filter(exchange).then(Mono.fromRunnable(() -> {  
             log.info("Custom POST Filter: response code -> {}", response.getStatusCode());  
          }));  
       };  
    }  
  
    public static class Config {  
       // Put the configuration properties for your filter here  
    }  
}
```

CustomFilter를 적요앟기 위해서 application.yml 파일 수정
- filters 항목에 CustomFilter 추가
![](../imgs/Pasted%20image%2020251029124646.png)

실행 결과 확인
- First Service의 메시지를 호출한 결과 API Gateway의 로그결과를 보면 Custom PreFilter와 Custom PostFilter가 정상적으로 실행된 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251029125002.png)

## Spring Cloud Gateway - Global Filter 적용
CustomFilter 같은 경우에는 각각의 서비스에 명시하여 적용하였습니다. 이번 시간에는 모든 서비스에 공통적으로 적용되는 Global Filter를 생성하고 적용하는 방법을 알아봅니다.

GlobalFilter 설정 클래스 코드는 다음과 같습니다.
- 구조는 CustomFilter 클래스와 다르지 않습니다.
- 중요한 것은 GlobalFilter로 작동하기 위해서 어느 위치에 설정할 것인지 입니다.
```java
@Component  
@Slf4j  
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {  

	public GlobalFilter(){
		super(Config.class);
	}
  
    @Override  
    public GatewayFilter apply(Config config) {  
       return ((exchange, chain) -> {  
          ServerHttpRequest request = exchange.getRequest();  
          ServerHttpResponse response = exchange.getResponse();  
  
          log.info("Global Filter baseMessage: {}", config.getBaseMessage());  
          if (config.isPreLogger()){  
             log.info("Global Filter Start: request id -> {}", request.getId());  
          }  
  
          return chain.filter(exchange).then(Mono.fromRunnable(() -> {  
             // Post Filter  
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

API Gateway 프로젝트의 application.yml 설정
- CustomFilter 같은 경우에는 webflux.routes 프로퍼티 아래에 설정한 반면에 GlobalFilter로 설정하기 위해서는 default-filters 프로퍼티 아래에 설정합니다.
- 글로벌 필터를 설정하면 First-Service, Second-Service에 공통적으로 해당 필터가 설정됩니다.
```yaml
spring:  
  application:  
    name: apigateway-service  
  cloud:  
    gateway:  
      server:  
        webflux:  
          routes:  
            # ...
          default-filters:  
            - name: GlobalFilter  
              args:  
                baseMessage: Spring Cloud Gateway WebFlux Global Filter  
                preLogger: true  
                postLogger: true
```


**실행 결과 확인**
API Gateway의 First-Service 메시지 요청
- 로깅 실행 결과를 보면 Global Filter가 먼저 실행되고 Custom Filter가 안쪽에서 실행된 것을 볼수 있음
![](../imgs/Pasted%20image%2020251029133926.png)
![](../imgs/Pasted%20image%2020251029134005.png)

API Gateway의 Second-Service 메시지 요청
- 실행 결과를 보면 Second-Service에도 동일하게 글로벌 필터가 로깅된 것을 볼수 있음
![](../imgs/Pasted%20image%2020251029134309.png)
![](../imgs/Pasted%20image%2020251029134345.png)

## Spring Cloud Gateway - Logging Filter
LoggingFilter 생성
```java
@Component  
@Slf4j  
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {  
  
    public LoggingFilter() {  
       super(Config.class);  
    }  
  
    @Override  
    public GatewayFilter apply(Config config) {  
       return ((exchange, chain) -> {  
          ServerHttpRequest request = exchange.getRequest();  
          ServerHttpResponse response = exchange.getResponse();  
  
          log.info("Logging Filter baseMessage: {}", config.getBaseMessage());  
          if (config.isPreLogger()){  
             log.info("Logging Filter Start: request URI -> {}", request.getURI());  
          }  
  
          return chain.filter(exchange).then(Mono.fromRunnable(() -> {  
             // Post Filter  
             if (config.isPostLogger()) {  
                log.info("Logging Filter End: response code -> {}", response.getStatusCode());  
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

LoggingFilter 설정
```yaml
spring:  
  application:  
    name: apigateway-service  
  cloud:  
    gateway:  
      server:  
        webflux:  
          default-filters:  
            - name: GlobalFilter  
              args:  
                baseMessage: Spring Cloud Gateway WebFlux Global Filter  
                preLogger: true  
                postLogger: true  
          routes:  
            - id: first-service  
              uri: http://localhost:8081/  
              predicates:  
                  - Path=/first-service/**  
              filters:  
                - AddRequestHeader=f-request, 1st-request-header-from-yaml  
                - AddResponseHeader=f-response, 1st-response-header-from-yaml  
                - CustomFilter  
            - id: second-service  
              uri: http://localhost:8082/  
              predicates:  
                  - Path=/second-service/**  
              filters:  
                - AddRequestHeader=s-request, 2nd-request-header-from-yaml  
                - AddResponseHeader=s-response, 2nd-response-header-from-yaml  
                - name: CustomFilter  
                - name: LoggingFilter  
                  args:  
                    baseMessage: Hi, there.  
                    preLogger: true  
                    postLogger: true
```


실행 결과 확인
- 실행 결과를 보면 Second-Service 요청 결과 Global Filter -> Custom Filter -> Logging Filter 순으로 정상적으로 로깅된 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251029135701.png)
![](../imgs/Pasted%20image%2020251029135718.png)

## Spring Cloud Gateway - Logging Filter 적용 2
LoggingFilter의 우선순위를 변경해봅니다.
- GatewayFilter 인터페이스의 구현체중에서 OrderedGatewayFilter 구현체로 필터 객체 생성
- 객체 생성자에 우선순위 설정
```java
@Component  
@Slf4j  
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {  
  
    public LoggingFilter() {  
       super(Config.class);  
    }  
  
    @Override  
    public GatewayFilter apply(Config config) {  
       return new OrderedGatewayFilter((exchange, chain) -> {  
          ServerHttpRequest request = exchange.getRequest();  
          ServerHttpResponse response = exchange.getResponse();  
  
          log.info("Logging Filter baseMessage: {}", config.getBaseMessage());  
          if (config.isPreLogger()) {  
             log.info("Logging Filter Start: request URI -> {}", request.getURI());  
          }  
  
          return chain.filter(exchange).then(Mono.fromRunnable(() -> {  
             // Post Filter  
             if (config.isPostLogger()) {  
                log.info("Logging Filter End: response code -> {}", response.getStatusCode());  
             }  
          }));  
       }, Ordered.HIGHEST_PRECEDENCE);  
    }  
  
    @Data  
    public static class Config {  
       private String baseMessage;  
       private boolean preLogger;  
       private boolean postLogger;  
    }  
}
```

위와 같이 최우선 순위로 설정한다면 GlobalFilter, CustomFilter보다 높은 우선순위로 먼저 실행될 것입니다.

**실행 결과 확인**
- 실행 결과를 보면 Logging Filter가 먼저 수행된 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251029140655.png)
![](../imgs/Pasted%20image%2020251029140715.png)

## Spring Cloud Gateway + Eureka 연동
현재 Service-Discovery 역할을 하는 Eureka Server가 실행중이고 First-Service, Second-Service가 등록된 상태입니다.
클라이언트가 Eureka 서버에 서비스들의 위치정보를 물어보면서 요청하는 것이 아니라 API Gateway와 Eureka 서버를 연동시켜서 클라이언트는 API Gateway에게만 요청하여 서비스에게 요청을 전달하도록 합니다.

Step1 : Eureka Client 추가 - pom.xml, application.yml
- Spring Cloud Gateway, First Service, Second Service 3개의 서버에 Eureka Client 의존성 라이브러리를 추가합니다.
```xml
<dependency>  
    <groupId>org.springframework.cloud</groupId>  
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>  
</dependency>
```

application.yml
- Spring Cloud Gateway, First Service, Second Service 3개의 서버에 Eureka Client 설정 정보 추가
```yaml
eureka:  
  client:  
    register-with-eureka: true  
    fetch-registry: true  
    service-url:  
      defaultZone: http://localhost:8761/eureka/
```


Step2 : API Gateway 프로젝트에 서비스별 라우팅 정보 수정
application.yml
- uri에 직접적인 IP 주소 및 포트번호가 아닌 lb라는 프로토콜을 사용하고, MY-FIRST-SERVICE, MY-SECOND-SERVICE와 같이 Eureka에 등록한 서비스 이름을 설정합니다.
![](../imgs/Pasted%20image%2020251029142246.png)

Step3 : Eureka Server - Service 등록 확인
- API Gateway 서버, First Service, Second Service 등록이 확인됨
![](../imgs/Pasted%20image%2020251029142508.png)
API Gateway 서버로 first-service, second-service 요청시 정상적으로 전달된 것을 볼수 있음
![](../imgs/Pasted%20image%2020251029142542.png)
![](../imgs/Pasted%20image%2020251029142620.png)

API Gateway가 클라이언트로부터 "/first-service/welcome" 경로를 전달받으면 Service Discovery에게 MY-FIRST-SERVICE는 지금 어디에 떠있는지 물어봅니다.
Service Discovery는 현재 등록된 인스턴스중 동작하는 MY-FIRST-SERVICE들의 위치 정보(IP 주소, 포트번호)를 API Gateway에게 알려줍니다.
API Gateway의 내부 로드밸런서는 해당 위치 정보를 가지고 그 주소들중 하나로 요청을 전달합니다.


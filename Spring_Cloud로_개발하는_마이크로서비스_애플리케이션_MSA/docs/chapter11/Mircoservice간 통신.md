
## 섹션 소개
- Communication types
- RestTemplate
- Feign Client - Log, Exception
- ErrorDecoder
- Multiple Orders Service

## Communication types
Microservice간 통신 방법에는 두가지가 있습니다.
- 동기형 HTTP 통신(Synchronous HTTP communication)
- AMQP를 통한 비동기형 통신(Asynchronous communication over AMQP)

동기형 HTTP 통신 수행과정은 다음과 같습니다.
1. 클라이언트가 User-Service에 사용자의 주문 확인을 요청합니다.
2. User-Service는 주문 데이터가 자신의 DB에 없기 때문에 Service Discovery를 통해서 Order-Service에 요청하여 사용자의 주문 데이터를 요청합니다.
3. Order-Service는 사용자의 주문 데이터를 조회한 후 호출한 쪽으로 전달합니다.
4. 주문 데이터를 받은 User-Service는 주문 데이터를 재가공하여 사용자 정보와 함께 클라이언트에게 응답합니다.
![](../imgs/Pasted%20image%2020251107153738.png)

동기형 HTTP 통신 방식이기 때문에 User-Service가 주문 데이터를 요청하고 응답받을때까지 대기합니다.

동기형 HTTP 통신 방식은 클라이언트와 서버간에 강력한 일관성을 갖습니다. 강력한 일관성을 가져야하는 작업으로 금융 서비스나 확실한 데이터 보존 작업이 필요한 경우가 있습니다. 
하지만 중간 과정에서 일관성을 조금 깨지더라도 최종적으로 일관성을 지켜도 되는 경우라면 비동기형 통신을 사용할 수 있습니다.

동기형 HTTP 통신 방식을 사용하기 위해서 REST API를 사용하고 비동기형 통신 방식을 사용하기 위해서 AMQP를 사용할 예정입니다.

### Rest Template
- 강력한 일관성 지원함
- Rest Template를 이용하면 클라이언트에서 user-service로 사용자의 주문 확인을 요청하면 내부적으로 Rest Template를 통하여 Order-Service에 주문 데이터를 요청하고 응답받는데 도움을 받습니다.

![](../imgs/Pasted%20image%2020251107155733.png)


## RestTemplate 사용
user-service RestTemplate Spring Bean 등록
```java
@Bean  
public RestTemplate restTemplate() {  
    return new RestTemplate();  
}
```

user-service UserController 
- userService.getUserByUserId 메서드에서 주문 데이터를 조회하여 UserDto에 넣어서 응답합니다.
```java
@GetMapping(value = "/users/{userId}")  
public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId){  
    UserDto userDto = userService.getUserByUserId(userId);  
  
    ModelMapper mapper = new ModelMapper();  
    mapper.getConfiguration().setMatchingStrategy(org.modelmapper.convention.MatchingStrategies.STRICT);  
    ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);  
  
    return ResponseEntity.status(HttpStatus.OK).body(responseUser);  
}
```

user-service UserService getUserByUserId 메서드
- 환경변수를 이용하여 주문 서비스의 URL을 참조합니다.
- RestTempalte 객체를 이용하여 주문 데이터 DTO를 가져옵니다.
```java
@Override  
public UserDto getUserByUserId(String userId) {  
    UserEntity userEntity = userRepository.findByUserId(userId);  
    if (userEntity == null) {  
       throw new UsernameNotFoundException("User not found");  
    }  
  
    UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);  
    String orderUrl = String.format(env.getProperty("order_service.url"), userId);  
    ResponseEntity<List<ResponseOrder>> orderListResponse = restTemplate.exchange(orderUrl, HttpMethod.GET, null, new ParameterizedTypeReference<>() {  
    });  
    List<ResponseOrder> orderList = orderListResponse.getBody();  
    userDto.setOrders(orderList);  
    return userDto;  
}
```

Local Git Repository user-service.yaml 수정
- order_service.url 프로퍼티 추가
![](../imgs/Pasted%20image%2020251107161036.png)

**사용자의 주문 확인 테스트**
우선 사용자의 주문을 확인하기 전에 상품 주문을 먼저 합니다.
![](../imgs/Pasted%20image%2020251107161620.png)

상품을 주문했다면 다시 user-service에 사용자의 주문을 확인을 요청합니다.
- 실행 결과를 보면 orders 프로퍼티에 방금 주문한 주문 정보가 출력된 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251107162028.png)

다음 실행 결과는 order-service의 h2 데이터베이스에 존재하는 주문 데이터입니다. user-service에 주문 데이터 요청시 user-service는 주문 데이터가 데이터베이스에 없기 때문에 order-service에 요청하여 다음 보이는 주문 데이터를 참조하여 응답합니다.
![](../imgs/Pasted%20image%2020251107162741.png)

## RestTemplate 사용 2
우리는 다음과 같이 user-service.yaml 파일에 주문 데이터를 참조하기 위해 URL을 설정하였습니다.
![](../imgs/Pasted%20image%2020251107163056.png)

그러나 위 url에는 문제점이 존재합니다. 그것은 호스트 및 포트번호가 변경될 수 있다는 점입니다. 왜냐하면 실행 중에 apigateway service 서버가 늘어나거나 줄어드는 과정에서 포트번호가 변경될 수 있고, IP 주소가 계속 변경될 수 있습니다. 즉, **호스트 및 포트번호가 변경될 수 있는 문제점**을 가집니다.

위 문제를 해결하기 위해서는 IP 및 포트번호를 직접적으로 명시하는것이 아니라 **마이크로서비스 이름을 명시**하고 로드밸런싱 하는 것입니다.

order_service.url 프로퍼티를 수정해줍니다.
![](../imgs/Pasted%20image%2020251107163641.png)

단, 마이크로서비스의 이름을 가지고 호출하기 위해서는 RestTemplate 스프링 빈 등록시 다음과 같이 변경해주어야 합니다.
- @LoadBalanced 애노테이션 추가
- 해당 애노테이션을 사용하면 Eureka Service Discovery에 등록된 이름으로 검색합니다.
```java
@Bean  
@LoadBalanced  
public RestTemplate restTemplate() {  
    return new RestTemplate();  
}
```

유레카 서비스를 들어가면 등록된 마이크로서비스중에 ORDER-SERVICE가 존재하는 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251107164136.png)

애노테이션을 설정했다면 user-service를 디버깅 모드로 다시 실행하고 확인해봅니다.
- 실행 결과를 보면 환경 변수로부터 가져온 orderUrl이 `http://order-service/order-service/8e4d9897-fa77-4475-87d7-17f0b791aede/orders` 인것을 볼수 있습니다. 이 실행 결과를 이용하여 마이크로서비스 이름으로 설정이 잘 변경된 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251107164759.png)

다음 실행 결과를 보면 주문 목록이 정상적으로 출력된 것을 볼수 있습니다. 이는 user-service가 주문 목록 요청시 order-service 마이크로서비스의 위치 정보를 Service Discovery 서버가 잘 전달하여 서빙한것을 볼수 있습니다.
참고로 OrderController의 요청 경로는 order-service로 시작하기 때문에 rewritePath와는 관계없습니다. 즉, apigateway-service를 거치지 않고 user-service -> Service Discovery -> order-service로 전달된 것입니다.
![](../imgs/Pasted%20image%2020251107164929.png)
![](../imgs/Pasted%20image%2020251107165203.png)

## FeignClient 사용
OpenFeign을 사용하여 RestTemplate를 대신해보도록 합니다.

FeignClient 개념 및 특징
- FeignClient는 HttpClient로써 REST API 호출을 추상화한 Spring Cloud Netflix 라이브러리입니다.
- RestTemplate를 더 사용하기 쉽도록 지원하는 라이브러입니다.
- FeignClient는 Load Balanced를 지원합니다.

사용방법
- 호출하려는 HTTP Endpoint에 대한 인터페이스를 생성함
- @FeignClient 애노테이션 선언함

Spring Cloud Netflix 의존성 라이브러리 추가
```xml
<!-- Feign Client -->  
<dependency>  
    <groupId>org.springframework.cloud</groupId>  
    <artifactId>spring-cloud-starter-openfeign</artifactId>  
</dependency>
```

@EnableFeignClients 애노테이션 추가
![](../imgs/Pasted%20image%2020251111142302.png)

OrderServiceClient 구현
- @FeginClient 애노테이션의 name 옵션 : 요청하고자 하는 애플리케이션의 이름
	- 애플리케이션 이름은 Service-Discovery에 등록된 서비스 이름과 동일함
- UserService는 @FeignClient에 의해서 서비스 디스커버리에 물어봐서 order-service의 주소 및 포트번호를 참조합니다. 
![](../imgs/Pasted%20image%2020251111142608.png)

UserServiceImple 코드 수정
- 기존 RestTemplate를 사용하던 코드를 OrderServiceClient 인터페이스로 교체함
![](../imgs/Pasted%20image%2020251111143632.png)


실행 결과 확인
- 실행 결과를 보면 User-Service에 주문 목록 조회시 정상적으로 Order-Service에 요청하여 가져온것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251111145235.png)


## FeignClient 사용 2
### FeignClient 사용시 발생한 로그 추적
user-service의 application.yaml 파일 수정
- userservice.client 패키지의 로깅 레벨을 DEBUG 레벨로 수정
![](../imgs/Pasted%20image%2020251111145611.png)

UserServiceApplication 클래스에 Logger.Level Spring Bean 등록
- Logger는 OpenFeign 클래스에 포함된 Logger 입니다.
- FeignClient가 외부 API를 호출할때 어떤 정보를 로그에 남길지를 결정합니다. 
![](../imgs/Pasted%20image%2020251111145839.png)

Logger.Level의 4단계 로그 레벨
- FULL 레벨 설정시 모든 요청 및 응답 정보를 로깅합니다.
- 모든 정보를 로깅하기 위해서 FULL 객체를 Spring Bean으로 등록합니다.
![](../imgs/Pasted%20image%2020251111150748.png)

회원 상세 보기 및 주문 목록 조회하여 로그 발생시키기
![](../imgs/Pasted%20image%2020251111150145.png)

다음 로깅된 것을 보면 주문 데이터를 가져오기 위해서 order-service에 userId를 전달하며 요청한 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251111150350.png)

## FeignClient 예외 처리
### FeignException
user-service에서 주문 데이터를 가져오기 위해서 order-service에 userId를 전달하며 요청합니다.
![](../imgs/Pasted%20image%2020251111152744.png)

다음 실행 결과를 보면 order-service에 존재하지 않는 경로인 `/order-service/{userId}/orders_ng` 경로로 요청시 다음과 같이 예외가 발생한 것을 볼수 있습니다.
user-service에서 order-service로 요청하였는데 404 응답이 나왔지만 클라이언트가 받는 것은 500 에러 응답인 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251111153124.png)

FeignException 처리
- try-catch문을 사용하여 예외 처리해봅니다.
- 예외가 발생하면 에러 메시지를 로깅합니다.
![](../imgs/Pasted%20image%2020251111153952.png)

실행 결과
- user-service에서 order-service에 요청한 주문 데이터가 실행하는 경우 주문 데이터를 제외한 정보를 클라이언트에게 200 OK 응답합니다.
- 주문 데이터 요청 실패에 대해서는 ERROR 레벨로 로깅된 것을 볼수 있음
![](../imgs/Pasted%20image%2020251111154245.png)
![](../imgs/Pasted%20image%2020251111154447.png)

위 try-catch 예외 처리를 통하여 주문 데이터를 조회하는 것이 실패하는 경우에는 주문 데이터를 포함하지 않고 응답하도록 예외 처리하였습니다.

정리
- 서비스가 다른 서비스에 요청하였다가 에러 응답을 받는 경우 그대로 에러 응답을 클라이언트에게 보내는 것이 아니라 예외 처리를 할 수 있다면 try-catch 문을 통하여 예외 처리를 하자

## ErrorDecoder를 이용한 예외 처리
### FeignErrorDecoder
- ErrorDecoder 인터페이스는 OpenFeign에서 제공하고 있음
- ErrorDecoder를 구현해서 서버로부터 에러 응답이 왔을때 어떻게 에러 처리를 할것인지 구현합니다.
	- 예를 들어 주문 데이터 조회 요청에 대하여 404 응답이 오는 경우에는 사용자의 주문이 비어있다는 메시지를 담은 예외 인스턴스를 반환합니다.

FeignErrorDecoder 구현
![](../imgs/Pasted%20image%2020251111160120.png)

FeignErrorDecoder Spring Bean 등록
![](../imgs/Pasted%20image%2020251111160151.png)

UserServiceImple 코드 수정
- 기존 try-catch문이 있다면 제거함
- 에러 응답 발생시 ErrorDecoder가 처리할 것임
![](../imgs/Pasted%20image%2020251111160311.png)

실행 결과 확인
- 실행 결과를 보면 order-serivce의 404 응답이 그대로 클라이언트에게 404 응답으로 내려온 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251111160424.png)

Properties 파일 적용
- FeignErrorDecoder로 에러 처리시 환경 변수를 이용하여 예외 메시지를 적용하도록 해봅니다.

user-service.yaml 파일 수정
![](../imgs/Pasted%20image%2020251111160840.png)

FeignErrorDecoder 수정
- 환경 변수로 메시지 프로퍼티를 참조하여 주입합니다.
![](../imgs/Pasted%20image%2020251111160942.png)

@FeignClient 애노테이션에 명시적 설정
- 다음과 같이 OrderServiceClient 사용시 에러 응답이 발생하면 FeignErrorDecoder가 처리할 것입니다.
![](../imgs/Pasted%20image%2020251111162214.png)

실행 결과 확인
- user-service의 FeignErrorDecoder 디버깅 화면을 보면 환경변수를 통해서 에러 메시지를 정상적으로 조회된 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251111161846.png)
![](../imgs/Pasted%20image%2020251111161933.png)
![](../imgs/Pasted%20image%2020251111161958.png)

만약 위와 같이 404 응답을 그대로 응답하는 것이 아닌 에러 응답을 받았을때 주문 데이터를 제외하고 응답하고 싶다면 try-catch문을 추가하여 별도로 예외 처리를 할 수 있습니다.

## 데이터 동기화 문제
### Multiple Orders Service
Orders Service 2개 기동
- Users의 요청 분산 처리
- **Orders 데이터도 분산 저장 -> 동기화 문제**

다음 그림에서 Client들이 User-Service에 서비스를 요청하면 User-Service는 주문 관련 서비스 처리를 위해서 RestTemplate이나 FeignClient를 통해서 Order-Service에 요청을 합니다. 
이때 스케일 아웃해서 2개의 Order-Service가 실행된다고 가정합니다. 주문 관련 요청할때마다 60001, 60002 순서로 순차적으로 요청을 분산 처리합니다.
이렇게 운용하는 경우 주문 데이터도 분산 저장될 수 있습니다. 이때 동기화 문제가 발생합니다.
이번에는 이러한 동기화 문제를 어떻게 해결하는지 학습합니다.
![](../imgs/Pasted%20image%2020251112142610.png)

Order-Service 2개 기동 확인
![](../imgs/Pasted%20image%2020251112143720.png)

60001 order-service의 DB(H2 Memory DB) 주문 데이터 확인
![](../imgs/Pasted%20image%2020251112145804.png)

60002 order-service DB 주문 데이터 확인
![](../imgs/Pasted%20image%2020251112145813.png)

같은 사용자 ID를 가진클라이언트는 3번의 주문을 요청하였습니다. 첫번째 주문은 60001 DB에 저장되고, 두번째 주문은 60002 DB에 저장되고, 마지막 세번째 주문은 60001 DB에 저장되었습니다.

위와 같이 3개의 주문 데이터가 저장된 상태에서 사용자의 정보 및 주문 데이터를 조회해봅니다.
- 실행 결과를 보면 3개의 주문 데이터가 아닌 1개의 주문 데이터만 조회된 것을 볼 수 있습니다.
- 이는 user-service에 주문 데이터 요청시 60002 order-service에 요청이 분산 처리된 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251112145913.png)
![](../imgs/Pasted%20image%2020251112150905.png)

문제점
- 주문 데이터가 분산 저장되는 경우에 동기화 문제가 발생합니다.
- 사용자 요청시마다 동기화가 되어 있지 않기 때문에 주문 데이터 결과가 다를 수 있습니다.

솔루션1 : 하나의 Database 사용
- order-service 각각의 데이터베이스를 사용하는 것이 아닌 하나의 데이터베이스를 사용하도록 한다.
![](../imgs/Pasted%20image%2020251112151259.png)

솔루션2 : Message Broder 사용
- 각각의 데이터베이스를 사용하는 대신에 메시지 브로커를 통해서 데이터를 동기화합니다.
- 메시지 브로커 구현 방법으로 카프카, RabbitMQ가 존재함
![](../imgs/Pasted%20image%2020251112151623.png)

추후 메시지 브로커를 도입하게 되면 Order-Service 각각이 데이터베이스를 갖는 것이 아닌 별도의 DB 공간을 갖게 되어 동기화를 수행하게 됩니다. 이 DB 공간에는 한개의 DB가 아닌 여러개의 DB가 존재할 수 있습니다. 
Order-Service 입장에서는 데이터베이스를 갖고 있을 필요가 없고 동기화에 대해서 신경쓰지 않고 비즈니스 로직에만 신경쓰게 되는 장점이 있습니다.
![](../imgs/Pasted%20image%2020251112152619.png)


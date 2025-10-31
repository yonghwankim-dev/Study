
## 섹션 소개
- Users Mircoservice 개요
- Users Mircoservice - 프로젝트 생성
- Users Mircoservice - DB
- Users Mircoservice - 회원가입
- Users Mircoservice - Security

## User Mircoservice 개요

### User Service 구현 정보
사용자와 관련된 정보를 처리하는 User Service를 구현합니다.
- 별도의 프론트 엔드는 없음
- 비즈니스 로직을 구현하고 테스트함
- H2 데이터베이스 사용함

### User Service Features
- 신규 회원 등록
- 회원 로그인
- 상세 정보 확인
- 회원 정보 수정/삭제
- 상품 주문
- 주문 내역 확인


### User Service APIs

| 기능              | URI(API Gateway 사용시)             | URI(API Gateway 미사용시) | HTTP Method |
| --------------- | -------------------------------- | --------------------- | ----------- |
| 사용자 정보 등록       | /user-service/users              | /users                | POST        |
| 전체 사용자 조회       | /user-service/users              | /users                | GET         |
| 사용자 정보, 주문내역 확인 | /user-service/users/{user_id}    | /users/{user_id}      | GET         |
| 작동 상태 확인        | /user-service/users/health_check | /users/health_check   | GET         |
| 환영 메시지          | /user-service/users/welcome      | /users/welcome        | GET         |

## Users Microservice - 프로젝트 생성
### Dependencies
- DevTools, Lombok, Spring Web, Eureka Discovery Client

### Version
- Spring Boot 3.5

### Spring Boot 기동 클래스 - Application Class
- @EnableDiscoveryClient 애노테이션을 추가하여 Discovery Service 서버와 통신할 수 있도록 설정함
```java
@SpringBootApplication  
@EnableDiscoveryClient  
public class UserServiceApplication {  
  
    public static void main(String[] args) {  
       SpringApplication.run(UserServiceApplication.class, args);  
    }  
  
}
```

### User Service 설정
application.yml
```yaml
server:  
  port: 0  
  
spring:  
  application:  
    name: user-service  
  
eureka:  
  instance:  
    instance-id: ${spring.application.name}:${spring.application.instance_id:${random.value}}  
  client:  
    service-url:  
      defaultZone: http://127.0.0.1:8761/eureka  
    fetch-registry: true  
    register-with-eureka: true
```

### RestController Class 구현
```java
@RestController  
@RequestMapping("/")  
public class UserController {  
    private final Environment env;  
  
    public UserController(Environment env) {  
       this.env = env;  
    }  
  
    @GetMapping("/health-check")  
    public String status(){  
       return String.format("It's Working in User Service, port(local.server.port)=%s, port(server.port)=%s", env.getProperty("local.server.port"), env.getProperty("server.port"));  
    }  
}
```

### Eureka Discovery Service 등록
![](imgs/Pasted%20image%2020251029170237.png)

실행 결과를 보면 USER-SERVICE가 등록되었고 포트번호가 54886인것을 볼수 있습니다.
![](imgs/Pasted%20image%2020251029170313.png)

### Confiugration 정보 추가
application.yml 파일에 Welcome Message 추가
```yaml
greeting:  
  message: Welcome to the Simple E-commerce.
```


userController 코드 추가
```java
@GetMapping("/welcome")  
public String welcome(HttpServletRequest request){  
    log.info("users.welcome ip: {}, {}, {}, {}", request.getRemoteAddr(),  
       request.getRemoteHost(), request.getRequestURI(), request.getRequestURL());  
    return env.getProperty("greeting.message");  
}
```

실행 결과
![](imgs/Pasted%20image%2020251030113837.png)

### @Value를 사용한 Configuration 정보 추가
application.yml 파일에 저장된 greeting.message 프로퍼티를 Environment 객체로 가져오는 것이 아니라 별도의 클래스를 선언하여 가져올 수 있습니다.

```java
@Data  
@Component  
public class Greeting {  
    @Value("${greeting.message}")  
    private String message;  
}
```

UserController
```java
@RestController  
@RequestMapping("/")  
@Slf4j  
public class UserController {  
    private final Greeting greeting;  
    private final Environment env;  
  
    public UserController(Greeting greeting, Environment env) {  
       this.greeting = greeting;  
       this.env = env;  
    }  
  
    @GetMapping("/health-check")  
    public String status(){  
       return String.format("It's Working in User Service, port(local.server.port)=%s, port(server.port)=%s", env.getProperty("local.server.port"), env.getProperty("server.port"));  
    }  
  
    @GetMapping("/welcome")  
    public String welcome(HttpServletRequest request){  
       log.info("users.welcome ip: {}, {}, {}, {}", request.getRemoteAddr(),  
          request.getRemoteHost(), request.getRequestURI(), request.getRequestURL());  
       return greeting.getMessage();  
    }  
}
```

실행 결과
![](imgs/Pasted%20image%2020251030114357.png)

### H2 Database
- 자바로 작성된 오픈소스, RDBMS
- Embedded, Server-Client 가능
- JPA 연동 가능

Dependency 추가
```xml
<dependency>  
    <groupId>org.springframework.boot</groupId>  
    <artifactId>spring-boot-starter-data-jpa</artifactId>  
</dependency>
<dependency>  
    <groupId>com.h2database</groupId>  
    <artifactId>h2</artifactId>  
    <scope>runtime</scope>  
</dependency>
```

application.yml 파일 설정
- h2 관련 설정 추가
- h2와 연동하기 위한 데이터 소스 설정 추가
- jpa 관련 설정 추가
```yaml
spring:  
  application:  
    name: user-service  
  h2:  
    console:  
      enabled: true  
      settings:  
        web-allow-others: true  
      path: /h2-console  
  datasource:  
    driver-class-name: org.h2.Driver  
    url: jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;  
    username: sa  
    password:  
  jpa:  
    hibernate:  
      ddl-auto: update
```

실행 결과 확인
- 정상적으로 접속한 것을 볼수 있습니다.
![](imgs/Pasted%20image%2020251030120911.png)
![](imgs/Pasted%20image%2020251030120853.png)


## Users Microservice - 사용자 추가 구현 (JPA)
의존성 추가
```xml
<dependency>  
    <groupId>org.springframework.boot</groupId>  
    <artifactId>spring-boot-starter-validation</artifactId>  
</dependency>
<dependency>  
    <groupId>org.modelmapper</groupId>  
    <artifactId>modelmapper</artifactId>  
    <version>3.2.5</version>  
</dependency>
```


## Users Microservice - Spring Security 연동
Spring Security
- Authentication + Authorization

Spring Security 도입과정
1. 의존성 라이브러리에 Spring Security 추가
2. @EnableWebSecurity 추가
3. PasswordEncoder Spring Bean 등록
4. SecurityFilterChain을 반환하는 Spring Bean 추가

Dependency 추가
```xml
<dependency>  
    <groupId>org.springframework.boot</groupId>  
    <artifactId>spring-boot-starter-security</artifactId>  
</dependency>
```

SecurityFilterChain Spring Bean 등록
```java
@Configuration  
@EnableWebSecurity  
public class WebSecurity {  
  
    @Bean  
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {  
       http.csrf(AbstractHttpConfigurer::disable);  
       http.authorizeHttpRequests(auth->auth  
          .requestMatchers("/h2-console/**").permitAll()  
          .anyRequest().authenticated()  
       );  
       http.httpBasic(Customizer.withDefaults());  
       http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));  
       return http.build();  
    }  
}
```

application.yml 설정에 user, password 설정 추가
```yaml
spring:
	security:  
	  user:  
	    name: user  
	    password: 9796c825-8712-4da6-881e-d53572cd13c4
```


## Users Microservice - BCryptPasswordEncode 개요
BCryptPasswordEncoder
- Password를 해싱하기 위해서 BCrypt 알고리즘 사용
- 랜덤 Salt를 부여하기 위해서 여러번 Hash를 적용한 암호화 방식

BCryptPasswordEncoder Spring Bean 등록
```java
@Bean
public BCryptPasswordEncoder passwordEncoder(){
	return new BCryptPasswordEncoder();
}
```


### 로그인 암호 생성
사용자에 대한 로그인 암호를 생성하기 위해서 유틸리티 클래스를 다음과 같이 구현합니다.
```java
public class Utils {  
    public static void main(String[] args) {  
        System.out.println(new BCryptPasswordEncoder().encode("password"));  
    }  
}
```

위 코드를 실행한 결과로 나온 암호화된 비밀번호를 application.yml 파일의 user에 대한 비밀번호로 설정합니다.
```yaml
spring:
	security:  
	  user:  
	    name: user  
	    password: $2a$10$rpeTmpDTs6ukQ0k4YkROKuGX4ireRSd8M/XhbnbrH6qfBqne3dSVy
```

포스트맨과 같이 API로 테스트할때 Username=user, Password=password으로 입력하여 간단하게 인증 가능합니다.
![](imgs/Pasted%20image%2020251030145135.png)


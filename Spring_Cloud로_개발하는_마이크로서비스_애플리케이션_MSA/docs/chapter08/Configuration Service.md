
## 섹션 소개
- Spring Cloud Config
- Local Git Repository
- Mircroservice에 적용
- Spring Boot Actuator
- Profiles 적용
- Remote Git Repository
- Native File Repository

## Spring Cloud Config
- 분산 시스템에서 서버, 클라이언트 설정 정보(application.yml)를 외부 시스템에서 관리
- 하나의 중앙화된 저장소에서 구성요소 관리 가능
- 설정 정보를 변경하면 각각의 마이크로서비스를 다시 빌드하지 않고 바로 적용이 가능함
- 애플리케이션 배포 파이프라인을 통해서 DEV-UAT(UserAcceptanceTesting)-PROD 환경에 맞는 구성 정보 사용

다음 그림을 보면 token.secret 프로퍼티 정보를 각각의 마이크로서비스에 저장하는 것이 아니라 Git 저장소와 같은 원격 저장소에 저장한 다음에 Spring Cloud Config Server를 통해서 User-Service와 Api Gateway Service가 같은 token.secret 프로퍼티 값을 갖을 수 있습니다.
요구사항 변경으로 Private Git Repository에 있는 token.secret=123의 값이 456으로 변경되면 각각의 User-Serivce, Api Gateway Service는 별도의 빌드 없이 바로 token.secret=456으로 참조가 가능합니다.
![](imgs/Pasted%20image%2020251104122642.png)

## Local Git Repository
- Local 환경에서 Git 저장소를 초기화 한다음에 해당 저장솟에 설정 파일을 저장하고 버전 관리합니다.
- Local 환경의 Git 저장소를 원격 환경의 Git 저장소와 동기화 시켜서 클라우드 환경에서 관리할 수 있습니다.

### Local Git Repository 생성
Local Git 저장소 디렉토리 생성
```shell
mkdir git-local-repo
cd git-local-repo
```

Git 초기화
```shell
git init
ls -al
```
![](imgs/Pasted%20image%2020251104125719.png)

설정 파일 추가
```shell
vim ecommerce.yaml
```
![](imgs/Pasted%20image%2020251104134310.png)

Git 트래킹 추가
```shell
git add ecommerce.yaml
```
![](imgs/Pasted%20image%2020251104125916.png)

Git Commit 추가
```shell
git commit -m "upload an application yaml file"
```
![](imgs/Pasted%20image%2020251104125944.png)

### Spring Cloud Project 생성 개요
구현 목록
- Config Server 의존성 추가
- Config Server 활성화 애노테이션 추가
- application.yaml 파일을 수정하여 Config Server 설정 추가
	- 포트번호
	- 애플리케이션 이름
	- Local Git Repo 위치

설정 정보 우선순위
- 1순위: 설정 저장소의 application_name-profile.yaml 파일
- 2순위: 설정 저장소의 application_name.yaml 파일
- 3순위: 설정 저장소의 application.yaml 파일
- 4순위: 서비스가 가지고 있는 application.yaml 설정 파일

예를 들어 서비스가 가지고 있는 application.yaml 설정 파일의 test=123라는 프로퍼티가 존재하고 설정 저장소의 application.yaml 설정 파일에 hello라는 프로퍼티가 존재하면 둘다 사용이 가능합니다.
그러나 설정 저장소의 application.yaml 설정 파일에 test=234라는 프로퍼티가 존재하면 시스템은 설정 저장소의 application.yaml 설정 파일에 있는 test=234를 더 우선시 하여 참조한다.

ecommerce 설정 파일의 default 프로필 설정 확인
```shell
http://localhost:8888/ecommerce/default
```

## Spring Cloud Config - 프로젝트 생성

프로젝트 생성
![](imgs/Pasted%20image%2020251104134613.png)

의존성 추가
![](imgs/Pasted%20image%2020251104134624.png)


프로젝트 생성후 의존성 추가
- 다음 실습을 대비하여 다음 의존성 라이브러리 미리 추가함
```xml
<dependency>  
    <groupId>org.springframework.boot</groupId>  
    <artifactId>spring-boot-starter-actuator</artifactId>  
</dependency>  
<dependency>  
    <groupId>org.springframework.boot</groupId>  
    <artifactId>spring-boot-starter-validation</artifactId>  
</dependency>  
<dependency>  
    <groupId>org.springframework.cloud</groupId>  
    <artifactId>spring-cloud-starter-bootstrap</artifactId>  
</dependency>
```


application.yaml 파일 설정
```yaml
spring:  
  application:  
    name: config-service  
  cloud:  
    config:  
      server:  
        git:  
          uri: file:///Users/yonghwankim/Documents/git-local-repo  
          default-label: master  
server:  
  port: 8888
```

Config Server 활성화 애노테이션 추가
![](imgs/Pasted%20image%2020251104135611.png)

Config Server 실행 및 확인
- 실행 결과를 보면 ecommerce.yaml 파일에 있는 설정 정보들이 출력된 것을 볼수 있습니다.
![](imgs/Pasted%20image%2020251104135806.png)

## User Microservice에서 Spring Cloud Config 연동 1
구현 목록
- User Service 프로젝트에 의존성 추가
	- spring-cloud-starter-config
	- spring-cloud-starter-bootstrap
- bootstrap.yaml 파일 추가
	- spring cloud config 정보 설정
- UserController에서 Spring Cloud Config 서버를 통해 가져온 설정 파일을 health-check API를 통해서 확인하는 코드 구현


## User Microservice에서 Spring Cloud Config 연동 2

User-Service에 의존성 추가
 ```xml
 <dependency>  
    <groupId>org.springframework.cloud</groupId>  
    <artifactId>spring-cloud-starter-config</artifactId>  
</dependency>  
<dependency>  
    <groupId>org.springframework.boot</groupId>  
    <artifactId>spring-boot-starter-actuator</artifactId>  
</dependency>  
<dependency>  
    <groupId>org.springframework.cloud</groupId>  
    <artifactId>spring-cloud-starter-bootstrap</artifactId>  
</dependency>
 ```

bootstrap.yaml 파일 추가
- Spring Cloud Config 정보를 8888 포트에 해당하는 서버로부터 가져온다는 것을 의미하고 그 설정중에서 ecommerce 이름을 가진 yaml 파일을 가지고 옵니다.
```yaml
spring:  
  cloud:  
    config:  
      uri: http://127.0.0.1:8888  
      name: ecommerce
```

health-check API 수정
- Config Server에서 설정을 잘 가져왔는지 다음과 같이 코드를 수정합니다.
```java
@GetMapping("/health-check")  
public String status(){  
    return String.format("It's Working in User Service"  
       + ", port(local.server.port)=" + env.getProperty("local.server.port")  
       + ", port(server.port)=" + env.getProperty("server.port")  
       + ", welcome message=" + env.getProperty("greeting.message")  
       + ", gateway ip(env)=" + env.getProperty("gateway.ip")  
       + ", token secret key=" + env.getProperty("token.secret")  
       + ", token expiration time=" + env.getProperty("token.expiration-time"));  
}
```

실행 결과 확인
- Service Discovery, Config Service, User Service를 실행합니다.

User Service 로그를 보면 Config Server로부터 ecommerce 설정 데이터를 가져온 것을 알수 있습니다.
![](imgs/Pasted%20image%2020251104151157.png)

health-check API를 요청하여 설정을 가져왔는지 확인합니다.
- 실행 결과의 secret key값을 보면 "my-very-long-secret-key-1234567890123456-from-git" 값으로써 token.secret 값이 설정 서버에서 참조된 것을 알 수 있습니다.
- 기존 user-serivce의 application.yml 파일에도 token.secret 프로퍼티가 존재하였습니다. 그러나 우선순위 상 설정 서버의 것을 참조한 것을 볼수 있습니다.
![](imgs/Pasted%20image%2020251104151705.png)

## Spring Boot Acturator - refresh
### Changed configuration values
User-Service가 Config Servier가 관리하고 있는 설정 정보를 참조해봤습니다. 그런데 만약 설정 정보가 변경되었을 경우에는 어떻게 해야 하는가? 예를 들어 로컬 Git 저장소에 있는 ecommerce.yaml 파일에 있는 token.secret 프로퍼티의 값이 변경되는 경우에 User-Service에서는 어떻게 보일까요?

변경된 값을 재적용하기 위해서는 다음과 같은 선택지가 존재합니다.
- 서버 재기동
	- 변경된 값을 사용하기 위해서는 서버를 재기동하는 것이 조건
	- 선택하지 않을 옵션
- Acturator refresh
	- Acturator를 이용하여 refresh 엔드포인트를 이용하여 서버를 완전 재기동하는 것이 아니라 기동하면서 환경 설정을 다시 업데이트합니다.
- Spring Cloud Bus 사용
	- 메시지 큐 방식을 이용해서 일괄적으로 환경 설정을 업데이트 해주는 방식

이번 시간에는 Acturator refresh를 이용해서 환경 설정을 업데이트하는 방식을 학습합니다.

Spring Boot Acturator
- Application 상태, 모니터링
- Metric 수집을 위한 Http Endpoint 제공

구현목록
- actuator 의존성 추가
- WebSecurity 클래스에서 `/acturator/**` 경로는  미인증 API로 설정
- user-service application.yml 파일에 actuator 엔드포인트 관련 설정 추가
	- refresh, health, beans, info

설정 변경시 재적용 시나리오
1. Local Git Repository에서 설정 파일을 변경하고 Commit
2. 클라이언트에서 User-Service 서버에게 actuator refresh 요청
	- `http://[user-service-ip]/actuator/refresh`
	- Http Response Body를 보면 배열 형식으로 변경된 프로퍼티 정보가 출력됩니다.

만약 서비스가 100개이고 100번의 actuator refresh 요청을 해야 한다면 이 문제를 해결 하기 위해서 Spring Cloud Buf를 사용할 수 있습니다.

user-service application.yml 파일에 actuator endpoint 추가
```yaml
management:  
  endpoints:  
    web:  
      exposure:  
        include:  
          - refresh  
          - health  
          - beans  
          - info
```

**actuator refresh 테스트**
우선은 현재 설정된 값을 확인합니다.
- token.secret=my-very-long-secret-key-1234567890123456-from-git
- gateway.ip=192.168.0.8
![](imgs/Pasted%20image%2020251104161300.png)

Local Git Repository의 ecommerce.yaml 파일을 수정합니다.
- token.secret 값을 수정하였습니다.
```yaml
token:
  expiration-time: 86400000 #10 days
  secret: change-my-very-long-secret-key-1234567890123456-from-git
gateway:
  ip: 192.168.0.8
```

변경사항을 적용하기 위해서 Commit 수행합니다.
```shell
git add .
git commit -m "change ecommerce.yaml file"
```
![](imgs/Pasted%20image%2020251104161617.png)

설정 파일을 변경한 다음에 config-server에 요청하여 설정이 변경되었는지 확인합니다.
- 실행 결과를 보면 token.secret 프로퍼티가 정상적으로 변경된 것을 확인할 수 있습니다.
![](imgs/Pasted%20image%2020251104162020.png)

이번에는 User-Service에 health-check API를 요청하여 설정값이 변경되었는 확인해봅니다.
실행 결과를 보면 **token.secret 프로퍼티의 값이 이전의 값을 가지는 것**을 볼수 있습니다.
![](imgs/Pasted%20image%2020251104162119.png)

User-Service에 최신 token.secret 프로퍼티의 값을 적용시키기 위한 가장 쉬운 방법은 서버를 재기동하는 것입니다. 하지만 이 방법은 매우 비효율적입니다. 이문제를 해결하기 위해서 Acutator refresh 기능을 사용합니다.

Acutator refresh 테스트
실행 결과를 보면 token.secret 프로퍼티와 config.client.version 프로퍼티가 변경된 것을 확인할 수 있습니다.
![](imgs/Pasted%20image%2020251104162935.png)

User-Service 로깅 결과를 보면 설정 정보를 다시 재갱신해오는 것을 볼수 있습니다.
![](imgs/Pasted%20image%2020251104163050.png)

actuator refresh 기능 수행후 다시 health-check 요청하여 token.secret 프로퍼티가 변경되었는지 확인합니다.
실행 결과를 보면 token.secret 프로퍼티가 변경된 것을 확인해볼 수 있습니다.
![](imgs/Pasted%20image%2020251104163159.png)

Actuator refresh 기능을 통해서 User-Service 서버를 재기동하지 않고도 갱신할 수 있게 되었습니다.

## Spring Cloud Gateway에서 Spring Cloud Config 연동
구현 목록
- API Gateway 프로젝트의존성 추가
	- Spring Cloud Config
	- Spring Cloud Bootstrap
	- Spring Boot Actuator
- API Gateway 프로젝트 bootstrap.yaml 파일 추가
	- Config Server의 ecommerce 설정 가져올 예정
- API Gateway 프로젝트의 token 관련 설정 제거
	- Config Server의 설정을 참조할 예정
- API Gateway의 application.yaml 설정 수정
	- actuator endpoint 추가
- API Gateway 서버에 Spring Bean 추가
	- HttpExchangeRepository
		- httpexchanges actuator endpoint 사용 위함
		- 사용자 요청을 기억하는 역할
- API Gateway테스트
	- actuator/health
	- actuator/httpexchanges


의존성 추가
```xml
<dependency>  
    <groupId>org.springframework.cloud</groupId>  
    <artifactId>spring-cloud-starter-config</artifactId>  
</dependency>  
<dependency>  
    <groupId>org.springframework.boot</groupId>  
    <artifactId>spring-boot-starter-actuator</artifactId>  
</dependency>  
<dependency>  
    <groupId>org.springframework.cloud</groupId>  
    <artifactId>spring-cloud-starter-bootstrap</artifactId>  
</dependency>
```

bootstrap.yaml 파일 추가
```yaml
spring:  
  cloud:  
    config:  
      uri: http://127.0.0.1:8888  
      name: ecommerce
```

application.yaml 파일 수정
- token.secret 프로퍼티 제거
- 제거된 프로퍼티는 Config Server를 통해서 참조할 예정
- user-service도 동일하게 token.secret 프로퍼티를 제거합니다.
![](imgs/Pasted%20image%2020251105121220.png)


application.yaml 파일 수정
- Acturator Endpoint 추가
```yaml
management:  
  endpoints:  
    web:  
      exposure:  
        include:  
          - refresh  
          - health  
          - beans  
          - info  
          - httpexchanges
```

HttpExchangeRepository Spring Bean 등록
```java
@SpringBootApplication  
public class ApigatewayServiceApplication {  
  
    public static void main(String[] args) {  
       SpringApplication.run(ApigatewayServiceApplication.class, args);  
    }  
  
    @Bean  
    public HttpExchangeRepository httpExchangeRepository(){  
       return new InMemoryHttpExchangeRepository();  
    }  
}
```

Acturator HttpExcahgnes 엔드포인트 테스트
우선은 User-Service의 Acturator가 정상 작동하는지 상태 체크해봅니다.
![](imgs/Pasted%20image%2020251105123626.png)

Api-gateway 서버에게 actuator/httpexchanges 엔드포인트를 요청합니다.
- 실행 결과를 보면 Api Gateway 서버에게 HTTP 요청 및 응답 정보가 저장된 것을 볼수 있습니다.
- 가장 최신 데이터일수록 exhcnages 배열의 앞에 저장됩니다. (Stack 구조)
![](imgs/Pasted%20image%2020251105123815.png)

Api Gateway 서버의 디버깅 결과
- token.secret 프로퍼티의 값이 Local Git Repository의 ecommerce.yaml 파일에 있는 값과 동일한 것을 볼수 있습니다.
- 현재 Api Gateway 서버의 application.yaml 파일에는 token.secret 프로퍼티 값이 없으므로 해당 프로퍼티는 Config Server로부터 참조된 것을 볼수 있습니다.
![](imgs/Pasted%20image%2020251105125300.png)

Api Gateway, User Service, Service Discovery, Config Service가 모두 실행중인 상태에서 Local Git Repository에 있는 token.secret 프로퍼티의 값을 다음과 같이 실시간으로 변경해보겠습니다.
다음과 같이 token.secret 프로퍼티의 값에 "banana"라는 접두사를 붙여서 커밋해보겠습니다.
![](imgs/Pasted%20image%2020251105125737.png)

다시 API 요청을 해서 API Gateway 서버의 token.secret 프로퍼티의 어떤 값을 참조하는지 확인해보겠습니다.
실행 결과를 보면 아직 banana 가 붙힌 접두사의 프로퍼티값이 적용되지 않은 것을 볼수 있습니다.
![](imgs/Pasted%20image%2020251105125926.png)

프로퍼티 변경을 적용하기 위해서 API Gateway 서버에 actuator/refresh 엔드포인트를 요청하여 리로드하겠습니다.
실행 결과를 보면 token.secret 프로퍼티가 갱신된 것을 볼수 있습니다.
![](imgs/Pasted%20image%2020251105130225.png)

다시 API 요청을 해서 token.secret 프로퍼티의 값을 디버깅해봅니다.
실행 결과를 보면 token.secret 프로퍼티의 값이 "banana" 접두사가 붙은 형태로 참조된 것을 볼수 있습니다.
![](imgs/Pasted%20image%2020251105130310.png)

위 실습을 통해서 알게 된 사실은 Config Service 및 다른 서비스를 실행중인 상태에서 프로퍼티 설정을 실시간으로 변경할 수 있고, 설정 변경을 적용하기 위해서는 해당 마이크로서비스 서버에 actuator/refresh 엔드포인트로 갱신할 수 있다는 점입니다.

하지만 만약에 갱신해야할 마이크로서비스가 100개와 같이 엄청나게 많은 경우에 번거로운 문제가 발생할 수 있습니다. 이 문제를 해결하기 위해서 Spring Cloud Bus를 이용해서 추후 문제를 해결할 수 있습니다.

## Multiple Profiles 사용하기
### Multiple Environments
Local Git Repository 저장소에 여러개의 yaml 파일이 있다고 가정합니다.
- application.yaml
- application-name.yaml
- application-name-{profile}.yaml


만약 위 3개의 파일에서 중복되는 프로퍼티가 존재한다고 가정하면 우선순위는 application-name-{profile}.yaml -> application-name.yaml -> application.yaml 순서입니다.

예를 들어 token .secret 프로퍼티가 3개의 파일(application.yaml, ecommerce.yaml, ecommerce-dev.yaml)에 존재하고 profile 조건(실행 환경이 dev 환경으로 가정)도 만족하면 가장 최우선으로 참조되는 것은 ecommerce-dev.yaml 파일에 있는 token.secret 프로퍼티입니다.

**프로퍼티 참조 우선순위**
프로파일 환경을 만족하고 각각의 설정 파일에 동일한 이름의 프로퍼티가 존재하는 경우 다음 우선순위를 따릅니다.
- 1순위: application-name-{profile}.yaml
- 2순위: application-name.yaml
- 3순위: application.yaml
- 4순위: spring boot application의 application.yaml

예를 들어 git-local-repo 영역에 application.yaml과 spring boot application에 application.yaml 파일에 동일한 프로퍼티가 존재하는 경우 git-local-repo 영역의 application.yaml 파일에 있는 프로퍼티가 우선시된다.


| Spring Boot Application                                 | Spring Cloud Config Server       | 우선 순위                                                                                          |
| -------------------------------------------------------- | -------------------------------- | ---------------------------------------------------------------------------------------------- |
| application.yaml                                         |                                  | (A) application.yaml                                                                           |
| application.yaml                                         | ecommerce.yaml                   | (C) ecommerce.yaml<br>(A) application.yaml                                                     |
| application.yaml                                         | ecommerce.yaml, application.yaml | (C) ecommerce.yaml<br>(C) application.yaml<br>(A) application.yaml                             |
| application.yaml ,application-dev.yaml<br>(profile: dev) | ecommerce.yaml                   | (C) ecommerce.yaml<br>(A) application-dev.yaml<br>(A) application.yaml                         |
| application.yaml ,application-dev.yaml<br>(profile: dev) | ecommerce.yaml, application.yaml | (C) ecommerce.yaml<br>(C) application.yaml<br>(A) application-dev.yaml<br>(A) application.yaml |


## Multiple Profiles 사용하기 2
User-Service application.yaml 파일 수정
- greeting.message 프로퍼티에 해당 메시지가 spring boot application의 프로퍼티임을 표시함
![](imgs/Pasted%20image%2020251105141507.png)

User-Service UserController health-check API 수정
- greeting.message 프로퍼티 참조
```java
@GetMapping("/health-check")  
public String status(){  
    return String.format("It's Working in User Service"  
       + ", port(local.server.port)=" + env.getProperty("local.server.port")  
       + ", port(server.port)=" + env.getProperty("server.port")  
       + ", welcome message=" + env.getProperty("greeting.message")  
       + ", gateway ip(env)=" + env.getProperty("gateway.ip")  
       + ", token secret key=" + env.getProperty("token.secret")  
       + ", token expiration time=" + env.getProperty("token.expiration-time"));  
}
```

테스트 실행 환경
- User Service Application
	- application.yaml
		- greeting.message 프로퍼티 존재
- Service Discovery, Config Service, Api Gateway Service, User Service 실행
![](imgs/Pasted%20image%2020251105142727.png)

User-Service의 health-check를 요청합니다.
- 실행 결과를 보면 welcome message의 값이 "Welcome to the Simple E-commerce. (spring boot)"로써 greeting.message 프로퍼티의 값 참조를 UserServiceApplication의 application.yaml 파일을 참조한 것을 볼수 있습니다.
![](imgs/Pasted%20image%2020251105142813.png)

Local Git Repository의 ecommerce.yaml 파일 수정
- greeting.message 프로퍼티 추가
- 프로퍼티 추가후 git commit을 수행해야함
 ![](imgs/Pasted%20image%2020251105143651.png)

Config Service, User Service를 재기동합니다.
- User-Service 로깅 결과를 보면 ecommerce.yaml 기본 파일을 참조한 것을 볼수 있습니다.
![](imgs/Pasted%20image%2020251105143844.png)
![](imgs/Pasted%20image%2020251105143933.png)


User-Service를 대상으로 health-check 요청합니다.
- 실행 결과를 보면 welcome message가 "welcome to the simple E-commerce. (config server)"인 것을 볼수 있습니다. 이는 spring boot application의 application.yaml 파일안에 greeting.message 프로퍼티가 아니라 config server의 ecommerce.yaml 파일안에 greeting.message 프로퍼티를 우선적으로 참조한 것을 볼수 있습니다.
![](imgs/Pasted%20image%2020251105144050.png)

위 테스트 결과로 알 수 있는 사실은 Config Server가 참조하고 있는 application-name.yaml 파일인 ecommerce.yaml 파일이 spring boot application의 application.yaml 파일보다 우선순위가 높다는 사실입니다.

User-Service application-dev.yaml 파일 추가
- 기본적인 설정은 application.yaml 파일과 동일
- spring.port=60000
- greeting.message=Welcome to the Simple E-commerce. (dev profile)
![](imgs/Pasted%20image%2020251105144816.png)
![](imgs/Pasted%20image%2020251105144823.png)

Local Git Repository ecommerce.yaml 파일 수정
- 시스템이 greeting.message 프로퍼티 참조시 spring boot application의 application-dev.yaml 파일에 있는 greeting.message 프로퍼티를 우선 참조하게 하기 위하여 Local Git Repository의 ecommerce.yaml 파일의 greeting.message 프로퍼티를 주석 처리합니다.
- 파일 수정 후 commit합니다.
![](imgs/Pasted%20image%2020251105145555.png)

User-Service를 재시작한 후 health-check API 요청
- spring boot application이 dev 프로파일에서 시작하는 것을 확인
- welcome message가 spring boot application의 application-dev.yaml 파일에 있는 greeting.message 프로퍼티의 값인 것을 확인함
![](imgs/Pasted%20image%2020251105145707.png)
![](imgs/Pasted%20image%2020251105145737.png)

Config Server 사용하고 여러개의 프로파일을 사용하는 경우 spring boot와 Config Server에 중복된 프로퍼티를 두지 않는 것을 권장합니다.

## Remote Git Repository
기존까지는 Local Git Repository에 설정 파일들을 저장하였습니다. 이번 시간에는 클라우드(원격) 환경에 설정 파일을 저장하고 Config Server가 원격 환경의 저장소를 설정 파일을 참조하도록 합니다.

Config-Service에서 application.yaml 파일 수정
- config-service는 다음 Git 원격 저장소의 설정 파일들을 참조합니다.
![](imgs/Pasted%20image%2020251105151523.png)

프로파일을 지정하지 않으면 기본적으로 ecommerce.yaml 파일을 참조하고 만약 user-service의 프로파일이 prod이면 ecommerce-prod.yaml 파일을 참조하게 됩니다.
![](imgs/Pasted%20image%2020251105151554.png)

User-Service 애플리케이션 실행시 프로파일을 "prod"로 설정하고 재시작하였습니다. 실행 결과를 보면 ecommerce-prod.yaml 파일을 참조하는 것을 볼수 있습니다.
![](imgs/Pasted%20image%2020251105152134.png)

실제 정말로 그런 것인지 Config Server에 ecommerce의 prod 프로파일을 확인해봅니다.
실행 결과를 보면 1순위로 ecommerce-prod.yml 파일 -> ecommerce.yml -> application.yml 파일 순으로 참조하는 것을 볼수 있습니다.
![](imgs/Pasted%20image%2020251105152522.png)

User-Service에 health-check를 요청한 결과는 다음과 같습니다.
실행 결과를 보면 token.secret 프로퍼티값이 "my-very-long-secret-key-1234567890123456-from-remote-repo-prod" 인것을 알 수 있습니다. 이는 User-Service의 프로파일이 "prod"이기 때문에 ecommerce-prod.yaml 파일의 것을 최우선적으로 참조하였기 때문입니다.
![](imgs/Pasted%20image%2020251105152835.png)

## Native File Repository
Native File Repository라는 것은 Git과 같은 관리툴을 사용하지 않고 해당 PC의 특정 경로에 파일을 저장하는 것을 의미합니다.

native-file-repo 디렉토리를 생성한후에 설정 파일들을 생성합니다.
```shell
mkdir native-file-repo
cd native-file-repo
touch application.yaml
touch ecommerce.yaml
touch user-service.yaml
```

application.yaml
```yaml
token:
  expiration_time: 864
  secret: "user_native_token"
gateway:
  ip: "192.168.0.7"
```

ecommerce.yaml
```yaml
token:
  expiration_time: 864
  secret: "user_native_ecommerce_native"
gateway:
  ip: "192.168.0.7"
```

user-service.yaml
```yaml
token:
  expiration_time: 864
  secret: "user_token_native_user_service"
gateway:
  ip: "192.168.0.7"
```

config-service 프로젝트의 application.yaml 수정
```yaml
  
spring:  
  application:  
    name: config-service  
  profiles:  
    active: native  
  cloud:  
    config:  
      server:  
        native:  
          search-locations: file://${HOME}/Documents/native-file-repo  
        git:  
#          uri: file:///Users/yonghwankim/Documents/git-local-repo  
          uri: https://github.com/joneconsulting/spring-cloud-config.git  
          default-label: master  
server:  
  port: 8888
```

Config-Service 서버 실행후 테스트합니다.
- config-service의 native 프로파일인 경우 프로퍼티 파일을 확인합니다.
- 실행 결과 application.yaml 파일을 참조한 것을 확인할 수 있습니다.
- Spring Cloud Config 같은 경우 기본적으로 {application}/{profile} 경로에 대해서 기본적으로 제공합니다.
![](imgs/Pasted%20image%2020251105154737.png)

이번에는 user-service.yaml 파일을 대상으로 native 프로파일인 경우 프로퍼티 소스를 확인합니다.
- 실행 결과 user-service.yaml 파일을 우선적으로 참조하는 것을 볼수 있습니다.
![](imgs/Pasted%20image%2020251105154900.png)

이번에는 ecommerce 파일을 대상으로 native profile인 경우 결과를 확인합니다.
실행 결과를 보면 ecommerce.yaml -> application.yaml 파일 순서로 참조하는 것을 볼수 있습니다.
![](imgs/Pasted%20image%2020251105155402.png)

native-file-repo 디렉토리에서 user-serivce.yaml 파일을 복사하여 user-service-dev.yaml 파일을 생성해봅니다.
```shell
cp user-service.yaml user-service-dev.yaml
ls -al
```
![](imgs/Pasted%20image%2020251105155749.png)

user-serivce-dev.yaml 파일 수정
![](imgs/Pasted%20image%2020251105155818.png)

**정리**
- Spring Cloud Config의 Native File Repository를 이용하면 별도의 Git 관리도구없이 Config Server가 로컬 파일 시스템에 접근하여 설정 파일을 참조할 수 있습니다.
- Remote Git Repository와 같이 원격 저장소의 설정 파일을 참조할때 기본적으로 원격 저장소 내부에서는 application-name-{profile}.yaml -> application-name.yaml -> application.yaml 우선순위로 설정 파일을 참조합니다. 그리고 원격 저장소의 설정 파일들에도 원하는 프로퍼티가 없으면 spring boot application의 설정 파일을 참조합니다. 우선순위는 application-name.yaml -> application.yaml 우선순위로 참조합니다.
- Git Repository의 설정 파일의 내용이 변경되는 경우 서버 재실행 없이 변경을 적용하기 위해서는 Actuator refresh 엔드포인트를 활용하여 각 마이크로서비스가 적용한다.
- 설정 파일의 프로퍼티 값 변경시 각각의 마이크로서비스에 Actuator refresh 엔드포인트를 적용하는데 많은 서비스가 존재하는 경우, 이 문제를 해결하기 위해서 Spring Cloud Bus를 사용하여 해결합니다.




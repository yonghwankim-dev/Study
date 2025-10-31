## 섹션 소개
- Spring Cloud Netflix Eureka 제품 사용
- Eureka Service Discovery 프로젝트 생성
- User Service 프로젝트 생성
- User Service를 서비스 디스커버리에 등록
- User Service - Load Balancer 구성

## Spring Cloud Netflix Eureka
### Netflix Eureka 기능 소개
![](imgs/Pasted%20image%2020251024131943.png)

마이크로서비스 3개로 구성된 A, B, C 서비스들이 존재합니다. 각각의 서비스들은 하나의 pc 또는 vm에서 수행될 수 있고, 각각의 독립적인 pc 또는 vm에서 독립적으로 실행될 수 있습니다. 
하나의 pc에서 수행되는 경우 동일한 IP 주소를 가지고 각 서비스를 분류하기 위해서 포트번호를 달리 해줍니다.
반면에 독립된 pc에서 수행되는 경우 서로 다른 IP 주소를 가지고 동일한 포트를 가질수 있습니다.

위 현재 상황에서 각각의 마이크로서비스가 서로간에 통신하기 위해서는 서비스들의 IP 주소 및 포트번호가 몇번인지 알고 있어야 합니다. 그런데 마이크로서비스가 수백개 있는 상황에서 각각의 서비스들이 모든 주소 및 포트번호를 전부 기억하는 것은 무리입니다. 또한 IP 주소가 동적할당되는 경우에는 더욱 힘듭니다.

위 문제점을 해결하도록 서비스들의 등록 정보, 생성 정보, 삭제 정보, 위치 정보를 알수 있도록 **서비스 레지스트리(Service Registry) 또는 서비스 디스커버리(Service Discovery)**를 제공합니다.

**서비스 디스커버리(Service Discovery)**
- 서비스 레지스트리와 서비스 디스커버리를 같은 의미로 사용함
![](imgs/Pasted%20image%2020251024134201.png)

Netflix Eureka는 Netflix에서 개발한 서비스 디스커버리(Service Discovery) 서버로, 마이크로서비스 환경에서 각 서비스들의 위치(IP, 포트 등)를 중앙에서 관리하고 조회할 수 있게 해줍니다. 

Eureka는 Netflix OSS(Open Source Software) 프로젝트 중 하나로, 다른 대표적인 구성 요소로는 **Hystrix(Circuit Breaker)**, **Zuul(API Gateway)**, **OpenFeign(선언적 HTTP 클라이언트)** 등이 있습니다.

Eureka 동작 방식 요약
1. 서비스 등록(Service Registration)
	- 각 서비스(A, B, C)가 애플리케이션 시작시 자신의 IP 주소, 포트, 서비스명(InstanceID) 등의 정보를 Eureka 서버에 등록합니다.
2. 레지스트리 유지(Registry Maintenance)
	- Eureka 서버는 등록된 서비스들의 정보를 저장하고, 각 서비스로부터 주기적으로 Heartbeat(상태 확인 신호)를 받아 해당 서비스가 정상 동작중인지 확인합니다.
3. 서비스 조회(Service Discovery)
	- 다른 서비스나 클라이언트가 특정 서비스(A, B, C 등)에 접근하려고 할때 Eureka에 등록된 서비스 목록(Registry)을 참고하여 해당 서비스의 실제 위치(IP/포트)을 확인할 수 있습니다.
4. 클라이언트 접근 방식
	- 일반적으로 클라이언트는 Eureka에 직접 접근하기 보다는 **API Gateway(Zuul, Spring Cloud Gateway 등)** 나 프론트 엔드 애플리케이션을 통해 서비스를 호출합니다. 이때 게이트웨이는 내부적으로 Eureka를 조회하여 요청을 해당 서비스 인스턴스로 **라우팅** 합니다.


## Eureka Server - 프로젝트 생성

Spring Boot 프로젝트 생성
![](imgs/Pasted%20image%2020251024141609.png)

의존성 추가
- Spring Boot 3.5.7 버전 선택
- Eureka Server 의존성 추가
![](imgs/Pasted%20image%2020251024141701.png)

프로젝트 생성 후 의존성 확인
- Spring Boot 3.5.7 확인
- spring-cloud-starter-netflix-eureka-server 의존성 확인
	- 상세 버전은 Spring Boot 3.5.7에 맞게 의존성 관리를 해줍니다.
![](imgs/Pasted%20image%2020251024142214.png)
![](imgs/Pasted%20image%2020251024142129.png)

Eureka 서버 활성화
- @EnableEurekaServer 애노테이션을 추가하여 서버 실행시 유레카 서버를 활성화 시켜줍니다.
![](imgs/Pasted%20image%2020251024142334.png)

application.yml 파일 설정
- 기본 서버 포트 : 8761
- 애플리케이션 이름 : service-discovery
- eureka.client.register-with-eureka
	- 유레카 서버인 자기 자신을 유레카 서버에 등록할 것인지에 대한 여부
	- 기본값: true
	- 서비스 디스커버리 자체가 자기 자신을 등록할 필요가 없기 때문에 false로 설정함
- eureka.client.fetch-registry
	- 등록되어진 다른 서비스들의 목록을 주기적으로 갱신해서 가지고 올거냐는 설정
	- 기본값: true
	- 해당 설정 역시 가져올 필요가 없기 때문에 false 지정함

```yaml
spring:  
  application:  
    name: service-discovery  
server:  
  port: 8761  
eureka:  
    client:  
        register-with-eureka: false  
        fetch-registry: false
```

Eureka 서버 실행 및 실행 결과 확인
- 실행 결과를 보면 웹 서버로 Tomcat을 사용함
- 포트 번호 8761번 확인
![](imgs/Pasted%20image%2020251024143613.png)

유레카 대시보드 실행 결과 확인
- http://localhost:8761 접속하여 실행 결과 확인
- 오른쪽을 보면 언제 기동되었는지 확인이 가능함
- DS Replicas 영역을 보면 이 서비스 자체가 복제가 되어있는 상태인지 확인
- 제일 밑에  보면 Eureka 서버에 등록된 현재 서비스들을 확인할 수 있음. 아직까지 등록된 서비스는 없는 상태
![](imgs/Pasted%20image%2020251024143858.png)

## Eureka Discovery Client - UserService
UserService 프로젝트 생성
![](imgs/Pasted%20image%2020251024150154.png)

의존성 추가
![](imgs/Pasted%20image%2020251024150228.png)

pom.xml 확인
- spring-cloud version 2025.0.0 확인
- web, eureka-client 확인
![](imgs/Pasted%20image%2020251024150457.png)


application.yml 설정
- eureka.client.server-url.defaultZone : Eureka 클라이언트가 Eureka 서버와 통신하기 위해 사용하는 URL을 설정
- eureka.client.fetch-registry : Eureka 클라이언트가 Eureka 서버로부터 등록된 서비스 정보를 가져올지 여부를 결정
	- 클라이언트가 **Eureka 서버로부터 서비스 레지스트리 정보를 주기적으로 가져옵니다.**
- eureka.client.register-with-eureka
	- **Eureka 클라이언트가 자신을 Eureka 서버에 등록할지 여부**를 결정
```yaml
spring:  
  application:  
    name: user-service  
server:  
  port: 60000  
eureka:  
  client:  
    service-url:  
        defaultZone: http://127.0.0.1:8761/eureka  
    fetch-registry: true  
    register-with-eureka: true
```

서비스 디스커버리 클라이언트 애노테이션 추가
![](imgs/Pasted%20image%2020251024152232.png)

service-discovery, user-service 기동후 실행 결과 확인
- service-discovery 서버의 대시보드에서 서비스가 등록되었는지 확인
- 실행 결과를 보면 USER-SERVICE 서비스가 등록된 것을 볼수 있습니다.
![](imgs/Pasted%20image%2020251024150847.png)

등록되어 있는 User Service의 Status 컬럼에 있는 주소를 클릭하면 다음과 같은 결과를 얻습니다.
- actuator/info 경로로 이동하는데 User Service 프로젝트 의존성에 Acturator 의존성을 추가하면 헬스체크를 하거나 부가적인 정보를 얻을 수 있습니다.
- 현재는 의존성이 설정되어 있지 않은 상태입니다.
![](imgs/Pasted%20image%2020251024152535.png)

## User Service - 프로젝트 생성
User Service 서버를 2개 실행합니다.
- 서버1: port=60000
- 서버2: port=60001

유레카 서버 실행 결과 확인
- User Service 서비스의 Status 컬럼을 보면 UP(2)로 되어 있고 각각의 주소가 등록된 것을 확인
![](imgs/Pasted%20image%2020251024153517.png)

터미널을 이용하여 User Service 서버 실행
```shell
mvn --version
brew install mvn
```
![](imgs/Pasted%20image%2020251024154239.png)

서버 실행
```shell
mvn spring-boot:run -Dspring-boot.run.jvmArguments='-Dserver.port=60002'
```

서버 실행 결과 확인
- 실행 결과를 보면 포트 번호 60002인 User Service 서비스가 등록된 것을 볼수 있음
![](imgs/Pasted%20image%2020251024154358.png)

Maven 명령을 통하여 컴파일 및 빌드
- clean->compile->package를 수행합니다.
- clean : 빌드 및 패키징한 결과물을 제거
- compile : 코드를 컴파일하여 바이트 코드 파일을 생성함
- package : 애플리케이션을 실행할 수 있는 jar파일을 생성함
![](imgs/Pasted%20image%2020251024154612.png)

패키징 실행 결과 확인
![](imgs/Pasted%20image%2020251024154806.png)

터미널을 이용하여 패키징한 결과물을 실행
```shell
java -jar -Dserver.port=60003 ./target/user-service-0.0.1-SNAPSHOT.jar
```

실행 결과 확인
- 실행 결과 포트번호=60003인 User Service 서비스가 정상적으로 등록됨
![](imgs/Pasted%20image%2020251024155020.png)

다음 실행 결과에서 마지막 로깅 결과를 보면 registration status = 204인 것을 볼수 있습니다. 이는 클라이언트가 서비스 디스커버리 서버에 등록 요청을 했을때 정상적으로 되었다는 응답입니다.
![](imgs/Pasted%20image%2020251024155149.png)

다른 User Service 서비스의 로깅 결과를 보면 Resolving Eureka Enpoint라고 해서 환경 설정을 주기적으로 받고 있는 모습을 볼 수 있습니다. 다른쪽에 있는 서비스가 추가가 된다고 하면 이렇게 정기적으로 새로운 리스트를 유레카 서버로부터 전달받아서 계속적으로 정보를 갱신하고 있는 상황입니다. 
![](imgs/Pasted%20image%2020251024155307.png)

위 실습을 통하여 인텔리제이를 통해서 UserService 서비스를 2개 실행하고, maven을 이용하여 1개, java 명령어를 이용하여 jar 파일을 1개 실행하였습니다. 총 4개를 실행하였습니다.

## User Service - Load Balancer
이전 실습을 통하여 우리는 User Service 4개를 실행해봤습니다. 각각의 서비스는 포트번호(60000 ~ 60003)를 달리하여 실행하였습니다.

클라이언트가 어떤 요청을 User Service 서비스에 전달하면 이상적으로는 1,2,3,4번 서비스에 순차적으로 실행될 수 있어야 하는데, 클라이언트 입장에서는 몇번이 되었든 User Service는 하나일 뿐입니다.

서버 입장에서는 다양한 클라이언트의 네트워크 트래픽이 증가를 했기 때문에 다양한 클라이언트의 정보를 많이 처리해주기 위해서 N개를 서비스를 만들어두었습니다. 이것을 우리가 확장성이라고 이야기 합니다. 동일한 스펙의 서비스가 여러개 만들었기 때문에 스케일 아웃(Scale-out)이라고 합니다.

스케일링 작업을 통해서 User Service가 확장되었기 때문에 실제 사용자 입장에서는 어떤 User Service를 사용하는 것이 아니라 User Service 앞단에서 그러한 요청을 핸들링할 수 있는 **로드밸런서**라는게 존재합니다.

그래서 로드밸런서라는 것은 부하 분산 처리로서 하나 이상의 서비스가 존재할때 클라이언트의 요청을 어디에 어떻게 보낼 것인가 그런것들을 정리하는걸 애기를 합니다.

현재 4개의 서비스 운용 상황에서 서비스를 더 늘리는 상황에서 수동적으로 포트를 변경하는 것은 상당히 불편합니다. 서비스 증가시 자동적으로 필요한 설정도 같이 생성되어야 하는데 이러한 설정을 직접하는 것은 확장에서 문제가 있습니다.

### Scaling - Random 포트 사용으로 같은 서비스 추가 실행
application.yml 설정
- port 번호를 0으로 설정하여 자동으로 할당되도록 설정함

```yaml
server:
	port: 0
```

서버를 실행하고 결과 확인
- port=53569로 할당된 것을 확인
![](imgs/Pasted%20image%2020251024162309.png)

유레카 대시보드 결과를 보면 1개의 서비스가 등록된 것을 볼수 있고 포트번호가 0인것을 볼수 있습니다.
![](imgs/Pasted%20image%2020251024162348.png)

maven으로 새로운 User Service를 실행해봅니다. 물론 별도의 포트 설정은 하지 않습니다.
- 실행 결과를 보면 53614로 포트번호가 할당됨
```shell
mvn spring-boot:run
```
![](imgs/Pasted%20image%2020251024162533.png)

실행 결과를 보면 서비스가 2개 실행되었음에도 "UP (1)"과 같이 표시된 것을 확인할 수 있습니다. 이는 **서비스의 인스턴스 아이디가 고유하게 할당되지 않았기 때문입니다.**
![](imgs/Pasted%20image%2020251024162615.png)

application.yml 설정
```yaml
eureka:  
  instance:  
    instance-id: ${spring.application.name}:${spring.application.instance_id:${random.value}}
```

User Service를 재시작하고 유레카 대시보드로 실행 결과 확인
- 실행 결과를 보면 정상적으로 UP(2)로 표시됨을 확인
![](imgs/Pasted%20image%2020251024163057.png)

정리
- User Service 서버 실행시 포트를 명시적으로 설정하는 것이 아닌 랜덤 포트로 설정하는 것을 학습
- 인스턴스 아이디를 설정하여 랜덤한 인스턴스 아이디를 할당하게 하고 정상적으로 서비스를 표시할 수 있도록 함

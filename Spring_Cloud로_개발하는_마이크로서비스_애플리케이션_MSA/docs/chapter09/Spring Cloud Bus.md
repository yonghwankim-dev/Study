
## 섹션 소개
- Spring Cloud Bus
- RabbitMQ 설치
- 프로젝트 수정 - Actuator 추가
- 테스트

## Spring Cloud Bus
Configuration Server가 참조하는 설정 파일의 내용이 변경되는 경우 각각의 마이크로서비스에 변경 내용을 적용하기 위해서는 다음과 같은 방법을 사용하였습니다.
1. 서버 재시동
2. Actuator refresh

위 방법 중에서 Acutator refresh 방법을 사용하면 마이크로 서비스가 실행중임에도 엔드포인트를 이용해서 설정을 재적용할 수 있습니다.
하지만 Acutator refresh 엔드포인트를 이용하는 방법은 각각의 마이크로서비스에 일일히 요청해야 합니다. 예를 들어 설정 내용이 변경되었을때 해당 설정 파일을 참조하는 마이크로서비스가 100개인 경우에 100개의 마이크로서비스에 각각 Acutator refresh 엔드포인트를 총 100번 요청해야 합니다. 이는 매우 비효율적입니다.
위 문제를 해결하기 위해서 **Spring Cloud Bus**를 사용합니다.

Spring Cloud Bus 사용
- 분산 시스템의 노드(마이크로서비스)를 경량 메시지 브로커와 연결
- 상태 및 구성에 대한 변경 사항을 연결된 노드에게 전달(broadcast)

Spring Cloud Bus 구성
Spring Cloud Config Server와 Spring Cloud Bus를 결합하여 구성합니다. Spring Cloud Bus 서버는 각각의 마이크로서비스와 연결되어 설정 파일 변경시 연결된 마이크로서비스에 변경 내용을 전파 및 적용합니다.
Spring Cloud Bus와 마이크로서비스간에 AMQP(Advanced Message Queuing Protocol) 통신 방식을 사용합니다.
![](../imgs/Pasted%20image%2020251106125410.png)

**AMQP(Advanced Message Queuing Protocol)**
- 메시지 지향 미들웨어를 위한 개방형 표준 응용 계층 프로토콜
- 메시지 지향, 큐잉, 라우팅(P2P, Publisher-Subscriber), 신뢰성, 보안
- Erlang, RabbitMQ에서 사용

**Kafka 프로젝트**
- Apache Software Foundation이 스칼라(Scalar) 언어로 개발한 오픈 소스 메시지 브로커 프로젝트
- 분산형 스트리밍 플랫폼
- 대용량의 데이터를 처리 가능한 메시징 시스템

### AMQP vs Kafaka
RabbitMQ
- 메시지 브로커
- 초당 20개 이상의 메시지를 소비자에게 전달할 수 있음
- 메시지 전달을 보장하고 시스템 간 메시지 전달함
- 브로커, 소비자 중심

Kafaka
- 초당 10만개(100k) 이상의 이벤트 처리할 수 있음
- Pub/Sub, Topic에 메시지 전달함
	- Publisher는 Topic이라는 공간에 메시지를 전달함
	- Topic을 등록한 Subscriber에게 데이터를 전달함
- Ack를 기다리지 않고 전달 가능
	- Publisher는 Subscriber가 데이터를 받았는지 대기하지 않고 전달할 수 있음
- 생산자 중심

RabbitMQ, Kafaka 성능 비교

|                           | Kafaka                | Pulsar                 | RabbitMQ<br>(Mirrored)       |
| ------------------------- | --------------------- | ---------------------- | ---------------------------- |
| Peak Throughput<br>(MB/s) | 605MB/s               | 305MB/s                | 38MB/s                       |
| p99 Latency(ms)           | 5ms<br>(200MB/s load) | 25ms<br>(200MB/s load) | 1ms<br>(reduced 30MB/s load) |

### Actuator bus-refresh Endpoint
- 분산 시스템의 노드(마이크로서비스)를 경량 메시지 브로커와 연결함
- 상태 및 구성에 대한 변경 사항을 연결된 노드에게 전달함(Broadcast)

![](../imgs/Pasted%20image%2020251106132111.png)
1. 클라이언트가 마이크로서비스(Config Server 포함) 중 하나에 `/busrefresh`를 요청합니다.
2. 요청을 받은 마이크로서비스는 변경 사항을 Spring Cloud Bus에게 알립니다.
3. Spring Cloud Bus가 변경 사항을 감지하고 자기와 연결된 나머지 마이크로서비스들에게 변경 사항을 업데이트합니다.

## RabbitMQ 설치 - Docker
사전조건
- Docker Desktop 설치

docker version 확인
```shell
docker version
```
![](../imgs/Pasted%20image%2020251106133731.png)

RabbitMQ 설치
- https://www.rabbitmq.com/docs/download
- 설치 화면을 보면 가장 쉬운 방법인 docker 컨테이너 실행하는 메뉴얼을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251106133839.png)

RabbitMQ docker 실행
```shell
# latest RabbitMQ 4.x
docker run -it -d --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:4-management
```
![](../imgs/Pasted%20image%2020251106134925.png)



RabbitMQ 접속
- username: guest
- password: guest
![](../imgs/Pasted%20image%2020251106134403.png)

다음과 같은 화면이 나온다면 RabbitMQ 서버를 docker로 설치하는 것을 완료한 것입니다.
![](../imgs/Pasted%20image%2020251106134423.png)

## RabbitMQ 설치 - MacOs
MacOS에 RabbitMQ 설치 방법
- homebrew

```shell
brew update
brew install rabbitmq
```
![](../imgs/Pasted%20image%2020251106135736.png)

rabbitmq 실행
```shell
brew services start rabbitmq
```
![](../imgs/Pasted%20image%2020251106135903.png)

rabbitmq 접속
- username : guest
- password : guest
![](../imgs/Pasted%20image%2020251106135922.png)

rabbitmq 접속 확인
![](../imgs/Pasted%20image%2020251106135946.png)

rabbitmq 실행 종료
```shell
brew services stop rabbitmq
```

rabbitmq 실행
```shell
$ rabbitmq-server
```

## AMQP 사용
config-service 의존성 추가
- Actuator
- AMQP for Spring Cloud Bus

```xml
<dependency>  
    <groupId>org.springframework.boot</groupId>  
    <artifactId>spring-boot-starter-actuator</artifactId>  
</dependency>  
<dependency>  
    <groupId>org.springframework.cloud</groupId>  
    <artifactId>spring-cloud-starter-bus-amqp</artifactId>  
</dependency>
```

user-service, apigateway-service 의존성 추가
- AMQP for Spring Cloud Bus
```xml
<dependency>  
    <groupId>org.springframework.cloud</groupId>  
    <artifactId>spring-cloud-starter-bus-amqp</artifactId>  
</dependency>
```

config-service, user-service, apigateway-service 서비스에 application.yaml 수정
- rabbitmq 접속 정보 추가
- actuator endpoint에 busrefresh 추가
```yaml
spring:  
  rabbitmq:  
    host: 127.0.0.1  
    port: 5672  
    username: guest  
    password: guest
management:  
  endpoints:  
    web:  
      exposure:  
        include:  
          - health  
          - info  
          - refresh  
          - beans  
          - httptrace  
          - busrefresh
```


### 테스트
1. RabbitMQ Server 실행
2. config-service 실행
3. service-discovery 실행
4. apigateway-service 실행
5. user-service 실행
![](../imgs/Pasted%20image%2020251106143842.png)

우선 기존 token.secret 프로퍼티값을 확인하기 위해서 user-service에 health-check를 요청합니다.
실행 결과를 보면 "banana-change-my-very-long-secret-key-1234567890123456-from-git" 값인 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251106144356.png)

## Spring Cloud Bus 테스트

Local Git Repository의 ecommerce.yaml 파일에 있는 token.secret 프로퍼티 값을 수정해보겠습니다.
![](../imgs/Pasted%20image%2020251106144929.png)

config-service에 요청하여 변경된 정보가 잘 반영되었는지 확인합니다.
실행 결과를 보면 프로퍼티 변경이 잘 반영된 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251106145042.png)

이번에는 user-serivce에 health-check하여 token.secret 프로퍼티를 확인해봅니다.
실행 결과를 보면 아직 token.secret 프로퍼티 변경이 반영되지 않은 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251106145235.png)

user-service에 actuator busrefresh 요청전에 apigateway-service에 라우팅 정보를 추가합니다.
```yaml
- id: user-service-actuator  
  uri: lb://USER-SERVICE  
  predicates:  
    - Path=/user-service/actuator/**  
    - Method=GET,POST  
  filters:  
    - RemoveRequestHeader=Cookie  
    - RewritePath=/user-service/(?<segment>.*), /$\{segment}
```

user-service를 대상으로 acutator busrefresh 요청합니다.
![](../imgs/Pasted%20image%2020251106150119.png)

user-serivce의 로그를 확인해봅니다.
실행 결과를 보면 user-service가 갱신된 것을 확인할 수 있습니다.
![](../imgs/Pasted%20image%2020251106150213.png)

이번에는 apigateway-service의 로그를 확인해봅니다.
실행 결과를 보면 역시 apigateway-service 또한 갱신된 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251106150324.png)

user-service에 health-check 요청하여 token.secret이 변경되었는지 확인합니다.
실행 결과를 보면 token.secret 값이 변경된 "banana-change-my-very-long-secret-key-1234567890123456-from-git-changed-#1" 값인 것을 확인할 수 있습니다.
![](../imgs/Pasted%20image%2020251106150527.png)

이번에는 apigateway-service에 user-service의 health-check 요청하여 토큰값이 변경되었는지 확인합니다. 
실행 결과를 보면 apigateway-service가 참조하는 token.secret 프로퍼티 값또한 최신의 것으로 변경된 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251106150752.png)

이번에는 token.secret 값이 한번더 변경합니다.
![](../imgs/Pasted%20image%2020251106151415.png)

모든 서비스가 실행된 상태에서 apigateway-service에게 actuator busrefresh를 요청합니다.
![](../imgs/Pasted%20image%2020251106151507.png)

busrefresh가 완료된 이후 apigateway-service와 user-service의 토큰값이 변경되었는지 확인합니다.

다음 실행 결과는 apigateway-service의 token.secret 프로퍼티 참조 결과입니다. 실행 결과를 보면 변경된 토큰값을 참조하는 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251106151555.png)

user-service의 health-check 결과를 보면 user-serivce 또한 최신 토큰값으로 출력된 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251106151649.png)

위 테스트를 통해 알수 있는 사실은 다음과 같습니다.
- apigateway-service, user-service 중 하나의 서비스에 actuator busrefresh 요청을 하면 다른 서비스들도 변경사항이 적용됩니다.


## 섹션 소개
- Design Deployment
- Configuration Server
- Eureka Discovery
- API Gateway
- Maria DB
- Kafka
- Zipkin
- Monitoring
- Microservices
- Multiple Environments

## 애플리케이션 배포 구성
### Running Microservices
- 기존 Intellij IDEA에서 로컬로 실행했음
- Jar 파일을 Docker Container 기반 환경에서 실행하도록 함
- 대표적인 배포 환경으로 GCP, AWS가 있습니다.
- 배포 환경 종류
	- Local + Intellij IDEA
	- Local + Jar File
	- Local + Docker
	- AWS EC2 + Docker
	- AWS EC2 + Docker Swarm Mode + Docker
	- AWS EC2 + Kubernetes + Docker

이 실습에서는 Local + Docker 배포 환경에서 애플리케이션을 배포할 예정입니다.


### Running Microservices in Local
로컬환경에서 docker container 기반으로 다음 애플리케이션이 배포 구성됩니다.
- Users Microservice
- Catalogs Microservice
- Orders Microservice
- Eureka
- API Gateway
- Configuration
- RabbitMQ
- MariaDB
- Kafka
- Zipkin
- Prometheus
- Grafana


### Create Bridge Network
네트워크 종류
- Bridge Network
	- 컨테이너 서비스들간에 통신하기 위한 네트워크
	- `docker network create --driver bridge [브릿지 이름]`
- Host Network
	- 네트워크를 호스트 환경을 기준으로 설정하여 컨테이너 서비스들간에 통신을 수행하게함
	- 호스트의 네트워크 환경을 그대로 사용함
	- 포트 포워딩 없이 내부 애플리케이션 사용
- None Network
	- 네트워크를 사용하지 않음
	- lo 네트워크만 사용, 외부와 단절됨

브릿지 네트워크 생성
```
docker network create --gateway 172.19.0.1 --subnet 172.19.0.0/16 ecommerce-network
```
![](../imgs/Pasted%20image%2020251125160730.png)
- gateway와 subnet을 명시적으로 설정해서 해당 네트워크안에서 실행되는 컨테이너들의 IP 주소들을 일정 범위안에서 할당 및 동작하도록 합니다.
- 도커 네트워크를 활용하여 네트워크 안에 컨테이너 이름을 이용해서 서로 통신할 수 있다.
- `--subnet 172.19.0.0/16` 
	- docker 네트워크가 사용할 IP 주소 범위(대역)을 의미합니다.
	- `172.19.0.0` : 네트워크 주소
	- `/16` : 서브넷 마스크 `255.255.0.0`
	- 사용 가능한 호스트 IP 범위
		- `172.19.0.1 ~ 172.19.255.254`
		- `172.19.255.254` 는 브로드캐스트 주소. 해당 네트워크 안에 모든 호스트(장치)들에게 동시에 데이터를 보내기 위해 예약된 주소입니다.
- `--gateway 172.19.0.1`
	- docker가 생성하는 브릿지 네트워크의 가상 라우터 IP
	- 모든 컨테이너는 이 IP를 통해 외부(호스트)로 패킷을 전송함
	- 컨테이너 내부에서 `route -n` 명령을 보면 default gateway 로 잡힘
	- 컨테이너들은 기본적으로 **172.19.0.1** 을 통해 외부와 통신

도커 네트워크 생성 확인
- ecommerce-network 네트워크가 생성된 것을 확인
```shell
docker network ls
```
![](../imgs/Pasted%20image%2020260625123553.png)


도커 네트워크 분석하기
```shell
docker network inspect ecommerce-network
```

실행 결과에서 Container 프로퍼티를 확인하면 현재 이 네트워크에서 실행중인 컨테이너가 아무것도 없다는 것을 알 수 있습니다.
![](../imgs/Pasted%20image%2020260625123720.png)
![](../imgs/Pasted%20image%2020260625123732.png)

네트워크 및 서비스 구성도
host 및 ecommerce-network 네트워크와 그 네트워크 안에서 할당된 서비스들의 구성도는 다음과 같습니다.
- subnet : 172.19.0.0/16
- gateway : 172.19.0.1
![](../imgs/Pasted%20image%2020260625125536.png)

## Rabbit MQ
### Rabbit MQ Container 실행
- RabbitMQ 컨테이너 서비스 실행시 `ecommerce-network`에서 실행하도록 함
```shell
docker run -d --name rabbitmq \
	--network ecommerce-network \
	-p 15672:15672 -p 5672:5672 -p 15671:15671 -p 5671:5671 -p 4369:4369 \
	-e RABBITMQ_DEFAULT_USER=guest \
	-e RABBITMQ_DEFAULT_PASS=guest \
	rabbitmq:3.13-management
```

실행 결과 확인
![](../imgs/Pasted%20image%2020260625130451.png)

ecommerce-network 네트워크에 RabbitMQ 서비스 수행 확인
```shell
docker network inspect ecommerce-network
```

실행 결과를 보면 해당 네트워크 안에 rabbitmq 컨테이너가 실핼중인것을 확인할 수 있습니다. 그리고 rabbitmq 컨테이너의 ipv4 주소가 `172.19.0.2`인것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020260625130535.png)

rabbitmq 컨테이너 실행 확인
```shell
docker ps
```
![](../imgs/Pasted%20image%2020260625130922.png)


## Configuration Service
### Create Config Server Docker Image
Dockerfile
```dockerfile
FROM eclipse-temurin:21-alpine
VOLUME /tmp
COPY apiEncryptionKey.jks apiEncryptionKey.jks
COPY target/config-service-1.0.jar config-service.jar  
ENTRYPOINT ["java", "-jar", "config-service.jar"]
```
- apiEncryptionKey.jks : 설정 파일에 유출되면 안되는 민감한 정보를 암호화하거나 복호화할때 사용하는 암호키를 보관하는 용도임.
- config service를 배포하기 위해서는 이 암호화 키 파일(apiEncryptionKey)이 필요함


config-service 빌드시 버전 설정
- config-service jar 파일로 패키징할때 버전이 1.0으로 나오도록 버전을 변경합니다.
![](../imgs/Pasted%20image%2020260625132129.png)

config-service key-store 프로퍼티 변경
bootstrap.yaml 파일의 `encrypt.key-store.location` 프로퍼티를 apiEncryptionKey.jks 파일이 저장된 경로를 입력해야 합니다.
apiEncryptionKey.jks 파일을 config-service 프로젝트의 최상위 경로에 위치시킵니다.
![](../imgs/Pasted%20image%2020260625134332.png)
![](../imgs/Pasted%20image%2020260625134210.png)

maven 기반 컴파일 및 패키징 명령어
```shell
mvn clean compile package -DsktipTests=true
```


> [!NOTE] host pc의 java 기본 자바 버전 변경
> mac os zshell 터미널에서mvn 기반으로 컴파일 및 패키징 수행시 다음과 같이 java 버전이 지원되지 않아서 실패하는 경우가 있습니다.
> ![error](../imgs/Pasted%20image%2020260625135421.png)
> 에러가 발생한 원인은 현재 mac os의 기본 자바 버전은 openjdk 17.0.18이고 config-service의 설정된 자바 버전은 21이기 때문입니다.
> ![java-version](../imgs/Pasted%20image%2020260625135519.png)
> 
> 변경 자바 버전 경로 확인
> - 실행 결과를 확인하면 open jdk 17.0.18에서 open jdk 21.0.8로 변경하고자 합니다.
> - open jdk 21.0.8의 경로를 확인합니다.
> ```shell
> /usr/libexec/java_home -V
> ```
> ![java-version](../imgs/Pasted%20image%2020260625140357.png)
> 
> `~/.zshrc 파일 수정`
> ![zshrc](../imgs/Pasted%20image%2020260625140616.png)
> 
> 위와 같이 JAVA_HOME 환경변수를 설정합니다.
> 
> 환경변수 변경 적용
> ```shell
> source ~/.zshrc
> java -version
> ```
> ![](../imgs/Pasted%20image%2020260625140745.png)

실행 결과 확인
실행 결과를 보면 정상적으로 config-service-1.0.jar 파일이 생성된 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020260625140923.png)

Config-Service 도커 빌드 수행
```shell
docker build -t nemo1107/config-service:1.0 .
```
![](../imgs/Pasted%20image%2020260625142449.png)

Config-Service 이미지 생성 확인
```shell
docker image ls
```
![](../imgs/Pasted%20image%2020260625142500.png)

Config-Service 컨테이너 실행
```shell
docker run -d -it --name my-config-service -p 8888:8888 --network ecommerce-network nemo1107/config-service:1.0
```
![](../imgs/Pasted%20image%2020260625142511.png)


Config-Service의 RabbitMQ 서비스 참조 현황
현재 Config-Service 서버의 RabbitMQ 설정은 다음과 같습니다. 현재 rabbitmq 서버에 대한 호스트 주소 및 포트번호는 127.0.0.1:5672를 가리킵니다.
```yaml
spring:  
  application:  
    name: config-service  
  # ...
  rabbitmq:  
    host: 127.0.0.1  
    port: 5672  
    username: guest  
    password: guest
```

rabbitmq 서버에 대한 접근 주소를 `127.0.0.1:5672`로 하겠다는 것은 config-service 컨테이너 서버 안에  5672 포트에 접근하겠다는 의미입니다. 하지만 config-service 컨테이너 안에 별도의 rabbitmq 서버는 실행하고 있지 않기 때문에 접근에 실패할 것입니다. 이 상황을 그림으로 표현하면 다음과 같습니다.
![](../imgs/Pasted%20image%2020260625144251.png)

위와 같은 문제를 해결하기 위해서는 가장 간단한 방법은 RabbitMQ 컨테이너의 IP 주소 및 포트를 설정하는 것입니다. 위 예시와 같은 경우에는 `host=172.19.0.6`, `port=5672`와 같이 설정하면 접근이 가능합니다. 단, Config 컨테이너와 RabbitMQ 컨테이너는 같은 네트워크에 위치해야 합니다.

하지만 RabbitMQ 컨테이너의 호스트 주소는 사정에 따라서 변경될 수 있습니다. 그래서 고정적인 IP 주소를 사용하는 것은 접근하지 못할 가능성이 높습니다. 이 문제를 해결하기 위해서 host 프로퍼티 이름으로 **IP 주소가 아니라 컨테이너 서비스의 이름인 "rabbitmq"를 입력**합니다.

Config-Service 컨테이너 실행 (프로퍼티 수정)
```shell
docker run -d -it \
	--name config-service \
	-p 8888:8888 \
	--network ecommerce-network \
	-e "spring.rabbitmq.host=rabbitmq" \
	-e "spring.profiles.active=default" \
	nemo1107/config-service:1.0
```

ecommerce-network 네트워크의 실행중인 컨테이너 확인
```shell
docker network inspect ecommerce-network
```
![](../imgs/Pasted%20image%2020260625151201.png)

config-service 컨테이너의 로그 확인
실행 결과를 보면 정상적으로 rabbitmq 서비스에 연결되었습니다.
![](../imgs/Pasted%20image%2020260625152129.png)

config-service 설정을 도커 컨테이너에 맞게 변경
현재 config-service 서버의 spring cloud server 설정파일 참조는 다음과 같습니다.
```yml
spring:  
  application:  
    name: config-service  
  cloud:  
    config:  
      server:  
        native:  
          search-locations: file://${user.home}/Documents/native-file-repo  
        git: # default  
#          uri: file:///Users/yonghwankim/Documents/git-local-repo  
          uri: https://github.com/joneconsulting/spring-cloud-config  
          default-label: master
```

git.uri 주소를 포크한 나의 저장소로 설정합니다.
```yaml
spring:  
  application:  
    name: config-service  
  cloud:  
    config:  
      server:  
        native:  
          search-locations: file://${user.home}/Documents/native-file-repo  
        git: # default  
          uri: https://github.com/yonghwankim-dev/spring-cloud-config.git  
          default-label: master
```

config-service를 다시 컴파일 및 패키징합니다.
```shell
mvn clean compile package -DsktipTests=true
```

도커 이미지 빌드전에 기존 이미지를 삭제합니다.
```shell
 docker image rm nemo1107/config-service:1.0
```

도커 이미지를 다시 빌드합니다.
```shell
docker build -t nemo1107/config-service:1.0 .
```

config-service 컨테이너 실행
```shell
docker run -d -it \
	--name config-service \
	-p 8888:8888 \
	--network ecommerce-network \
	-e "spring.rabbitmq.host=rabbitmq" \
	-e "spring.profiles.active=default" \
	nemo1107/config-service:1.0
```
![](../imgs/Pasted%20image%2020260625155238.png)

ecommerce-network 네트워크에 config-service 컨테이너가 포함되어 있는지 확인
```shell
docker network inspect ecommerce-network
```
![](../imgs/Pasted%20image%2020260625155322.png)

config-service 서비스의 ecommerce 설정 확인
![](../imgs/Pasted%20image%2020260625155715.png)
![](../imgs/Pasted%20image%2020260625155800.png)

---
## Discovery Service
### 디스커버리 서비스의 설정 추가
config service의 URI 및 참고하고자 하는 설정 이름을 추가합니다.

의존성 추가
```xml
<dependency>  
    <groupId>org.springframework.cloud</groupId>  
    <artifactId>spring-cloud-starter-config</artifactId>  
</dependency>  
<dependency>  
    <groupId>org.springframework.cloud</groupId>  
    <artifactId>spring-cloud-starter-bootstrap</artifactId>  
</dependency>
```

프로퍼티 추가 (bootstrap.yaml)
- 디스커버리 서버는 ecommerce.yaml 설정 파일을 참조합니다.
```yaml
spring:  
  cloud:  
    config:  
      uri: http://127.0.0.1:8888  
      name: ecommerce
```

### 유레카 디스커버리 도커 이미지 빌드하기
Dockerfile
```Dockerfile
FROM eclipse-temurin:21-alpine
VOLUME /tmp
COPY target/service-discovery-1.0.jar service-discovery.jar
ENTRYPOINT ["java", "-jar", "service-discovery.jar"]
```

mvn 컴파일 및 패키징
```shell
mvn clean compile package -DsktipTests=true
```
![](../imgs/Pasted%20image%2020260625162416.png)

도커 이미지 빌드
```shell
docker build -t nemo1107/service-discovery .
```
![](../imgs/Pasted%20image%2020260625164636.png)

도커 이미지 푸시
```shell
docker push nemo1107/service-discovery
```
![](../imgs/Pasted%20image%2020260625164748.png)

Discovery 서비스 컨테이너 실행
```shell
docker run -d -it -p 8761:8761 \
	--network ecommerce-network \
	-e "spring.cloud.config.uri=http://config-service:8888" \
	--name service-discovery \
	nemo1107/service-discovery
```
![](../imgs/Pasted%20image%2020260625164809.png)

Discovery 서비스 컨테이너 로그 확인
```shell
docker logs service-discovery
```
![](../imgs/Pasted%20image%2020260625164827.png)

ecommerce-network 네트워크에서 service-discovery 컨테이너 실행 확인
```shell
docker network inspect ecommerce-network
```
![](../imgs/Pasted%20image%2020260625165109.png)

웹 브라우저를 통해서 service-discovery 접근
![](../imgs/Pasted%20image%2020260625165148.png)

---
## Apigateway Service
### Apigateway Service 도커 이미지 빌드하기
Apigateway Service Dockerfile
```Dockerfile
FROM eclipse-temurin:21-alpine
VOLUME /tmp
COPY target/apigateway-1.0.jar Apigateway.jar  
ENTRYPOINT ["java", "-jar", "Apigateway.jar"]
```

mvn 컴파일 및 패키징
```shell
mvn clean compile package -DsktipTests=true
```

도커 이미지 빌드
```shell
docker build -t nemo1107/apigateway-service:1.0 .
```

도커 이미지 푸시
```shell
docker push nemo1107/apigateway-service:1.0
```

### Apigateway Service 실행하기
Apigateway Service를 도커 기반 컨테이너를 실행하고 다른 서비스와 통신하기 위해서 몇개의 프로퍼티를 도커 실행시 설정해야 합니다.
```shell
docker run -d -p 8000:8000 --network ecommerce-network \
	-e "spring.cloud.config.uri=http://config-service:8888" \
	-e "spring.rabbitmq.host=rabbitmq" \
	-e "eureka.client.service-url.defaultZone=http://service-discovery:8761/eureka" \
	--name apigateway-service \
	nemo1107/apigateway-service:1.0
```

---
## MariaDB
### MariaDB 도커 이미지 빌드하기
Dockerfile
```Dockerfile
FROM mariadb  
ENV MARIADB_ROOT_PASSWORD test1357  
ENV MARIADB_DATABASE mydb  
COPY --chown=mysql:mysql ./mariadb /var/lib/mysql  
EXPOSE 3306
```

도커 이미지 빌드하기
```shell
docker build -t nemo1107/my_mariadb:1.0 -f Dockerfile_mariadb .
```

### MariaDB 컨테이너 실행하기
```shell
docker run -d -p 3306:3306 \
	--network ecommerce-network \
	--name mariadb \
	nemo1107/my_mariadb:1.0
```



---

## Kafka
### Kafka Server 실행하기
Kafka Standalone
- docker-compose로 실행
- `git clone https://github.com/wurstmeister/kafka-docker.git`
- docker-compose-single-broker.yaml 수정

```shell
docker-compose -f docker-compose-single-broker.yaml up -d
```

docker-compose-single-broker.yaml
```yaml
version: '3'  
services:  
  kafka:  
    image: apache/kafka:latest  
    container_name: kafka  
    ports:  
      - "9092:9092"  
    environment:  
      # 1. 클러스터 역할 및 고유 ID 설정 (KRaft 모드 필수 설정)  
      KAFKA_NODE_ID: 1  
      KAFKA_PROCESS_ROLES: broker,controller  
      # 2. 리스너(접속 규격) 설정  
      # 내부 도커 네트워크 통신(9092)과 내부 컨트롤러 통신(29093) 분리  
      KAFKA_LISTENERS: PLAINTEXT://0.0.0.0:9092,CONTROLLER://0.0.0.0:29093  
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:9092  
      KAFKA_CONTROLLER_LISTENER_NAMES: CONTROLLER  
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT  
      KAFKA_CONTROLLER_QUORUM_VOTERS: 1@kafka:29093  
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1  
      KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: 1  
      KAFKA_TRANSACTION_STATE_LOG_MIN_ISR: 1  
      KAFKA_GROUP_INITIAL_REBALANCE_DELAY_MS: 0  
      KAFKA_NUM_PARTITIONS: 3  
    networks:  
      ecommerce-network:  
        ipv4_address: 172.19.0.100  
  kafka-ui:  
    image: provectuslabs/kafka-ui:latest  
    container_name: kafka-ui  
    ports:  
      - "8089:8080"  
    environment:  
      DYNAMIC_CONFIG_ENABLED: 'true'  
      KAFKA_CLUSTERS_0_NAME: local-kafka-cluster  
      KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS: kafka:9092 # 동일 네트워크 안의 kafka 서비스 이름 지정  
    depends_on:  
      - kafka  
    networks:  
      ecommerce-network:  
        ipv4_address: 172.19.0.101  
networks:  
  ecommerce-network:  
    external: true
```

kafka, kafka-ui 실행
```shell
docker compose -f docker-compose-single-broker.yaml up -d
```

kafka-ui 접속
- local-kafka-cluster가 online으로 뜨면 성공입니다.
![](../imgs/Pasted%20image%2020260629154533.png)

---

## Zipkin
### Zipkin 실행하기
```shell
docker run -d -p 9411:9411 \
	--network ecommerce-network \
	--name zipkin \
	openzipkin/zipkin
```


---
## Monitoring
### Prometheus 실행하기
```shell
docker run -d -p 9090:9090 \
	--network ecommerce-network \
	--name prometheus \
	-v ./config/prometheus/prometheus.yml:/etc/prometheus/prometheus.yml \
	prom/prometheus
```

### Grafana 실행하기
```shell
docker run -d -p 3000:3000 \
	--network ecommerce-network \
	--name grafana \
	grafana/grafana
```


---

## Deployed Services
현재 배포된 서비스 상태는 다음과 같습니다.
![](../imgs/Pasted%20image%2020260702115219.png)

ecommerce-network에 소속된 컨테이너들의 내부 IP 주소는 다음과 같습니다.
- maraidb : 172.19.0.5/16
- rabbitmq : 172.19.0.6/16
- zipkin : 172.19.0.8/16
- prometheus : 172.19.0.2/16
- grafana : 172.19.0.9/16
- service-discovery : 172.19.0.7/16
- kafka : 172.19.0.100/16
- kafka-ui : 172.19.0.101/16
- config-service : 172.19.0.4/16
- apigateway-service : 172.19.0.3/16

user-service, order-service, catalog-service의 내부 IP 주소는 변경되어도 중요치 않지만, 위 서비스들의 IP 주소는 고정되어 있는편이 좋다. 왜냐하면 위 서비스들을 참조하는 경우가 있기 때문이다.

---

## User Microservice
### User Microservice 도커 이미지 빌드 및 실행하기
Dockerfile
```dockerfile
FROM eclipse-temurin:21-alpine  
VOLUME /tmp  
COPY target/user-service-1.0.jar user-service.jar  
ENTRYPOINT ["java", "-jar", "user-service.jar"]
```

user-service 컴파일 및 패키징
```shell
mvn clean compile package -DsktipTests=true
```

user-service 도커 이미지 빌드
```shell
docker build -t nemo1107/user-service:1.0 .
```

user-service 도커 허브에 푸시
```shell
docker push nemo1107/user-service:1.0
```

user-service 도커 컨테이너 실행
```shell
docker run -d --network ecommerce-network \
	--name user-service \
	-e "spring.cloud.config.uri=http://config-service:8888" \
	-e "spring.rabbitmq.host=rabbitmq" \
	-e "management.zipkin.tracing.endpoint=http://zipkin:9411/api/v2/spans" \
	-e "eureka.client.service-url.defaultZone=http://service-discovery:8761/eureka/" \
	-e "logging.file=/api-logs/users-ws.log" \
	nemo1107/user-service:1.0
```

---

## Order Microservice
### Order Microservice 도커 이미지 빌드 및 실행하기
Dockerfile
```Dockerfile
FROM eclipse-temurin:21-alpine  
VOLUME /tmp  
COPY target/order-service-1.0.jar order-service.jar  
ENTRYPOINT ["java", "-jar", "order-service.jar"]
```

order-service 컴파일 및 패키징
```shell
mvn clean compile package -DsktipTests=true
```

order-service 도커 이미지 빌드
```shell
docker build -t nemo1107/order-service:1.0 .
```

order-service 도커 허브 푸시
```shell
docker push nemo1107/order-service:1.0
```

order-service 도커 컨테이너 실행
```shell
docker run -d --network ecommerce-network \
	--name order-service \
	-e "management.zipkin.tracing.endpoint=http://zipkin:9411/api/v2/spans" \
	-e "eureka.client.service-url.defaultZone=http://service-discovery:8761/eureka" \
	-e "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDBDialect" \
	-e "spring.datasource.url=jdbc:mariadb://mariadb:3306/mydb" \
	-e "logging.file=/api-logs/orders-ws.log" \
	nemo1107/order-service:1.0
```

---

## Catalog Microservice
### Catalog Microservice 도커 이미지 빌드 및 실행하기
Dockerfile
```shell
FROM eclipse-temurin:21-alpine  
VOLUME /tmp  
COPY target/catalog-service-1.0.jar catalog-service.jar  
ENTRYPOINT ["java", "-jar", "catalog-service.jar"]
```

catalog-service 컴파일 및 패키징
```shell
mvn clean compile package -DsktipTests=true
```

catalog-service 도커 이미지 빌드
```shell
docker build -t nemo1107/catalog-service:1.0 .
```

catalog-service 도커 허브에 푸시
```shell
docker push nemo1107/catalog-service:1.0
```

catalog-service 도커 컨테이너 실행
```shell
docker run -d --network ecommerce-network \
	--name catalog-service \
	-e "eureka.client.service-url.defaultZone=http://service-discovery:8761/eureka" \
	-e "logging.file=/api-logs/catalogs-ws.log" \
	nemo1107/catalog-service:1.0
```


---

## Test
지금까지 만든 도커 기반 컨테이너 서비스들을 실행한 다음에 테스트 해봅니다.

User-Service에 사용자 등록
![](../imgs/Pasted%20image%2020260702151545.png)

User-Service 로그인 요청
![](../imgs/Pasted%20image%2020260702151608.png)

Order-Service 상품 주문 요청
![](../imgs/Pasted%20image%2020260702151645.png)

Catalog-Service에 상품 목록의 재고 변화 확인
![](../imgs/Pasted%20image%2020260702151718.png)

User-Service에 주문 상품 조회
![](../imgs/Pasted%20image%2020260702151747.png)

---

## Multi Profiles
### Microservice 실행
- 도커 파일을 기반으로 로컬 개발 환경에서 실행할수도 있고, AWS와 같은 클라우드 환경에서 실행할 수도 있습니다.
- Spring 서버 실행시 프로파일을 설정하여 환경에 맞게 설정을 변경하여 실행할 수 있습니다.
	- mvn 기반으로 실행시 `-Dspring.boot.run.arguments=--spring.profiles.active` 사용
	- jar 파일 실행시 `-Dspring.profiles.active` 사용
![](../imgs/Pasted%20image%2020260702152057.png)

Config Server에 yaml 파일이나 properties 파일을 주입하여 환경에 맞도록 설정을 주입할 수 있습니다. 예를 들어 AWS 환경에서 실행해야 하는 설정 파일 이름들을 `A.production.yaml`과 같이 작성할 수 있습니다.

다음 사진은 spring-cloud-config 원격 저장소의 파일 구성입니다. ecommerce 설정 파일을 보면 dev, prod와 같은 프로파일도 있습니다. 그리고 단순한 ecommerce.yaml 파일 같은 경우에는 기본 설정으로써 profile=default인 경우에 적용되는 설정 파일입니다. 따라서 다음과 같이 여러 환경에 맞는 프로파일 설정 파일들을 작성 및 저장해두고 가져다 쓸수 있습니다. 
![](../imgs/Pasted%20image%2020260702152923.png)
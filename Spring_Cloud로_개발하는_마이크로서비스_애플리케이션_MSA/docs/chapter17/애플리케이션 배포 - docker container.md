
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
	- `docker network create --driver bridge [브릿지 이름]`
- Host Network
	- 네트워크를 호스트로 설정하면 호스트의 네트워크 환경을 그대로 사용함
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
- `--gateway 172.19.0.1`
	- docker가 생성하는 브릿지 네트워크의 가상 라우터 IP
	- 모든 컨테이너는 이 IP를 통해 외부(호스트)로 패킷을 전송함
	- 컨테이너 내부에서 `route -n` 명령을 보면 default gateway 로 잡힘
	- 컨테이너들은 기본적으로 **172.19.0.1** 을 통해 외부와 통신

도커 네트워크 분석하기
```shell
docker network inspect ecommerce-network
```
![](../imgs/Pasted%20image%2020251125160806.png)

## Rabbit MQ
### Rabbit MQ Container 실행
```shell
docker run -d --name rabbitmq --network ecommerce-network \
	-p 15672:15672 -p 5672:5672 -p 15671:15671 -p 5671:5671 -p 4369:4369 \
	-e RABBITMQ_DEFAULT_USER=guest \
	-e RABBITMQ_DEFAULT_PASS=guest \
	rabbitmq:management
```

실행 결과 확인
![](../imgs/Pasted%20image%2020251125163109.png)


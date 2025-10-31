
## E-commerce 애플리케이션 개요
- CATALOG-SERVICE
	- 상품 정보 제공
	- 상품 재고 수량 관리
- USER-SERVICE
	- 사용자 정보 제공
	- 사용자 정보 관리
- ORDER-SERVICE
	- 주문 정보 제공
	- 주문 처리
	- 주문 정보 관리
![](imgs/Pasted%20image%2020251029145411.png)

만약 사용자가 "주문 확인" 서비스를 요청한다면 User-Service는 주문에 대한 정보를 가지고 있지 않기 때문에 Order-Service에 사용자 ID를 전달하며 사용자 ID가 가지고 있는 주문 목록 조회를 요청할 것입니다. 왜냐하면 User-Service의 데이터베이스에는 주문 정보를 가지고 있지 않기 때문입니다.

만약 사용자가 "상품 주문" 서비스를 요청하면 Order-Service에서는 상품 수량을 업데이트하기 위해서 Catalog-Service에 "상품 수량 업데이트" 요청을 할 것입니다.


## E-commerce 애플리케이션 구성
### 전체 애플리케이션 구성
Registry Service(Eureka Server) 사용하여 마이크로서비스들의 등록 관리

Catalog-Service, User-Service, Order-Service 마이크로서비스들은 Registry Service에 등록됩니다. 각각의 서비스들은 하나의 PC에서 다른 포트번호로 실행됩니다. 그리고 서비스들은 각각의 데이터베이스를 가지고 있습니다.

Catalog-Service, User-Service, Order-Service 구성
- Server: Spring Boot
- DB: H2 (Inmemory Database)

Order-Service에서 Caltalog-Service로 상품을 조회할때 동기적인 방식이 아닌 비동기적인 방식으로 수행됩니다. 비동기적 방식을 수행하기 위해서 메시지 브로커(Message Channels)를 이용합니다. 메시지가 전달되면 Catalog Service가 메시지를 가져가고 다시 메시지 채널에 결과를 메시지로 전송합니다.

즉, 서비스들끼리 메시지를 주고받을때 직접적으로 보내고 받을때까지 대기하는 방식이 아닌 메시지 브로커를 통하여 메시지를 주고 받을 수 있도록 설계합니다. 이렇게 하면 비동기적인 방식이기 때문에 메시지를 보내고 서비스는 자기 할일을 할 수 있습니다.
![](imgs/Pasted%20image%2020251029152517.png)

마이크로서비스 앞단에 API Gateway를 배치하여 클라이언트 요청에 대한 **부하 분산/라우팅 서비스**를 제공합니다.
![](imgs/Pasted%20image%2020251029153434.png)

Configuration Service를 사용하여 서비스들이 설정을 참고할때 별도의 Config Server를 참조하여 사용할 수 있도록 합니다. 별도의 Config Server를 두는 이유는 만약 마이크로서비스의 설정이 변경되면 다시 배포해야 되지만 Config Server를 사용하면 마이크로서비스를 별도로 배포하지 않아도 됩니다.
![](imgs/Pasted%20image%2020251029153647.png)

마이크로서비스들은 쿠버네티스 클러스터 위에서 작동합니다. 각각의 서비스들은 도커 컨테이너 단위로 실행됩니다.
![](imgs/Pasted%20image%2020251029154421.png)

마이크로서비스들에 대한 모니터링 정보들은 프로메테우스 및 그라파나에 의해서 표시될 것입니다.
![](imgs/Pasted%20image%2020251029154831.png)

개발자가 로컬 환경에서 서비스를 개발하고 커밋 및 푸시하게 되면 CI/CD Pipeline이 작동하여 배포 작업을 자동적으로 수행합니다. 그 과정에서 Docker Registry에 이미지 파일이 업로드되고 최종적으로 쿠버네티스 클러스터에 배포하게 됩니다.
![](imgs/Pasted%20image%2020251029155141.png)

### 전체 애플리케이션 구성 요소

| 구성요소               | 설명                           | Inner/Outer Architecture |
| ------------------ | ---------------------------- | ------------------------ |
| Git Repository     | 마이크로서비스 소스 관리 및 프로파일 관리      | -                        |
| Config Server      | Git 저장소에 등록된 프로파일 정보 및 설정 정보 | Outer                    |
| Eureka Server      | 마이크로서비스 등록 및 검색              | Outer                    |
| API Gateway Server | 마이크로서비스 부하 분산 및 서비스 라우팅      | Outer                    |
| Microservices      | 회원 서비스, 주문 서비스, 상품(카탈로그) 서비스 | Inner                    |
| Message Broker     | 마이크로서비스 간 메시지 발행 및 구독        | Outer                    |
- 프로파일 관리는 배포 환경별(local, release, production) 설정 관리를 의미합니다.

### 애플리케이션 APIs

| 마이크로서비스         | REST API(Endpoint)                                                                 | 설명                                               | HTTP Method              |
| --------------- | ---------------------------------------------------------------------------------- | ------------------------------------------------ | ------------------------ |
| Catalog Service | - /catalog-service/catalogs                                                        | - 상품 목록 제공                                       | - GET                    |
| User Service    | - /user-service/users<br>- /user-service/users<br>- /user-service/users/{user_id}  | - 사용자 정보 등록<br>- 전체 사용자 조회<br>- 사용자 정보, 주문 내역 조회 | - POST<br>- GET<br>- GET |
| Order Service   | - /order-service/users/{user_id}/orders<br>- /order-service/users/{user_id}/orders | - 주문 등록<br>- 주문 확인                               | - POST<br>- GET          |



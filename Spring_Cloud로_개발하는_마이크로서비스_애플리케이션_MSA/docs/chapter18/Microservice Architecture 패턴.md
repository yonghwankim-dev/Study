
목차
- Event Sourcing
- CQRS Pattern
- Saga Pattern

## EventSourcing + CQRS + Saga Pattern
### 이벤트 기반 아키텍처(Event Driven Architecture)
모놀리식(Monolithic) 방식
- 단일 데이터베이스
- 트랜잭션 처리 -> ACID
	- 원자성(Atomicity)
	- 일관성(Consistency)
	- 독립성(Isolation)
	- 지속성(Durable)

마이크로서비스(Microservice) 방식
- 각 서비스마다 독립적인 DB(Polyglot)
- API를 통해 접근

모놀리식 방식의 서버는 다른 서버의 데이터베이스에 접속하는 것을 원칙적으로 금지하고 있습니다. 마이크로서비스 방식에서는 대신에 API를 이용해서 통신하도록 하고 있습니다. 예를 들어 user-service가 catalog-service와 연결된 데이터베이스를 참조하고 싶을때는 직접적으로 연결하는 것이 아닌 catalog-service 서버의 API에 요청하여 접근합니다.

마이크로서비스 방식에서는 모놀리식 방식에서 지원되었던 트랜잭션 처리 방식을 완벽하게 지원하지는 않습니다. 대신에 다른 방식으로 트랜지션을 지원하고 있습니다. 예를 들어 Commit Transaction이 존재합니다.
- Atomicity, Consistency -> Commit Transaction

**마이크로서비스 아키텍처의 커밋 트랜잭션 동작과정**
order-service에서 order-id=2인 상품 주문을 처리합니다. 그리고 다음과 같이 동작합니다.
1. order-service는 order-id=2인 주문 데이터를 생성(저장)한 다음에 status 컬럼의 값을 대기(PENDING) 상태로 저장합니다.
2. order-service는 메시지 브로커인 카프카에게 주문 정보를 담은 이벤트 메시지를 전송합니다.
3. catalog-service는 카프카로부터 주문 메시지를 읽습니다.
4. catalog-service는 주문에서 해당하는 상품의 수량만큼 catalog-002 데이터의 수량을 감소시킵니다.
5. 업데이트한 성공한 catalog-service는 카프카에게 확정 메시지를 전송합니다.
6. order-service는 카프카로부터 확정 메시지를 읽습니다.
7. order-service는 대기(PENDING) 상태의 주문 데이터를 확정(CONFIRM) 상태로 업데이트합니다.
8. 주문 과정을 마칩니다.

![](../imgs/Pasted%20image%2020260702160337.png)

Rollback Transaction
1. catalog-service가 카프카로부터 주문 메시지를 읽습니다.
2. catalog-service가 상품 수량을 감소시키기 전에 수량을 체크합니다.
3. 현재 재고 수량이 부족하여 주문을 처리할 수 없습니다.
4. 카프카에게 거절 메시지를 전달합니다.
5. order-service는 메시지를 받은 다음에 해당 상품 주문 데이터에 대해서 PENDING 상태에서 CANCELED 상태로 변경한다.
![](../imgs/Pasted%20image%2020260817120059.png)

### Event Sourcing
- **이벤트 소싱(Event Sourcing)**이란 **데이터의 마지막 상태만 저장하는 것이 아니라 해당 데이터에 수행된 전체 이력을 기록하는 방법**을 말한다.
- 데이터 구조 단순
- 데이터의 일관성과 트랜잭션 처리 가능
- 데이터 저장소의 개체를 직접 업데이트하지 않기 때문에, 동시성에 대한 충돌 문제 해결

다음 그림을 보면 기존 order-service는 주문 테이블의 하나의 레코드가 "ACCPETED"라는 하나의 상태만 가지고 있는 것을 볼수 있습니다. 그러나 이벤트 소싱을 도입한 구조에서는 어떤 하나의 주문에 대해서 어떤 이벤트들이 발생했는지 이력을 저장하고 있고 customer-service는 해당 이벤트를 읽은 다음에 처리할 수 있습니다.
![](../imgs/Pasted%20image%2020260817121504.png)
Event Sourcing에서는 INSERT 개념만 존재하고 UPDATE, DELETE와 같은 개념은 존재하지 않습니다.

Event Sourcing에 대한 기능을 사용하게 되면 어떤 하나의 주문 데이터에 대한 상태 이력들(Created, Approved, Shipped 등)을 저장할 수 있습니다. 반면에 해당 주문 Event Sourcing 데이터를 소비하는 서비스들은 해당 이력 데이터들을 통해서 상태값 변화를 확인할 수 있습니다.

**Event Sourcing 특징**
- 도메인 주도 설계(Domain-Driven Design)
	- Aggregate : 데이터 상태값을 변경하기 위한 방법
	- Projection : 현재 상태값이 어떤것인지 확인하는 방법
- 메시지 중심의 비동기 작업 처리
- 단점
	- 모든 이벤트에 대해복원 -> 스냅샷 기술을 도입하여 문제 해결
		- 이벤트 데이터가 1000개라면 1000개를 거슬러 올라가야 함. 이로 인해 시간이 걸림.
		- 스냅샷 기술을 도입하여 1~100번까지 트랜잭션만 가지고 있고, 101~200번까지의 트랜잭션만 가지고 있는 방식으로 문제를 해결함
	- 다양한 데이터 조회 -> CQRS을 이용하여 해결

### CQRS
- CQRS(Command and Query Responsibility Segregation)
- 명령과 조회의 상태 분리
	- 상태 변경을 담당하는 Command
	- 조회를 담당하는 Query

예를 들어서 다음 그림을 보면 데이터를 입력만 하는 App(Application write interface)이 존재하고 데이터를 읽기만 하는 App(Application read interface)이 존재한다고 가정합니다. A App이 이벤트 큐에 데이터 입력 이벤트를 입력하면 Event Store에서 저장되고 카프카 이벤트 핸들러에 의해서 데이터베이스 안에 데이터 상태가 변경됩니다. B App은 데이터베이스로부터 데이터를 읽기만 하여 사용합니다.
다음 그림의 Event Queue, Event Store를 합쳐서 Command Model이라고 하고, Application Store를 Query Model이라고 합니다.
![](../imgs/Pasted%20image%2020260817124752.png)


#### E-Commerce application
상품 주문 수행 과정
1. 클라이언트가 API Gateway에게 상품 주문을 요청 (POST /orders)
2. API Gatway는 요청을 order-service에게 전달함
3. order-service는 Command Model(CreateOrder, UpdateOrder, DeleteOrder)를 이용해서 상품 주문을 처리함.
4. Kafka Event Store에게 상품 주문 생성 이벤트를 발행함
5. Kafka Event Store를 구독하고 있는 다른 서비스들(Catalog-Service, 이메일 서비스, 이벤트 핸들러)에게 상품 주문 생성 이벤트를 전달함
6. 이벤트 핸들러에서는 해당 이벤트 데이터를 이용하여 MariaDB의 데이터를 업데이트함

상품 목록 조회 과정
1. 클라이언트가 API Gateway에게 상품 목록 조회를 요청함(GET /orders)
2. API Gateway는 요청을 order-service에게 전달
3. order-service는 ListOrders 서비스를 수행하여 클라이언트에게 응답함
![](../imgs/Pasted%20image%2020260702164953.png)
## Saga Pattern
- 사가 패턴은 애플리케이션에서 트랜잭션을 처리하는 방법을 의미합니다.
	- Choreography, Orchestration
	- 기존에는 데이터베이스 차원에서 트랜잭션을 처리하였습니다.
- 애플리케이션이 분리된 경우에는 각각의 **로컬 트랜잭션**만 처리
- 각 애플리케이션에 대한 연속적인 트랜잭션에서 실패할 경우
	- Rollback 처리 구현 -> **보상 트랜잭션**
- 데이터의 원자성을 보장하지는 않지만, 일관성을 보장함
![](../imgs/Pasted%20image%2020260817130843.png)

애플리케이션이 분리된 경우, 각각의 애플리케이션은 자신만의 트랜잭션만을 처리한다는 개념입니다.
애플리케이션에 대한 작업이 하나로 끝나는 것이 아니라 여러 서비스에 걸쳐서 작동하는 경우 하나의 서비스에서 문제가 생기면 이전 서비스들에 대한 작업을 원상복귀하는 과정이 필요합니다. 이것을 사가 패턴에서는 보상 트랜잭션을 통해서 롤백 처리합니다.

로컬 트랜잭션을 통해서 트랜잭션 성공 및 실패를 다른 서비스에 이벤트 메시지를 통해서 알려줄 수 있습니다.

**커리어그래피 기반 사가(Choreography-based saga)**
![](../imgs/Pasted%20image%2020260817132157.png)
1. 주문 서비스에서 주문 요청(POST)을 수신하고 PENDING 상태의 주문 생성
2. 주문 생성 이벤트 전달
3. 고객 서비스의 Event Handler가 Credit 예약 시도
4. 결과 이벤트 전달
5. 주문 서비스의 Event Handler를 통해 주문 승인 or 거부

오케스트레이션 기반 사가(Orchestration-based saga)
![](../imgs/Pasted%20image%2020260817132757.png)
1. 주문 서비스가 주문 요청(POST)을 수신하고 **Create Order saga orchestrator** 생성
2. Order saga orchestrator가 PENDING 상태의 주문을 생성
3. Credit 예약 명령을 고객 서비스에 전달
4. 고객 서비스가 Credit 예약 처리
5. 결과 메시지 전달
6. Order saga orchestrator에서 주문의 승인 or 거부

Saga 패턴을 사용하는 이유
- 분산 트랜잭션의 데이터 일관성을 보장하기 위해서
- 중간에 서비스가 실패했을때 보상 트랜잭션을 실행해서 이전 상태로 되돌리기 위해서

커리어그래피 기반 사가(Choreography-based saga)
- 중앙의 제어자 없이 각 서비스가 이벤트들을 발행하고 구독하며 자율적으로 다음 동작을 이어가는 방식
- 장점
	- 서비스간 결합도가 낮아서 구현이 가볍고 신속함
	- 중앙 제어자가 없어서 단일 실패 지점(SPOF, Single Point of Failure)이 존재하지 않음
- 단점
	- 서비스가 늘어날수록 트랜잭션 흐름 파악이 어렵고 디버깅이 까다로움
	- 순환 의존성 발생 위험이 존재함

오케스트레이션 기반 사가(Orchestration-based Saga)
- 중앙 오케스트레이터(Orchestrator) 서비스가 전체 사가의 상태를 관리하며, 각 서비스에게 수행할 명령을 내리고 응답을 받는 방식
- 동작 방식
	- 오케스트레이터가 결제 서비스에 결제 요청 명령을 내리고, 결제 완료 응답을 받으면, 다음으로 재고 서비스에 재고 차감 요청 명령을 보낸다. 실패시 오케스트레이터가 보상 명령을 순차적으로 하달함
- 장점
	- 중앙에서 전체 트랜잭션의 진행 상태 및 로직을 한눈에 파악하고 관리 가능함
	- 복잡한 서비스 로직과 보상 트랜잭션의 제어가 수월함
- 단점
	- 오케스트레이터 서비스에 비즈니스 로직이 집중되어 복잡도가 증가할 수 있음
	- 오케스트레이터 자체가 단일 실패 지점(SPOF)이나 병목이 될 위험이 있음

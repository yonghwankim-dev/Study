
### 1. 클래스, 속성, 관계 식별
아키텍처를 작성하기 위해서 먼저 해야 할 것은 요구사항 명세서를 보면서 클래스, 속성, 관계를 식별하는 것입니다. 요구사항 명세서를 보면서 명사들을 식별하고 해당 명사들을 도메인 영역의 주요한 개념으로 선택합니다.

다음 그림은 초기 도메인 모델 예시입니다.
이 도메인 모델은 요구사항에 있는 것들 중에서 정적인 것들을 추출하여 설계한 것입니다. 그림을 보면 클래스 이름 및 필드, 관계만 존재하고 메서드는 없는 것을 볼수 있습니다. 

![[Pasted image 20250701124553.png]]


### 2. 도메인 모델에 행위 추가하기
위에서와 같이 구현한 정적인 도메인 모델에 행위(메서드)를 추가합니다.
도메인 모델의 행위를 결정하기 위해서 도메인 모델의 책임(Responsibility)과 상호작용(Collaboration)을 식별합니다. 클래스의 책임은 **클래스가 아는것(속성, 관계), 하는 것, 결정 하는 것 등**이 있습니다. **클래스의 상호작용은 책임 수행을 위해서 호출하는 다른 클래스들을 의미**합니다.

클래스의 책임과 상호작용을 식별하기 위한 첫번째는 요구 사항(UseCase, 유저스토리, UI 디자인) 분석을 통해서 애플리케이션이 처리해야 할 요구사항을 식별해야 합니다. 두번째는 도메인 모델의 클라이언트(Presentation 등)에게 도메인 모델을 노출하기 위한 도메인 모델의 인터페이스(타입, 메서드)를 결정해야 합니다. 마지막 세번째는 해당 인터페이스를 각각의 요구사항을 고려하여 TDD 접근법으로 구현합니다.

### 2.1 요구사항 식별하기
UseCase, 유저스토리, UI 디자인 등을 분석해서 처리해야할 요구사항을 식별하고 어떻게 응답할지 결정합니다.
요구 사항은 2가지로 구성되어 있습니다. 첫번째는 **사용자 행위**, 두번째는 사용자 행위에 대한 **애플리케이션의 응답(책임)** 입니다.
애플리케이션의 책임은 2가지로 그룹화할 수 있습니다. 첫번째는 **사용자 입력 검증, 값 계산, 데이터베이스 갱신**입니다. 두번째는 **값 출력**입니다. 이는 데이터베이스와 같은 저장소에 데이터를 조회하는 것과 같은 행위입니다.

### 2.2 메서드 식별하기
각 요청에 대한 두가지 메서드 유형이 존재합니다. 첫번째는 서비스 메서드입니다. 해당 메서드는 사용자 요청 검증, 계산 수행, 데이터베이스 갱신 등을 수행합니다. 두번째는 Reository 메서드입니다. 조회를 통하여 출력을 위한 데이터를 반환하는 메서드입니다.

위 두가지 메서드를 기반으로 도메인 모델의 클라이언트는 도메인 티어를 2번 호출합니다. (서비스 메서드 + Repository 메서드)

### 2.3 TDD로 메서드 구현하기
메서드를 구현하기 위해서 TDD 방법론으로 구현을 시작합니다. 우선은 메서드에 대한 Failing Test를 생성하고 통과할 수 있도록 메서드를 구현합니다. 테스트를 통과하고 나서는 리팩토링하는 과정을 거칩니다. 리팩토링 과정을 통해서 매개변수를 추가한다거나, 메서드를 추출하거나 의존성 필드를 추가하는 과정을 수행합니다. 

TDD 구현중 mock 객체를 이용해서 service method -> repository method 순으로 top-down 순으로 구현합니다. 이 방식의 장점은 구현 도중에 새롭게 필요한 협력 객체(collaborator)를 발견해도 머리속에서 컨텍스트 스위칭 없이 그대로 테스트와 구현에 집중할 수 있습니다.
예를 들어 위키 페이지를 생성하는 `WikiPageService.createPage(title, content)` 메서드를 구현한다고 가정해봅니다. 먼저 TDD 방식으로 `WikiPageService` 의 동작을 테스트하면서 `wikiPageReository.save(...)` 가 호출되어야 한다는 사실을 알게 됩니다. 이때 WikiPageRepository는 아직 구현되지 않았지만, mock 객체로 대체하고 테스트를 먼저 작성할 수 있습니다.
이렇게 하면 구현 중간에 새로운 의존 객체를 위해 잠시 코드를 멈추고 그 객체를 직접 구현하러 가는 컨텍스트 스위칭(Context Swithing) 없이 상위 레벨의 로직(WikiPageService.createPage 메서드)에 계속 집중할 수 있습니다. 결과적으로 더 유지보수하기 쉬운 구조를 자연스럽게 갖추게 됩니다.

### Architecutre
예를 들어 Web 기반 Accounting 시스템의 아키텍처가 존재한다고 가정합니다. 이때 우리는 어느 시스템에 주목해야 할까요? 우리는 Web 기반이 아닌 Accounting 시스템에 주목해야 합니다. 소프트웨어 아키텍처가 존재할때 Accounting Issue와 관련된 부분을 드러내야 하고 Web에 대해서는 언급하지 말아야 합니다. 하지만 대개의 Web 시스템은 반대인 경우가 많습니다. Web Issue에 대해서만 언급하고 Accounting 관련 비즈니스 의도에 대해서는 거의 언급하지 않는 경우가 많습니다.

### Web 시스템에 만연하는 MVC Architecture
다음 그림을 보면 Controllers와 Views가 Models 영역에 있는 Business Object들을 참조하여 Web에 출력하는 것을 볼수 있습니다. Controllers와 Views는 Models와 Web에 결합되어 있다고 볼수 있습니다. 이러한 상태에서 Models의 영역이 수정되면 Controllers와 Views, Web 영역까지 수정의 영향이 미칠 수 있습니다.

![[Pasted image 20250701133255.png]]

위 그림을 통하여 말하고 싶은 것은 위와 같이 Web 영역과 과하게 결합되어 있는 구조는 요구사항 변경으로 도메인 모델의 수정시 Web 영역에 까지 영향을  미칠 수 있다는 점이었습니다.

### Accounting System Architecture
Accounting System Architecture 설계 시 우리가 해야 할것은 Architecture의 변경 없이 Delivery 메커니즘을 변경할 수 있어야 하는 것입니다. 동일한 Accounting System을 기반으로 웹과 콘솔에 Delivery 할수 있어야 합니다. 즉, 웹과 콘솔이 바라보는 아키텍처는 동일해야 한다는 점입니다.

### Use Cases
유스케이스(UseCase)란 사용자와 시스템과의 상호작용 하는 것을 정의한 것입니다. 유스케이스를 사용한 아키텍처에는 Delivery 메커니즘을 사용하지 않고 링크, 버튼, 클릭 등의 용어를 사용하지 않고 표현합니다.
이러한 조건을 기반으로 애플리케이션 개발은 Delivery와 독립적인 UseCase에 의해 주도되어야 합니다.

### Use Case Driven Architecture
UseCase 기반(Driven) 시스템의 아키텍처를 보면 Delivery 수행방식이 아닌 UseCase를 보게 됩니다. 다음 그림을 보면 직원들의 출근 시간을 이용하여 급여를 계산하는 시스템입니다. TimeCardTransaction은 시간 카드를 이용해서 급여를 계산하는 유즈케이스입니다. 독자의 시선에서는 웹이니 상세한 MyBatis와 같은 기술이 아닌 유즈케이스에 대한 추상화된 클래스만을 보며 아키텍처를 파악할 수 있습니다. 이러한 유즈케이스를 기반으로 한 아키텍처가 독자가 보게하는 시스템의 의도입니다.
![[Pasted image 20250705113156.png]]

### Use Cases
**유즈케이스(UseCase)란 사용자가 특정한 목적을 위해서 시스템과 어떻게 상호작용하는지 기술한 것**입니다.
예를 들어 다음은 주문 처리 시스템에서 새로운 주문을 생성하는 것에 대한 유즈케이스 기술입니다.
```
Create Order
Data:
	<customer-id>, <customer-contact-info>, <shipment-destination>, 
	<shipment-mechanism>, <payment-information>
Primary Course:
	1. 수주 담당자(order clerk)가 위의 데이터를 가지고 "create order" 커맨드를 실행시킨다.
	2. 시스템은 모든 데이터를 검증한다.
	3. 시스템은 주문을 생성하고 주문 아이디를 결정한다.
	4. 시스템은 주문 아이디를 수주 담당자에게 전달한다.
```

주문 생성 유즈케이스 기술에 대해서 특별한 형식은 없는 편입니다. 주로 기술 하는 것은 Primary Course로써 사용자와 시스템간에 무엇을 하는 것에 대하여 기술합니다. 위 예시 같은 경우에는 유즈케이스에 사용된 데이터를 기술한 것을 볼수 있습니다. 필요한 데이터를 기술하는 것은 필수는 아니며 형식중에 하나일 뿐입니다. 그리고 Primary Course는 주문 성공에 대한 케이스이고 Secondary Course로써 주문 생성에 실패하는 경우도 작성할 수 있습니다.

UseCase 특징
- screen, button, field 등과 같은 웹(Delivery 메커니즘)과 관련된 요소들을 언급하지 않고 있습니다.
- 시스템으로 들어가는 데이터, 커맨드와 시스템이 응답하는 것만 언급하고 있습니다.
- Delivery 메커니즘과 무관한 아키텍처를 가지려면 Delivery 메커니즘과 무관한 UseCase로 시작해야 합니다.
- UseCase는 입력 데이터를 해석하여 출력 데이터를 생성하는 필수적인 알고리즘입니다.

UseCase 알고리즘
- UseCase 알고리즘은 다른 비즈니스 객체(Customer, Order)를 언급합니다.
- UseCase 알고리즘에서 알고리즘은 UseCase 정의, 비즈니스 규칙을 내포하고 있습니다.
	- 비즈니스 규칙은 Customer나 Order 객체에 속하지 않습니다.
	- Customer 객체는 상품에 대해서는 모릅니다. 반면에 Order 객체는 상품에 대해서는 아는데 Customer에 대해서는 모른다.
	- 객체간 비즈니스 규칙을 유즈케이스 객체에 위치시켜야 합니다.
- 우리가 고민해야 할것은 우리의 시스템을 위치시켜서 UseCase가 어떻게 central organizing principle이 되게 할것인가이다.

### Partitioning
야콥슨이 말하기를 아키텍처는 3개의 기초적인 객체 종류를 갖는다고 하였습니다. 객체 종류는 다음과 같습니다.
- Business Objects : Entity라고 불리는 객체들입니다.
- UI Objects: Boundaries라고 불리는 객체들입니다.
	- DTO, VO와 같은 객체들이 Boundaries 객체에 해당됩니다.
- Use Case Objects: Interactors라고 불리는 객체들입니다.

Partitioning 특징
- Entity들은 애플리케이션(Application)과 독립적인 로직을 갖습니다.
- Entity들은 다른 application에서도 Entity들은 사용되지만, Entity들은 특정 Application에 특화된 메서드를 갖으면 안됩니다.
	- 예를 들어 A 회사의 주문 처리 시스템과 B 회사의 주문 처리 시스템에서 둘다 동일하게 Order라는 도메인의 Entity를 사용하지만 Application 로직이 서로 다를 것입니다.
	- Application에 특화된 메서드들은 UseCaseObject(Interactor)로 옮겨야 합니다.
- Interactors는 Application에 종속적인 비즈니스 로직을 가지고 있습니다.
- 특정 Application에 특화된 메서드들은 Interator 객체에 구현합니다.
- Interator는 application에 특화된 로직을 통해서 목적을 달성합니다.
	- application과 무관한 Entity 로직을 호출합니다.
	- 예를 들어 CreateOrderInterator Interator는 Order Entity에 접근하여 Order의 생성자 및 getId를 호출합니다.
	- Order 객체의 생성자와 getId 메서드는 application 로직과는 무관한 메서드입니다.
	- UseCase의 목적을 달성하기 위해서 이러한 메서드들을 어떻게 호출하는지 아는 것이 Interator의 책임입니다.
- UseCase의 책임 중 하나는 사용자로부터 입력받아서 결과를 다시 사용자에게 반환하는 것입니다. 
	- UseCase의 입출력을 위해서 Boundary Object 객체가 필요합니다.
	- Boundary Object 객체는 인터페이스 타입으로써 Controller와 같은 외부에서 오는 클라이언트의 요청을 받아서 반환합니다.
		- 실제 처리는 Boundary 인터페이스의 구현체가 처리하여 반환합니다.
![[Pasted image 20250705135732.png]]

위 그림을 보면 Controller에서 Boundary 인터페이스로 요청을 보내고 있습니다. 런타임 시점에서는 Boundary 인터페이스를 구현한 UseCase 객체가 실제 처리하여 다른 Boundary 인터페이스 타입으로 응답합니다.
UseCaser가 요청을 처리하는 과정에서 Business Object인 Entity 객체와 저장소 조회를 위한 Repository 인터페이스에 접근합니다.

Boundary Object
- Boundary Object를 사용하여 UseCase를 Delivery 메커니즘으로부터 분리시킬 수 있습니다.
- Boundary Object를 이용하여 UseCase와 Delivery 메커니즘간의 통신수단을 제공할 수 있습니다.
- MVC / Console / Thick Client 등은 Boundary의 반대편에 존재하는 요소들입니다.
- UseCase는 반대편의 Delivery 메커니즘의 존재를 모른채로 존재합니다.

### Partitioning 수행과정
- Delivery 메커니즘 수행
	- 시스템에서 사용자의 요청을 수집합니다. 사용자의 요청은 RequestModel로 표현합니다.
	- 시스템은 Boundary를 통해서 RequestModel을 Interator로 전달합니다.
- Interators 수행
	- Applications에 특화된 비즈니스 로직을 수행합니다.
	- Entity에 접근하여 Application 독립적인 비즈니스 로직을 수행합니다.
	- 처리를 하면 Result Model을 생성하여 반환합니다.
	- Boundary를 통해서 다시 Delivery 메커니즘으로 전달합니다.

### Use Cases and Partitioning
우리는 시스템의 행위를 UseCase로 기술합니다.
- UseCase에서 application에 특화된 행위를 Interator 객체로 캡처합니다.
- application에 무관한 행위를 Entity 객체로 캡처하고 Interator로 제어합니다.
- UI에 종속적인 행위는 Boundary 객체로 캡처하여 Interator와 통신합니다.

### Isolation
소스 코드의 의존성은 Delivery 메커니즘에서 Boundary 인터페이스 쪽으로 향해야 하고 UseCase에서 Delivery 메커니즘쪽으로  향하면 안된다.
이는 UseCase를 Delivery 메커니즘으로부터 독립시키기 위해서입니다.





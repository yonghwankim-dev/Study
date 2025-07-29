
- [[#1. 트랜잭션 코드의 분리|1. 트랜잭션 코드의 분리]]
	- [[#1. 트랜잭션 코드의 분리#1.1 메서드 분리|1.1 메서드 분리]]
	- [[#1. 트랜잭션 코드의 분리#1.2 의존성 주입(Dependency Injection, DI)을 이용한 클래스의 분리|1.2 의존성 주입(Dependency Injection, DI)을 이용한 클래스의 분리]]
- [[#2. 고립된 단위 테스트|2. 고립된 단위 테스트]]
- [[#3. Dynamic Proxy & Factory Bean|3. Dynamic Proxy & Factory Bean]]
	- [[#3. Dynamic Proxy & Factory Bean#3.1 Proxy, Proxy Pattern, Decorator Pattern|3.1 Proxy, Proxy Pattern, Decorator Pattern]]
	- [[#3. Dynamic Proxy & Factory Bean#3.2 Dynamic Proxy|3.2 Dynamic Proxy]]
	- [[#3. Dynamic Proxy & Factory Bean#3.4 Dynamic Proxy를 위한 Factory Bean|3.4 Dynamic Proxy를 위한 Factory Bean]]
	- [[#3. Dynamic Proxy & Factory Bean#ProxyFactoryBean 방식의 장점과 한계|ProxyFactoryBean 방식의 장점과 한계]]
- [[#4. Spring의 ProxyFactoryBean|4. Spring의 ProxyFactoryBean]]
	- [[#4. Spring의 ProxyFactoryBean#4.1 ProxyFactoryBean|4.1 ProxyFactoryBean]]
- [[#5. Spring AOP|5. Spring AOP]]
	- [[#5. Spring AOP#5.1 자동 Proxy 생성|5.1 자동 Proxy 생성]]


## 1. 트랜잭션 코드의 분리
### 1.1 메서드 분리
Spring의 서비스 레이어 메서드에서 트랜잭션 처리가 필요한 경우에 `@Transactional`  애노테이션을 메서드에 정의합니다. 하지만 만약에 해당 애노테이션을 사용하지 않고 트랜잭션 처리를 사용하기 위해서는 프로그래밍 방식의 트랜잭션 관리를 할 수 밖에 없습니다.
예를 들어 사용자들의 등급 레벨을 한단계씩 업그레이드하는 서비스가 존재합니다. 코드로 구현하면 다음과 같습니다.
```java
public void upgradeLevels() {  
    PlatformTransactionManager transactionManager = new DataSourceTransactionManager(datasource);  
    TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());  
    try {  
       List<User> users = userDao.getAll();  
       for (User user : users) {  
          if (canUpgradeLevel(user)) {  
             upgradeUser(user);  
          }  
       }  
       transactionManager.commit(status);  
    } catch (Exception e) {  
       transactionManager.rollback(status);  
       throw e;  
    }  
}
```

transactionManager 객체를  통해서 트랜잭션을 생성하고 업그레이드에 성공하게 되면 커밋을 하는 것을 볼수 있습니다. 만약 에외가 발생하면 트랜잭션을 롤백합니다. 

위 코드의 문제점은 트랜잭션을 처리하는 로직과 비즈니스 로직이 서로 주고받는 정보가 없음에도 하나의 메서드에서 동작한다는 점입니다. 즉, 두개의 로직은 서로 독립적으로 수행할 수 있습니다. 단, 비즈니스 로직은 트랜잭션의 시작과 종료 작업 사이에 수행되어야 한다는 원칙만 지키면 됩니다.

비즈니스 로직을 수행하는 코드를 별도의 메서드로 추출하여 독립시킵니다.
```java
public void upgradeLevels() {  
    PlatformTransactionManager transactionManager = new DataSourceTransactionManager(datasource);  
    TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());  
    try {  
       upgradeLevelsInternal();  
       transactionManager.commit(status);  
    } catch (Exception e) {  
       transactionManager.rollback(status);  
       throw e;  
    }  
}  
  
private void upgradeLevelsInternal() {  
    List<User> users = userDao.getAll();  
    for (User user : users) {  
       if (canUpgradeLevel(user)) {  
          upgradeUser(user);  
       }  
    }  
}
```

비즈니스 로직 코드를 upgradeLevelsInternal 메서드로 추출하여 독립시킴으로써 트랜잭션의 처리와 비즈니스 로직 코드의 경계를 분명히 합니다.

비즈니스 로직 코드를 별도의 메서드로 추출 했음에도 문제점은 존재합니다. 만약에 트랜잭션 처리 코드만이 아닌 추가적인 기능이 필요하다면 코드의 복잡성이 증가합니다. 예를 들어 메서드의 실행 시간을 측정하여 로깅하기 위해서 다음과 같이 기능을 추가할 수 있습니다.
```java
public void upgradeLevels() {
    long startTime = System.currentTimeMillis();  
    PlatformTransactionManager transactionManager = new DataSourceTransactionManager(datasource);  
    TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());  
    try {  
       upgradeLevelsInternal();  
       transactionManager.commit(status);  
    } catch (Exception e) {  
       transactionManager.rollback(status);  
       throw e;  
    }  
    long endTime = System.currentTimeMillis();  
    log.info("Upgrade levels took {} ms", endTime - startTime);  
}
```

`upgradeLevels()` 메서드의 실행 시간을 측정하고 로깅하기 위해서 메서드의 시작 부분과 종료 부분에 코드를 추가하였습니다. 그러나 위와 같이 기능을 추가할때 문제점이 있습니다. 수행 시간 로깅 기능의 재사용이 어렵고 코드가 중복되는 문제가 있습니다. 만약 `upgradleLevels()` 메서드만이 아닌 다른 메서드에서도 수행 시간 로깅 기능을 사용해야 한다면 어떻게 해야 할까요? 아마 다른 메서드에서도 중복적으로 코드를 추가해야 할 것입니다. 또한 로깅 기능은 메서드의 시작 부분과 종료 부분에 코드가 존재하기 때문에 별도의 메서드로 추출하는 것도 어렵습니다.

정리하면 비즈니스 로직과 관계없는 부가 기능(트랜잭션 처리, 수행 시간 로깅)이 추가될수록 복잡성이 증가되고, 코드의 재사용이 어렵고 코드가 중복되는 문제가 발생합니다.

### 1.2 의존성 주입(Dependency Injection, DI)을 이용한 클래스의 분리
비즈니스 로직을 별도의 메서드로 분리 했음에도 부가 기능의 재사용이 불가능하다는 문제점이 있습니다. 이 문제를 해결하기 위해서 부가 기능을 클래스로 분리하여 해결할 수 있습니다. 위 예제의 트랜잭션 처리하는 코드를 별도의 클래스로 분리합니다.
```java
public class UserServiceTx implements UserService {  
  
    private final PlatformTransactionManager transactionManager;  
    private final UserService userService;  
  
    public UserServiceTx(PlatformTransactionManager transactionManager, UserService userService) {  
       this.transactionManager = transactionManager;  
       this.userService = userService;  
    }  
  
    @Override  
    public void upgradeLevels() {  
       TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());  
       try {  
          userService.upgradeLevels();  
       } catch (Exception e) {  
          transactionManager.rollback(status);  
          throw e;  
       } finally {  
          transactionManager.commit(status);  
       }  
    }
}
```
`upgradeLevels()` 메서드를 보면 트랜잭션 처리를 수행하고 중간에 비즈니스 로직을 수행하는 부분은 다른 UserService 객체에게 위임하여 실행하는 것을 볼수 있습니다. 위 코드에서 실행하는 userService 객체는 사용자들의 등급 레벨을 한단계씩 업그레이드하는 UserServiceImpl 구현체 클래스입니다.

여기서 주목할 점은 `upgradeLevels()`  메서드를 호출하는 클라이언트는 UserServiceTx 클래스를 직접적으로 참조하는 것이 아닌 인터페이스 타입인 UserService 타입으로 참조합니다. 이 구조를 그림으로 표현하면 다음과 같습니다.
![[image-15.png]]
UserServiceTx 구현체는 트랜잭션 처리 역할을 담당하여 실질적인 비즈니스 로직은 UserServiceImpl 구현체 클래스에게 위임합니다. 클라이언트는 구현체 클래스를 모르며 UserService에 요청하여 업그레이드를 요청합니다. 그렇게 되면 UserServiceTx가 먼저 요청을 받아서 위임하여 처리하는 방식으로 수행됩니다.

위와 같이 부가 기능을 별도의 구현체 클래스로 분리함으로써 요구사항 변경으로 인하여 부가 기능이 추가되어도 기존 UserServiceTx, UserServiceImpl의 코드를 수정할 필요없이 확장할 수 있습니다. 반대로 UserServiceImpl의 비즈니스 로직이 변경되어도 UserServiceTx의 코드를 수정할 필요가 없습니다.

## 2. 고립된 단위 테스트
의존성 주입을 이용하여 부가 기능을 별도의 클래스로 분리해서 좋은 점은 단위 테스트하기 편하다는 점입니다. 예를 들어 별도의 클래스로 분리하기 전에 다음과 같은 코드는 단위 테스트를 수행하기 어렵습니다.
```java
public void upgradeLevels() {  
    PlatformTransactionManager transactionManager = new DataSourceTransactionManager(datasource);  
    TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());  
    try {  
       upgradeLevelsInternal();  
       transactionManager.commit(status);  
    } catch (Exception e) {  
       transactionManager.rollback(status);  
       throw e;  
    }  
}  

private void upgradeLevelsInternal() {  
    List<User> users = userDao.getAll();  
    for (User user : users) {  
       if (canUpgradeLevel(user)) {  
          upgradeUser(user);  
       }  
    }  
}
```
만약 `upgradeLevels()` 메서드를 대상으로 단위 테스트를 수행하기 위해서는 transactionManager와 userDao에 대해서 목처리를 수행해야 합니다.

하지만 의존성 주입을 이용하여 트랜잭션 처리 기능을 별도의 UserServiceTx 클래스로 분리하였기 때문에 UserServiceImpl 클래스의 `upgradeLevels()` 메서드를 대상으로 단위 테스트를 수행하기 위해서 userDao 객체만 목처리만 수행하면 됩니다.
```java
class UserServiceImpl implements UserService {  
  
    private final UserDao userDao;  
  
    UserServiceImpl(UserDao userDao) {  
       this.userDao = userDao;  
    }  
  
    @Override  
    public void upgradeLevels() {  
       List<User> users = userDao.getAll();  
       for (User user : users) {  
          if (canUpgradeLevel(user)) {  
             upgradeUser(user);  
          }  
       }  
    }
    // ...
}
```

UserDao 객체에 대해서 목처리를 수행하는 방법중 하나는 UserDao 타입을 인터페이스로 선언하고 목처리를 수행하는 구현체 클래스를 구현하는 방법이 있습니다.
```java
public class MockUserDao implements UserDao {  
  
    private final List<User> users;  
    private final List<User> updated;  
  
    public MockUserDao(List<User> users) {  
       this.users = users;  
       this.updated = new ArrayList<>();  
    }  
  
    @Override  
    public List<User> getAll() {  
       return Collections.unmodifiableList(users);  
    }  
  
    @Override  
    public void upgradeLevel(User user) {  
       this.updated.add(user);  
    }  
  
    public List<User> getUpdated() {  
       return Collections.unmodifiableList(updated);  
    }  
  
    @Override  
    public void deleteAll() {  
       throw new UnsupportedOperationException();  
    }  
  
    @Override  
    public int getCount() {  
       throw new UnsupportedOperationException();  
    }  
  
    @Override  
    public void add(User user) {  
       throw new UnsupportedOperationException();  
    }  
  
    @Override  
    public User get(String id) {  
       throw new UnsupportedOperationException();  
    }  
}
```
단위 테스트에서 등급 상승된 사용자의 개수를 검증하기 위해서 별도의 updated 리스트를 관리합니다.

위와 같은 MockUserDao 클래스를 구현한 상태에서 다음과 같은 단위 테스트를 구현할 수 있습니다.
```java
class UserServiceImplTest {  
    private UserService userService;  
    private MockUserDao userDao;  
  
    @BeforeEach  
    void setUp() {  
       List<User> users = List.of(  
          new User(1L, "Alice", Level.BASIC),  
          new User(2L, "Bob", Level.SILVER),  
          new User(3L, "Charlie", Level.GOLD)  
       );  
       userDao = new MockUserDao(users);  
       userService = new UserServiceImpl(userDao);  
    }  
  
    @DisplayName("사용자들의 레벨을 업그레이드한다")  
    @Test  
    void upgradeLevels() {  
       // given  
  
       // when       
       userService.upgradeLevels();  
       // then  
       List<User> actual = userDao.getUpdated();  
       Assertions.assertThat(actual).hasSize(2);  
    }  
}
```

UserDao 객체를 목처리하는 다른 방법은 Mockito 프레임워크를 활용하는 방법이 있습니다. 해당 프레임워크를 사용하면 별도의 MockUserDao 클래스를 구현할 필요가 없습니다. 테스트 코드로 구현하면 다음과 같습니다.
```java
@DisplayName("업그레이드된 사용자 수를 검증한다")  
@Test  
void verifyUpgradeCount() {  
    // given  
    UserDao userDao = Mockito.mock(UserDao.class);  
    Mockito.when(userDao.getAll()).thenReturn(List.of(  
       new User(1L, "Alice", Level.BASIC),  
       new User(2L, "Bob", Level.SILVER),  
       new User(3L, "Charlie", Level.GOLD)  
    ));  
    UserService userService = new UserServiceImpl(userDao);  
    // when  
    userService.upgradeLevels();  
    // then  
    Mockito.verify(userDao, Mockito.times(2)).upgradeLevel(Mockito.any());  
}
```

정리하면 Mock 클래스를 구현하여 목처리를 하는 방법이 있고 Mockito 프레임워크를 이용하여 목 클래스 구현없이 목처리를 할 수 있습니다. 그리고 부가기능을 별도의 클래스로 분리하여 위임하는 방식을 이용하면 의존하는 클래스를 줄여서 단위 테스트하기 편리할 수 있습니다.

## 3. Dynamic Proxy & Factory Bean
### 3.1 Proxy, Proxy Pattern, Decorator Pattern
우리는 클라이언트가 직접적으로 UserServiceImpl 클래스에 요청하는 것이 아닌 UserService라는 인터페이스를 정의하고 해당 인터페이스에 요청하도록하여 부가기능을 수행하는 UserServiceTx가 먼저 실행되도록 하였습니다. 이와 같이 **마치 자신이 클라이언트가 사용하려고 하는 실제 대상인것처럼 위장해서 클라이언트의 요청을 처리하는 것을 프록시(Proxy)**라고 합니다. 다음 그림을 보면 클라이언트는 부가기능 구현체 클래스나 핵심 기능 구현체 클래스를 직접적으로 호출하는 것이 아닌 반드시 핵심 기능이 정의된 인터페이스만을 보고 요청합니다.
![[image-16.png]]

위 그림을 기준으로 부가 기능을 수행하는 구현체 클래스를 프록시라고 합니다. 반대로 **프록시를 통해서 핵심 기능을 위임받아 수행하는 클래스를 타겟(Target)**이라고 합니다.

**Proxy 특징**
첫번째로 Proxy 역할을 수행하는 부가기능 클래스가 타겟 클래스와 같은 인터페이스 상속받고 구현합니다. 두번째로 Proxy 객체가 타겟 객체를 제어할 수 있는 위치에 있습니다. 그래서 Proxy 객체는 자신이 원하는 위치에 타겟 객체의 핵심 기능을 위임 실행 할 수 있습니다.

**Proxy의 사용 목적**
첫번째는 클라이언트가 타겟에 접근하는 방법을 제어하기 위해서입니다. 두번째는 타겟에 부가 기능을 부여하기 위함입니다.

**Decorator Pattern은 무엇인가?**
타겟에 적용하는 부가적인 기능을 런타임 시점에 동적으로 부여하기 위해서 Proxy를 사용하는 디자인 패턴입니다. 런타임 시점에 부가기능을 부여하기 때문에 컴파일 시점에서는 어떤 방법과 어떤 순서로 Proxy와 Target이 연결되어 사용되는지 모릅니다. 

예를 들어 UserService 객체 생성시 다음과 같이 데코레이터를 선택적으로 추가할 수 있습니다.
```java
public UserService createUserService(String option) {
    UserService service = new UserServiceImpl();

    if (option.contains("log")) {
        service = new LoggingUserService(service);
    }
    if (option.contains("tx")) {
        service = new TransactionalUserService(service);
    }

    return service;
}
```
위 코드를 보면 option 문자열에 log, tx가 포함되냐 안되느냐에 따라서 로깅 기능이나 트랜잭션 기능이 추가되거나 안될 수도 있습니다. 이와 같이 컴파일러 입장에서는 위 코드만으로는 로깅 기능이 추가될지, 트랜잭션 기능이 추가될지 모릅니다. 실제로 어떤 순서로 Proxy와 Target이 연결될지는 런타임 시점에서만 알 수 있습니다.

**Decorator Pattern 특징**
첫번째는 핵심 기능에 적용되는 부가 기능이 1개 이상일 수 있습니다. 위 예제에서와 같이 option 문자열에 포함되는 값 유무에 따라서 UserService에 로깅 기능과 트랜잭션 기능이 모두 추가될 수 있습니다.
두번째는 인터페이스를 통해서 위임하는 방식이기 때문에 어느 Proxy에서 타겟으로 연결 될지 컴파일 시점에서는 알 수 없습니다.
세번째는 부가 기능을 추가하여도 클라이언트의 호출과 타겟 클래스에 코드 수정의 영향이 없습니다. 클라이언트는 인터페이스만을 바라보고 있기 때문에 메서드 시그니처가 수정되지 않는한 인터페이스 밑에 부가기능 구현체 클래스가 추가되거나 제거되어도 호출에 영향을 미치지 않습니다. 타겟 클래스에 영향이 없는 이유는 타겟 클래스에는 부가기능 클래스가 참조되지 않고 호출 당하는 입장이기 때문입니다.

**Proxy Pattern은 무엇인가?**
Proxy Pattern은 타겟에 접근하는 것을 제어하거나 기능을 위임하기 위해 사용되는 디자인 패턴입니다. 타겟과 동일한 인터페이스를 구현하고 클라이언트와 타겟 사이에 존재하면서 부가 기능이나 접근 제어를 담당하는 오브젝트를 모두 Proxy라고 부릅니다.

**Proxy Pattern의 특징**
프록시 패턴은 타겟의 기능 자체에는 관여하지 않습니다. 대신에 Proxy를 이용하여 접근 방법을 제어합니다. 예를 들어 Collections.unmodifiableCollection() 메서드를 통해서 반환되는 컬렉션은 추가 및 제거를 지원하지 않습니다. 해당 메서드를 통해서 반환되는 컬렉션은 정확히는 Proxy 객체로써 클라이언트가 해당 Proxy 객체에 추가 및 제거 연산을 요청하면 예외를 발생시킵니다.

**Proxy vs Proxy Pattern**
Proxy라는 용어와 Proxy Pattern 용어는 서로 다른 개념입니다. 두 용어의 비교표는 다음과 같습니다.

| 구분  | Proxy                              | Proxy Pattern                                       |
| --- | ---------------------------------- | --------------------------------------------------- |
| 정의  | 어떤 객체를 대신해서 요청을 처리하는 객체 자체를 의미함    | 객체에게 접근하는 것을 제어하거나 기능을 위임하기 위해 사용하는 디자인 패턴          |
| 역할  | 실제 객체 대신에 클라이언트와 통신해서 요청을 전달하고 제어함 | Proxy 객체를 사용해서 접근 제어, 부가 기능 추가, 지연로딩 등을 다양한 목적을 달성함 |
| 형태  | 클래스/인터페이스 구현체                      | 특정한 설계 원칙과 목적을 가진 구조                                |

### 3.2 Dynamic Proxy
자바의 java.lang.reflect 패키지에는 Proxy를 쉽게 생성하기 위해 지원되는 클래스들이 존재합니다. 

Proxy 역할을 수행하는 클래스들의 문제점은 다음과 같습니다.
첫번째는 Target의 인터페이스를 구현하고 Target으로 위임하는 코드를 모든 메서드에 구현해야 합니다. 이는 매우 번거롭습니다.
두번째는 부가 기능이 필요 없는 메서드 또한 구현해서 Target으로 위임하는 코드를 일일히 구현해야 합니다.
마지막 세번째는 Proxy 클래스가 Target의 인터페이스를 상속받아 구현시 각 메서드에 부가 기능 코드를 구현하는데, 이 부가 기능 코드가 각각의 메서드마다 중복되는 문제점이 있습니다.

세번째 문제점에 대한 예시로써 UserServiceTx Proxy 클래스는 UserService(Target) 인터페이스를 상속받아 구현합니다. 이때 부가 기능 코드인 트랜잭션 처리하는 코드가 upgradeLevels() 메서드와 add() 메서드에 중복적으로 발생하는 것을 볼수 있습니다.
```java
public class UserServiceTx implements UserService {  
  
    private final PlatformTransactionManager transactionManager;  
    private final UserService userService;  
  
    public UserServiceTx(PlatformTransactionManager transactionManager, UserService userService) {  
       this.transactionManager = transactionManager;  
       this.userService = userService;  
    }  
  
    @Override  
    public void upgradeLevels() {  
       TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());  
       try {  
          userService.upgradeLevels();  
       } catch (Exception e) {  
          transactionManager.rollback(status);  
          throw e;  
       } finally {  
          transactionManager.commit(status);  
       }  
    }  
    // ...
      
    @Override  
    public void add(User user) {  
       TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());  
       try {  
          userService.add(user);  
       } catch (Exception e) {  
          transactionManager.rollback(status);  
          throw e;  
       } finally {  
          transactionManager.commit(status);  
       }  
    }  
}
```


**Dynamic Proxy 클래스 만들기**
Dynamic Proxy를 이용한 Proxy 클래스를 만들어봅니다. 우선 인터페이스와 해당 인터페이스를 상속하여 구현하는 Target 클래스를 구현합니다. 다음 코드에서는 인터페이스가 Hello, Target이 HelloTarget 클래스가 됩니다.
```java
public interface Hello {  
    String sayHello(String name);  
  
    String sayHi(String name);  
  
    String sayThankYou(String name);  
}

public class HelloTarget implements Hello {  
    @Override  
    public String sayHello(String name) {  
       return "Hello " + name;  
    }  
  
    @Override  
    public String sayHi(String name) {  
       return "Hi " + name;  
    }  
  
    @Override  
    public String sayThankYou(String name) {  
       return "Thank You " + name;  
    }  
}
```

Hello 인터페이스를 구현한 HelloUppercase Proxy 클래스를 구현합니다. HelloUppercase Proxy 클래스는 문자열을 모두 대문자로 변경해줍니다.
```java
public class HelloUppercase implements Hello {  
    private final Hello delegate;  
  
    public HelloUppercase(Hello delegate) {  
       this.delegate = delegate;  
    }  
  
    @Override  
    public String sayHello(String name) {  
       return delegate.sayHello(name).toUpperCase();  
    }  
  
    @Override  
    public String sayHi(String name) {  
       return delegate.sayHi(name).toUpperCase();  
    }  
  
    @Override  
    public String sayThankYou(String name) {  
       return delegate.sayThankYou(name).toUpperCase();  
    }  
}
```

위와 같이 구현한 HelloUppercase Proxy 클래스에는 다음과 같은 문제점을 가지고 있습니다.
첫번째는 구현하는 모든 메서드에 Target에 위임하는 코드를 구현해야 합니다. 위 예제 같은 경우에는 delegate 객체를 이용하여 3번의 위임하는 메서드를 호출하는 것을 볼수 있습니다. 만약 Hello 인터페이스에 정의된 메서드가 100개라면 전부 delegate 객체의 메소드를 위임 호출하는 코드를 구현해야 할 것입니다.
두번째는 반환된 문자열을 대문자로 변환하는 toUpperCase() 메서드를 모든 메서드에 구현하고 있습니다. 이는 부가 기능이 모든 메서드에 중복적으로 나타나고 있습니다.

위 예제를 통하여 알수 있는 사실은 Proxy 클래스 내에서 **위임 객체와 부가 기능 코드가 중복된다는 문제점**을 알수 있습니다.

**Dynamic Proxy 생성 및 실행 과정**
HelloUppercase Proxy 클래스의 코드 중복 문제를 해결하기 전에 Dynamic Proxy의 실행과정을 봅니다. Dynamic Proxy를 이용하면 HelloUppercase와 같은 클래스를 직접 생성하지 않고도 객체를 생성할 수 있습니다.

Dynamic Proxy의 생성 및 실행 과정은 다음과 같습니다.
![[image-17.png]]
1. 클라이언트는 Proxy Factory에게 Dynamic Proxy 객체 생성을 요청합니다. Proxy Factory는 인터페이스 정보 및 InvocationHandler 정보를 사용하여 Dynamic Proxy 객체를 반환합니다.
2. 클라이언트는 반환받은 Dynamic Proxy 객체를 대상으로 메서드를 요청합니다. 위 예제를 대상으로 하면 Hello 인터페이스 타입의 객체를 대상으로 sayHello()와 같은 메서드를 요청합니다.
3. Hello 인터페이스 타입을 가진 Dynamic Proxy 객체는 요청을 받은 다음에 InvocationHandler 객체에게 invoke() 메서드를 요청하며 처리를 요청합니다.
4. 요청을 받은 Invocationhandler 객체는 부가 기능을 수행하고 실제 비즈니스 로직 수행은 Target 객체에게 위임 요청하여 처리합니다. 처리한 결과를 다시 Dynamic Proxy 객체에게 반환합니다.
5. Dynamic Proxy는 반환받은 결과를 최종적으로 클라이언트에게 반환하고 실행을 종료합니다.

**Dynamic Proxy 특징**
첫번째는 Dynamic Proxy는 런타임 시점에 Proxy Factory에 의해서 동적 생성되는 객체입니다.
두번째는 동적 생성되는 Dynamic Proxy는 Target의 인터페이스와 같은 인터페이스 타입을 가집니다. 예를 들어 Target의 인터페이스가 Hello라면 생성되는 Dynamic Proxy 또한 Hello 인터페이스 타입을 가집니다.
세번째는 클라이언트는 Target, Dynamic Proxy 객체를 구분하지 않고 인터페이스를 통하여 동일하게 호출할 수 있습니다.
네번째는 Proxy Factory를 이용하여 Dynamic Proxy 객체를 생성한 것이기 때문에 별도의 Proxy 클래스를 구현하지 않습니다. 예를 들어 Proxy Factory를 이용하여 Dynamic Proxy 객체를 생성하면 위 예제의 HelloUppercase 클래스를 생성 및 인터페이스 구현할 필요가 없습니다.

**InvocationHandler 인터페이스 구현**
Proxy Factory가 Dynamic Proxy 객체를 동적 생성하기 위해서는 Target의 인터페이스 타입 정보와 부가 기능을 수행하는 InvocationHandler 인터페이스를 구현한 객체가 필요합니다. 
기존에는 Proxy 클래스가 부가 기능 수행과 Target 객체에 대한 위임 요청을 함께 처리했지만, Dynamic Proxy를 사용하면 메서드 호출시 해당 메서드 정보와 매개변수(args)가 InvocationHandler로 전달되며, 실제 Target 객체에 대한 호출과 부가 기능은 모두 InvocationHandler 내부에서 이루어지도록 분리할 수 있습니다.

InvocationHandler 인터페이스 스펙은 다음과 같습니다. 클라이언트로부터 요청을 받은 Dyanmic Proxy 객체는 호출한 메서드 정보와 입력받은 매개변수(args)들을 InvocationHandler 객체의 invoke() 메서드 호출시 전달하여 Target 객체의 위임 요청 처리 및 부가 기능을 요청합니다. invoke() 메서드의 proxy 매개변수는 Dynamic Proxy 객체 자신을 의미합니다.
```java
public interface InvocationHandler {  
	public Object invoke(Object proxy, Method method, Object[] args)  
        throws Throwable;
}   
```

Dynamic Proxy 객체는 클라이언트로부터 요청을 받게 되면 요청 정보들을 Reflect 정보(method, args)로 변환 후 InvocationHandler 객체의 invoke() 메서드로 전달합니다.
Dynamic Proxy 객체가 클라이언트로부터 받은 요청을 Reflect 정보로 변환해서 InvocationHandler 객체에게 전달함으로써 Dynamic Proxy 객체의 모든 메서드가 InvocationHandler 객체의 하나의 invoke() 메서드로 전달되는 것을 알수 있습니다. 이로 인해서 **Target 객체의 위임 요청 및 부가 기능 코드가 중복되는 문제를 해결**할 수 있습니다.

예를 들어 다음 그림을 보면 Hello 인터페이스 타입을 가진 Dynamic Proxy 객체가 있습니다. 구현된 Hello 인터페이스 메서드 전부가 InvocationHandler 객체의 invoke() 메서드를 호출하는 구조입니다. 그리고 요청을 받은 InvocationHandler 객체는 객체 생성시 주입된 Target에게 위임 요청을 하는 것을 볼수 있습니다. 이로 인해서 Target 객체에게 위임 요청을 딱 한번만 할수 있게 되고, 부가 기능 코드 또한 한번만 하여 코드 중복을 막을 수 있습니다.
![[image-18.png]]

문자열을 대문자로 변환하는 InvocationHandler 클래스는 다음과 같이 구현합니다. Dynamic Proxy는 Target을 전달해주지 못하기 때문에 객체 생성시 생성자를 통해서 Target 객체를 주입받습니다.
```java
public class UppercaseHandler implements InvocationHandler {  
    private final Object target;  
  
    public UppercaseHandler(Object target) {  
       this.target = target;  
    }  
  
    @Override  
    public java.lang.Object invoke(java.lang.Object proxy, Method method, java.lang.Object[] args) throws Throwable {  
       java.lang.Object result = method.invoke(target, args);  
       if (result instanceof String text) {  
          return text.toUpperCase();  
       }  
       return result;  
    }  
}
```
method 객체를 이용하여 Target 객체에 위임 요청을 수행하고 실행 결과를 반환받습니다. 그리고 문자열 타입이면 대문자로 변경하여 반환합니다.

클라이언트 코드 구현은 다음과 같습니다.
```java
var classes = new Class[] {Hello.class};  
InvocationHandler uppercaseHandler = new UppercaseHandler(new HelloTarget());  
Hello proxiedHello = (Hello)Proxy.newProxyInstance(getClass().getClassLoader(),  
    classes, uppercaseHandler);
```
Dynamic Proxy 객체를 생성하기 위해서는 java.lang.reflect 패키지의 Proxy 클래스의 newProxyInstance 정적 팩토리 메서드를 호출해야 합니다. 첫번째 매개변수에는 클래스 로더(ClassLoader)를 전달합니다. 클래스 로더는 생성된 Dynamic Proxy 객체를 JVM에 로딩하는 역할입니다.
두번째 매개변수에는 Dynamic Proxy가 구현해야할 인터페이스 정보들입니다. 배열 형태로 들어가기 때문에 1개 이상의 인터페이스가 들어갑니다. 
마지막 매개변수에는 Target 객체에게 위임 요청 및 부가 기능을 수행하는 InvocationHandler 인터페이스의 구현체 객체를 전달합니다. 위 예제에서는 UppercaseHandler 객체가 해당됩니다.

위와 같이 생성된 proxiedHello Dynamic Proxy 객체는 클라이언트 입장에서는 Proxy인지 실제 Target 객체인지 구분하지 않고 메서드를 요청할 수 있습니다.

### 3.4 Dynamic Proxy를 위한 Factory Bean
Dynamic Proxy 객체는 일반적인 방법으로 Spring Bean 등록할 수 없습니다. Spring은 애플리케이션 컨텍스트 초기화 시 @Component, @Bean 등을 통해서 미리 정해진 클래스들을 스캔하거나 인스턴스화합니다. 하지만 Dynamic Proxy는 Proxy.newProxyInstance() 메서드 호출을 통해 런타임에 생성되는 객체이며, 실제 클래스가 아닌 Proxy 클래스입니다. 즉, 이런 Proxy 객체는 클래스 파일(.class)로 존재하지 않고, **JVM 메모리 안에서 동적으로 만들어지는 익명 클래스**입니다. 그래서 정적인 컴포넌트 스캔이나 설정 기반으로는 알수 없습니다.
만약 Dynamic Proxy 객체를 Spring Bean으로 등록하고 싶다면 다음 처럼 수동적으로 등록할 수는 있습니다.
```java
@Bean
public MyInterface myProxyBean() {
	MyInterface target = new MyTarget(); // 실제 객체
	InvocationHandler handler = new MyHandler(target);
	return (MyInterface) Proxy.newProxyInstance(
			MyInterface.class.getClassLoader(),
			new Class[]{MyInterface.class},
			handler
	);
}
```
이처럼 동적 생성된 Proxy 객체는 코드 안에서 생성한 뒤에 @Bean 메서드를 통해 수동 등록해야 합니다. 

**Factory Bean**
Spring은 클래스 정보와 생성자를 통해서 객체를 만드는 방법 외에도 **FactoryBean이라는 것을 이용하여 Spring Bean을 생성하는 방법도 존재합니다.** FactoryBean을 이용하면 Spring을 대신해서 객체를 생성후 Spring Bean으로 등록해줍니다.

FactoryBean 인터페이스 스펙은 다음과 같습니다.
```java
public interface FactoryBean<T> {
	@Nullable
	T getObject() throws Exception;
	@Nullable
	Class<?> getObjectType();
	default boolean isSingleton() {
		return true;
	}
}
```
getObject() : Dynamic Proxy 객체를 반환합니다. 해당 Proxy 객체는 제네릭 타입 T 타입을 가지고 반환합니다. 예를 들어 `FactoryBean<Hello>` 객체를 생성한 다음에 getObject() 메서드를 호출하면 Hello 타입을 가진 Dynamic Proxy 객체를 반환합니다.
getObjectType() : Dynamic Proxy 객체의 타입을 반환합니다. 예를 들어 DynamicProxy 객체의 타입이 Hello라면 `Class<Hello>`를 반환합니다.
isSingleton() : 싱글톤 객체인지 여부를 반환합니다.

### 3.5 ProxyFactoryBean 방식
**ProxyFactoryBean을 통한 Dynamic Proxy의 Spring Bean 자동 등록**
Spring Context에 여러개의 ProxyFactoryBean 객체를 등록할 수 있습니다. ProxyFactoryBean 클래스는 FactoryBean 인터페이스를 구현한 구현체 클래스입니다. 해당 구현체 클래스를 기반으로 생성한 객체를 @Bean 애노테이션과 같은 방법으로 **ProxyFactoryBean 객체를 Spring Bean으로 등록하면 ProxyFactoryBean 객체의 의해서 생성되는 DynamicProxy 객체는 Spring Context에 Spring Bean으로써 자동 등록**됩니다.

예를 들어 Animal 인터페이스로 Cat, Dog 클래스가 존재하고 해당 구현체 클래스들에 대문자 부가 기능을 부여하고 싶습니다. Dynamic Proxy 객체를 Spring Bean으로 등록하기 위해서 ProxyFactoryBean 객체를 생성하여 다음과 같이 Spring Bean으로 등록합니다.
```java
@Configuration  
public class AnimalConfig {  
    @Bean  
    public ProxyFactoryBean cat() {  
       ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();  
       proxyFactoryBean.setTarget(new Cat());  
       proxyFactoryBean.addAdvice(new UpperCaseAdvice());  
       return proxyFactoryBean;  
    }  
  
    @Bean  
    public ProxyFactoryBean dog() {  
       ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();  
       proxyFactoryBean.setTarget(new Dog());  
       proxyFactoryBean.addAdvice(new UpperCaseAdvice());  
       return proxyFactoryBean;  
    }  
}
```

AnimalConfig 클래스에서 cat(), dog() 메서드에서 생성한 UpperCaseAdvice 객체는 다음과 같이 클래스를 구현합니다.
```java
public class UpperCaseAdvice implements MethodInterceptor {  
    @Override  
    public Object invoke(MethodInvocation invocation) throws Throwable {  
       Object result = invocation.proceed();  
       if (result instanceof String text) {  
          return text.toUpperCase();  
       }  
       return result;  
    }  
}
```
MethodInterceptor 인터페이스는 Advice 인터페이스를 상속받은 서브 인터페이스입니다. 여기서 Advice의 역할은 InvocationHandler 역할과 동일합니다. ProxyFactoryBean 객체 생성시 Target 객체에 부가 기능을 부여하기 위해서는 MethodInterceptor 인터페이스를 구현한 객체가 필요합니다.

클라이언 코드를 보면 Spring 컨테이너에서 ProxyFactoryBean의 빈 이름인 "cat", "dog"로 조회하면 ProxyFactoryBean 객체 자체가 아닌 팩토리에 의해서 생성되는 Proxy 객체가 반환됩니다.
```java
@Test  
void saySound_withSpringBoot() {  
    // given  
    SpringApplication application = new SpringApplication(AnimalConfig.class);  
    ConfigurableApplicationContext context = application.run();  
    Animal cat = context.getBean("cat", Animal.class);  
    Animal dog = context.getBean("dog", Animal.class);  
    // when  
    String catSound = cat.saySound();  
    String dogSound = dog.saySound();  
    // then  
    Assertions.assertThat(catSound).isEqualTo("MEOW");  
    Assertions.assertThat(dogSound).isEqualTo("BOW");  
}
```

만약 다음과 같이 Animal.class 타입이 아닌 ProxyFactoryBean.class 타입으로 Spring Bean을 조회하면 런타임 시점에 다음과 같은 에러가 발생합니다.
```java
ProxyFactoryBean cat = context.getBean("cat", ProxyFactoryBean.class);
System.out.println(cat.getObject());
```
![[image-19.png]]
에러 내용을 보면 ProxyFactoryBean 클래스 타입으로 예상되었으나 실제 타입은 Proxy 타입이어서예외가 발생한 것을 볼수 있습니다.

**ProxyFactoryBean 방식의 장점**
첫번째는 Dynamic Proxy 객체를 Spring Bean으로 등록할 수 있습니다.

Proxy 객체를 생성하기 위해서 타겟 인터페이스를 일일히 구현할 필요가 없습니다. FactoryBean은 타입 정보를 전달받으면 내부적으로 타입에 대한 Proxy 객체를 생성하여 반환합니다. 
다른 장점으로는 하나의 UppercaseHandler와 같은 **핸들러**를 구현하여 인터페이스에 정의된 많은 메서드에 부가기능을 부여할 수 있습니다. 또한 위임 메서드 구현으로 인한 코드 중복도 감소합니다.

**ProxyFactoryBean의 한계**
하나의 클래스에 존재하는 여러개의 메서드에 부가기능을 한번에 제공하는건 어렵지 않게 가능해졌습니다. 하지만 **한번에 여러개의 클래스에 공통적인 부가 기능을 제공하는 일은 어렵습니다.** 만약에 위 예제를 예를 들어 Animal 인터페이스만이 아닌 다른 인터페이스 타입에 대해서 동일하게 부가 기능을 제공하기 위해서는 인터페이스 개수 만큼의 ProxyFactoryBean이 필요할 것입니다. 이는 ProxyFactoryBean 등록의 중복이 발생합니다.

즉, ProxyFactoryBean 방식의 한계를 정리하면 하나의 클래스 또는 인터페이스 타입에 대하여 부가 기능을 부여하는 것은 가능하지만 여러개의 클래스에 존재하는 각각의 메서드들에 대하여 공통적인 부가기능을 부여하는 것은 현재까지의 방법으로는 어렵습니다. 만약 부가기능을 부여해야 한다면 각각의 타입에 대하여 n개의 ProxyFactoryBean을 Spring Bean으로 등록해야 할 것입니다.

## 4. Spring의 ProxyFactoryBean
### 4.1 ProxyFactoryBean
Spring은 트랜잭션 기술과 메일 발송 기술에 적용했던 서비스 추상화를 프록시 기술에도 동일하게 적용하고 있습니다.
자바에는 JDK에서 제공하는 DynamicProxy 외에도 편리하게 프록시를 만들수 있도록 지원하는 다양한 기술이 존재합니다.
Spring은 일관된 방법으로 Proxy를 만들 수 있게 도와주는 추상 레이어를 제공합니다.

Spring은 Proxy 객체를 생성해주는 기술을 추상화한 FactoryBean을 제공해줍니다. ProxyFactoryBean은 Proxy를 생성해서 Spring Bean 객체로 등록하는 역할을 수행합니다.
ProxyFactoryBean은 순수하게 Proxy를 생성하는 작업만을 담당하고, Proxy를 통해 제공해줄 부가 기능은 별도의 Spring Bean으로 둘수 있습니다.
ProxyFactoryBean이 생성하는 Proxy 객체에 적용하는 부가기능은 **MethodInterceptor** 인터페이스를 구현하여 적용할 수 있습니다.

MethodInterceptor와 InvocationHandler는 차이점이 존재합니다. InvocationHandler의 invoke() 메서드는 Target 객체에 대한 정보를 제공하지 않습니다. 따라서 Target은 InvocationHandler를 구현한 클래스가 직접 의존해야 합니다.
반면에 MethodInterceptor의 invoke() 메서드는 ProxyFactoryBean으로부터 Target 객체에 대한 정보도 함께 제공받습니다. 즉, MethodInterceptor는 Target 객체 상관없이 독립적으로 만들어 질수 있습니다.
```java
interface Hello {  
    String sayHello(String name);  
  
    String sayHi(String name);  
  
    String sayThankYou(String name);  
}
class HelloTarget implements Hello {  
    @Override  
    public String sayHello(String name) {  
       return "Hello " + name;  
    }  
  
    @Override  
    public String sayHi(String name) {  
       return "Hi " + name;  
    }  
  
    @Override  
    public String sayThankYou(String name) {  
       return "Thank You " + name;  
    }  
}
class UpperCaseAdvice implements MethodInterceptor {  
    @Override  
    public Object invoke(MethodInvocation invocation) throws Throwable {  
       Object result = invocation.proceed();  
       if (result instanceof String text) {  
          return text.toUpperCase();  
       }  
       return result;  
    }  
}
class HelloTest {  
  
    @Test  
    void should_returnUppercaseHello_whenAddUpperCaseAdviceToProxyFactoryBean() {  
       // given  
       ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();  
       proxyFactoryBean.setTarget(new HelloTarget());  
       proxyFactoryBean.addAdvice(new UpperCaseAdvice());  
       // when  
       Hello proxiedHello = (Hello)proxyFactoryBean.getObject();  
       String actual = Objects.requireNonNull(proxiedHello).sayHello("Dave");  
       // then  
       Assertions.assertThat(actual).hasToString("HELLO DAVE");  
    }  
}
```

UpperCaseAdvice 클래스를 보면 MethodInterceptor 인터페이스를 구현하는 것을 볼수 있습니다. 이러한 MethodInterceptor를 구현한 클래스를 **Advice**라고 합니다.
주목할 점은 **MethodInterceptor를 구현한 UpperCaseAdvice 클래스에는 Target(여기서는 HelloTarget 구현체 또는 Hello 인터페이스)를 의존하지 않습니다.** 대신에 MethodInvocation 객체에 Target 객체 실행을 위임하는 것을 볼수 있습니다.
**MethodInvocation은 일종의 콜백 오브젝트**입니다. proceed() 메서드를 실행하면 Target 객체의 메소드를 내부적으로 실행합니다. 

ProxyFactoryBean은 작은 단위의 템플릿/콜백 구조를 응용해서 적용했기 때문에 템플릿 역할을 하는 MethodInvocation을 싱글톤으로 두고 공유할 수 있습니다.
addAdvice() 메서드를 이용하여 ProxyFactoryBean에는 여러개의 MethodInterceptor를 추가할 수 있습니다. 즉, ProxyFactoryBean 하나만으로 여러개의 부가 기능을 제공하는 Proxy를 생성할 수 있다는 의미입니다.

MethodInterceptor는 **Advice 인터페이스**를 상속하고 있는 서브 인터페이스입니다. **Target 객체에 적용하는 부가기능을 담은 객체를 Spring에서는 Advice라고 부릅니다.**
ProxyFactoryBean은 인터페이스 타입을 제공받지도 않습니다. 다음 코드를 보면 Proxy.newProxyInstance 정적 팩토리 메서드를 이용하여 DynamicProxy 생성시 Hello 인터페이스 타입을 제공받는 것을 볼수 있습니다. 그러나 **ProxyFactoryBean을 이용하여 Proxy 생성시에는 별도의 인터페이스 타입을 받지 않습니다.**
```java
var classes = new Class[] {Hello.class};  
InvocationHandler uppercaseHandler = new UppercaseHandler(new HelloTarget());  
Hello proxiedHello = (Hello)Proxy.newProxyInstance(getClass().getClassLoader(),  
    classes, uppercaseHandler);
```

ProxyFactoryBean에 있는 인터페이스 자동 검출 기능을 사용해서 Target 객체가 구현한 인터페이스 정보를 알아냅니다. 알아낸 인터페이스 정보를 이용하여 해당 인터페이스를 구현한 Proxy를 생성합니다.
Target 객체가 구현하는 인터페이스 중에서 일부 인터페이스의 메서드들에만 Proxy를 적용하기를 원한다면 인터페이스 정보를 명시적으로 제공해주어도 됩니다.
ProxyFactoryBean은 기본적으로 JDK가 제공하는 Dynamic Proxy를 만들어줍니다. 경우에 따라서는 CGLib 오픈소스 바이트코드 생성 프레임워크를 이용해 Proxy를 만들기도 합니다.
**재사용 가능한 부분을 만들어두고 변경되는 부분(콜백 객체와 메서드 호출정보)만 외부에서 주입해서 이를 작업 흐름(부가 기능 부여)중에 사용하도록 하는 전형적인 템플릿/콜백 구조입니다.**

**PointCut이란 부가기능 적용 대상 메서드의 선정 방법을 의미합니다.** 기존의 방식은 다음과 같았습니다.
![[image-21.png]]

문제점은 다음과 같습니다.
**Target이 다르고 메소드 선정 방식이 다르면 InvocationHandler 객체를 여러 Proxy가 공유할 수 없습니다.**
Target이 다르면 invoke 내부 로직이 달라져야 합니다.
InvocationHandler는 내부에 다음과 같은 로직이 들어갑니다.
```java
public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    // 타겟 객체의 실제 메서드 호출
    return method.invoke(target, args);
}
```

여기서 target 객체는 보통 비즈니스 로직을 가진 객체입니다. 그런데 Proxy1과 Proxy2가 각각 다른 target 객체(T1, T2)를 가지는데, 같은 InvocationHandler를 사용하면 어떤 Target 객체에 method를 호출해야 할지 혼란이 생깁니다.
즉, InvocationHandler는 자신이 가로채는 호출이 어떤 Target 객체에 적용되는지 알고 있어야 하며, 그게 다르면 로직이 섞이게 됩니다.

예를 들어 사용자가 원하는 것은 HelloTarget2 객체에 대해서도 동일하게 대문자를 원하지만 실제 적용되는 것은 helloTarget1 객체에 대해서만 적용됩니다.
```java
@Test  
void sayHello2() {  
    // given  
    var classes = new Class[] {Hello.class};  
    HelloTarget helloTarget1 = new HelloTarget();  
    HelloTarget2 helloTarget2 = new HelloTarget2();  
    InvocationHandler uppercaseHandler = new UppercaseHandler(helloTarget1);  
    Hello proxy1 = (Hello)Proxy.newProxyInstance(getClass().getClassLoader(),  
       classes, uppercaseHandler);  
    Hello proxy2 = (Hello)Proxy.newProxyInstance(getClass().getClassLoader(),  
       classes, uppercaseHandler);  
    // when  
    String actual = proxy1.sayHello("Dave");  
    String actual2 = proxy2.sayHello("Dave");  
    // then  
    Assertions.assertThat(actual).hasToString("HELLO DAVE");  
    Assertions.assertThat(actual2).hasToString("HELLO2 DAVE"); // 실제 결과: "HELLO DAVE"
}
```

proxy2.sayHello() 메서드를 호출하여도 실제 Target은 helloTarget2가 아닌 helloTarget1을 가리킵니다. 왜냐하면 uppercaseHandler InvocationHandler 객체 생성시 target 주입을 helloTarget1으로 주입했기 때문입니다.

위 예제를 통해서 알수 있는 사실은 InvocationHandler 객체를 여러 Proxy 객체가 공유할 수 없다는 점입니다.

Spring의 ProxyFactoryBean 방식은 두가지 확장 기능인 **부가기능과 메소드 선정 알고리즘을 활용하는 유연한 구조를 제공**합니다. 2가지 기능은 다음과 같습니다.
첫번째는 Advice입니다. Advice는 부가 기능을 제공하는 역할입니다. 두번째는 PointCut입니다. PointCut은 메소드 선정 알고리즘 역할입니다. PointCut 인터페이스를 구현해서 생성합니다.
![[image-22.png]]

Advice와 PointCut을 활용한 테스트 코드는 다음과 같습니다.
```java
@Test  
void should_returnUppercaseHello_whenAddUpperCaseAdviceToProxyFactoryBeanWithPointcut() {  
    // given  
    ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();  
    proxyFactoryBean.setTarget(new HelloTarget());  
    NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();  
    pointcut.setMappedName("sayH*");  
    DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new UpperCaseAdvice());  
  
    proxyFactoryBean.addAdvisor(advisor);  
    // when  
    Hello proxyHello = (Hello)proxyFactoryBean.getObject();  
    String actual1 = Objects.requireNonNull(proxyHello).sayHello("Dave");  
    String actual2 = proxyHello.sayHi("Dave");  
    String actual3 = proxyHello.sayThankYou("Dave");  
    // then  
    Assertions.assertThat(actual1).hasToString("HELLO DAVE");  
    Assertions.assertThat(actual2).hasToString("HI DAVE");  
    Assertions.assertThat(actual3).hasToString("Thank You Dave");  
}
```

위 코드를 보면 DefaultPointCutAdvisor 객체를 생성하기 위해서 PointCut 객체와 Advice 객체(UpperCaseAdvice 객체)를 주입하는 것을 볼수 있습니다. 그리고 생성한 Advisor를 다시 ProxyFactoryBean에 추가하고 있습니다.

PointCut 객체의 설정을 보면 setMappedName 메서드를 호출하면서 sayH로 시작하는 메서드에 대해서만 부가기능인 Advice를 실행하라고 설정하는 것을 볼수 있습니다.

그래서 테스트 결과를 보면 sayHello, sayHi 메서드에 대해서는 대문자로 변환하는 부가 기능이 적용된 반면에 sayThankYou에 대해서 sayH로 시작하지 않기 때문에 대문자 변환하지 않고 그대로 반환된 것을 볼수 있습니다.

위 예제를 통해서 알수 있는 사실은 **Advice와 PointCut을 묶은 인터페이스 이름을 Advisor라고 부릅니다.** 정리하면 PointCut은 메서드 선정 알고리즘 역할을 수행하고 Advice는 부가 기능을 수행하는 역할입니다.

#### **Advice와 PointCut의 재사용**
ProxyFactoryBean은 Spring의 DI(Dependency Injection)과 템플릿/콜백 패턴, 서비스 추상화 등의 기법이 모두 적용된 것입니다. 그래서 독립적이고 여러 Proxy가 공유할 수 있는 Advice와 PointCut으로 확장 기능을 분리할 수 있습니다.

![[image-23.png]]

위 그림을 보면 AdvisorA는 PointCutA + TransactionAdvice를 조합한 것이고 AdvisorB는 PointCutB + TransactionAdvice를 조합한 것입니다.
이 두개의 Avisor를 사용하는 여러개의 ProxyFactoryBean이 존재합니다. 이 여러개의 ProxyFactoryBean은 Spring Bean으로 등록됩니다.
각가의 ProxyFactoryBean은 하나의 Target 객체를 가리키는데, 이 객체들은 대개 구현 클래스의 Spring Bean이 됩니다. 왜냐하면 구현 클래스의 빈은 싱글톤이기 때문입니다.

## 5. Spring AOP
### 5.1 자동 Proxy 생성
부가 기능의 적용이 필요한 Target 객체마다 거의 비슷한 내용의 ProxyFactoryBean 빈 설정 정보를 추가해주어야 합니다.

#### **중복 문제의 접근 방법**
JDBC API를 사용하는 DAO 코드는 템플릿/콜백 패턴으로 해결합니다.

Proxy 클래스 코드는 Dynamic Proxy라는 런타임 코드 자동생성 기법을 이용합니다.
JDK의 Dynamic Proxy는 특정 인터페이스를 구현한 객체에 대해서 Proxy 역할을 해주는 클래스를 런타임 시점에 내부적으로 만들어줍니다. 런타임 시점에 만들어서 사용되기 때문에 클래스 소스가 따로 남아있지 않을 뿐이지 Target 인터페이스의 모든 메서드를 구현하는 클래스가 분명히 만들어집니다.
변하지 않는 Target으로의 위임과 부가 기능 적용 여부 판단 부분은 코드 생성 기법을 이용하는 Dynamic Proxy 기술에 맡기고, 변하는 부가 기능 코드는 별도로 생성하여 Dynamic Proxy 생성 팩토리에 DI로 제공하는 방법을 사용했습니다.

#### 반복적인 프록시의 메소드 구현은 코드 자동생성 기법을 이용해서 해결했다면 반복적인 ProxyFactoryBean 설정 문제는 설정 자동 등록 기법으로 해결할 수 있을까?
BeanPostProcessor는 인터페이스를 구현해서 만드는 빈 후처리기입니다. 빈 후처리기는 이름 그대로 Spring Bean 객체로 만들어지고 난 후에, Bean 객체를 다시 가공할 수 있게 해줍니다.

```java
public interface BeanPostProcessor {
	@Nullable
	default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Nullable
	default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}
}
```

**DefaultAdvisorAutoProxyCreator**
Spring이 제공하는 빈 후처리기중 하나입니다. Advisor를 이용한 자동 프록시 생성기입니다.

Spring은 빈 후처리기가 Spring Bean으로 등록되어 있으면 Spring Bean 객체가 생성될 때마다 빈 후처리기에 보내서 후처리 작업을 요청합니다. 빈 후처리기는 Spring Bean 객체의 프로퍼티를 강제로 수정할수도 있고 별도의 초기화 작업을 수행할 수도 있습니다. 심지어는 만들어진 Spring Bean 객체 자체를 바꿔치기 할수도 있습니다.

![[image-24.png]] 
1. DefaultAdvisorAutoProxyCreator 빈 후처리기가 등록되어 있으면, Spring은 Bean 객체를 생성할때마다 후처리기에게 Bean 객체를 보낸다.
2. DefaultAdvisorAutoProxyCreator는 Spring Bean으로 등록된 모든 Advisor내의 PointCut을 이용해서 전달받은 Spring Bean이 Proxy 적용 대상인지 확인합니다.
3. Proxy 적용 대상이면 그때는 내장된 Proxy 생성기에게 현재 Spring Bean에 대한 Proxy를 만들게 하고 만들어진 Proxy에게 Advisor를 연결해줍니다.
4. 빈 후처리기는 Proxy가 생성되면, 원래 컨테이너가 전달해준 Spring Bean 객체 대신에 Proxy 객체를 컨테이너에 돌려줍니다.
5. 컨테이너는 최종적으로 빈 후처리기가 돌려준 Proxy 객체를 빈으로 등록하고 사용합니다.
	- 적용할 Spring Bean을 선정하는 로직이 추가된 PointCut이 담긴 Advisor를 등록하고 빈 후처리기를 사용하면, 일일히 ProxyFactoryBean 빈을 등록하지 않아도 Target 객체에 자동으로 Proxy가 적용되게 할수 있습니다.

#### 확장된 포인트컷
```java
public interface Pointcut {
	ClassFilter getClassFilter();
    MethodMatcher getMethodMatcher();
    Pointcut TRUE = TruePointcut.INSTANCE;
}
```

PointCut은 ClassFilter와 MethodMatcher를 반환하는 두가지 메서드를 가지고 있습니다. ClassFilter는 Proxy에 적용할 클래스인지 확인합니다. 
MethodMatcher는 Advice를 적용할 메서드인지 확인합니다. 
ClassFilter는 모든 클래스를 다 받아주도록 만들어져 있습니다.

Pointcut 선정 기능을 모두 적용한다면 먼저 프록시를 적용할 클래스인지 판단하고 나서, 적용 대상 클래스인 경우에는 Advice를 적용할 메서드인지 확인하는 식으로 동작합니다.

모든 Spring Bean에 대해서 Proxy 자동 적용 대상을 선별해야 하는 빈 후처리기인 DefaultAdvisorAutoProxyCreator는 클래스와 메서드 선정 알고리즘을 모두 갖고 있는 Pointcut이 필요합니다. 정확히는 그런 Pointcut과 Advice가 결합되어 있는 Advisor가 등록되어 있어야 합니다.

### 5.2 DefaultAdvisorAutoProxyCreator의 적용
클래스 필터

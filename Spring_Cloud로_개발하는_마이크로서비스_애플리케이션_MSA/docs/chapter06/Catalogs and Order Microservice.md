
## 섹션 소개
- User Microservice 기능 추가
- Catalogs Microservice 프로젝트 생성
- Orders Microservice 프로젝트 생성

## Users Microservice - 사용자 조회
- API Gateway Service 변경 - application.yml
	- User Service route 추가


User Service Port 번호 변경
```yaml
server:
	port: 0
```

API Gateway Service 변경
- User Service route 추가
```yaml
spring:  
  application:  
    name: apigateway-service  
  cloud:  
    gateway:  
      server:   
          routes:  
            - id: user-service  
              uri: lb://USER-SERVICE  
              predicates:  
                - Path=/user-service/**
```

Eureka 서버 확인
![](../imgs/Pasted%20image%2020251030150829.png)

User Service 헬스 체크 확인
![](../imgs/Pasted%20image%2020251030150856.png)

## Users Microservice와 Spring Cloud Gateway 연동
API Gateway를 통해서 요청을 전달할때 단순 "/welcome" 경로가 아닌 "/user-service/welcome"과 같이 요청해야 합니다. 물론 User Service의 Controller의 경로 또한 접두사에 "/user-service"가 설정되어야 합니다.
```java
@RestController  
@RequestMapping("/user-service")  
@Slf4j  
@RequiredArgsConstructor  
public class UserController {
}
```
## Users Microservice - 사용자 조회
ResponseUser 클래스 수정
- @JsonInclude 추가하여 null값인 경우에는 프로퍼티를 제외한다
- ResponseOrder 타입 리스트인 orders 필드 추가
```java
@Data  
@JsonInclude(JsonInclude.Include.NON_NULL)  
public class ResponseUser {  
    private String email;  
    private String name;  
    private String userId;  
  
    private List<ResponseOrder> orders;  
}
```

ResponseOrder 클래스 추가
```java
@Data  
public class ResponseOrder {  
    private String productId;  
    private Integer qty;  
    private Integer unitPrice;  
    private Integer totalPrice;  
    private Date createdAt;  
  
    private String orderId;  
}
```

UserService, UserServiceImpl 클래스 수정
- 사용자 조회 API 추가 및 구현
```java
  
@Override  
public UserDto getUserByUserId(String userId) {  
    UserEntity userEntity = userRepository.findByUserId(userId);  
    if (userEntity == null) {  
       throw new UsernameNotFoundException("User not found");  
    }  
  
    UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);  
    List<ResponseOrder> orderList = new ArrayList<>();  
    userDto.setOrders(orderList);  
    return userDto;  
}  
  
@Override  
public Iterable<UserEntity> getUserByAll() {  
    return userRepository.findAll();  
}
```

UserController - 전체 사용자 목록 보기
```java
@GetMapping(value = "/users")  
public ResponseEntity<List<ResponseUser>> getUsers(){  
    Iterable<UserEntity> userList = userService.getUserByAll();  
  
    ModelMapper mapper = new ModelMapper();  
    mapper.getConfiguration().setMatchingStrategy(org.modelmapper.convention.MatchingStrategies.STRICT);  
  
    List<ResponseUser> result = new ArrayList<>();  
    userList.forEach(v-> result.add(mapper.map(v, ResponseUser.class)));  
  
    return ResponseEntity.status(HttpStatus.OK).body(result);  
}
```

실행 결과
![](../imgs/Pasted%20image%2020251030154104.png)

UserController - 사용자 상세 보기 + 주문 목록 보기
```java
@GetMapping(value = "/users/{userId}")  
public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId){  
    UserDto userDto = userService.getUserByUserId(userId);  
  
    ModelMapper mapper = new ModelMapper();  
    mapper.getConfiguration().setMatchingStrategy(org.modelmapper.convention.MatchingStrategies.STRICT);  
    ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);  
  
    return ResponseEntity.status(HttpStatus.OK).body(responseUser);  
}
```

실행 결과
![](../imgs/Pasted%20image%2020251030154959.png)

## Catalogs Microservice - 개요
상품 주문 기능을 구현하기 위해서 Catalog Service 구현이 요구됩니다.

APIs

| 기능             | 마이크로서비스               | URI(API Gateway 사용시)            | HTTP Method |
| -------------- | --------------------- | ------------------------------- | ----------- |
| 상품 목록 조회       | Catalogs Microservice | /catalog-service/catalogs       | GET         |
| 사용자 별 상품 주문    | Orders Microservice   | /order-serivce/{user_id}/orders | POST        |
| 사용자 별 주문 내역 조회 | Orders Microservice   | /order-service/{user_id}/orders | GET         |

Catalogs Microservice Project Dependency
- Lombok
- DevTools
- Spring Web
- Eureka Discovery Client
- Spring Data JPA
- H2 Database
- Model Mapper

Catalogs Microservice application.yml
- spring.jpa.defer-datasource-initialization : datasource 초기화 시점을 JPA 초기화 이후로 지연시키는 설정. JPA 초기화를 이용하여 엔티티 테이블을 초기화한 다음에 data.sql 스크립트를 실행시키기 위함.
	- 해당 설정이 false로 설정되면 datasource 초기화가 JPA 초기화 앞 시점에서 수행됨
```yaml
server:  
  port: 0  
spring:  
  application:  
    name: catalog-service  
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
      ddl-auto: create-drop  
    show-sql: true  
    generate-ddl: true  
    database: h2  
    defer-datasource-initialization: true  
eureka:  
  instance:  
    instance-id: ${spring.application.name}:${spring.application.instance_id:${random.value}}  
  client:  
    service-url:  
      defaultZone: http://127.0.0.1:8761/eureka  
    fetch-registry: true  
    register-with-eureka: true
```


Catalogs Microservice 초기 데이터 SQL 파일
```mysql
insert into catalog(product_id, product_name, stock, unit_price)
values('CATALOG-001', 'Berlin', 100, 1500);
insert into catalog(product_id, product_name, stock, unit_price)
values('CATALOG-002', 'Tokyo', 110, 1000);
insert into catalog(product_id, product_name, stock, unit_price)
values('CATALOG-003', 'Stockholm', 120, 2000);
```

Catalog Entity
```java
@Data  
@Entity  
@Table(name = "catalog")  
public class CatalogEntity {  
    @Id  
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)  
    private Long id;  
    @Column(nullable = false, length = 120, unique = true)  
    private String productId;  
    @Column(nullable = false)  
    private String productName;  
    @Column(nullable = false)  
    private Integer stock;  
    @Column(nullable = false)  
    private Integer unitPrice;  
  
    @Column(nullable = false, updatable = false, insertable = false)  
    @ColumnDefault(value = "CURRENT_TIMESTAMP")  
    private Date createdAt;  
}
```

CatalogRepository
```java
public interface CatalogRepository extends CrudRepository<CatalogEntity, Long> {  
    CatalogEntity findByProductId(String productId);  
}
```

CatalogService
```java
public interface CatalogService {  
    Iterable<CatalogEntity> getAllCatalogs();  
}
@Service  
@Slf4j  
@Data  
public class CatalogServiceImpl implements CatalogService {  
  
    private CatalogRepository catalogRepository;  
    private Environment env;  
  
    @Override  
    public Iterable<CatalogEntity> getAllCatalogs() {  
       return catalogRepository.findAll();  
    }  
}
```

CatalogDto, ResponseCatalog
```java
@Data  
public class CatalogDto {  
    private String productId;  
    private Integer qty;  
    private Integer unitPrice;  
    private Integer totalPrice;  
  
    private String orderId;  
    private Integer userId;  
}
@Data  
@JsonInclude(JsonInclude.Include.NON_NULL)  
public class ResponseCatalog {  
    private String productId;  
    private String productName;  
    private Integer unitPrice;  
    private Integer stock;  
    private Date createdAt;  
}
```

CatalogController
```java
@RestController  
@RequestMapping("/catalog-service")  
public class CatalogController {  
    private final Environment env;  
    private final CatalogService catalogService;  
  
    public CatalogController(Environment env, CatalogService catalogService) {  
       this.env = env;  
       this.catalogService = catalogService;  
    }  
  
    @GetMapping("/health-check")  
    public String status(){  
       return String.format("It's Working in Catalog Service on LOCAL PORT %s (SERVER PORT %s)", env.getProperty("local.server.port"), env.getProperty("server.port"));  
    }  
  
    @GetMapping("/catalogs")  
    public ResponseEntity<List<ResponseCatalog>> getCatalogs(){  
       Iterable<CatalogEntity> catalogs = catalogService.getAllCatalogs();  
       List<ResponseCatalog> result = new ArrayList<>();  
       catalogs.forEach(v -> {  
          result.add(new ModelMapper().map(v, ResponseCatalog.class));  
       });  
       return ResponseEntity.status(HttpStatus.OK).body(result);  
    }  
}
```

API Gateway 서버에 Catalog Service 등록
```yaml
routes:  
  - id: user-service  
    uri: lb://USER-SERVICE  
    predicates:  
      - Path=/user-service/**  
  - id: catalog-service  
    uri: lb://CATALOG-SERVICE  
    predicates:  
      - Path=/catalog-service/**
```

서버 실행
![](../imgs/Pasted%20image%2020251030164934.png)

실행 결과
![](../imgs/Pasted%20image%2020251030165029.png)
![](../imgs/Pasted%20image%2020251030165224.png)

## Orders Microservice - 개요
APIs
- 사용자별 상품 주문
- 사용자별 주문 내역 조회

Orders Microservice Dependency
- DevTools
- Lombok
- Spring Web
- Eureka Discovery Client
- Spring Data JPA
- H2 Database
- Model Mapper

application.yaml
```yaml
server:  
  port: 0  
spring:  
  application:  
    name: order-service  
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
eureka:  
  instance:  
    instance-id: ${spring.application.name}:${spring.application.instance_id:${random.value}}  
  client:  
    service-url:  
      defaultZone: http://127.0.0.1:8761/eureka  
    fetch-registry: true  
    register-with-eureka: true  
logging:  
  level:  
    org.springframework.security: DEBUG
```

OrderEntity
```java
@Data  
@Entity  
@Table(name = "orders")  
public class OrderEntity implements Serializable {  
    @Id  
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)  
    private Long id;  
  
    @Column(nullable = false, length = 120)  
    private String productId;  
    @Column(nullable = false)  
    private Integer qty;  
    @Column(nullable = false)  
    private Integer unitPrice;  
    @Column(nullable = false)  
    private Integer totalPrice;  
    @Column(nullable = false)  
    private String userId;  
    @Column(nullable = false, unique = true)  
    private String orderId;  
    @Column(nullable = false, updatable = false, insertable = false)  
    @ColumnDefault(value = "CURRENT_TIMESTAMP")  
    private Date createdAt;  
}
```

OrderRepository
```java
public interface OrderRepository extends CrudRepository<OrderEntity, Long> {  
    OrderEntity findByOrderId(String orderId);  
    Iterable<OrderEntity> findByUserId(String userId);  
}
```

OrderDto
```java
@Data  
public class OrderDto implements Serializable {  
    private String productId;  
    private Integer qty;  
    private Integer unitPrice;  
    private Integer totalPrice;  
  
    private String orderId;  
    private String userId;  
}
```

ResponseOrder
```java
@Data  
@JsonInclude(JsonInclude.Include.NON_NULL)  
public class ResponseOrder {  
    private String productId;  
    private Integer qty;  
    private Integer unitPrice;  
    private Integer totalPrice;  
    private Date createdAt;  
  
    private String orderId;  
}
```

OrderService
```java
public interface OrderService {  
    OrderDto createOrder(OrderDto orderDetails);  
    OrderDto getOrderByOrderId(String orderId);  
    Iterable<OrderEntity> getOrdersByUserId(String userId);  
}
```

OrderServiceImpl
```java
@Service  
@RequiredArgsConstructor  
public class OrderServiceImpl implements OrderService {  
  
    private final OrderRepository orderRepository;  
  
    @Override  
    public OrderDto createOrder(OrderDto orderDetails) {  
       orderDetails.setOrderId(UUID.randomUUID().toString());  
       orderDetails.setTotalPrice(orderDetails.getQty() * orderDetails.getUnitPrice());  
       ModelMapper modelMapper = new ModelMapper();  
       modelMapper.getConfiguration().setMatchingStrategy(org.modelmapper.convention.MatchingStrategies.STRICT);  
  
       OrderEntity orderEntity = modelMapper.map(orderDetails, OrderEntity.class);  
       orderRepository.save(orderEntity);  
  
       return modelMapper.map(orderEntity, OrderDto.class);  
    }  
  
    @Override  
    public OrderDto getOrderByOrderId(String orderId) {  
       OrderEntity orderEntity = orderRepository.findByOrderId(orderId);  
       ModelMapper modelMapper = new ModelMapper();  
       modelMapper.getConfiguration().setMatchingStrategy(org.modelmapper.convention.MatchingStrategies.STRICT);  
       return modelMapper.map(orderEntity, OrderDto.class);  
    }  
  
    @Override  
    public Iterable<OrderEntity> getOrdersByUserId(String userId) {  
       return orderRepository.findByUserId(userId);  
    }  
}
```

OrderController
```java
@RestController  
@RequiredArgsConstructor  
@RequestMapping("/order-service")  
public class OrderController {  
  
    private final OrderService orderService;  
    @PostMapping("/{userId}/orders")  
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId,  
       @RequestBody RequestOrder orderDetails) {  
       ModelMapper modelMapper = new ModelMapper();  
       modelMapper.getConfiguration().setMatchingStrategy(org.modelmapper.convention.MatchingStrategies.STRICT);  
  
       OrderDto orderDto = modelMapper.map(orderDetails, OrderDto.class);  
       orderDto.setUserId(userId);  
       OrderDto createDto = orderService.createOrder(orderDto);  
       ResponseOrder result = modelMapper.map(createDto, ResponseOrder.class);  
  
       return ResponseEntity.status(HttpStatus.CREATED).body(result);  
    }  
  
    @GetMapping("/{userId}/orders")  
    public ResponseEntity<List<ResponseOrder>> getOrders(@PathVariable("userId") String userId) {  
       Iterable<OrderEntity> orderList = orderService.getOrdersByUserId(userId);  
       ModelMapper modelMapper = new ModelMapper();  
       modelMapper.getConfiguration().setMatchingStrategy(org.modelmapper.convention.MatchingStrategies.STRICT);  
  
       List<ResponseOrder> result = new ArrayList<>();  
  
       orderList.forEach(v->{  
          result.add(modelMapper.map(v, ResponseOrder.class));  
       });  
  
       return ResponseEntity.status(HttpStatus.OK).body(result);  
    }  
}
```

API Gateway Service에 Order Service 등록
![](../imgs/Pasted%20image%2020251031131533.png)

Eureka 실행 결과 확인
![](../imgs/Pasted%20image%2020251031132327.png)

상품 주문 테스트
![](../imgs/Pasted%20image%2020251031133219.png)

상품 주문 조회 테스트
![](../imgs/Pasted%20image%2020251031133354.png)


위 테스트를 한후 사용자 정보를 조회하면, 해당 사용자의 주문 목록이 빈 리스트로 응답된 것을 볼수 있습니다. 이는 아직 상품 주문시 별도의 이벤트가 발생한 것을 구현하지 않았기 때문입니다. 이 문제를 해결하기 위해서 메시지 브로커를 추가하여 메시지를 전달할 수 있도록 합니다.


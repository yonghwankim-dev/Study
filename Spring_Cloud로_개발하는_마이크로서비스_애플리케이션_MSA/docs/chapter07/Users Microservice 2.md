
## 섹션 소개
- Users Microservice - Login
- JWT(Json Web Token)
- API Gateway service - AuthorizationHeaderFilter

## Users Microservice - 기능 추가
APIs

| 기능      | URI (API Gateway 사용시) | URI (API Gateway 미사용시) | HTTP Method |
| ------- | --------------------- | ---------------------- | ----------- |
| 사용자 로그인 | /user-service/login   | /login                 | POST        |

사용자 로그인 API Request Body
```json
{
	"email": "test2@test.com",
	"password": "12345678"
}
```

사용자 로그인 API Response Header
- token : JWT 토큰
- userId : 사용자 식별자 아이디

구현 목록
- RequestLogin 구현
- AuthenticationFilter 구현
	- UsernamePasswordAuthenticationFilter 클래스 상속
	- attempAuthentication(), successfulAuthentication() 함수 구현
		- successfulAuthentication 메서드에서 JWT 발급
- WebSecurity 구현
	- 사용자 요청에 대해서 AuthenticationFilter를 거치도록 수정
	- 모든 경로에 대해서 접근할 수 있는 IP 주소 설정
- UserService 인터페이스 수정
	- UserDetailsService 상속받도록 수정
		- loadUserByUsername 메서드 재정의
- API Gateway Service 수정
	- User Service에 대한 Routes 정보 수정
		- user-service-register : 인증 필요없음
		- user-service-login : 인증 필요없음
		- user-service : 인증 요구됨

## Users Microservice - 인증 처리 구현
AuthenticationFilter 구현
- UsernamePasswordAuthenticationFilter 클래스를 상속받아 다음 두 메서드를 재정의함
- Http RequestBody의 내용을 RequestLogin 객체로 추출하고 UsernamePasswordAuthenticationToken 객체를 생성할때 이메일과 비밀번호를 전달함
- 권한 리스트(authorities)는 현재 시스템에 불필요하기 때문에 빈 리스트를 전달합니다.
- successfulAuthentication 메서드는 이후 구현할 예정
```java
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {  
  
    @Override  
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws  
       AuthenticationException {  
       try {  
          RequestLogin creds = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);  
          return getAuthenticationManager().authenticate(  
             new UsernamePasswordAuthenticationToken(  
                creds.getEmail(),  
                creds.getPassword(),  
                new ArrayList<>()  
             )  
          );  
       } catch (IOException e) {  
          throw new RuntimeException(e);  
       }  
    }  
  
    @Override  
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,  
       Authentication authResult) throws IOException, ServletException {  
    }  
}
```


UserSerivce 구현
```java
@Override  
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {  
    UserEntity userEntity = userRepository.findByEmail(username);  
    if (userEntity == null) {  
       throw new UsernameNotFoundException("User not found");  
    }  
    return new User(  
       userEntity.getEmail(),  
       userEntity.getEncryptedPwd(),  
       true,  
       true,  
       true,  
       true,  
       new ArrayList<>()  
    );  
}
```


## Users Microservice - API Gateway -Rewrite Path 처리
API Gateway 서버의 application.yml 파일에 라우팅 정보 수정
```yaml
- id: user-service-register  
  uri: lb://USER-SERVICE  
  predicates:  
    - Path=/user-service/users  
    - Method=POST  
  filters:  
    - RemoveRequestHeader=Cookie  
    - RewritePath=/user-service/(?<segment>.*), /$\{segment}  
- id: user-service-login  
  uri: lb://USER-SERVICE  
  predicates:  
    - Path=/user-service/login  
    - Method=POST  
  filters:  
    - RemoveRequestHeader=Cookie  
    - RewritePath=/user-service/(?<segment>.*), /$\{segment}  
- id: user-service  
  uri: lb://USER-SERVICE  
  predicates:  
    - Path=/user-service/\*\*
    - Method=GET  
  filters:  
    - RemoveRequestHeader=Cookie  
    - RewritePath=/user-service/(?<segment>.*), /$\{segment}
```

필터 설명
- RemoveRequestHeader=Cookie
	- Http Request의 쿠키를 제거함
- RewritePath
	- **요청 경로에서 `/user-service/` 부분을 제거하고, 나머지 경로(`segment`)로 요청을 전달하라** 는 의미입니다.
```yaml
filters:  
    - RemoveRequestHeader=Cookie  
    - RewritePath=/user-service/(?<segment>.*), /$\{segment}
```

구성 요소별 설명

| 부분                             | 의미                                                                  |
| ------------------------------ | ------------------------------------------------------------------- |
| `/user-service/(?<segment>.*)` | 요청 경로를 정규식으로 매칭하는 패턴                                                |
| `(?<segment>.*)`               | **정규식 캡처 그룹**으로, `/user-service/` 뒤에 오는 모든 문자열을 `segment`라는 이름으로 저장 |
| `/\${segment}`                 | 위에서 캡처된 그룹(`segment`)을 실제 경로로 치환                                    |

동작 예시

| 들어오는 요청 경로                   | 캡처된 segment    | 최종 전달되는 경로      |
| ---------------------------- | -------------- | --------------- |
| `/user-service/login`        | `login`        | `/login`        |
| `/user-service/api/v1/users` | `api/v1/users` | `/api/v1/users` |
| `/user-service/`             | _(빈 문자열)_      | `/`             |

즉, `/user-service/` 접두어를 제거하고, 그 뒤의 나머지 부분만 남겨서 실제 서비스로 전달합니다.

User-Service에 RewritePath 필터를 사용함으로써 클라이언트는 "http://localhost:8000/user-service/users"와 같이 요청하여 서비스 구분을 할 수 있고, User-Service 서버 입장에서는 "http://localhost:60000/users"와 같이 URL 경로에 불필요한 "user-service" 접두사 경로를 넣지 않아도 됩니다.

## Users Microservice - 인증 기능 테스트
User-Service의 시큐리티 설정 정보 수정
- 실제 IPv4 주소 설정해야함. API Gateway에는 127.0.0.1로 오지만 User Service 서버에 수신시 실제 IP 주소로 변환된어 들어옴
```java
http.authorizeHttpRequests(auth->auth  
    .requestMatchers("/h2-console/**").permitAll()  
    .requestMatchers("/welcome").permitAll()  
    .requestMatchers("/**").access(  
       new WebExpressionAuthorizationManager(  
          "hasIpAddress('127.0.0.1') or hasIpAddress('::1') or hasIpAddress('172.30.1.97')"  
       )  
    )  
    .anyRequest().authenticated()  
);
```

![](../imgs/Pasted%20image%2020251031175641.png)

로그인 실행 결과
![](../imgs/Pasted%20image%2020251102141104.png)

#### 이슈1: 로그인 실패 문제
배경 : 회원을 추가한 뒤에 API Gateway에 로그인을 시도시 404 오류가 발생합니다.
![](../imgs/Pasted%20image%2020251102141605.png)

원인 : 경로를 RewritePath하는 것과 User-Service에서 인증을 성공하는 것까지는 성공이지만 인증 후에 처리에서 "/" 경로로 기본적으로 리다이렉트합니다. 하지만 현재 User-Service 서버에서는 "/" 경로에 대한 리소스나 컨트롤러 매핑이 없기 때문에 404 에러가 발생한 것입니다.

인증 후에 상위 클래스의 메서드를 호출하게 됨으로써 기본적인 "/" 경로로 리다이렉트하게 됩니다.
```java
@Override  
protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,  
    Authentication authResult) throws IOException, ServletException {  
    super.successfulAuthentication(request, response, chain, authResult);  
}
```


해결 방법 : AuthenticationFilter의 successfulAuthentication 메서드에서 다음과 같이 상위 클래스로 위임하지 않고 그대로 종료하도록 변경합니다.
```java
@Override  
protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,  
    Authentication authResult) throws IOException, ServletException {  
}
```

실행 결과 확인
- 로그인에 성공(200 OK)한 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251102141104.png)

## AuthenticationFilter 추가
Spring Security 로그인 수행과정
1. AuthenticationFilter 클래스의 attempAuthentication 메서드에서 AuthenticationToken(UsernamePasswordAuthenticationToken) 객체를 생성하고 AuthenticationManager에게 인증 작업을 위임합니다.
2. AuthenticationManager는 UserDetailService에게 username을 입력으로 username에 해당하는 회원을 찾고, User 객체를 생성하도록 합니다.
3. UserDetailService가 User 객체를 반환하면 인증이 성공한 것이기 때문에 최종적으로 AuthenticationFilter의 successfulAuthentication() 메서드를 수행하고 인증 과정을 마칩니다.
	- successfulAuthentication 메서드에서 JWT를 발급하고 클라이언트에게 전달하도록 합니다.

위 수행과정을 그림으로 표현하면 다음과 같습니다.
![](../imgs/Pasted%20image%2020251102145732.png)

## successfulAuthentication 구현
```java
@Override  
protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,  
    Authentication authResult) throws IOException, ServletException {  
    String userEmail = ((User)authResult.getPrincipal()).getUsername(); // email  
    UserDto userDto = userService.getUserDetailsByEmail(userEmail);  
  
    byte[] secretKeyBytes = env.getProperty("token.secret").getBytes(StandardCharsets.UTF_8);  
      
}
```

## JWT(Json Web Token) 개요
전통적인 인증 시스템
1. 클라이언트는 서버에게 username과 password를 전달하며 인증을 요청합니다.
2. 서버는 인증 과정을 거치고 인증이 완료되면 Cookie에 sessionId를 전달하며 응답합니다.
3. 클라이언트는 이후 인증이 요구되는 API 경로로 서버에 리소스를 요청합니다.
	- 클라이언트는 헤더 Cookie에 이전에 받은 sessionId를 첨부하여 같이 전달합니다.
4. 서버는 클라이언트로부터 받은 sessionId를 기반으로 인증된 사용자 정보를 가져옵니다. 그리고 해당 사용자가 리소스 접근해도 되는지 인가 검사합니다. 리소스 접근이 허용되면 API 요청 서비스를 처리하고 결과를 응답합니다.

전통적인 인증 시스템의 문제점
- 세션과 쿠키는 모바일 애플리케이션에서 유효하게 사용할 수 없음(공유 불가)
- 렌더링된 HTML 페이지가 반환되지만, 모바일 애플리케이션에서는 JSON(or XML)과 같은 포맷이 필요함

---

Token 기반 인증 시스템
1. 클라이언트는 서버에게 username과 password를 전달하며 인증 요청합니다.
2. 서버는 인증 과정을 거치고 클라이언트에게 JWT을 발급하여 전달합니다. 
	- JWT를 전달받은 클라이언트는 해당 토큰을  보관해두었다가 다음 요청시 같이 전달하도록 합니다.
3. 클라이언트는 인증이 요구되는 API를 요청합니다.
	- JWT 토큰도 헤더에 포함하여 같이 전달합니다.
4. 서버는 JWT를 검증합니다. JWT가 유효하면 서버는 요청한 정보를 처리하고 클라이언트에게 응답합니다.

JWT란 무엇인가?
- Json Web Token
- 인증 헤더 내에서 사용되는 토큰 포맷
- 두개의 시스템끼리 안전한 방법으로 통신 가능

다음 화면을 보면 왼쪽의 JWT를 디코딩하면 오른쪽과 같은 헤더와 바디에서 정보를 얻을 수 있습니다. 토큰의 타입은 JWT이고 알고리즘 종류는 HS256입니다. 바디에서는 해당 토큰의 이름이 "John Doe"인 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251102154539.png)

JWT 장점
- 클라이언트 독립적인 서비스(stateless)
- CDN
- No Cookie-Session(No CSRF, 사이트간 요청 위조)
- 지속적인 토큰 저장

#### 여러개의 서버가 존재하는 상황에서 JWT를 활용한 인가 수행과정
다음과 같은 구성으로 서버가 존재한다고 가정합니다.
- 클라이언트 2개
- 서비스 서버 3개
- 로드밸런서 1개

1. 클라이언트가 로드밸런서에 요청을 하게 되면 로드밸런서는 부하 분산에 따라서 서버1, 2, 3 중에 하나로 요청을 전달합니다.
	- 클라이언트 1이 서버1로 서빙되고, 클라이언트 2가 서버2로 서빙된다고 가정합니다.
2. 실행중 서버2가 다운된다고 가정합니다. 이후의 요청들은 서버2로 서빙되지 않고 서버1, 3으로 서빙됩니다.
3. 클라이언트 1이 발급받은 JWT를 그대로 다시 서버 3에 전달되어도 다시 별도의 인증 과정을 거치지 않고 서버3은 토큰을 가지고 검증한뒤 유효하면 요청을 처리할 것입니다.
	- 만약 서버들간에 인증 방식이 세션 방식이라면 서버2가 다운되면 다른 서버에서 별도의 인증 과정을 거쳐야 합니다. (JWT과의 차이점)
	- 세션 방식인 경우 sessionId를 서버에 로컬로 가지고 있는 경우에 한하여 가정합니다.

### JWT 구현 과정
- application.yml 파일에 JWT 관련 프로퍼티 추가
- AuthenticationFilter의 successfulAuthentication 메서드를 수정합니다.
	- JWT 발급하는 코드 구현
	- 헤더에 JWT를 추가하는 코드 구현
- Test
	- 회원가입
	- 로그인
		- 로그인 후에 Response 헤더에 token과 userId를 발급 확인
	- 로그인 후 발급받은 토큰은 jwt.io에서 디코딩하여 확인

## JWT 구현
User-Service 의존성 추가
```xml
<dependency>  
    <groupId>io.jsonwebtoken</groupId>  
    <artifactId>jjwt</artifactId>  
    <version>0.13.0</version>  
</dependency>
```

User-Service application.yml 파일 수정
- JWT 발급하는데 사용되는 시크릿 토큰 값 정의
- 이 시크릿 토큰 값을 이용해서 JWT에 서명을 합니다.
```yaml
token:  
  expiration-time: 86400000 #10 days  
  secret: my-very-long-secret-key-1234567890123456
```

AuthneticationFilter 클래스의 successfulAuthentication 메서드 수정
- JWT 발급
- HMAC SHA-256 방식일 경우에 최소 256비트, 즉 32바이트의 키가 필요합니다. 그래서 시크릿 토큰 값을 32자리 이상으로 지정하였습니다.

```java
@Override  
protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,  
    Authentication authResult) throws IOException, ServletException {  
    String userEmail = ((User)authResult.getPrincipal()).getUsername(); // email  
    UserDto userDetails = userService.getUserDetailsByEmail(userEmail);  
  
    byte[] secretKeyBytes = env.getProperty("token.secret").getBytes(StandardCharsets.UTF_8);  
  
    SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);  
  
    Instant now = Instant.now();  
    String token = Jwts.builder()  
       .subject(userDetails.getUserId()) // UUID  
       .expiration(Date.from(now.plusMillis(Long.parseLong(env.getProperty("token.expiration-time")))))  
       .issuedAt(Date.from(now))  
       .signWith(secretKey)  
       .compact();  
    response.addHeader("token", token);  
    response.addHeader("userId", userDetails.getUserId());  
}
```
- subject : 어떤 대상을 암호화 할것인지 설정
- expiration : 토큰 유효 시간 설정
- issuedAt : 발행 날짜 지정
- signWith : JWT를 서명하기 위해서 사용할 비밀키 또는 공개/개인키 쌍을 지정
	- 전달한 키를 이용하여 헤더, 페이로드를 암호화하여 서명(Signature)를 생성합니다.
- compact : 결과물 String 형식으로 생성
- Response 헤더에 userId 헤더를 추가한 이유는 JWT를 복호화하면 userId가 추출되는데 userId 헤더를 통해서 한번더 받음으로써 클라이언트 입장에서 처음에 저장했었던 userId와 같은지 검증용으로 사용하기 위해서입니다. 즉, 중간 단계에서 위조, 변조가 없었는지를 확인하기 위한 용도입니다.


> [!NOTE] HMAC(Hash-based Message Authentication Code)
> - 무결성과 인증을 위한 해시 기반 메시지 인증 코드
> - 키(key)를 사용해서 해시를 만들기 때문에, 수신자는 동일한 키로 해시를 재계산하여 메시지의 위조 여부를 검증합니다.

실행 결과 확인
- 로그인 실행 결과를 보면 Response Header에 token과 userId 헤더가 포함된 것을 볼수 있습니다.
- 다음 결과에 나온 token을 헤더에 담아서 요청해서 인가가 요구되는 API 응답을 받을 수 있습니다.
![](../imgs/Pasted%20image%2020251103132018.png)

token 헤더의 값을 디코딩하면 다음과 같습니다.
- 디코딩된 영역을 보면 헤더의 알고리즘이 HS256인 것을 볼수 있고 페이로드에서 sub에 userId의 값, exp, iat로써 유효기간, 발행일자를 타임스탬프로 표시된 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251103132330.png)


## API Gateway - Filter 추가
- API Gateway 서버에 User-Service에서 발급한 JWT를 처리하는 방법을 구현합니다.
- API Gateway 서버에 Spring Security와 jjwt 의존성을 추가합니다.
- API Gateway 서버에서 발급받은 JWT의 유효성 검사를 수행합니다.
- API Gateway 서버가 요청 API에 대한 인가 검사를 수행합니다.

구현 목록
- Spring Security, jjwt 의존성 추가
- AuthorizationHeaderFilter 추가 및 구현
	- AbstractGatewayFilterFactory 클래스 상속받음
	- Authorization 헤더를 포함하고 있는지 검사함
	- 헤더에서 JWT를 추출함
	- 추출한 JWT가 유효한지 검사함
- JWT가 유효한지 검사할때 복호화를 수행하는데, 복호화 수행시 암호화할때 사용한 시크릿 토큰 값이 필요합니다.
	- APi Gateway 서비스에서 가지고 있는 시크릿 토큰값과 User-Service 서비스에서 가지고 있는 시크릿 토큰값은 동일해야 합니다.
	- JWT 유효한지 검사할때 sub 값을 추출하고 null이거나 비어있지 않은지 검사합니다.

## AuthorizationHeaderFilter 구현
apigateway-service 의존성 추가
```xml
<dependency>  
    <groupId>io.jsonwebtoken</groupId>  
    <artifactId>jjwt</artifactId>  
    <version>0.13.0</version>  
</dependency>
```

apigateway-service application.yml 수정
- token.secret 프로퍼티 값 추가
```yaml
token:  
  secret: my-very-long-secret-key-1234567890123456
```
- user-serivce의 token.secret 프로퍼티와 동일한 값으로 추가합니다. 하지만 이 문제는 user-serivce와 apigateway-service가 중복된 설정 정보를 가지고 있기 때문에 관리가 어려울수 있습니다. 이 문제를 해결하기 위해 추후 Spring Cloud Config 서버를 통해서 문제를 해결할 수 있습니다.

apigateway-service AuthorizationHeaderFilter 구현
```java
@Component
@Slf4j  
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {  
  
    private final Environment env;  
  
    public AuthorizationHeaderFilter(Environment env) {  
       super(Config.class);  
       this.env = env;  
    }  
  
    @Override  
    public GatewayFilter apply(Config config) {  
       return (exchange, chain) -> {  
          ServerHttpRequest request = exchange.getRequest();  
  
          if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){  
             return onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);  
          }  
  
          String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);  
          String jwt = authorizationHeader.replace("Bearer ", "");  
  
          if (!isJwtValid(jwt)){  
             return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);  
          }  
  
          return chain.filter(exchange);  
       };  
    }  
  
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {.  
       ServerHttpResponse response = exchange.getResponse();  
       response.setStatusCode(httpStatus);  
       log.error(err);  
  
       byte[] bytes = "The requested token is invalid".getBytes(StandardCharsets.UTF_8);  
       DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);  
       return response.writeWith(Flux.just(buffer));  
    }  
  
    private boolean isJwtValid(String jwt) {  
       byte[] secretKeyBytes = env.getProperty("token.secret").getBytes();  
       SecretKey secretKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());  
  
       boolean returnValue = true;  
       String subject = null;  
  
       try{  
          JwtParser jwtParser = Jwts.parser()  
             .setSigningKey(secretKey)  
             .build();  
  
          subject = jwtParser.parseClaimsJws(jwt).getBody().getSubject();  
       }catch (Exception ex){  
          returnValue = false;  
       }  
  
       if (subject == null || subject.isEmpty()){  
          returnValue = false;  
       }  
       return returnValue;  
    }  
  
    public static class Config {  
       // Put the configuration properties for your filter here  
    }  
}
```
- parseClaimsJws() : 서명이 포함된 JWT를 파싱할때 사용합니다.
	- 토큰값을 디코딩했을때 헤더에 "alg" 프로퍼티 값(예를 들어 HS256)이 존재하는 경우 해당 토큰은 서명이 포함되었다고 간주할 수 있음
- parseClaimsJwt() : 서명이 없는 순수한 JWT를 파싱할 때 사용합니다.

## API Gateway - Route 처리
application.yaml
- user-service 라우팅 정보에 AuthorizationHeaderFilter 추가
- user-service로 라우팅하기 전에 AuthorizationHeaderFilter 필터가 작동하여 전달받은 JWT를 기반으로 유효한지 확인하고 라우팅합니다.
```yaml
- id: user-service  
  uri: lb://USER-SERVICE  
  predicates:  
    - Path=/user-service/**  
    - Method=GET  
  filters:  
    - RemoveRequestHeader=Cookie  
    - RewritePath=/user-service/(?<segment>.*), /$\{segment}  
    - AuthorizationHeaderFilter
```

테스트
- 로그인하여 JWT를 발급받고 다른 API 요청시 Authorization 헤더에 토큰값을 설정합니다.
![](../imgs/Pasted%20image%2020251103144614.png)

- 회원 상세 보기 및 주문 목록 조회 API 요청시 헤더에 JWT를 설정합니다.
- pathVariable에 userId를 설정합니다.
![](../imgs/Pasted%20image%2020251103144736.png)
![](../imgs/Pasted%20image%2020251103144806.png)

- Authorization 헤더에 발급받은 JWT 값을 설정하여 요청합니다.
- 실행 결과 회원의 상세 정보가 응답된 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251103145458.png)



## 1. 개요
이 글에서는 Spring 5 WebSockets API와 Spring WebFlux에서 제공하는 반응형(reactive) 기능을 이용하여 간단한 예제를 만들어 보겠습니다.
WebSocket은 클라이언트와 서버간에 양방향 통신을 가능하게 하는 프로토콜로, 일반적으로 클라이언트와 서버가 높은 빈도(high frequency)와 낮은 지연 시간(low latency)으로 이벤트를 교환해야 하는 웹 애플리케이션에서 사용됩니다.

## 2. Maven Dependencies
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-integration</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
```

## 3. Spring에서 WebSocket 설정
우리는 Spring WebSocket 애플리케이션에서 소켓 세션을 처리할 WebSocketHandler를 주입할 것입니다.
```java
@Autowired
private WebSocketHandler webSocketHandler;
```

또한 요청과 핸들러 객체 간의 매핑을 담당할 HandlerMapping 스프링 빈을 정의합니다.
```java
@Bean
public HandlerMapping webSocketHandlerMapping() {
    Map<String, WebSocketHandler> map = new HashMap<>();
    map.put("/event-emitter", webSocketHandler);

    SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
    handlerMapping.setOrder(1);
    handlerMapping.setUrlMap(map);
    return handlerMapping;
}
```
URL은 `ws://localhost:{PORT}/event-emitter`와 같이 연결할 수 있습니다.

## 4. Spring에서 WebSocket Message Handling
_ReactiveWebSocketHandler_ 클래스는 서버 사이드의 WebSocket 세션 관리를 담당할 것입니다.
_ReactiveWebSocketHandler_ 클래스는 _WebSocketHandler_ 인터페이스를 상속받고 handle 메서드를 재정의합니다. 

```java
@Component
public class ReactiveWebSocketHandler implements WebSocketHandler {
    
    // private fields ...

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        return webSocketSession.send(intervalFlux
          .map(webSocketSession::textMessage))
          .and(webSocketSession.receive()
            .map(WebSocketMessage::getPayloadAsText)
            .log());
    }
}
```

## 5. 간단한 Reactive WebSocket Client 생성하기
우리의 WebSocket 서버와 연결하고 정보를 교환하기 위해서 Spring Reactive WebSocket 클라이언트를 생성해봅시다.

### 5.1 Maven Dependency
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
```

### 5.2 WebSocket Client
서버와 통신하는 _ReactiveClientWebSocket_ 클래스를 생성해봅시다.
```java
public class ReactiveJavaClientWebSocket {
 
    public static void main(String[] args) throws InterruptedException {
 
        WebSocketClient client = new ReactorNettyWebSocketClient();
        client.execute(
          URI.create("ws://localhost:8080/event-emitter"), 
          session -> session.send(
            Mono.just(session.textMessage("event-spring-reactive-client-websocket")))
            .thenMany(session.receive()
              .map(WebSocketMessage::getPayloadAsText)
              .log())
            .then())
            .block(Duration.ofSeconds(10L));
    }
}
```
위 코드에서 Reactor Netty의 사용을 위해서 WebSocketClient 인터페이스의 구현체인 _ReactorNettyWebSocketClient_ 클래스를 사용하는 것을 볼수 있습니다.
추가적으로 클라이언트는 WebSocket 서버와 `ws://localhost:8080/event-emitter` URL을 통해서 연결됩니다.
우리는 또한 서버에게 "_event-spring-reactive-client-websocket_" 문자열 메시지를 전송할 것입니다. thenMany 연산을 통해서 메시지를 받은 다음에 매핑을 수행하고 로깅합니다. block 연산을 통해서 최대 10초까지 기다립니다.

### 5.3 Client 시작하기
이를 실행하려면, 먼저 Reactive WebSocket 서버가 실행중인지 확인하세요. 그 다음에 ReactiveJavaClientWebSocket 클래스를 실행하면 sysout 로그에서 이벤트가 방출되는 것을 확인할 수 있습니다.

```shell
[reactor-http-nio-4] INFO reactor.Flux.Map.1 - 
onNext({"eventId":"6042b94f-fd02-47a1-911d-dacf97f12ba6",
"eventDt":"2018-01-11T23:29:26.900"})
```

또한 연결 시도 중에 클라이언트가 보낸 메시지를 Reactive WebSocket 서버의 로그에서 확인할 수 있습니다.
```shell
[reactor-http-nio-2] reactor.Flux.Map.1: 
onNext(event-me-from-reactive-java-client)
```

또한, 클라이언트가 요청을 완료한 후(위 예제의 경우 10초) 연결 종료 메시지를 확인할 수 있습니다.
```shell
[reactor-http-nio-2] reactor.Flux.Map.1: onComplete()
```

## 6. Browser WebSocket Client 생성하기
우리의 반응형 WebSocket 서버 애플리케이션을 소비할 간단한 HTML/Javascript 클라이언트, WebSocket을 생성해봅시다. 
```html
<div class="events"></div>
<script>
    var clientWebSocket = new WebSocket("ws://localhost:8080/event-emitter");
    clientWebSocket.onopen = function() {
        console.log("clientWebSocket.onopen", clientWebSocket);
        console.log("clientWebSocket.readyState", "websocketstatus");
        clientWebSocket.send("event-me-from-browser");
    }
    clientWebSocket.onclose = function(error) {
        console.log("clientWebSocket.onclose", clientWebSocket, error);
        events("Closing connection");
    }
    clientWebSocket.onerror = function(error) {
        console.log("clientWebSocket.onerror", clientWebSocket, error);
        events("An error occured");
    }
    clientWebSocket.onmessage = function(error) {
        console.log("clientWebSocket.onmessage", clientWebSocket, error);
        events(error.data);
    }
    function events(responseEvent) {
        document.querySelector(".events").innerHTML += responseEvent + "<br>";
    }
</script>
```

WebSocket 서버가 실행중인 상태에서 이 HTML 파일을 브라우저에서 열면, WebSocket 서버에서 정의한 대로 각 이벤트가 1초 간격으로 화면에 출력되는 것을 볼수 있습니다.
```shell
{"eventId":"c25975de-6775-4b0b-b974-b396847878e6","eventDt":"2018-01-11T23:56:09.780"}
{"eventId":"ac74170b-1f71-49d3-8737-b3f9a8a352f9","eventDt":"2018-01-11T23:56:09.781"}
{"eventId":"40d8f305-f252-4c14-86d7-ed134d3e10c6","eventDt":"2018-01-11T23:56:09.782"}
```

## 7. 결론
Spring WebFlux에서 제공하는 새로운 반응형 기능을 구현한 Spring 5 프레임워크를 사용하여 서버와 클라이언트 사이에 웹소켓 통신을 생성하는 예제를 살펴봤습니다.


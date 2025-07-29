
- [[#1. 개요|1. 개요]]
- [[#2. Spring 6 Webflux의 SSE 사용|2. Spring 6 Webflux의 SSE 사용]]
	- [[#2. Spring 6 Webflux의 SSE 사용#2.1 Flux를 사용한 Stream Events|2.1 Flux를 사용한 Stream Events]]
	- [[#2. Spring 6 Webflux의 SSE 사용#2.2 ServerSentEvent 요소 활용하기|2.2 ServerSentEvent 요소 활용하기]]
	- [[#2. Spring 6 Webflux의 SSE 사용#2.3 WebClient에서 Server-Sent Events를 소비하기|2.3 WebClient에서 Server-Sent Events를 소비하기]]
- [[#3. Spring MVC에서 SSE 스트리밍|3. Spring MVC에서 SSE 스트리밍]]
- [[#4. Server-Sent Events 이해|4. Server-Sent Events 이해]]
- [[#References|References]]

## 1. 개요
이번 튜토리얼에서는 Spring에서 Server-Sent-Events 기반 API들을 어떻게 구현할 것인지 알아볼 것입니다.
#Server-Sent-Events(SSE) 는 웹 애플리케이션이 단방향 이벤트 스트림을 처리하고, 서버가 데이터를 전송할 때마다 업데이트를 받을 수 있도록 하는 HTTP 표준입니다.
Spring 4.2 버전에서 이미 SSE를 지원했지만, Spring 5부터는 더 직관적이고 편리한 방식으로 이를 처리할 수 있습니다.

## 2. Spring 6 Webflux의 SSE 사용
Spring 6 Webflux에서 SSE를 사용하기 위해서는 Reactor 라이브러리에서 제공하는 Flux 클래스를 사용할 수있으며, ServerSentEvent 엔티티를 활용하면 이벤트의 메타 데이터를 더욱 세밀하게 제어할 수 있습니다.

### 2.1 Flux를 사용한 Stream Events
Flux는 이벤트 스트림을 반응형으로 표현한 객체이며, 특정 요청 또는 응답의 미디어 타입(MediaType)에 따라 다르게 처리됩니다.
SSE 스트리밍 엔드포인트를 생성하기 위해서는 [W3C 스펙들](https://html.spec.whatwg.org/multipage/server-sent-events.html#server-sent-events)을 따라야 하고 MIME 타입을 text/event-stream으로 설계되어야 합니다.
```java
@GetMapping(path = "/stream-flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public Flux<String> streamFlux() {
    return Flux.interval(Duration.ofSeconds(1))
      .map(sequence -> "Flux - " + LocalTime.now().toString());
}
```
interval 메서드는 점진적으로 증가하는 long 값을 방출하는 Flux 객체를 생성한다. 그런 다음에 우리는 그 값들을 원하는 출력형식으로 변환합니다. 위 예제에서는 long 값인 sequence 값을 사용하지 않고 Flux-{현재시간} 형식으로 매핑합니다.
이제 애플리케이션을 시작하고, 엔드포인트를 브라우징하여 결과를 확인해봅니다.
우리는 서버가 초 단위로 푸시하는 이벤트에 대해서 브라우저가 어떻게 반응하는지 확인할 수 있습니다. 

### 2.2 ServerSentEvent 요소 활용하기
우리는 출력 타입을 String 타입에서 ServerSentEvent 타입으로 래핑할 것입니다. 그리고 ServerSentEvent 타입의 장점을 설명할 것입니다.
```java
@GetMapping("/stream-sse")
public Flux<ServerSentEvent<String>> streamEvents() {
    return Flux.interval(Duration.ofSeconds(1))
      .map(sequence -> ServerSentEvent.<String> builder()
        .id(String.valueOf(sequence))
          .event("periodic-event")
          .data("SSE - " + LocalTime.now().toString())
          .build());
}
```
ServerSentEvent 엔티티를 사용하면 다음과 같은 장점이 있습니다. 
1. 이벤트의 메타데이터를 제어할 수 있으며, 이는 실제 사례에서 필수적일 수 있습니다.
	1. 우리는 "text/event-stream" 미디어 타입 정의를 무시할 수 있습니다.

위 예제에서 우리는 식별자(id), 이벤트 이름(event name), 이벤트 데이터(data)를 정의하였습니다.
또한 우리는 comment 속성과 retry 값을 추가할 수 있습니다. 이를 통해 이벤트 전송 시 클라이언트의 재연결 시간을 지정할 수 있습니다.

### 2.3 WebClient에서 Server-Sent Events를 소비하기
WebClient를 사용하여 이벤트 스트림을 소비해봅시다.
```java
public void consumeServerSentEvent() {
    WebClient client = WebClient.create("http://localhost:8080/sse-server");
    ParameterizedTypeReference<ServerSentEvent<String>> type
     = new ParameterizedTypeReference<ServerSentEvent<String>>() {};

    Flux<ServerSentEvent<String>> eventStream = client.get()
      .uri("/stream-sse")
      .retrieve()
      .bodyToFlux(type);

    eventStream.subscribe(
      content -> logger.info("Time: {} - event: name[{}], id [{}], content[{}] ",
        LocalTime.now(), content.event(), content.id(), content.data()),
      error -> logger.error("Error receiving SSE: {}", error),
      () -> logger.info("Completed!!!"));
}
```
subscribe 메서드를 사용하면 이벤트를 성공적으로 수신했을 때, 오류가 발생했을 때, 그리고 스트리밍이 완료되었을 때 각각 어떻게 처리할지를 지정할 수 있습니다.
위 예제에서는 retrieve 메서드를 사용했으며, 이는 ResponseBody를 가져오는 간단하고 직관적인 방법입니다.
이 메서드는 onStatus 연산을 추가하여 시나리오를 제어할 수 있음에도 불구하고 만약 4xx, 5xx 응답을 순시한다면 WebClientResponseException 예외가 발생합니다.
반면에 exchange 메서드를 사용할 수도 있는데, exchange 메서드를 사용하면 ClientResponse에 접근할 수 있도록 해주며, 실패한 응답에 대해서 오류 신호를 보내지 않습니다.
이벤트 메타데이터가 필요하지 않다면, ServerSentEvent 래퍼를 생략하고 직접 데이터를 반환할 수 있습니다.

## 3. Spring MVC에서 SSE 스트리밍
위에서 언급했던 것처럼, SSE 스펙은 SSEmitter 클래스가 소개되었을때 Spring 4.2부터 지원되고 있었습니다. 
간단히 말해서, 우리는 ExecutorService를 정의하고, SseEmitter가 데이터를 푸시할 스레드를 생성한 후 Emitter 인스턴스를 반환하여 연결을 유지하는 방식으로 구현할 수 있습니다.
```java
@GetMapping("/stream-sse-mvc")
public SseEmitter streamSseMvc() {
    SseEmitter emitter = new SseEmitter();
    ExecutorService sseMvcExecutor = Executors.newSingleThreadExecutor();
    sseMvcExecutor.execute(() -> {
        try {
            for (int i = 0; true; i++) {
                SseEventBuilder event = SseEmitter.event()
                  .data("SSE MVC - " + LocalTime.now().toString())
                  .id(String.valueOf(i))
                  .name("sse event - mvc");
                emitter.send(event);
                Thread.sleep(1000);
            }
        } catch (Exception ex) {
            emitter.completeWithError(ex);
        }
    });
    return emitter;
}
```
항상 사용 사례에 적합한 ExecutorService를 선택하는 것이 중요합니다.
Spring MVC에서 SSE에 대해서 더 배울 수 있고 예제를 보기 위해서는 이 [튜토리얼](https://www.baeldung.com/spring-mvc-sse-streams)을 참고해주세요.

## 4. Server-Sent Events 이해
이제 SSE 엔드포인트를 구현하는 방법을 알았으니, 기본 개념을 더 깊이 이해해보겠습니다.
SSE는 대부분의 브라우저에서 채택된 사양으로 언제든지 단방향으로 이벤트 스트리밍을 허용합니다.
'events'는 스펙에 정의된 형식을 따르는 UTF-8 인코딩된 텍스트 데이터의 스트림에 불과합니다. 
이 형식은 id, retry, data, event와 같은 키-값 요소들이 줄 바꿈으로 구분된 형태로 구성됩니다. 여기서 event는 이벤트의 이름을 나타냅니다.
Comment도 지원됩니다.
스펙은 데이터 페이로드 형식에 제한을 두지 않으며, 간단한 문자열이나 더 복잡한 JSON 또는 XML 구조를 사용할 수 있습니다.
마지막으로 고려해야할 점은 SSE 스트리밍과 WebSocket을 사용할 때의 차이점입니다.
WebSocket은 서버와 클라이언트간에 양방향 통신을 제공하는 반면에, SSE는 단방향 통신을 사용합니다.
또한 WebSocket은 SSE와는 다르게 HTTP 프로토콜이 아니고 오류 처리 표준을 제공하지 않습니다.

## Source Code
- https://github.com/yonghwankim-dev/spring/tree/main/demo/spring-demo/sse

## References
- https://www.baeldung.com/spring-server-sent-events
- https://html.spec.whatwg.org/multipage/



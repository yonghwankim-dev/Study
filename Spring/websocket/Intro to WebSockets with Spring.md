
## 1. 개요
이번장에서는 Spring Framework 4.0에 새롭게 도입된 WebSocket 기능을 사용하여 메시징을 구현하는 간단한 웹 애플리케이션을 만들어 보겠습니다.
WebSocket은 웹 브라우저와 서버 사이에 **양방향, 전이중(Full-Duplex, 양쪽에서 동시에 데이터를 주고받을 수 있음), 지속적인 연결**을 제공하는 통신 방식입니다. WebSocket 연결이 설립되면, 클라이언트 또는 서버가 연결을 끊을 때까지 연결을 open 상태로 유지합니다.
대표적인 사용 사례는 여러 사용자가 서로 소통하는 애플리케이션인 채팅 애플리케이션이 있습니다.

## 2. Maven Dependencies
```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-websocket</artifactId>
    <version>6.0.13</version>
</dependency>

<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-messaging</artifactId>
    <version>6.0.13</version>
</dependency
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-core</artifactId>
    <version>2.17.2</version>
</dependency>

<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId> 
    <version>2.17.2</version>
</dependency>
```

## 3. Spring에서 WebSocket 활성화하기
우선 WebSocket을 활성화해야 합니다. 활성화를 위해서는 @EnableWebSocketMessageBroker 애노테이션을 추가해야 합니다.
이름처럼 해당 애노테이션은 메시지 브로커에 의해서 WebSocket 메시지 제어를 활성화합니다.
```java
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
         registry.addEndpoint("/chat");
         registry.addEndpoint("/chat").withSockJS();
    }
}
```

첫번째로 우리는 인메모리 메시지 브로커를 활성화하여, `/topic` 접두사가 붙은 대상(destination)으로 메시지를 클라이언트에게 전달할 수 있도록 설정합니다.
우리는 `/app` 접두사를 지정하여 `@MessageMapping` 애노테이션이 적용된 애플리케이션 메서드를 대상으로 하는 목적지(destination)을 필터링함으로써 간단한 설정을 완료하였습니다.
`registerStompEndpoints` 메서드는 `/chat` 엔드포인드를 등록해서 Spring의 STOMP 지원을 활성화합니다. 또한 유연성을 고려해서 SockJS 없이도 동작하는 엔드포인트를 추가하고 있습니다.
`/chat` 엔드포인트 앞에는 접두사로 `/app`이 붙여집니다. `/app/chart` 엔드포인트 경로는 ChartController 클래스의 send 메서드에 매핑됩니다.
`/chat` 엔드포인트는 SockJS 폴백 옵션 또한 활성화합니다. 이 옵션은 만약 WebSocket이 이용 불가능하면 사용될 수 있는 대체 메시지 옵션입니다. 이는 WebSocket이 아직 모든 브라우저에서 지원되지 않거나, 제한적인 네트워크 프록시로 인해 차단될 수 있기 때문에 유용합니다.

## 4. 메시지 모델 생성하기
지금 우리는 프로젝트와 웹소켓 설정을 환경구성하였습니다. 우리는 전송하기 위한 메시지를 생성해야 합니다.
이 엔드포인트는 송신자 이름과 텍스트가 포함된 메시지를 STOMP 메시지로 받아, 그 본문(body가 JSON 객체)을 처리합니다.
메시지는 다음과 같을 수 있습니다.
```json
{
    "from": "John",
    "text": "Hello!"
}
```

텍스트를 전달하기 위한 메시지를 모델링하기 위해서는 우리는 다음과 같은 자바 클래스를 생성할 수 있습니다.
```java
public class Message {

    private String from;
    private String text;

    // getters and setters
}
```
Spring은 기본적으로 JSON에서 자바 객체로 전환하는데 Jackson 라이브러리를 사용할 것입니다.

## 5. Message-Handling Controller 생성하기
STOMP 메시징 작업은 엔드포인트에 설정된 컨트롤러 메서드와 연관되어 있습니다. 우리는 이 메시징 작업을 `@MessageMapping` 애노테이션을 통해서 할 수 있습니다. 엔드포인트와 컨트롤러 간의 연결을 통해 필요에 따라 메시지를 처리할 수 있는 능력을 제공합니다.
```java
@MessageMapping("/chat")
@SendTo("/topic/messages")
public OutputMessage send(Message message) throws Exception {
    String time = new SimpleDateFormat("HH:mm").format(new Date());
    return new OutputMessage(message.getFrom(), message.getText(), time);
}
```
위 예제에서 설정된 목적지로 전송하기 위해서 출력 메시지를 의미하는 OutputMessage 객체를 생성하였습니다. OutputMessage 객체에는 송신자, 수신된 메시지 내용, 현재 시간이 포함되어 있습니다.
메시지가 처리된 후에 `@SendTo` 애노테이션에 정의된 목적지로 전송됩니다. `/topic/messages` 목적지를 구독한 모든 구독자들은 메시지를 수신받을 것입니다.

## 6. 브라우저 클라이언트 생성하기
```html
<html>
    <head>
        <title>Chat WebSocket</title>
        <script src="resources/js/sockjs-0.3.4.js"></script>
        <script src="resources/js/stomp.js"></script>
        <script type="text/javascript">
            var stompClient = null;
            
            function setConnected(connected) {
                document.getElementById('connect').disabled = connected;
                document.getElementById('disconnect').disabled = !connected;
                document.getElementById('conversationDiv').style.visibility 
                  = connected ? 'visible' : 'hidden';
                document.getElementById('response').innerHTML = '';
            }
            
            function connect() {
                var socket = new SockJS('/chat');
                stompClient = Stomp.over(socket);  
                stompClient.connect({}, function(frame) {
                    setConnected(true);
                    console.log('Connected: ' + frame);
                    stompClient.subscribe('/topic/messages', function(messageOutput) {
                        showMessageOutput(JSON.parse(messageOutput.body));
                    });
                });
            }
            
            function disconnect() {
                if(stompClient != null) {
                    stompClient.disconnect();
                }
                setConnected(false);
                console.log("Disconnected");
            }
            
            function sendMessage() {
                var from = document.getElementById('from').value;
                var text = document.getElementById('text').value;
                stompClient.send("/app/chat", {}, 
                  JSON.stringify({'from':from, 'text':text}));
            }
            
            function showMessageOutput(messageOutput) {
                var response = document.getElementById('response');
                var p = document.createElement('p');
                p.style.wordWrap = 'break-word';
                p.appendChild(document.createTextNode(messageOutput.from + ": " 
                  + messageOutput.text + " (" + messageOutput.time + ")"));
                response.appendChild(p);
            }
        </script>
    </head>
    <body onload="disconnect()">
        <div>
            <div>
                <input type="text" id="from" placeholder="Choose a nickname"/>
            </div>
            <br />
            <div>
                <button id="connect" onclick="connect();">Connect</button>
                <button id="disconnect" disabled="disabled" onclick="disconnect();">
                    Disconnect
                </button>
            </div>
            <br />
            <div id="conversationDiv">
                <input type="text" id="text" placeholder="Write a message..."/>
                <button id="sendMessage" onclick="sendMessage();">Send</button>
                <p id="response"></p>
            </div>
        </div>

    </body>
</html>
```

## 7. 테스팅
웹 브라우저를 이용하여 다음 경로에 접속합니다.
```shell
http://localhost:8080
```

다음과 같이 닉네임을 입력한 다음에 connect 버튼을 누릅니다. 그리고 메시지를 입력한 다음에 send 버튼을 누르면 다음과 같이 메시지가 출력됩니다.
![[image-5.png]]

## 결론
- Spring의 WebSocket 기능을 알아보았습니다.
- 서버 사이드의 설정과 sockjs와 stomp 자바스크립트 라이브러리를 사용하여 간단한 클라이언트 사이드의 HTML 페이지를 구현함

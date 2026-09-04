널
## 섹션 소개
- Hystrix Dashboard + Turbin Server
- Micrometer
- Prometheus
- Grafana

## Micrometer 개요
### Turbin Server
마이크로서비스에 설치된 Hystix 클라이언트의 스트림을 통합
- 마이크로서비스에서 생성되는 Hystix 클라이언트 스트림 메시지를 터빈 서버로 수집

터빈 서버도 Spring Cloud Application 기반으로 개발하면 된다.
터빈 서버의 application.yaml 파일에 수집하고자 하는 애플리케이션 이름을 지정하면 된다.

예를 들어 다음 "msa-service-product-order"라는 이름은 order-service의 spring.application.name 프로퍼티의 값과 동일해야 합니다.
```yaml
# Turbin Server
turbin:
  appConfig:
    msa-service-product-order
    msa-service-product-member
    msa-service-product-status
clusterNameExpression: new String("default")
```

### Hystrix Dashbaord
Hystrix 클라이언트에서 생성하는 스트림을 시각화
- Web Dashboard

다음 그림을 보면 클라이언트가 회원 확인, 상품 주문, 배송 처리와 같은 요청을 하면 Turbin Server에서 서비스에서 발생하는 Hstrix Stream 메시지에 대해서 수집을 합니다. 수집하고 있다가 Hystrix Dashboard에서 데이터를 요구하면 Hystrix Dashboard Server에서 Turbin Server에 Hystrix Stream을 요청하여 요청을 처리합니다.
![](../imgs/Pasted%20image%2020251124115559.png)

다음 화면은 Hystrix Dashboard 예시 화면입니다. 가운데 입력창에 모니터링하고 싶은 Turbin Server 주소를 입력합니다.
![](../imgs/Pasted%20image%2020251124120104.png)
다음 화면은 예시 화면입니다. 현재 작동중인 마이크로서비스 또는 함수를 모니터링 할 수 있습니다. 예를 들어 다음의 각각의 함수에 색깔별로 성공이나 실패 여부 카운팅을 볼수 있고, Circuit Breaker가 열려 있는지 닫혀 있는지 확인할 수 있습니다.
![](../imgs/Pasted%20image%2020251124120303.png)

### Mircrometer + Monitoring

다음 표에 나온 것처럼 Spring Cloud 2025 버전 기준으로 Hystrix는 Deprecated되어 Resilience4j를 사용해야 합니다.
Hystrix Dashboard / Turbin은 Deprecated되어 Micrometer + Monitoring System을 사용해야 합니다.
![](../imgs/Pasted%20image%2020251124122255.png)


### Micrometer
Micrometer
- https://micrometer.io/
- JVM 기반의 애플리케이션의 Metrics 제공
- Spring Framework 5, Spring Boot 2부터 Spring의 Metrix 처리
- Prometheus 등의 다양한 모니터링 시스템 지원
- **Metric Data를 수집하고 모니터링 시스템으로 전달하는 라이브러리**

Timer
- 짧은 지연 시간, 이벤트의 사용 빈도를 측정
- 시계열로 이벤트의 시간, 호출 빈도 등을 제공
- `@Timed` 애노테이션 제공
	- 특정 메서드에 붙혀서 호출 횟수 등을 모니터링할 수 있음

의존성 추가
- User Service의 의존성 추가
```xml
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

User Service application.yaml 수정
- 엔드포인트 노출 목록에 prometheus와 metrics 엔드포인트를 노출시킵니다.
- `/actuator/prometheus` : Prometheus 서버가 스크랩할 수 있도록 모든 메트릭을 _Prometheus 포맷으로 일괄 출력
- `/actuator/metrics` : 애플리케이션 내부의 개별 메트릭의  목록 조회 및 단건 조회
```yaml
management:  
  endpoints:  
    web:  
      exposure:  
        include:  
          - health  
          - info  
          - refresh  
          - beans  
          - httptrace  
          - busrefresh  
          - prometheus  
          - metrics
```


microservice 수정
- `/health_check` 컨트롤러 메서드에 @Timed 애노테이션을 추가하여 매트릭 수집할 수 있게 설정한다.
- `/welcome` 컨트롤러 메서드에 @Timed 애노테이션 추가하여 매트릭 수집할 수 있게 한다.
- 두 엔드포인트는 `/actuator/metrics` 엔드포인트 요청시 목록에 출력될 것입니다.

## Micrometer 구현
user-service에 의존성 추가
```xml
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

user-service actuator 엔드포인트 추가
![](../imgs/Pasted%20image%2020251124130341.png)


user-service @Timed 애노테이션 추가
- 매트릭 데이터를 수집하고 싶은 컨트롤러 메서드에 @Timed 애노테이션을 추가합니다.
- 해당 실습에서는 `/health_check`, `/welcome` 컨트롤러 메서드에 추가함
- longTask option
	- `@Timed(longTask = true)`는 **일반적인 짧은 작업이 아니라, 실행 시간이 길 수 있는 작업(Long-running Task)을 별도로 측정하도록 Micrometer에 지시하는 옵션**

![](../imgs/Pasted%20image%2020251124130801.png)



user-service TimeAspect 스프링 빈 등록
- 동일하게 Timer metric 에서 @Timed 라는 어노테이션을 적용하려면 TimedAspect 타입의 bean을 아래처럼 등록해줘야 합니다. AOP를 적용하려면  @Aspect 와 @Around 가 사용되어야 하는데 TimedAspect 클래스 내부에 관련 코드가 있습니다.
```java
@Configuration  
public class TimedConfiguration {  
    @Bean  
    public TimedAspect timedAspect(MeterRegistry meterRegistry) {  
        return new TimedAspect(meterRegistry);  
    }  
}
```

api-gateway service 의존성 추가
- micrometer-registry-prometheus 의존성 추가
```xml
<dependency>  
    <groupId>io.micrometer</groupId>  
    <artifactId>micrometer-registry-prometheus</artifactId>  
</dependency>
```

api-gateway endpoint 추가
![](../imgs/Pasted%20image%2020251124132554.png)


order-service 의존성 추가
- micrometer-registry-prometheus 의존성 추가
- actuator 의존성이 없다면 actuator 의존성 또한 추가되어 있어야 함
![](../imgs/Pasted%20image%2020251124134157.png)

order-service actuator 엔드포인트 추가
- prometheus, metrcis 엔드포인트 추가
![](../imgs/Pasted%20image%2020251124134538.png)

health_check 테스트
![](../imgs/Pasted%20image%2020251124135309.png)

welcome 테스트
![](../imgs/Pasted%20image%2020251124135819.png)

metrics 엔드포인트 테스트
- 실행 결과를 보면 메트릭 목록 중에서 "users.status"와 "users.welcome" 메트릭이 존재하는 것을 볼수 있습니다.
![](../imgs/Pasted%20image%2020251124141251.png)


prometheus 엔드포인트 테스트
- 실행 결과를 보면 `/welcome` 엔드포인트에 대한 지표가 존재하는 것을 볼수 있습니다.
- 몇번 호출되었는지와 같은 통계 데이터가 존재합니다.
![](../imgs/Pasted%20image%2020251124141558.png)

## Prometheus와 Grafana 개요
Prometheus
- **Metrics를 수집하고 모니터링 및 알람에 사용되는 오픈소스 애플리케이션**
- 2016년부터 CNCF에서 관리되는 2번째 공식 프로젝트
	- Level DB -> Time Series Database(TSDB)
- Pull 방식의 구조와 다양한 Metric Exporter 제공
- 시계열 DB에 Metrics 저장 -> 조회 가능 (Query)
- Prometheus에서 Spring 서버로 `/actuator/prometheus` 엔드포인트를 호출하여 매트릭 데이터를 수집합니다.

Grafana
- 데이터 시각화, 모니터링 및 분석을 위한 오픈소스 애플리케이션
- 시계열 데이터를 시각화 하기 위한 대시보드 제공
- Prometheus와 Grafana를 연동하여 데이터를 시각화합니다.

### Prometheus 설치
Prometheus 다운로드
- https://prometheus.io/
- 운영체제가 mac os라면 os=darwin의 것을 선택하여 다운로드받습니다.
![](../imgs/Pasted%20image%2020251124144758.png)

Prometheus 압축 해제
```shell
curl -L -o prometheus-3.5.0.darwin-amd64.tar.gz \
https://github.com/prometheus/prometheus/releases/download/v3.5.0/prometheus-3.5.0.darwin-amd64.tar.gz

tar xvf prometheus-3.5.0.darwin-amd64.tar.gz
```
![](../imgs/Pasted%20image%2020251124145007.png)

prometheus.yaml 파일 수정
- target 지정

```shell
cd prometheus-3.5.0.darwin-amd64
vim prometheus.yml
```

user-service에서 발생하는 매트릭을 수집하기 위해서 다음과 같이 설정을 추가합니다. 그리고 api gateway를 통해서 매트릭 데이터를 수집해야 하기 때문에 api gateway 주소인 localhost:8000를 명시합니다.
![](../imgs/Pasted%20image%2020251124145230.png)

prometheus 실행
- prometheus 서버가 정상적으로 실행되면 9090 포트로 할당받아 실행됩니다.
```shell
./prometheus --config.file=prometheus.yml
```
![](../imgs/Pasted%20image%2020251124145543.png)

Prometheus Dashboard
- Expression을 입력하여 지표를 검색할 수 있습니다.
![](../imgs/Pasted%20image%2020251124145617.png)

metrics 검사 - Table
- http_server_requests_seconds_count : HTTP 요청 처리 시간(histogram)의 **카운트(요청 횟수)**
![](../imgs/Pasted%20image%2020251124145722.png)

metrics 검사 - Graph
- 해당 시간대에 호출된 API들을 시간대별로 확인할 수 있습니다.
![](../imgs/Pasted%20image%2020251124145925.png)

#### 도커 기반 Prometheus 실행
설정 파일 작성(prometheus.yml)
```yml
# my global config
global:
  scrape_interval: 15s # Set the scrape interval to every 15 seconds. Default is
  evaluation_interval: 15s # Evaluate rules every 15 seconds. The default is eve
  # scrape_timeout is set to the global default (10s).

# Alertmanager configuration
alerting:
  alertmanagers:
    - static_configs:
        - targets:
          # - alertmanager:9093

# Load rules once and periodically evaluate them according to the global 'evalua
rule_files:
# - "first_rules.yml"
# - "second_rules.yml"

# A scrape configuration containing exactly one endpoint to scrape:
# Here it's Prometheus itself.
scrape_configs:
  # The job name is added as a label `job=<job_name>` to any timeseries scraped
  - job_name: "prometheus"

      # metrics_path defaults to '/metrics'
      # scheme defaults to 'http'.

    static_configs:
      - targets: ["localhost:9090"]
        # The label name is added as a label `label_name=<label_value>` to any ti
        labels:
          app: "prometheus"
  - job_name: "user-service"
    scrape_interval: 15s
    metrics_path: '/user-service/actuator/prometheus'
    static_configs:
      - targets: ['host.docker.internal:8000']
  - job_name: "apigateway-service"
    scrape_interval: 15s
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['host.docker.internal:8000']
  - job_name: "order-service"
    scrape_interval: 15s
    metrics_path: '/order-service/actuator/prometheus'
    static_configs:
      - targets: ['host.docker.internal:8000']
```

도커 기반 컨테이너 실행
- apigateway service, user service, order service 등은 호스트 운영체제에서 실행하기 때문에 타겟의 호스트를 host.docker.internal로 설정함
```shell
docker run -it -d \
  -p 9090:9090 \
  --name my-prometheus \
  -v ./config/prometheus/prometheus.yml:/etc/prometheus/prometheus.yml \
  prom/prometheus
```

### Grafana
Grafana 다운로드 - MacOS
```shell
curl -O https://dl.grafana.com/grafana/release/12.3.0/grafana_12.3.0_19497075765_darwin_amd64.tar.gz
tar xvf grafana_12.3.0_19497075765_darwin_amd64.tar.gz

cd grafana-12.3.0
ls
```
![](../imgs/Pasted%20image%2020251124151023.png)

docker 실행
```bash
docker run -d --name=my-grafana -p 3000:3000 grafana/grafana
```

행
- localhost:3000 주소로 대시보드 접속
- id=admin, pw=admin
```shell
./bin/grafana-server web
```
![](../imgs/Pasted%20image%2020251124151301.png)

![](../imgs/Pasted%20image%2020251124151311.png)
## Prometheus와 Grafana의 연동과 Dashboard 구성
Grafana Dashboard
- JVM(Micrometer)
- Prometheus
- Spring Cloud Gateway

### Prometheus 서버와 연동
Grafana - Prometheus 연동
![](../imgs/Pasted%20image%2020251124151656.png)
![](../imgs/Pasted%20image%2020251124151710.png)

### JVM 대시보드 생성
Dashboard 생성
- Import dashboard 메뉴를 클릭합니다.
![](../imgs/Pasted%20image%2020251124152429.png)

대시보드 ID를 가져오기 위해서 "Grafana.com" 사이트에서 가져오고자 합니다.
![](../imgs/Pasted%20image%2020251124152602.png)

Grafana Dashboard 템플릿 사이트
- https://grafana.com/grafana/dashboards/?plcmt=oss-nav
- 검색창 및 필터를 이용하여 원하는 대시보드를 탐색합니다.

![](../imgs/Pasted%20image%2020251124152851.png)

대시보드 템플릿 검색
- micrometer 검색
- JVM(Micrometer) 선택
![](../imgs/Pasted%20image%2020251124153028.png)

![](../imgs/Pasted%20image%2020251124153056.png)
Dashboard ID 복사
![](../imgs/Pasted%20image%2020251124153118.png)

Import dashboard 창에서 복사한 dashboard ID를 붙여넣습니다.
![](../imgs/Pasted%20image%2020251124153142.png)

데이터 저장소로 Prometheus 선택합니다.
![](../imgs/Pasted%20image%2020251124153226.png)

dashboard import 성공화면
![](../imgs/Pasted%20image%2020251124153240.png)


### Prometheus 대시보드 생성
템플릿 주소
- https://grafana.com/grafana/dashboards/3662-prometheus-2-0-overview/

![](../imgs/Pasted%20image%2020251124153753.png)

Import dashboard
![](../imgs/Pasted%20image%2020251124153843.png)

Prometheus 데이터 소스를 선택합니다.
![](../imgs/Pasted%20image%2020251124154206.png)

대시보드 import 확인
![](../imgs/Pasted%20image%2020251124154320.png)

대시보드 생성 확인
![](../imgs/Pasted%20image%2020251124154247.png)
### Spring Cloud Gateway Dashboard 생성
![](../imgs/Pasted%20image%2020251124154443.png)


11506 ID 입력
![](../imgs/Pasted%20image%2020251124154505.png)

데이터 저장소 - Prometheus 저장소 선택
![](../imgs/Pasted%20image%2020251124154533.png)

Spring Cloud Gateway 대시보드 생성 확인
![](../imgs/Pasted%20image%2020251124154556.png)

prometheus.yml 파일 수정
![](../imgs/Pasted%20image%2020251124160336.png)

### Total Request Served 지표 수정
대시보드 지표 수정
- Total Requests Served 항목 수정
- Prometheus 자동완성에서 spring_cloud_gateway_requests_seconds_count가 검색됨
![](../imgs/Pasted%20image%2020251124160503.png)

패널을 다음과 같이 수정합니다.
![](../imgs/Pasted%20image%2020251124160613.png)

설정에 성공하면 다음과 같이 출력됩니다.
![](../imgs/Pasted%20image%2020251124165340.png)

### Total Successful Requests Served 지표 수정
![](../imgs/Pasted%20image%2020251125121452.png)

### Total Unsuccessful Request Served
![](../imgs/Pasted%20image%2020251125121510.png)

### Successful API Calls
![](../imgs/Pasted%20image%2020251125121709.png)

### Memory Used
![](../imgs/Pasted%20image%2020251125123608.png)

### CPU Usage
![](../imgs/Pasted%20image%2020251125123531.png)



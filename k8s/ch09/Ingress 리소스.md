## 9.1 Ingress란?
- Ingress 리소스는 **HTTP, HTTPS 등 네트워크 Layer 7에 대한 설정을 담당**하는 리소스입니다.
- Ingress 리소스의 가장 기본적인 역할은 **외부 HTTP 호출에 대한 트래픽을 처리**하는 역할입니다.
	- 예를 들어 부하 분산, TLS 종료, 도메인 기반 라우팅 기능.
	- TLS(Transport Layer Security) 종료는 클라이언트와 서버간 TLS 암호화 통신을 중간에서 해제하는 과정입니다. 즉, TLS 종료는 로드밸런서나 프록시 서버에서 HTTPS 트래픽을 복호화하여 내부 서버에 HTTP로 전달하는 방식입니다.
- Ingress는 쿠버네티스 **클러스터 내부 서비스에 접근 가능한 URL을 부여**해서 일반 사용자들이 쉽게 접근할 수 있도록 통로는 제공합니다.
- Ingress에는 그에 맞는 Ingress Controller가 존재하고 Ingress Controller는 Ingress에 정의된 트래픽 라우팅 규칙을 보고 라우팅을 수행합니다.

### 9.1.1 Ingress Controller란?
- Ingress 리소스는 어떤 프로그램이 아닌 트래픽 처리 규칙에 가깝습니다.
- Ingress Controller는 Ingress 리소스를 이용하여 외부의 트래픽을 그에 맞는 Service로 전달합니다.
- Ingress Controller는 쿠버네티스 내장 컨트롤러와는 다르게 **명시적으로 컨트롤러를 설치**해야 합니다.
- Ingress Controller에는 여러 종류의 구현체가 있습니다.
	- Nginx Ingress Controller
	- HAProxy
	- AWS ALB Ingress
	- Ambassador
	- Kong
	- traefik

### 9.1.2 NGINX Ingress Controller
예제에서는 NGINX Ingress Controller를 사용합니다.

### 9.1.3 NGINX Ingress Controller 설치
helm을 이용하여 NGINX Ingress Controller를 설치할 수 있습니다.

NGINX Ingress Controller를 위한 네임스페이스를 생성합니다.
```shell
kubectl create ns ctrl
```
![[image-288.png]]

nginx-ingress 패키지를 설치합니다.
```shell
helm install nginx-ingress bitnami/nginx-ingress-controller \
--version 11.6.11 \
-n ctrl
```

ctrl 네임스페이스에 있는 pod와 service를 확인합니다.
```shell
kubectl get pod -n ctrl -o wide
```
![[image-361.png]]

```shell
kubectl get svc -n ctrl
```
![[image-363.png]]
- nginx-ginress-nginx-ingress-controller svc 리소스를 보면 LoadBalaner 타입으로 설정되었고 2개의 외부 IP가 할당되어 있습니다. 또한 80, 443 포트포워딩이 설정되어 있습니다.
- 앞으로 Ingress로 들어오는 모든 트래픽은 ingress-controller service로 들어오게 됩니다.

## 9.2 Ingress 기본 사용법
### 9.2.1 도메인 주소 테스트
도메인 주소를 테스트하기 위해서는 우선은 도메인 주소가 필요합니다. 도메인 주소를 만들기 위해서는 https://sslip.io 사이트를 이용해서 만들 수 있습니다.

우선은 다음 콜아웃을 확인한 다음에 ingress-controller 서비스의 IP를 확인합니다.

> [!NOTE] Ingress Controller IP 확인 방법
> ```shell
> INGRESS_IP=$(kubectl get svc nginx-ingress-nginx-ingress-controller -n ctrl -o jsonpath="{.status.loadBalancer.ingress[0].ip}")
> echo $INGRESS_IP
> ```

![[image-364.png]]
- 위 실행 결과를 통해서 서비스의 IP 주소가 172.31.37.140인것을 알수 있습니다.

위 IP 주소를 기반으로 https://sslip.io의 서브 도메인에 IP를 입력하면 해당하는 IP를 DNS lookup 결과로 반환합니다. https://sslip.io 사이트의 DNS 규칙은 다음과 같습니다.
```shell
IP == IP.sslip.io
```

그러면 nslookup 명령어를 이용하여 도메인 네임에 대한 IP 주소를 조회해보겠습니다.
```shell
nslookup 172.31.37.140.sslip.io
```
![[image-365.png]]
- 위 실행 결과를 보면 172.31.37.140.sslip.io 도메인에 대한 IP 주소는 172.31.37.140인것을 볼수 있습니다.

Ingress 기능 중 하나인 Domain-based routing을 위해서 2차 서브 도메인 주소도 테스트해봅니다.
```shell
nslookup subdomain.172.31.37.140.sslip.io
```
![[image-366.png]]
- 실행 결과를 보면 2차 서브 도메인을 붙여도 마찬가지로 172.31.37.140가 반환됩니다.

위 예제를 통해서 **네트워크 Layer4에서는 동일한 IP(172.31.37.140)를 가지지만, Layer7에서는 서로 다른 도메인 이름으로 라우팅 규칙을 설정할 수 있습니다.**

### 9.2.2 첫 Ingress 생성
Ingress와 연결할 nginx 서비스부터 생성합니다.
```shell
kubectl run mynginx --image nginx --expose --port 80
```
![[image-367.png]]

mynginx의 service, pod를 조회해봅니다
```shell
kubectl get pod,svc mynginx
```
![[image-368.png]]
- 실행 결과를 보면 pod와 service 정상 동작하는 것을 볼수 있습니다.

Ingress 리소스를 정의합니다.
```yaml
# mynginx-ingress.yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: mynginx
spec:
  ingressClassName: nginx
  rules:
  - host: 172.31.37.140.sslip.io
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: mynginx
            port:
              number: 80
```
- annotations : annotations은 메타 정보를 저장하기 위한 property입니다. 레이블과 다르게 리소스 필터링하지 못하고 **Ingress에서는 Ingress Controller에게 메타 정보를 전달하는 목적으로 사용됩니다.**
	- kubernetes.io/ingress.class : 해당 Ingress가 NGINX Ingress Controller에 의해 처리될 것을 명시합니다.
- rules : 외부 트래픽을 어떻게 처리할지 정의합니다.
	- rules[0].host : 특정 도메인으로 들어오는 트래픽에 대해 라우팅을 정의합니다.
	- rules[0].http.paths[0].path : Ingress path를 정의합니다.
	- rules[0].http.paths[0].backend : Ingress의 트래픽을 받을 Service와 포트를 정의합니다.
- 위 정의를 통하여 사용자가 http://172.31.37.140.sslip.io/ 경로로 요청하면 Ingress 리소스에서는 위 라우팅 규칙을 기반으로 mynginx Service로 요청을 전달합니다.

위 ingress 리소스 정의서를 기반으로 쿠버네티스 클러스터에 적용합니다.
```shell
kubectl apply -f mynginx-ingress.yaml
```
![[image-369.png]]

mynginx Ingress 리소스가 잘 생성되었는지 확인해봅니다.
```shell
kubectl get ingress
```
![[image-370.png]]
- Ingress Controller가 mynginx Ingress 리소스를 읽어서 도메인 네임(172.31.37.140.sslip.io)과 경로가 일치하면 호스트 운영체제(172.31.37.140)의 mynginx svc 80번 포트로 라우팅합니다.

curl 명령어를 이용하여 mynginx 서비스로 연결해보겠습니다. 다음 명령어는 worker 노드에서 수행합니다.
![[image-371.png]]
- 사용자가 요청을 하게 되면 Ingress 리소스가 트래픽을 mynginx 서비스의 80번 포트로 전달합니다.

### 9.2.3 도메인 기반 라우팅
서브 도메인 주소를 이용해서 도메인 기반 라우팅 규칙을 정의해봅니다. 서브 도메인별 연결될 서비스를 2개 생성합니다.

apache web server 생성
```shell
kubectl run apache --image httpd --expose --port 80
```
![[image-372.png]]

nginx web server 생성
```shell
kubectl run nginx --image nginx --expose --port 80
```
![[image-373.png]]

apache 웹서버와 nginx 웹서버로 라우팅하기 위한 Ingress 리소스를 정의합니다.
```yaml
# domain-based-ingress.yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: apache-domain
spec:
  ingressClassName: nginx
  rules:
  - host: "apache.172.31.37.140.sslip.io"
    http:
      paths:
      - pathType: Prefix
        path: /
        backend:
          service:
            name: apache
            port:
              number: 80
---
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: nginx-domain
spec:
  ingressClassName: nginx
  rules:
  - host: "nginx.172.31.37.140.sslip.io"
    http:
      paths:
      - pathType: Prefix
        path: /
        backend:
          service:
            name: nginx
            port:
              number: 80
```
- 각각의 서브 도메인이 다음과 같이 라우팅 됩니다.
	- apache.172.31.37.140.sslip.io -> apache:80/
	- nginx.172.31.37.140.sslip.io -> nginx:80/

yaml 파일을 기반으로 domain-based-ingress Ingress 리소스를 생성합니다.
```shell
kubectl apply -f domain-based-ingress.yaml
```
![[image-375.png]]

curl 명령어를 이용하여 서브 도메인이 apache, nginx로 각각 요청을 날려봅니다.
```shell
curl apache.172.31.37.140.sslip.io
```
![[image-377.png]]

```shell
curl nginx.172.31.37.140.sslip.io
```
![[image-378.png]]

실행 결과를 보면 서브 도메인이 apache, nginx냐에 따라서 서로 다른 페이지를 렌더링하는 것을 볼수 있습니다. 이는 apache-domain, nginx-domain Ingress 리소스가 적절하게 사용자의 요청 경로를 기반으로 처리된것을 알수 있습니다.

위 예제의 서브 도메인 기반의 Ingress 리소스를 이용하여 IP 주소는 같아도 도메인 주소를 기준으로 서로 다른 서비스로 HTTP 트래픽을 라우팅할 수 있습니다.

### 9.2.4 Path 기반 라우팅
Ingress 리소스는 URL path를 기반으로 라우팅할 수도 있습니다. 이미 생성한 nginx, apache Service에 대해서 path를 기반으로 Ingress를 구성합니다.

URL path 기반으로 라우팅하는 Ingress 리소스를 정의합니다.
```yaml
# path-based-ingress.yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  annotations:
    nginx.ingress.kubernetes.io/rewrite-target: /
  name: apache-path
spec:
  ingressClassName: nginx
  rules:
  - host: "172.31.37.140.sslip.io"
    http:
      paths:
      - pathType: Prefix
        path: /apache
        backend:
          service:
            name: apache
            port:
              number: 80
---
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  annotations:
    nginx.ingress.kubernetes.io/rewrite-target: /
  name: nginx-path
spec:
  ingressClassName: nginx
  rules:
  - host: "172.31.37.140.sslip.io"
    http:
      paths:
      - pathType: Prefix
        path: /nginx
        backend:
          service:
            name: nginx
            port:
              number: 80
```

```shell
kubectl apply -f path-based-ingress.yaml
```
![[image-379.png]]

```shell
curl 172.31.37.140.sslip.io/apache
```
![[image-380.png]]

```shell
curl 172.31.37.140.sslip.io/nginx
```
![[image-381.png]]

실행 결과를 보면 /apache, /nginx 경로로 각각 접근했을때 서로 다른 페이지를 응답하는 것을 볼수 있습니다. 위 예제와 같이 **Ingress 리소스를 통해서 외부 트래픽을 처리할 때 매번 로드밸런서를 생성하지 않고도 애플리케이션 레이어에서 path 및 도메인을 기반으로 트래픽을 라우팅할 수 있습니다.**

## 9.3 Basic Auth 설정
Ingress 리소스에 간단한 HTTP Authentication 기능을 추가 할 수있습니다. Basic Auth 설정은 HTTP Authentication 종류 중 하나입니다. 우리는 Basic Auth 설정을 이용해서 외부 사용자에 대한 최소한의 보안 절차를 설정해봅니다.


### 9.3.1 Basic Authentication
HTTP 요청시 헤더에 Authorization 헤더를 추가해서 username과 password를 콜론으로 묶은 다음에 base64로 인코딩하여 전달이 가능합니다.

HTTP 헤더의 형식은 다음과 같습니다.
```http
Authorization: Basic $base64(user:password)
```

예를 들어 httpbin.org 사이트에 인증없이 HTTP 요청을 해보겠습니다.
```shell
curl -v https://httpbin.org/basic-auth/foo/bar
```
![[image-382.png]]
- 위 실행 결과를 보면 응답 결과가 401로 응답된 것을 볼수 있습니다. 이는 사용자가 인증되지 않은 사용자이기 때문에 서버에서 거부한 것입니다.

이번에는 Authorization 헤더에 인증 정보를 포함해서 요청해보겠습니다. 사이트는 user를 foo, password를 bar로 전달하면 인증됩니다.
```shell
curl -v -H "Authorization: Basic $(echo -n foo:bar | base64)" https://httpbin.org/basic-auth/foo/bar
```
![[image-383.png]]
- 실행 결과를 보면 사용자가 인증되어 200 응답하였습니다.

위 예제를 통하여 Ingress 리소스에 Basic Auth와 같은 설정을 해서 외부의 사용자 접근에 대한 보안을 수행할 수 있습니다.

### 9.3.2 Basic Auth 설정
Ingress에 Basic Auth 설정을 하기 위해서 사용자 인증 정보를 담고 있는 basic authentication 파일을 생성합니다. 이 파일을 생성하기 위해서는 htpasswd 패키지 툴을 설치해야 합니다.

```shell
# htpasswd binary 설치
sudo apt install -y apache2-utils
```

```shell
# user=foo, password=bar인 auth 파일 생성
htpasswd -cb auth foo bar
```
- -c : 새로운 인증 파일 auth 생성, 기존 파일이 있는 경우 덮어쓴다.
- -b : 비밀번호를 명령어에서 입력, bar가 foo 사용자의 비밀번호가 된다.
- auth : 인증 정보를 저장할 파일 이름
- foo : 사용자 이름
- bar: 비밀번호

![[image-384.png]]

이제 생성한 auth 파일을 Secret 리소스로 생성합니다.
```shell
kubectl create secret generic basic-auth --from-file=auth
```
- generic : 파일 기반으로 인증 정보가 포함된 Secret 리소스 생성하는 방식
- basic-auth : 생성할 Secret 리소스 이름
- `--from-file=auth` : 현재 디렉토리의 auth 파일을 Secret 리소스 데이터로 추가
![[image-385.png]]

Secret 리소스 생성을 확인해봅니다.
```shell
kubectl get secret basic-auth -o yaml
```
![[image-386.png]]

위 단계에서 생성한 basic-auth Secret 리소스를 Ingress 리소스에 설정해야 합니다. 다음과 같이 Ingress 리소스를 생성합니다.
```yaml
# apache-auth.yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  annotations:
    nginx.ingress.kubernetes.io/auth-type: basic
    nginx.ingress.kubernetes.io/auth-secret: basic-auth
    nginx.ingress.kubernetes.io/auth-realm: 'Authentication Required - foo'
  name: apache-auth
spec:
  ingressClassName: nginx
  rules:
  - host: "apache-auth.172.31.37.140.sslip.io"
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: apache
            port:
              number: 80
```
- `nginx.ingress.kubernetes.io/auth-type`
	- nginx ingress 리소스의 인증 방식을 설정합니다.
- `nginx.ingress.kubernetes.io/auth-secret`
	- nginx ingress 리소스에서 인증 방식을 지원하기 위해서 Secret 타입의 리소스를 설정합니다.
- `nginx.ingress.kubernetes.io/auth-realm`
	- 이 애노테이션은 Basic Authentication의 인증 요청 대화 상자에서 표시될 안내문(Realm)을 설정합니다.

위에서 작성한 정의서를 기반으로 apache-auth Ingress 리소스를 생성합니다.
```shell
kubectl apply -f apache-auth.yaml
```
![[image-387.png]]

apache-auth 서브 도메인에 접속하면 인증을 요구하는지 확인해봅니다. 우선 Authorization 헤더 없이 HTTP 요청해봅니다.
```shell
curl -I apache-auth.172.31.37.140.sslip.io
```
![[image-389.png]]
- 실행 결과를 보면 401 응답하였습니다. 이는 인증 설정이 정상적으로 설정되었다는 것을 의미합니다.

이번에는 Authorization 헤더에 인증 정보를 포함하여 요청해보겠습니다.
```shell
curl -I -H "Authorization: Basic $(echo -n foo:bar | base64)" apache-auth.172.31.37.140.sslip.io
```
![[image-390.png]]
- 실행 결과를 보면 200 응답되었습니다. 이는 Authorization에 올바른 인증 정보를 포함하였기 때문입니다.

위와 같은 예제를 통하여 Ingress 리소스에 보안 설정을 하여 외부 사용자로부터 기본적인 보안을 수행할 수 있습니다.

## 9.4 TLS 설정
Ingress 리소스에 TLS 설정을 해서 외부로부터 HTTPS 프로토콜 기반의 트래픽이 들어와도 Ingress 리소스에서 복호화하여 HTTP 트래픽으로 전달합니다. Ingress 리소스의 annotations에 "Self-signed 인증서"나 "정식 CA를 통해서 서명된 인증에 대한 정보"를 등록하면 바로 HTTPS 서비스를 제공할 수 있습니다.

### 9.4.1 Self-signed 인증서 설정
**self-signed 인증서**
- 인증서 발급기관(Certificate Authority, CA)으로부터 정식으로 인증서를 발급받지 않은, 직접 서명한 인증서를 의미합니다.
- self-signed 인증서를 사용하면 공인 발급기관으로부터 인증 받은 인증서가 아니기 때문에 웹 브라우저에 접속시 경고가 뜹니다. 하지만 간편하게 인증서 설정을 테스트할 수 있기 때문에 유용한 인증서 타입입니다.

#### Self-signed 인증서 생성하기
다음과 같이 openssl을 이용해서 self-signed 인증서를 생성해봅니다.
```shell
openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout tls.key -out tls.crt -subj "/CN=apache-tls.172.31.37.140.sslip.io"
```
- `openssl req`
	- req 명령어는 Certificate Signing Request(CSR)을 생성하는 명령어입니다.
- `-x509`
	- 해당 옵션을 사용하면 자체 서명된 인증서(self-signed)를 생성하도록 OpenSSL에 지시합니다.
	- CSR 대신 인증서를 직접 생성합니다.
- `-nodes`
	- No DES의 약자로서 **개인키를 암호화 하지 않도록 설정**합니다.
	- 일반적으로 개인키는 암호화하여 보호하지만, 이 옵션을 사용하면, 암호화 없이 개인 키를 생성합니다.
	- 개인키를 암호화하지 않으면 비밀번호 입력 없이 서버에서 사용할 수 있음
- `-days 365`
	- 인증서 유효기간을 365일로 설정합니다.
- `-newkey rsa:2048`
	- 새로운 RSA 개인 키를 생성하는 옵션입니다.
	- `rsa:2048`은 2048 bit 길이의 RSA 키를 생성하겠다는 의미입니다.
- `-keyout tls.key`
	- 개인키를 `tls.key`파일에 저장합니다.
	- 이 파일은 비공개 키로, 서버에서 SSL/TLS 연결을 설정하는데 사용됩니다.
- `-out tls.crt`
	- 생성된 자체 서명된 인증서를 `tls.crt` 파일에 저장합니다.
	- 이 인증서는 클라이언트와 서버와의 연결을 신뢰할 수 있도록 도와줍니다.
- `-subj "/CN=apache-tls.172.31.37.140.sslip.io"`
	- 인증서의 주체(Subject) 정보를 설정합니다.
	- CN(Common Name)은 인증서를 사용할 호스트 이름을 정의합니다.
	- 이 예시에서는 `apache-tls.172.31.37.140.sslip.io`를 Common Name으로 설정하여 해당 도메인 네임에 대한 인증서를 생성합니다.

![[image-391.png]]
생성 결과를 확인해보면 tls.crt, tls.key 파일이 생성된 것을 볼수 있습니다.
![[image-392.png]]
- tls.crt : 인증서
- tls.key : 개인키

Basic Auth 사용자 파일과 똑같이 self-signed 인증서를 Secret 리소스 형태로 저장합니다.
```yaml
# my-tls-certs.yaml
apiVersion: v1
kind: Secret
metadata:
  name: my-tls-certs
  namespace: default
data:
  tls.crt: $(cat tls.crt | base64 | tr -d '\n')
  tls.key: $(cat tls.key | base64 | tr -d '\n')
type: kubernetes.io/tls
```

위 yaml 파일을 기반으로 Secret 리소스를 생성합니다.
```shell
cat << EOF | kubectl apply -f -
apiVersion: v1
kind: Secret
metadata:
  name: my-tls-certs
  namespace: default
data:
  tls.crt: $(cat tls.crt | base64 | tr -d '\n')
  tls.key: $(cat tls.key | base64 | tr -d '\n')
type: kubernetes.io/tls
EOF
```
- `type: kubernetes.io.tls`
	- 기본적인 Opaque이 아닌 인증서를 보관할 kubernetes.io/tls 타입을 사용합니다.
	- TLS 인증서와 비공개 키를 저장하기 위한 특수한 Secret 타입입니다.
	- `tls.crt`와 `tls.key`라는 두개의 필드가 필수로 존재해야 하며, 각각 인증서와 비공개 키를 base64로 인코딩하여 저장해야 합니다.
	- 해당 타입은 TLS 인증서와 비공개 키를 쿠버네티스 클러스터 내에서 안전하게 관리하는데 사용됩니다.
- `cat << EOF`
	- 파일을 출력하는 명령어입니다. `<<`는 here document를 시작하는 구분자입니다. 즉, 여러 줄에 걸쳐 입력된 텍스트를 cat 명령어로 출력하겠다는 의미입니다.
	- EOF는 종료 구분자입니다.
- `<<EOF` 이후에 입력되는 모든 내용은 `EOF`가 나오기 전까지 하나의 문자열로 취급됩니다.
- `| kubectl apply -f -`
	- 파이프(`|`)는 앞선 명령어인 `cat << EOF`의 출력을 다음 명령어로 전달하는 역할을 수행합니다.
	- `kubectl apply -f -`:  `-`는 표준 입력으로 데이터를 받는 것을 의미합니다. 즉, `cat << EOF`의 출력 내용을 `kubectl apply -f` 명령어에 전달하여 Secret 리소스를 쿠버네티스 클러스터에 적용합니다.
- 명령어 전체 흐름
	- `cat << EOF`로 시작하여 여러줄의 텍스트를 입력합니다.
	- `EOF`가 나오기 전까지 입력된 모든 내용은 cat 명령어로 출력됩니다.
	- 출력된 내용은 파이프를 통해서 `kubectl apply -f -` 명령어로 전달됩니다.
	- 시크릿 리소스를 생성합니다.

위 예제에서 조금더 변형하여 다음과 같이 여러줄을 입력하고 echo 명령어로 입력으로 전달할 수 있습니다.
![[image-393.png]]

위 예제의 Secret 리소스 생성 결과는 다음과 같습니다.
![[image-394.png]]

my-tls-certs 시크릿 리소스가 정상적으로 생성되었는지 확인합니다.
```shell
kubectl get secret my-tls-certs -o yaml
```
![[image-395.png]]

#### Ingress TLS 설정하기
HTTPS Ingress 리소스를 생성합니다.
```yaml
# apache-tls.yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: apache-tls
spec:
  tls:
  - hosts:
      - apache-tls.172.31.37.140.sslip.io
    secretName: my-tls-certs
  ingressClassName: nginx
  rules:
  - host: "apache-tls.172.31.37.140.sslip.io"
    http:
      paths:
      - pathType: Prefix
        path: /
        backend:
          service:
            name: apache
            port:
              number: 80
```
- tls[0].hosts
	- tls를 적용할 도메인을 설정합니다.
- tls[0].secretName
	- 인증서와 개인키가 저장된 Secret 리소스의 이름을 입력합니다.

위 yaml 파일을 기반으로 Ingress 리소스를 생성합니다.
```shell
kubectl apply -f apache-tls.yaml
```
![[image-396.png]]

실행 결과를 확인하기 위해서 다음과 같이 실행합니다.
```shell
curl -k -i https://apache-tls.172.31.37.140.sslip.io
```
![[image-397.png]]
- `-k` : Self-signed SSL 인증서 검증을 스킵합니다.
- -i : HTTP 응답 헤더를 같이 출력합니다.

실행 결과를 보면 정상적으로 출력되는 것을 볼수 있습니다.

### 9.4.2 cert-manager를 이용한 인증서 발급 자동화
cert-manager는 쿠버네티스 X509 인증서 관리 컴포넌트입니다. 공인된 인증서 발급을 도와주고 인증서가 지속적으로 유효하도록 자동으로 인증서를 갱신합니다. Ingress 인증서의 생성, 갱신, 관리 등을 책임지는 역할을 담당합니다.

#### cert-manager 설치
cert-manager 컴포넌트들을 설치합니다.
```shell
kubectl apply -f https://github.com/cert-manager/cert-manager/releases/download/v1.17.0/cert-manager.yaml
```

cert-manager namespace에서 동작하는 pod를 조회해봅니다.
```shell
kubectl get pods --namespace cert-manager
```
![[image-410.png]]

#### Ingress 생성
인증서 발급을 도와줄 Issuer 리소스를 생성합니다. Issuer 리소느는 cert-manager에서 생성한 사용자 정의 리소스이고 Ingress의 설정 값을 참조해서 Let's encrypt 사이트에 정식 인증서를 요청하고 응답받은 인증서를 Ingress에 연결하는 일련의 작업을 자동화 해주는 리소스입니다.

Issuer 종류
- ClusterIssuer : 네임스페이스 상관없이 클러스터 레벨에서 동작하는 발급자
- Issuer : 특정 네임스페이스 안의 Ingress 만을 관리하는 발급자

전체 클러스터를 담당하는 ClusterIssuer를 생성합니다.
```yaml
# http-issuer.yaml
apiVersion: cert-manager.io/v1alpha2
kind: ClusterIssuer
metadata:
  name: http-issuer
spec:
  acme:
    email: yonghwankim.dev@gmail.com
    server: https://acme-v02.api.letsencrypt.org/directory
    privateKeySecretRef:
      name: issuer-key
    solvers:
    - http01:
        ingress:
          class: nginx
```

ClusterIssuer 리소스를 생성합니다.
```shell
kubectl apply -f http-issuer.yaml
```

cert-manager 관련 리소스 삭제
```
helm uninstall cert-manager -n cert-manager
helm list -n cert-manager

kubectl delete namespace cert-manager
kubectl get ns | grep cert-manager

kubectl delete crd certificaterequests.cert-manager.io \
  certificates.cert-manager.io \
  clusterissuers.cert-manager.io \
  issuers.cert-manager.io \
  orders.acme.cert-manager.io \
  challenges.acme.cert-manager.io
kubectl get crds | grep cert-manager

kubectl delete clusterrole cert-manager cert-manager-controller cert-manager-cainjector cert-manager-webhook
kubectl delete clusterrolebinding cert-manager cert-manager-controller cert-manager-cainjector cert-manager-webhook

kubectl delete all --all -n cert-manager
kubectl delete mutatingwebhookconfiguration cert-manager-webhook
kubectl delete validatingwebhookconfiguration cert-manager-webhook
```
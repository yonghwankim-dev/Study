- [[#8.1 helm이란?|8.1 helm이란?]]
	- [[#8.1 helm이란?#8.1.1 helm 설치|8.1.1 helm 설치]]
	- [[#8.1 helm이란?#8.1.2 chart 생성|8.1.2 chart 생성]]
	- [[#8.1 helm이란?#8.1.3 chart 설치|8.1.3 chart 설치]]
	- [[#8.1 helm이란?#8.1.4 chart 리스트 조회|8.1.4 chart 리스트 조회]]
	- [[#8.1 helm이란?#8.1.5 chart 랜더링|8.1.5 chart 랜더링]]
	- [[#8.1 helm이란?#8.1.6 chart 업그레이드|8.1.6 chart 업그레이드]]
	- [[#8.1 helm이란?#8.1.7 chart 배포 상태 확인|8.1.7 chart 배포 상태 확인]]
	- [[#8.1 helm이란?#8.1.8 chart 삭제|8.1.8 chart 삭제]]
- [[#8.2 원격 리파지토리(repository)|8.2 원격 리파지토리(repository)]]
	- [[#8.2 원격 리파지토리(repository)#8.2.1 리파지토리 추가|8.2.1 리파지토리 추가]]
	- [[#8.2 원격 리파지토리(repository)#8.2.2 리파지토리 업데이트|8.2.2 리파지토리 업데이트]]
	- [[#8.2 원격 리파지토리(repository)#8.2.3 리파지토리 조회|8.2.3 리파지토리 조회]]
	- [[#8.2 원격 리파지토리(repository)#8.2.4 리파지토리내 chart 조회|8.2.4 리파지토리내 chart 조회]]
- [[#8.3 외부 chart 설치(WordPress)|8.3 외부 chart 설치(WordPress)]]
	- [[#8.3 외부 chart 설치(WordPress)#8.3.1 chart install|8.3.1 chart install]]
- [[#WordPress chart 실행 트러블 슈팅|WordPress chart 실행 트러블 슈팅]]
	- [[#WordPress chart 실행 트러블 슈팅#wp-wordpress LoadBalancer External-IP 할당 문제 해결|wp-wordpress LoadBalancer External-IP 할당 문제 해결]]
	- [[#WordPress chart 실행 트러블 슈팅#wordpress Pod 생성 문제 해결|wordpress Pod 생성 문제 해결]]
	- [[#WordPress chart 실행 트러블 슈팅#8.3.2 chart fetch|8.3.2 chart fetch]]
- [[#8.4 마치며|8.4 마치며]]


## 8.1 helm이란?
- helm은 **쿠버네티스 패키지 매니저**입니다.
	- apt, yum, pip 패키지 매니저와 비슷하게 플랫폼의 패키지를 관리합니다.
- helm을 이용해서 원하는 소프트웨어(패키지)를 쿠버네티스에 손쉽게 설치가 가능합니다.
- helm 패키지 또한 **yaml 형식**으로 구성됩니다. 이것을 **chart**라고 합니다.
	- 즉, helm 패키지 매니저에 의해서 설치된 **yaml 형식의 패키지**를 **chart**라고 부릅니다.

#### helm chart 구조
- values.yaml과 templates/ 디렉토리로 구성되어 있습니다.
- values.yaml : 사용자가 원하는 값들을 설정하는 파일
- templates/ : 설치할 리소스 파일들이 존재하는 디렉토리입니다.
	- templates 디렉토리에는 Deployment, Service 등과 같은 쿠버네티스 리소스가 yaml 파일 형태로 들어있습니다.
	- 각 yaml 파일들은 설정값이 비워져 있고 values.yaml의 설정값들로 채워져 있습니다.

패키지 설치 시점에 values.yaml 파일의 설정값들을 이용해서 templates 디렉토리에 들어있는 yaml 파일의 구멍난 부분을 채웁니다.

### 8.1.1 helm 설치
```shell
curl -fsSL https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 | bash
helm version
```
![[image-252.png]]

### 8.1.2 chart 생성
명령어 형식
```shell
helm create {CHART_NAME}
```

**chart는 리소스들을 편리하게 배포하거나 다른 사람들과 쉽게 공유 할 수 있게 패키징한 설치파일 묶음입니다.**

mychart라는 이름을 가진 chart를 생성하고 파일들을 분석해봅니다.
```shell
helm create mychart
ls mychart
```
![[image-253.png]]
- Chart.yaml : chart 이름, 버전 정보 등 chart의 전반적인 정보를 담고 있습니다.
- charts : chart 속에 또 다른 여러 chart들을 넣을 수 있습니다. 기본적으로 비어 있음
- templates/ : chart의 뼈대가 되는 쿠버네티스 리소스가 들어있는 디렉토리입니다.
- values.yaml : 사용자가 정의하는 설정값을 가진 yaml 파일

templates 디렉토리 안에 파일들을 확인해봅니다.
```shell
ls mychart/templates
```
![[image-254.png]]

service.yaml 파일 내용을 보면 placeholder가 있는 것을 볼수 있습니다. 내용을 보면 values.yaml 파일의 설정 값들을 참조합니다.
```shell
cat mychart/templates/service.yaml
```
![[image-255.png]]

values.yaml 파일을 편집하여 service.type과 service.port를 로드밸런서와 8888로 수정합니다.
```shell
vim mychart/values.yaml
```
![[image-256.png]]


### 8.1.3 chart 설치
명령어 형식
```shell
helm install {CHART_NAME} {CHART_PATH}
```

mychart chart를 이용하여 foo라는 이름의 mychart 패키지를 설치합니다.
```shell
helm install foo ./mychart
```
![[image-257.png]]

mychart 패키지를 설치한 다음에 Service를 조회합니다.
```shell
kubectl get svc
```
![[image-258.png]]
실행 결과를 보면 foo-mychart Service의 타입이 로드밸런서이고, 포트가 8888로 설정된 것을 볼수 있습니다.

### 8.1.4 chart 리스트 조회
- 설치된 helm chart들을 조회합니다.

설치된 chart 리스트를 확인합니다.
```shell
helm list
```
![[image-259.png]]

kube-system 네임스페이스에 설치된 chart 리스트를 확인해봅니다.
```shell
helm list -n kube-system
```
![[image-260.png]]

### 8.1.5 chart 랜더링
명령어 형식
```shell
helm template {CHART_PATH}
```
- 실제 설치가 아닌 values.yaml 파일과 templates 안의 템플릿 파일들의 합쳐진 yaml 정의서 결과를 확인하고 싶다면 `template` 명령을 사용할 수 있습니다.
- helm에서는 이것을 **rendering**한다고 표현합니다.

```shell
helm template foo ./mychart > foo-output.yaml
cat foo-output.yaml
```
![[image-261.png]]

### 8.1.6 chart 업그레이드
명령어 형식
```shell
helm upgrade {CHART_NAME} {CHART_PATH}
```

이미 설치한 chart에 대해 values.yaml 값을 수정하고 업데이트할 수 있습니다.

예를 들어 Service 리소스 타입을 NodePort로 수정하고 다시 배포해보겠습니다.
```shell
vim mychart/values.yaml
```

```yaml
# values.yaml
service:
  type: NodePort
  port: 8888
```
![[image-262.png]]

foo chart를 업그레이드합니다.
```shell
helm upgrade foo ./mychart
```
![[image-263.png]]

Service 리소스를 다시 확인해봅니다.
```shell
kubectl get svc
```
![[image-264.png]]
실행 결과를 보면 foo-mychart Service의 타입이 NodePort로 변경되었습니다.

helm 리스트를 출력해봅니다.
```shell
helm list
```
![[image-265.png]]
실행 결과를 보면 REVISION의 값이 1에서 2로 변경한 것을 볼수 있습니다.

### 8.1.7 chart 배포 상태 확인
명령어 형식
```shell
helm status {CHART_NAME}
```

foo chart의 상태를 확인해봅니다.
```shell
helm status foo
```
![[image-266.png]]

### 8.1.8 chart 삭제
명령어 형식
```shell
helm delete {CHART_NAME}
```

foo chart를 삭제합니다.
```shell
helm delete foo
helm list
```
![[image-267.png]]

## 8.2 원격 리파지토리(repository)
- helm을 이용하면 외부에 구축된 애플리케이션을 쉽게 가져올 수 있음
- helm에는 chart 원격 저장소인 **리파지토리**가 있습니다.
- 리파지토리는 여러 chart를 한곳에 묶어서 보관해놓은 저장소입니다.

### 8.2.1 리파지토리 추가
예를 들어 bitnami이라는 리파지토리를 추가해보겠습니다.
```shell
helm repo add bitnami https://charts.bitnami.com/bitnami
```
![[image-283.png]]

### 8.2.2 리파지토리 업데이트
리파지토리의 인덱스 정보를 최신으로 업데이트합니다.
```shell
helm repo update
```
![[image-284.png]]

### 8.2.3 리파지토리 조회
현재 등록된 리파지토리 리스트를 확인합니다.
```shell
helm repo list
```
![[image-285.png]]

### 8.2.4 리파지토리내 chart 조회
stable 리파지토리 안에 저장된 chart 리스트를 확인합니다.
```shell
helm search repo bitnami
```
![[image-286.png]]

## 8.3 외부 chart 설치(WordPress)
### 8.3.1 chart install
- stable 리파지토리에 있는 WordPress chart를 설치합니다.
- 로컬 디렉토리에 chart가 존재하지 않아도 원격 리파지토리에 있는 chart를 바로 설치가 가능함
- 옵션
	- --version : chart 버전 설정
	- --set : values.yaml의 값을 동적으로 설정 가능
	- --namespace : chart가 설치될 namespace 설정

```shell
helm install wp bitnami/wordpress \
  --version 24.1.18 \
  --set service.ports.http=8080 \
  --namespace default
```
![[image-290.png]]


> [!NOTE] Helm 로컬 설치
> Helm 차트를 로컬로 설치한 다음에 실행시킬 수 있습니다.
> $ helm fetch bitnami/wordpress --version 24.1.18 --untar --untardir ./wp-chart
> $ helm install wp ./wp-chart/wordpress \ --set service.port=8080 \ --namespace default



Pod 상태를 조회합니다.
```shell
kubectl get pod
```
![[image-273.png]]

Service 상태를 조회합니다.
```shell
kubectl get svc
```
![[image-291.png]]

```shell
vim ./mychart/values.yaml
```


```shell
curl localhost:8080
```

## WordPress chart 실행 트러블 슈팅
### wp-wordpress LoadBalancer External-IP 할당 문제 해결
다음과 같이 명령어를 실행하여 WordPress chart를 실행시킵니다.
```shell
helm install wp bitnami/wordpress \
  --version 24.1.18 \
  --set service.ports.http=8080 \
  --namespace default
```

WordPress chart를 설치한 다음에 svc 리소스를 조회했습니다.
```shell
kubectl get svc
```
![[image-328.png]]
- 실행 결과를 보면 wp-wordpress LoadBalancer의 external-ip가 계속 pending 상태를 유지하고 있습니다.
- 위와 같이 pending 상태를 유지하며 외부 ip 주소를 할당하지 못하는 이유는 **LoadBalancer 타입의 리소스 중에서 이미 443 포트를 매핑중이기 때문에 443 포트가 충돌이 발생**했기 때문입니다.

현재 LoadBalancer 타입의 리소스 중에서 443 포트를 사용중인 서비스를 조회합니다.
```shell
kubectl get svc -A | grep 443
```
![[image-329.png]]
- 실행 결과를 보면 kube-system namespace에 있는 **traefik LoadBalancer svc 리소스가 이미 443 포트를 사용**하고 있는 것을 볼수 있습니다.

**traefik 리소스는 k3s의 기본 Ingress Controller이며, 현재 443 포트를 32666/TCP로 사용하고 있습니다.** 만약 wordpress chart 실행시 wp-wordpress 서비스가 같은 포트인 443 포트를 사용하게 된다면 충돌이 발생합니다.
kubeernets, metrics-server도 443 포트를 사용하고 있습니다. 그러나 두 리소스의 타입은 ClusterIP 타입이기 때문에 LoadBalancer와 직접 충돌하지 않습니다. 그래서 사용이 가능합니다.

위와 같은 문제를 해결하기 위해서는 wordpress chart 설치시 다음과 같은 옵션을 통하여 https 포트를 443 포트 대신 다른 포트번호로 설정하면 문제가 해결됩니다.
재시작 전에 helm 패키지 매니저를 이용하여 wp chart를 삭제하고 재시작해보겠습니다.
```shell
helm delete wp
kubectl delete pvc --all
helm install wp bitnami/wordpress \
  --version 24.1.18 \
  --set service.ports.http=8080 \
  --set service.ports.https=444 \
  --namespace default
```

wp chart를 재시작한 후에 서비스를 다시 확인해보겠습니다.
```shell
kubectl get svc
```
![[image-330.png]]
- 실행 결과를 보면 wp-wordpress LoadBalancer 리소스의 external-ip가 할당된 것을 볼수 있습니다.

### wordpress Pod 생성 문제 해결
이번에는 wp 차트의 pod 리소스들이 제대로 생성되었는지 확인해보겠습니다.
```shell
kubectl get pods
```
![[image-331.png]]
- 실행 결과를 보면 wp-wordpress-...vp6 Pod의 READY 컬럼을 보면 0/1로써 생성되지 않고 재시작을 수행하는 것을 볼수 있습니다.

wordpress Pod가 생성되지 않고 재시작하는 이유를 분석하기 위해서 로그를 조회하겠습니다.
```shell
kubectl logs wp-wordpress-7f546669f8-lqvb6
```
![[image-332.png]]

로그 조회 결과를 보면 중간에 다음과 같은 로그를 확인할 수 있습니다. 다음 로그를 통해서 wordpress Pod가 wp-mariadb 도메인 네임에 대한 IP 주소를 조회할 수 없어서, mariadb 데이터베이스 서버에 접속할 수 없고, wordpress Pod가 생성되지 않는 것을 볼수 있습니다.
```
wordpress 03:21:08.39 WARN  ==> Hostname wp-mariadb could not be resolved, this could lead to connection issues
```

쿠버네티스 클러스터에서 동작하는 Pod는 어떤 도메인 네임에 대한 IP 주소를 질의하기 위해서는 kube-system namespace에 존재하는 kube-dns svc를 통해서 질의합니다. 도메인 네임에 대한 IP 주소 질의를 받은 kube-dns svc는 해당 서비스에서 수행하는 coredns Pod에게 전달하여 IP 주소를 질의하고 응답합니다.

coredns가 정상적으로 wp-mariadb 도메인의 IP 주소를 응답하는지 확인하기 위해서 busybox를 이용해서 질의해보겠습니다.
```shell
kubectl run busybox --rm -it --image=busybox --restart=Never -- nslookup wp-mariadb
```
![[image-333.png]]
- 실행 결과를 보면 wp-mariadb에 대한 IP 주소를 찾지 못하는 것을 볼수 있습니다.

네임서버에 대한 설정이 되어 있는지 설정파일을 확인해보겠습니다.
```
kubectl run busybox --rm -it --image=busybox --restart=Never -- cat /etc/resolv.conf
```
![[image-334.png]]
- 실행 결과를 보면 nameserver에 대한 IP 주소로 10.43.0.10이 설정된 것을 볼수 있습니다.
- 위 결과에서 10.43.0.10 IP 주소는 kube-system namespace에서 동작하는 kube-dns svc 리소스의 클러스터 IP 주소입니다.

위 실행 결과를 통해서 kube-dns svc로 요청이 들어가지만 coredns Pod가 정상적으로 동작하지 않다는 것을 추론할 수 있습니다. 실제로 coredns Pod가 동작하지 않는지 이벤트 내역을 확인해보겠습니다.
```shell
kubectl get pods -n kube-system
kubectl describe pod coredns-6f6b97bf7b-4zcrb -n kube-system
```
![[image-335.png]]
- 위 실행 결과를 보면 coredns의 이벤트 내역에서 `Liveness probe`와 `Readiness probe`의 체크 결과가 Unhealthy인것을 볼수 있습니다.

coredns Pod가 어느 노드에서 수행하는지 확인해보겠습니다.
```shell
kubectl get pods -o wide -n kube-system
```
![[image-336.png]]
- 실행 결과를 보면 coredns Pod가 master 노드에서 수행하는 것을 볼수 있습니다.

위 실행 결과를 보면 kube-system namespace 안에서 동작하는 coredns Pod를 포함한 몇몇 Pod는 master 노드에서 동작하는 것을 볼수 있습니다.
쿠버네티스에서는 기본적으로 master 노드에는 워크로드(일반 애플리케이션) Pod를 배포하지 않는 것을 권장하고 있습니다. 이는 클러스터의 안정성과 성능을 보장하기 위해서입니다.

**master 노드에 Pod를 스케줄링 하는것을 권장하지 않는 이유**
1. master 노드는 클러스터 관리 역할을 수행해야 합니다.
	- master 노드에는 클러스터를 관리하는 핵심 컴포넌트인 kube-apiserver, kube-controller-manager, kube-scheduler, etcd 등이 있습니다.
	- 이러한 관리 서비스는 클러스터의 안정성에 필수적이므로 리소스를 안정적으로 사용할 수 있어야 합니다.
	- 만약 master 노드에서 애플리케이션이 실행되면, 관리 서비스가 성능 저하나 리소스 부족을 겪을 수 있습니다.
2. master 노드는 리소스 경합 문제를 겪을 수 있습니다.
	- master 노드는 클러스터를 관리하는 중요 역할을 하기 때문에 CPU, 메모리, 네트워크 대역폭을 안정적으로 유지해야 합니다.
	- master 노드에 애플리케이션이 배포되면, 자원 경합이 발생하기 때문에 kubernetes 서비스가 느려지거나 충돌이 발생할 수 있습니다.
	- 예를 들어 etcd 성능이 저하되면, 클러스터 전체의 API 응답 속도가 느려지고 장애가 발생할 . 수있습니다. kube-apiserver의 응답이 느려지면, kubectl 명령어, 자동 스케일링, 상태 업데이트가 지연될 수 있습니다.
3. 고가용성 및 장애 문제를 겪을 수 있습니다.
	- master 노드는 고가용성 구성을 해야하며, 장애가 발생하면 빠르게 복구할 수 있어야 합니다. 그러나 애플리케이션이 master 노드에 배포되면, 장애 발생시 추가적인 복구 작업이 필요합니다.
4. 보안 문제가 발생할 수 있습니다.
	- master 노드는 클러스터의 핵심 API와 데이터(etcd)를 관리하기 때문에 보안적으로 중요합니다.
	- 하지만 일반적인 애플리케이션이 master 노드에 배포되면, 악성 코드 또는 보안 취약점을 통해서 master 노드가 공격받을 가능성이 높아집니다.
	- 예를 들어, master 노드에 보안 취약한 컨테이너가 실행되면, etcd에 접근할 가능성이 높습니다. 또는 Pod가 높은 권한을 요구하면, master 노드의 `kube-apiserver` 또는 `scheduler`를 조작할 위험이 있습니다. 마지막으로 master 노드에 트래픽이 몰리면, 중요 서비스(API, 스케줄러 등)가 디도스(DDos) 공격을 받을 가능성이 있습니다.

위와 같이 master 노드에 Pod를 스케줄링하지 않기 위해서 Taint 설정을 통해서 스케줄링하지 않을 . 수있습니다.

우선은 master 노드에 현재 Taint 설정이 어떤지 확인해봅니다.
```shell
kubectl describe node master | grep Taint
```
![[image-337.png]]
- 실행 결과를 보면 master 노드에 아무것도 설정되어 있지 않습니다.

다음 명령어를 이용하여 master 노드에 Pod가 스케줄링되지 않도록 Taint를 설정합니다.
```shell
kubectl taint nodes master node-role.kubernetes.io/master=:NoSchedule
```
![[image-338.png]]

다시 master 노드에 Taint를 캐치하여 Taint가 잘 설정되어 있는지 확인해봅니다.
```shell
kubectl describe node master | grep Taint
```
![[image-339.png]]
- 실행 결과를 보면 정상적으로 NoSchedule이 설정된 것을 볼수 있습니다.

kube-system namespace에 있는 Pod들을 제거하고 다시 스케줄링되도록 해보겠습니다.
```shell
kubectl delete pod --all -n kube-system
```
![[image-340.png]]

kube-system에 있는 pod들이 worker 노드에 스케줄링되었는지 확인해봅니다.
```shell
kubectl get pods -n kube-system -o wide
```
![[image-341.png]]
- 실행 결과를 보면 예상과 다르게 master 노드에 스케줄링 된것을 볼수 있습니다.

master 노드에서 Taint 설정을 했음에도 master 노드에 Pod가 스케줄링 된 이유는 **kube-system namespace에 있는 일부 Pod는 `toleration` 이 설정되어 있어서 master 노드에서 실행**할 수 있습니다.

위 설명이 맞는지 확인하기 위해서 master 노드에서 실행되는 Pod의 tolerations 설정을 확인해보겠습니다.
```
kubectl get pod coredns-6f6b97bf7b-dk8gq -n kube-system -o yaml | grep -A 5 tolerations
```
![[image-342.png]]
- key: CriticalAddonsOnly
	- 해당 키를 가진 toleration가 존재하면 해당 노드에서 실행할 수 있습니다.
- key: `node-role.kubernetes.io/control-plane` 인 노드에서 실행할 수 있도록 허용합니다.
	- effect: NoSchedule은 해당 taint가 있는 노드에는 기본적으로 스케줄링되지 않지만, 이 tolerations이 있으면 실행 가능하다는 의미입니다.


```shell
kubectl describe node master -o yaml
```
![[image-343.png]]
- 실행 결과를 보면 master 노드의 Roles 부분에서 control-plane 역할이 할당되어 있습니다.
- 그리고 레이블 설정에서 `node-role,kubernetes.io/control-plane=true`로 설정되어 있습니다.
- 그래서 `node-role.kubernetes.io/control-plane` 레이블이 true로 설정되어 있기 때문에 master 노드에서 Pod가 실행될 수 있습니다.

우선은 coredns와 같은 Pod들의 deployment를 수정하겠습니다. (사전에 wp chart를 삭제하고 시작하였습니다.)
```shell
kubectl edit deployment coredns -n kube-system
```
![[image-344.png]]
위 설정에서 key가 `node-role.kubernetes.io/control-plane`, `node-role.kubernetes.io/master` 키를 삭제합니다.

키를 삭제한 다음에 coredns가 어느 노드에서 시작되는지 확인합니다.
```shell
kubectl get pods -n kube-system -o wide
```
![[image-354.png]]
- 실행 결과를 보면 coredns가 worker 노드에서 수행하는 것을 볼수 잇습니다.

위와 같이 실행한 상태에서 다시 wordpress chart를 설치해봅니다. 여기서 nodeSelector 옵션을 설정하여 worker 노드에서 스케줄링 되도록 합니다.
```shell
helm install wp bitnami/wordpress \
  --version 24.1.18 \
  --namespace default \
  --set service.ports.http=8080 \
  --set service.ports.https=444 \
  --set nodeSelector."kubernetes\.io/hostname"=worker \
  --set mariadb.primary.nodeSelector."kubernetes\.io/hostname"=worker
```

wordpress chart의 pod들이 정상적으로 생성되었는지 확인합니다.
```shell
kubectl get pods
```
![[image-357.png]]
- 실행 결과를 보면 정상적으로 생성된 것을 볼수 있습니다.

wordpess 사이트로 정상적으로 들어갈수 있는지 확인해봅니다. 그러기 위해서는 worker 노드의 public ipv4 주소를 이용해서 8080포트로 들어가서 확인합니다.
![[image-358.png]]
- 실행 결과를 보면 정상적으로 들어간 것을 볼수 있습니다.


트러블 슈팅 정리
- wp-wordpress svc의 external-ip가 할당되지 않은 이유는 kube-system namespace의 traefik svc가 이미 443 포트를 사용하고 있기 때문입니다.
- wordpress Pod가 wp-mariadb 도메인 네임의 IP를 찾지 못한 이유는 coredns가 제대로 작동하지 않았기 때문입니다. coredns Pod를 master 노드가 아닌 worker 노드에서 스케줄링하도록 해야 합니다.
- wordpress chart에서 실행되는 pod들은 worker 노드에서 수행해야 정상적으로 수행됩니다.

References
- https://stackoverflow.com/questions/65772538/k8s-why-coredns-cant-run-on-master-node
---

### 8.3.2 chart fetch
- 리파지토리의 chart를 원격에서 바로 설치하는 것이 아닌 로컬 디렉토리로 다운로드해서 설치하는 방식입니다.
- 사용자가 세부적으로 설정값들을 수정 후 애플리케이션을 설치하고 싶을때 fetch를 사용합니다.

```shell
helm fetch --untar bitnami/wordpress --version 24.1.18
```
![[image-275.png]]

```shell
vim wordpress/values.yaml
```
- 사용자 입맛에 따라 세부 설정값 변경

wordpress chart 설치
```shell
helm install wp-fetch ./wordpress
```

worker 노드에서 다음과 같이 요청해봅니다.
```shell
curl localhost:80/
```
![[image-359.png]]

브라우저를 이용하여 다음과 같이 접속할 수 있습니다. 여기서 public ipv4주소는 워커 노드의 public ipv4입니다.
![[image-360.png]]


## 8.4 마치며
- helm 패키지 매니저는 쿠버네티스트를 사용하는데 있어서 강력함을 더해주는 툴입니다.
- 복잡한 애플리케이션도 손쉽게 구축할 수 있도록 지원함
- 다음 장은 쿠버네티스 애플리케이션 계층 네트워크 설정을 담당하는 Ingress 리소스에 대해서 살펴봅니다.

#### Clean up
```shell
helm delete wp
helm delete wp-fetch
kubectl delete pvc data-wp-mariadb-0 data-wp-fetch-mariadb-0
```

- [[#5.1 Pod 소개|5.1 Pod 소개]]
	- [[#5.1 Pod 소개#5.1.1 Pod 특징|5.1.1 Pod 특징]]
- [[#5.2 라벨링 시스템|5.2 라벨링 시스템]]
	- [[#5.2 라벨링 시스템#5.2.1 라벨 정보 부여|5.2.1 라벨 정보 부여]]
	- [[#5.2 라벨링 시스템#5.2.2 라벨 정보 확인|5.2.2 라벨 정보 확인]]
	- [[#5.2 라벨링 시스템#5.2.3 라벨을 이용한 조건 필터링|5.2.3 라벨을 이용한 조건 필터링]]
	- [[#5.2 라벨링 시스템#5.2.4 nodeSelector를 이용한 노드 선택|5.2.4 nodeSelector를 이용한 노드 선택]]
- [[#5.3 실행 명령 및 파라미터 지정|5.3 실행 명령 및 파라미터 지정]]
- [[#5.4 환경변수 설정|5.4 환경변수 설정]]
- [[#5.5 볼륨 연결|5.5 볼륨 연결]]
- [[#5.6 리소스 관리|5.6 리소스 관리]]
	- [[#5.6 리소스 관리#5.6.1 requests|5.6.1 requests]]
	- [[#5.6 리소스 관리#5.6.2 limits|5.6.2 limits]]
- [[#5.7 상태 확인|5.7 상태 확인]]
	- [[#5.7 상태 확인#5.7.1 livenessProbe|5.7.1 livenessProbe]]
	- [[#5.7 상태 확인#5.7.2 readinessProbe|5.7.2 readinessProbe]]
- [[#5.8 2개 컨테이너 실행|5.8 2개 컨테이너 실행]]
- [[#5.9 초기화 컨테이너|5.9 초기화 컨테이너]]
- [[#5.10 Config 설정|5.10 Config 설정]]
	- [[#5.10 Config 설정#5.10.1 ConfigMap 리소스 생성|5.10.1 ConfigMap 리소스 생성]]
	- [[#5.10 Config 설정#5.10.2 ConfigMap 활용|5.10.2 ConfigMap 활용]]
- [[#5.11 민감 데이터 관리|5.11 민감 데이터 관리]]
	- [[#5.11 민감 데이터 관리#5.11.1 Secret 리소스 생성|5.11.1 Secret 리소스 생성]]
	- [[#5.11 민감 데이터 관리#5.11.2 Secret 활용|5.11.2 Secret 활용]]
- [[#5.12 메타데이터 전달|5.12 메타데이터 전달]]
	- [[#5.12 메타데이터 전달#5.12.1 볼륨 연결|5.12.1 볼륨 연결]]
	- [[#5.12 메타데이터 전달#5.12.2 환경변수 - env|5.12.2 환경변수 - env]]
- [[#5.13 마치며|5.13 마치며]]


## 5.1 Pod 소개
쿠버네티스 클러스터에서 Pod는 최소 실행 단위입니다. 도커 같은 경우에는 컨테이너(Container)가 이에 해당됩니다. 다만 Pod에는 1개 이상의 컨테이너가 배치될 수 있다는 점이 약간은 다른 점입니다.

### 5.1.1 Pod 특징
Pod의 특징은 다음과 같습니다.

#### 1개 이상의 컨테이너 실행
- 한개의 Pod의 1개 이상의 컨테이너 실행이 가능합니다. 
- 일반적으로 기본적으로 1개, 상황에 따라서 2개, 많게는 3개까지 정도만 실행합니다.

#### 동일 노드에 할당
- Pod 안에 실행되는 컨테이너들은 반드시 동일한 노드(Node)에 할당됩니다. 
- Pod 안에 컨테이너들은 동일한 생명 주기를 갖습니다. 그래서 Pod 삭제시 Pod 안의 모든 컨테이너들은 전부 같이 삭제됩니다.

#### 고유 Pod IP
- Pod 리소스는 노드 IP와는 별개로 클러스터 안에서 접근 가능한 고유 IP를 할당받습니다.
- 쿠버네티스에서는 다른 노드에 위치한 Pod여도 NAT 통신없이 Pod IP를 이용해서 접근할 수 있습니다.


> [!NOTE] NAT(Network Address Translation)
> 여러개의 내부 IP를 한개의 외부 IP와 연결하는 기술입니다. 대표적으로 포트 포워딩이 있습니다.

#### IP 공유
- Pod 안에 있는 컨테이너들은 서로 IP를 공유합니다.
- Pod 안의 컨테이너들은 localhost를 통해서 서로 네트워크 접근이 가능하며 포트를 이용해서 구분합니다.
예를 들어서  다음과 같이 mynginx pod는 10.42.2.9 IP 주소를 가지고 있습니다. 해당 Pod안에 여러개의 컨테이너들이 실행되는 경우 10.42.2.9:8080, 10.42.2.9:8081과 같이 IP 주소를 공유하고 포트로 구분합니다.
![[image-92.png]]

#### volume 공유
Pod 안의 컨테이너들은 동일한 볼륨과 연결이 가능하여 파일 시스템을 기반으로 서로 파일을 주고 받을 수 있습니다. 즉, 하나의 Pod 안에 2개의 컨테이너가 실행중이고 하나의 볼륨과 연결되어 있다고 가정합니다. 그러면 1개의 컨테이너가 볼륨으로 연결된 경로에 파일 시스템으로 파일을 저장하면 또다른 컨테이너가 동일한 볼륨으로 설정한 경로를 파일 시스템으로 접근해서 사용할 수 있습니다.

실습으로 Pod를 실제로 생성하지 않고 `--dry-run`, `-o yaml` 옵션을 조합해서 템플릿 파일을 생성해보겠습니다.
```shell
kubectl run mynginx --image nginx --dry-run=client -o yaml > mynginx.yaml
```
![[image-94.png]]
![[image-95.png]]
Pod를 구성하기 위한 최소 property 설명은 다음과 같습니다.
- apiVersion
	- 모든 리소스에는 apiVersion이 정의되어 있음
	- 리소스 이름이 동일한 경우 충돌을 피하기 위해서 리소스의 버전을 정의한 것입니다.
- kind
	- 리소스 타입 정의
- metadata
	- 리소스 메타 정보 정의
	- `labels` : 리소스 라벨 정보 표기
	- `name` : 리소스 이름 표기(mynginx)
- spec
	- 리소스 스펙 정의
	- `containers` : 1개 이상의 컨테이너 정의
		- name : 컨테이너 이름 표기
		- image : 컨테이너 이미지 주소 지정

이제 mynginx.yaml 파일을 기반으로 Pod를 생성해봅니다.
```shell
kubectl apply -f mynginx.yaml
```
![[image-96.png]]

#### Pod 생성 과정
1. 사용자가 kubectl 명령어를 이용해서 Pod 정의서를 쿠버네티스 마스터 노드에게 전달
2. 마스터 노드는 **yaml 정의와 유효성 체크** 후에 특정 워커 노드에 사용자의 요청에 따라서 컨테이너를 실행하도록 명령을 내림
3. 워커노드는 요구사항에 맞게 실제 컨테이너를 노드에서 실행

## 5.2 라벨링 시스템
라벨링은 단순히 **key,value 형태의 문자열**이고 특정 Pod에 key,value 형태로 라벨링할 수 있습니다. 라벨링은 **특정 리소스 참조**를 위해서 사용하기도 하고, **Pod에 트래픽 전달**할 때도 라벨링 시스템을 사용합니다.

### 5.2.1 라벨 정보 부여
명령어 형식
```shell
kubectl label pod {POD_NAME} {KEY}={VALUE} 
```

mynginx Pod에 hello=world 라벨을 추가해봅니다.
```shell
kubectl label pod mynginx hello=world
```
![[image-97.png]]

mynginx Pod에 라벨링이 추가되었는지 확인 해봅니다.
```shell
kubectl get pod mynginx -o yaml
```
![[image-98.png]]
실행 결과를 보면 정상적으로 hello: world가 추가된 것을 볼수 있습니다.

#### 선언형 명령을 이용하는 방법
Pod yaml 파일 작성시 직접 metadata property에 라벨을 추가 할 수 있습니다.

```shell
cat << EOF | kubectl apply -f -
apiVersion: v1
kind: Pod
metadata:
  # hello=world 라벨 저장
  labels:
    hello: world
    run: mynginx
  name: mynginx
spec:
  containers:
  - image: nginx
    name: mynginx
EOF
```
![[image-99.png]]

```shell
kubectl get pod mynginx -o yaml
```
![[image-100.png]]
실행 결과를 보면 metadata.labels 설정에 hello: world가 추가되어 있습니다.


> [!NOTE] 기본 라벨
> kubectl run {NAME} 명령어를 수행하면 자동으로 run={NAME} 라벨이 추가됩니다.
> 예를 들어 `kubectl run hello --image nginx` 라는 명령어로 pod를 생성하면 해당 pod에는 run=hello 라벨이 기본적으로 생성됩니다.


### 5.2.2 라벨 정보 확인
Pod에 설정된 특정 라벨을 확인하기 위해서 `-L` 옵션을 사용합니다. 예를 들어 run이라는 키값에 대한 값을 표시합니다.
```shell
kubectl get pod mynginx -L run
```
![[image-101.png]]
위 실행 결과를 보면 run=mynginx 라벨인 것을 볼수 있습니다.

만약에 해당 pod의 전체 라벨을 조회하고 싶다면 `--show-labels` 옵션을 사용하면 됩니다.
```shell
kubectl get pod mynginx --show-labels
```
![[image-102.png]]
실행 결과를 보면 `hello=world`, `run=myngnix` 라벨이 설정되어 있습니다.

### 5.2.3 라벨을 이용한 조건 필터링
특정 라벨을 가진 Pod만 필터링해서 조회하고 싶다면 `-l` 옵션을 사용하면 됩니다.

예를 들어 yournginx Pod를 생성한 다음에 특정한 라벨을 가진 Pod들을 조회해보겠습니다. (현재 myngnix Pod가 실행중인 상태입니다.)
```shell
kubectl run yournginx --image nginx
```
![[image-103.png]]

라벨 key가 run인 Pod들을 조회해보겠습니다.
```shell
kubectl get pod -l run
```
![[image-104.png]]
실행 결과를 보면 run key를 가진 Pod가 2개인 것을 볼수 있습니다.

이번에는 라벨이 `run=mynginx`인 Pod를 조회해보겠습니다.
```shell
kubectl get pod -l run=mynginx
```
![[image-105.png]]
실행 결과를 보면 mynginx Pod 한개인 것을 볼수 있습니다.

### 5.2.4 nodeSelector를 이용한 노드 선택
라벨링 시스템을 이용해서 Pod가 특정 노드에 할당되도록 스케줄링할 수 있습니다.

예를 들어 A 노드는 디스크가 SSD로 설정되어 있고 B 노드는 디스크가 HDD로 설정되어 있습니다. 특정 Pod에 한정해서 SSD 디스크를 사용해야 한다고 가정합니다. 이러한 경우 쿠버네티스가 Pod를 SSD 디스크를 가진 노드에 할당하기 위해서 nodeSelector를 사용할 수 있습니다.

위 문제를 해결 하기 위해서 우선은 master 노드에는 `disktype=ssd` 라벨링을 추가하고 worker 노드에는 `disktype=hdd` 라벨을 추가합니다.
```shell
kubectl label node master disktype=ssd
kubectl label node worker disktype=hdd
```
![[image-106.png]]

라벨링이 노드에 제대로 추가되었는지 확인해봅니다.
```shell
kubectl get node --show-labels | grep disktype
```
![[image-107.png]]
위 실행 결과를 보면 라벨링이 제대로 추가된 것을 볼수 있습니다.

이제 실행하고자 하는 Pod yaml 정의서에 **nodeSelector** property를 추가합니다.
```yaml
# node-selector.yaml
apiVersion: v1
kind: Pod
metadata:
  name: node-selector
spec:
  containers:
  - name: nginx
    image: nginx
  # 특정 노드 라벨 선택
  nodeSelector:
    disktype: ssd
```
- node-selector Pod 실행시 노드의 라벨이 disktype=ssd인 노드 중에서 할당합니다.
- 만약 같은 라벨을 가진 노드가 2개 이상인 경우 쿠버네티스 스케줄링이 최적의 노드를 선택해서 할당합니다.

```shell
kubectl apply -f node-selector.yaml
```
![[image-108.png]]

node-selector Pod가 어느 노드에서 수행되는지 확인합니다.
```shell
kubectl get pod node-selector -o wide
```
![[image-109.png]]
위 실행 결과를 보면 node-selector Pod가 master 노드에서 실행되고 있는것을 볼 수 있습니다.

이번에는 node-selector.yaml 파일을 수정해서 node-selector Pod가 디스크 타입이 HDD인 노드에서 실행하도록 설정을 수정해보겠습니다.
```yaml
# node-selector.yaml
apiVersion: v1
kind: Pod
metadata:
  name: node-selector
spec:
  containers:
  - name: nginx
    image: nginx
  nodeSelector:
    disktype: hdd # 기존 ssd에서 hdd로 변경
```

기존 node-selector Pod를 삭제한 다음에 다시 적용해보겠습니다.
```shell
kubectl delete pod node-selector
```
![[image-110.png]]

새로 라벨링(disktype=hdd)한 Pod를 생성합니다.
```shell
kubectl apply -f node-selector.yaml
```
![[image-111.png]]

새로 생성한 node-selecotr Pod가 어느 노드에서 실행중인지 확인합니다.
```shell
kubectl get pod node-selector -o wide
```
![[image-112.png]]
실행 결과를 보면 node-selector Pod가 worker 노드에서 실행중인 것을 볼수 있습니다.

## 5.3 실행 명령 및 파라미터 지정
- Pod 생성시 실행 명령어와 파라미터를 전달할 수 있습니다.
- 지금까지 nginx 컨테이너 실행시 기본 실행 명령을 사용하고 있었습니다.
- nginx 컨테이너 실행시 사용자가 원하는 명령어를 실행할 수 있습니다.

```shell
vim cmd.yaml
```

```yaml
# cmd.yaml
apiVersion: v1
kind: Pod
metadata:
  name: cmd
spec:
  restartPolicy: OnFailure
  containers:
  - name: nginx
    image: nginx
    # 실행 명령
    command: ["/bin/echo"]
    # 파라미터
    args: ["hello"]
```
- command
	- 컨테이너 시작시 실행할 명령어를 정의합니다.
	- 도커의 ENTRYPOINT에 대응되는 property입니다.
- args
	- command에 정의한 명령어에 전달할 파라미터를 지정합니다.
	- 도커의 CMD에 대응되는 property입니다.
- restartPolicy
	- Always : Pod 종료시 항상 재시작을 시도합니다. (default)
	- Never : 재시작을 시도하지 않습니다.
	- OnFailure: 실패 시에만 재시작을 시도합니다.

cmd.yaml 파일을 기반으로 Pod를 생성합니다.
```shell
kubectl apply -f cmd.yaml
```

cmd Pod의 로그를 확인합니다.
```shell
kubectl logs -f cmd
```
![[image-113.png]]
실행 결과를 보면 hello 문자열이 출력된 것을 볼수 있습니다.

## 5.4 환경변수 설정
Pod에 환경변수를 설정하는 방법에 대해서 알아봅니다. `env` property를 활용하여 환경 변수를 설정합니다.
```shell
vim env.yaml
```

```yaml
# env.yaml
apiVersion: v1
kind: Pod
metadata:
  name: env
spec:
  containers:
  - name: nginx
    image: nginx
    env:
    - name: hello
      value: "world!"
```
- nginx 컨테이너에 hello="world!" 환경 변수를 설정합니다.

env Pod를 생성한 다음에 env Pod에 환경 변수 hello를 확인해봅니다.
```shell
kubectl apply -f env.yaml
kubectl exec env -- printenv | grep hello
```
![[image-114.png]]
실행 결과를 보면 env Pod에 hello 환경 변수가 설정된 것을 볼수 있습니다.

## 5.5 볼륨 연결
Pod 내부의 스토리지는 Pod가 삭제되면 같이 삭제됩니다. 그래서 데이터 영속성을 위해서는 Pod를 볼륨과 연결해야 합니다. 예제에서는 기본이 되는 host Volume을 사용합니다. host Volume은 호스트 서버의 볼륨 공간에 Pod가 데이터를 저장하는 것을 의미합니다.

```shell
vim volume.yaml
```

```yaml
# volume.yaml
apiVersion: v1
kind: Pod
metadata:
  name: volume
spec:
  containers:
  - name: nginx
    image: nginx
    # 컨테이너 내부의 연결 위치 지정
    volumeMounts:
    - mountPath: /container-volume
      name: my-volume
  volumes:
  - name: my-volume
    hostPath:
      path: /home
```
- volumeMounts
	- 컨테이너 내부에서 사용될 볼륨을 설정합니다.
	- mountPath: 컨테이너 내부에 볼륨이 연결된 경로를 설정합니다.(/container-volume)
	- name: volumeMounts와 volumes을 연결하는 식별자(my-volume)
- volumes
	- Pod에서 사용할 volume을 설정합니다.
	- name: volumeMounts와 volumes을 연결하는 식별자(my-volume)
	- hostPath: 호스트 서버의 연결 위치를 지정

volume.yaml을 이용하여 volume Pod를 생성합니다.
```shell
kubectl apply -f volume.yaml
```
![[image-115.png]]

volume Pod의 파일 시스템에서 `/container-volume` 디렉토리를 확인해봅니다.
```shell
kubectl exec volume -- ls /container-volume
```
![[image-116.png]]
실행 결과를 보면 ubuntu 디렉토리가 저장되어 있습니다.

호스트 서버에서 `/home`경로를 확인해봅니다.
```shell
ls -l /home
```
![[image-117.png]]
실행 결과를 보면 마스터 노드의 /home 경로와 volume Pod의 /container-volume 디렉토리가 볼륨으로 연결되어 있는 것을 볼수 있습니다.

이번에는 호스트 서버의 디렉토리를 연결하는 hostPath 외에 **Pod 내에서 임시로 생성하는 `emptyDir` property도 존재합니다.** emptyDir volume은 주로 컨테이너끼리 파일 데이터를 주고받는데 사용합니다.
```yaml
vim volume-empty.yaml
```

```yaml
# volume-empty.yaml
apiVersion: v1
kind: Pod
metadata:
  name: volume-empty
spec:
  containers:
  - name: nginx
    image: nginx
    # 컨테이너 내부의 연결 위치 지정
    volumeMounts:
    - mountPath: /container-volume
      name: my-volume
  volumes:
  - name: my-volume
    emptyDir: {}
```
- emptyDir
	- Pod의 생명주기를 따라가는 임시 volume입니다.
	- Pod 삭제가 되면 해당 volume도 삭제됩니다.
	- 컨테이너 내부에 파일을 저장하는것과 다름 없지만, 해당 property를 사용하는 이유는 같은 Pod 내에서 실행되는 다른 컨테이너와 파일을 주고 받기 위해서입니다.

```shell
kubectl apply -f volume-empty.yaml
```
![[image-118.png]]

/container-voume 경로에 데이터를 생성합니다.
```shell
kubectl exec volume-empty -- touch /container-volume/data1
kubectl exec volume-empty -- ls -l /container-volume
```
![[image-119.png]]
실행 결과를 보면 Pod의 /container-volume 디렉토리에 data1 파일이 저장되어 있습니다. volume-empty Pod가 삭제되면 해당 /container-volume 볼륨도 삭제됩니다.

## 5.6 리소스 관리
이ㄴ 절에서 다루는 리소스 관리는 쿠버테니스의 Pod, Service와 같은 종류의 리소스가 아닌 하드웨어 CPU, 메로리의 리소스 사용량 관리를 의미합니다.

쿠버테니스에서는 컨테이너가 실행할 때 필요한 리소스를 제어할 수 있습니다. 예를 들어 어떤 컨테이너가 메모리를 사용하는데 최소 100MB가 필요하면 최소 메모리 사용량을 설정할 수 있습니다.
명령형 정의 선언서 yaml 파일에서 `resources` 라는 property를 이용해서 리소스를 관리할 수 있습니다.

### 5.6.1 requests
resources.requests property를 이용해서 Pod가 보장받을 수 있는 최소 리소스량을 정의합니다.

예를 들어 nginx 컨테이너가 cpu를 최소 0.25core(250m)를 사용해햐아고 500MB의 메모리를 사용해야 한다면 다음과 같이 정의서를 작성합니다.
```yaml
# requests.yaml
apiVersion: v1
kind: Pod
metadata:
  name: requests
spec:
  containers:
  - name: nginx
    image: nginx
    resources:
      requests:
        cpu: "250m"
        memory: "500Mi"
```
- cpu에서 1000m은 1core를 의미합니다.
- memory의 Mi는 1Mib(2^20 bytes)를 의미합니다.

위 파일을 기반으로 requests Pod를 생성합니다.
```shell
kubectl apply -f requests.yaml
```
![[image-120.png]]

### 5.6.2 limits
`resources.limits` property는 Pod가 최대 리소스 사용량을 정의합니다.
```shell
# limits.yaml
apiVersion: v1
kind: Pod
metadata:
  name: limits
spec:
  restartPolicy: Never
  containers:
  - name: mynginx
    image: python:3.7
    command: ["python"]
    args: ["-c", "arr = []\nwhile True: arr.append(range(1000))"]
    resources:
      limits:
        cpu: "500m"
        memory: "1Gi"
```

위 파일을 기반으로 limits Pod를 생성합니다.
```shell
kubectl apply -f limits.yaml
kubectl get pod limits
```
![[image-121.png]]
실행 결과를 보면 mynginx 컨테이너에서 파이썬 스크립트로 인하여 메모리를 무한히 잡아먹어서 OutOfMemory 에러가 발생합니다. 에러가 발생하기 때문에 컨테이너가 제한된 리소스양을 넘으면 프로세스가 종료됩니다.
위와 같이 Pod가 다운되도 다른 Pod에 영향을 미치지 않습니다.

`resources.request`와 `resources.limits` property를 조합해서 최소, 최대 리소스 사용량을 정의할 수 있습니다. 
```yaml
# resources.yaml
apiVersion: v1
kind: Pod
metadata:
  name: resources
spec:
  containers:
  - name: nginx
    image: nginx
    resources:
      requests:
        cpu: "250m"
        memory: "500Mi"
      limits:
        cpu: "500m"
        memory: "1Gi"
```

위 파일을 기반으로 resources Pod를 실행합니다.
```shell
kubectl apply -f resources.yaml
```
![[image-122.png]]

## 5.7 상태 확인
이번 절에서는 Pod가 정상적으로 실행하고 있는지 확인하는 상태 확인(health check) 기능에 대해서 알아보겠습니다.

### 5.7.1 livenessProbe
`livenessProbe` property를 이용하면 쿠버네티스에는 컨테이너가 정상적으로 동작하는지 확인할 수 있습니다.

```yaml
# liveness.yaml
apiVersion: v1
kind: Pod
metadata:
  name: liveness
spec:
  containers:
  - name: nginx
    image: nginx
    livenessProbe:
      httpGet:
        path: /live
        port: 80
```

위 파일을 기반으로 liveness Pod을 생성합니다.
```shell
kubectl apply -f liveness.yaml
```
![[image-123.png]]

watch 명령어를 이용하여 liveness Pod의 상태를 모니터링합니다.
```shell
watch -n 2 kubectl get pod liveness
```
![[image-124.png]]

이번에는 liveness Pod의 로그를 출력해보겠습니다.
```shell
kubectl logs -f liveness
```
![[image-126.png]]
실행 결과를 보면 /live 경로는 아직 경로가 없기 때문에 404 에러를 응답합니다. 

시간이 지난후에 다시 liveness Pod의 상태를 모니터링하면 다음과 같습니다.
![[image-127.png]]
RESTARTS 컬럼을 보면 5가 출력됩니다. 이는 컨테이너가 다시 재시작된 횟수입니다. 재시작된 횟수가 증가된 이유는 상태 확인(health check) 결과 404 에러를 반환하게 되서 컨테이너를 강제로 재시작하게 된것이기 때문입니다.

위 문제를 해결하기 위해서 liveness Pod에 live 파일을 생성하여 상태 확인을 성공하게 만듭니다.
```shell
kubectl exec liveness -- touch /usr/share/nginx/html/live
```
![[image-128.png]]
위 실행 결과를 보면 시간이 지나도 RESTARTS 컬럼의 값이 증가하지 않는 것을 볼수 있습니다.

### 5.7.2 readinessProbe
livenessProbe는 Pod가 살아있는지 확인하는 용도라면 **readinessProbe은 Pod 생성 직후에 트래픽을 받을 준비가 완료되었는지 확인하는 property입니다.** 대표적인 사례로 jenkins 서버와 같이 처음 구동하는데 시간이 오래 걸리는 웹 서비스라면 구동 완료 이후에 트래픽을 받아야 합니다. 이러한 경우에 `readinessProbe` property를 이용해서 Pod 초기화 확인합니다.
```yaml
# readiness.yaml
apiVersion: v1
kind: Pod
metadata:
  name: readiness
spec:
  containers:
  - name: nginx
    image: nginx
    readinessProbe:
      httpGet:
        path: /ready
        port: 80
```

readiness Pod를 생성합니다.
```shell
kubectl apply -f readiness.yaml
```
![[image-129.png]]

```shell
kubectl logs -f readiness
```
![[image-130.png]]
로그 실행 결과를 보면 404 응답을 반환합니다.

readiness Pod 상태를 확인합니다.
```shell
kubectl get pod readiness
```
![[image-131.png]]
실행 결과를 보면 컨테이너 생성하지 못하는 것을 볼수 있습니다.

/ready 경로가 성공하기 위해서 다음과 같이 명령어를 실행합니다.
```shell
kubectl exec readiness -- touch /usr/share/nginx/html/ready
kubectl get pod
```
![[image-132.png]]
실행 결과를 보면 READY 1/1 로써 컨테이너가 생성된 것을 볼수 있습니다.

livenessProbe, readlinessProbe에서 HTTP 통신만이 아닌 명령어 실행 성공여부를 통해서도 정상 여부를 확인할 수 있습니다.
```yaml
# readiness-cmd.yaml
apiVersion: v1
kind: Pod
metadata:
  name: readiness-cmd
spec:
  containers:
  - name: nginx
    image: nginx
    readinessProbe:
      exec:
        command:
        - cat
        - /tmp/ready
```
- 명령어의 리턴값이 0이면 정상, 0 제외한 다른 값이 반환되면 비정상으로 인식

위 파일을 사용하여 readiness-cmd Pod를 실행합니다.
```shell
kubectl apply -f readiness-cmd.yaml
```
![[image-133.png]]

readiness-cmd의 READY 상태를 확인합니다.
```shell
kubectl get pod readiness-cmd
```
![[image-134.png]]
실행 결과를 보면 READY 0/1이기 때문에 /tmp/ready 파일이 없기 때문에 컨테이너가 생성되지 않습니다.

```shell
kubectl exec readiness-cmd -- touch /tmp/ready
kubectl get pod readiness-cmd
```
![[image-135.png]]
실행 결과를 보면 `cat /tmp/ready` 명령어가 성공하였기 때문에 Pod의 READY가 1/1가 되어 정상 상태가 됩니다.

## 5.8 2개 컨테이너 실행
이번절에서는 하나의 Pod에 2개의 서로 다른 컨테이너를 실행해보겠습니다.

second Pod에 nginx 컨테이너와 curl 컨테이너를 실행합니다. curl 컨테이너는 5초 간격으로 localhost:80에 HTTP 요청을 합니다.
```shell
vim second.yaml
```

second.yaml
```yaml
# second.yaml
apiVersion: v1
kind: Pod
metadata:
  name: second
spec:
  containers:
  - name: nginx
    image: nginx
  - name: curl
    image: curlimages/curl
    command: ["/bin/sh"]
    args: ["-c", "while true; do sleep 5; curl localhost; done"]
```

second.yaml 파일을 기반으로 second Pod를 생성합니다.
```shell
kubectl apply -f second.yaml
```
![[image-136.png]]

다음 Pod의 nginx 컨테이너 로그를 확인해봅니다.
```shell
kubectl logs second -c nginx
```
![[image-137.png]]
위 실행 결과를 보면 5초 간격으로 루트 경로를 요청받는 것을 볼수 있습니다.

이번에는 curl 컨테이너의 로그를 확인해봅니다.
```shell
kubectl logs second -c curl
```
![[image-138.png]]
실행 결과를 보면 localhost:80/ 요청한 결과인 index 페이지가 출력된 것을 볼수 있습니다.

위 예제에서 2개의 컨테이너를 실행하는데, curl 컨테이너가 5초간 대기하는 이유는 내부 컨테이너간의 실행 순서를 보장하지 않기 때문입니다. 그래서 nginx 서비스가 올라온 이후에 curl을 호출하기 위해서 5초간 대기하게 된 것입니다.

## 5.9 초기화 컨테이너
하나의 Pod의 두개 이상의 컨테이너가 실행되어야 하고 순서가 있어야 한다면, `initContainers` property를 이용해서 먼저 초기화 작업을 수행할 수 있습니다.

```yaml
# init-container.yaml
apiVersion: v1
kind: Pod
metadata:
  name: init-container
spec:
  restartPolicy: OnFailure
  containers:
  - name: busybox
    image: k8s.gcr.io/busybox
    command: ["ls"]
    args: ["/tmp/moby"]
    volumeMounts:
    - name: workdir
      mountPath: /tmp
  initContainers:
  - name: git
    image: alpine/git
    command: ["sh"]
    args:
    - "-c"
    - "git clone https://github.com/moby/moby.git /tmp/moby"
    volumeMounts:
    - name: workdir
      mountPath: /tmp
  dnsConfig:
    nameservers: 
      - 8.8.8.8 
      - 8.8.4.4 
    searches: 
      - default.svc.cluster.local 
  dnsPolicy: None
  volumes:
  - name: workdir
    emptyDir: {}
```
- initContainers : 메인 컨테이너 실행에 앞서서 초기화를 위해서 먼저 실행하는 컨테이너를 정의합니다.

메인 컨테이너인 busybox가 실행되기 전에 git 리포지토리를 미리 받아야 한다면 초기화 컨테이너에서 git pull을 받아서 컨테이너 끼리의 공유 공간인 emptyDir volume(/tmp)을 통해서 git 리포지토리를 전달합니다.

메인 컨테이너인 busybox는 git 리포지토리 데이터가 이미 있는 것을 가정하고 로직을 수행할 수 있습니다.

init-container Pod를 생성해봅니다.
```shell
kubectl apply -f init-container.yaml
```
![[image-139.png]]

```shell
kubectl get pod
```
![[image-140.png]]

```shell
kubectl logs init-container -c git -f
```
![[image-141.png]]

```shell
kubectl logs init-container
```
![[image-142.png]]
실행 결과를 보면 busybox 컨테이너가 /tmp/moby 디렉토리가 있다고 가정하고 파일 목록을 출력한 것을 볼수 있습니다.

## 5.10 Config 설정
쿠버네티스에서는 설정값들을 따로 모아두고 필요할 때 꺼내 사용할 수 있는 메커니즘이 존재합니다. 쿠버네티스에서는 이러한 리소스를 `ConfigMap`이라고 합니다. 이번절에서는 ConfigMap에 들어있는 설정값 들을 불러와서 Pod에 전달하는 방법을 살펴봅니다.

### 5.10.1 ConfigMap 리소스 생성
`ConfigMap` 리소스는 메타데이터(설정값)를 저장하는 리소스입니다. 지금까지는 Pod에 직접 설정값들을 넣었지만, ConfigMap을 이용하면 해당 리소스에 모든 설정들을 저장해놓고 Pod에서 필요한 설정 정보들을 가져올 수 있습니다.

명령어 형식
```shell
kubectl create configmap {KEY} {DATA-SOURCE}
```

다음과 같은 설정 파일들을 작성합니다.
```properties
# game.properties
weapon=gun
health=3
potion=5
```

`--from-file` 옵션을 이용해서 game.properties 파일을 game-config라는 이름의 ConfigMap으로 만듭니다.
```shell
kubectl create configmap game-config --from-file=game.properties
```
![[image-143.png]]

생성한 game-config ConfigMap을 상세 조회해봅니다.
```
kubectl get configmap game-config -o yaml
```
![[image-144.png]]
실행 결과를 보면 game.properties 파일 안에 있는 설정값들이 저장되어 있습니다.

이번에는 `--from-literal` 옵션을 사용하여 ConfigMap 리소스를 생성해보겠습니다.
```shell
kubectl create configmap special-config \
			--from-literal=special.power=10 \
			--from-literal=special.strength=20
```
![[image-145.png]]

생성한 special-config ConfigMap 리소스의 상세 정보를 확인해봅니다.
```shell
kubectl get configmap special-config -o yaml
```
![[image-146.png]]

사용자가 직접 ConfigMap 리소스를 yaml 정의서로 작성하여 생성할 수 있습니다.
```yaml
# monster-config.yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: monster-config
  namespace: default
data:
  monsterType: fire
  monsterNum: "5"
  monsterLife: "3"
```

위 파일을 기반으로 monster-config ConfigMap 리소스를 생성합니다.
```shell
kubectl apply -f monster-config.yaml
```
![[image-147.png]]

생성한 configmap을 확인합니다.
```shell
kubectl get configmap monster-config -o yaml
```
![[image-148.png]]

### 5.10.2 ConfigMap 활용
#### 볼륨 연결
ConfigMap 리소스 활용 방법으로 ConfigMap을 볼륨으로 마운트하여 파일처럼 사용 할 수 있습니다.
```yaml
# game-volume.yaml
apiVersion: v1
kind: Pod
metadata:
  name: game-volume
spec:
  containers:
  - name: game-volume
    image: k8s.gcr.io/busybox
    command: ["/bin/sh", "-c", "cat /etc/config/game.properties"]
    volumeMounts:
    - name: game-volume
      mountPath: /etc/config
  volumes:
  - name: game-volume
    configMap:
      name: game-config
```
- volumes.configMap : 기존 hostPath, emptyDir를 제외한 configMap이라는 볼륨을 사용할 수 있습니다.
- volumes.configMap.name: 볼륨으로 사용할 ConfigMap 리소스 이름을 설정합니다.

game-volume Pod를 생성합니다.
```shell
kubectl apply -f game-volume.yaml
```
![[image-149.png]]

game-volume Pod의 로그를 확인하여 gmae.properties 파일을 성공적으로 출력했는지 확인합니다.
```shell
kubectl logs game-volume
```
![[image-150.png]]
실행 결과를 보면 성공적으로 game.properties의 파일 내용을 출력하였습니다.

#### 환경변수 - valueFrom
ConfigMap을 Pod의 환경변수로도 사용할 수 있습니다.
```yaml
# special-env.yaml
apiVersion: v1
kind: Pod
metadata:
  name: special-env
spec:
  restartPolicy: OnFailure
  containers:
  - name: special-env
    image: k8s.gcr.io/busybox
    command: ["printenv"]
    args: ["special_env"]
    env:
    - name: special_env
      valueFrom:
        configMapKeyRef:
          name: special-config
          key: special.power
```
- special-env 컨테이너의 환경변수 sepcial_env 변수를 정의합니다. value 값은 special-config ConfigMap의 special.power의 value값입니다.

special-env Pod를 생성합니다.
```shell
kubectl apply -f special-env.yaml
```
![[image-151.png]]

special-env Pod의 로그를 확인해봅니다.
```shell
kubectl logs special-env
```
![[image-152.png]]

#### 환경변수 - envFrom
이번에는 1개의 설정값이 아니라 ConfigMap에 포함된 모든 설정값을 환경 변수로 사용하는 envFrom이라는 property에 대해서 알아봅니다.
```shell
# monster-env.yaml
apiVersion: v1
kind: Pod
metadata:
  name: monster-env
spec:
  restartPolicy: OnFailure
  containers:
  - name: monster-env
    image: k8s.gcr.io/busybox
    command: ["printenv"]
    # env 대신에 envFrom 사용
    envFrom:
    - configMapRef:
        name: monster-config
```
- envFrom : ConfigMap 설정값을 환경 변수 전체로 사용하는 것을 선언
- envFrom.configMapRef : ConfigMap의 특정 키(configMapKeyRef)가 아닌 전체 **ConfigMap(configMapRef)**을 사용하도록 설정합니다.
- envFrom.configMapRef.name : 사용하려는 ConfigMap 이름을 설정합니다.

monster-env Pod를 생성합니다.
```shell
kubectl apply -f monster-env.yaml
```
![[image-153.png]]

monster-env Pod의 로그를 확인해봅니다.
```shell
kubectl logs monster-env | grep monster
```
![[image-154.png]]
실행 결과를 보면 monster-config ConfigMap에 있는 모든 설정값들이 출력된 것을 볼수 있습니다.

## 5.11 민감 데이터 관리
Secret 리소스는 각 노드에서 사용될 때 디스크에 저장되지 않고, tmpfs라는 메모리 기반 파일 시스템을 사용해서 보안을 더욱 강하화합니다.

### 5.11.1 Secret 리소스 생성
계정 이름과 비밀번호에 대한 값들을 base64로 인코딩합니다.
```shell
echo -ne admin | base64
echo -ne password123 | base64
```
- -n : 출력 문자열에 줄바꿈 없음
- -e : 이스케이프 문자 해석할 수 있도록 함

![[image-155.png]]

위 값을 이용하여 Secret 리소스를 생성해보겠습니다.
```yaml
# user-info.yaml
apiVersion: v1
kind: Secret
metadata:
  name: user-info
type: Opaque
data:
  username: YWRtaW4=
  password: cGFzc3dvcmQxMjM=
```

kubectl apply 명령어를 이용하여 Secret 리소스를 생성합니다.
```shell
kubectl apply -f user-info.yaml
```
![[image-156.png]]

시크릿 정보를 확인해봅니다.
```shell
kubectl get secret user-info -o yaml
```
![[image-158.png]]
metadata.annotations.kubectl.kubenertes.io/last-applied-configuration 필드를 보면 중간에 username과 password에 대한 인코딩된 값이 있습니다.

만약 쿠버네티스가 대신 base64로 인코딩을 원한다면 data 대신 `stringData` property를 사용하면 됩니다.
```yaml
# user-info-stringdata.yaml
apiVersion: v1
kind: Secret
metadata:
  name: user-info-stringdata
type: Opaque
stringData:
  username: admin
  password: password123
```

Secret 리소스 생성해봅니다.
```shell
kubectl apply -f user-info-stringdata.yaml
```
![[image-159.png]]

```shell
kubectl get secret user-info-stringdata -o yaml
```
![[image-160.png]]
실행 결과를 보면 data.username, data.password 설정에 base64 인코딩된 값들이 저장되어 있습니다.

이번에는 yaml 파일이 아닌 명령형 커맨드를 이용해서 Secret 리소스를 생성해보도록 하겠습니다. 예를 들어 다음과 같이 사용자 정보가 저장된 properties 파일이 있습니다.
```shell
vim user-info.properties
```

```properties
# user.info.properties
username=admin
password=password123
```

`--from-env-file` 옵션을 이용해서 properties 파일로부터 Secret 리소스를 생성합니다.
```shell
kubectl create secret generic user-info-from-file \
  --from-env-file=user-info.properties
```
- generic : key-value 형태의 데이터를 저장하는 일반적인 시크릿, 예를 들어 환경 변수, 설정 파일, 비밀번호 등 저장 가능합니다.
- user-info-from-file : 시크릿 리소스 이름
![[image-161.png]]

```shell
kubectl get secret user-info-from-file -o yaml
```
![[image-162.png]]
- 실행 결과를 보면 username과 password의 값이 base64로 인코딩된것을 볼수 있습니다. (암호화 되지는 않음)

**base 64 수행과정**
1. 문자 a는 아스키문자로 97에 해당됩니다. 97를 이진수로 표현하면 01100001입니다.
2. 이진수를 6비트 단위로 쪼갭니다. a 같은 경우에는 011000 01입니다. 그런데 나머지 2개는 0으로 채워서 최종 이진수는 011000 010000이게 됩니다.
3. 각 6비트 값을 Base64 인코딩 테이블에서 찾습니다.
	1. 011000은 10진수로 24이고 Base64 인코딩 테이블에서는 Y입니다.
	2. 010000은 10진수로 16이고 Base64 인코딩 테이블에서는 Q입니다.
4. Base64 인코딩 테이블에 매핑된 결과는 YQ입니다.
5. 패딩을 추가합니다. Base64는 항상 3바이트(24비트)단위로 데이터를 처리해야 합니다. 하지만 "a"는 1바이트 밖에 없으므로 부족한 16비트 부분은 "="로 패딩합니다.
	1. a는 1바이트 밖에 없기 때문에 최종 결과는 "YQ\=\="가 됩니다.

최종결과 base64(a) = **"YQ\=\="**

### 5.11.2 Secret 활용
생성한 Secret을 Pod에서 활용해봅니다.
#### 볼륨 연결
secret도 ConfigMap과 동일하게 볼륨 연결이 가능합니다. `volumes` property에 secret이라는 이름으로 볼륨을 연결할 수 있습니다.
```yaml
# secret-volume.yaml
apiVersion: v1
kind: Pod
metadata:
  name: secret-volume
spec:
  restartPolicy: OnFailure
  containers:
  - name: secret-volume
    image: k8s.gcr.io/busybox
    command: ["sh"]
    args: ["-c", "ls /secret; cat /secret/username"]
    volumeMounts:
    - name: secret
      mountPath: "/secret"
  volumes:
  - name: secret
    secret:
      secretName: user-info
```

secret-volume Pod를 생성합니다.
```shell
kubectl apply -f secret-volume.yaml
```
![[image-163.png]]

```shell
kubectl logs secret-volume
```
![[image-164.png]]
실행 결과를 보면 user-info secret 리소스에 password, username 프로퍼티가 있고 username 프로퍼티의 값은 admin인 것을 볼수 있습니다.

#### 환경변수 - env
Secret리소스에 있는 정보를 환경변수로 추출할 수 있습니다. 개별적으로 환경변수를 설정할 때 `valueFrom.secretKeyRef` property를 사용합니다.
```yaml
# secret-env.yaml
apiVersion: v1
kind: Pod
metadata:
  name: secret-env
spec:
  restartPolicy: OnFailure
  containers:
  - name: secret-env
    image: k8s.gcr.io/busybox
    command: ["printenv"]
    env:
    - name: USERNAME
      valueFrom:
        secretKeyRef:
          name: user-info
          key: username
    - name: PASSWORD
      valueFrom:
        secretKeyRef:
          name: user-info
          key: password
```

secret-env Pod를 생성한 다음에 환경변수를 추출했는지 확인하기 위해서 로그를 확인합니다.
```shell
kubectl apply -f secret-env.yaml
```
![[image-165.png]]

secret-env Pod의 로그를 확인합니다.
```shell
kubectl logs secret-env
```
![[image-166.png]]
실행 결과를 보면 USERNAME, PASSWORD 환경 변수가 저장된 것을 볼수 있습니다.

#### 환경변수 - envFrom
전체 환경변수를 부르고자 할때는 `envFrom.secretRef` property를 사용합니다.
```yaml
# secret-envfrom.yaml
apiVersion: v1
kind: Pod
metadata:
  name: secret-envfrom
spec:
  restartPolicy: OnFailure
  containers:
  - name: secret-envfrom
    image: k8s.gcr.io/busybox
    command: ["printenv"]
    envFrom:
    - secretRef:
        name: user-info
```

secret-envfrom Pod를 생성합니다.
```shell
kubectl apply -f secret-envfrom.yaml
```
![[image-167.png]]

secret-envfrom 로그를 확인합니다.
```shell
kubectl logs secret-envfrom
```
![[image-168.png]]
실행 결과를 보면 username, password가 전부 환경변수로 설정된 것을 볼수 있습니다.

## 5.12 메타데이터 전달
쿠버네티스에서는 Pod의 메타 데이터를 컨테이너에게 전달 할 수 있습니다. ConfigMap과 Secret 리소스와 마찬가지로 환경변수, 볼륨 연결을 통해서 컨테이너에 전달할 수 있습니다.

### 5.12.1 볼륨 연결
```shell
vim downward-volume.yaml
```
```yaml
# downward-volume.yaml
apiVersion: v1
kind: Pod
metadata:
  name: downward-volume
  labels:
    zone: ap-north-east
    cluster: cluster1
spec:
  restartPolicy: OnFailure
  containers:
  - name: downward
    image: k8s.gcr.io/busybox
    command: ["sh", "-c"]
    args: ["cat /etc/podinfo/labels"]
    volumeMounts:
    - name: podinfo
      mountPath: /etc/podinfo
  volumes:
  - name: podinfo
    downwardAPI:
      items:
      - path: "labels"
        fieldRef:
          fieldPath: metadata.labels
```
- downwardAPI : DownwardAPI 볼륨 사용 선언
	- items : 메타 데이터로 사용할 아이템 리스트 지정
		- path: 볼륨과 연결될 컨테이 내부 path를 지정합니다.
		- fieldRef : 참조할 필드 선언
			- fieldPath: Pod의 메타데이터 필드를 지정


downward-volume Pod를 실행합니다.
```shell
kubectl apply -f downward-volume.yaml
```
![[image-169.png]]

downward-volume 로그 정보를 확인해봅니다.
```shell
kubectl logs downward-volume
```
![[image-170.png]]
실행 결과를 보면 downard-volume Pod의 labels에 있는 zone, cluster 레이블 정보가 출력되었습니다.

DownwardAPI를 이용하여 Pod의 라벨 필드를 컨테이너의 /etc/podinfo/labels 위치에 볼륨을 연결했습니다.

### 5.12.2 환경변수 - env
Downward API도 마찬가지로 환경변수로 선언할 수 있습니다.
```shell
vim downward-env.yaml
```
```yaml
# downward-env.yaml
apiVersion: v1
kind: Pod
metadata:
  name: downward-env
spec:
  restartPolicy: OnFailure
  containers:
  - name: downward
    image: k8s.gcr.io/busybox
    command: ["printenv"]
    env:
    - name: NODE_NAME
      valueFrom:
        fieldRef:
          fieldPath: spec.nodeName
    - name: POD_NAME
      valueFrom:
        fieldRef:
          fieldPath: metadata.name
    - name: POD_NAMESPACE
      valueFrom:
        fieldRef:
          fieldPath: metadata.namespace
    - name: POD_IP
      valueFrom:
        fieldRef:
          fieldPath: status.podIP
```

downward-env Pod를 생성합니다.
```kubectl
kubectl apply -f downward-env.yaml
```
![[image-171.png]]

downward-env 로그를 확인합니다.
```shell
kubectl logs downward-env
```
![[image-172.png]]

## 5.13 마치며
- Pod 리소스에 대해서 자세히 살펴봄
- Pod는 쿠버네티스의 중심이 되는 리소스로써 Pod의 핵심적인 기능에 대해서 전박적으로 살펴봄
- 다음 장에서는 쿠버네티스의 네트워킹을 책임지는 Service 리소스에 대해서 살펴봄

#### Clean up
```shell
kubectl delete pod --all
```


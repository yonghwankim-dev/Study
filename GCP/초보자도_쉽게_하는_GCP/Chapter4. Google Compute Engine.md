
## Compute Engine 생성해보기
Compute Engine을 생성하기 위해서 Compute Engine 서비스로 이동합니다.

서비스로 입장했으면 상단 메뉴의 "인스턴스 만들기" 버튼을 클릭하여 생성 페이지로 입장합니다.
![image](imgs/Pasted%20image%2020250819113607.png)

Compute Engine 프리티어 기준을 보면 오리건 리전에 실행하는 e2-micro VM 인스턴스는 1개 무료로 사용할 수 있습니다.
![image](imgs/Pasted%20image%2020250819121910.png)

생성 페이지에서 Compute Engine 정보를 입력합니다.
![image](imgs/Pasted%20image%2020250819122728.png)
![image](imgs/Pasted%20image%2020250819122735.png)

사이드 메뉴의 OS 및 스토리지 메뉴로 이동하여 부팅 디스크 설정을 다음과 같이 변경합니다.
![image](imgs/Pasted%20image%2020250819123442.png)

네트워킹 페이지로 이동하여 네트워크 태그 부분에 "ssh-allow" 태그를 추가합니다.
![image](imgs/Pasted%20image%2020250819123953.png)

네트워크 인터페이스 부분에서 이전 단계에서 생성한 vpc를 설정합니다.
![image](imgs/Pasted%20image%2020250819124035.png)

만들기 버튼을 클릭하여 VM 인스턴스 생성을 확인합니다.
![image](imgs/Pasted%20image%2020250819124259.png)

생성한 인스턴스에서 연결 컬럼의 SSH 값을 누르면 해당 인스턴스에 SSH 연결하여 접근할 수 있습니다. 그러나 실행 결과를 보면 SSH 연결할 수 없다고 나옵니다. 연결할 수 없는 이유는 인스턴스의 22번 포트 벽이 열려 있지 않기 때문입니다.
![image](imgs/Pasted%20image%2020250819124657.png)

방화벽 문제를 해결하기 위해서 "VPC 네트워크 -> 방화벽" 메뉴로 이동합니다.
![image](imgs/Pasted%20image%2020250819124813.png)

방화벽 페이지에서 상단 메뉴에 있는 "방화벽 규칙 만들기" 버튼을 클릭합니다.
![image](imgs/Pasted%20image%2020250819125327.png)

생성 페이지에서 다음과 같이 방화벽 규칙을 입력합니다.
![image](imgs/Pasted%20image%2020250819130840.png)

대상 태그에 "ssh-allow" 태그를 단 VM 인스턴스에만 방화벽이 작동합니다.
![image](imgs/Pasted%20image%2020250819130104.png)

TCP/22 프로토콜 및 포트에만 트래픽 규칙이 적용됩니다.
![image](imgs/Pasted%20image%2020250819130112.png)

방화벽 규칙을 생성한 다음에 생성 확인합니다.
![image](imgs/Pasted%20image%2020250819130956.png)

방화벽 규칙을 생성한 다음에 다시 Compute Engine 서비스로 이동하여 다시 SSH 연결해봅니다.

실행 결과를 보면 정상적으로 접속한 것을 확인할 수 있습니다.
![image](imgs/Pasted%20image%2020250819131244.png)

브라우저에서 SSH를 통해서 연결하는 것만이 아닌 Gcloud를 이용해서도 연결할 수 있습니다. 다음 화면에서 "Cloud Shell에서 실행" 버튼을 클릭하여 연결해봅니다.
![image](imgs/Pasted%20image%2020250819131336.png)

실행 결과를 보면 정상적으로 터미널을 생성하여 접속한 것을 볼수 있습니다.
![image](imgs/Pasted%20image%2020250819131500.png)

위 명령어를 엔터를 누르면 VM 인스턴스에 들어가기 위한 SSH 키 파일 생성 작업에 들어갑니다. 그리고 비밀번호를 입력하면 다음과 같이 VM 인스턴스에 접속이 가능합니다.
![image](imgs/Pasted%20image%2020250819132026.png)

apt-get 패키지 매니저를 업데이트 해봅니다.
```shell
sudo apt-get update -y
```

다음 시간에는 Private Compute Engine을 생성해봅니다.

## Private Compute Engine 만들어보기
### 방화벽 규칙 생성
Private Compute Engine을 생성하기 전에 IAP(Identity-Aware Proxy)에 대해서 알아야 합니다. IAP를 활용하면 외부에서 접근할 수 없는 Private Compute Engine에 접근할 수 있도록 도와줍니다.

우선은 "VPC 네트워크 -> 방화벽" 페이지로 입장합니다.  그리고 "방화벽 규칙 만들기" 버튼을 클릭하여 새로운 방화벽 규칙을 생성합니다.
![image](imgs/Pasted%20image%2020250819134051.png)
![image](imgs/Pasted%20image%2020250819134100.png)

위 방화벽 규칙을 통하여 Private Compute Engine에 접근하기 위한 그림은 다음과 같습니다.  외부 IP 주소가 없는 Private Compute Engine에 접근하기 위해서 사용자는 IAP에 접근하여 Compute Engine에 접근합니다.
![image](imgs/Pasted%20image%2020250819134342.png)

### IAP TCP 전달을 사용할 권한 부여하기
"IAM 및 관리자" 페이지로 이동합니다.
![image](imgs/Pasted%20image%2020250819140725.png)

위 주 구성중에서 본인의 속하는 fineants 사용자를 대상으로 편집버튼을 눌러서 역할을 추가합니다. 
IAP 보안 터널 사용자 역할을 추가하고 IAM 조건 추가 버튼을 클릭하여 추가적인 조건을 설정합니다.
![image](imgs/Pasted%20image%2020250819140915.png)

다음과같이 제목과 조건을 입력합니다. 이렇게 입력하면 사용자가 IAP를 통하여 22, 3389 포트로만 접근할 수 있습니다.
![image](imgs/Pasted%20image%2020250819141028.png)

조건을 추가했으면 추가적인 역할을 생성합니다.
![image](imgs/Pasted%20image%2020250819141539.png)

### Compute Engine 생성하기
Compute Engine 서비스로 이동하여 VM 인스턴스를 생성하고자 합니다.
![image](imgs/Pasted%20image%2020250819142712.png)
![image](imgs/Pasted%20image%2020250819142719.png)
![image](imgs/Pasted%20image%2020250819142725.png)
![image](imgs/Pasted%20image%2020250819142740.png)

네트워크 인터페이스의 외부 IPv4 주소를 보면 임시에서 없음으로 변경하여 Private Compute Engine으로 설정합니다.
![image](imgs/Pasted%20image%2020250819142748.png)

만들기 버튼을 눌러서 Private Compute Engine 생성 확인을 합니다.

### IAP 관련해서 한번더 체크하기
IAP와 연결된 VM 인스턴스를 확인할 수 있습니다. "IAM 및 관리자 -> IAP" 메뉴로 이동합니다.
![image](imgs/Pasted%20image%2020250819143116.png)

위 페이지에서 SSH 및 TCP 리소스 메뉴를 클릭합니다. 클릭하면 아래 리소스에 두개의 vm 인스턴스가 연결되어 있는 것을 볼수 있습니다.
![image](imgs/Pasted%20image%2020250819143142.png)

### Private Compute Engine SSH 연결
Compute Engine 서비스로 이동하여 생성한 Private Compute Engine VM 인스턴스(vm-uc1-backend01)에 SSH 연결해봅니다.
![image](imgs/Pasted%20image%2020250819143558.png)

실행 결과 다음과 같이 접속할 수 있습니다.
![image](imgs/Pasted%20image%2020250819143656.png)

다음 결과를 보면 내부 인스턴스에서 apt-get 패키지 매니저를 업데이트하려고 하지만 업데이트하지 못하였습니다. 원인은 외부와 연결되어 있지 않기 때문입니다.
![image](imgs/Pasted%20image%2020250819143817.png)

위 문제를 해결하기 위해서 VM 인스턴스를 라우터와 NAT를 연결해주어야 합니다.
Cloud NAT 서비스로 이동합니다. 그리고 Cloud NAT 게이트 만들기를 선택합니다.
![image](imgs/Pasted%20image%2020250819144539.png)

다음과 같이 Cloud NAT 게이트웨이 정보를 입력합니다.
![image](imgs/Pasted%20image%2020250819145137.png)

us-central1 리전에 속하는 라우터가 없기 때문에 새로운 라우터를 생성해야 합니다.
![image](imgs/Pasted%20image%2020250819145252.png)

Cloud NAT 생성을 확인합니다.
![image](imgs/Pasted%20image%2020250819150151.png)

다시 Private VM 인스턴스에 SSH 접속해서 apt-get 패키지 매니저를 업데이트해봅니다.
```shell
sudo apt-get update -y
```

실행 결과를 보면 성공적으로 외부와 통신하여 패키지 매니저를 업데이트하는 것을 볼수 있습니다.
![image](imgs/Pasted%20image%2020250819150458.png)


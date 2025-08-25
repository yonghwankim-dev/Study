
## 일반 VPC 생성해보기
VPC 네트워크 메뉴를 이용하여 서비스로 이동합니다.
![image](imgs/Pasted%20image%2020250818163626.png)
![image](imgs/Pasted%20image%2020250818163639.png)

메뉴에서 "VPC 네트워크 만들기" 버튼을 클릭하여 새로운 VPC 네트워크를 만듭니다.

![image](imgs/Pasted%20image%2020250818164004.png)

서브넷 또한 생성합니다.
![image](imgs/Pasted%20image%2020250819114915.png)
![image](imgs/Pasted%20image%2020250819114931.png)
- 비공개 Google 액세스 사용의 의미
	- VPC 내부의 **프라이빗 IP만 가진 VM 인스턴스**(외부 IP 없음)가 있을 때,   이 인스턴스가 **Google API 및 서비스(예: Cloud Storage, BigQuery, Pub/Sub 등)**에 접근할 수 있게 해주는 기능입니다.

서브넷을 생성한 다음에 만들기 버튼을 클릭하여 VPC 및 서브넷을 생성합니다.
![image](imgs/Pasted%20image%2020250818164913.png)

gcp-share-vcp 생성을 확인합니다.
![image](imgs/Pasted%20image%2020250818165117.png)
- gcp-share-vpc가 있기 때문에 default vpc는 삭제합니다.

## 일반 VPC에 서브넷 추가해보기
이전 단계에서 생성한 gcp-share-vpc를 클릭하여 상세 페이지로 이동합니다.
![image](imgs/Pasted%20image%2020250818165716.png)

위 화면에서 서브넷 메뉴를 클릭한 다음에 추가적인 서브넷 생성을 합니다.
![image](imgs/Pasted%20image%2020250819115216.png)
![image](imgs/Pasted%20image%2020250818165957.png)

추가적인 서브넷 생성을 확인합니다.
![image](imgs/Pasted%20image%2020250819115544.png)


## 라우터와 NAT 연결하기
메뉴에서 네트워킹 연결->Cloud Router로 이동합니다. 다음 화면에서 라우터 만들기를 클릭합니다.
![image](imgs/Pasted%20image%2020250818170621.png)

다음과 같이 라우터 정보를 입력합니다.
![image](imgs/Pasted%20image%2020250819115932.png)

라우터 생성을 확인합니다.
![image](imgs/Pasted%20image%2020250819120148.png)

라우터를 생성하였으면 NAT를 생성하기 위해서 탐색창에서 다음과 같이 검색합니다.
![image](imgs/Pasted%20image%2020250818170834.png)

다음 Cloud NAT 화면에서 시작하기 버튼을 눌러서 NAT를 생성해봅니다.
![image](imgs/Pasted%20image%2020250818171040.png)

NAT 정보를 입력합니다.
![image](imgs/Pasted%20image%2020250819120549.png)
![image](imgs/Pasted%20image%2020250818171253.png)

NAT 생성을 확인합니다.
![image](imgs/Pasted%20image%2020250819120815.png)


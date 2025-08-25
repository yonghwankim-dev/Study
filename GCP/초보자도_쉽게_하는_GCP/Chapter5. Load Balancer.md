
## go를 활용한 서버 만들기
이번 강의에서는 로드 밸런서에 들어갈 서버를 go 언어를 활용해서 만들어봅니다.

Cloud Shell에 접속합니다. 
![[Pasted image 20250819151451.png]]

Cloud Shell에는 기본적으로 go 언어가 설치되어 있습니다. go 언어 설치를 확인해봅니다. 실행 결과를 보면 go1.24.6 버전이 설치되어 있습니다.
```shell
$ go version
go version go1.24.6 linux/amd64
```

server라는 이름의 디렉토리를 생성 및 해당 디렉토리로 이동합니다.
```shell
mkdir server
cd server
```

현재 디렉토리를 "main"이라는 이름의 Go 모듈로 초기화해서, go.mod 파일을 생성합니다.
```shell
go mod init main
```

mod 파일이 생성되었는지 확인하고 내용을 확인해봅니다.
```shell
ls
cat go.mod
```
![[Pasted image 20250820111742.png]]

gin-gonic web framework 설치
```shell
go get -u github.com/gin-gonic/gin
```

main.go파일에 코드 작성하기
- /ping 경로로 요청이 들어오면 pong 메시지를 반환한다
- /test 경로로 요청이 들어오면 호스트 네임을 반환한다
```shell
# main.go
package main

import (
  "net/http"
  "os"
  "fmt"
  "github.com/gin-gonic/gin"
)
func GetHostName(c *gin.Context){
	hostname, err := os.Hostname()
	if err != nil {
	fmt.Println(err)
	    hostname = "error"
	}
	c.JSON(http.StatusOK, gin.H{
	  "message": hostname,
	})
}

func main() {
	r := gin.Default()
	r.GET("/ping", func(c *gin.Context) {
	c.JSON(http.StatusOK, gin.H{
	  "message": "pong",
	})
	})

	r.GET("/test",GetHostName)
	// listen and serve on 0.0.0.0:8080 (for windows "localhost:8080")
	r.Run() 
}
```

go 서버 실행
```shell
go run main.go
```

새로운 Cloud Shell 터미널을 열어서 테스트해봅니다.
```shell
curl -XGET localhost:8080/ping
```
![[Pasted image 20250820113937.png]]
- 실행 결과를 보면 json 파일 형식으로 pong 메시지가 응답됨

"/test" 경로로 요청을 나려서 호스트네임이 무엇인지 테스트해봅니다.
```shell
curl -XGET localhost:8080/test
```
![[Pasted image 20250820114550.png]]
- 실행 결과를 보면 cs-755977014282-default 인것을 볼수 있습니다.

main.go 파일 빌드
- `-o server` : server 라는 이름의 파일로 빌드합니다.
```shell
go build -o server
```
![[Pasted image 20250820114859.png]]
- 빌드 결과를 보면 server 실행 파일이 생성됨

server 파일을 대상으로 다음과 같이 서버를 실행할 수 있습니다.
```shell
./server
```


## Instance Image 만들기
이번 글에서는 이전 단계에서 만든 서버를 컴퓨터 인스턴스에 올리고 머신 이미지로 생성하는 작업을 해보도록 하겠습니다.

Compoute Engine 서비스 이동 -> VM 인스턴스 만들기
![[Pasted image 20250820125527.png]]
![[Pasted image 20250820131121.png]]

부팅 디스크 설정
![[Pasted image 20250820130015.png]]

네트워크 설정
- VPC의 서브넷에서 외부 IP 없음 설정
![[Pasted image 20250820131329.png]]

VM 인스턴스 생성 확인
![[Pasted image 20250820131340.png]]

이렇게 만든 VM 인스턴스 서버에 이전에 만든 server 파일을 VM 인스턴스에 전달하여 실행하도록 합니다.

gcloud 명령 보기 버튼을 클릭하여 터미널 접속
![[Pasted image 20250820131838.png]]

![[Pasted image 20250820132257.png]]

이전 단계에서 빌드한 server 파일을 vm-uc1-goserver-01 인스턴스에 업로드하기 위해서 다음과 같은 클라우드 명령어를 실행합니다.
- gcloud compute scp 명령어를 사용하여 server 파일을 vm-uc1-goserver-01 인스턴스의 /home/fineants_co_2024 디렉토리에 복사합니다.
```shell
gcloud compute scp server vm-uc1-goserver-01:/home/fineants_co_2024 --zone=us-central1-c
```
![[Pasted image 20250820133826.png]]

vm 인스턴스에 접속하여 server 파일이 업로드되었는지 확인합니다.
- 실행 결과를 보면 정상적으로 server 파일이 업로드 되었음
![[Pasted image 20250820133852.png]]

해당 인스턴스에 server가 업로드되었기 때문에 머신 이미지로 만들어보겠습니다.

### 머신 이미지 생성
Compute Engine -> 스토리지 -> 이미지 메뉴를 클릭합니다. 이미지 페이지에서 이미지 만들기 버튼을 눌러서 이미지를 생성합니다.
![[Pasted image 20250820135737.png]]

이미지 정보를 입력합니다.
![[Pasted image 20250820135851.png]]

이미지 생성을 확인합니다.
- 필터의 유형: 커스텀으로 설정
![[Pasted image 20250820135943.png]]
이후 VM 인스턴스 이미지를 가지고 인스턴스 템플릿을 생성할 수 있습니다.


## 인스턴스 템플릿 만들기
이전 단계에서 생성한 VM 인스턴스 이미지를 활용하여 인스턴스 템플릿을 생성해보겠습니다.

### 인스턴스 템플릿 생성
Compute Engine -> 인스턴스 템플릿 페이지 -> 인스턴스 템플릿 만들기
![[Pasted image 20250820140904.png]]

다음과 같이 템플릿 설정을 해줍니다.
![[Pasted image 20250820142435.png]]

머신 유형 설정
![[Pasted image 20250820142448.png]]

부팅 디스크 설정
![[Pasted image 20250820142501.png]]

![[Pasted image 20250821131828.png]]

![[Pasted image 20250821133125.png]]

![[Pasted image 20250820142557.png]]

자동화 스크립트 설정
- 인스턴스 실행시 server 파일을 실행함
![[Pasted image 20250820143210.png]]

인스턴스 템플릿 생성 확인
![[Pasted image 20250820143315.png]]

## Instance Group 만들기
인스턴스 템플릿은 VM 인스턴스를 생성하기 위한 설계도입니다. 인스턴스 템플릿을 이용하면 서버의 환경 설정을 처음부터 하는 것이 아닌 환경 설정을 완성한 상태로 인스턴스를 생성할 수 있습니다. (물론 특정 이미지를 기반으로 생성함)

Compute Engine -> 인스턴스 그룹 페이지로 이동합니다. 인스턴스 그룹 만들기 버튼을 클릭합니다.
![[Pasted image 20250820144229.png]]

인스턴스 그룹 정보 입력
![[Pasted image 20250820144741.png]]

![[Pasted image 20250820150311.png]]
![[Pasted image 20250820144815.png]]

인스턴스 그룹 생성 확인
![[Pasted image 20250820150428.png]]

VM 페이지로 이동하면 인스턴스 2개가 생성되고 있는 것을 볼수 있습니다.
![[Pasted image 20250820150435.png]]

인스턴스 중 하나에 들어가서 server 서버에 요청을 날려봅니다.
```shell
curl -XGET "localhost:8080/ping"
```
![[Pasted image 20250820150626.png]]
- 실행 결과를 보면 정상적으로 응답된 것을 볼수 있음


## Health Check 만들기
Compute Engine -> 인스턴스 그룹 -> 상태 점검 페이지로 입장합니다. Health Check를 생성하기 위해서 상단 메뉴의 "상태 확인 만들기" 버튼을 클릭합니다.
![[Pasted image 20250820151356.png]]

goserver에 대한 상태 확인 정보를 입력
![[Pasted image 20250820151532.png]]
![[Pasted image 20250820151541.png]]

상태 확인 생성 확인
![[Pasted image 20250820151553.png]]

인스턴스 그룹으로 이동하여 goserver-group 인스턴스 그룹을 수정하여 hc-goserver를 상태확인으로써 수정합니다.
![[Pasted image 20250820151821.png]]


goserver-group 인스턴스 그룹 상세 페이지로 이동해서 상태 확인 결과를 확인합니다.

## 부하 분산기 만들기
네트워크 서비스 -> 부하 분산 메뉴로 입장합니다.
![[Pasted image 20250821112708.png]]

"부하 분산기 만들기" 버튼을 클릭합니다.
![[Pasted image 20250821113303.png]]

부하 분산기 유형 선택
- 애플리케이션 부하 분산기 선택
![[Pasted image 20250821113511.png]]

공개 또는 내부 유형 선택
![[Pasted image 20250821113623.png]]

전역 또는 단일 리전 배포
![[Pasted image 20250821113642.png]]

부하 분산기 생성
![[Pasted image 20250821114100.png]]

부하 분산기 이름 설정 및 프론트엔드 설정
![[Pasted image 20250821114420.png]]

백엔드 구성 설정
![[Pasted image 20250821134302.png]]

![[Pasted image 20250821115511.png]]

백엔드 구성 생성 확인
![[Pasted image 20250821115740.png]]

라우팅 규칙 설정
![[Pasted image 20250821115804.png]]

부하 분산기 생성 확인
- 생성 결과 백엔드 인스턴스를 대상으로 Health Check가 실패하였습니다.
![[Pasted image 20250821121053.png]]

Health Check가 실패한 원인
- GCP Load Balancer의 헬스 체크 트래픽이 백엔드 VM 인스턴스에 도달하지 못해서 실패함
- 기본적으로 GPC VPC 네트워크에는 기본 "deny ingress" 규칙이 있어서 특별히 허용하지 않으면 외부에서 들어오는 요청이 차단됨

Health Check 실패 문제의 해결 방법
- Google Cloud 헬스 체크 프로버(Health Check Probers)의 IP 대역에서 오는 트래픽을 허용하는 방화벽 규칙(Firewall Rule)을 추가합니다.

VPC 네트워크 -> 방화벽 규칙 메뉴로 이동
![[Pasted image 20250821121455.png]]

방화벽 규칙 생성
![[Pasted image 20250821123003.png]]
![[Pasted image 20250821123010.png]]

방화벽 규칙 생성 확인
![[Pasted image 20250821133909.png]]
![[Pasted image 20250821133917.png]]

인스턴스 템플릿 생성 단계에서 네트워킹에 "allow-heath-check" 태그를 추가합니다.
![[Pasted image 20250821133125.png]]

인스턴스 그룹을 생성하고 결과를 확인해봅니다.
- 실행 결과를 보면 인스턴스 그룹 생성 단계에서 Health Check가 성공한 것을 확인할 수 있습니다.
![[Pasted image 20250821134024.png]]

다시 부하 분산기를 생성합니다.
- 실행 결과를 보면 정상적으로 Health-Check도 통과한 것을 볼수 있음
![[Pasted image 20250821134539.png]]
![[Pasted image 20250821134551.png]]

웹 브라우저를 이용한 로드 밸런서에 ping 요청
![[Pasted image 20250821134759.png]]

웹 브라우저에 "http://34.54.221.183:80/ping" 경로로 요청을 해봅니다.
![[Pasted image 20250821140129.png]]

## 부하 분산기 만들기 (2)
### 도메인 구매
- 웹 브라우저에서 "https://도메인네임/ping" 으로 요청하기 위해서는 도메인 구매가 필요함
- 본인은 가비아 플랫폼에서 "nemo1107.store" 라는 도메인을 구매함
- 도메인의 네임 서버는 가비아가 관리하도록 함
![[Pasted image 20250825115300.png]]

### 로드 밸런서 프론드엔드의 HTTPS 구성 추가

이전 단계에서 만들었던 lb-go-server 부하 분산기를 수정합니다. 프론트 엔드 구성에서 "프론트엔드 IP 및 포트 추가" 버튼을 클릭하여 추가합니다.
![[Pasted image 20250821140808.png]]
	
새 프론트엔드 IP 및 포트를 만드는 과정에서 HTTPS 프로토콜을 선택합니다. 그후 인증서를 선택해야 하는데, 우리는 기존 SSL 인증서가 없기 때문에 다음과 같이 "새 인증서 만들기"를 선택하여 만들고자 합니다.
![[Pasted image 20250821140924.png]]

새 인증서 만들기 정보 입력
- 새로운 인증서의 이름은 nemo-dns로 하겠습니다. 
![[Pasted image 20250825120642.png]]

새 프론트엔드 생성 확인
![[Pasted image 20250821142128.png]]

업데이트 버튼을 눌러서 완료합니다.
![[Pasted image 20250821142151.png]]

로드밸런서의 상세 정보를 보면 HTTPS 프로토콜 프론트엔드가 추가된 것을 볼수 있습니다.
![[Pasted image 20250825115635.png]]

HTTPS 프로토콜의 IP 포트인 "34.102.212.91" IP 주소를 복사한 다음에 가비아 플랫폼의 nemo1107.store 도메인의 레코드 설정에서 다음과 같이 레코드를 추가합니다.
- 사용자가 https://back.nemo1107.store 로 접속시 34.102.212.91:443 으로 라우팅됩니다.
- 34.102.212.91 IP 주소는 로드밸런서의 HTTPS 프론트엔드 IP 주소
![[Pasted image 20250825115729.png]]

로드 밸런서 상세 페이지에서 nemo-dns 인증서를 클릭하여 상태를 확인합니다.
- 시간이 지나서 상태를 보면 PROVISIONING 상태에서 ACTIVE 상태가 되는 것을 확인할 수 있습니다.
![[Pasted image 20250825115902.png]]

웹 브라우저에서 테스트하여 결과를 확인해봅니다.
![[Pasted image 20250825115936.png]]

![](imgs/Pasted%20image%2020250825130015.png)


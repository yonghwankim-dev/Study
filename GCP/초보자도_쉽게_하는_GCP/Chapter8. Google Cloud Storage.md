
## 버킷 생성해보기
### 버킷 생성
Cloud Storage 서비스의 버킷 메뉴로 이동합니다.
![](imgs/Pasted%20image%2020250826135841.png)

![](imgs/Pasted%20image%2020250826135848.png)

새로운 버킷 생성
- "만들기" 버튼을 클릭하여 버킷을 생성하고자 함
![](imgs/Pasted%20image%2020250826140227.png)
![](imgs/Pasted%20image%2020250826140242.png)

![](imgs/Pasted%20image%2020250826140251.png)

![](imgs/Pasted%20image%2020250826140908.png)

![](imgs/Pasted%20image%2020250826141304.png)

버킷 생성 확인
![](imgs/Pasted%20image%2020250826141333.png)

### 데이터 넣어보기
gcp-pjt-share-test-1107 버킷에 이미지 파일을 하나 업로드해봅니다.
![](imgs/Pasted%20image%2020250826142300.png)

업로드된 이미지 파일의 상세 정보를 보면 다음과 같습니다.
- 인증된 URL과 공개 URL이 존재함
- 인증된 URL은 권한을 가진 사람만 파일을 열수 있음.
![](imgs/Pasted%20image%2020250826142316.png)

권한을 가진 사람이 URL로 접근한 결과는 다음과 같음.
![](imgs/Pasted%20image%2020250826142506.png)


구글 다른 계정으로 접근시 결과는 다음과 같음
![](imgs/Pasted%20image%2020250826142710.png)

### 버킷의 파일에 공개 권한 부여
버킷의 권한 탭으로 이동
- 액세스 권한 부여 클릭
![](imgs/Pasted%20image%2020250826143933.png)

권한 부여 정보 입력
![](imgs/Pasted%20image%2020250826144040.png)

권한 생성 확인
![](imgs/Pasted%20image%2020250826144103.png)

이미지의 공개 URL에 접속해봄
![](imgs/Pasted%20image%2020250826144123.png)

정상적으로 접근된 것을 확인할 수 있다
![](imgs/Pasted%20image%2020250826144142.png)

## 버킷 데이터 이관해보기
버킷 페이지로 이동합니다.
- gcp-pjt-share-test-1107 버킷에 있는 데이터를 gcp-share-sql-backup-1107 버킷으로 옮길 예정입니다.
![](imgs/Pasted%20image%2020250826145541.png)

검색창에 "Storage Transfer Service"를 검색하고 이동합니다.
![](imgs/Pasted%20image%2020250826151934.png)

전송 작업 만들기 버튼 클릭
![](imgs/Pasted%20image%2020250826152654.png)

소스 유형 및 대상 위치 유형 선택
- 이 실습에서는 버킷에서 버킷으로 데이터를 이관하는 것이기 때문에 둘다 Google Cloud Storage 선택
- 해당 전송 작업은 1회성이기 때문에 예약 모드에서 단위로 선택
![](imgs/Pasted%20image%2020250826152859.png)

소스 선택
- 프리픽스별 필터링 옵션을 통해서 특정 조건을 갖춘 데이터만 이관이 가능함
![](imgs/Pasted%20image%2020250826153011.png)

대상 위치 선택
- 데이터가 이관될 목적지 버킷을 선택함
![](imgs/Pasted%20image%2020250826153342.png)

작업 실행 시점 선택
- 이번 실습에서는 일회성이기 때문에 한번만 실행하도록 선택함
![](imgs/Pasted%20image%2020250826153357.png)

설정 선택
- 추가적인 설정 변경 없이 그대로 진행
![](imgs/Pasted%20image%2020250826153454.png)
![](imgs/Pasted%20image%2020250826153510.png)
![](imgs/Pasted%20image%2020250826153516.png)

전송 작업 실행 확인
- 전송 작업이 진행중인 것을 확인
![](imgs/Pasted%20image%2020250826153533.png)

대상 버킷인 gcp-share-sql-backup-1107 버킷으로 이동하여 전송 작업 실행 결과 확인
- 출발지 버킷의 이미지 파일이 전송된 것을 확인함
![](imgs/Pasted%20image%2020250826153620.png)


이와 같은 데이터 전송 작업을 이용해서 AWS S3 버킷에서 GCP Cloud Storage로 이관 같은 작업이 가능합니다.

## 정적 웹 호스팅 해보기
- GCS(Google Cloud Storage) 서비스를 이용해서 정적 웹사이트를 호스팅 할 예정

Cloud Storage 서비스 이동
- "만들기" 버튼을 클릭하여 버킷 생성
![](imgs/Pasted%20image%2020250826155709.png)

버킷 생성 정보 입력
![](imgs/Pasted%20image%2020250826155922.png)

![](imgs/Pasted%20image%2020250826155942.png)

![](imgs/Pasted%20image%2020250826160002.png)

![](imgs/Pasted%20image%2020250826160016.png)

![](imgs/Pasted%20image%2020250826160027.png)

버킷 생성 확인
- 해당 버킷에 웹 사이트를 올릴 예정
- Cloud Shell을 켜고 React 앱 생성할 예정
![](imgs/Pasted%20image%2020250826160039.png)

웹 브라우저에서 "Create React App" 검색 및 이동
- 설명문 중에서 npm install 명령어 부분을 복사하여 "create-react-app" 라이브러리를 설치합니다.
![](imgs/Pasted%20image%2020250826160611.png)

Cloud Shell에서 "create-react-app" 라이브러리 설치
- 실행 결과를 보면 정상적으로 설치된 것을 볼수 있음
```shell
npm install -g create-react-app
```
![](imgs/Pasted%20image%2020250826160827.png)
![](imgs/Pasted%20image%2020250826160854.png)

React 앱 생성
```shell
npx create-react-app tutorial  
cd tutorial
npm start
```

![](imgs/Pasted%20image%2020250826161603.png)

로컬 주소로 이동해봅니다.
- 실행 결과 정상적으로 앱이 실행된 것을 볼수 있음
![](imgs/Pasted%20image%2020250826161617.png)

gcp-pjt-share-web 버킷을 선택한 다음에 **"웹사이트 구성 수정"** 버튼을 클릭합니다.
![](imgs/Pasted%20image%2020250826161728.png)

기본 페이지 및 오류 페이지를 입력
![](imgs/Pasted%20image%2020250826161749.png)

React 앱 빌드
- Cloud Shell에서 진행
- 빌드한 파일을 Cloud Storage에 업로드하여 웹 호스팅 예정
```shell
npm run build
```
![](imgs/Pasted%20image%2020250826161938.png)

빌드 결과물 다운로드
- build 디렉토리를 다운로드
![](imgs/Pasted%20image%2020250826162103.png)

다운받은 zip 파일을 압축 해제하고 확인
![](imgs/Pasted%20image%2020250826162144.png)

build 디렉토리에 있는 모든 내용을 버킷에 업로드
![](imgs/Pasted%20image%2020250826162315.png)

버킷의 권한 탭으로 이동
- 권한에서 액세스 권한 부여 추가
![](imgs/Pasted%20image%2020250826162432.png)

주 구성원 추가 확인
![](imgs/Pasted%20image%2020250826162451.png)

네트워크 서비스 -> 부하 분산 서비스로 이동
- 부하 분산기 만들기 선택
![](imgs/Pasted%20image%2020250826162533.png)

부하 분산 생서 정보 입력
![](imgs/Pasted%20image%2020250826163259.png)
![](imgs/Pasted%20image%2020250826163324.png)
![](imgs/Pasted%20image%2020250826163330.png)
![](imgs/Pasted%20image%2020250826163340.png)

생성 정보 입력
![](imgs/Pasted%20image%2020250826163509.png)

SSL 인증서 생성
- nemo1107.store 도메인은 가비아에서 구입하였음
![](imgs/Pasted%20image%2020250826163624.png)

백엔드 구성
- 백엔드 버킷 만들기 선택
![](imgs/Pasted%20image%2020250826163721.png)

![](imgs/Pasted%20image%2020250826163800.png)

백엔드 구성 생성 확인
![](imgs/Pasted%20image%2020250826163815.png)

라우팅 규칙
![](imgs/Pasted%20image%2020250826163833.png)

부하 분산기 생성 확인
- 생성하는데 5분 정도 소요됨
![](imgs/Pasted%20image%2020250826163913.png)

부하 분산기를 생성하였으면 부하 분산기의 IP 주소를 도메인(web.nemo1107.store)과 연결 설정해야 합니다.
- 34.144.234.198 IP 주소를 web.nemo1107.store 와 연결하도록 도메인 레코드 수정합니다.
![](imgs/Pasted%20image%2020250826164013.png)

저는 nemo1107.store 도메인을 가비아에서 구입하였기 때문에 해당 사이트의 레코드를 다음과 같이 수정하였습니다.
![](imgs/Pasted%20image%2020250826164148.png)

웹 브라우저에 https://web.nemo1107.store 접속하여 확인
- 실행 결과를 보면 정상적으로 리액트 앱이 실행중  인것을 확인할 수 있음
- 해당 결과는 SSL 인증서가 Active 상태여야 가능함
![](imgs/Pasted%20image%2020250827120948.png)


### 리액트 소스 코드 수정후 다시 배포하기
리액트 앱이 저장된 버킷으로 접근합니다.
![](imgs/Pasted%20image%2020250827122135.png)

Cloud Shell을 통하여 개발 환경에 있는 tutorial 디렉토리로 이동합니다.
- src 디렉토리에서 App.js 파일을 수정하여 다시 버킷에 배포하도록 할 예정
```shell
cd tutorial/src
```
![](imgs/Pasted%20image%2020250827122403.png)

App.js 수정
```shell
vim App.js
```
![](imgs/Pasted%20image%2020250827122919.png)
- Learn React 텍스트를 Learn React 123123으로 변경

리액트 빌드
```shell
npm run build
```

Cloud Shell의 다운로드 기능을 이용하여 build 디렉토리 다운로드
![](imgs/Pasted%20image%2020250827123114.png)

다운로드한 build 디렉토리의 내용물을 버킷에 덮어쓰기함
![](imgs/Pasted%20image%2020250827123208.png)

다시 웹 브라우저를 이용하여 요청을 날려봅니다.
- 실행 결과를 보면 다시 변경하여 배포했음에도 변경사항이 반영되지 않았음
- 원인은 부하 분산기가 여전히 캐싱 데이터를 가지고 있기 때문
![](imgs/Pasted%20image%2020250827123412.png)

부하 분산 서비스로 이동, 캐시 무효화 상단 메뉴로 이동
![](imgs/Pasted%20image%2020250827123529.png)

캐시 무효화 정보 입력
![](imgs/Pasted%20image%2020250827123619.png)

다시 웹 브라우저에 요청합니다.
- 실행 결과를 보면 변경사항이 반영됨
![](imgs/Pasted%20image%2020250827123639.png)

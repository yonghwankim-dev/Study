
## GCP 회원가입하기

### GCP 플랫폼 들어가기
- 링크 : [GCP](https://cloud.google.com/free?utm_source=google&utm_medium=cpc&utm_campaign=japac-KR-all-ko-dr-BKWS-all-core-trial-EXA-dr-1710102&utm_content=text-ad-none-none-DEV_c-CRE_765685091655-ADGP_Hybrid+%7C+BKWS+-+EXA+%7C+Txt++-+Generic+Cloud+-+Cloud+Generic+-+Core+GCP+-+KR_ko-KWID_43700082396039665-kwd-87853815&userloc_1009880-network_g&utm_term=KW_gcp&gclsrc=aw.ds&gad_source=1&gad_campaignid=20424845163&authuser=1&gclid=Cj0KCQjw-4XFBhCBARIsAAdNOkveez5L20LkSe3g_7uHWzQCafd72aW1wHxnfudfIklrKssTWv_bzIkaAvvnEALw_wcB)

### GCP 무료 플랜 가입하기
무료 플랜을 사용하기 위한 가입을 진행합니다.
![[Pasted image 20250818133936.png]]
- 가입을 진행하면서 결제 정보 또한 입력하여 가입을 마칩니다.

무료 플랜 가입을 마치면 Console 버튼을 눌러서 GCP 콘솔 페이지로 입장합니다.
![[Pasted image 20250818140236.png]]


## 프로젝트 탐색해보기
다음 화면에서 My First Project를 클릭합니다.
![[Pasted image 20250818141318.png]]

클릭하면 다음과 같이 "My First Project"라는 이름의 프로젝트가 생성되어 있습니다. 해당 프로젝트는 GCP 가입시 GCP에서 자동적으로 만들어주는 프로젝트입니다. 프로젝트에는 ID가 존재하는데 프로젝트 이름은 변경되어도 ID 값은 변경되지 않습니다. 이 값은 프로젝트는 식별해주는 식별자 값입니다.
![[Pasted image 20250818141416.png]]

"New Project" 버튼을 클릭하여 새로운 프로젝트를 만들어봅니다.
![[Pasted image 20250818142034.png]]

새로 만든 프로젝트로 이동하면 다음과 같이 생성을 확인할 수 있습니다.
![[Pasted image 20250818142120.png]]

왼쪽 사이드바 메뉴에서 대시보드 페이지로 이동합니다.
![[Pasted image 20250818142339.png]]

대시보드 페이지를 입장할 수 있습니다.
![[Pasted image 20250818142410.png]]
- 프로젝트에 대한 정보와 그 밑에 여러가지 리소스를 사용할 수 있습니다.

## 서비스 둘러보기
서비스는 왼쪽 사이드바 메뉴를 통해서 볼수도 있고 검색창을 통해서도 검색해서 둘러볼 수 있습니다.
![[Pasted image 20250818143420.png]]
![[Pasted image 20250818143429.png]]

### Compute Engine
Cpute Engine 서비스는 일반적인 서버를 사용할 수 있는 서비스입니다. AWS 클라우드로는 EC2 서비스에 해당됩니다.
![[Pasted image 20250818145810.png]]

MarketPlace 메뉴로 들어가서 Compute Engine API를 검색하고 "사용" 버튼을 클릭하여 Compute Engine을 사용할 수 있도록 합니다.
![[Pasted image 20250818150141.png]]

### 데이터베이스
서비스 메뉴에서 데이터베이스를 선택하면 다양한 데이터베이스를 선택하고 실행할 수 있습니다.
![[Pasted image 20250818150551.png]]

### Cloud Run
**GCP Cloud Run**은 구글 클라우드에서 제공하는 **서버리스(Serverless) 컨테이너 실행 서비스**입니다. 쉽게 말하면, 컨테이너로 만든 애플리케이션을 **서버 관리 없이** 배포하고 실행할 수 있는 서비스입니다.

### Cloud Function
**GCP Cloud Functions**는 구글 클라우드에서 제공하는 **서버리스(Function as a Service, FaaS) 컴퓨팅 서비스**입니다.
간단히 말하면, **코드 단위(함수 단위)로 작성해서 이벤트가 발생할 때만 실행되는 서비스**입니다. AWS Cloud의 람다(Lambda)와 동일한 기능의 서비스입니다.

### IAM 
GCP IAM(Identity and Access Management)은 구글 클라우드에서 **사용자, 그룹, 서비스 계정** 등에 대해 **리소스 접근 권한을 관리하는 서비스**입니다.

간단히 말하면, 누가 어떤 리소스를 어떠한 권한으로 접근할수 있는지 관리하는 서비스입니ㅏㄷ.


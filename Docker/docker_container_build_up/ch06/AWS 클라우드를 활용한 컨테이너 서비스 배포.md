
# 6.1 AWS 클라우드 ECS
## 6.1.1 AWS 컨테이너 오케스트레이션 도구
컨테이너 생성과 종료, 자동 배치 및 복제, 로드 밸런싱, 클러스터링, 장애 복구, 스케줄링에 대한 요구사항을 채워주기 위해서 컨테이너 오케스트레이션 도구가 있습니다. 대표적인 도구는 다음과 같습니다.
- 구글, 쿠버네티스
- 도커, 스웜
- 아파치, 메소스
- 하시코프, 노마드
- 아마존, 엘라스틱 컨테이너 서비스(ECS), 엘라스틱 쿠버네티스 서비스(EKS)

아마존 ECS는 클러스터링 사앹 확인, 리소스 요구와 가용성 요구사항을 파악 가능, 컨테이너 확장 기능을 지원합니다. 
아마존 ECS의 두가지 호스팅 방법
- 서버를 두지 않고 클라우드 인프라에서 애플리케이션을 호스팅하는 파게이트(Fargate) 서버리스 방식
- 아마존 EC2 인스턴스를 서버로 구성한 도커 기반 서비스 인프라를 호스팅하는 방식

## 6.1.2 AWS 클라우드 가입
실습을 진행하기 위해서 AWS 프리티어로 가입합니다.

# 6.2 AWS 클라우드 EC2 + Docker +ECR 구성해보기
이번 절에서는 AWS 계정을 이용해 다음 작업을 구성합니다.
- AWS 클라우드에 서버 구성하기 -> EC2
- EC2에 도커 설치
- Dockerfile을 이용해서 이미지 생성
- 생성된 이미지를 AWS 클라우드 컨테이너 이미지 정상소에 업로드하기 -> ECR

## 6.2.1 도커 컨테이너를 위한 서버 구성: AWS EC2 인스턴스
이번 절에서는 EC2 서버에 도커를 설치하고 AWS ECR에 Dockerfile로 빌드한 이미지를 업로드 해봅니다.

AWS 관리 콘솔로 로그인합니다.
![[img/image-575.png]]
![[img/image-576.png]]

EC2 인스턴스 서비스 페이지로 이동한 다음에 EC2 인스턴스를 생성하려고 합니다.
![[img/image-577.png]]

EC2 생성 페이지에서 ec2 이름과 AMI를 선택합니다.
- 이름 : docker-EC2
- AMI : Amazon Linux 2023 AMI 64bit
![[img/image-578.png]]

인스턴스 유형 및 키페어를 선택합니다.
![[img/image-579.png]]

네트워크 설정에서는 기본 VPC와 기본 보안 그룹을 선택합니다. 보안 그룹은 추후 생성하여 수정할 예정입니다.
![[img/image-580.png]]

스토리지 구성에서 30GIB를 입력하고 인스턴스를 시작합니다.
![[img/image-581.png]]

새 보안 그룹을 생성합니다. 포트는 SSH 원격 접속을 위해서 22번 TCP 포트를 설정합니다.
- 이름: ECS-sg
- 설명: ECS Security group
![[img/image-582.png]]

생성한 인스턴스를 선택한 다음에 보안 그룹을 ECS-sg로 변경합니다.
![[img/image-583.png]]

인스턴스 생성을 확인합니다.
![[img/image-584.png]]

nemo.pem 키를 이용하여 원격 접속해보겠습니다.
```
ssh -i "nemo.pem" ec2-user@3.39.223.231.ap-northeast-2.compute.amazonaws.com
```

위와 같이 지정하는 방법도 있고 ~/.ssh/config 파일에 다음과 같이 원격접속 정보를 지정한 다음에 접속하는 방법도 있습니다.
```txt
Host nemo
HostName 3.39.223.231
User ec2-user
Port 22
IdentityFile ~/.ssh/nemo.pem
```

```shell
ssh nemo
```
![[img/image-585.png]]
실행 결과를 보면 원격 접속하였습니다.

## 6.2.2 EC2 인스턴스에 도커 설치 및 테스트
원격 접속한 EC2 인스턴스에 도커를 설치합니다.
```shell
sudo yum update -y
sudo yum install -y docker
sudo usermod -aG docker ec2-user
sudo systemctl enable docker
sudo systemctl restart docker
sudo reboot
# 재시작 확인뒤 다시 원격접속한 다음에 docker version의 서버(Server:)를 통해 정상 동작하는 도커를 확인합니다.
docker version
```
![[img/image-586.png]]

4.3.1 절의 실습 4-2 예제를 통해서 테스트해봅니다. 우선은 작업할 디렉토리를 생성합니다.
```shell
mkdir LABs
cd LABs
```

git 패키지를 설치합니다.
```shell
sudo yum -y install git
git clone https://github.com/brayanlee/webapp.git
ls
```
![[img/image-587.png]]

webapp 디렉토리로 이동한 다음에 Dockerfile을 작성합니다.
```shell
cd webapp
ls
mkdir dockerfiles
cd dockerfiles
vim Dockerfile
```

Dockerfile
```Dockerfile
# 베이스 이미지 설정
FROM ubuntu:14.04
# 작성자 정보 작성
MAINTAINER "nemo <yonghwankim.dev@gmail.com>"
# 이미지 설명
LABEL "purpose"="container web application practice."
# apt 업데이트 및 필요한 패키지 설치
RUN apt-get update && apt-get install -y apache2 vim curl
# 웹 소스 압축 파일을 아파치의 기본 웹 페이지 경로에 복사
# ADD 명령어는 압축 파일을 해제하여 경로에 복사하는 장점을 가지고 있음
ADD webapp.tar.gz /var/www/html
# 작업 디렉토리 설정
WORKDIR /var/www/html
# 80 포트 노출
EXPOSE 80
# 컨테이너 실행시 아파치 데몬 실행
CMD /usr/sbin/apachectl -D FOREGROUND
```

webapp 디렉토리로 이동한 뒤에 이미지를 빌드합니다.
```shell
webapp $ docker build -t webapp:ecr1.0 -f ./dockerfiles/Dockerfile .
```

생성한 이미지를 확인합니다.
```shell
docker images
```

![[img/image-588.png]]

컨테이너를 실행해서 테스트해봅니다.
```shell
docker run -it -d --name=webapp_ecr -p 10000:80 webapp:ecr1.0
```

컨테이너 실행을 확인합니다.
```shell
docker ps
```
![[img/image-589.png]]

웹 접근이 가능한지 curl 명령어로 확인해봅니다.
```shell
curl localhost:10000
```
![[img/image-590.png]]
실행 결과를 보면 정상적으로 페이지가 로딩되었습니다.

EC2 인스턴스의 보안 그룹에서 10000번 TCP 포트를 추가합니다.
![[img/image-591.png]]

웹 브라우저를 이용해서 서버를 확인합니다.
![[img/image-592.png]]
위와 같은 사이트가 접속되면 성공입니다.

## 6.2.3 AWS 기반의 이미지 저장소(ECR) 생성
ECR은 도커 이미지를 제공하는 AWS 개별 이미지 저장소입니다. 이 저장소에 이미지를 업로드할수 있습니다. 이 저장소에 이미지를 배포하려면 AWS 시스템 권한이 필요합니다.

다음 실습을 통해서 아마존 사용자 계정을 생성합니다.
우선은 IAM 서비스 페이지로 이동합니다.
![[img/image-593.png]]

왼쪽 사이드 메뉴에서 **사용자**를 선택하고 사용자 생성을 클릭합니다.
![[img/image-594.png]]

사용자 이름을 "ecsuser"로 입력하고 다음 버튼을 클릭합니다.
![[img/image-595.png]]

권한 설정 페이지에서 AdministratorAccess를 선택합니다.
![[img/image-596.png]]

사용자 생성 버튼을 눌러서 사용자를 생성합니다.
![[img/image-597.png]]

생성한 사용자의 상세 페이지 -> 보안 자격 증명 탭으로 이동합니다.
![[img/image-598.png]]

액세스 키 만들기 버튼을 클릭합니다. 그리고 CLI 메뉴를 선택한 다음에 다음을 클릭합니다.
![[img/image-599.png]]

액세스 키를 생성하였으면 .csv 파일 다운로드를 클릭하여 다운로드합니다.
![[img/image-600.png]]

다시 ec2 인스턴스로 원격 접속한 다음에 **자격증명 작업을 수행**합니다.
```shell
webapp$ aws configure
AWS Access Key ID [None]: {액세스 키 아이디 입력}
AWS Secret Access Key [None]: {스키릿 액세스 키 입력}
Default region name [None]: ap-northeast-2
Default output format [None]: json
```

.aws 디렉토리에 있는 config 파일을 출력하여 구성 내용을 확인합니다.
```shell
cat ~/.aws/config
```
![[img/image-601.png]]

```shell
cat ~/.aws/credentials
```
```
[default]
aws_access_key_id = {...}
aws_secret_access_key = {...}
```

ECR 저장소에 로그인하기 위해서 aws ecr 명령으로 접속 정보를 취득합니다.
```shell
aws ecr get-login-password --region ap-northeast-2 | docker login -u AWS --password-stdin 851725574055.dkr.ecr.ap-northeast-2.amazonaws.com
```

![[img/image-602.png]]
경고성 메시지의 내용은 인증정보가 config.json에 파일로 저장되었기 때문에 경고하는 것입니다.

aws 명령어를 이용하여 다음과 같이 ECR 서비스의 저장소를 생성할 수 있습니다.
```shell
aws ecr create-repository --repository-name "webapp"
```
![[img/image-603.png]]

ECR 저장소 생성을 확인합니다.
![[img/image-604.png]]

ec2 인스턴스 터미널에서 도커 허브에 업로드했던 방식처럼 태그를 설정합니다. 이때 ECR 연결 주소는 리포지토리에서 제공되는 URI 주소를 사용합니다.
```shell
docker image tag webapp:ecr1.0 \
851725574055.dkr.ecr.ap-northeast-2.amazonaws.com/webapp:ecr1.0

docker push 851725574055.dkr.ecr.ap-northeast-2.amazonaws.com/webapp:ecr1.0
```
![[img/image-605.png]]

관리 콘솔에서 업로드된 이미지를 확인해봅니다.
![[img/image-606.png]]
실행 결과를 보면 ecr:1.0 이미지가 업로드되었습니다.

aws 명령을 통해서도 조회할 수 있습니다.
```shell
aws ecr list-images --repository-name=webapp
```
![[img/image-607.png]]

# 6.3 AWS 클라우드 ECS 구성 워크숍
아마존 ECS는 컨테이너 애플리케이션 서비스를 위한 배포, 관리를 쉽게 조정할 수 있고, 클러스터 기반으로 운영되기 때문에 로드 밸런싱 및 자동 규모 조정 등 여러 유연성 있는 서비스를 통한 자동화된 컨테이너 배치와 확장이 가능한 완전 관리형 컨테이너 오케스트레이션 서비스입니다.

다음은 일반적인 EC2 기반의 ECS를 사용하기 위한 작업 순서입니다.
1. 컨테이너 관리 방식 지정
	- 도커를 설치하고 컨테이너 환경을 제공하기 위한 환경을, 서버리스 방식으로 하면 **Fargate**를 선택하고, 일반 서버 방식으로 하면 **EC2 인스턴스**를 선택합니다.
2. 작업 정의 구성
	- 도커 이미지, 컨테이너 개수, 컨테이너에 할당되는 자원에 대한 내용을 정의합니다.
3. 클러스터 구성
	- EC2 인스턴스, VPC 네트워크, 보안 그룹, IAM 사용자 구성 등을 처리하는 **AWS CloudFormation 스택**을 생성합니다.
4. 아마존 ECS 서비스 구성
	- 도커 컴포즈와 같이 다중 컨테이너 서비스를 실행하기 위한 구성으로 작업 정의 인스턴스를 실행 . 및관리하여 문제 있는 작업은 ECS 서비스에 의해서 유지 관리됩니다.
5. 서비스 확인
	- 구성한 서비스를 확인하는 단계입니다. 웹 서비스인 경우에는 브라우저를 이용해서 확인하고 데이터베이스 서비스인 경우에는 클라이언트 도구로 확인합니다.

## 6.3.1 AWS ECS 워크숍: 작업 순서
워크숍은 Nginx 웹 서비스 기반의 장고 애플리케이션 서비스를 AWS ECS 기반으로 구현하는 작업입니다. 다음 그림은 ECS 전체 서비스 구조입니다.
![[img/image-608.png]]

## 6.3.2 AWS ECS 워크숍: 인프라 구성
### 가상 프라이빗 클라우드(VPC) 생성
VPC 서비스 페이지로 이동한 다음에 VPC 생성 버튼을 클릭합니다.
![[img/image-609.png]]

VPC 생성에 필요한 정보를 입력합니다.
![[img/image-610.png]]

VPC 생성을 확인합니다.
![[img/image-611.png]]

### 서브넷 생성
외부 연결이 가능한 2개의 public subnet과 외부 연결이 차단되는 private subnet 2개를 ecs-vpc에 생성합니다.
![[img/image-612.png]]

서브넷 생성 페이지에서 다음과 같이 ecs-vpc를 선택하고 서브넷을 설정합니다.
![[img/image-613.png]]

![[img/image-614.png]]
![[img/image-615.png]]
![[img/image-616.png]]
![[img/image-617.png]]

서브넷을 생성하고 확인합니다.
![[img/image-618.png]]

### 인터넷 게이트웨이 생성
VPC 서비스의 왼쪽 메뉴에서 인터넷 게이트웨이를 선택하고, 생성 버튼을 클릭합니다.

![[img/image-619.png]]

생성 페이지에서 정보를 입력합니다.
![[img/image-620.png]]

생성한 인터넷 게이트웨이를 선택한 다음에 VPC에 연결 메뉴를 선택합니다. 연결 페이지에서 esc-vpc를 선택하고 연결합니다.
![[img/image-621.png]]

![[img/image-622.png]]
실행 결과를 보면 ecs-vpc에 연결된 것을 볼수 있습니다.

### NAT Gateway 생성
Private Subnet에 위치한 인스턴스가 인터넷과 통신할 수 있도록 NAT 게이트웨이를 생성합니다.
VPC 서비스 페이지의 NAT 게이트웨이 메뉴를 선택하고 NAT 게이트웨이 생성 버튼을 클릭합니다.
![[img/image-623.png]]

NAT 게이트웨이 생성 페이지에서 다음과 같이 입력합니다. 탄력적 할당 IP도 할당합니다. NAT Gateway는 Public Subnet에 위치해야 하기 때문에 esc-public-subnet-1을 선택합니다.
![[img/image-624.png]]

NAT 게이트웨이 생성을 확인합니다.
![[img/image-625.png]]

### 라우팅 테이블 생성
#### public 라우팅 테이블 생성
VPC 서비스 페이지에서 라우팅 테이블을 선택하고 라우팅 테이블 생성 버튼을 클릭합니다.
![[img/image-626.png]]

생성 페이지에서 다음과 같이 ecs-vpc를 선택합니다.
![[img/image-627.png]]

라우팅 테이블을 생성 확인합니다.
![[img/image-628.png]]

생성된 라우팅 테이블을 선택하고 라우팅 편집을 합니다.
![[img/image-629.png]]
위와 같이 외부에서 들어오는 모든 트래픽에 대해서 이전에 생성한 인터넷 게이트웨이로 갈수 있도록 설정합니다.

변경 사항을 저장한 다음에 ecs 라우팅 테이블을 선택한 다음에 서브넷 연결을 편집합니다.
![[img/image-630.png]]

서브넷 연결 편집 화면에서 다음과 같이 public 서브넷 2개를 선택합니다.
![[img/image-631.png]]

#### private 라우팅 테이블 생성
private 라우팅 테이블을 생성하기 위해서 라우팅 테이블 메뉴로 이동한 다음에 라우팅 테이블 생성 버튼을 클릭합니다.
![[img/image-632.png]]

라우팅 테이블 생성 페이지에서 private 라우팅 테이블에 대한 정보를 입력합니다.
![[img/image-633.png]]

ecs-private-route-table을 선택하고 라우팅 편집합니다.
![[img/image-634.png]]

라우팅 편집 화면에서 다음과 같이 NAT 게이트웨이를 선택합니다.
![[img/image-635.png]]

다시 private 라우팅 테이블을 선택한 다음에 서브넷 연결을 편집합니다.
![[img/image-636.png]]

서브넷 연결 편집화면에서 ecs-private-subnet1, ecs-private-subnet2를 선택하고 연결합니다.
![[img/image-637.png]]

### 보안 그룹 생성

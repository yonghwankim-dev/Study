# 4.1 코드로 개발하는 컨테이너 인프라, Dockerfile
## 4.1.1 Iac와 Dockerfile
Dockerfile은 서버의 환경을 코드로 구성하는 방법입니다. Dockerfile 방법은 Iac 개념에서 출발합니다. Iac(Infrastructure as Code)는 인프라를 코드로 개발한다는 개념입니다. Iac가 필요한 이유는 다음과 같습니다.
- CLI를 이용하여 사용자가 직접 명령어 실행시 오류가 발생할 수 있음
- 예를 들어 APM(Apache + PHP + Mysql) 구축시 복잡한 설치 순서를 고려해야 합니다.
- 환경 설정 정보와 설치 프로그램을 요구사항에 맞게 따져봐야함
- 설치 이후에 설정이 있다면 수정해야 하고, 재설치도 불가피할수 있음

위와 같은 IaC 문제를 해결하기 위해서 도커, 앤서블, 쿠버네티스 도구가 있습니다.

## 4.1.2 최적의 Dockerfile 만들기
도커 이미지를 생성하기 위해서는 Dockerfile 파일을 생성해야 합니다. Dockerfile에는 도커 이미지에 필요한 모든 설정이 담긴 파일입니다. 컨테이너 애플리케이션 서비스를 배포하기 위해서는 운영체제, 필요한 프로그램, 환경 변수, 서비스 실행 방법, 데이터 공유 저장 방법 등을 제공하기 위한 기준이 필요합니다. 이러한 것들을 Dockerfile에 명세하여 이미지를 생성해야 합니다. Dockerfile 작성을 위해서는 빌드시간, 이미지 크기, 재사용성, 보안, 유지보수성 등을 고려해야 합니다.

다음은 Dockerfile 작성 권장사항합니다.
- 경량 컨테이너 서비스 제공
- Dockerfile에 담기는 레이어 최소화
- 하나의 애플리케이션은 하나의 컨테이너에 담도록 하기
- 캐시 기능 활용하기
- Iac 환경 개발은 디렉토리 단위로 수행
	- Dockerfile 파일 위치를 기준으로 그 아래의 디렉토리 및 파일은 빌드 컨텍스트에 포함되기 때문에 별도의 디렉토리에 Dockerfile을 위치하여 필요한 것들만 빌드 컨텍스트에 포함하도록 해야 합니다.
- 서버리스 환경으로 개발하기
	- 애플리케이션이 특정 서버 환경에 의존하지 말도록 개발해야 한다는 의미입니다.
	- 특정 OS, 파일 시스템, 하드웨어 환경에 묶이지 않도록 하기
	- 예를 들어 불필요한 ubuntu:20.04와 같은 이미지 기반을 사용하기 등이 있습니다. 너무 무겁고 불필요한 패키지가 많습니다.

# 4.2 Dockerfile 명령어와 이미지 빌드
### 4.2.1 Dockerfile 명령어
#### FROM
- 필수 명령어
- 이미지의 베이스 이미지를 지정합니다.
- 이미지 선택시 이미지 크기를 경량시키기 위해서 slim과 Alpine 이미지를 권장합니다.
- 태그를 별도로 넣지 않으면 latest 버전으로 선택됩니다.
- 예시
	- FROM ubuntu:20.04
	- FROM python:3.9-slime-buster
	- FROM mongo:4.4.4-bionic

#### MAINTAINER
- 이미지 빌드한 사람의 작성자 이름과 이메일을 작성
- 예시
	- MAINTAINER kevin.lee <hylee@dshub.cloud>

#### LABEL
- 이미지 작성 목적으로 버전, 타이틀, 설명, 라이센스 정보 작성, 1개 이상 작성이 가능합니다.
- 예시
	- LABEL purpose = 'Nginx for webserver'
	- LABEL version = '1.0'
	- LABEL description = 'web service application using Nginx'
- 권장사항 방법
```
LABEL purpose = 'Nginx for webserver' \ 
	  version = '1.0' \
	  description = 'web service application using Nginx'
```

#### RUN
- 설정된 기본 이미지에 패키지 업데이트, 패키지 설치, 명령 실행 등에 사용합니다. 1개 이상 작성 가능합니다.
- 예시
	- RUN apt update
	- RUN apt -y install nginx
- 권장사항
	- 다단계 빌드 사용 권장, 각 이미지별로 개별 Dockerfile로 빌드
		- 다단계 빌드는 Dockerfile에서 여러개의 FROM 단계를 사용하여 빌드 과정과 실행 환경을 분리하는 방식입니다. 다단계 빌드를 통해서 빌드 종속성을 제거하고 컨테이너 크기를 줄이고 성능을 최적화 가능합니다.
		- 각 이미지별로 개별 Dockerfile로 빌드하라는 의미는 애플리케이션 구성요소별(예: 백엔드, 프론트엔드, 데이터베이스 등)에 따라 별도의 Dockerfile을 생성하여 관리하라는 의미입니다.
	- RUN 명려어의 개별 명령 개수를 최소화하기 위해서 여러 설치 명령을 연결해서 사용하면 이미지의 레이어 개수를 감소시킬 수 있습니다.
	- autoremove, autoclean, rm -rf/var/lib/apt/lists/* 를 사용하면 저장되어 있는 apt 캐시가 삭제되므로 이미지 크기를 감소시킬 수 있습니다.


**예제1: 다단계 빌드 적용 전(잘못된 방식)**
```Dockerfile
FROM node:18

WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install

COPY . .
RUN npm run build  # 여기서 불필요한 빌드 종속성 포함됨

CMD ["node", "dist/index.js"]

```
- 문제점
	- 빌드 과정에서 사용한 node_modules와 빌드파일(dist/)이 최종 이미지에도 포함됩니다. 이는 이미지의 불필요한 용량 상승입니다.
	- npm install 실행하면서 개발용 패키지도 포함될 가능성이 있습니다.


**예제2: 다단계 빌드 적용 후(권장 방식)**
```Dockerfile
# 1️⃣ 빌드용 컨테이너 (Node.js 환경)
FROM node:18 AS builder

WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install --production  # 실행에 필요한 패키지만 설치

COPY . .
RUN npm run build  # 빌드 실행

# 2️⃣ 실행용 컨테이너 (경량 이미지)
FROM node:18-alpine

WORKDIR /app
COPY --from=builder /app/dist ./dist  # 빌드 결과물만 복사
COPY --from=builder /app/node_modules ./node_modules  # 필요한 패키지만 복사

CMD ["node", "dist/index.js"]

```
- 개선점
	- 빌드 환경과 실행 환경을 분리하였습니다. 빌드에 필요한 패키지는 제거되고 최소한의 파일만 포함됩니다.
	-  **`node:18-alpine`을 사용하여 경량 이미지 적용** → 컨테이너 크기 대폭 감소.
	- **불필요한 개발 종속성(`devDependencies`) 제외**

**Shell 방식**
```Dockerfile
RUN apt update && apt install -y nginx \
								 git \
								 Vim \
								 curl && \
	apt-get clean -y && \
	apt-get autoremove -y && \
	rm -rfv /tmp/* /var/lib/apt/lists/* /var/tmp/*
```

**Exec 방식**
```shell
RUN ["/bin/bash", "-c", "apt update"]
RUN ["/bin/bash", "-c", "apt -y install nginx git vim curl"]
```

#### CMD
컨테이너 실행시 수행하는 명령어들입니다. ENTRYPOINT 명령문과 같이 사용하면 ENTRYPOINT 명령어의 인수로 전달됩니다. 그리고 ENTRYPOINT 명령문 다음에 **여러개의 CMD를 작성해도 마지막 하나만 처리됩니다.** 만약 ENTRYPOINT 없이 단독으로 사용하면 명령어를 이용하여 실행해야 합니다. 컨테이너 실행시 애플리케이션 데몬이 실행되도록 하는 경우 유용합니다.

**Shell 방식**
```Dockerfile
CMD apachectl -D FOREGROUND
```

**Exec 방식**
```Dockerfile
CMD ["/usr/sbin/apachectl", "-D", "FOREGROUND"]
CMD ["nginx", "-g", "daemon off;"]
CMD ["python", "app.py"]
```

#### ENTRYPOINT
컨테이너 실행시 수행하는 명령어를 명세할때 사용하는 명령문입니다.  컨테이너 실행할 명령어 및 인자를 전달합니다. ENTRYPOINT 명령문을 이용해서 명령어를 설정하고, CMD 명령문을 이용해서 기본 인자를 설정하면 타련적으로 이미지를 실행할 수 있습니다. 물론 docker run 실행시 옵션을 이용하여 CMD 명령어를 덮어쓸수 있습니다. 예를 들어 다음과 같이 실행합니다.
```Dockerfile
ENTRYPOINT ["python"]
CMD ["runapp.py"]
```

**사용방법**
CMD와 유사하지만 인자 값을 사용하는 경우에 유용합니다.
```Dockerfile
ENTRYPOINT ["npm", "start"]
ENTRYPOINT ["python", "runapp.py"]
```

**사용예시**
동일 환경에 entrypoint.sh 쉘 스크립트를 이미지에 넣고, 실행 권한을 설정한 후 컨테이너 실행시 entrypoint.sh를 실행합니다.
```Dockerfile
...
ADD ./entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh
ENTRYPOINT ["/bin/bash", "/entrypoint.sh"]
```

**CMD와 ENTRYPOINT 비교**
- ENTRYPOINT는 도커 컨테이너 실행시 항상 수행해야 하는 명령어를 지정해야 합니다.(예: 웹서버, 데이터베이스 데몬 등)
- CMD는 도커 컨테이너 실행시 다양한 명령어를 지정하는 경우에 유용합니다.

#### COPY
**설명**
- 호스트 환경의 파일이나 디렉토리를 이미지 안에 복사하는 명령문입니다.
- 빌드 작업 디렉토리의 외부의 파일은 COPY 할 수 없습니다.
	- 예를 들어 Dockerfile이 `/home/user/project`에 있는데, docker build 명령어를 `/home/user/project`에서 실행할 때 `/home/user/project`는 빌드 컨텍스트가 되고 `/home/user/project` 경로를 제외한 디렉토리 경로는 외부 디렉토리가 됩니다. 이때 COPY 명령어는 `/home/user/project` 내의 파일만 사용할 수 있고, `/home/user`의 파일은 사용할 수 없습니다.


**사용방법**
```Dockerfile
COPY index.html /usr/share/nginx/html
COPY ./runapp.py /
```

**주의사항**
```Dockerfile
COPY ./app
```
위와 같이 작업 영역 전체를 COPY하는 것은 비효율적입니다.

#### ADD
ADD 명령문은 호스트 환경의 파일이나 디렉토리를 이미지 안에 복사하는 방법만이 아니라 URL 주소를 이용하여 다운로드 받아 이미지에 넣을수도 있고, 압축파일(tar, tar.gz)인 경우에는 지정한 경로에 압축 해제하여 이미지에 넣을 수 있습니다.
ADD 명령문은 빌드 컨텍스트에 포함되지 않는 외부 디렉토리의 파일은 이미지에 복사하여 넣을 수 없습니다. **디렉토리를 이미지에 추가할 때는 `/`로 끝나야 합니다.**

**사용방법**
```Dockerfile
ADD index.html /usr/share/nginx/html
ADD http://example.com/view/customer.tar.gz /workspace/data/
ADD website.tar.gz /var/www/html
```

#### ENV
이미지에 환경 변수를 설정합니다. Dockerfile에서 ENV를 설정하면 RUN, WORKDIR 등에서 환경변수를 이용해서 반복을 피할 수 있습니다.

**사용방법**
```Dockerfile
ENV JAVA_HOME /usr/lib/jvm/java-8-oracle
ENV PATH /usr/local/nginx/bin:$PATH
ENV Python 3.9
```

**사용예시**
RUN이나 WORKDIR 등의 이미지 파일 안에서 ENV 명령문으로 설정한 환경 변수를 사용하기 위해서는 `$환경변수명` 을 사용해야 합니다.
```Dockerfile
ENV NODE_VERSION v15.1.0
RUN curl -SLO "http://nodejs.org/dist/$NODE_VESION/node-$NODE_VERSION-linux-x64.tar.gz" \
&& tar -xzf "node-$NODE_VERSION-linux-x64.tar.gz" -C /usr/local --strip-components=1 \
&& rm "node-$NODE_VERSION-linux-x64.tar.gz"
```

#### EXPOSE
컨테이너에서 외부로의 접근을 노출할 **포트와 프로토콜을 설정합니다.** docker run 명령어로 컨테이너 실행시 추가적으로 포트를 추가할 수 있습니다.

**사용방법**
```Dockerfile
EXPOSE 80
EXPOSE 80/tcp
EXPOSE 443
EXPOSE 8080/udp
```

#### VOLUME
볼륨을 이미지 빌드시 미리 설정합니다. VOLUME으로 지정된 컨테이너의 경로는 볼륨의 기본 경로 /var/lib/docker와 자동으로 연결됩니다.

**사용방법**
```Dockerfile
VOLUME /var/log
VOLUME /var/www/html
VOLUME /etc/nginx
VOLUME ["/project"]
```

#### USER
컨테이너의 기본 사용자는 `root`입니다. 애플레킹션이 권한 없이 서비스를 실행할 수 있으면 USER를 통해 다른 사용자로 변경해서 사용이 가능합니다.

**사용방법**
```Dockerfile
RUN ["useradd", "kevinlee"]
USER kevinlee
RUN ["/bin/bash", "-c", "date"]
```

#### WORKDIR
컨테이너 안에서 작업할 경로(디렉토리) 설정하기 위해서 사용합니다. WORKDIR 명령문을 설정하면 RUN, CMD< ENTRYPOINT, COPY, ADD 명령문은 해당 디렉토리를 기준으로 실행합니다. 지정한 경로가 없으면 자동 생성되고, 컨테이너 실행 이후에 컨테이너 접속하면 WORKDIR 명령문으로 지정한 경로로 접속합니다.

**사용방법**
```Dockerfile
WORKDIR /workspace
WORKDIR /usr/share/nginx/html
WORKDIR /go/src/app
```

#### ARG
docker build 명령어를 실행하는 시점에 변수 값을 전달하기 위해서 `--build-arg=인자`  옵션을 사용합니다.
비밀키, 계정 비밀번호 같은 **시크릿 정보를 사용시 이미지에 그대로 존재해서 노출될 위험이 있기 때문에 주의해야 합니다.**

**사용방법**
이미지 파일에 ARG 명령문 정의
```Dockerfile
# Dockerfile에 ARG 변수를 정의
ARG db_name
```

이미지 파일을 기반으로 이미지를 빌드하는 경우에 `--build-arg` 옵션을 이용하여 인자 값을 전달합니다.
```shell
docker build --build-arg db_name=jpub_db .
```

입력받은 변수값을 다음과 같이 명령에 사용합니다.
```Dockerfile
CMD db_start.sh -h 127.0.0.1 -d ${db_name}
```

#### ONBULID
베이스 이미지가 빌드될 때는 실행되지 않지만, 해당 이미지를 기반으로 다른 Dockerfile이 빌드될때 실행되는 명령어입니다.

ONBUILD를 사용한 베이스 이미지(base.Dockerfile)
```Dockerfile
# 베이스 이미지
FROM node:18

# 하위 이미지에서 실행될 명령어 (트리거)
ONBUILD COPY package.json /app/
ONBUILD RUN npm install
```

베이스 이미지를 사용하는 실제 Dockerfile
```Dockerfile
# base.Dockerfile을 기반으로 하는 새로운 Dockerfile
FROM my-base-image

# `ONBUILD` 명령어가 실행됨
COPY . /app/
CMD ["node", "server.js"]
```
- my-base-image가 빌드할때는 ONBUILD가 실행되지 않음
- my-base-image 기반으로 빌드시 ONBUILD 명령문에 정의된 명령문 실행

ONBUILD 명령문을 사용하는 이유는 반복적으로 사용할 베이스 이미지에서 공통 작업을 자동화할 때 유용합니다.

#### STOPSIGNAL
기본적으로 docker stop 명령어 실행시 컨테이너에게 SIGTERM을 보내서 정상적으로 정지하도록 합니다. 이때 다른 시그널을 전달하고자 하는 경우에 STOPSIGNAL 명령문을 사용합니다.

**사용방법**
```Dockerfile
STOPSIGNAL SIGKILL
```

#### HEALTHCHECK
컨테이너의 프로세스 상태를 체크하고자 하는 경우에 사용합니다.

HEALTHCHECK는 하나의 명령문만 유효하고 여러개 작성하면 마지막에 선언된 HEATHCHECK 명령문만 적용됩니다.

**HEALTHCHECK 옵션**

| 옵션             | 설명       | 기본값 |
| -------------- | -------- | --- |
| --interval=(초) | 헬스 체크 간격 | 30s |
| --timeout=(초)  | 타임 아웃    | 30s |
| --retries=N    | 타임 아웃 횟수 | 3   |

**HEALTHCHECK 상태 코드**

| EIXT 코드      | 설명                   |
| ------------ | -------------------- |
| 0: success   | 컨테이너 정상 상태           |
| 1: unhealthy | 컨테이너 올바르게 작동하지 않는 상태 |
| 2: starting  | 예약된 코드               |
docker ps 명령어를 이용하여 STATUS 컬럼에서 확인 가능합니다.

**사용방법**
1분 마다 CMD에 있는 명령을 실행하여 3초 이상 소요되면 한번의 실패로 간주하고 5번  실패시 컨테이너의 상태는 "unhealthy"로 변경됩니다.
```Dockerfile
HEALTHCHECK --interval=1m --timout=3s --retries=5 \
	CMD curl -f http://localhost || exit 1
```
curl -f 옵션은 HTTP 요청이 실패(4xx, 5xx)하는 경우에 오류 메시지를 출력하지 않고 종료 코드(1)를 반환합니다.
http://localhost에 성공하면 exit 0이 되고 오류시 exit 1이 됩니다.

#### SHELL
Dockerfile 내부에서 사용할 기본 쉘을 지정하는 경우에 사용합니다. 기본값으로 "/bin/sh"가 설정됩니다.

**사용방법**
```Dockerfile
SHELL ["/bin/bash", "-c"]
RUN echo "Docker world!"
```

### 4.2.2 이미지 생성을 위한 Dockerfile 빌드
#### 이미지 빌드
docker build 명령어를 사용하여 Dockerfile을 가지고 이미지를 빌드할 수 있습니다. 명령어 형식은 다음과 같습니다.
```shell
docker build [OPTIONS] 이미지명:[태그] 경로 | URL | 압축파일(tar | tar.gz)
```
- 옵션
	- -t(tag) : 이미지에 태그를 지정하는 경우에 추가합니다. (예: app:1.0)
	- -f(file) : Dockerfile이 아닌 다른 파일명을 사용하는 경우에 추가합니다.
		- 예: -f Dockerfile_nginx
- 이미지명:[태그]
	- 이미지 이름과 태그 설정
	- 태그는 생략 가능, 생략하게 되면 latest로 표시
	- 태그는 버전 관리 차원으로 고려해야 합니다.
		- 예: my-nginx-image:1.19_v1.0
- 경로
	- 디렉토리 단위의 개발을 권장하였고, 현재 경로에 Dockerfile이 있따면 "."을 사용합니다. 또는 Dockerfile이 있는 절대 경로를 작성해도 됩니다.
- URL
	- Dockerfile이 포함된 깃허브 URL을 제공하는 경우에 사용할 수 있습니다.
	- 예: docker build -t phpserver:2.0 github.com/brayanlee/docker-phpserver
- 압축 파일
	- 압축 파일 안에 Dockerfile이 포함되어 있는 경우에 사용할 수 있습니다.
	- 예: docker build -f apple/Dockerfile http://server/appl.tar.gz
		- 위 압축 파일의 appl 디렉토리 안에 있는 Dockerfile을 이미지 빌드에 사용합니다.

#### 왜 Dockerfile이 필요한가?
우리는 신규 서비스에 사용할 애플리케이션 서버 구성이 필요합니다. 우리는 서버를 세팅하고 개발 팀에서 요청한 것들을 준비할 것입니다. 운영체제, 환경 설정, 애플리케이션 서비스 테스트까지 거쳐서 개발팀에서 받은 웹 소스를 넣고 배포합니다. 
우리는 위와 같은 과정을 컨테이너를 실행시킨 다음에 하나하나 설치하고 실행할 수 있습니다.
하지만 위와 같은 과정은 사람이 하나씩 수행하기 복잡하고 시간이 많이 소요됩니다.

위와 같은 문제를 해결하기 위해서 Dockerfile을 사용합니다.

다음 방법들은 서버를 직접 구축하는 것과, 컨테이너를 실행시킨 다음에 서버를 세팅하는 방법, 마지막으로 Dockerfile을 이용해서 이미지를 생성하는 방법을 비교한 것입니다.
##### 방법1: 서버를 직접 구축
서버를 직접 구축해봅니다. 우선은 httpd 패키지를 설치하고 80번 포트가 열려있는지 확인합니다.
```shell
sudo yum update
sudo yum -y install httpd
sudo service httpd start
sudo service httpd status
# httpd가 사용하는 80번 포트가 열렸는지 확인
sudo netstat -nlp | grep 80
```
![[img/image-447.png]]

연결 확인을 위해서 curl 명령어로 요청을 날려봅니다.
```shell
curl localhost:80
```
![[img/image-448.png]]

메인 페이지를 간단하게 변경하고 테스트해봅니다.
```shell
cd /var/www/html/
vim index.html
```

index.html
```
<h1>Welcome to my webserver!</h1>
```

```shell
curl localhost:80
```
![[img/image-449.png]]

웹 프로그래밍을 위해서 PHP를 설치합니다.
```shell
sudo yum -y install php php-cli php-common php-mysql
sudo systemctl start httpd
sudo systemctl enable httpd
```

다음 경로로 이동해서 index.php 파일을 작성합니다.
```shell
cd /var/www/html
vim index.php
```

index.php
```text
<?php
	phpinfo();
?>
```

curl 명령어를 이용하여 index.php 파일을 요청합니다.
```shell
curl localhost/index.php
```
![[img/image-450.png]]

호스트 운영체제에서 위와 같은 방법으로 httpd와 php를 직접 설치하여 서버를 실행시켜 보았습니다. 이러한 인프라 작업은 규모가 클수록 인프라 구성 관리에 부담이 늘어납니다.

#### 방법2: 컨테이너를 실행 시킨 다음에 서버를 구축하는 방법
우선은 docker 프로세스를 이용하여 ubuntu 컨테이너를 생성합니다.
```shell
docker run -it -d --name=myweb -p 8080:80 ubuntu:14.04
docker exec -it myweb bash
```

방법1에서와 같이 컨테이너 환경에서 동일하게 httpd와 php를 설치합니다.
```shell
apt-get update
apt-get install -y apache2
service apache2 start
```

두번째 터미널을 이용하여 호스트 운영체제에서 서버 요청을 날려봅니다.
```shell
curl localhost:8080
```
![[img/image-451.png]]
실행 결과 정상적으로 apache2 홈페이지에서 랜더링 되었습니다.

첫번째 터미널에서 vim을 이용하여 나만의 index.html을 생성합니다.
```shell
mv /var/www/html/index.html /var/www/html/index.html.org
vim /var/www/html/index.html
```

index.html
```html
<h1> Hello, Docker application.</h1>
```

두번째 터미널에서 다시 확인합니다.
```shell
curl localhost:8080
```
![[img/image-452.png]]
정상적으로 변경이 완료되었습니다.

첫번째 터미널에서 컨테이너 안에서 PHP 설치하고 phpinfo() 함수로 확인합니다.
```shell
apt-get -y install php5
vi /var/www/html/index.php
```

index.php
```php
<?php
	phpinfo();
?>
```

컨테이너 안에서 apache2 서비스를 재시작합니다.
```shell
service apache2 restart
```

두번째 터미널에서 php 웹 페이지를 확인합니다.
![[img/image-453.png]]

myweb 컨테이너의 상태를 확인해봅니다.
```shell
docker ps
```
![[img/image-454.png]]

컨테이너에서 변경한 모든 내용을 이미지로 저장한 . 뒤컨테이너로 실행해봅니다.
```shell
docker commit myweb myphpapp:1.0
```
![[img/image-455.png]]

도커 이미지를 확인해봅니다.
```shell
docker images
```
![[img/image-456.png]]

커밋한 이미지(myphpapp:1.0)를 기반으로 phpapp 컨테이너를 실행합니다. 그전에 실행한 myweb은 종료합니다.
```shell
docker stop myweb
docker rm myweb
docker run -it -d --name=phpapp -p "8080:80" myphpapp:1.0
```

```shell
curl localhost:8080
```
![[img/image-457.png]]
실행 결과를 보면 서버로부터 아무 응답이 없습니다. 이는 apache2 프로세스가 실행되고 있지 않기 때문입니다.

직접 phpapp 컨테이너로 접속하여 apache2 서비스를 시작하고 다시 시도해봅니다.
```shell
docker exec -it phpapp bash
```
![[img/image-458.png]]

```
service apache2 start
```

다시 호스트 운영체제에서 curl 명령어를 이용하여 요청해봅니다.
```
curl localhost:8080
```
![[img/image-459.png]]

위와 같이 컨테이너에 접속하여 apache2 프로세스를 다시 실행시켜서 정상적인 결과가 나왔지만, 매번 아파치 서비스를 수동으로 시작해야 한다면 매우 번거로울 것입니다.

위와 같은 문제를 해결하기 위해서는 생성한 베이스 이미지가 컨테이너 실행시 자동으로 아파치 서비스를 실행한다면 편할 것입니다. 이를 위해서 Dockerfile의 CMD를 활용합니다.

그러기 위해서는 호스트 운영체제에서 phpapp 디렉토리를 생성하고 해당 디렉토리로 이동합니다.
```shell
mkdir phpapp
cd phpapp
```

phpapp 디렉토리 안에서 Dockerfile을 생성합니다.
```shell
vim Dockerfile
```

Dockerfile
```Dockerfile
FROM myphpapp:1.0
MAINTAINER nemo <yonghwankim.dev@gmail.com>
EXPOSE 80
CMD ["/usr/sbin/apache2ctl", "-D", "FOREGROUND"]
```

Dockerfile을 이용하여 이미지 빌드합니다.
```shell
docker build -t myphpapp:2.0 .
```

이미지 빌드 결과를 확인합니다.
```shell
docker images | grep myphpapp
```
![[img/image-460.png]]

현재 실행중인 myphpapp:1.0 컨테이너를 제거합니다.
```shell
docker stop phpapp
docker rm phpapp
```

docker run 명령어를 이용하여 myphpapp:2.0 이미지를 기반으로 컨테이너를 실행합니다.
```shell
docker run -it -d --name=phpapp -p 8080:80 myphpapp:2.0
```

curl 명령어를 이용하여 테스트해봅니다.
```shell
curl localhost:8080
```
![[img/image-461.png]]
실행 결과를 보면 별도의 아파치 프로세스를 실행하지 않고도 정상적으로 출력되는 것을 볼수 있습니다. 이는 컨테이너 실행시 CMD 명령문에 의해서 자동 실행된 것이기 때문입니다.

문제점
- 이번 작업도 사용자 의존성을 가지고 docker commit을 이용하여 이미지를 생성하였습니다.  다시 Dockerfile로 다시 빌드하는 문제점을 갖고 있습니다.

#### 방법3: Dockerfile을 이용한 방법
인프라 구성 정보를 코드로 관리하면 애플리케이션 개발시 버전 관리 차원의 소스 코드 관리 가능해져서 변경 이력을 관리할 수 있습니다. 대표적으로 git이 존재합니다. 이처럼 **인프라 구성을 코드로 관리하는 것이 Dockerfile을 사용하는 이유**입니다.

phpapp2 디렉토리를 생성하고 해당 디렉토리로 이동해서 Dockerfile을 생성합니다.
```shell
mkdir phpapp2
cd phpapp2
vim Dockerfile
```

Dockerfile
```Dockerfile
# 베이스 이미지 설정
FROM ubuntu:14.04
# Dockerfile 작성자에 대한 정보 기록
MAINTAINER nemo <yonghwankim.dev@gmail.com>
# 생성하려는 이미지에 대한 설명 작성
LABEL title "IaC, PHP application"
# 필요한 패키지 설치
RUN apt-get update && apt-get -y install apache2 php5 git curl ssh wget
# apache2 환경 변수 설정 다음 값은 apache2 기본값
ENV APACHE2_RUN_USER www-data \
	APACHE2_RUN_GROUP www-data \
	APACHE2_LOG_DIR /var/log/apache2 \
	APACHE2_WEB_DIR /var/www/html \
	APACHE2_PID_FILE /var/run/apache2/apache2.pid
# 기본 웹 페이지 생성
RUN echo 'Hello, Docker Application.' > /var/www/html/index.html
# 테스트 PHP 웹 페이지 생성
RUN echo '<?php phpinfo(); ?>' > /var/www/html/index.php
# 80번 포트 노출
EXPOSE 80
# RUN, CMD, ENTRYPOINT의 명령어 실행되는 기준 디렉토리 설정
WORKDIR /var/www/html
# 이미지가 컨테이너로 실행 시 아파치 서비스를 자동 실행
CMD ["/usr/sbin/apache2ctl", "-D", "FOREGROUND"]
```

Dockerfile을 기준으로 myphpapp:3.0 이미지를 생성합니다.
```shell
docker build -t myphpapp:3.0 .
```

myphpapp:3.0 이미지를 기반으로 컨테이너를 실행합니다.
```shell
docker run -it -d --name=phpapp3 -p 8080:80 myphpapp:3.0
docker ps
```
![[img/image-462.png]]


8080포트가 LISTEN 상태인지 확인합니다.
```shell
sudo netstat -nlp | grep 8080
```


8080포트에 매핑된 PID를 가지고 조회해봅니다.
```shell
ps  -ef | grep {PID}
```
![[img/image-463.png]]

curl 명령어를 이용하여 localhost:8080 으로 요청을 날려봅니다.
```shell
curl localhost:8080
```
![[img/image-464.png]]

php 파일도 요청해봅니다.
```shell
curl localhost:8080/index.php
```
![[img/image-465.png]]

생성한 이미지를 조회해봅니다.
```shell
docker image inspect myphpapp:3.0
```
![[img/image-466.png]]
- RepoTags : myphpapp:3.0이 포함되어 있습니다.

![[img/image-467.png]]
- AUthor : nemo <yonghwankim.dev@gmail.com>로 설정되어 있습니다.

![[img/image-468.png]]
- Config.ExposedPorts : 80/tcp 포트로 설정되어 있습니다.
- Env : APACHE2_RUN_USER, APACHE2_RUN_GROUP, APACHE2_LOG_DIR, APACHE2_WEB_DIR, APACHE2_PID_FILE 환경변수가 설정되어 있습니다.

![[img/image-469.png]]
- Cmd: apache2ctl 프로세스를 시작합니다.
- WorkingDir : /var/www/html 디렉토리로 설정되어 있습니다.
- Labels.title : "IaC, PHP application"이라고 설정되어 있습니다.

### 4.2.3 이미지 빌드 과정
#### Dockerfile 작성 라이프 사이클
Dockerfile을 기반으로 이미지 빌드시 주의할 것은 사용자와의 대화식 처리가 아닌 자동화된 빌드여야 한다는 점입니다.

python_lab 디렉토리 생성 및 이동 후 Dockerfile 파일을 생성합니다.
```shell
mkdir python_lab
cd python_lab
vim Dockerfile
```

Dockerfile
```Dockerfile
FROM ubuntu:18.04
RUN apt-get install python
```

Dockerfile을 기반으로 mypyapp:1.0 이미지를 빌드합니다.
```shell
docker build -t mypyapp:1.0 .
```
![[img/image-470.png]]

위 실행 결과를 보면 이미지 빌드에 실패하였습니다. 이미지 빌드에 실패한 원인은 python 패키지를 찾지 못하였기 때문입니다. 패키지를 찾지 못한 이유는 ubuntu 기반의 이미지 생성시 반드시 apt-get update를 사전에 수행하여 최신 패키지 목록을 유지해야 하기 때문입니다.

다시 Dockerfile을 수정합니다.
```Dockerfile
FROM ubuntu:18.04
RUN apt-get update
RUN apt-get install python
```

docker build 명령어를 이용하여 수정된 Dockerfile를 기반으로 이미지를 빌드합니다.
```
docker build -t mypyapp:1.0 .
```
![[img/image-471.png]]
위 실행 결과를 보면 python 패키지 설치 도중에 정말 설치할것인지 한번 물어보는 구문에서 Abort 되어 이미지 빌드에 실패하였습니다.

위와 같은 문제를 해결하기 위해서는 apt-get install 명령어 설치시 -y 옵션을 추가해야 합니다.
```Dockerfile
FROM ubuntu:18.04
RUN apt-get update
RUN apt-get install -y python
```

다시 이미지 빌드합니다.
```shell
docker build -t mypyapp:1.0 .
```
![[img/image-472.png]]
위 실행결과를 보면 정상적으로 mypyapp 이미지가 빌드되었습니다.

##### 빌드 컨텍스트
- docker build를 실행하는 현재 작업 중인 디렉토리를 **빌드 컨텍스트**라고 부릅니다. 
- 도커에서는 Dockerfile을 이용하여 이미지 빌드시 빈 디렉토리에서 생성하여 빌드하는 것을 권장합니다. 
- -f 옵션으로 Dockerfile이 존재하는 다른 경로 지정도 가능합니다. 해당 옵션으로 빌드하게 되면 빌드 컨텍스트는 Dockerfile 위치와 상과없이 현재 디렉토리의 모든 파일과 디렉토리의 컨텐츠는 빌드 컨텍스트에 포함됩니다.  
- 만약 빌드 컨텍스트에서 제외하고 싶은 대상이 있다면 `.dockerignore` 파일에 작성하여야 합니다.
- 도커 데몬은 Dockerfile 빌드 시작시 Dockerfile에 포함된 모든 내용을 읽어서 유효성 검사를 수행합니다. 에러가 있으면 에러를 출력합니다.

예를 들어 Dockerfile이 다음과 같습니다. RUN 명령문에서 오타가 발생하여 RUNN으로 명시하고 빌드해보겠습니다.
```Dockerfile
FROM ubuntu:18.04
RUNN apt-get update
RUN apt-get install -y python
```

```shell
docker build -t mypyapp:1.0 .
```
![[img/image-473.png]]
실행 결과를 보면 2번째 줄에서 에러가 발생한 것을 볼수 있습니다. 심지어 RUN 명령문 아니냐고 메시지를 출력합니다. 이와 같이 도커 데몬은 이미지 빌드시 유효성 검사도 같이 수행한다는 것을 알수 있습니다.

이번에는 appimage 디렉토리를 생성하고 Dockerfile을 appimage 디렉토리로 이동시키겠습니다. 하지만 현재 위치는 python_lab 디렉토리에 위치합니다.
```shell
mkdir appimage && mv Dockerfile ./appimage
```

그런 다음에 도커 이미지 빌드시 -f 옵션을 사용하여 Dockerfile을 지정하여 이미지를 빌드해보겠습니다.
```shell
docker build -t mypyapp:1.0 -f ./appimage/Dockerfile .
```
![[img/image-474.png]]
다른 경로에 있는 Dockerfile을 -f 옵션으로 지정해서 이미지를 빌드해도 동일하게 빌드 컨텍스트로 전달하여 빌드합니다.

#### 이미지 빌드 과정
다음 실습은 Nginx를 포함하는 이미지 빌드 과정입니다.

appimage 디렉토리를 생성하고 해당 디렉토리로 이동합니다. 그리고 해당 디렉토리 안에서 Dockerfile_nginx 파일을 생성합니다.
```shell
mkdir appimage
cd appimage
vim Dockerfile_nginx
```

Dockerfile_nginx
```Dockerfile
# 베이스 이미지 설정
FROM ubuntu:latest
# 작성자 정보 설정
MAINTAINER "nemo <yonghwankim.dev@gamil.com>"
# 필요 패키지 설치
RUN apt-get update && apt-get install -y nginx curl vim
# nginx 기본 웹 페이지 작성
RUN echo 'Docker Container Application.' > /var/www/html/index.html
# 80 포트 노출
EXPOSE 80
# 컨테이너 시작시 nginx 데몬 자동 실행
CMD ["nginx", "-g", "daemon off;"]
```

위 Dockerfile_nginx를 가지고 이미지를 빌드합니다.
```shell
docker build -f Dockerfile_nginx -t webapp:1.0 .
```
![[img/image-475.png]]

동일한 이미지를 두번째 태그하여 빌드해봅니다.
```
docker build -f Dockerfile_nginx -t webapp:2.0 .
```
![[img/image-476.png]]
위 실행 결과를 보면 2번째 3번째 단계인 RUN 명령문이 캐시된 것을 볼수 있습니다. 이것을 **빌드 캐시** 라고 합니다. docker build 명령어 수행시 빠른 빌드를 위해서 이미지 캐시를 사용합니다. `--no-cache`옵션을 이용해서 캐시를 사용하지 않을 수 있습니다. `--cache-from`을 이용해서 다른 이미지로부터 빌드 캐시를 사용해서 빌드할두 있습니다.

도커 18.09 버전에 Buildkit이 추가되어 이미지 빌드에 향상된 기능을 제공합니다. 기능은 다음과 같습니다.
- 빌드 과정을 병렬 처리해서 더 빠른 빌드 제공
- 사용하지 않은 빌드 단계를 찾아서 비활성화
- 노출하면 안되는 정보가 포함되는 경우 비밀 구축이 가능
- 빌드 주우 빌드 정보에 따라 변경된 파일만 잔송
- 자동 빌드시 빌드 캐시의 우선순위를 정함

#### 실습: Buildkit 적용
Buildkit을 사용하려면 환경 변수 `DOCKER_BUILDKIT = 1`을 설정해야 합니다.
```shell
export DOCKER_BUILDKIT=1
```
![[img/image-477.png]]

Dockerfile_nginx 파일을 이용하여 이미지를 빌드합니다.
```shell
docker build -f Dockerfile_nginx -t webapp:4.0 .
```
![[img/image-478.png]]

또는 다음과 같이 이미지 빌드할 수 있습니다.
```shell
DOCKER_BUILDKIT=1 docker build -f Dockerfile_nginx -t webapp:5.0 .
```
![[img/image-479.png]]

출력 결과를 보고서 형태로 볼 수 있습니다.
```shell
docker build -f Dockerfile_nginx -t webapp:6.0 --progress=plain .
```
![[img/image-480.png]]
![[img/image-481.png]]


# 4.3 Dockerfile을 활용한 다양한 이미지 생성
Dockerfile은 임시 컨테이너를 생성하고 파일에 명시된 명령어를 실행한 다음에 읽기 전용 이미지 레이어를 생성합니다. 이러한 생성-실행-읽기 전용 이미지 생성하는 과정을 반복하여 이미지를 빌드합니다.

예를 들어 다음과 같은 Dockerfile이 존재합니다.
```Dockerfile
FROM ubuntu:20.04
COPY app.py /app
RUN apt-get update && apt-get -y install python python-pip
RUN pip install -r requirements.txt
CMD python /app/app.py
```

위 Dockerfile의 더 빠른 빌드를 위한 개선사항은 다음과 같습니다.
1. Ubuntu 대신 알파인 리눅스(Alpine Linux)와 같이 용량이 작은 리눅스 선택하세요. 또한 알파인 리눅스 기반에 파이썬이 이미 설치되어 있다면 빌드 성능을 향상시킬수 있습니다.
2. COPY에 사용된 소스 코드 복사는 RUN 명령어를 사용한 파이썬과 파이프와 같은 패키짖 ㅗㅇ속성 설치 이후에 작성하세요. 만약 COPY 명령문이 변경되면 그 이후의 모든 레이어의 빌드 캐시는 무효화되기 때문에 다시 레이어 빌드가 발생합니다.

다음은 개선사항을 반영한 Dockerfile입니다.
```Dockerfile
FROM python:3.9.2-alpine
RUN apt-get update && apt-get -y install python python-pip
RUN pip install -r requirements.txt
COPY app.py /app
CMD python /app/app.py
```

최적화된 Dockerfile을 통해서 빌드를 수행하면 명령문에 따른 일기 전용 이미지 레이어가 생성됩니다. 그리고 추가되는 변경 사항을 위해서 끄기 가능한 컨테이너 레이어를 임시로 추가하여 새 파일을 추가하고 수정하게 됩니다. 이러한 과정으로 생성된 이미지 레이어는 /var/lib/docker 디렉토리 아래에 추가됩니다.

위와 같이 생성한 이미지로 여러개의 컨테이너를 실행해도 읽기 전용 이미지 레이어는 보존되며 컨테이너마다 병합된 스냅숏 형태로 제공합니다.

### 4.3.1 다양한 방법의 Dockerfile 작성


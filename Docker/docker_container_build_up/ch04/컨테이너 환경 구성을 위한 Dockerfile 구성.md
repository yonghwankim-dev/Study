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





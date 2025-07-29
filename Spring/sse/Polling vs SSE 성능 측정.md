
실험 환경 구성
1. Spring Boot 서버
	- `/polling` 엔드포인트 (폴링 방식)
	- `/sse` 엔드포인트 (SSE 방식)
2. Apache Benchmark을 이용하여 부하 테스트, Prometheus + Grafana 활용하여 측정
	- CPU/메모리 사용량, 네트워크 트래픽, 응답 시간 측정

서버 성능 측정
1. Apache Benchmark를 이용하여 서버 부하 테스트
```
# 폴링 방식 테스트 (5초 간격, 6회 요청)
ab -n 6 -c 1 -v 3 -H 'Cookie:  accessToken=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkcmFnb25iZWFkOTVAbmF2ZXIuY29tIiwiaWQiOjEsIm5pY2tuYW1lIjoi7J286rCc66-4MTExMSIsInByb3ZpZGVyIjoibG9jYWwiLCJyb2xlcyI6IlJPTEVfVVNFUiIsImlhdCI6MTc0MzEzMjcxMywiZXhwIjoxNzQzMTc1OTEzfQ.CLhC-Z7j9I9kBcPiBxXSnKJ0svsncbyO12mbL-l9dPA; refreshToken=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkcmFnb25iZWFkOTVAbmF2ZXIuY29tIiwiaWQiOjEsIm5pY2tuYW1lIjoi7J286rCc66-4MTExMSIsInByb3ZpZGVyIjoibG9jYWwiLCJyb2xlcyI6IlJPTEVfVVNFUiIsImlhdCI6MTc0MzEzMjcxMywiZXhwIjoxNzQ0MzQyMzEzfQ.2cwcFOCEuqr2dn2qcuAOJYK4Xmegjt4WA6o0eHEHSNY' http://localhost:8080/api/portfolio/1/holdings

# SSE 방식 테스트 (1회 연결 후 6번 스트리밍)
ab -n 1 -c 1 http://localhost:8080/sse
```

2. Prometheus + Grafana로 서버 리소스 모니터링
    - CPU 사용량
    - 메모리 사용량
    - 네트워크 트래픽

curl 명령어를 이용하여 5초 간격 5분간 요청
```shell
curl -H 'Cookie:  accessToken=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkcmFnb25iZWFkOTVAbmF2ZXIuY29tIiwiaWQiOjEsIm5pY2tuYW1lIjoi7J286rCc66-4MTExMSIsInByb3ZpZGVyIjoibG9jYWwiLCJyb2xlcyI6IlJPTEVfVVNFUiIsImlhdCI6MTc0MzEzMjcxMywiZXhwIjoxNzQzMTc1OTEzfQ.CLhC-Z7j9I9kBcPiBxXSnKJ0svsncbyO12mbL-l9dPA; refreshToken=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkcmFnb25iZWFkOTVAbmF2ZXIuY29tIiwiaWQiOjEsIm5pY2tuYW1lIjoi7J286rCc66-4MTExMSIsInByb3ZpZGVyIjoibG9jYWwiLCJyb2xlcyI6IlJPTEVfVVNFUiIsImlhdCI6MTc0MzEzMjcxMywiZXhwIjoxNzQ0MzQyMzEzfQ.2cwcFOCEuqr2dn2qcuAOJYK4Xmegjt4WA6o0eHEHSNY' http://localhost:8080/api/portfolio/1/holdings
```

10개의 스레드로 포트폴리오 상세 조회 결과
메모리 사용량
![[image-2.png]]

CPU 사용량
![[image-3.png]]

네트워크 사용량

디스크 I/O 사용량
![[image-4.png]]

time : {"from":"2025-03-28 15:30:00","to":"2025-03-28 15:35:00"}
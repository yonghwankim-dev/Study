"""
title : 디스크 컨트롤러
date : 2020-10-18
author : 김용환
reference : https://programmers.co.kr/learn/courses/30/lessons/42627
            https://johnyejin.tistory.com/132

detail :
현재 디스크의 실제 소요시간 = 이전 디스크의 종료시간 - 현재 디스크의 요청시간 시점 + 현재 디스크의 소요시간
현재 디스크의 종료시간 = 현재 디스크의 요청시간 시점 + 현재 디스크의 실제 소요시간


"""


def solution(jobs):
    answer = 0  # 작업 시간 평균, 실제 소요시간을 누적하는데 사용
    now = 0  # 현재 작업 시간
    n = len(jobs)  # 기존 jobs의 작업 개수, 작업 시간 평균을 구하기 위해서 사용

    jobs = sorted(jobs, key=lambda x: x[1])  # 작업 소요시간을 오름차순으로 정렬

    while len(jobs) != 0:
        for i in range(len(jobs)):
            if jobs[i][0] <= now:  # 작업 요청 시점이 now보다 같거나 작은지 검사
                now += jobs[i][1]  # 현재 작업 시간에 소요시간 누적, 종료 시간
                # 실제 소요시간 = 종료 시간 - 소요시간, 실제 소요시간을 누적한다.
                answer += (now-jobs[i][0])
                jobs.pop(i)  # 해당 작업을 제거, 처리 완료
                break

            # 해당시점에 아직 작업이 들어오지 않았으면 현재 시간 1초 증가
            # jobs 배열의 index i가 마지막 index이면 now를 1초 증간한다.
            # 19번 라인의 반복문을 계속 반복하는 이유는 jobs 배열의 작업들의 작업 요청시점이 now보다 작은지 검사하기 위해서이다.
            if i == len(jobs)-1:
                now += 1

    return int(answer/n)  # 작업 평균 시간


jobs = [[0, 3], [4, 6], [11, 6]]
solution(jobs)

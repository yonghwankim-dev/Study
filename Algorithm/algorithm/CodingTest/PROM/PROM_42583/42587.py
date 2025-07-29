"""
제목 : 스킬트리
작성일 : 2020-11-23
reference : https://programmers.co.kr/learn/courses/30/lessons/42587
"""

# from collections import deque 공부 후 도전
# enumerate 공부
from collections import deque


def solution(priorities, location):
    answer = 0

    # enumerate(priorities) => 인덱스와 요소 값을 반환
    d = deque([(v, i) for i, v in enumerate(priorities)])

    while len(d):
        item = d.popleft()  # (index, priority)

        if d and max(d)[0] > item[0]:
            d.append(item) # 맨 뒤쪽에 삽입
        else: # 최우선도 item 처리
            answer += 1
            if item[1] == location:
                break
    return answer


solution([2, 1, 3, 2], 2)

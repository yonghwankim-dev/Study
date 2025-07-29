"""
title : 모의고사
date : 2020-10-20
author : 김용환
reference : https://programmers.co.kr/learn/courses/30/lessons/42840


detail :

"""

# 수포자가 찍을 숫자 리스트 생성


def create_answer(answers, answer):
    tester = []  # 수포자가 생성한 답 리스트
    idx = 0

    for _ in range(len(answers)):
        tester.append(answer[idx])
        idx += 1
        if idx >= len(answer):
            idx = 0

    return tester

# 정답 비교


def check_answer(answers, tester):
    idx = 0
    count = 0
    for _ in range(len(answers)):
        if answers[idx] == tester[idx]:
            count += 1
        idx += 1
    return count


def solution(answers):
    answer = []

    tester_answer = [[1, 2, 3, 4, 5],
                     [2, 1, 2, 3, 2, 4, 2, 5],
                     [3, 3, 1, 1, 2, 2, 4, 4, 5, 5]]

    tester = []
    for idx in range(len(tester_answer)):
        tester.append(create_answer(answers, tester_answer[idx]))

    tester_count = []
    for idx in range(len(tester)):
        tester_count.append(check_answer(answers, tester[idx]))

    maximum_val = max(tester_count)

    for idx in range(len(tester_count)):
        if tester_count[idx] == maximum_val:
            answer.append(idx+1)

    answer.sort()

    return answer


solution([1, 2, 3, 4, 5])


"""
2. enumerate 함수
리스트가 있는 경우 순서와 리스트의 값을 전달하는 기능을 가집니다.
enumerate는 “열거하다”라는 뜻입니다.
이 함수는 순서가 있는 자료형(list, set, tuple, dictionary, string)을 입력으로 받아 인덱스 값을 포함하는 enumerate 객체를 리턴합니다.
보통 enumerate 함수는 for문과 함께 자주 사용됩니다

# enumerate 함수
data = enumerate((1, 2, 3))
print(data, type(data))

for i, value in data:
    print(i, ":", value)
print()

output
-> 
0 : 1
1 : 2
2 : 3

def solution(answers):
    pattern1 = [1,2,3,4,5]
    pattern2 = [2,1,2,3,2,4,2,5]
    pattern3 = [3,3,1,1,2,2,4,4,5,5]
    score = [0, 0, 0]
    result = []

    for idx, answer in enumerate(answers):
        if answer == pattern1[idx%len(pattern1)]:
            score[0] += 1
        if answer == pattern2[idx%len(pattern2)]:
            score[1] += 1
        if answer == pattern3[idx%len(pattern3)]:
            score[2] += 1

    for idx, s in enumerate(score):
        if s == max(score):
            result.append(idx+1)

    return result
"""

"""
제목 : 두개 뽑아서 더하기
작성일 : 2020-11-13
reference : https://programmers.co.kr/learn/courses/30/lessons/68644?language=python3


"""


def solution(numbers):
    answer = []

    for i in range(len(numbers)):
        for j in range(i+1, len(numbers)):
            answer.append(numbers[i] + numbers[j])

    answer = sorted(list(set(answer)))
    return answer


numbers = [2, 1, 3, 4, 1]
print(solution(numbers))

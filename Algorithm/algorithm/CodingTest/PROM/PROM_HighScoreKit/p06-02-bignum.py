"""
title : 큰수 만들기
date : 2020-10-24
author : 김용환
reference : https://programmers.co.kr/learn/courses/30/lessons/42883
            https://mungto.tistory.com/45
            https://medium.com/hyeon-hwang/%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%A8%B8%EC%8A%A4-%ED%81%B0-%EC%88%98-%EB%A7%8C%EB%93%A4%EA%B8%B0-lv-1-42883-%EC%88%AB%EC%9E%90-%EB%AC%B8%EC%A0%9C-%EA%B7%B8%EB%A6%AC%EB%94%94-585ce3b8c604

detail :

"""

"""
def solution(number, k):

    n = len(number)
    left_ch = n-k
    min_idx = 0
    max_idx = n-left_ch


    while left_ch > 0:
        if min_idx+left_ch == n:
            answer.append(number[min_idx:])
            break
        number_ = number[min_idx:max_idx+1]
        max_val = max(number_)
        answer.append(max_val)

        min_idx = min_idx + number_.index(max_val)+1
        left_ch -= 1
        max_idx = n-left_ch

    return "".join(answer)


solution("4177252841", 4)
"""


def solution(number, k):
    complete_count = 0
    numbers = list(number)

    while complete_count < k:
        for i in range(len(numbers)-1):
            if number[i] == "9":
                continue
            if numbers[i] < numbers[i+1]:
                numbers.pop(i)
                complete_count += 1
                break

            # if i==len(numbers)-2:
            #     numbers.pop(i+1)
            #     complete_count +=1
            #     break
        
    return "".join(numbers)


solution("999999", 4)

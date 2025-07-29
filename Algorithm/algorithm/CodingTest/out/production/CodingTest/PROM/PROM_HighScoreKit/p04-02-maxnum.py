"""
title : 가장큰수
date : 2020-10-19
author : 김용환
reference : https://programmers.co.kr/learn/courses/30/lessons/42746
            https://wooaoe.tistory.com/82

detail : sort 기준을 x*3을 한 이유는 num의 인수값이 1000이하이기 때문에 3자리수로 맞춘다.  

example) [6,10,2] => [666,101010,222] 가 나오게 되고 첫번째 인덱스부터 아스키 값으로 치환되어 비교한다.
                    만약 값이 동일하다면 그 다음 인덱스와 비교하여 정렬을 수행한다.
                    [22,23,24] -> [222222,232323,242424] 이면 첫번째 인덱스 2가 동일하므로 두번째 인덱스
                    2,3,4를 비교하여 내림차순으로 정렬한다.
                    2-> 50, 3->51, 4->52.
"""


def solution(numbers):
    num = list(map(str, numbers))
    # x*3, 인수값이 1000이하이므로 3자리수로 맞춘뒤 아스키 값 비교
    num.sort(key=lambda x: x*3, reverse=True)

    # int후 str을 하는 이유는 [0,0,0]이 입력될시 "000"->"0"으로 변환하기 위해서이다.
    return str(int("".join(num)))


solution([6, 10, 2])

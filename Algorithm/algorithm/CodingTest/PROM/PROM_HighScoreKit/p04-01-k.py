"""
title : k번째 수
date : 2020-07-20
author : 김용환
reference : https://programmers.co.kr/learn/courses/30/lessons/42748
"""

def solution(array, commands):
    answer = []
    
    for idx in range(len(commands)):
        i, j, k = commands[idx]
        part_arr = array[i-1:j]
        part_arr.sort()
        answer.append(part_arr[k-1])
        
    return answer

"""
다른 사람 풀이
lambda를 사용하여 x에 commands의 1차원 데이터들을 넣는다.
따라서 x는 [2,5,3], [4,4,1], [1,7,3]이 된다.
리스트의 인덱스는 0번째부터 시작한다.
마지막 매핑한 결과를 list에 추가하여 반환한다.
def solution(array, commands):
    return list(map(lambda x: sorted(array[x[0]-1:x[1]])[x[2]-1], commands))

array = [1, 5, 2, 6, 3, 7, 4]
commands = [[2, 5, 3], [4, 4, 1], [1, 7, 3]]
print(solution(array, commands))
"""

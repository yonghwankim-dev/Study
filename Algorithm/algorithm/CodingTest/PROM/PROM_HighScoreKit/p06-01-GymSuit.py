

def solution(n, lost, reserve):
    answer = 0

    temp_lost = set(lost)
    temp_reserve = set(reserve)
    lost = list(temp_lost.difference(temp_reserve))
    reserve = list(temp_reserve.difference(temp_lost))

    print(lost, reserve)

    for i in reserve:
        if i in lost:
            lost.remove(i)
        elif (i-1) in lost:
            lost.remove(i-1)
        elif (i+1) in lost:
            lost.remove(i+1)

    answer = n - len(lost)

    return answer


#solution(5, [2, 4], [1, 3, 5])
#solution(3, [3], [1])
#solution(5, [2, 4], [3])
answer = solution(5, [3, 4, 5], [2, 3, 4])
print(answer)

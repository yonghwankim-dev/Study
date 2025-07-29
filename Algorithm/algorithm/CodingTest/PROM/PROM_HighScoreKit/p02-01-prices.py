def solution(prices):
    answer = []
    for i in range(len(prices)):
        t = 0
        for j in range(i+1, len(prices)):
            if prices[i] <= prices[j]:
                t += 1
            else:
                t += 1
                break
        answer.append(t)
    print(answer)
    return answer


solution([1, 2, 3, 2, 3])

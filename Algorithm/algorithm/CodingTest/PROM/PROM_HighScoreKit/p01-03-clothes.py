
# def solution(clothes):
#     dic = {}
#     answer = 0
#     for item in clothes:
#         dic.update({item[1]: 0})

#     for item in clothes:
#         dic.update({item[1]: dic[item[1]]+1})

#     for key, value in dic.items():
#         dic.update({key: value+1})

#     answer = 1
#     for i in dic.values():
#         answer *= i

#     answer -= 1
#     return answer


def solution(clothes):
    from collections import Counter
    from functools import reduce
    cnt = Counter([kind for name, kind in clothes])
    answer = reduce(lambda x, y: x*(y+1), cnt.values(), 1) - 1
    print("cnt : ", cnt)


clothes = [["yellow_hat", "headgear"], [
    "blue_sunglasses", "eyewear"], ["green_turban", "headgear"]]

solution(clothes)

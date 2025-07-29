# 최대값 구하기
# 입력: 숫자가 n개 들어있는 리스트
# 출력: 숫자 n개 중 최대값


def find_max(List):
    n = len(List)
    max = List[0]
    for i in range(1, n):
        if max < List[i]:
            max = List[i]

    return max


def find_max_index(List):
    n = len(List)
    max = List[0]
    idx = 0
    for i in range(1, n):
        if max < List[i]:
            max = List[i]
            idx = i

    return idx


v = [17, 92, 18, 33, 58, 7, 33, 42]
print(find_max(v))
print(find_max_index(v))

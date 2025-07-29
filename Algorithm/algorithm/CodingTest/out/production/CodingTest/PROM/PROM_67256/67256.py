"""
제목 : 키패드 누르기
작성일 : 2020-11-14
reference : https://programmers.co.kr/learn/courses/30/lessons/67256
"""


def init_keypad():
    # keypad number position 설정
    keyNumber = 1
    pos = {}
    for i in range(3):
        for j in range(3):
            pos[keyNumber] = (i, j)
            keyNumber += 1
    pos["*"] = (3, 0)
    pos[0] = (3, 1)
    pos["#"] = (3, 2)

    return pos


def solution(numbers, hand):
    answer = ''
    pos = init_keypad()
    left_hand_pos = (3, 0)  # 왼손 엄지 초기 위치 => * 버튼
    right_hand_pos = (3, 2)  # 오른손 엄지 초기 위치 => # 버튼

    for number in numbers:
        if number in (1, 4, 7):
            answer += "L"
            left_hand_pos = pos[number]
        elif number in (3, 6, 9):
            answer += "R"
            right_hand_pos = pos[number]
        else:
            left_dist = abs(pos[number][0] - left_hand_pos[0]) + \
                abs(pos[number][1]-left_hand_pos[1])
            right_dist = abs(pos[number][0] - right_hand_pos[0]) + \
                abs(pos[number][1]-right_hand_pos[1])

            if left_dist < right_dist:
                answer += "L"
                left_hand_pos = pos[number]
            elif left_dist > right_dist:
                answer += "R"
                right_hand_pos = pos[number]
            elif left_dist == right_dist:
                if hand == "left":
                    answer += "L"
                    left_hand_pos = pos[number]
                else:
                    answer += "R"
                    right_hand_pos = pos[number]

    return answer


numbers = [1, 3, 4, 5, 8, 2, 1, 4, 5, 9, 5]
hand = "right"
print(solution(numbers, hand))

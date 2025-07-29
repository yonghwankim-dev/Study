
"""
제목 : 스킬트리
작성일 : 2020-11-22
reference : https://programmers.co.kr/learn/courses/30/lessons/49993?language=python3
"""


def solution(skill, skill_trees):
    answer = 0

    for st in skill_trees:
        stack = []
        count = 0
        for s in st:
            if s not in skill:  # 스킬트리 상관없는 스킬인지 검사
                count += 1
            else:
                if s == skill[0]:  # 제일 처음의 스킬인지 검사
                    count += 1
                    stack.append(s)
                else:
                    if stack:
                        # 스킬을 배울수 있는지 검사
                        if stack[-1] == skill[skill.index(s)-1]:
                            count += 1
                            stack.append(s)
                        else:
                            break
                    else:
                        break
            if count == len(st):
                answer += 1

    return answer


"""
def solution(skill, skill_trees):
    answer = 0

    for skills in skill_trees:
        skill_list = list(skill)  # "CBD" => ["C","B","D"]

        for s in skills:
            if s in skill:  # 해당 스킬이 skill 트리순서를 고려할때
                if s != skill_list.pop(0):  # s가 선행 스킬트리와 다를때 반복문 탈출
                    break
        else: # for문이 중간에 끊기지 않고 전부 완료했을때 수행하는 문장
            answer += 1

    return answer
"""

print(solution("CBD", ["BACDE", "CBADF", "AECB", "BDA"]))

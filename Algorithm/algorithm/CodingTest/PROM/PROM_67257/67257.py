import re
from itertools import permutations


def get_opPermu(expr):
    # expr에 쓰인 연산자(*,+,-) 추출
    # "50*6-3*20" => ["*","-"]
    op = list(set(list(("".join(re.split("[\d]", expr)).split())[0])))
    op_permu = list(permutations(op))  # 연산자 순열 생성

    return op_permu


def split_expr(expr):
    # "50*6-3*20" -> [50, '*', 6, '-', 3, '*', 20]
    start = 0
    dummy = []
    for idx in range(len(expr)):
        if expr[idx] in ("*", "-", "+"):
            dummy.append(int(expr[start:idx]))
            dummy.append(expr[idx])
            start = idx+1
    dummy.append(int(expr[start:]))

    return dummy


def cal_postfix(post_expr):
    num_stack = []
    for elem in post_expr:
        if elem in ("*", "-", "+"):
            val1 = num_stack.pop(-1)
            val2 = num_stack.pop(-1)

            if elem == "*":
                elem_sum = val2 * val1
            elif elem == "-":
                elem_sum = val2 - val1
            elif elem == "+":
                elem_sum = val2 + val1

            num_stack.append(elem_sum)
        else:
            num_stack.append(elem)
    return num_stack.pop(0)


def get_maxPostfixValue(spl_expr, op_permu):
    vals = []
    op_stack = []
    post_expr = []
    for op_part in op_permu:
        for item in spl_expr:
            if item in ("*", "-", "+"):
                if not op_stack:
                    op_stack.append(item)
                else:  # 연산자 우선순위 비교
                    _item = None
                    while op_stack:
                        if op_part.index(item) >= op_part.index(op_stack[-1]):
                            post_expr.append(op_stack.pop(-1))
                        else:
                            _item = item
                            break

                    if _item == None:
                        op_stack.append(item)
                    else:
                        op_stack.append(_item)
            else:
                post_expr.append(item)
        while op_stack:
            post_expr.append(op_stack.pop(-1))

        val = abs(cal_postfix(post_expr))
        vals.append(val)

        op_stack.clear()
        post_expr.clear()
    return max(vals)


def solution(expression):
    op_permu = get_opPermu(expression)
    spl_expr = split_expr(expression)
    answer = get_maxPostfixValue(spl_expr, op_permu)

    return answer


print(solution("100-200*300-500+20"))


"""
import re
from itertools import permutations


def solution(expression):
    # 1
    # expression 안에 있는 operator 추출
    op = [x for x in ['*', '+', '-'] if x in expression]
    op = [list(y) for y in permutations(op)]  # operator 조합 생성

    # \D : 숫자가 아닌 것과 매치 [^0-9]와 동일한 표현
    # 정규식 문자열 앞에 r 문자를 삽입하면 이 정규식은 Raw String 규칙에 의해 백슬레시 2개 대신 1개만 써도 2개를 쓴것과 동일한 의미를 갖게 된다.
    # 정규표현식에서 ( ) 괄호는 그룹을 의미한다., split 메소드의 패턴인자에 괄호를 사용하면 해당 분리 문자도 결과 문자열 포함
    # https://devanix.tistory.com/296
    # 100-200*300-500+20 => ["100","-","200","*","300","-","500","+","20"]
    ex = re.split(r'(\D)', expression)
    
    
    # 2
    a = []
    for x in op:
        _ex = ex[:]
        for y in x:
            while y in _ex: # _ex 리스트에 y 연산자가 있는동안 계속 연산 수행, _ex 리스트에 y 연산자가 없으면 다음 연산자 진행
                tmp = _ex.index(y)
                _ex[tmp-1] = str(eval(_ex[tmp-1]+_ex[tmp]+_ex[tmp+1])) # operator의 이전 숫자와 이후 숫자를 연산하여 결과를 이전 숫자쪽에 저장
                _ex = _ex[:tmp]+_ex[tmp+2:] # operator 이전 expr와 operator 이후 숫자 다음에 있는 expr를 연결하여 _ex에 다시 저장
        a.append(_ex[-1]) # 연산 결과 값(_ex[-1])을 리스트에 추가

    # 3
    return max(abs(int(x)) for x in a)


print(solution("100-200*300-500+20"))

"""

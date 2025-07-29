import math


def exp(n):
    sum = 0
    for i in range(n+1):
        sum += math.pow(i, 2)

    return sum


def exp2(n):
    return ((n)*(n+1)*(2*n+1))/6


print(exp(10))
print(exp2(10))

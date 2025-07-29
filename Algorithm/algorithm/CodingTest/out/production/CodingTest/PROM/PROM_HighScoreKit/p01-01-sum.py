
def sum(num):
    val = 0
    for i in range(num+1):
        val += i
    return val


def sum2(num):
    return ((num)*(num+1))//2


print(sum(100))
print(sum2(100))

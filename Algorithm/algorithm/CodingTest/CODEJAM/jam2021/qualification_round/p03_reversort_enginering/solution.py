import sys
import os
if not os.environ.get("ONLINE_JUDGE"):
    sys.stdin = open('in.txt', 'r')
import time
start_time = time.time()

T = int(input())


def construct(N, C, M):
    # Construct an array with elements from M..N
    # which, when reversorted, incurs a cost of C.
    # Precondition: C is an attainable cost

    if(N == 1):
        return str(M)
    else:
        if((C-1) >= N-2 and (C-1) <= N*(N-1)/2 - 1):
            # If C-1 is within valid bounds for N-1,
            # Then place X at the beginning and recurse directly.
            # Note that the minimum increases to M+1 in recursion.

            return str(M) + " " + construct(N-1, C-1, M+1)
        else:
            delta = int(C - N*(N-1)/2 + 1)
            y = construct(N-1, C - delta, M+1)

            smaller = y.split(" ")
            newarr = [str(M)]
            newarr.extend(smaller)

            ans = " ".join(newarr[:delta][::-1] + newarr[delta:])
            return ans


for case in range(1, T+1):
    N, C = list(map(int, input().strip().split()))

    if C < N-1 or C > N*(N+1)/2 - 1:
        # If C is not within the appropriage bounds,
        # reject immediately:
        print("Case #" + str(case) + ": IMPOSSIBLE")
    else:
        # Otherwise, invoke the recursive mechanism
        # for constructing an array with the appropriate cost:
        A = construct(N, C, 1)
        print("Case #" + str(case) + ": " + A)

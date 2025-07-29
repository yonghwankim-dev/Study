package PROM.PROM_414744.PROM10408;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

class Solution {
    public static int solution(int[] queue1, int[] queue2) {
        int answer = 0;
        Queue<Integer> q1 = toQueue(queue1);
        Queue<Integer> q2 = toQueue(queue2);
        long q1_sum = sum(queue1);
        long q2_sum = sum(queue2);

        if((q1_sum + q2_sum) % 2 == 1){
            return -1;
        }

        while(q1_sum != q2_sum){
            if(answer >= queue1.length + queue2.length + 2){
                return -1;
            }
            if(q1_sum > q2_sum){
                q1_sum -= q1.peek();
                q2_sum += q1.peek();
                q2.add(q1.poll());
            }else if(q1_sum < q2_sum) {
                q1_sum += q2.peek();
                q2_sum -= q2.peek();
                q1.add(q2.poll());
            }
            answer++;
        }

        return answer;
    }
    private static Queue<Integer> toQueue(int[] arr){
        return Arrays.stream(arr).boxed().collect(Collectors.toCollection(LinkedList::new));
    }
    private static int sum(int[] queue){
        return Arrays.stream(queue).sum();
    }


}

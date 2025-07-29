package PROM.PROM_118667;

import java.util.LinkedList;
import java.util.Queue;

class Solution {
    public static int solution(int[] queue1, int[] queue2) {
        int answer = 0;
        Queue<Long> q1 = toLinkedList(queue1);
        Queue<Long> q2 = toLinkedList(queue2);

        int size1 = queue1.length;
        int size2 = queue2.length;
        long sum1 = sum(queue1);
        long sum2 = sum(queue2);

        if(isOdd(sum1 + sum2)){
            return -1;
        }

        while(!isEqualSum(sum1, sum2)){
            if(answer > size1 + size2 + 2){
                return -1;
            }

            if(sum1 < sum2){
                q1.add(q2.peek());
                sum1 += q2.peek();
                sum2 -= q2.peek();
                q2.poll();
            }else{
                q2.add(q1.peek());
                sum2 += q1.peek();
                sum1 -= q1.peek();
                q1.poll();
            }
            answer++;
        }

        return answer;
    }
    private static LinkedList<Long> toLinkedList(int[] arr){
        LinkedList<Long> linkedList = new LinkedList<>();
        for(int i = 0; i < arr.length; i++){
            linkedList.add((long) arr[i]);
        }
        return linkedList;
    }


    private static long sum(int[] arr){
        long sum = 0;
        for(int i = 0; i < arr.length; i++){
            sum += arr[i];
        }
        return sum;
    }

    private static boolean isOdd(long sum){
        return sum % 2 == 1;
    }

    private static boolean isEqualSum(long sum1, long sum2){
        return sum1 == sum2;
    }


}

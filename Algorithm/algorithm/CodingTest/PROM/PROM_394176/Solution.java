package PROM.PROM_394176;

import java.util.HashSet;
import java.util.Set;

public class Solution {
    public static int answer = 0;
    public static Set<String> set = new HashSet<>();
    public String join(int[] numbers, boolean[] isMinused){
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < numbers.length; i++){
            if(isMinused[i]){
                sb.append("-"+numbers[i]);
            }else {
                sb.append("+"+numbers[i]);
            }
        }
        return sb.toString();
    }

    public void go(int[] numbers, int target, boolean[] isMinused, boolean[] isChoiced, int index, int n){
        if(index == n){
            // 합산 하여 검사
            int sum = 0;

            for(int i = 0; i < n ; i++){
                if(isMinused[i]){
                    sum += (numbers[i] * -1);
                }else{
                    sum += numbers[i];
                }
            }

            if(sum == target) {
                answer++;
            }

            return;
        }
        isMinused[index] = true;
        go(numbers, target, isMinused, isChoiced, index + 1, n);

        isMinused[index] = false;
        go(numbers, target, isMinused, isChoiced, index + 1, n);
    }

    public int solution(int[] numbers, int target) {
        int n = numbers.length;
        boolean[] isMinused = new boolean[n];
        boolean[] isChoiced = new boolean[n];

        go(numbers, target, isMinused, isChoiced, 0, n);

        return answer;
    }

    public static void main(String[] args){
        int[] numbers = new int[] {1,1,1,1,1};
        int target = 3;

        int answer = new Solution().solution(numbers, target);
        System.out.println(answer);
    }
}

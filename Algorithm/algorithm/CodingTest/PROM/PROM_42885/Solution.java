package PROM.PROM_42885;

import java.util.Arrays;

public class Solution {
    public static int solution(int[] people, int limit) {
        int answer = 0;
        int left = 0;
        int right = people.length - 1;
        int weight_left, weight_right, weight_sum;

        Arrays.sort(people);
               
        while(left <= right) {
            weight_left = people[left];
            weight_right = people[right];
            weight_sum = weight_left + weight_right;

            // left 사람과 right사람의 무게합이 보트무게 제한에 들어오는 경우
        	if(isNotMoreThanLimit(weight_sum, limit)) {
        		left++; // left는 오른쪽으로 이동
        	}
        	right--; // 제한 상관없이 right은 왼쪽으로 이동
        	answer++;
        }
        return answer;
    }

    private static boolean isNotMoreThanLimit(int weight, int limit){
        return weight <= limit;
    }
}

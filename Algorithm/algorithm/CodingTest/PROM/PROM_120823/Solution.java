package PROM.PROM_120823;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public static int[] solution(int n) {
        List<Integer> answer = new ArrayList<>();

        for(int i = 1; i <= n; i++){
            if(isOdd(i)){
                answer.add(i);
            }
        }

        return answer.stream().mapToInt(Integer::intValue).toArray();
    }
    private static boolean isOdd(int num){
        return num % 2 == 1;
    }
}

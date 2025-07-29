package PROM.PROM_120897;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Solution {
    public int[] solution(int n) {
        int[] answer = {};
        List<Integer> divisors = new ArrayList<>();
        for(int i = 1; i*i <= n; i++){
            if(n % i == 0){
                divisors.add(i);
                divisors.add(n / i);
            }
        }

        Collections.sort(divisors);
        answer = divisors.stream().mapToInt(Integer::intValue).distinct().toArray();
        return answer;
    }
}

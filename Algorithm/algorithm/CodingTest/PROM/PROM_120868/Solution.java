package PROM.PROM_120868;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class Solution {
    private Set<Integer> set;

    public int solution(int[] sides) {
        int answer = 0;
        set = new HashSet<>();
        Arrays.sort(sides); // sides[a,b]

        countWhenBisMaximumSide(sides[0], sides[1]);
        countWhenCisMaximumSide(sides[0], sides[1]);

        answer = set.size();
        return answer;
    }

    private void countWhenBisMaximumSide(int a, int b){
        for(int i = b - a + 1; i <= b; i++){
            set.add(i);
        }
    }

    private void countWhenCisMaximumSide(int a, int c){
        for(int i = c; i < a + c; i++){
            set.add(i);
        }
    }
}

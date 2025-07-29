package PROM.PROM_120835;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

class Solution {
    public int[] solution(int[] emergency) {
        int n = emergency.length;
        int[] answer = new int[n];

        for(int i = 0; i < n; i++){
            for(int j = i; j < n; j++){
                if(emergency[i] <= emergency[j]){
                    answer[i]++;
                }else{
                    answer[j]++;
                }
            }
        }

        return answer;
    }

    public int[] solution2(int[] e) {
        return Arrays.stream(e).map(i -> Arrays.stream(e).boxed().sorted(Comparator.reverseOrder()).collect(Collectors.toList()).indexOf(i) + 1).toArray();
    }
}

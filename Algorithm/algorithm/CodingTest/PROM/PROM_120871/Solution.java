package PROM.PROM_120871;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public int solution(int n) {
        int answer;
        List<Integer>  list = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        int i = 1;
        while(list.size() <= n){
            if (isQualified(i)) {
                list.add(i);
            }
            i++;
        }
        answer = list.get(n - 1);
        return answer;
    }

    private boolean isQualified(int n){
        return !isDividedByThree(n) && !containsThree(n);
    }

    private boolean isDividedByThree(int n){
        return n % 3 == 0;
    }

    private boolean containsThree(int n) {
        return String.valueOf(n).contains("3");
    }
}

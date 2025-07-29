package PROM.PROM_120887;

import java.util.Arrays;

class Solution {
    public int solution(int i, int j, int k) {
        int answer;
        StringBuilder sb = new StringBuilder();
        while(i <= j){
            sb.append(i);
            i++;
        }
        long count = Arrays.stream(sb.toString().split(""))
                .mapToInt(Integer::parseInt)
                .filter(num -> num == k)
                .count();
        answer = (int) count;
        return answer;
    }
}

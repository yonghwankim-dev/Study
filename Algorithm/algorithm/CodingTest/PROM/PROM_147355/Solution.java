package PROM.PROM_147355;

public class Solution {
    public int solution(String t, String p) {
        int answer = 0;
        int p_len = p.length();
        long p_val = Long.parseLong(p);
        for(int i = 0; i <= t.length() - p_len; i++){
            long t_val = Long.parseLong(t.substring(i, i + p_len));
            if(t_val <= p_val){
                answer++;
            }
        }
        return answer;
    }
}

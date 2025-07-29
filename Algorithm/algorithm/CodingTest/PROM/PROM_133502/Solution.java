package PROM.PROM_133502;

public class Solution {

    public static int solution(int[] ingredient) {
        int answer = 0;
        String s = "";
        for(int i : ingredient){
            s += i;
            if(match(s)){
                answer++;
            }
        }

        return answer;
    }

    public static boolean match(String s){
        if(s.length() >= 4){
            return s.charAt(s.length()-4) == '1' &&
                    s.charAt(s.length()-3) == '2' &&
                    s.charAt(s.length()-2) == '3' &&
                    s.charAt(s.length()-1) == '1';
        }
        return false;
    }
}

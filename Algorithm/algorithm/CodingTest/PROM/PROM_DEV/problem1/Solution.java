package PROM.PROM_DEV.problem1;

class Solution {

    public static String solution(String src) {
        char first = src.charAt(0);
        char prev  = first;
        int  cnt   = 1;
        StringBuilder sb = new StringBuilder();
        String answer;

        sb.append(first);
        for(int i = 1; i < src.length(); i++, cnt++){
            if(src.charAt(i) != prev){
                prev = src.charAt(i);
                sb.append(getAlphabet(cnt));
                cnt = 0;
            }
        }
        sb.append(getAlphabet(cnt));

        answer = sb.toString();
        return answer;
    }

    private static char getAlphabet(int pos){
        return (char) (64 + pos);
    }
}

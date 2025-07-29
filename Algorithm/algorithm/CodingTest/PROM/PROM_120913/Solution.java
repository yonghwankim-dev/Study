package PROM.PROM_120913;

class Solution {
    public String[] solution(String my_str, int n) {
        String[] answer = new String[my_str.length() / n + 1];
        int pos = 0;
        for(int i = 0; i < my_str.length(); i+=n){
            int end = i + n;
            if(i+n >= my_str.length()){
                answer[pos++] = my_str.substring(i);
            }else{
                answer[pos++] = my_str.substring(i, i+n);
            }

        }
        return answer;
    }
}

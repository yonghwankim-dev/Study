package PROM.PROM_155652;

class Solution {
    public String solution(String s, String skip, int index) {
        StringBuilder answer = new StringBuilder();
        char[] chars = s.toCharArray();
        for(char ch : chars){
            char result = find(ch, skip, index);
            answer.append(result);
        }
        return answer.toString();
    }

    private char find(char ch, String skip, int index){
        int cnt = 0;
        int pos = 1;
        char target = ch;
        while(cnt < index){
            pos = pos > 26 ? 1 : pos;
            target = check((char) (ch + pos));

            if(!skip.contains(String.valueOf(target))){
                cnt++;
            }
            pos++;
        }

        return check(target);
    }

    private char check(char ch){
        return ch > 'z' ? (char) (ch - 26) : ch;
    }
}

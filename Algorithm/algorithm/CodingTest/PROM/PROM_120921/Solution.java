package PROM.PROM_120921;

class Solution {
    public int solution(String A, String B) {
        int answer = -1;

        if(A.equals(B)){
            return 0;
        }

        for(int i = 0; i < A.length(); i++){
            A = moveRight(A);
            answer = A.equals(B) ? i + 1 : answer;
        }

        return answer;
    }

    private String moveRight(String text){
        String last = text.substring(text.length() - 1);
        String movedStr = last + text.substring(0, text.length() - 1);
        return movedStr;
    }
}

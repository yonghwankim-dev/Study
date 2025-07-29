package PROM.PROM_132267;

class Solution {
    public static int solution(int a, int b, int n) {
        int answer = 0;
        

        while(n >= a){
            int coke = (n / a) * b;
            n %= a;
            n += coke;
            answer += coke;
        }

        return answer;
    }
}

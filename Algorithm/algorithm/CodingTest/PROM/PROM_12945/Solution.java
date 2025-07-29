package PROM.PROM_12945;

public class Solution {
    public int solution(int n) {
        int[] fibo  = new int[n+1];

        for(int i = 0; i < fibo.length; i++) {
            if (i < 2) {
                fibo[i] = i;
            }else {
                fibo[i] = (fibo[i - 1] + fibo[i - 2]) % 1234567;
            }
        }
        return fibo[n];
    }

    public static void main(String[] args){
        int n = 5;
        System.out.println(new Solution().solution(n));
    }
}

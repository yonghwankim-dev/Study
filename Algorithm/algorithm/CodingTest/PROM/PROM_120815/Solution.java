package PROM.PROM_120815;

public class Solution {
    public static int solution(int n) {
        int pizza = 6;
        return lcm(n, pizza) / pizza;
    }

    private static int lcm(int a, int b){
        return Math.abs(a * b) / gcd(a, b);
    }

    private static int gcd(int a, int b){
        if(b == 0){
            return a;
        }
        return gcd(b, a % b);
    }

}

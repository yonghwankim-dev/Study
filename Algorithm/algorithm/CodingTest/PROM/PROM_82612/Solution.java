package PROM.PROM_82612;

class Solution {
    public static long solution(long price, long money, long count) {
        long answer = -1;

        long val = price * (count*(count+1)/2);
        answer = val - money;
          
        return (answer > 0 ? answer : 0);
    }
    public static void main(String args[])
    {
    	System.out.println(solution(3,20,4));
    }
}

package PROM.PROM_12934;

public class Solution {

    public static long solution(long n) {
        double x = Math.sqrt(n);
        return (long) (n%x==0 ? Math.pow(x+1, 2) : -1);
    }
    
	public static void main(String[] args)
	{
		System.out.println(solution(121));
	}
}

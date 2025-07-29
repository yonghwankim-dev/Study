package PROM.PROM_12921;

public class Solution {
    
	public static boolean isPrime(int n)
	{
		for(int i=2;i<=Math.sqrt(n);i++)
		{
			if(n%i==0)
			{
				return false;
			}
		}
		return true;
	}
	
	public static int solution(int n)
	{
		int cnt=0;
		for(int i=2;i<=n;i++)
		{
			if(isPrime(i))
			{
				cnt++;
			}
		}
		return cnt;
	}
	
    public static void main(String[] args)
    {
    	System.out.println(solution(10));
    }
}

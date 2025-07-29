package PROM.PROM_77884;


public class Solution {
	
	public static int getDivisorCount(int n)
	{
		int count = 0;
		for(int i=1;i<=n;i++)
		{
			if(n%i==0)
			{
				count++;
			}
		}
		return count;
	}
	
	public static int solution(int left, int right)
	{
		int count=0;
		int sum=0;
		for(int i=left; i<=right;i++)
		{
			count = getDivisorCount(i);
			if(count%2==0)
			{
				sum += i;
			}
			else
			{
				sum -= i;
			}
		}
		return sum;
	}
    
	
	public static void main(String[] args) {
		System.out.println(solution(24, 27));
	}
}

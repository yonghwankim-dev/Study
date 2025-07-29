package PROM.PROM_12943;


public class Solution {

	public static int solution(int num) 
	{
		int cnt = 0;
		long temp = num;
		
		while(temp!=1)
		{			
			if(temp%2==0)
			{
				temp = temp / 2;
			}
			else
			{
				temp = (temp*3)+1;
			}
			
			if(cnt==500)
			{
				return -1;
			}
			cnt++;
		}
		return cnt;
	}

    
	public static void main(String[] args)
	{		
		System.out.println(solution(626331));
	}
}

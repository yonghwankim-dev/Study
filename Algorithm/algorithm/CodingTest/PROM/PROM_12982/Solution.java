package PROM.PROM_12982;

import java.util.Arrays;

public class Solution {
	
	public static int solution(int[] d, int budget)
	{
		Arrays.sort(d);
		int answer = 0;
		for(int i=0;i<d.length;i++)
		{
			if(budget-d[i]>=0)
			{
				answer++;
				budget -= d[i];
			}
			else
			{
				break;
			}
		}
		return answer;
	}
    
	
	public static void main(String[] args) {
//		int[] d = {1,3,2,5,4};
		int[] d = {2,2,3,3};
		int budget = 10;
		System.out.println(solution(d, budget));
	}
}

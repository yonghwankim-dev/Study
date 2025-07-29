package PROM.PROM_42860;


public class Solution {
	public static int getMinUpDown(char target)
	{
		return Math.min(target-'A', 'Z'-target+1);
	}
	public static int solution(String name)
	{
		int n = name.length();
		int answer = 0;
		
		int next = 0;
		int min_move = n-1;	// left->right
		
		while(name.charAt(min_move)=='A' && min_move>0)
		{
			min_move--;
		}
		
		if(min_move<0)
		{
			return answer;
		}
		
		for(int i=0;i<n;i++)
		{
			answer += getMinUpDown(name.charAt(i));
		
			next = i+1;
			while(next<n && name.charAt(next)=='A')
			{
				next++;
			}
			min_move = Math.min(min_move, i+i+(n-next));
		}
		
		answer += min_move;
		
		return answer;
	}
	public static void main(String[] args)
	{
		solution("JAN");
	}
}

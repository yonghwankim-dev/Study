package BOJ.BOJ_1463;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	static int[] dp = new int[1000001];
	public static int solution(int n)
	{
		
		for(int i=2;i<=n;i++)
		{
			dp[i] = dp[i-1] + 1;
			if(i%2==0)
			{
				dp[i] = Math.min(dp[i], dp[i/2]+1);
			}
			if(i%3==0)
			{
				dp[i] = Math.min(dp[i], dp[i/3]+1);
			}
		}
		return dp[n];
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		System.out.println(solution(n));
	}
}

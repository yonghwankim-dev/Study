package BOJ.BOJ_1904;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	public static int[] dp = new int[1000001];
	public static int solution(int n)
	{
		if(dp[n]!=0)
		{
			return dp[n];
		}
		
		if(n<=2)
		{
			return dp[n] = n;
		}
		
		return dp[n] = (solution(n-1) + solution(n-2))%15746;
	}
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		System.out.println(solution(n));
	}
}

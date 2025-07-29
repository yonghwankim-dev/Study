package BOJ.BOJ_2579;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	static int[] dp;
	static int[] arr;
	
	private static int recursion(int i)
	{
		if(i<=0)
		{
			return 0;
		}
		
		int var1 = 0;
		int var2 = 0;
		
		if(i>=3)
		{
			var1 = (dp[i-3]==0 ? recursion(i-3) : dp[i-3]);
			var2 = (dp[i-2]==0 ? recursion(i-2) : dp[i-2]);
		}
		
		return dp[i] = Math.max(
								arr[i] + arr[i-1] + var1,
								arr[i] + var2
								);
	}
	
	public static int solution(int n)
	{			
		// 마지막 계단 이전 계단을 밟은 경우
		int var1 = arr[n] + arr[n-1] + recursion(n-3);
		// 마지막 계단 이전 계단을 밟지 않은 경우
		int var2 = arr[n] + recursion(n-2);
	
		// 위의 두가지 경우중 값이 큰것을 반환
		return Math.max(var1, var2);
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		arr = new int[n+1];
		dp = new int[n+1];
		
		for(int i=1;i<=n;i++)
		{
			arr[i] = Integer.parseInt(br.readLine());
		}
		
		System.out.println(solution(n));		
	}
}

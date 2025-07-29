package BOJ.BOJ_2156;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	
	public static long solution(long[] dp, long[] arr,int n)
	{	
		if(n==1)
		{
			dp[n] = arr[n];
		}
		else if(n==2)
		{
			dp[n-1] = arr[n-1];
			dp[n] = dp[n-1] + arr[n];
		}else if(n>=3)
		{
			dp[1] = arr[1];
			dp[2] = dp[1] + arr[2];
		}
		
		
		for(int i=3;i<=n;i++)
		{
			// n번째 잔(arr[n])을 마신 경우
			// max 첫번째 매개변수 : n번째 잔을 마시고 n-1번째 잔을 마시지 않은 경우
			// max 두번째 매개변수 : n번째 잔을 마시고 n-1번째 잔을 마신 경우
			dp[i] = Math.max(dp[i-2]+arr[i],
					dp[i-3]+arr[i-1]+arr[i]);
			
			// max 첫번째 매개변수 : n번째 잔을 마신 경우
			// max 두번째 매개변수 : n번재 잔을 마시지 못하는 경우
			dp[i] = Math.max(dp[i-1],dp[i]);
		}
		
		return dp[n];
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		long[] arr = new long[n+1];
		for(int i=1;i<=n;i++)
		{
			arr[i] = Long.parseLong(br.readLine());
		}
		long[] dp = new long[n+1];
		System.out.println(solution(dp,arr,n));
	}
}

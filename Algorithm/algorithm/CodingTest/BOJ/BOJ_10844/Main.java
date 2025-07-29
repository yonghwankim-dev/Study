package BOJ.BOJ_10844;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	public static long solution(int n)
	{
		long[][] dp = new long[n+1][10];
		final long DIVISOR = 1000000000;
		for(int j=1;j<=9;j++)
		{
			dp[1][j] = 1;
		}
		
		for(int i=2;i<=n;i++)
		{
			for(int j=0;j<=9;j++)
			{
				if(j==0)
				{
					dp[i][j] = dp[i-1][j+1] % DIVISOR;
				}
				else if(j>=1 && j<=8)
				{
					dp[i][j] = (dp[i-1][j-1] + dp[i-1][j+1]) % DIVISOR;
				}
				else if(j==9)
				{
					dp[i][j] = dp[i-1][j-1] % DIVISOR;
				}
			}
		}
		
		long answer = 0;
		for(int j=0;j<=9;j++)
		{
			answer += dp[n][j];
		}
		
		return answer %DIVISOR;
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		long answer = solution(n);
		System.out.println(answer);
	}
}

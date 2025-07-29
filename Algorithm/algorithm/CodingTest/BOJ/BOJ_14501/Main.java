package BOJ.BOJ_14501;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	private static int[] t;
	private static int[] p;
	private static int[] dp;

	public static int solution(int n)
	{
		int maxMoney=0;
		
		for(int i=1;i<=n;i++)
		{
			// 1) i번째 날에 일을 했을 경우
			if(i+t[i]<=n+1)
			{
				dp[i+t[i]] = Math.max(dp[i+t[i]], dp[i]+p[i]);
				
				// 최대값 갱신
				maxMoney= Math.max(maxMoney, dp[i+t[i]]);
			}
			
			// 2) i번째 날에 일을 하지 않은 경우
			dp[i+1] = Math.max(dp[i+1], dp[i]);
			
			// 최대값 갱신
			maxMoney= Math.max(maxMoney, dp[i+1]);
		}
		return maxMoney;
	}
	
	public static int solution2(int today, int n, int maxMoney)
	{
		if(today>n)
		{
			return maxMoney;
		}
		// 일을 할 수 있는 경우
		if(today+t[today]<=n+1)
		{
			dp[today+t[today]] = Math.max(dp[today+t[today]], dp[today]+p[today]);
			
			//최대값 갱신
			maxMoney = Math.max(maxMoney, dp[today+t[today]]);
		}
		
		// 일을 하지 않는 경우
		dp[today+1] = Math.max(dp[today+1], dp[today]);
		
		// 최대값 갱신
		maxMoney = Math.max(maxMoney, dp[today+1]);
		
		return solution2(today+1, n, maxMoney);	
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());	// 일수
		int nMaxCount = 17;
		dp = new int[nMaxCount];	// n일을 기준으로 받을 수 있는 최고 보수
		t = new int[nMaxCount];	// 상담 일수
		p = new int[nMaxCount];	// 보수 금액
		
		for(int i=1;i<=n;i++)
		{
			String[] str = br.readLine().split(" ");
			t[i] = Integer.parseInt(str[0]);
			p[i] = Integer.parseInt(str[1]);
		}
		System.out.println(solution2(1,n,0));
	}
}

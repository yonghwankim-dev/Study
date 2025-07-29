package CODEJAM.jam2021.qualification_round.p02_moon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution {
	
	public static long solution(int x, int y, String s)
	{
		int n = s.length();
		long[][] dp = new long[2][n];
		
		for(int i=0;i<2;i++)
		{
			for(int j=0;j<n;j++)
			{
				dp[i][j] = Integer.MAX_VALUE;
			}
		}
		
		if(s.charAt(0)=='C' || s.charAt(0)=='?')
		{
			dp[0][0] = 0;
		}
		if(s.charAt(0)=='J' || s.charAt(0)=='?')
		{
			dp[1][0] = 0;
		}
		
		for(int i=1; i<n; i++)
		{
			if(s.charAt(i)=='C' || s.charAt(i)=='?')
			{
				// min(CC, JC+y)
				dp[0][i] = Math.min(dp[0][i-1], dp[1][i-1]+y);
			}
			if(s.charAt(i)=='J' || s.charAt(i)=='?') 
			{
				// min(JJ, CJ+x)
				dp[1][i] = Math.min(dp[1][i-1], dp[0][i-1]+x);
			}
		}
		
		return Math.min(dp[0][n-1], dp[1][n-1]);
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int tc = Integer.parseInt(br.readLine());
		String[] line = null;
		int x, y;
		String s;
		for(int i=0; i<tc; i++)
		{
			line = br.readLine().split(" ");
			x = Integer.parseInt(line[0]);
			y = Integer.parseInt(line[1]);
			s = line[2];

			System.out.printf("Case #%d: %d\n",i+1,solution(x, y, s));
		}
		
	}

}

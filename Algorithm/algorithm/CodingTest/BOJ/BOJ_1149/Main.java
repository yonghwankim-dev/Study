package BOJ.BOJ_1149;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	static int[][] arr;
	static long[][] dp;

	public static long recursion(int i, int color)
	{
		if(i==0)
		{
			return 0;
		}
		
		if(color==0)
		{
			return dp[i][0] = arr[i][0] + Math.min(
					dp[i-1][1]==0 ? recursion(i-1,1) : dp[i-1][1],
					dp[i-1][2]==0 ? recursion(i-1,2) : dp[i-1][2]
					); 
			
		}
		else if(color==1)
		{
			return dp[i][1] = arr[i][1] + Math.min(
										dp[i-1][0]==0 ? recursion(i-1,0) : dp[i-1][0],
										dp[i-1][2]==0 ? recursion(i-1,2) : dp[i-1][2]
										);
		}
		else
		{
			return dp[i][2] = arr[i][2] + Math.min(
										dp[i-1][0]==0 ? recursion(i-1,0) : dp[i-1][0],
										dp[i-1][1]==0 ? recursion(i-1,1) : dp[i-1][1]
										);
		}	
	}
	
	public static long solution(int n)
	{	
		return Math.min(Math.min(recursion(n,0), recursion(n,1)), recursion(n,2));
	}
	
	public static void main(String args[]) throws NumberFormatException, IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		arr = new int[n+1][3];
		dp = new long[n+1][3];
		
		for(int i=1;i<=n;i++)
		{
			int[] rgb = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			arr[i] = rgb;
		}		
		System.out.println(Main.solution(n));
	}
}

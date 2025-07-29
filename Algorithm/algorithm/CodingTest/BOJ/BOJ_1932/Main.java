package BOJ.BOJ_1932;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class Main {	

	static int[][] dp;
	
	public static int solution(int[][] matrix, int row, int col, int n)
	{
		if(row==n)
		{
			return dp[row][col] = matrix[row][col];
		}
		dp[row+1][col] = dp[row+1][col]==0 ? solution(matrix, row+1, col, n) : dp[row+1][col];
		dp[row+1][col+1] = dp[row+1][col+1]==0 ? solution(matrix, row+1, col+1, n) : dp[row+1][col+1];
		dp[row][col] = matrix[row][col] + Math.max(dp[row+1][col], dp[row+1][col+1]);
		return dp[row][col];
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		int[][] matrix = new int[n][n];
		dp = new int[n][n];
		
		for(int i=0;i<n;i++)
		{
			String[] str = br.readLine().split(" ");
			for(int j=0;j<str.length;j++)
			{
				matrix[i][j] = Integer.parseInt(str[j]);
			}
		}
		System.out.println(solution(matrix, 0,0,n-1));
		
	}
}

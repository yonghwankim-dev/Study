package dp.dp06_knapsack;

public class Knapsack {
	public static int knapsack(int n, int W, int[] weight, int[] value)
	{
		int[][] dp = new int[n+1][W+1];
		
		for(int w=0; w<=W; w++)
		{
			dp[0][w] = 0;
		}
		
		for(int i=1; i<=n; i++)
		{
			for(int w=1; w<=W; w++)
			{
				if(weight[i]>w)
				{
					dp[i][w] = dp[i-1][w];
				}
				else
				{
					dp[i][w] = Math.max(dp[i-1][w], value[i] + dp[i-1][w-weight[i]]);
				}
			}
		}
		print(dp);
		
		return dp[n][W];
	}
	
	public static void print(int[][] dp)
	{
		System.out.println("print dp");
		for(int i=0; i<dp.length; i++)
		{
			for(int j=0; j<dp[i].length; j++)
			{
				System.out.print(dp[i][j] + "\t");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args)
	{
		int n = 5;
		int W = 11;
		int[] weight = {0, 1, 2, 5, 6, 7};
		int[] value = {0, 1, 6, 18, 22, 28};
		
		System.out.println(knapsack(n, W, weight, value));
	}
}

package dp.dp03_matrix_path.memoization;

public class Driver {
	static int[][] dp = new int[5][5];
	public static int mat(int[][] m, int i, int j)
	{
		if(dp[i][j]!=0)
		{
			return dp[i][j];
		}
		
		if(i==1 && j==1)
		{
			dp[i][j] = m[i][j]; 
		}
		else if(j==1)
		{
			dp[i][j] = mat(m,i-1,j) + m[i][j];
		}
		else if(i==1)
		{
			dp[i][j] = mat(m,i,j-1) + m[i][j];
		}
		else
		{
			dp[i][j] = Math.min(mat(m,i-1,j), mat(m,i,j-1)) + m[i][j];
		}
		return dp[i][j];
	}
	
	public static void main(String args[])
	{
		int[][] m = {{0,0,0,0,0},
					{0,6,7,12,5},
					{0,5,3,11,18},
					{0,7,17,3,3},
					{0,8,10,14,9}
					};
		System.out.println(mat(m,4,4));
	}
}

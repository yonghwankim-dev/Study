package dp.dp03_matrix_path.dynamic;

public class Driver {
	static int[][] dp = new int[5][5];
	static char[][] path = new char[5][5];
	public static int mat(int[][] m, int n)
	{
		for(int i=1;i<=n;i++)
		{
			for(int j=1;j<=n;j++)
			{
				if(i==1 && j==1)
				{
					dp[i][j] = m[i][j];
					path[i][j] = '-';
				}
				else
				{
					if(dp[i-1][j]<dp[i][j-1])	// À§ÂÊ
					{
						dp[i][j] = m[i][j] + dp[i-1][j];
						path[i][j] = '¡è';
					}
					else	// ¿ÞÂÊ
					{
						dp[i][j] = m[i][j] + dp[i][j-1];
						path[i][j] = '¡ç';
					}					
				}
			}
		}
		return dp[n][n];
	}
	public static void printPath(char[][] path, int i, int j)
	{
		while(path[i][j]!='-')
		{
			System.out.print(i + " " + j+"\n");
			if(path[i][j]=='¡ç')
			{
				j--;
			}
			else
			{
				i--;
			}
		}
		System.out.print(i + " " + j+"\n");
		
	}
	
	public static void printPathRecursive(char[][] path, int i, int j)
	{
		if(path[i][j]=='-')
		{
			System.out.print(i + " " + j + "\n");
		}
		else
		{
			if(path[i][j]=='¡è')
			{
				printPathRecursive(path, i-1, j);
			}
			else if(path[i][j]=='¡ç')
			{
				printPathRecursive(path, i, j-1);
			}
			System.out.print(i + " " + j + "\n");
		}
		
	}
	
	public static void main(String args[])
	{
		int[][] m = {{0,0,0,0,0},
					{0,6,7,12,5},
					{0,5,3,11,18},
					{0,7,17,3,3},
					{0,8,10,14,9}
					};
		int n = 4;
		for(int i=0;i<=n;i++)
		{
			dp[i][0] = Integer.MAX_VALUE;
		}
		for(int j=0;j<=n;j++)
		{
			dp[0][j] = Integer.MAX_VALUE;
		}
		System.out.println(mat(m,4));
		//printPath(path, n, n);
		printPathRecursive(path, n, n);
	}
}

package dp.dp04_martix_mul;

public class Driver {
	public static int[][] m;
	public static int[] p;
	
	public static int martixChain(int n)
	{
		for(int i=1;i<=n;i++)
		{
			m[i][i] = 0;
		}
		
		for(int r=1; r<=n-1; r++)
		{
			for(int i=1; i<=n-r; i++)
			{
				int j = i + r;
				m[i][j] = m[i+1][j] + p[i-1]*p[i]*p[j];
				
				for(int k=i+1; k<=j-1; k++) {
					if(m[i][j] > m[i][k] + m[k+1][j] + p[i-1]*p[k]*p[j])
					{
						m[i][j] = m[i][k] + m[k+1][j] + p[i-1]*p[k]*p[j]; 
					}
				}
			}
		}
		
		return m[1][n];
	}
	public static void main(String[] args) {
		int n = 3;
		m = new int[n+1][n+1];
		// P0 = 10
		// P1 = 100
		// A1 = P0 * P1 = 10 * 100
		p = new int[]{10,100,5,50};
		
		System.out.println(martixChain(n));
	}

}

package dp.dp05_lcs;

/**
 * @title 가장 긴 공통 부분수열을 구하는 클래스
 * - 'bcdb'는 문자열 'abcbdab'의 subsequence이다.
 * - 'bca'는 문자열 'abcbdab'와 'bdcaba'의 common subsequence이다.
 * - Longest Common Subsequence(LCS) : common subsequence들 중 가장 긴것
 * - 'bcba'는 'abcbdab'와 'bdcaba'의 LCS이다.
 */
public class LongestCommonSubsequence {
	String x;
	String y;
	int[][] c;
	String[][] z; 
	/**
	 * 문자열 x, y의 인덱스는 1부터 시작
	 * @param x 문자열 x
	 * @param y 문자열 y
	 */
	public LongestCommonSubsequence(String x, String y) {
		this.x = " "+x;
		this.y = " "+y;
		c = new int[x.length()+1][y.length()+1];
		z = new String[x.length()+1][y.length()+1];
	}
	
	/**
	 * 두 문자열의 LCS(LongestCommonSubsequence)의 길이을 계산합니다.
	 * @param m 문자열 x의 길이
	 * @param n 문자열 y의 길이
	 * @return 두 문자열 x,y의 공통되고 가장 긴 부분수열
	 */
	public int lcsLength(int m, int n)
	{
		// 0열 0으로 초기화
		for(int i=0; i<=m; i++)
		{
			c[i][0] = 0;
		}
		// 0행 0으로 초기화
		for(int j=0; j<=n; j++)
		{
			c[0][j] = 0;
		}
		
		// bottom-up 방식으로 계산값 저장됨
		// c[i][j] 값을 계산하기 위해서는 
		// c[i-1][j-1], c[i-1][j], c[i][j-1]가 계산되어야함
		for(int i=0; i<=m; i++)
		{
			for(int j=0; j<=n; j++)
			{
				// base case
				if(i==0 || j==0)
				{
					continue;
				}
				
				// 두 문자의 일치
				if(x.charAt(i)==y.charAt(j))
				{
					c[i][j] = c[i-1][j-1] + 1;
				}
				// 두 문자의 불일치
				else
				{
					c[i][j] = Math.max(c[i-1][j], c[i][j-1]);
				}
			}
		}
		
		return c[m][n];
	}
	
	/**
	 * 두 문자열의 LCS(LongestCommonSubsequence) 문자열을 계산합니다.
	 * @param m 문자열 x의 길이
	 * @param n 문자열 y의 길이
	 * @return
	 */
	public String lcsString(int m, int n)
	{
		// 0열 0으로 초기화
		for(int i=0; i<=m; i++)
		{
			z[i][0] = "";
		}
		// 0행 0으로 초기화
		for(int j=0; j<=n; j++)
		{
			z[0][j] = "";
		}
		
		// bottom-up 방식으로 계산값 저장됨
		// c[i][j] 값을 계산하기 위해서는 
		// c[i-1][j-1], c[i-1][j], c[i][j-1]가 계산되어야함
		for(int i=0; i<=m; i++)
		{
			for(int j=0; j<=n; j++)
			{
				// base case
				if(i==0 || j==0)
				{
					continue;
				}
				
				// 두 문자의 일치
				if(x.charAt(i)==y.charAt(j))
				{
					z[i][j] = z[i-1][j-1] + x.charAt(i);
				}
				// 두 문자의 불일치
				else
				{
					if(z[i-1][j].length()>z[i][j-1].length())
					{
						z[i][j] = z[i-1][j];
					}
					else
					{
						z[i][j] = z[i][j-1];
					}
				}
			}
		}
		
		return z[m][n];	
	}
}

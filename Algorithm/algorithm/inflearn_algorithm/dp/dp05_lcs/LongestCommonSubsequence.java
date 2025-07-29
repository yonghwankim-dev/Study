package dp.dp05_lcs;

/**
 * @title ���� �� ���� �κм����� ���ϴ� Ŭ����
 * - 'bcdb'�� ���ڿ� 'abcbdab'�� subsequence�̴�.
 * - 'bca'�� ���ڿ� 'abcbdab'�� 'bdcaba'�� common subsequence�̴�.
 * - Longest Common Subsequence(LCS) : common subsequence�� �� ���� ���
 * - 'bcba'�� 'abcbdab'�� 'bdcaba'�� LCS�̴�.
 */
public class LongestCommonSubsequence {
	String x;
	String y;
	int[][] c;
	String[][] z; 
	/**
	 * ���ڿ� x, y�� �ε����� 1���� ����
	 * @param x ���ڿ� x
	 * @param y ���ڿ� y
	 */
	public LongestCommonSubsequence(String x, String y) {
		this.x = " "+x;
		this.y = " "+y;
		c = new int[x.length()+1][y.length()+1];
		z = new String[x.length()+1][y.length()+1];
	}
	
	/**
	 * �� ���ڿ��� LCS(LongestCommonSubsequence)�� ������ ����մϴ�.
	 * @param m ���ڿ� x�� ����
	 * @param n ���ڿ� y�� ����
	 * @return �� ���ڿ� x,y�� ����ǰ� ���� �� �κм���
	 */
	public int lcsLength(int m, int n)
	{
		// 0�� 0���� �ʱ�ȭ
		for(int i=0; i<=m; i++)
		{
			c[i][0] = 0;
		}
		// 0�� 0���� �ʱ�ȭ
		for(int j=0; j<=n; j++)
		{
			c[0][j] = 0;
		}
		
		// bottom-up ������� ��갪 �����
		// c[i][j] ���� ����ϱ� ���ؼ��� 
		// c[i-1][j-1], c[i-1][j], c[i][j-1]�� ���Ǿ����
		for(int i=0; i<=m; i++)
		{
			for(int j=0; j<=n; j++)
			{
				// base case
				if(i==0 || j==0)
				{
					continue;
				}
				
				// �� ������ ��ġ
				if(x.charAt(i)==y.charAt(j))
				{
					c[i][j] = c[i-1][j-1] + 1;
				}
				// �� ������ ����ġ
				else
				{
					c[i][j] = Math.max(c[i-1][j], c[i][j-1]);
				}
			}
		}
		
		return c[m][n];
	}
	
	/**
	 * �� ���ڿ��� LCS(LongestCommonSubsequence) ���ڿ��� ����մϴ�.
	 * @param m ���ڿ� x�� ����
	 * @param n ���ڿ� y�� ����
	 * @return
	 */
	public String lcsString(int m, int n)
	{
		// 0�� 0���� �ʱ�ȭ
		for(int i=0; i<=m; i++)
		{
			z[i][0] = "";
		}
		// 0�� 0���� �ʱ�ȭ
		for(int j=0; j<=n; j++)
		{
			z[0][j] = "";
		}
		
		// bottom-up ������� ��갪 �����
		// c[i][j] ���� ����ϱ� ���ؼ��� 
		// c[i-1][j-1], c[i-1][j], c[i][j-1]�� ���Ǿ����
		for(int i=0; i<=m; i++)
		{
			for(int j=0; j<=n; j++)
			{
				// base case
				if(i==0 || j==0)
				{
					continue;
				}
				
				// �� ������ ��ġ
				if(x.charAt(i)==y.charAt(j))
				{
					z[i][j] = z[i-1][j-1] + x.charAt(i);
				}
				// �� ������ ����ġ
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

package dp.dp02_binomial.binomial03_dynamic;

/**
 * title : bottom-up ������� ���װ�� ����
 * content : �ߺ��� ����� ���ϱ� ���ؼ� (0,0) ~ (n,k)
 * ���� ���������� ����Ͽ� �ߺ��� ����� ���Ѵ�
 * 
 * ���װ�� Ư¡
 * 1. k<=n
 * 
 * memoization vs dynamic programming
 * - ��ȯ���� ���� ����ϴ� ������̴�.
 * - �Ѵ� ������ȹ���� �������� ���⵵ �Ѵ�
 * - memoization�� top-down ����̸�, ������ �ʿ���
 * subproblem���� Ǭ��
 * - ������ȹ���� bottom-up ����̸�, recursion��
 * ���ݵǴ� overhead�� ����.
 * 
 */
public class Driver {
	static int[][] binomial = new int[100][100];
	public static int binomial(int n, int k)
	{
		for(int i=0; i<=n; i++)
		{
			for(int j=0; j<=k && j<=i; j++)
			{
				if(i==j || j==0)
				{
					binomial[i][j] = 1;
				}
				else
				{
					binomial[i][j] = binomial(i-1,j) + binomial(i-1, j-1); 
				}
			}
		}
		return binomial[n][k];
	}
	public static void main(String[] args) {
		System.out.println(binomial(5,2));
	}

}

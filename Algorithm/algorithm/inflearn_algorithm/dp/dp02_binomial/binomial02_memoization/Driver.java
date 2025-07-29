package dp.dp02_binomial.binomial02_memoization;

/**
 * title : memoization ����� �̿��� ���װ�� ����
 * content
 * memoization ����� ����Ͽ� binomial[n][k]����
 * binomial[0][0]���� top-down �������� ����Ͽ� �����մϴ�.
 * 
 */
public class Driver {
	static int[][] binomial = new int[100][100];
	public static int binomial(int n, int k)
	{
		if(n==k || k==0)
		{
			return 1;
		}
		else if(binomial[n][k]!=0)
		{
			return binomial[n][k];
		}
		binomial[n][k] = binomial(n-1,k) + binomial(n-1,k-1);
		return binomial[n][k];
	}
	public static void main(String[] args) {
		System.out.println(binomial(5,2));
	}

}

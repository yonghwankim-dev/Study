package dp.dp01_fibo.fibo03_bottomup;

/**
 * title : bottom-up ������� �ߺ��� ���ϴ� �Ǻ���ġ �� �޼��� ����
 * content : ���� ������ memoization ����� �̿��� ������
 * top-down ����� �����̰� �� ������ bottom-up �������
 * dp �迭�� 0��°���� ä�������� ����Դϴ�.
 *
 */
public class Driver {
	public static int fibo(int n)
	{
		int[] dp = new int[n+1];
		
		dp[0] = 0;
		dp[1] = 1;
		
		for(int i=2;i<=n;i++)
		{
			dp[i] = dp[i-1] + dp[i-2];
		}
		return dp[n];
	}
	public static void main(String[] args) {
		System.out.println(fibo(7));
	}

}

package dp.dp01_fibo.fibo02_memoization;

/**
 * title : memoization ����� �̿��� �Ǻ���ġ �� �޼��� ����
 * content : ���� �Ǻ���ġ �� �޼����� �������� ������� ȣ�⿡��
 * �ߺ��� ����� �ϴ� �������� �ذ��ϱ� ���ؼ� memoization �����
 * ����Ͽ� �ذ��� �� �ֽ��ϴ�.
 * 
 * memoization ����̶�?
 * Ư���� ���� ��ҿ� �ѹ� ���� ����� �����س��� ������� ȣ�⿡��
 * ���� ��Ҹ� Ȯ���� ���� ����� �Ǿ��ִٸ� �̹� ���� �����
 * ����ϴ� ����Դϴ�.
 *
 */
public class Driver {
	static int[] dp = new int[100];
	
	public static int fibo(int n)
	{
		if(n<2)
		{
			return n;
		}
		else if(dp[n]!=0)
		{
			return dp[n];
		}
		else
		{
			dp[n] = fibo(n-1) + fibo(n-2);
			return dp[n];
		}
	}
	public static void main(String[] args) {
		System.out.println(fibo(7));	// 13
	}

}

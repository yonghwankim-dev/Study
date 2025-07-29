package dp.dp01_fibo.fibo03_bottomup;

/**
 * title : bottom-up 방식으로 중복을 피하는 피보나치 수 메서드 구현
 * content : 이전 에제인 memoization 기술을 이용한 구현은
 * top-down 방식의 구현이고 이 에제는 bottom-up 방식으로
 * dp 배열의 0번째부터 채워나가는 방식입니다.
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

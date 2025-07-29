package dp.dp01_fibo.fibo02_memoization;

/**
 * title : memoization 기술을 이용한 피보나치 수 메서드 구현
 * content : 기존 피보나치 수 메서드의 문제점인 재귀적인 호출에서
 * 중복된 계산을 하는 문제점을 해결하기 위해서 memoization 기술을
 * 사용하여 해결할 수 있습니다.
 * 
 * memoization 기술이란?
 * 특정한 저장 장소에 한번 계산된 결과를 저장해놓고 재귀적인 호출에서
 * 저장 장소를 확인한 다음 계산이 되어있다면 이미 계산된 결과를
 * 사용하는 기술입니다.
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

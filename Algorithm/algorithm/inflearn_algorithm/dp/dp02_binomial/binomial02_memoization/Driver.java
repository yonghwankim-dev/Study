package dp.dp02_binomial.binomial02_memoization;

/**
 * title : memoization 기술을 이용한 이항계수 구현
 * content
 * memoization 기술을 사용하여 binomial[n][k]부터
 * binomial[0][0]까지 top-down 방향으로 계산하여 저장합니다.
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

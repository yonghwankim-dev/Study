package dp.dp02_binomial.binomial03_dynamic;

/**
 * title : bottom-up 방식으로 이항계수 구현
 * content : 중복된 계산을 피하기 위해서 (0,0) ~ (n,k)
 * 까지 순차적으로 계산하여 중복된 계산을 안한다
 * 
 * 이항계수 특징
 * 1. k<=n
 * 
 * memoization vs dynamic programming
 * - 순환식의 값을 계산하는 기법들이다.
 * - 둘다 동적계획법의 일종으로 보기도 한다
 * - memoization은 top-down 방식이며, 실제로 필요한
 * subproblem만을 푼다
 * - 동적계획법은 bottom-up 방식이며, recursion에
 * 수반되는 overhead가 없다.
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

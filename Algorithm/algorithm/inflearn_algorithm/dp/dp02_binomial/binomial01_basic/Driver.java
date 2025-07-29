package dp.dp02_binomial.binomial01_basic;

/**
 * title : 일반적인 이항계수(binomial coefficient) 재귀적으로 구현
 * content : 
 * 이항계수란 무엇인가?
 * 이항 계수란 주어진 크기 집합(n)에서 원하는 개수(k)만큼
 * 순서 없이 뽑는 조합의 가짓수를 의미합니다.
 * 예를 들어 n=3, k=2이면
 * (1,2)
 * (1,3)
 * (2,3)
 * 으로 총 3가지를 뽑을 수 있습니다.
 * 
 * (n,k) = 1, if n=k or k=0
 * (n,k) = (n-1,k) + (n-1,k-1), otherwise
 * 
 * 문제점
 * 본 예제는 이항계수를 재귀적으로 계산하는 동안
 * 중복된 계산이 발생합니다.
 */
public class Driver {
	public static int binomial(int n, int k)
	{
		if(n==k || k==0)
		{
			return 1;
		}
		return binomial(n-1,k) + binomial(n-1,k-1);
	}
	public static void main(String[] args) {
		System.out.println(binomial(5,2));
	}
}

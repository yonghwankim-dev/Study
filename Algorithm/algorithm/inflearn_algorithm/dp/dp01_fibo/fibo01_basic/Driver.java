package dp.dp01_fibo.fibo01_basic;

/**
 * title : 피보나치 수
 * content : 정수 n에 대한 피보나치 수를 구하기 위해 fibo 메서드 구현
 * 피보나치 수는 특정 n항에 대해서 n-1항과 n-2항을 합한 값의 수열이 됩니다.
 * 피보나치 수열 : 0 1 1 2 3 5 8 13 ...
 * f(0)=0, f(1)=1, f(n) = f(n-1) + f(n-2)
 * 
 * 문제점
 * fibo 메서드를 재귀적으로 구현하였으나 재귀적인 호출에서 이미 계산된 것을
 * 다시 계산하는 중복 계산이 발생함
 */
public class Driver {
	public static int fibo(int n)
	{
		if(n<2)
		{
			return n;
		}
		return fibo(n-1) + fibo(n-2);
	}
	public static void main(String args[])
	{
		System.out.println(fibo(7));	// 13
	}
}

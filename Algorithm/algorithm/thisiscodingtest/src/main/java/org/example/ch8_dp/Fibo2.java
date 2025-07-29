package org.example.ch8_dp;

public class Fibo2 {
	public long fibo(int x, long[] dp){
		if (x == 1 || x == 2){
			return 1;
		}
		if (dp[x] != 0){
			return dp[x];
		}
		dp[x] = fibo(x - 1, dp) + fibo(x - 2, dp);
		return dp[x];
	}

	public static void main(String[] args) {
		Fibo2 fibo = new Fibo2();
		long[] dp = new long[100];
		System.out.println(fibo.fibo(99, dp));
	}
}

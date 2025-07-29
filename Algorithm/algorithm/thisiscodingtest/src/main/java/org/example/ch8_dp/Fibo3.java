package org.example.ch8_dp;

public class Fibo3 {
	public long fibo(int x, long[] dp){
		dp[1] = 1;
		dp[2] = 1;
		int n = x;
		for (int i = 3; i < n + 1; i++){
			dp[i] = dp[i - 1] + dp[i - 2];
		}
		return dp[x];
	}

	public static void main(String[] args) {
		Fibo3 fibo = new Fibo3();
		long[] dp = new long[100];
		System.out.println(fibo.fibo(5, dp));
	}
}

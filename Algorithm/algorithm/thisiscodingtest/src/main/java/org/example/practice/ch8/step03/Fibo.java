package org.example.practice.ch8.step03;

public class Fibo {
	public static void main(String[] args) {
		int n = 10;
		int answer = solution(n);
		int expected = 55;
		System.out.println(answer);
		assert answer == expected;
	}

	private static int solution(int n){
		int[] dp = new int[n + 1];
		dp[0] = 0;
		dp[1] = 1;
		for (int i = 2; i <= n; i++){
			dp[i] = dp[i - 1] + dp[i - 2];
		}
		return dp[n];
	}
}

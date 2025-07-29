package org.example.ch8_dp;

public class Square {
	private int solution(int n) {
		int[] dp = new int[n + 1];
		dp[1] = 1;
		dp[2] = 3;
		for (int i = 3; i <= n; i++) {
			dp[i] = (dp[i - 1] + dp[i - 2] * 2) % 796796;
		}
		return dp[n] ;
	}

	public static void main(String[] args) {
		Square square = new Square();
		int answer = square.solution(3);
		System.out.println(answer);
	}
}


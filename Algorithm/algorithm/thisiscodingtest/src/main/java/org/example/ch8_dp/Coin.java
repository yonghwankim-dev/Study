package org.example.ch8_dp;

import java.util.Arrays;

public class Coin {
	private int solution(int[] coins, int money){
		// a_i = min(a_i, a_i-k + 1) a_i를 만들 수 있는 경우
		// a_i = 10001, a_i를 만들 수 없는 경우
		int n = money;
		int[] dp = new int[n + 1];
		Arrays.fill(dp, 10001);
		dp[0] = 0;

		for(int coin : coins){
			for(int i = 1; i <= n; i++){
				if (i < coin){
					continue;
				}
				dp[i] = Math.min(dp[i], dp[i - coin] + 1);
			}
		}
		return dp[n] == 10001 ? -1 : dp[n];
	}

	public static void main(String[] args) {
		Coin coin = new Coin();
		int answer = coin.solution(new int[] {2, 3}, 15);
		System.out.println(answer);
	}
}

package org.example.practice.ch8.step04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Coin {
	public static void main(String[] args) {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
			int[] inputs = Arrays.stream(br.readLine().split(" "))
				.mapToInt(Integer::parseInt)
				.toArray();
			int coinCount = inputs[0];
			int n = inputs[1];
			int[] coins = new int[coinCount];
			for (int i = 0; i < coinCount; i++) {
				coins[i] = Integer.parseInt(br.readLine());
			}
			int answer = solution(coins, n);
			int expected = Integer.parseInt(br.readLine());
			System.out.println(answer);
			assert answer == expected;
		}catch (IOException e){
			throw new IllegalArgumentException(e);
		}
	}

	private static int solution(int[] coins, int n){
		int[] dp = new int[n + 1];
		Arrays.fill(dp, 10_001);
		dp[0] = 0;
		for (int coin : coins){
			for (int i = 1; i <= n; i++){
				if (i < coin){
					continue;
				}
				dp[i] = Math.min(dp[i], dp[i - coin] + 1);
			}
		}
		return dp[n];
	}
}

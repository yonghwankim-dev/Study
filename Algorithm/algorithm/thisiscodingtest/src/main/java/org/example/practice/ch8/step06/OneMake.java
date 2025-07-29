package org.example.practice.ch8.step06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class OneMake {
	public static void main(String[] args) {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
			int x = Integer.parseInt(br.readLine());
			int answer = solution(x);
			int expected = Integer.parseInt(br.readLine());
			System.out.println(answer);
			assert answer == expected;
		}catch (IOException e){
			throw new IllegalArgumentException(e);
		}
	}

	private static int solution(int x){
		// 5, 3, 2, -1
		int[] dp = new int[x + 1];
		Arrays.fill(dp, Integer.MAX_VALUE);
		dp[0] = dp[1] = 0;
		for (int i = 2; i <= x; i++){
			dp[i] = dp[i-1] + 1;
			if (i % 5 == 0){
				dp[i] = Math.min(dp[i], dp[i / 5] + 1);
			}
			if (i % 3 == 0){
				dp[i] = Math.min(dp[i], dp[i / 3] + 1);
			}
			if (i % 2 == 0) {
				dp[i] = Math.min(dp[i], dp[i / 2] + 1);
			}
		}
		return dp[x];
	}
}

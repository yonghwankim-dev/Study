package org.example.practice.ch8.step07;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Sqaure {
	public static void main(String[] args) {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
			int n = Integer.parseInt(br.readLine());
			int answer = solution(n);
			int expected = Integer.parseInt(br.readLine());
			assert answer == expected;
		}catch (IOException e){
			throw new IllegalArgumentException(e);
		}
	}

	private static int solution(int n){
		int[] dp = new int[n + 1];
		dp[0] = 0;
		dp[1] = 1;
		dp[2] = 3;
		for (int i = 3; i <= n; i++){
			dp[i] = dp[i-1] + (dp[i-2] * 2) % 796_796;
		}
		return dp[n];
	}
}

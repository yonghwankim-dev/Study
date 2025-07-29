package org.example.practice.ch8.step02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Fibo {
	public static void main(String[] args) {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
			int n = Integer.parseInt(br.readLine());
			int[] dp = new int[n + 1];
			int[] answer = new int[n + 1];
			for (int i = 0; i <= n; i++){
				answer[i] = solution(i, dp);
			}
			int[] expected = Arrays.stream(br.readLine().split(" "))
				.mapToInt(Integer::parseInt)
				.toArray();
			System.out.println(Arrays.toString(answer));
			assert Arrays.equals(answer, expected);
		}catch (IOException e){
			throw new IllegalArgumentException(e);
		}
	}

	private static int solution(int x, int[] dp){
		if(x <= 1){
			return x;
		}
		if (dp[x] != 0){
			return dp[x];
		}
		dp[x] = solution(x - 1, dp) + solution(x - 2, dp);
		return dp[x];
	}
}

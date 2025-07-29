package org.example.practice.ch8.step05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Ant {
	public static void main(String[] args) {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
			int n = Integer.parseInt(br.readLine());
			int[] stores = Arrays.stream(br.readLine().split(" "))
				.mapToInt(Integer::parseInt)
				.toArray();
			int answer = solution(n, stores);
			int expected = Integer.parseInt(br.readLine());
			assert answer == expected;
		}catch (IOException e){
			throw new IllegalArgumentException(e);
		}
	}

	public static int solution(int n, int[] stores){
		int[] dp = new int[n + 1];
		for (int i = 0; i < n; i++){
			if (i == 0){
				dp[i] = stores[i];
			}else if (i == 1){
				dp[i] = Math.max(dp[i-1], stores[i]);
			}else{
				dp[i] = Math.max(dp[i - 1], dp[i-2] + stores[i]);
			}
		}
		return dp[n - 1];
	}
}

package org.example.ch8_dp;

import org.example.ch4_imple.Solution2;

public class OneMake {
	public int solution(int n){
		int[] dp = new int[30001];

		for(int i = 2; i < n + 1; i++){
			// 현재의 수에서 1을 빼는 경우
			dp[i] = dp[i - 1] + 1;
			// 현재의 수가 2로 나누어 떨어지는 경우
			if(i % 2 == 0){
				dp[i] = Math.min(dp[i], dp[i / 2] + 1);
			}
			// 현재의 수가 3로 나누어 떨어지는 경우
			if (i % 3 == 0){
				dp[i] = Math.min(dp[i], dp[i / 3] + 1);
			}
			// 현재의 수가 5로 나누어 떨어지는 경우
			if (i % 5 == 0){
				dp[i] = Math.min(dp[i], dp[i / 5] + 1);
			}
		}
		return dp[n];
	}

	public static void main(String[] args) {
		OneMake oneMake = new OneMake();
		System.out.println(oneMake.solution(26));
	}
}

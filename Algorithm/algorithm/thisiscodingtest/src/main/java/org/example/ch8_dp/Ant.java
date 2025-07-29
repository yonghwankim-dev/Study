package org.example.ch8_dp;

public class Ant {
	public int solution(int[] stores){
		int n = stores.length;
		int[] dp = new int[n];
		for(int i = 0; i < n; i++){
			if (i == 0){
				dp[0] = stores[i];
			}else if (i == 1){
				dp[1] = Math.max(dp[0], stores[1]);
			}else{
				dp[i] = Math.max(dp[i-1], dp[i-2] + stores[i]);
			}
		}
		return dp[n - 1];
	}

	public static void main(String[] args) {
		Ant ant = new Ant();
		int answer = ant.solution(new int[] {1, 3, 1, 5});
		System.out.println(answer);
	}
}

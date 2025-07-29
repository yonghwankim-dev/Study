package PROM.PROM_12914;

class Solution {
	public long solution(int n) {
		long answer = 0;
		long[] dp = new long[n+1];

		for(int i = 0; i <= n; i++){
			if(i <= 2){
				dp[i] = i;
			}else{
				dp[i] = (long)((dp[i-1] + dp[i-2]) % 1234567);
			}
		}

		return dp[n];
	}
}

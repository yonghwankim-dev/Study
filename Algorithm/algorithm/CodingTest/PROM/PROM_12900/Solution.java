package PROM.PROM_12900;


class Solution {
	public int solution(int n) {
		int[] dp = new int[n+1];

		for(int i = 0; i <= n; i++){
			if(i <= 2){
				dp[i] = i;
			}else{
				dp[i] = (int) ((dp[i-1] + dp[i-2]) % 1000000007L);
			}
		}
		return dp[n];
	}

	public static void main(String[] args){
		int answer = new Solution().solution(7);
		System.out.println(answer);
	}
}

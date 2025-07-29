package BOJ.BOJ_2294;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


// dp[j] = min(dp[j], dp[j - coin[i]] + dp[coin[i]]);
// 만약 코인을 사용할 수 없는 경우는 최대 10,001 입니다.
// 따라서 dp[k] 가 10,001 인 경우는 코인을 사용할 수 없는 경우입니다.
// 따라서 코인을 사용할 수 없는 경우는 -1 을 출력합니다.
// 코인을 사용할 수 있는 경우는 dp[k]
public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());

		int[] coin = new int[n];
		for (int i = 0; i < n; i++){
			coin[i] = Integer.parseInt(br.readLine());
		}
		int answer = solution(n, k, coin);
		System.out.println(answer);
	}

	private static int solution(int n, int k, int[] coin) {
		int[] dp = new int[k + 1];
		Arrays.fill(dp, 10_001);
		dp[0] = 0;

		for(int i = 0; i < n; i++){
			for (int j = coin[i]; j <= k; j++){
				if (j - coin[i] == 0){
					dp[j] = 1;
				}
				else{
					dp[j] = Math.min(dp[j], dp[j - coin[i]] + dp[coin[i]]);
				}
			}
		}
		if (dp[k] == 10_001){
			return -1;
		}
		return dp[k];
	}
}

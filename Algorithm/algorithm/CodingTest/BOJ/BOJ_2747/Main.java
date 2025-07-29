package BOJ.BOJ_2747;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int answer = Main.solution(n);
        System.out.println(answer);
    }
    public static int solution(int n){
        int[] dp = new int[n+1];

        for(int i = 0; i <= n; i++){
            if(i <= 1){
                dp[i] = i;
                continue;
            }
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n];
    }
}

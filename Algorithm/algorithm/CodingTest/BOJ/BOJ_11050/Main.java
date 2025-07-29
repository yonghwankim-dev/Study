package BOJ.BOJ_11050;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().split(" ");
        int n = Integer.parseInt(line[0]);
        int k = Integer.parseInt(line[1]);
        long answer = Main.solution(n, k);
        System.out.println(answer);
    }

    private static long solution(int n, int k) {
        long[] dp = new long[n+1];

        for(int i = 0; i <= n; i++){
            if(i <= 1){
                dp[i] = 1;
                continue;
            }
            dp[i] = i * dp[i-1];
        }

        long result = dp[n] / (dp[k] * dp[n-k]);
        return result;
    }

}

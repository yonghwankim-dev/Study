package kr.yh.chap06_dp.coin.coin_elements;

public class Coin {
    private int[] coins;

    public Coin(int[] coins) {
        this.coins = coins;
    }

    // 사용된 동전들의 배열을 반환
    public int[] solve(int n){
        double[] dp = new double[n + 1];
        int[] first = new int[n + 1];
        for(int x = 1; x <= n; x++){
            dp[x] = Double.POSITIVE_INFINITY;
            for(int coin : coins){
                if(x - coin >= 0 && dp[x - coin] + 1 < dp[x]){
                    dp[x] = dp[x - coin] + 1;
                    first[x] = coin;
                }
            }
        }
        return first;
    }
}

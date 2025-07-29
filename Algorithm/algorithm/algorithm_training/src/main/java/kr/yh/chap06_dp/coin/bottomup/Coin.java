package kr.yh.chap06_dp.coin.bottomup;

public class Coin {
    private int[] coins;

    public Coin(int[] coins) {
        this.coins = coins;
    }

    public double solve(int n){
        double[] dp = new double[n + 1];

        for(int x = 1; x <= n; x++){
            dp[x] = Double.POSITIVE_INFINITY;
            for(int coin : coins){
                if(x - coin >= 0){
                    dp[x] = Math.min(dp[x], dp[x - coin]+1);
                }
            }
        }
        return dp[n];
    }
}

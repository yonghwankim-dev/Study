package kr.yh.chap06_dp.coin.memoization;

public class Coin {
    private int[] coins;
    private boolean ready[];
    private int[] dp;

    public Coin(int[] coins) {
        this.coins = coins;
        ready = new boolean[100];
        dp = new int[100];
    }

    public double solve(int x){
        if(x < 0){
            return Double.POSITIVE_INFINITY;
        }else if(x == 0){
            return 0;
        }else if(ready[x]){
            return dp[x];
        }
        double best = Double.POSITIVE_INFINITY;
        for(int coin : coins){
            best = Math.min(best, solve(x - coin) + 1);
        }
        ready[x] = true;
        dp[x] = (int) best;
        return best;
    }
}

package kr.yh.chap06_dp.coin.basic;

import java.util.List;

public class Coin {
    private int[] coins;

    public Coin(int[] coins) {
        this.coins = coins;
    }

    public double solve(int x){
        if(x < 0){
            return Double.POSITIVE_INFINITY;
        }else if(x == 0){
            return 0;
        }
        double best = Double.POSITIVE_INFINITY;
        for(int coin : coins){
            best = Math.min(best, solve(x - coin) + 1);
        }
        return best;
    }
}

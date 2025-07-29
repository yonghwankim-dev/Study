package kr.yh.chap06_dp.coin.numberOfCases;

public class Coin {
    private int[] coins;

    public Coin(int[] coins) {
        this.coins = coins;
    }

    // 합 n을 만드는 경우의 수를 반환
    public int solve(int n){
        int m = (int) (Math.pow(10, 9) + 7);
        int[] count = new int[n + 1];
        count[0] = 1;

        for(int x = 1; x <= n; x++){
            for(int coin : coins){
                if(x - coin >= 0){
                    count[x] += count[x - coin];
                    count[x] %= m;
                }
            }
        }
        return count[n];
    }
}

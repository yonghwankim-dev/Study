package kr.yh.chap06_dp.coin.counting_solution;

// 해의 개수 세기
public class Coin {
    // count[x] : 합이 x가 되게 만들 수 있는 경우의 수
    public int solution(int n){
        int[] count = new int[n + 1];
        int[] coins = {1, 3, 4};
        count[0] = 1;
        for(int x = 1; x <= n; x++){
            for(int coin : coins){
                if(x - coin >= 0){
                    count[x] += count[x - coin];
                }
            }
        }
        return count[n];
    }
}

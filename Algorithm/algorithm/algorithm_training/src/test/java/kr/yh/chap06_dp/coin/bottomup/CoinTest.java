package kr.yh.chap06_dp.coin.bottomup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class CoinTest {

    private Coin coin;

    @BeforeEach
    public void setup(){
        coin = new Coin(new int[]{1,3,4});
    }

    @Test
    public void testcase1(){
        int[] actual = new int[11];
        int i = 0;
        for(int x = 0; x <= 10; x++){
            actual[i++] = (int) coin.solve(x);
        }

        assertThat(actual).isEqualTo(new int[]{0, 1, 2, 1, 1, 2, 2, 2, 2, 3, 3});
    }

}
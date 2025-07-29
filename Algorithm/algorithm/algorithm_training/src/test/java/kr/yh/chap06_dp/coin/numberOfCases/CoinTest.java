package kr.yh.chap06_dp.coin.numberOfCases;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class CoinTest {
    @Test
    public void testcase1(){
        Coin coin = new Coin(new int[]{1,3,4});
        int actual = coin.solve(5);
        assertThat(actual).isEqualTo(6);
    }

}
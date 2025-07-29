package kr.yh.chap06_dp.coin.coin_elements;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
        int n = 10;
        List<Integer> list = new ArrayList<>();
        int[] actual = coin.solve(n);
        while(n > 0){
            list.add(actual[n]);
            n -= actual[n];
        }
        assertThat(list).isEqualTo(List.of(3,3,4));
    }

}
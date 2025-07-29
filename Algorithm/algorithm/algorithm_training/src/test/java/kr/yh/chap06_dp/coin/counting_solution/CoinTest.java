package kr.yh.chap06_dp.coin.counting_solution;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class CoinTest {
    @Test
    public void testcase1(){
        //given
        int n = 5;
        //when
        int solution = new Coin().solution(n);
        //then
        assertThat(solution).isEqualTo(6);
    }

}
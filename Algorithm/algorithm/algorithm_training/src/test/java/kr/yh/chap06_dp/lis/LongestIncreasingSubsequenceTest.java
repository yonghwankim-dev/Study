package kr.yh.chap06_dp.lis;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LongestIncreasingSubsequenceTest {
    @Test
    public void testSolution(){
        //given
        int[] array = {6, 2, 5, 1, 7, 4, 8, 3};
        //when
        int actual = new LongestIncreasingSubsequence().solution(array);
        //then
        Assertions.assertThat(actual).isEqualTo(6);
    }

}
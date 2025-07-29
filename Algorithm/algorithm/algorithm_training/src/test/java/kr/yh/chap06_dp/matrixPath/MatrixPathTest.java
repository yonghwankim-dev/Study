package kr.yh.chap06_dp.matrixPath;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MatrixPathTest {
    @Test
    public void testSolution(){
        //given
        int[][] value = {{3, 7, 9, 2, 7},
                        {9, 8, 3, 5, 5},
                        {1, 7, 9, 8, 5},
                        {3, 8, 6, 4, 10},
                        {6, 3, 9, 7, 8}};
        //when
        int actual = new MatrixPath().solution(value);
        //then
        Assertions.assertThat(actual).isEqualTo(67);
    }
}
package PROM.PROM_12905;

import org.junit.Assert;
import org.junit.Test;


class SolutionTests {

    @Test
    void test1(){
        int[][] board = {{0,1,1,1},{1,1,1,1},{1,1,1,1},{0,0,1,0}};
        int answer = new Solution().solution(board);

        Assert.assertEquals(9, answer);
    }

    @Test
    void test2(){
        int[][] board = {{0,0,1,1},{1,1,1,1}};
        int answer = new Solution().solution(board);

        Assert.assertEquals(4, answer);
    }
}

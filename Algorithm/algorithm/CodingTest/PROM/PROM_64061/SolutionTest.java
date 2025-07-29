package PROM.PROM_64061;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {

    @Test
    public void test() throws Exception{
        //given
        int[][] board = {{0,0,0,0,0},{0,0,1,0,3},{0,2,5,0,1},{4,2,4,4,2},{3,5,1,3,1}};
        int[] moves = {1,5,3,5,1,2,1,4};
        int expected = 4;

        //when
        int actual = Solution.solution(board, moves);

        //then
        assertThat(actual).isEqualTo(expected);
    }
}

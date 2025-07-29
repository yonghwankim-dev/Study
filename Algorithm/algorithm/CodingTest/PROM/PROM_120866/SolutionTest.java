package PROM.PROM_120866;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class SolutionTest {

    @Test
    public void test(){
        //given
        int[][] board = {{0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 1, 0, 0}, {0, 0, 0, 1, 0}, {0, 0, 0, 0, 0}};
        //when
        int actual = new Solution().solution(board);
        //then
        Assertions.assertThat(actual).isEqualTo(11);
    }
}

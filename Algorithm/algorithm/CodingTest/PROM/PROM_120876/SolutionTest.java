package PROM.PROM_120876;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class SolutionTest {
    @Test
    public void test(){
        //given
        int[][] lines = {{0, 1}, {2, 5}, {3, 9}};
        //when
        int actual = new Solution().solution(lines);
        //then
        Assertions.assertThat(actual).isEqualTo(2);
    }

    @Test
    public void test2(){
        //given
        int[][] lines = {{-1, 1}, {1, 3}, {3, 9}};
        //when
        int actual = new Solution().solution(lines);
        //then
        Assertions.assertThat(actual).isEqualTo(0);
    }

    @Test
    public void test3(){
        //given
        int[][] lines = {{0, 5}, {3, 9}, {1, 10}};
        //when
        int actual = new Solution().solution(lines);
        //then
        Assertions.assertThat(actual).isEqualTo(8);
    }

}

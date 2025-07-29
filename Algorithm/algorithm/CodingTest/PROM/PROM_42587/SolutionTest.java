package PROM.PROM_42587;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    @Test
    public void test() throws Exception{
        //given
        int[] priorities = {2, 1, 3, 2};
        int location = 2;
        int expected = 1;

        //when
        int actual = Solution.solution(priorities, location);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void test2() throws Exception{
        //given
        int[] priorities = {1, 1, 9, 1, 1, 1};
        int location = 0;
        int expected = 5;

        //when
        int actual = Solution.solution(priorities, location);

        //then
        assertThat(actual).isEqualTo(expected);
    }
}

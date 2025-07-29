package PROM.PROM_77484;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class SolutionTest {
    @Test
    public void test1() throws Exception{
        //given
        int[] lottos = {44, 1, 0, 0, 31, 25};
        int[] win_nums = {31, 10, 45, 1, 6, 19};
        int[] expected = {3,5};

        //when
        int[] answer = Solution.solution(lottos, win_nums);

        //then
        assertThat(answer).isEqualTo(expected);
    }
}

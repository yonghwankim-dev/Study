package PROM.PROM_118667;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    @Test
    public void test() throws Exception{
        //given
        int[] queue1 = {3,2,7,2};
        int[] queue2 = {4,6,5,1};
        //when
        int actual = Solution.solution(queue1, queue2);

        //then
        assertThat(actual).isEqualTo(2);
    }

    @Test
    public void test2() throws Exception{
        //given
        int[] queue1 = {1,1};
        int[] queue2 = {1,5};
        //when
        int actual = Solution.solution(queue1, queue2);

        //then
        assertThat(actual).isEqualTo(-1);
    }
}

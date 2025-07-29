package PROM.PROM_42840;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class SolutionTest {
    @Test
    public void test() throws Exception{
        //given
        int[] answers = {1, 2, 3, 4, 5};

        //when
        int[] actual = Solution.solution(answers);

        //then
        Assertions.assertThat(actual).isEqualTo(new int[]{1});
    }

    @Test
    public void test2() throws Exception{
        //given
        int[] answers = {1,3,2,4,2};

        //when
        int[] actual = Solution.solution(answers);

        //then
        Assertions.assertThat(actual).isEqualTo(new int[]{1,2,3});
    }
}

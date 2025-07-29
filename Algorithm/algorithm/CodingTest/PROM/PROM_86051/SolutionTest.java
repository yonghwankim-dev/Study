package PROM.PROM_86051;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {

    @Test
    public void test1() throws Exception{
        //given
        int[] numbers = {1,2,3,4,6,7,8,0};

        //when
        int actual = Solution.solution(numbers);

        //then
        assertThat(actual).isEqualTo(14);
    }

    @Test
    public void test2() throws Exception{
        //given
        int[] numbers = {5,8,4,0,6,7,9};

        //when
        int actual = Solution.solution(numbers);

        //then
        assertThat(actual).isEqualTo(6);
    }
}

package PROM.PROM_76501;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    
    @Test
    public void test() throws Exception{
        //given
        int[] absolutes = {4, 7, 12};
        boolean[] signs = {true, false, true};

        //when
        int actual = Solution.solution(absolutes, signs);

        //then
        assertThat(actual).isEqualTo(9);
    }

    @Test
    public void test2() throws Exception{
        //given
        int[] absolutes = {1, 2, 3};
        boolean[] signs = {false, false, true};

        //when
        int actual = Solution.solution(absolutes, signs);

        //then
        assertThat(actual).isEqualTo(0);
    }
}

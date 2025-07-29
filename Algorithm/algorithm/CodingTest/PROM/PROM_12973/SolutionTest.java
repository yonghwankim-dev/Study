package PROM.PROM_12973;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    @Test
    public void test() throws Exception{
        //given
        String s = "baabaa";
        //when
        int actual = Solution.solution(s);
        //then
        assertThat(actual).isEqualTo(1);
    }
}

package PROM.PROM_132267;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    @Test
    public void test(){
        //given
        int a = 2, b = 1, n = 20, expected = 19;
        //when
        int actual = Solution.solution(a, b, n);
        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void test2(){
        //given
        int a = 3, b = 1, n = 20, expected = 9;
        //when
        int actual = Solution.solution(a, b, n);
        //then
        assertThat(actual).isEqualTo(expected);
    }

}

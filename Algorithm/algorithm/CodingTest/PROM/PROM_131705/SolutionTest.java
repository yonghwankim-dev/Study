package PROM.PROM_131705;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    @Test
    public void test(){
        //given
        int[] number = {-2, 3, 0, 2, -5};
        //when
        int answer = Solution.solution(number);
        //then
        assertThat(answer).isEqualTo(2);
    }

    @Test
    public void test2(){
        //given
        int[] number = {-3, -2, -1, 0, 1, 2, 3};
        //when
        int answer = Solution.solution(number);
        //then
        assertThat(answer).isEqualTo(5);
    }

    @Test
    public void test3(){
        //given
        int[] number = {-1, 1, -1, 1};
        //when
        int answer = Solution.solution(number);
        //then
        assertThat(answer).isEqualTo(0);
    }

}

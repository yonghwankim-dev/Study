package PROM.PROM_155652;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class SolutionTest {
    @Test
    public void testcase1(){
        //given

        //when
        String solution = new Solution().solution("aukks", "wbqd", 5);
        //then
        Assertions.assertThat(solution).isEqualTo("happy");
    }

    @Test
    public void testcase2(){
        //given

        //when
        String solution = new Solution().solution("ybcde", "az", 1);
        //then
        Assertions.assertThat(solution).isEqualTo("bcdef");
    }

    @Test
    public void testcase3(){
        //given

        //when
        String solution = new Solution().solution("z", "abcdefghij", 20);
        //then
        Assertions.assertThat(solution).isEqualTo("n");
    }
}

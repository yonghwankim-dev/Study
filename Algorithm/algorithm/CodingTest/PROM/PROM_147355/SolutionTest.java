package PROM.PROM_147355;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class SolutionTest {
    @Test
    public void test1(){
        //given
        String t = "3141592";
        String p = "271";
        //when
        int solution = new Solution().solution(t, p);
        //then
        Assertions.assertThat(solution).isEqualTo(2);
    }

    @Test
    public void test2(){
        //given
        String t = "10203";
        String p = "10203";
        //when
        int solution = new Solution().solution(t, p);
        //then
        Assertions.assertThat(solution).isEqualTo(1);
    }

}

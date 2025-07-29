package PROM.PROM_120884;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class SolutionTest {
    @Test
    public void test(){
        //given

        //when
        int actual = new Solution().solution(0);
        //then
        Assertions.assertThat(actual).isEqualTo(0);
    }

    @Test
    public void test2(){
        //given

        //when
        int actual = new Solution().solution(1000000);
        //then
        Assertions.assertThat(actual).isEqualTo(111111);
    }

    @Test
    public void test3(){
        //given

        //when
        int actual = new Solution().solution(1999);
        //then
        Assertions.assertThat(actual).isEqualTo(222);
    }

}

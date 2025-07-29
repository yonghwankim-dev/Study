package PROM.PROM_131128;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {

    @Test
    public void test1(){
        //given
        String X = "100", Y = "2345", expected = "-1";
        //when
        String actual = Solution.solution(X, Y);
        //then
        assertThat(actual).isEqualTo("-1");
    }

    @Test
    public void test2(){
        //given
        String x = "100";
        String y = "";
        for(int i = 1; i <= 3000000; i++){
            y += (i % 9);
        }
        //when
        String actual = Solution.solution(x, y);
        //then
        assertThat(actual).isEqualTo("100");
    }
}

package PROM.PROM_DEV.problem1;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    @Test
    public void test() throws Exception{
        //given
        String src = "111100100011";
        //when
        String answer = Solution.solution(src);
        //then
        assertThat("1DBACB");
    }

    @Test
    public void test2() throws Exception{
        //given
        String src = "00011011100000";
        //when
        String answer = Solution.solution(src);
        //then
        assertThat("0CBACE");
    }

    @Test
    public void test3() throws Exception{
        //given
        String src = "11111";
        //when
        String answer = Solution.solution(src);
        //then
        assertThat("1E");
    }

}

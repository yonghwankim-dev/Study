package PROM.PROM_17687;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    @Test
    public void test() throws Exception{
        //given
        int n = 2;
        int t = 4;
        int m = 2;
        int p = 1;

        //when
        String answer = Solution.solution(n, t, m, p);
        //then
        assertThat(answer).isEqualTo("0111");
    }
    
    @Test
    public void test2() throws Exception{
        //given
        int n = 16;
        int t = 16;
        int m = 2;
        int p = 1;

        //when
        String answer = Solution.solution(n, t, m, p);
        //then
        assertThat(answer).isEqualTo("02468ACE11111111");
    }
    
    @Test
    public void test3() throws Exception{
        //given
        int n = 16;
        int t = 16;
        int m = 2;
        int p = 2;

        //when
        String answer = Solution.solution(n, t, m, p);
        //then
        assertThat(answer).isEqualTo("13579BDF01234567");
    }
}

package PROM.PROM_12952;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    @Test
    public void test1() throws Exception{
        //given
        int n = 8;
        //when
        int actual = Solution.solution(n);
        //then
        assertThat(actual).isEqualTo(92);
    }

    @Test
    public void test2() throws Exception{
        //given
        int limit = 12;
        int[] expected = {0, 1, 0, 0, 2, 10, 4, 40, 92, 352, 724, 2680, 14200};
        //when
        for(int n = 1; n <= limit; n++){
            long start = System.currentTimeMillis();
            int actual = Solution.solution(n);
            assertThat(actual).isEqualTo(expected[n]);
            long end = System.currentTimeMillis();
            System.out.printf("n = %d : %d\n", n, end-start);
        }
    }
}

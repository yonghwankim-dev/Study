package PROM.PROM_1829;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    @Test
    public void test() throws Exception{
        //given
        int m = 6;
        int n = 4;
        int[][] picture = {
                {1,1,1,0},
                {1,2,2,0},
                {1,0,0,1},
                {0,0,0,1},
                {0,0,0,3},
                {0,0,0,3}
        };
        //when
        int[] result = Solution.solution(m, n, picture);
        //then
        assertThat(result).isEqualTo(new int[]{4,5});

    }
}

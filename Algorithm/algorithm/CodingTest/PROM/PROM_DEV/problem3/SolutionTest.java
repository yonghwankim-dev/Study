package PROM.PROM_DEV.problem3;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {

    @Test
    public void test() throws Exception{
        //given
        int N = 9;
        int[][] mine = {{1,2}, {2,6}, {3,4}, {3,8}, {4,9}, {5,4}, {5,8}, {6,7}, {7,2}, {9,1}};
        int[] P = {8,5};
        //when
        int answer = Solution.solution(N, mine, P);
        //then
        assertThat(answer).isEqualTo(29);
    }

}

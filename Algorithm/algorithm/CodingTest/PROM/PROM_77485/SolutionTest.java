package PROM.PROM_77485;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    @Test
    public void test() throws Exception{
        //given
        int row = 3;
        int col = 3;
        int[][] queries = {{1,1,2,2},{1,2,2,3},{2,1,3,2},{2,2,3,3}};
        //when
        int[] answer = Solution.solution(3,3, queries);
        //then
        assertThat(answer).isEqualTo(new int[]{1,1,5,3});
    }

}

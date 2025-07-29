package PROM.PROM_68936;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class SolutionTest {

    @Test
    public void test() throws Exception{
        //given
        int n = 4;
        int[][] arr = new int[][]{{1,1,0,0},{1,0,0,0},{1,0,0,1},{1,1,1,1}};
        //when
        int[] answer = Solution.solution(arr);
        //then
        assertThat(answer).isEqualTo(new int[]{4,9});
    }

}

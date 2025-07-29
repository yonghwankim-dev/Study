package PROM.PROM_12913;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    @Test
    public void test() throws Exception{
        //given
        int[][] land = {{1,2,3,5},{5,6,7,8},{4,3,2,1}};
        //when
        int answer = Solution.solution(land);
        //then
        assertThat(answer).isEqualTo(16);
    }

}

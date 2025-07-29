package PROM.PROM_12978;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    
    @Test
    public void test() throws Exception{
        //given
        int n = 5;
        int[][] road = {{1,2,1},{2,3,3},{5,2,2},{1,4,2},{5,3,1},{5,4,2}};
        int k = 3;
        //when
        int answer = Solution.solution(n, road, k);
        //then
        assertThat(answer).isEqualTo(4);
    }

    @Test
    public void test2() throws Exception{
        //given
        int n = 6;
        int[][] road = 	{{1,2,1},{1,3,2},{2,3,2},{3,4,3},{3,5,2},{3,5,3},{5,6,1}};
        int k = 4;
        //when
        int answer = Solution.solution(n, road, k);
        //then
        assertThat(answer).isEqualTo(4);
    }

}

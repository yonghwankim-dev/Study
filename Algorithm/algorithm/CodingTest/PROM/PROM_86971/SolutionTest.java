package PROM.PROM_86971;

import org.junit.Test;

public class SolutionTest {

    @Test
    public void test() throws Exception{
        //given
        int n = 9;
        int[][] wires = {{1,3},{2,3},{3,4},{4,5},{4,6},{4,7},{7,8},{7,9}};
        //when
        Solution.solution(n, wires);
        //then
    }

}

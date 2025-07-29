package PROM.PROM_12936;

import org.junit.Test;

import java.util.Arrays;

public class SolutionTest {
    @Test
    public void test() throws Exception{
        //given
        int n = 3;
        long k = 5;

        //when
        int[] answer = Solution.solution(n ,k);
        //then
        System.out.println(Arrays.toString(answer));
    }

}

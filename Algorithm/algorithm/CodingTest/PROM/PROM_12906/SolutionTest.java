package PROM.PROM_12906;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class SolutionTest {

    @Test
    public void test() throws Exception{
        //given
        int[] arr = new Random().ints(1000000, 0, 10).toArray();
        //when
        int[] answer = Solution.solution(arr);
        //then
        System.out.println(Arrays.toString(answer));
    }
}

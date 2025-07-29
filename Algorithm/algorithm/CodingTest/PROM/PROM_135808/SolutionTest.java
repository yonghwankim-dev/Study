package PROM.PROM_135808;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Arrays;

public class SolutionTest {
    @Test
    public void test(){
        //given
        
        //when
        int solution = new Solution().solution(3, 4, new int[]{1, 2, 3, 1, 2, 3, 1});
        //then
        Assertions.assertThat(solution).isEqualTo(8);
    }

    @Test
    public void test2(){
        //given
        int[] arr = new int[1000000];
        Arrays.fill(arr, 9);
        //when
        int solution = new Solution().solution(9, 10, arr);
        //then
        Assertions.assertThat(solution).isEqualTo(9*10*100000);
    }

}

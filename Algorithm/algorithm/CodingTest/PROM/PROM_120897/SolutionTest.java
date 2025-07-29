package PROM.PROM_120897;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class SolutionTest {
    @Test
    public void testcase1(){
        int n = 24;
        int[] solution = new Solution().solution(n);
        Assertions.assertThat(solution).isEqualTo(new int[]{1,2,3,4,6,8,12,24});
    }

    @Test
    public void testcase2(){
        int n = 1;
        int[] solution = new Solution().solution(n);
        Assertions.assertThat(solution).isEqualTo(new int[]{1});
    }

}

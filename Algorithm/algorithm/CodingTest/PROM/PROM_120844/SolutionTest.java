package PROM.PROM_120844;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    @Test
    public void testcase1(){
        int[] actual = new Solution().solution(new int[]{1, 2, 3}, "right");

        assertThat(actual).isEqualTo(new int[]{3, 1, 2});
    }

    @Test
    public void testcase2(){
        int[] actual = new Solution().solution(new int[]{4, 455, 6, 4, -1, 45, 6}, "left");

        assertThat(actual).isEqualTo(new int[]{455, 6, 4, -1, 45, 6, 4});
    }

}

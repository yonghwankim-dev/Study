package PROM.PROM_120812;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    @Test
    public void testcase1(){
        int[] array = {1, 2, 3, 3, 4};
        int result = Solution.solution(array);
        assertThat(result).isEqualTo(3);
    }
}

package PROM.PROM_42748;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class SolutionTest {
    @Test
    public void test() throws Exception{
        //given
        int[] array = {1, 5, 2, 6, 3, 7, 4};
        int[][] commands = {{2, 5, 3}, {4, 4, 1}, {1, 7, 3}};

        //when
        int[] actual = Solution.solution(array, commands);

        //then
        Assertions.assertThat(actual).isEqualTo(new int[]{5,6,3});
    }
}

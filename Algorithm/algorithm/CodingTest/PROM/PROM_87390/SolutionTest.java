package PROM.PROM_87390;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class SolutionTest {
    @Test
    public void test() throws Exception{
        //given
        int n = 3;
        int left = 2;
        int right = 5;

        //when
        int[] answer = Solution.solution(n, left, right);
        //then
        Assertions.assertThat(answer).isEqualTo(new int[]{3,2,2,3});
    }
}

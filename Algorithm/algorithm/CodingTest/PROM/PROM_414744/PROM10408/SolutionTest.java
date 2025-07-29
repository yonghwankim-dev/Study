package PROM.PROM_414744.PROM10408;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class SolutionTest {
    @Test
    public void test() throws Exception{
        //given
        int[] queue1 = {3,2,7,2};
        int[] queue2 = {4,6,5,1};

        //when
        int answer = Solution.solution(queue1, queue2);

        //then
        Assertions.assertThat(answer).isEqualTo(2);
    }
}

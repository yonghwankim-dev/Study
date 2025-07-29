package PROM.PROM_42578;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    @Test
    public void test() throws Exception{
        //given
        String[][] clothes = {{"yellow_hat", "headgear"}, {"blue_sunglasses", "eyewear"}, {"green_turban", "headgear"}};
        //when
        int answer = Solution.solution(clothes);
        //then
        assertThat(answer).isEqualTo(5);
    }

}

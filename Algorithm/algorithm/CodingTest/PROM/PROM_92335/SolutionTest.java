package PROM.PROM_92335;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {

    @Test
    public void test() throws Exception{
        //given

        //when
        int answer1 = new Solution().solution(437674, 3);
        int answer2 = new Solution().solution(110011, 10);

        //then
        assertThat(answer1).isEqualTo(3);
        assertThat(answer2).isEqualTo(2);
    }

}

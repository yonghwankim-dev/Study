package PROM.PROM_140108;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class SolutionTest {
    @Test
    public void test(){
        //given
        String s = "banana";
        //when
        int solution = new Solution().solution(s);
        //then
        Assertions.assertThat(solution).isEqualTo(3);
    }
}

package PROM.PROM_120871;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class SolutionTest {
    @Test
    public void test(){
        //given
        int n = 15;
        int n2 = 40;
        //when
        int actual1 = new Solution().solution(n);
        int actual2 = new Solution().solution(n2);
        //then
        Assertions.assertThat(actual1).isEqualTo(25);
        Assertions.assertThat(actual2).isEqualTo(76);
    }
}

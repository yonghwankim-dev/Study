package PROM.PROM_120887;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class SolutionTest {
    @Test
    public void test(){
        //given
        
        //when
        int actual = new Solution().solution(1, 13, 1);
        //then
        Assertions.assertThat(actual).isEqualTo(6);
    }

}

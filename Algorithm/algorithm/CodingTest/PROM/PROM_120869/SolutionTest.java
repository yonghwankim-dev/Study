package PROM.PROM_120869;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class SolutionTest {
    @Test
    public void test(){
        //given
        String[] spell = {"p", "o", "s"};
        String[] dic = {"sod", "eocd", "qixm", "adio", "soo"};
        //when
        int actual = new Solution().solution(spell, dic);
        //then
        Assertions.assertThat(actual).isEqualTo(2);
    }
}

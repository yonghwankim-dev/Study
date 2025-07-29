package PROM.PROM_155651;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class SolutionTest {
    @Test
    public void testcase1(){
        //given
        String[][] book_time = {{"15:00", "17:00"}, {"16:40", "18:20"}, {"14:20", "15:20"}, {"14:10", "19:20"}, {"18:20", "21:20"}};
        //when
        int solution = new Solution().solution(book_time);
        //then
        Assertions.assertThat(solution).isEqualTo(3);
    }

}

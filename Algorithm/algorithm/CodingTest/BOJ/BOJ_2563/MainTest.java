package BOJ.BOJ_2563;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class MainTest {
    @Test
    public void testcase1(){
        //given
        int n = 3;
        int[][] points = {{3, 7}, {15, 7}, {5, 2}};
        //when
        int actual = Main.solution(n, points);
        //then
        Assertions.assertThat(actual).isEqualTo(260);
    }

}

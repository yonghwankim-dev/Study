package BOJ.BOJ_10807;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class MainTest {
    @Test
    public void testSolution_case1(){
        //given
        int n = 11;
        int[] numbers = {1, 4, 1, 2, 4, 2, 4, 2, 3, 4, 4};
        int v = 2;
        //when
        int actual = Main.solution(n, numbers, v);
        //then
        Assertions.assertThat(actual).isEqualTo(3);
    }

    @Test
    public void testSolution_case2(){
        //given
        int n = 11;
        int[] numbers = {1, 4, 1, 2, 4, 2, 4, 2, 3, 4, 4};
        int v = 5;
        //when
        int actual = Main.solution(n, numbers, v);
        //then
        Assertions.assertThat(actual).isEqualTo(0);
    }

}

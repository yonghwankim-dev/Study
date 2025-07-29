package BOJ.BOJ_5597;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MainTest {
    @Test
    public void testSolution_case1(){
        //given
        int[] numbers = {3, 1, 4, 5, 7,
                         9, 6, 10, 11, 12,
                         13, 14, 15, 16, 17,
                         18, 19, 20, 21, 22,
                         23, 24, 25, 26, 27,
                         28, 29, 30};
        //when
        int[] actual = Main.solution(numbers);
        //then
        assertThat(actual).isEqualTo(new int[]{2,8});
    }
}

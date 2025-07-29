package PROM.PROM_67256;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    @Test
    public void test1() throws Exception{
        //given
        int[] numbers = {1, 3, 4, 5, 8, 2, 1, 4, 5, 9, 5};
        String hand = "right";
        String expected = "LRLLLRLLRRL";

        //when
        String actual = new Solution().solution(numbers, hand);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void test2() throws Exception{
        //given
        int[] numbers = {7, 0, 8, 2, 8, 3, 1, 5, 7, 6, 2};
        String hand = "left";
        String expected = "LRLLRRLLLRR";

        //when
        String actual = new Solution().solution(numbers, hand);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void test3() throws Exception{
        //given
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        String hand = "right";
        String expected = "LLRLLRLLRL";

        //when
        String actual = new Solution().solution(numbers, hand);

        //then
        assertThat(actual).isEqualTo(expected);
    }
}

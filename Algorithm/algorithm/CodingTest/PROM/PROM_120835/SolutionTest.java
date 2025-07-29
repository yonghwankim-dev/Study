package PROM.PROM_120835;

import org.junit.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    
    @Test
    public void testcase1(){
        int[] emergency = {3, 76, 24};
        int[] expected = {3, 1, 2};

        int[] actual = new Solution().solution(emergency);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testcase2(){
        int[] emergency = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] expected = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

        int[] actual = new Solution().solution(emergency);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testcase3(){
        int[] emergency = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] expected = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

        int[] actual = new Solution().solution2(emergency);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testcase4(){
        int[] emergency = IntStream.rangeClosed(1, 100).limit(100000000).toArray();


        int[] actual = new Solution().solution2(emergency);

    }

}

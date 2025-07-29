package PROM.PROM_84512;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    @Test
    public void test() throws Exception{
        //given
        String word1 = "AAAAE";
        String word2 = "AAAE";
        String word3 = "I";
        String word4 = "EIO";
        //when

        int actual1 = Solution.solution(word1);
        int actual2 = Solution.solution(word2);
        int actual3 = Solution.solution(word3);
        int actual4 = Solution.solution(word4);

        //then
        assertThat(actual1).isEqualTo(6);
        assertThat(actual2).isEqualTo(10);
        assertThat(actual3).isEqualTo(1563);
        assertThat(actual4).isEqualTo(1189);
    }
}

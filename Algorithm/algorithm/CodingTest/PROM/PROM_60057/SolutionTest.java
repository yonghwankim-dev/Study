package PROM.PROM_60057;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    @Test
    public void test() throws Exception{
        //given

        //when
        int actual1 = Solution.solution("aabbaccc");
        int actual2 = Solution.solution("ababcdcdababcdcd");
        int actual3 = Solution.solution("abcabcdede");
        int actual4 = Solution.solution("abcabcabcabcdededededede");
        int actual5 = Solution.solution("xababcdcdababcdcd");

        //then
        assertThat(actual1).isEqualTo(7);
        assertThat(actual2).isEqualTo(9);
        assertThat(actual3).isEqualTo(8);
        assertThat(actual4).isEqualTo(14);
        assertThat(actual5).isEqualTo(17);

    }
}

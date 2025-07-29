package PROM.PROM_12951;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {

    @Test
    public void test() throws Exception{
        //given
        String s = "3people   unFollowed me";
        //when
        String result = Solution.solution(s);
        //then
        assertThat(result).isEqualTo("3people Unfollowed Me");
    }

    @Test
    public void test2() throws Exception{
        //given
        String s = "me  ";
        //when
        String result = Solution.solution(s);
        //then
        assertThat(result).isEqualTo("Me  ");
    }
}

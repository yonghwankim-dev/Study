package PROM.PROM_118666;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    @Test
    public void test() throws Exception{
        //given
        String[] survey = {"AN", "CF", "MJ", "RT", "NA"};
        int[] choices = {5,3,2,7,5};
        //when
        String answer = Solution.solution(survey, choices);
        //then
        assertThat(answer).isEqualTo("TCMA");
    }

    @Test
    public void test2() throws Exception{
        //given
        String[] survey = {"TR", "RT", "TR"};
        int[] choices = {7,1,3};
        //when
        String answer = Solution.solution(survey, choices);
        //then
        assertThat(answer).isEqualTo("RCJA");
    }

}

package PROM.PROM_120907;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    @Test
    public void testSplitExpAndAnswer(){
        //given
        Solution solution = new Solution();
        String text = "3 - 4 = -3";
        //when
        String[] expAndAnswer = solution.getExpAndAnswer(text);
        String infixExp = solution.getExp(expAndAnswer);
        int expAnswer = solution.getExpAnswer(expAndAnswer);
        //then
        assertThat(infixExp).isEqualTo("3 - 4");
        assertThat(expAnswer).isEqualTo(-3);
    }


}

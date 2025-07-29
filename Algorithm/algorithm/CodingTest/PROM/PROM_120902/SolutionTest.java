package PROM.PROM_120902;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class SolutionTest {
    @Test
    public void testInfixToPostfix(){
        //given
        String my_string = "3 + 4";
        //when
        String actual = new Solution().infixToPostfix(my_string.split(" "));
        //then
        Assertions.assertThat(actual).isEqualTo("3 4 +");
    }
    
    @Test
    public void testSolution(){
        //given
        String my_string = "3 - 4";
        //when
        int actual = new Solution().solution(my_string);
        //then
        Assertions.assertThat(actual).isEqualTo(-1);
    }

}

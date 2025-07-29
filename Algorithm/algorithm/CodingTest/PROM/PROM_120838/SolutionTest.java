package PROM.PROM_120838;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    @Test
    public void testcase1(){
        String letter = ".... . .-.. .-.. ---";

        String actual = new Solution().solution(letter);

        assertThat(actual).isEqualTo("hello");
    }

}

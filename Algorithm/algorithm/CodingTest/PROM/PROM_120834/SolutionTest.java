package PROM.PROM_120834;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class SolutionTest {
    @Test
    public void testcase1(){
        int[] ages = {23, 51, 100};
        String[] expected = {"cd", "fb", "baa"};
        String[] actual = new String[ages.length];

        for(int i = 0; i < ages.length; i++){
            actual[i] = new Solution().solution(ages[i]);
        }

        Assertions.assertThat(actual).isEqualTo(expected);
    }

}

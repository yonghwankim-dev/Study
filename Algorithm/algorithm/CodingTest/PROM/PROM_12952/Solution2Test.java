package PROM.PROM_12952;

import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;


public class Solution2Test {

    @Test
    public void test1() throws Exception{
        //given
        int n = 4;
        //when
        int actual = Solution2.solution(n);
        //then
        assertThat(actual).isEqualTo(2);
    }

    @Test
    public void test2() throws Exception{
        //given
        int n = 8;
        //when
        int actual = Solution2.solution(n);
        //then
        assertThat(actual).isEqualTo(92);
    }

    @Test
    public void test3() throws Exception{
        //given
        int limit = 12;
        int[] actual = new int[limit+1];
        int[] expected = {0, 1, 0, 0, 2, 10, 4, 40, 92, 352, 724, 2680, 14200};
        //when
        for(int n = 1; n <= limit; n++){
            actual[n] = Solution2.solution(n);
        }
        //then
        System.out.println(Arrays.toString(actual));
        assertThat(actual).isEqualTo(expected);
    }
}

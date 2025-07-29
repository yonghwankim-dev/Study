package PROM.PROM_120864;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class SolutionTest {
    @Test
    public void testcase1(){
        //given
        String my_string = "aAb1B2cC34oOp";
        //when
        int answer = new Solution().solution(my_string);
        //then
        Assertions.assertThat(answer).isEqualTo(37);
    }

    @Test
    public void testcase2(){
        //given
        String my_string = "1a2b3c4d123Z";
        //when
        int answer = new Solution().solution(my_string);
        //then
        Assertions.assertThat(answer).isEqualTo(133);
    }

    @Test
    public void testcase3(){
        //given
        String my_string = "abcdefg";
        //when
        int answer = new Solution().solution(my_string);
        //then
        Assertions.assertThat(answer).isEqualTo(0);
    }

    @Test
    public void testcase4(){
        //given
        String my_string = "1";
        //when
        int answer = new Solution().solution(my_string);
        //then
        Assertions.assertThat(answer).isEqualTo(1);
    }

    @Test
    public void testcase5(){
        //given
        String my_string = "a";
        //when
        int answer = new Solution().solution(my_string);
        //then
        Assertions.assertThat(answer).isEqualTo(0);
    }

    @Test
    public void testcase6(){
        //given
        String my_string = "a1b23";
        //when
        int answer = new Solution().solution(my_string);
        //then
        Assertions.assertThat(answer).isEqualTo(24);
    }

    @Test
    public void testcase7(){
        //given
        String my_string = "a1bb1000";
        //when
        int answer = new Solution().solution(my_string);
        //then
        Assertions.assertThat(answer).isEqualTo(1001);
    }
}

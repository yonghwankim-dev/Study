package PROM.PROM_17677;

import org.junit.Assert;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class SolutionTest {

    @Test
    public void test1(){
        int answer = new Solution().solution("FRANCE", "french");

        Assert.assertEquals(16384, answer);
    }

    @Test
    public void test2(){
        int answer = new Solution().solution("handshake", "shake hands");

        //Assert.assertEquals(65536, answer);
    }
    
    @Test
    public void test3() throws Exception{
        //given
        String str1 = "A+C";
        String str2 = "DEF";
        //when
        int answer = Solution.solution(str1, str2);
        //then
        assertThat(answer).isEqualTo(0);
    }

    @Test
    public void test4() throws Exception{
        //given
        String str1 = "BAAAA";
        String str2 = "AAA";
        //when
        int answer = Solution.solution(str1, str2);
        //then
        assertThat(answer).isEqualTo(32768);
    }

    @Test
    public void test5() throws Exception{
        //given
        String str1 = "abc";
        String str2 = "abbb";

        //when
        int answer = Solution.solution(str1, str2);

        //then
        assertThat(answer).isEqualTo(16384);
    }


}

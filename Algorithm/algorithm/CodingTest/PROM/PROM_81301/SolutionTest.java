package PROM.PROM_81301;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {

    @Test
    public void test1() throws Exception{
        //given
        String s = "one4seveneight";

        //when
        int actual = new Solution().solution(s);

        //then
        assertThat(actual).isEqualTo(1478);
    }

    @Test
    public void test2() throws Exception{
        //given
        String s = "23four5six7";

        //when
        int actual = new Solution().solution(s);

        //then
        assertThat(actual).isEqualTo(234567);
    }

    @Test
    public void test3() throws Exception{
        //given
        String s = "2three45sixseven";

        //when
        int actual = new Solution().solution(s);

        //then
        assertThat(actual).isEqualTo(234567);
    }

    @Test
    public void test4() throws Exception{
        //given
        String s = "123";

        //when
        int actual = new Solution().solution(s);

        //then
        assertThat(actual).isEqualTo(123);
    }

}

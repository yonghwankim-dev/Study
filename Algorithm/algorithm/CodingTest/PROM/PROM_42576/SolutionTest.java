package PROM.PROM_42576;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {

    @Test
    public void test() throws Exception{
        //given
        String[] participant = {"leo", "kiki", "eden"};
        String[] completion = {"eden", "kiki"};

        //when
        String actual = Solution.solution(participant, completion);

        //then
        assertThat(actual).isEqualTo("leo");
    }

    @Test
    public void test2() throws Exception{
        //given
        String[] participant = {"marina", "josipa", "nikola", "vinko", "filipa"};
        String[] completion = {"josipa", "filipa", "marina", "nikola"};

        //when
        String actual = Solution.solution(participant, completion);

        //then
        assertThat(actual).isEqualTo("vinko");
    }

    @Test
    public void test3() throws Exception{
        //given
        String[] participant = {"mislav", "stanko", "mislav", "ana"};
        String[] completion = {"stanko", "ana", "mislav"};

        //when
        String actual = Solution.solution(participant, completion);

        //then
        assertThat(actual).isEqualTo("mislav");
    }
}

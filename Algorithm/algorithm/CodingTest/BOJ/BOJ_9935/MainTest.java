package BOJ.BOJ_9935;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MainTest {
    @Test
    public void test1(){
        //given
        String s = "mirkovC4nizCC44";
        String boom = "C4";
        //when
        String answer = Main.solution(s, boom);
        //then
        assertThat(answer).isEqualTo("mirkovniz");
    }

    @Test
    public void test2(){
        //given
        String s = "12ab112ab2ab";
        String boom = "12ab";
        //when
        String answer = Main.solution(s, boom);
        //then
        assertThat(answer).isEqualTo("FRULA");
    }

}

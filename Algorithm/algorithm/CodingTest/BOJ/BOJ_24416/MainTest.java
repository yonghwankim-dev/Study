package BOJ.BOJ_24416;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class MainTest {
    @Test
    public void testcase1(){
        int n = 5;
        String actual = Main.solution(n);
        Assertions.assertThat(actual).isEqualTo("5 3");
    }

    @Test
    public void testcase2(){
        int n = 30;
        String actual = Main.solution(n);
        Assertions.assertThat(actual).isEqualTo("832040 28");
    }
}

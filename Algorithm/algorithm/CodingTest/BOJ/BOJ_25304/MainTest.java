package BOJ.BOJ_25304;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class MainTest {

    @Test
    public void test1() throws Exception{
        //given
        int totalPrice = 260000;
        int n = 4;
        String[] items = {"20000 5", "30000 2", "10000 6", "5000 8"};

        //when
        String actual = Main.solution(totalPrice, n, items);

        //then
        Assertions.assertThat(actual).isEqualTo("YES");
    }

    @Test
    public void teset2() throws Exception{
        //given
        int totalPrice = 250000;
        int n = 4;
        String[] items = {"20000 5", "30000 2", "10000 6", "5000 8"};
        //when
        String actual = Main.solution(totalPrice, n, items);

        //then
        Assertions.assertThat(actual).isEqualTo("NO");
    }
}

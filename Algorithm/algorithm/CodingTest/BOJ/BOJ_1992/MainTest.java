package BOJ.BOJ_1992;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MainTest {
    @Test
    public void test() throws Exception{
        //given
        int n = 8;
        int[][] arr = {{1,1,1,1,0,0,0,0},
                        {1,1,1,1,0,0,0,0},
                        {0,0,0,1,1,1,0,0},
                        {0,0,0,1,1,1,0,0},
                        {1,1,1,1,0,0,0,0},
                        {1,1,1,1,0,0,0,0},
                        {1,1,1,1,0,0,1,1},
                        {1,1,1,1,0,0,1,1}};
        //when
        String answer = Main.solution(n, arr);
        //then
        assertThat(answer).isEqualTo("((110(0101))(0010)1(0001))");
    }
}

package BOJ.BOJ_2738;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MainTest {
    @Test
    public void testcase1(){
        //given
        int n = 3, m = 3;
        int[][] matrix1 = {{1, 1, 1}, {2, 2, 2}, {0, 1, 0}};
        int[][] matrix2 = {{3, 3, 3}, {4, 4, 4}, {5, 5, 100}};
        //when
        int[][] actual = Main.solution(n, m, matrix1, matrix2);
        //then
        assertThat(actual).isEqualTo(new int[][]{{4, 4, 4}, {6, 6, 6}, {5, 6, 100}});
    }
}

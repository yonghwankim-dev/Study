package PROM.PROM_120875;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class SolutionTest {
    @Test
    public void test(){
        //given
        int[][] dots = {{1, 1}, {5, 1}, {1, 10}, {3, 10}};
        //when
        int answer = new Solution().solution(dots);
        //then
        Assertions.assertThat(answer).isEqualTo(1);
    }

    @Test
    public void test2(){
        //given
        int[][] dots = {{1,2},{1,-2},{-1,2},{-1,-2}};
        //when
        int answer = new Solution().solution(dots);
        //then
        Assertions.assertThat(answer).isEqualTo(1);
    }

    @Test
    public void test3(){
        //given
        int[][] dots = {{0,0},{10,0},{0,1},{0,10}};
        //when
        int answer = new Solution().solution(dots);
        //then
        Assertions.assertThat(answer).isEqualTo(0);
    }
    
    @Test
    public void test4(){
        //given
        int[][] dots = {{1,4},{9,2},{3,8},{11,6}};
        //when
        int answer = new Solution().solution(dots);
        //then
        Assertions.assertThat(answer).isEqualTo(1);
    }

    @Test
    public void test5(){
        //given
        int[][] dots = {{1,4},{9,2},{1,4},{9,2}};
        //when
        int answer = new Solution().solution(dots);
        //then
        Assertions.assertThat(answer).isEqualTo(1);
    }

}

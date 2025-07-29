package PROM.PROM_133502;

import org.junit.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    @Test
    public void testSolution(){
        //given
        int[] ingredient = {2, 1, 1, 2, 3, 1, 2, 3, 1};
        //when
        int actual = Solution.solution(ingredient);
        //then
        assertThat(actual).isEqualTo(2);
    }

    @Test
    public void testSolution_case2(){
        //given
        int[] ingredient = {1, 3, 2, 1, 2, 1, 3, 1, 2};
        //when
        int actual = Solution.solution(ingredient);
        //then
        assertThat(actual).isEqualTo(0);
    }

    @Test
    public void testSolution_case3(){
        //given
        int[] ingredient =  new Random().ints(1000000, 1, 4).toArray();
        //when
        int actual = Solution.solution(ingredient);
        //then
        System.out.println(actual);
    }

}

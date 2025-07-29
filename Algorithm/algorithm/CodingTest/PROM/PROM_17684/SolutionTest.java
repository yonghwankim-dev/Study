package PROM.PROM_17684;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {

    @Test
    public void test(){
        //given
        String msg = "KAKAO";
        //when
        int[] answer = Solution.solution(msg);
        //then
        assertThat(answer).isEqualTo(new int[]{11, 1, 27, 15});
    }

    @Test
    public void test2(){
        //given
        String msg = "TOBEORNOTTOBEORTOBEORNOT";
        //when
        int[] answer = Solution.solution(msg);
        //then
        assertThat(answer).isEqualTo(new int[]{20, 15, 2, 5, 15, 18, 14, 15, 20, 27, 29, 31, 36, 30, 32, 34});
    }

    @Test
    public void test3(){
        //given
        String msg = "ABABABABABABABAB";
        //when
        int[] answer = Solution.solution(msg);
        //then
        assertThat(answer).isEqualTo(new int[]{1, 2, 27, 29, 28, 31, 30});
    }

}

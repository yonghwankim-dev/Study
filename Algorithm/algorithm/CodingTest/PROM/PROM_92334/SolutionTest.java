package PROM.PROM_92334;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    @Test
    public void test() {
        //given
        String[] id_list = {"con", "ryan"};
        String[] report = {"ryan con", "ryan con", "ryan con", "ryan con"};
        int k = 2;

        //when
        int[] answer = Solution.solution(id_list, report, k);

        //then
        assertThat(answer).isEqualTo(new int[]{0, 0});

    }

    @Test
    public void test2(){
        //given
        String[] id_list = 	{"muzi", "frodo", "apeach", "neo"};
        String[] report = {"muzi frodo", "apeach frodo", "frodo neo", "muzi neo", "apeach muzi"};
        int k = 2;

        //when
        int[] answer = Solution.solution(id_list, report, k);

        //then
        assertThat(answer).isEqualTo(new int[]{2, 1, 1, 0});
    }
}

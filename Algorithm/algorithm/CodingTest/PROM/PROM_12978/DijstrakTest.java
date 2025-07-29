package PROM.PROM_12978;

import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class DijstrakTest {
    @Test
    public void test() throws Exception{
        //given
        int n = 6; // ������ ����
        int e = 7; // ������ ����
        int[][] edge = {{1,2,2},{1,4,1},{2,3,3},{2,4,2},{3,6,5},{4,5,1},{5,6,2}}; // {{�����, ������, ����ġ},...}
        //when
        int[] answer = Dijstrak.solution(n, e, edge);
        //then
        System.out.println(Arrays.toString(answer));
        assertThat(answer).isEqualTo(new int[]{0,0,2,5,1,2,4});
    }

}

package BOJ.BOJ_24060;

import org.junit.Test;

import java.util.stream.IntStream;

public class MainTest {
    @Test
    public void test(){
        //given
        int n = 500000;
        int k = (int) Math.pow(10, 8);
        int[] arr = IntStream.rangeClosed(1, n).toArray();

        //when
        int answer = Main.solution(n, k, arr);
        //then
        System.out.println(answer);
    }


}

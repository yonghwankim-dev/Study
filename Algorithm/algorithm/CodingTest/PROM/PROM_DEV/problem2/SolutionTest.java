package PROM.PROM_DEV.problem2;

import org.junit.Test;

import java.util.Arrays;

public class SolutionTest {

    @Test
    public void test() throws Exception{
        //given
        String[] queries = {"desktop1 request", "desktop2 request",
                            "desktop3 request", "desktop3 request",
                            "desktop1 release", "desktop3 request"};
        int n = 2;
        //when
        String[] answer = Solution.solution(n, queries);
        //then
        System.out.println(Arrays.toString(answer));
    }
    
    @Test
    public void test2() throws Exception{
        //given
        String[] queries = {"desktop1 request", "desktop2 request",
                "desktop1 release", "desktop2 release",
                "desktop3 request", "desktop3 release",
                "desktop2 request", "desktop1 request"};
        int n = 2;
        //when
        String[] answer = Solution.solution(n, queries);
        //then
        System.out.println(Arrays.toString(answer));
    }

}

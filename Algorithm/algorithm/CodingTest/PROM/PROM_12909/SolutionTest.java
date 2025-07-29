package PROM.PROM_12909;

import org.junit.Test;

public class SolutionTest {
    @Test
    public void test() throws Exception{
        String s1 = "()()";
        String s2 = "(())()";
        String s3 = ")()(";
        String s4 = "(()(";

        System.out.println(new Solution().solution(s1));
        System.out.println(new Solution().solution(s2));
        System.out.println(new Solution().solution(s3));
        System.out.println(new Solution().solution(s4));
    }
}

package PROM.PROM_49994;

import org.junit.Assert;
import org.junit.Test;


public class SolutionTests {

    @Test
    public void test1(){
        String dirs = "ULURRDLLU";
        int except = 7;

        int answer = new Solution().solution(dirs);

        Assert.assertEquals(except, answer);
    }

    @Test
    public void test2(){
        String dirs = "LULLLLLLU";
        int except = 7;

        int answer = new Solution().solution(dirs);

        Assert.assertEquals(except, answer);
    }

    @Test
    public void test3(){
        String dirs = "LRLRL";
        int except = 1;

        int answer = new Solution().solution(dirs);

        Assert.assertEquals(except, answer);
    }

    @Test
    public void test4(){
        String dirs = "UDU";
        int except = 1;

        int answer = new Solution().solution(dirs);

        Assert.assertEquals(except, answer);
    }
}

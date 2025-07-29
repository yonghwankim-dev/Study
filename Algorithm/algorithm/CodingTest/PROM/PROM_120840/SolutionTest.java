package PROM.PROM_120840;

import org.junit.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {

    @Test
    public void testFactorial(){
        BigInteger factorial = new Solution().factorial(BigInteger.valueOf(5L));
        assertThat(factorial.intValue()).isEqualTo(120);
    }

    @Test
    public void testcase1(){
        int actual = new Solution().solution(3, 2);
        assertThat(actual).isEqualTo(3);
    }

    @Test
    public void testcase2(){
        int actual = new Solution().solution(5, 3);
        assertThat(actual).isEqualTo(10);
    }

    @Test
    public void testcase3(){
        int actual = new Solution().solution(1, 1);
        assertThat(actual).isEqualTo(1);
    }

    @Test
    public void testcase4(){
        int actual = new Solution().solution(30, 30);
        assertThat(actual).isEqualTo(1);
    }

}

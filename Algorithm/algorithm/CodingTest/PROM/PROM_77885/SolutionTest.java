package PROM.PROM_77885;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    @Test
    public void test() throws Exception{
        //given
        long[] numbers = {0,1,2,3,4,5,6,7};
        //when
        long[] result = Solution.solution(numbers);
        //then
        assertThat(result).isEqualTo(new long[]{1,2,3,5,5,6,7,11});
    }

    @Test
    public void test2() throws Exception{
        //given
        long[] numbers = {4};
        //when
        long[] result = Solution.solution(numbers);
        //then
        assertThat(result).isEqualTo(new long[]{5});
    }

    @Test
    public void test3() throws Exception{
        //given
        long[] numbers = LongStream.rangeClosed(0, 1000000000000000L).limit(100000).boxed().mapToLong(Long::longValue).toArray();
        //when
        long[] result = Solution.solution(numbers);
        //then
        System.out.println(Arrays.toString(result));
    }
}

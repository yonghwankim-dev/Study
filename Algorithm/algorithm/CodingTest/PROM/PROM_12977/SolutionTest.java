package PROM.PROM_12977;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {

	@Test
	public void test() throws Exception{
	    //given
	    int[] nums = {1, 2, 3, 4};

	    //when
	    int acutal = Solution.solution(nums);

	    //then
		assertThat(acutal).isEqualTo(1);
	}

	@Test
	public void test2() throws Exception{
		//given
		int[] nums = {1, 2, 7, 6, 4};

		//when
		int acutal = Solution.solution(nums);

		//then
		assertThat(acutal).isEqualTo(4);
	}
}

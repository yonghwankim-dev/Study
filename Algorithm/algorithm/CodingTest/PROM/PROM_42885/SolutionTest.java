package PROM.PROM_42885;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class SolutionTest {

	@Test
	public void test1() throws Exception{
		//given
		int[] people = {70, 50, 80, 50};
		int limit = 100;
		int expected = 3;

		//when
		int actual = Solution.solution(people, limit);

		//then
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void test2() throws Exception{
		//given
		int[] people = {70, 80, 50};
		int limit = 100;
		int expected = 3;

		//when
		int actual = Solution.solution(people, limit);

		//then
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void test3() throws Exception{
		//given
		int[] people = {40, 40, 80};
		int limit = 160;
		int expected = 2;

		//when
		int actual = Solution.solution(people, limit);

		//then
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void test4() throws Exception{
		//given
		int[] people = {20, 50, 50, 80};
		int limit = 100;
		int expected = 2;

		//when
		int actual = Solution.solution(people, limit);

		//then
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void test5() throws Exception{
		//given
		int[] people = {40,50,60,70,80,90};
		int limit = 100;
		int expected = 5;

		//when
		int actual = Solution.solution(people, limit);

		//then
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void test6() throws Exception{
		//given
		int[] people = {40,40,40};
		int limit = 100;
		int expected = 2;

		//when
		int actual = Solution.solution(people, limit);

		//then
		assertThat(actual).isEqualTo(expected);
	}
}

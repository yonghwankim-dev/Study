package PROM.PROM_85002;


import org.junit.Test;

import java.util.Arrays;



class SolutionTest {

	@Test
	void test1() {
		int[] weights = {50,82,75,120};
		String[] head2head = {"NLWL","WNLL","LWNW","WWLN"};
		System.out.println(Arrays.toString(Solution.solution(weights, head2head)));
	}
	@Test
	void test2() {
		int[] weights = {145,92,86};
		String[] head2head = {"NLW","WNL","LWN"};
		System.out.println(Arrays.toString(Solution.solution(weights, head2head)));
	}
	@Test
	void test3() {
		int[] weights = {60,70,60};
		String[] head2head = {"NNN","NNN","NNN"};
		System.out.println(Arrays.toString(Solution.solution(weights, head2head)));
	}
}

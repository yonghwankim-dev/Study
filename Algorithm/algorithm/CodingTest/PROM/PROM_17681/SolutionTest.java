package PROM.PROM_17681;


import org.junit.Test;

import java.util.Arrays;

class SolutionTest {

	@Test
	void test() {
		int n = 100;
		int[] arr1 = new int[n];
		int[] arr2 = new int[n];
		for(int i=0;i<n;i++)
		{
			arr1[i] = i+1;
			arr2[i] = i+3;
		}
		System.out.println(Arrays.toString(Solution.solution(n, arr1, arr2)));
	}

}

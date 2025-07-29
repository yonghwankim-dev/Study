package CODEJAM.jam2021.qualification_round.p01_reversort;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

class SolutionTest {

	@Test
	void test() {
		int n = 100;
		List<Integer> L = new ArrayList<>();
		
		for(int i=1; i<=n; i++)
		{
			L.add(n-i+1);
		}
		System.out.println(Solution.reverSort(L));
	}
	
	@Test
	void test2() {
		int n = 2;
		List<Integer> L = new ArrayList<>();
		
		for(int i=1; i<=n; i++)
		{
			L.add(n-i+1);
		}
		System.out.println(Solution.reverSort(L));
	}

}

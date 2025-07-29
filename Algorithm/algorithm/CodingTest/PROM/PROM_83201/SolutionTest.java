package PROM.PROM_83201;


import org.junit.Test;

class SolutionTest {

	@Test
	void test1() {
		Solution sol = new Solution();
		int[][] scores = {{100,90,98,88,65},
				{50,45,99,85,77},
				{47,88,95,80,67},
				{61,57,100,80,65},
				{24,90,94,75,65}
			};
		sol.solution(scores);
		System.out.println();
	}
	
	@Test
	void test2() {
		Solution sol = new Solution();
		int[][] scores = {{50,90},{50,87}};
		sol.solution(scores);
		System.out.println();
	}

	@Test
	void test3() {
		Solution sol = new Solution();
		int[][] scores = {{70,49,90},{68,50,38},{73,31,100}};
		sol.solution(scores);
		System.out.println();
	}
}

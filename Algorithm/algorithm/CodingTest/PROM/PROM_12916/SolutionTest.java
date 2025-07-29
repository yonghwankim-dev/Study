package PROM.PROM_12916;


import org.junit.Test;

class SolutionTest {

	@Test
	void test() {
		StringBuilder sb = new StringBuilder();
    	for(long i=0;i<100000000;i++)
    	{
    		sb.append("p");
    	}
    	System.out.println(Solution.solution(sb.toString()));
	}

}

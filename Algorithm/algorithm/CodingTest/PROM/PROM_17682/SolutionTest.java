package PROM.PROM_17682;


import org.junit.Test;

class SolutionTest {

	@Test
	void test1() {
		String[] dartResults = {"1S2D*3T","1D2S#10S","1D2S0T",
								"1S*2T*3S","1D#2S*3S","1T2D3D#",
								"1D2S3T*"};
		
		
		for(String s : dartResults)
		{
			System.out.println(s+":"+Solution.solution(s));
		}
	}
	
	

	

}

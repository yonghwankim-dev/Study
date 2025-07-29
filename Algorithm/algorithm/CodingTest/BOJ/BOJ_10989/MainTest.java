package BOJ.BOJ_10989;


import org.junit.Test;

import java.io.IOException;

class MainTest {

	@Test
	void test() throws NumberFormatException, IOException {
		int n = 10000000;
		int[] origin = new int[Main.MAX_SIZE+1];
		for(int i=1,j=1;i<=n;i++,j++)
		{
			origin[j] = 10000; 
		}
		int[] result = Main.solution(origin, n);
		
		for(int i=1;i<=n;i++)
		{
			System.out.printf("%d\n", result[i]);
		}
	}
}

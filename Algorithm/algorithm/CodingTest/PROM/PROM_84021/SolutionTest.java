package PROM.PROM_84021;


import org.junit.Test;



class SolutionTest {

	public static void print(int[][] ret)
	{
		for(int i=0;i<ret.length;i++)
		{
			for(int j=0;j<ret.length;j++)
			{
				System.out.print(ret[i][j]+ " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	@Test
	void test1() {
		Solution sol = new Solution();
		int[][] m = {{1,1,1},{1,0,0},{0,0,0}};
		int[][] ret = sol.rotate_90(m);
		
		// before
		print(m);
		// after
		print(ret);
		
	}
	
	@Test
	void test2() {
		Solution sol = new Solution();
		int[][] m = {{1,1,1},{1,0,0},{0,0,0}};
		int[][] ret = m;
		
		for(int i=0;i<4;i++)
		{
			ret = sol.rotate_90(ret);
			print(ret);
		}		
	}

}

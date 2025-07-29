package PROM.PROM_1835;

public class DFSTest {
	boolean[] chosen;
	int[] nums;
	int[] prt;
	int n, r, rst;

	public DFSTest(boolean[] chosen, int[] nums, int[] prt, int n, int r) {
		this.chosen = chosen;
		this.nums = nums;
		this.prt = prt;
		this.n = n;
		this.r = r;
		this.rst = 0;
	}

	public void DFS(int level)
	{
		if(level == r)
		{
			for(int i = 0; i < r; i++)
			{
				System.out.printf("%d ", prt[i]);
			}
			System.out.println();
			
			rst++;
		}
		else
		{
			for(int i = 1; i <= n; i++)
			{
				if(!chosen[i])
				{
					prt[level] = nums[i];
					chosen[i] = true;
					DFS(level + 1);
					chosen[i] = false;
				}
			}
		}
	}
	
	public static void main(String[] args)
	{
		int n = 8;
		int r = 8;
		boolean[] chosen = new boolean[n+1];
		int[] nums = {0, 1, 2, 3, 4, 5, 6, 7, 8};
		int[] prt = new int[n+1];
		
		DFSTest d = new DFSTest(chosen, nums, prt, n, r);
		d.DFS(0);
		
		System.out.printf("rst = %d\n", d.rst);
	}
}

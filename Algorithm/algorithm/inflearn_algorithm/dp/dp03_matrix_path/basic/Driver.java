package dp.dp03_matrix_path.basic;

public class Driver {
	public static int mat(int[][] m, int i, int j)
	{
		if(i==1 && j==1)
		{
			return m[i][j]; 
		}
		else if(j==1)
		{
			return mat(m,i-1,j) + m[i][j];
		}
		else if(i==1)
		{
			return mat(m,i,j-1) + m[i][j];
		}
		else
		{
			return Math.min(mat(m,i-1,j), mat(m,i,j-1)) + m[i][j];
		}
	}
	
	public static void main(String args[])
	{
		int[][] m = {{0,0,0,0,0},
					{0,6,7,12,5},
					{0,5,3,11,18},
					{0,7,17,3,3},
					{0,8,10,14,9}
					};
		System.out.println(mat(m,4,4));
	}
}

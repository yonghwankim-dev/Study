/**
 * n queens problem
 */
package recursion.recursion16_nqueen;

public class Queen {
	private static int N=4;
	private static int [] cols = new int[N+1];
	
	public static boolean promising(int level)
	{
		for(int i=1;i<level;i++)
		{
			if(cols[i]==cols[level]) // ���� ���� �������� �˻�
			{
				return false;
			}
			else if(level-i==Math.abs(cols[level]-cols[i])){ //�밢������ �˻�
				return false;
			}
		}
		return true;
	}
	
	public static boolean queens(int level)
	{
		if(!promising(level))
		{
			return false;
		}
		else if(N==level)
		{
			for(int i=1;i<=N;i++)
			{
				System.out.println("("+i+", "+cols[i] + ")");
			}
			return true;
		}
		else
		{
			for(int i=1;i<=N;i++) // level + 1
			{
				cols[level+1] = i;
				if(queens(level+1))
				{
					return true;
				}
			}
			return false;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		queens(0);
	}

}

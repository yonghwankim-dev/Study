/**
 * 
	powerset(������)
 */
package recursion.recursion17_powerset;

public class PowerSet {
	private static char data[] = {'a','b','c'};
	private static int n = data.length;
	private static boolean[] include = new boolean[n];

	public static void powerSet(int k)
	{
		if(k==n)
		{
			for(int i=0;i<n;i++)
			{
				if(include[i])
				{
					System.out.print(data[i] + " ");
				}
			}
			System.out.println();
			return;
		}
		include[k] = false; //k��° ���Ҹ� ����
		powerSet(k+1);		//k+1������ �κ������� ������ Ž��
		include[k] = true;	//k��° ���� ����
		powerSet(k+1);		//k+1������ �κ������� ������ Ž��
		
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		powerSet(0);
	}

}

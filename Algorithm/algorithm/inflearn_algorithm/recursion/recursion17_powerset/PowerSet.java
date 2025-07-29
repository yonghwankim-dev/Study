/**
 * 
	powerset(멱집합)
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
		include[k] = false; //k번째 원소를 제외
		powerSet(k+1);		//k+1이후의 부분집합의 멱집합 탐색
		include[k] = true;	//k번째 원소 포함
		powerSet(k+1);		//k+1이후의 부분집합의 멱집합 탐색
		
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		powerSet(0);
	}

}

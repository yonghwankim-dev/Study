package recursion.recursion13_twosum;

public class Main {

	// 2-SUM, recursion ����
	// data[begin] ~ data[end] ���̿��� ���� k�� �Ǵ� ���� �����ϴ��� �˻�
	// data �迭�� �������� ���ĵǾ� �ִٰ� ����
	// ��������� ������(begin)�� ������(end)�� �����Ͽ� ȣ��
	public static boolean twoSum(int[] data, int begin, int end, int k)
	{
		// ���� �ߺ� ������ �����ϴٸ� "="�� ���� ��
		if(begin>=end)
		{
			return false;
		}
		else 
		{
			if(data[begin]+data[end]==k)
			{
				return true;
			}
			else if(data[begin]+data[end]<k)
			{
				return twoSum(data, begin+1, end, k);
			}
			else
			{
				return twoSum(data, begin, end-1, k);	
			}
		}	
	}
	
	public static void main(String[] args) {
		int[] data = {1,2,3,4,5};
		System.out.println(Main.twoSum(data, 0, data.length-1, 3));	// Expected Output : true
	}
}

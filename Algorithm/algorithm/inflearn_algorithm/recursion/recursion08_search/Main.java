package recursion.recursion08_search;

public class Main {

	// ���� Ž��
	public static int search(int[] data, int n, int target) 
	{
		if (n <= 0) 
		{
			return -1;
		}
		else if (target == data[n - 1]) 
		{
			return n - 1;
		} 
		else 
		{
			return search(data, n - 1, target);
		}
	}
	
	// ����Ž��, Recursion ����
	// ������(begin)�� ������(end)�� �����Ͽ� �������� �������� ��������� �����մϴ�.
	public static int search(int data[], int begin, int end, int target)
	{
		if(begin>end)
		{
			return -1;
		}
		else if(data[begin]==target)
		{
			return begin;
		}
		else
		{
			return search(data, begin+1, end, target);
		}
	}
	
	public static void main(String[] args) {
		int[] data = {1,2,3,4,5};
		System.out.println(search(data,data.length,3)); // Expected Output : 2
		System.out.println(search(data, 0, data.length, 5));	// Expected Output : 4 
	}

}

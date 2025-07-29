package recursion.recursion12_binarysearch;

public class Main {
	// ����Ž��, iteration����
	// �Ͻ������� 0��°���� n-1��°������ �� �� target�� �ִ��� Ž���մϴ�.
	// �̰��� begin, end ������ Ȱ���Ͽ� ��������� ������ �����մϴ�.
	public static int binarySearch(int[] data, int n, int target)
	{
		int begin = 0, end = n-1;
		while(begin<=end)
		{
			int middle = (begin+end)/2;
			if(data[middle]==target)
			{
				return middle;
			}
			else if(data[middle]>target)
			{
				end = middle - 1;
			}
			else
			{
				begin = middle + 1;
			}
		}
		return -1;
	}
	
	// ����Ž��, recursion ����
	// ��������� ����(begin)�� ��(end)�� ��������� ȣ���մϴ�.
	public static int binarySearch(int[] data, int target, int begin, int end)
	{
		if(begin>end)
		{
			return -1;	// Ž�� ����
		}
		else
		{
			int middle = (begin+end)/2;
			if(data[middle]==target)
			{
				return middle;
			}
			else if(data[middle]>target)
			{
				return binarySearch(data, target, begin, middle-1);
			}
			else
			{
				return binarySearch(data, target, middle+1, end); 
			}
		}		
	}
	
	public static void main(String[] args) {
		int[] data = {1,2,3,4,5};
		System.out.println(binarySearch(data, data.length, 4));	// Expected Output : 3
		System.out.println(binarySearch(data, 5, 0, data.length));	// Expected Output : 4
	}

}

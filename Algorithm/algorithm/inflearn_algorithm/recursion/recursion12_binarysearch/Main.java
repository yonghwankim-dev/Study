package recursion.recursion12_binarysearch;

public class Main {
	// 이진탐색, iteration버전
	// 암시적으로 0번째부터 n-1번째까지의 수 중 target이 있는지 탐색합니다.
	// 이것을 begin, end 변수를 활용하여 명시적으로 범위를 지정합니다.
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
	
	// 이진탐색, recursion 버전
	// 명시적으로 시작(begin)과 끝(end)을 재귀적으로 호출합니다.
	public static int binarySearch(int[] data, int target, int begin, int end)
	{
		if(begin>end)
		{
			return -1;	// 탐색 실패
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

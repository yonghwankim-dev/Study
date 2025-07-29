package recursion.recursion09_findMax;

public class Main {

	// 최대값 찾기
	public static int findMax(int n, int[] data) 
	{
		if (n == 1) 
		{
			return data[0];
		}
		// data[0]에서 data[n-1] 사이에서 최대값을 반환
		return Math.max(data[n - 1], findMax(n - 1, data));
	}

	public static void main(String[] args) {
		int[] data = { 1, 2, 3, 4, 5 };
		System.out.println(findMax(data.length,data)); // Expected Output : 5
	}

}

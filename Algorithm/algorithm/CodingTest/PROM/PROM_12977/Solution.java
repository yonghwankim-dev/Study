package PROM.PROM_12977;


public class Solution {
	static int cnt=0;
	
	public static int getSum(int[] nums, boolean[] visited)
	{
		int sum = 0;
		for(int i = 0; i < nums.length; i++)
		{
			sum += visited[i] ? nums[i] : 0;
		}
		return sum;
	}
	
	public static void combination(int[] nums, boolean[] visited, int depth, int n, int r)
	{
		if(r == 0)
		{
			if(isPrime(getSum(nums, visited)))
			{
				cnt++;
			}
			return;
		}

		if(depth == n)
		{
			return;
		}

		// 특정원소를 포함하는 경우
		visited[depth] = true;
		combination(nums, visited, depth + 1, n, r - 1);
		
		// 특정원소를 포함하지 않는 경우
		visited[depth] = false;
		combination(nums, visited, depth + 1, n, r);
	}

	public static boolean isPrime(int num)
	{	
		for(int i = 2; i * i <= num; i++)
		{
			if(num % i == 0)
			{
				return false;
			}
		}
		return true;
	}
	
	public static int solution(int[] nums) {
        int answer = -1;
        int n = nums.length;
        int r = 3;
        boolean[] flags = new boolean[n];
        
        combination(nums, flags, 0, n, r);
        answer = cnt;
        return answer;
    }
}

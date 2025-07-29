package PROM.PROM_43165;

public class Solution {
	public static int answer = 0;
	public static int count = 0;
	public static int sum(int[] numbers)
	{
		int total = 0;
		for(int n : numbers)
		{
			total += n;
		}
		return total;
	}
	public static void search(int[] numbers, int target, int i)
	{
		if(numbers.length==i)
		{
			int sum = sum(numbers);
			if(sum==target)
			{
				answer++;
			}
			return;
		}
		numbers[i] *= 1;
		search(numbers, target, i+1);
		
		numbers[i] *=(-1);
		search(numbers, target, i+1);
	}
	
	public static int solution(int[] numbers, int target)
	{
		search(numbers, target, 0);
		return answer;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] numbers = {1,2,3};
		int target = 0;
		System.out.println(solution(numbers, target));
	}

}


/**
 * �ٸ� ��� Ǯ��
 *
 */
class Solution2{
	public int solution(int[] numbers, int target)
	{
		int answer = 0;
		answer = dfs(numbers,0,0,target);
		return answer;
	}
	
	public int dfs(int[] numbers, int n, int sum, int target)
	{
		if(n==numbers.length)
		{
			if(sum==target)
			{
				return 1;
			}
			return 0;
		}
		return dfs(numbers, n+1, sum + numbers[n], target) + dfs(numbers, n+1, sum - numbers[n], target);
	}
	
}

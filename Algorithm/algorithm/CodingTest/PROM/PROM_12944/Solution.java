package PROM.PROM_12944;

import java.util.Arrays;


public class Solution {

	public static double solution(int[] arr)
	{
		return Arrays.stream(arr).average().orElse(0);
	}

    
	public static void main(String[] args)
	{
		int[] arr = {1,2,3,4};
		System.out.println(solution(arr));
	}
}

package CSES.CSES_1083;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Solution {
	
	public static long solution1(long n, long[] arr)
	{
		long answer = 0;
		
		// 1. 1~n의 합계를 계산
		long n_sum = (n * (n + 1)) / 2;
		
		// 2. 배열을 순회
		for(long item : arr) {
			// 3. 1~n까지의 합계에서 각각의 요소를 차감
			n_sum -= item;
		}
		
		return n_sum;
	}
	
	public static long solution2(long n, long[] arr) {
		long a1 = 0, a2 = 0;
		
		for(int i = 1; i <= n; i++) {
			a1 = a1^i;
		}
		
		for(int i = 0; i < arr.length; i++) {
			a2 = a2^arr[i];
		}
		
		
		return a1^a2;
	}
		
	public static void main(String args[]) throws NumberFormatException, IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		long n = Integer.parseInt(br.readLine());
		long[] arr = Arrays.stream(br.readLine().split(" ")).mapToLong(Long::parseLong).toArray();
		
		System.out.println(new Solution().solution2(n, arr));
	}
}

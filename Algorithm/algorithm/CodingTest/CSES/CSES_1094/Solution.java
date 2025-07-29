package CSES.CSES_1094;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Solution {
	public long solution(long n, int[] arr) {
		long answer = 0;
		int prev = 0;
		
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] < prev) {
				int move = prev - arr[i];
				arr[i] += move;
				answer += move;
			}
			prev = arr[i];
		}
		
		return answer;		
	}
	
	public static void main(String args[]) throws NumberFormatException, IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		long n = Integer.parseInt(br.readLine());
		int[] arr = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		
		System.out.println(new Solution().solution(n, arr));
			
	}
}

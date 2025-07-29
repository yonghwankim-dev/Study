package org.example.ch15_bin_search.step03;

import java.util.Arrays;

public class Solution {
	public static void main(String[] args) {
		int n = 5;
		int c = 3;
		int[] arr = new int[]{1, 2, 8, 4, 9};
		int answer = solution(arr, n, c);
		System.out.println(answer);
	}

	private static int solution(int[] arr, int n, int c) {
		int result = 0;
		Arrays.sort(arr);
		int start = 1;
		int end = arr[n - 1] - arr[0];

		while (start <= end){
			int mid = (start + end) / 2;
			int count = 1;
			int recent = arr[0];

			for (int i = 1; i < n; i++){
				if (arr[i] - recent >= mid){
					count++;
					recent = arr[i];
				}
			}

			if(count >= c){
				start = mid + 1;
				result = mid;
			}else{
				end = mid - 1;
			}
		}
		return result;
	}

}

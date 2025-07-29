package org.example.ch15_bin_search.step02;

public class Solution {
	public static void main(String[] args) {
		int[] arr = new int[]{-15, 4, 1, 8, 13};
		int answer = solution(arr);
		System.out.println(answer);

		arr = new int[]{-1, 0, 1, 3, 4};
		answer = solution(arr);
		System.out.println(answer);
	}

	private static int solution(int[] arr) {
		return binarySearch(arr, 0, arr.length - 1);
	}

	private static int binarySearch(int[] arr, int start, int end) {
		if(start > end){
			return -1;
		}
		int middle = (start + end) / 2;
		if(arr[middle] == middle){
			return middle;
		}else if(arr[middle] > middle){
			return binarySearch(arr, start, middle - 1);
		}else{
			return binarySearch(arr, middle + 1, end);
		}
	}
}

package org.example.ch7_binary_search;

public class RecursiveBinarySearch {
	public int search(int[] arr, int target, int start, int end){
		if (start > end){
			return -1;
		}
		int mid = (start + end) / 2;
		if (arr[mid] == target){
			return mid;
		}else if (arr[mid] > target){
			return search(arr, target, start, mid - 1);
		}else{
			return search(arr, target, mid + 1, end);
		}
	}
}

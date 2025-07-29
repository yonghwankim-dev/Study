package org.example.ch7_binary_search;

public class IteratorBinarySearch {
	public int search(int[] arr, int target, int start, int end){
		while (start <= end){
			int mid = (start + end) / 2;
			if (arr[mid] == target){
				return mid;
			}
			else if (arr[mid] > target){
				end = mid - 1;
			}else{
				start = mid + 1;
			}
		}
		return -1;
	}
}

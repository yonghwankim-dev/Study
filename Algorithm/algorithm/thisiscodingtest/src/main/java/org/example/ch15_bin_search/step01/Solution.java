package org.example.ch15_bin_search.step01;

public class Solution {
	public static void main(String[] args){
		int[] arr = new int[]{1, 1, 2, 2, 2, 2, 3};
		int target = 2;
		int answer = solution(arr, arr.length, target);
		System.out.println(answer);
	}

	private static int solution(int[] arr, int n, int target) {
		int firstIdx = firstBinarySearch(arr, 0, n-1, target);
		int secondIdx = lastBinarySearch(arr, 0, n-1, target);
		System.out.println("firstIdx : " + firstIdx);
		System.out.println("secondIdx : " + secondIdx);
		return secondIdx - firstIdx + 1;
	}

	private static int firstBinarySearch(int[] arr, int start, int end, int target){
		if(start > end){
			return -1;
		}
		int middle = (start + end) / 2;
		// 해당 값을 가지는 원소 중에서 가장 왼쪽에 있는 경우에만 인덱스 반환
		if((middle == 0 || arr[middle - 1] < target) && arr[middle] == target){
			return middle;
		}else if (arr[middle] >= target){
			// 중간점의 값보다 찾고자 하는 값이 작거나 같은 경우 왼쪽 확인
			return firstBinarySearch(arr, start, middle - 1, target);
		}else{
			// 중간점의 값보다 찾고자 하는 값이 큰 경우 오른쪽 확인
			return firstBinarySearch(arr, middle + 1, end, target);
		}
	}

	private static int lastBinarySearch(int[] arr, int start, int end, int target) {
		if(start > end){
			return -1;
		}
		int middle = (start + end) / 2;
		// 해당 값을 가지는 원소 중에서 가장 오른쪽 있는 경우에만 인덱스 반환
		if((middle == arr.length - 1 || arr[middle + 1] > target) && arr[middle] == target){
			return middle;
		}else if (arr[middle] > target){
			// 중간점의 값보다 찾고자 하는 값이 작은 경우 왼쪽 확인
			return lastBinarySearch(arr, start, middle - 1, target);
		}else{
			// 중간점의 값보다 찾고자 하는 값이 크거나 같은 경우 오른쪽 확인
			return lastBinarySearch(arr, middle + 1, end, target);
		}
	}
}

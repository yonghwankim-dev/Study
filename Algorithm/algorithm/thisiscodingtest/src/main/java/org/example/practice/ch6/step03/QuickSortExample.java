package org.example.practice.ch6.step03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class QuickSortExample {
	public static void main(String[] args) {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
			int[] arr = Arrays.stream(br.readLine().split(" "))
				.mapToInt(Integer::parseInt)
				.toArray();
			int[] answer = solution(arr);
			int[] expected = Arrays.stream(br.readLine().split(" "))
				.mapToInt(Integer::parseInt)
				.toArray();
			System.out.println(Arrays.toString(answer));
			assert Arrays.equals(answer, expected);
		}catch (IOException e){
			throw new IllegalArgumentException(e);
		}
	}

	private static int[] solution(int[] arr){
		int n = arr.length;
		return quickSort(arr, 0, n - 1);
	}

	private static int[] quickSort(int[] arr, int start, int end){
		if (start >= end){
			return arr;
		}
		int pivot = start;
		int left = start + 1;
		int right = end;

		while (left <= right){
			while (left <= end && arr[left] <= arr[pivot]){
				left++;
			}
			while (right > start && arr[right] >= arr[pivot]){
				right--;
			}
			if (left > right){
				swap(pivot, right, arr);
			}else{
				swap(left, right, arr);
			}
		}

		quickSort(arr, start, right - 1);
		quickSort(arr, right + 1, end);

		return arr;
	}

	private static void swap(int from, int to, int[] arr){
		int temp = arr[from];
		arr[from] = arr[to];
		arr[to] = temp;
	}
}

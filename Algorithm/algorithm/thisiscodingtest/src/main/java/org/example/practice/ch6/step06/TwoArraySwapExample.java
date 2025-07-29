package org.example.practice.ch6.step06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

// 두 배열의 원소 교체
public class TwoArraySwapExample {
	public static void main(String[] args) {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
			String[] split = br.readLine().split(" ");
			int n = Integer.parseInt(split[0]);
			int k = Integer.parseInt(split[1]);
			int[] arr1 = Arrays.stream(br.readLine().split(" "))
				.mapToInt(Integer::parseInt)
				.toArray();
			int[] arr2 = Arrays.stream(br.readLine().split(" "))
				.mapToInt(Integer::parseInt)
				.toArray();
			int answer = solution(n, k, arr1, arr2);
			int expected = Integer.parseInt(br.readLine());
			assert answer == expected;
		}catch (IOException e){
			throw new IllegalArgumentException(e);
		}
	}

	private static int solution(int n, int k, int[] arr1, int[] arr2){
		// arr1 오름차순 정렬
		ascSort(arr1);
		// arr2 내림차순 정렬
		descSort(arr2);

		for (int i = 0; i < k; i++){
			arr1[i] = arr2[i];
		}
		return Arrays.stream(arr1)
			.sum();
	}

	private static void ascSort(int[] arr){
		int n = arr.length;
		for (int i = 0; i < n - 1; i++){
			for (int j = i; j < n - 1; j++){
				if (arr[j] > arr[j + 1]){
					swap(j, j + 1, arr);
				}
			}
		}
	}

	private static void descSort(int[] arr){
		int n = arr.length;
		for (int i = 0; i < n - 1; i++){
			for (int j = 0; j < n - 1 - i; j++){
				if (arr[j] < arr[j + 1]){
					swap(j, j + 1, arr);
				}
			}
		}
	}

	private static void swap(int from, int to, int[] arr){
		int temp = arr[from];
		arr[from] = arr[to];
		arr[to] = temp;
	}

}

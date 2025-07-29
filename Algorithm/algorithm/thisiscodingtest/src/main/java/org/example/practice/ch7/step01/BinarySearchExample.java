package org.example.practice.ch7.step01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BinarySearchExample {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int[] arr = Arrays.stream(br.readLine().split(" "))
			.mapToInt(Integer::parseInt)
			.toArray();
		int target = 4;
		int answer = solution(arr, target);
		int expected = Integer.parseInt(br.readLine());
		System.out.println(answer);
		assert answer == expected;
	}

	private static int solution(int[] arr, int target){
		int start = 0;
		int end = arr.length - 1;

		while (start <= end){
			int mid = (start + end) / 2;
			if (arr[mid] == target){
				return mid;
			}else if (arr[mid] > target){
				end = mid - 1;
			}else if (arr[mid] < target){
				start = mid + 1;
			}
		}
		return -1;
	}
}

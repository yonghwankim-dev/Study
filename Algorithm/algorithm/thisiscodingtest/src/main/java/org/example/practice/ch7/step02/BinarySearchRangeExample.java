package org.example.practice.ch7.step02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

// 값이 특정 범위에 속하는 데이터의 개수 구하기
public class BinarySearchRangeExample {
	public static void main(String[] args) {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
			int[] arr = Arrays.stream(br.readLine().split(" "))
				.mapToInt(Integer::parseInt)
				.toArray();
			int[] range = Arrays.stream(br.readLine().split(" "))
				.mapToInt(Integer::parseInt)
				.toArray();
			int expected = Integer.parseInt(br.readLine());
			int answer = solution(arr, range);
			System.out.println(answer);
			assert answer == expected;
		}catch (IOException e){
			throw new IllegalArgumentException(e);
		}
	}

	private static int solution(int[] arr, int[] range){
		int rightValue = range[1];
		int leftValue = range[0];

		int rightIdx = upperBound(arr, rightValue);
		int leftIdx = lowerBound(arr, leftValue);
		System.out.printf("rightIdx=%d, leftIdx=%d%n", rightIdx, leftIdx);
		return rightIdx - leftIdx + 1;
	}

	private static int upperBound(int[] arr, int target){
		int start = 0;
		int end = arr.length - 1;

		while (start < end){
			int mid = (start + end) / 2;
			if (arr[mid] <= target){
				start = mid + 1;
			}else{
				end = mid;
			}
		}
		return start - 1;
	}

	private static int lowerBound(int[] arr, int target){
		int start = 0;
		int end = arr.length - 1;
		while (start < end){
			int mid = (start + end) / 2;
			if (arr[mid] < target){
				start = mid + 1;
			}else{
				end = mid;
			}
		}
		return start;
	}
}

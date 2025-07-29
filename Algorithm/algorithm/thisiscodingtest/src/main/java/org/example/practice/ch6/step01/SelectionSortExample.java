package org.example.practice.ch6.step01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class SelectionSortExample {
	public static void main(String[] args) {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
			int[] arr = Arrays.stream(br.readLine().split(" "))
				.mapToInt(Integer::parseInt)
				.toArray();
			int[] answer = solution(arr);
			int[] expected = Arrays.stream(br.readLine().split(" "))
				.mapToInt(Integer::parseInt)
				.toArray();
			assert Arrays.equals(answer, expected);
			System.out.println(Arrays.toString(answer));
		}catch (IOException e){
			throw new IllegalArgumentException(e);
		}
	}

	private static int[] solution(int[] arr){
		int n = arr.length;
		for (int i = 0; i < n - 1; i++){
			int minValue = arr[i];
			int minIdx = i;
			for (int j = i + 1; j < n; j++){
				if (minValue > arr[j]){
					minValue = arr[j];
					minIdx = j;
				}
			}
			swap(i, minIdx, arr);
		}
		return arr;
	}

	private static void swap(int from, int to, int[] arr){
		int temp = arr[from];
		arr[from] = arr[to];
		arr[to] = temp;
	}
}

package org.example.practice.ch6.step02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;

public class InsertionSortExample {
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
		for (int i = 1; i < n; i++){
			for (int j = i; j > 0; j--){
				if (arr[j] < arr[j-1]){
					swap(j, j-1, arr);
				}
			}
		}
		return arr;
	}

	private static void swap(int from, int to, int[] arr){
		int temp = arr[from];
		arr[from] = arr[to];
		arr[to] = temp;
	}


}

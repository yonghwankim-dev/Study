package org.example.practice.ch6.step04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class CountSortExample {
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
			assert Arrays.equals(answer ,expected);
		}catch (IOException e){
			throw new IllegalArgumentException(e);
		}
	}

	private static int[] solution(int[] arr){
		int n = arr.length;
		int maxValue = max(arr);
		int[] count = new int[maxValue + 1];

		for (int i = 0; i < arr.length; i++){
			count[arr[i]]++;
		}
		int[] result = new int[n];
		int idx = 0;
		for (int i = 0; i < count.length; i++){
			for (int j = 0; j < count[i]; j++){
				result[idx++] = i;
			}
		}
		return result;
	}

	private static int max(int[] arr) {
		int result = arr[0];
		for (int i = 1; i < arr.length; i++){
			if (result < arr[i]){
				result = arr[i];
			}
		}
		return result;
	}
}

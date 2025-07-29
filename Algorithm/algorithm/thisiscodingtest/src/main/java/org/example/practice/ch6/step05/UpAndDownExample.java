package org.example.practice.ch6.step05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

// 위에서 아래로
public class UpAndDownExample {
	public static void main(String[] args) {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
			int n = Integer.parseInt(br.readLine());
			int[] arr = new int[n];
			for (int i = 0; i < n; i++){
				arr[i] = Integer.parseInt(br.readLine());
			}
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
		for (int i = 0; i < n - 1; i++){
			for (int j = i + 1; j < n; j++){
				if (arr[i] < arr[j]){
					swap(i, j, arr);
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

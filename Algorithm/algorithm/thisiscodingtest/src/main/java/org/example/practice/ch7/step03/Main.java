package org.example.practice.ch7.step03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

// 랜선 자르기
class Main {
	public static void main(String[] args) {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
			int[] lenCounts = Arrays.stream(br.readLine().split(" "))
				.mapToInt(Integer::parseInt)
				.toArray();
			int k = lenCounts[0];
			int n = lenCounts[1];
			int[] arr = new int[k];
			for (int i = 0; i < k; i++){
				arr[i] = Integer.parseInt(br.readLine());
			}
			long answer = solution(k, n, arr);
			System.out.println(answer);
		}catch (IOException e){
			throw new IllegalArgumentException(e);
		}
	}

	private static long solution(int k, int n, int[] arr){
		long result = 0;
		Arrays.sort(arr);
		long left = 1;
		long right = arr[k - 1];

		while (left <= right){
			long mid = (left + right) / 2;
			long total = 0;
			for (int num : arr){
				total += (num / mid);
			}

			if (total >= n){
				left = mid + 1;
				result = mid;
			}else{
				right = mid - 1;
			}
		}
		return result;
	}
}

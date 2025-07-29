package org.example.practice.ch3.step02;

import java.util.Arrays;

public class Solution {
	public static void main(String[] args) {
		int n = 5;
		int m = 8;
		int k = 3;
		int[] numbers = {2, 4, 5, 4, 6};
		int answer = solution(n, m, k, numbers);
		assert answer == 46;
	}

	private static int solution(int n, int m, int k, int[] numbers) {
		Arrays.sort(numbers);
		// 2 4 4 5 6
		int answer = 0;
		int count = 0;
		int lastIndex = n - 1;
		for (int i = 0; i < m; i++){
			if (count < k){
				answer += numbers[lastIndex];
				count++;
			}else{
				answer += numbers[lastIndex - 1];
				count = 0;
			}
		}
		return answer;
	}
}

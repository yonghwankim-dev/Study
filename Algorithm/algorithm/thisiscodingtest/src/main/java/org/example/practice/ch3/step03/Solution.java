package org.example.practice.ch3.step03;

import java.util.Arrays;

public class Solution {
	public static void main(String[] args) {
		int n = 3;
		int m = 3;
		int[][] cards = {{3, 1, 2},
			{4, 1, 4},
			{2, 2, 2}
		};
		assert solution(n, m, cards) == 2;
	}

	private static int solution(int n, int m, int[][] cards) {
		int answer = 0;
		for (int i = 0; i < n; i++){
			int minValue = findMinValue(cards[i]);
			answer = Math.max(answer, minValue);
		}
		return answer;
	}

	private static int findMinValue(int[] arr){
		int result = arr[0];
		for (int i = 1; i < arr.length; i++){
			if (arr[i] < result){
				result = arr[i];
			}
		}
		return result;
	}
}

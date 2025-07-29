package org.example.practice.ch3.step02;

import java.util.Arrays;

public class Solution2 {
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
		int count = m / (k+1) * k;
		count += m % (k+1);
		int first = numbers[n-1];
		int second = numbers[n-2];

		int answer = count * first;
		answer += (m - count) * second;

		return answer;
	}
}

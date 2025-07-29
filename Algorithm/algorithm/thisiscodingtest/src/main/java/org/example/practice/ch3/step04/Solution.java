package org.example.practice.ch3.step04;

public class Solution {
	public static void main(String[] args) {
		int n = 25;
		int k = 5;
		assert solution(n, k) == 2;
		assert solution(25, 4) == 5;
		assert solution(100_000, 2) == 21;
	}

	private static int solution(int n, int k) {
		int result = 0;
		while (n >= k){
			while (n % k != 0){
				n--;
				result++;
			}
			n /= k;
			result++;
		}

		while (n > 1){
			n--;
			result++;
		}
		return result;
	}

}

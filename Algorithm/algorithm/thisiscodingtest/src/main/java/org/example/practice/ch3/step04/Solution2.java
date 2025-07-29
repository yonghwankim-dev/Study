package org.example.practice.ch3.step04;

public class Solution2 {
	public static void main(String[] args) {
		int n = 25;
		int k = 5;
		assert solution(n, k) == 2;
		assert solution(25, 4) == 5;
		assert solution(100_000, 2) == 21;
	}

	private static int solution(int n, int k) {
		int result = 0;
		int target;
		while (true){
			target = (n / k) * k;
			result += (n - target);
			n = target;

			if (n < k){
				break;
			}
			result++;
			n /= k;
		}
		result += n - 1;
		return result;
	}
}

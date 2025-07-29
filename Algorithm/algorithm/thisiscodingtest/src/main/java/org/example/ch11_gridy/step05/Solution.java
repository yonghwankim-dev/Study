package org.example.ch11_gridy.step05;

public class Solution {
	public int solution(int n, int m, int[] weights){
		int answer = 0;
		int[] counts = new int[11];

		// 무게들의 개수를 저장
		for(int w : weights){
			counts[w]++;
		}

		// 무게별로 순회하면서 개수에서 무게별 개수를 차감, 남은 차감개수 * 무게별 개수를 더함
		for(int i = 1; i <= m; i++){
			n -= counts[i];
			answer += (counts[i] * n);
		}

		return answer;
	}

	public static void main(String[] args) {
		Solution solution = new Solution();
		int answer = solution.solution(5, 3, new int[] {1, 3, 2, 3, 2});
		System.out.println(answer);

		answer = solution.solution(8, 5, new int[] {1, 5, 4, 3, 2, 4, 5, 2});
		System.out.println(answer);
	}
}

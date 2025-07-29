package org.example.ch3_gridy;

// 숫자 카드 게임
public class Solution2 {
	public int solution(int[][] cards){
		int answer = 0;
		for (int[] row : cards){
			int minValue = row[0];
			for (int card : row){
				minValue = Math.min(minValue, card);
			}
			answer = Math.max(answer, minValue);
		}
		return answer;
	}
}

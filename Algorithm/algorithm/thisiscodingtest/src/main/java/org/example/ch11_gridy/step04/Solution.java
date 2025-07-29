package org.example.ch11_gridy.step04;

import java.util.Arrays;

public class Solution {
	public int solution(int[] coins, int n){
		Arrays.sort(coins);

		int target = 1;
		for(int coin : coins){
			if(target >= coin){
				target += coin;
			}else{
				break;
			}
		}
		return target;
	}

	public static void main(String[] args) {
		Solution solution = new Solution();
		int answer = solution.solution(new int[] {3, 2, 1, 1, 9}, 5);
		System.out.println(answer);

		int answer2 = solution.solution(new int[] {1, 2, 3, 5, 13}, 5);
		System.out.println(answer2);
	}
}

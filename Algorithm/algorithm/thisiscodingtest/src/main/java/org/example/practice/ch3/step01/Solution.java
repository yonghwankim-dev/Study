package org.example.practice.ch3.step01;

public class Solution {
	public static void main(String[] args) {
		System.out.println(solution(1260));
		System.out.println(solution(10));
	}

	private static int solution(int n) {
		int answer = 0;
		int[] coins = {500, 100, 50, 10};

		int remainCoin = n;
		for (int coin : coins){
			answer += remainCoin / coin;
			remainCoin %= coin;
		}
		return answer;
	}
}

package org.example.practice.ch3.step05;

public class Solution {
	public static int solution(int n, int k){
		int answer = 0;
		int number = n;

		while (number != 1){
			if (number % k == 0){
				number /= k;
			}else{
				number--;
			}
			answer++;
		}
		return answer;
	}
	public static void main(String[] args) {
		assert solution(17,4) == 3;
		assert solution(25, 5) == 2;
	}
}

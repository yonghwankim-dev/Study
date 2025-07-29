package org.example.ch11_gridy.step01;

import java.util.Arrays;

public class Solution {
	public static void main(String[] args) {
		int n = 5;
		int[] person = {2, 3, 1, 2, 2};
		int answer = solution(person, n);
		System.out.println(answer);
	}

	public static int solution(int[] person, int n){
		int answer = 0;
		// 1. 오름차순 정렬
		Arrays.sort(person);

		// 2. 반복시마다 현재 그룹에 +1해준다
		int count = 0;
		for (int i = 0; i < n; i++){
			count++;
			// 3. 현재 그룹의 모험가 수가 현재 모험가의 공포도 이상이라면 그룹을 결성한다
			if (count >= person[i]){
				answer++;
				count = 0;
			}
		}
		return answer;
	}
}

package org.example.ch3_gridy;

// 1이 될때까지
public class Solution3 {

	public int solution(int n, int k){
		int answer = 0;
		int target;
		while(true){
			target = (n / k) * k;
			answer += (n - target);
			n = target;

			if (n < k){
				break;
			}

			answer++;
			n /= k;
		}

		answer += (n - 1);
		return answer;
	}
}

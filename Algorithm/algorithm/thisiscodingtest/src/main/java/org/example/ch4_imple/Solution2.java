package org.example.ch4_imple;

// 시각
public class Solution2 {
	public int solution(int n){
		int answer = 0;
		for (int h = 0; h <= n; h++){
			for (int m = 0; m <= 59; m++){
				for (int s = 0; s <= 59; s++){
					if (String.join("", String.valueOf(h), String.valueOf(m), String.valueOf(s)).contains("3")){
						answer++;
					}
				}
			}
		}
		return answer;
	}
}

package org.example.practice.ch4.step02;

public class Solution {
	public static void main(String[] args) {
		int hour = 5;
		assert solution(hour) == 11475;
	}

	private static int solution(int hour){
		int result = 0;
		// O(24 * 60 * 60) = 86400
		for (int h = 0; h <= hour; h++){
			for (int m = 0; m <= 59; m++){
				for (int s = 0; s <= 59; s++){
					String time = String.format("%d%d%d", h, m, s);
					if (time.contains("3")){
						result++;
					}
				}
			}
		}
		return result;
	}
}

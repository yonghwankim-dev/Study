package org.example.ch11_gridy.step03;

public class Solution {
	public int solution(String s){
		int count0 = 0; // 모든 문자열에서 1에서 0으로 변경해야 하는 횟수
		int count1 = 0; // 모든 문자열에서 0에서 1로 변경해야 하는 횟수

		String[] numbers = s.split("");

		if(numbers[0].equals("1")){
			count0++;
		}else{
			count1++;
		}

		for(int i = 0; i < numbers.length - 1; i++){
			if(!numbers[i].equals(numbers[i+1])){
				if(numbers[i+1].equals("1")){
					count0++;
				}else{
					count1++;
				}
			}
		}
		return Math.min(count0, count1);
	}

	public int solution2(String s){
		String[] data = s.split("");
		int count = 0;
		int comp = data[0].equals("0") ? 0 : 1;

		for(int i = 1; i < data.length; i++){
			if(comp !=  Integer.parseInt(data[i])){
				comp = Integer.parseInt(data[i]);
				count++;
			}
		}
		return (int)Math.ceil((double)count / 2);
	}

	public static void main(String[] args) {
		Solution solution = new Solution();
		int answer1 = solution.solution("0001100");
		assert answer1 == 1;

		int answer2 = solution.solution("1001100");
		assert answer2 == 2;

		int answer3 = solution.solution2("0001100");
		assert answer3 == 1;

		int answer4 = solution.solution2("1001100");
		assert answer4 == 2;
	}
}

package PROM.PROM_42842;

class Solution {
	public int[] solution(int brown, int yellow) {
		int[] answer = new int[2];

		for(int y_width = 1; y_width <= yellow; y_width++){
			int y_height = yellow   / y_width;
			int b_width  = y_width  + 2;
			int b_height = y_height + 2;
			int b = (b_width * b_height) - yellow;

			if(b == brown && b_width >= b_height){
				answer[0] = b_width;
				answer[1] = b_height;
				break;
			}
		}
		return answer;
	}
	public static void main(String[] args){
		new Solution().solution(10, 2);
	}
}

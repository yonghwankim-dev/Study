package PROM.PROM_76502;

import java.util.Stack;

class Solution {
	public String rotateLeft(String s){
		return s.substring(1) + s.charAt(0);
	}

	public boolean isCorrectBracket(String s){
		Stack<Character> stack = new Stack<>();

		for(char c :  s.toCharArray()){
			if(c == '(' || c == '[' || c == '{'){
				stack.add(c);
			}else{
				if(stack.isEmpty()){
					return false;
				}
				if(c == ')' && stack.peek() == '('){
					stack.pop();
				}else if(c == ']' && stack.peek() == '['){
					stack.pop();
				}else if(c == '}' && stack.peek() == '{'){
					stack.pop();
				}else{
					stack.add(c);
				}
			}
		}

		if(stack.isEmpty()){
			return true;
		}else{
			return false;
		}
	}

	public int solution(String s) {
		int answer = 0;

		// 0~len(s)까지 반복
		for(int x = 0; x < s.length(); x++){

			// 괄호를 왼쪽으로 x회전 수행
			if(x > 0){
				s = rotateLeft(s);
			}

			// 괄호가 올바른지 검사
			if(isCorrectBracket(s)){
				answer++;
			}
		}
		return answer;
	}

	public static void main(String[] args){
		String s = "[](){}";

		new Solution().solution(s);
	}
}

package PROM.PROM_12909;

import java.util.Stack;

class Solution {
	public static boolean solution(String s) {
		Stack<Character> stack = new Stack<>();

		for(char c : s.toCharArray()){
			if(isOpen(c)){
				stack.add(c);
				continue;
			}else if(stack.isEmpty()){
				return false;
			}
			stack.pop();// ')'
		}

		return stack.isEmpty();
	}

	private static boolean isOpen(char ch){
		return ch == '(';
	}
}

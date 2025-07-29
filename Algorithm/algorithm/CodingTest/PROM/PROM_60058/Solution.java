package PROM.PROM_60058;

import java.util.Stack;

class Solution {
	public String[] splitString(String p){
		String[] split = {"", ""};
		int open_bracket = 0;
		int close_bracket = 0;

		for(int i = 0; i < p.length(); i++){
			if(p.charAt(i) == '('){
				open_bracket++;
			}else if(p.charAt(i) == ')'){
				close_bracket++;
			}

			if(open_bracket == close_bracket){
				split[0] = p.substring(0, i+1);
				split[1] = p.substring(i+1);
				break;
			}
		}
		return split;
	}

	public boolean isCorrect(String p){
		Stack<Character> stack = new Stack<>();

		for(char c : p.toCharArray()){
			if(c == '('){
				stack.push(c);
			}else{
				if(!stack.isEmpty() && stack.peek() == '('){
					stack.pop();
				}else{
					return false;
				}
			}
		}
		if(stack.isEmpty()){
			return true;
		}else{
			return false;
		}
	}

	// ���ڿ��� ù��° ���ڿ� ������ ���ڸ� ������ �� ��ȯ�մϴ�.
	public String deleteFirstLastCharacter(String p){
		return p.substring(1, p.length()-1);
	}

	// ���ڿ��� ��ȣ ������ �ݴ�� ��ȯ�Ͽ� ��ȯ�մϴ�.
	public String reverseBracket(String p){
		StringBuilder sb = new StringBuilder();

		for(char c : p.toCharArray()){
			if(c == '('){
				sb.append(')');
			}else{
				sb.append('(');
			}
		}
		return sb.toString();
	}

	public String solution(String p) {
		String answer = "";

		// 1. p�� �� ���ڿ��̶�� �״�� ��ȯ
		if(p.equals("")){
			return p;
		}

		// 2. ���ڿ� p�� u,v�� �и�
		String[] split = splitString(p);
		String u = split[0];
		String v = split[1];

		if(isCorrect(u)){
			answer =  u + solution(v);
		}else{
			answer = "(" + solution(v) + ")" + reverseBracket(deleteFirstLastCharacter(u));
		}
		return answer;
	}
	public static void main(String[] args){
		String p3 = "()))((()";
		System.out.println(new Solution().solution(p3));

	}
}

package org.example.ch13_graph.step04;

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
		return stack.isEmpty();
    }

    // 문자열의 첫번째 문자와 마지막 문자를 제거한 후 반환합니다.
    public String deleteFirstLastCharacter(String p){
        return p.substring(1, p.length()-1);
    }

    // 문자열의 괄호 방향을 반대로 변환하여 반환합니다.
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

        // 1. p가 빈 문자열이라면 그대로 반환
        if(p.equals("")){
            return p;
        }

        // 2. 문자열 p를 u,v로 분리
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
}

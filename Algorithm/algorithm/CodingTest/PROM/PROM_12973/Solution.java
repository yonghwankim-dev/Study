package PROM.PROM_12973;

import java.util.Stack;

public class Solution{
	private static Stack<Character> stack;
    public static int solution(String s)
    {
    	stack = new Stack<>();

		for(char alphabet : s.toCharArray()){
			if(isRemovable(alphabet)){
				stack.pop();
				continue;
			}
			stack.add(alphabet);
		}

    	return isEmpty();
    }

	private static boolean isRemovable(char alphabet){
		return !stack.isEmpty() && stack.peek().equals(alphabet);
	}

	private static int isEmpty(){
		return stack.isEmpty() ? 1 : 0;
	}
}

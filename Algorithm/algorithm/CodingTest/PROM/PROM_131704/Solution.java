package PROM.PROM_131704;

import java.util.Stack;

public class Solution {
	public int solution(int[] order) {
		int[] priority = new int[order.length];

		for (int i = 0; i < order.length; i++){
			priority[order[i] - 1] = i;
		}

		Stack<Integer> assistant = new Stack<>();
		int target = 0;

		for (int i = 0; i < order.length; i++){
			if (priority[i] == target){
				target++;
			}else{
				assistant.push(priority[i]);
			}

			while (!assistant.empty() && assistant.peek() == target){
				assistant.pop();
				target++;
			}
		}
		return target;
	}
}

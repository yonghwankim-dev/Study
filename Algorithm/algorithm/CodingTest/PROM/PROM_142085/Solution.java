package PROM.PROM_142085;

import java.util.Comparator;
import java.util.PriorityQueue;

class Solution {
	public int solution(int n, int k, int[] enemy) {
		int answer = 0;
		PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.reverseOrder());
		for (int e : enemy){
			queue.offer(e);
		}
		while (!queue.isEmpty()){
			Integer e = queue.poll();

			if (k > 0){
				k--;
				answer++;
				continue;
			}
			if (n < e) {
				break;
			}
			n -= e;
			answer++;
		}
		return answer;
	}

	public static void main(String[] args) {
		int answer = new Solution().solution(7, 3, new int[] {4, 2, 4, 5, 3, 3, 1});
		System.out.println(answer);
	}
}

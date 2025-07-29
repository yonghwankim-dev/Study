package org.example.ch10_graph.class03.step03;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 커리큘럼
 * 위상 정렬
 * 1. 진입 차수가 0인 노드를 큐에 넎는다.
 * 2. 큐에서 노드를 꺼내서 노드와 연결된 간선들을 제거하면서 진입 차수를 1씩 감소
 * 3. 진입차수가 0이면 다시 노드를 큐에 넣음
 * 4. 큐가 빌때까지 2~3번 과정 반복
 */
public class Solution {
	public static void main(String[] args) {
		int n = 5;
		String[] inputs = {
			"10 -1",
			"10 1 -1",
			"4 1 -1",
			"4 3 1 -1",
			"3 3 -1"
		};
		LinkedList<Integer>[] graph = new LinkedList[n + 1];
		for (int i = 1; i <= n; i++){
			graph[i] = new LinkedList<>();
		}
		int[] indegree = new int[n + 1];
		int[] costs = new int[n + 1];
		for (int i = 0; i < inputs.length; i++){
			StringTokenizer st = new StringTokenizer(inputs[i]);
			int cost = Integer.parseInt(st.nextToken());
			costs[i + 1] = cost;
			while (st.hasMoreTokens()){
				int node = Integer.parseInt(st.nextToken());
				if (node == -1){
					break;
				}
				graph[node].add(i + 1);
				indegree[i + 1]++;
			}
		}
		int[] answer = solution(graph, indegree, costs, n);
		for (int i = 1; i <= n; i++){
			System.out.println(answer[i]);
		}
	}

	private static int[] solution(LinkedList<Integer>[] graph, int[] indegree, int[] costs, int n) {
		int[] answer = new int[n + 1];
		for (int i = 1; i <= n; i++){
			answer[i - 1] = costs[i];
		}
		Queue<Integer> queue = new LinkedList<>();
		for (int i = 1; i <= n; i++){
			if (indegree[i] == 0){
				queue.offer(i);
			}
		}

		while (!queue.isEmpty()){
			int now = queue.poll();
			for (int i : graph[now]){
				indegree[i]--;
				answer[i] = Math.max(answer[i], answer[now] + costs[i]);
				if (indegree[i] == 0){
					queue.offer(i);
				}
			}
		}

		return answer;
	}
}

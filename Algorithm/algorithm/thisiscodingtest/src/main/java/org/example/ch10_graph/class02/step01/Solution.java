package org.example.ch10_graph.class02.step01;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * 위상 정렬
 */
public class Solution {
	public static void main(String[] args) {
		int n = 7;
		int m = 8;
		int[] inDegree = new int[n + 1];
		LinkedList<Integer>[] graph = new LinkedList[n + 1];
		for (int i = 1; i <= n; i++){
			graph[i] = new LinkedList<>();
		}

		int[][] inputs = {
			{1, 2},
			{1, 5},
			{2, 3},
			{2, 6},
			{3, 4},
			{4, 7},
			{5, 6},
			{6, 4}
		};
		for (int i = 0; i < m; i++){
			int[] edge = inputs[i];
			int src = edge[0];
			int dst = edge[1];
			graph[src].add(dst);
			inDegree[dst]++;
		}
		int[] answer = solution(graph, inDegree, n);
		String output = Arrays.stream(answer)
			.mapToObj(String::valueOf)
			.collect(Collectors.joining(" "));
		System.out.println(output);
	}

	private static int[] solution(LinkedList<Integer>[] graph, int[] inDegree, int n) {
		int[] answer = new int[n];
		int idx = 0;
		Queue<Integer> queue = new LinkedList<>();
		for (int i = 1; i <= n; i++){
			if (inDegree[i] == 0){
				queue.offer(i);
			}
		}

		while (!queue.isEmpty()){
			int node = queue.poll();
			for (int dst : graph[node]){
				inDegree[dst]--;
				if (inDegree[dst] == 0){
					queue.offer(dst);
				}
			}
			answer[idx++] = node;
		}

		return answer;
	}
}

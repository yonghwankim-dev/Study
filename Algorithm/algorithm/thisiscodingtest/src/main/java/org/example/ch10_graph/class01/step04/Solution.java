package org.example.ch10_graph.class01.step04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 서로소 집합, 크루스칼 알고리즘(최손 신장 트리)
 */
public class Solution {
	public static void main(String[] args) {
		int n = 7;
		int m = 9;

		Queue<int[]> edges = new PriorityQueue<>(Comparator.comparingInt(o1->o1[2]));
		String[] inputs = {
			"1 2 29",
			"1 5 75",
			"2 3 35",
			"2 6 34",
			"3 4 7",
			"4 6 23",
			"4 7 13",
			"5 6 53",
			"6 7 25"
		};
		for (int i = 0; i < m; i++){
			StringTokenizer st = new StringTokenizer(inputs[i]);
			int src = Integer.parseInt(st.nextToken());
			int dst = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			edges.offer(new int[]{src, dst, cost});
		}
		int answer = solution(edges, n);
		System.out.println(answer);
	}

	private static int solution(Queue<int[]> edges, int n) {
		int answer = 0;
		int[] parent = new int[n + 1];
		for (int i = 1; i <= n; i++){
			parent[i] = i;
		}

		while (!edges.isEmpty()){
			int[] edge = edges.poll();
			int src = edge[0];
			int dst = edge[1];
			int cost = edge[2];
			if (find_parent(parent, src) != find_parent(parent, dst)){
				union_parent(parent, src, dst);
				answer += cost;
			}
		}
		return answer;
	}

	private static int find_parent(int[] parent, int i) {
		if (parent[i] != i){
			parent[i] = find_parent(parent, parent[i]);
		}
		return parent[i];
	}

	private static void union_parent(int[] parent, int src, int dst) {
		int a = find_parent(parent, src);
		int b = find_parent(parent, dst);
		if (a < b){
			parent[b] = a;
		}else{
			parent[a] = b;
		}
	}
}

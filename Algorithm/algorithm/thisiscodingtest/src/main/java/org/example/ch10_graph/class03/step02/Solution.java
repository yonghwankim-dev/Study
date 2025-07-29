package org.example.ch10_graph.class03.step02;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 도시 분할 계획
 * 최소 신장 트리
 * 1. 간선의 비용을 기준으로 간선들을 오름차순 정렬
 * 2. 간선을 최소신장 트리에 포함했을때 싸이클 여부를 확인
 * 2.1 싸이클이 발생하면 최소신장 트리 집합에 포함 x
 * 2.2 싸이클이 발생하지 않으면 최소신장 트리 집합에 포함 o
 * 3. 최소신장 트리 집합의 비용합을 계산해서 출력
 */
public class Solution {
	public static void main(String[] args) {
		int n = 7;
		int m = 12;

		Queue<int[]> edges = new PriorityQueue<>(Comparator.comparingInt(o1->o1[2]));
		String[] inputs = {
			"1 2 3",
			"1 3 2",
			"3 2 1",
			"2 5 2",
			"3 4 4",
			"7 3 6",
			"5 1 5",
			"1 6 2",
			"6 4 1",
			"6 5 3",
			"4 5 3",
			"6 7 4"
		};
		for (int i = 0; i < m; i++){
			StringTokenizer st = new StringTokenizer(inputs[i]);
			int src = Integer.parseInt(st.nextToken());
			int dst = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			edges.add(new int[]{src, dst, cost});
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
		int last = 0;
		while (!edges.isEmpty()){
			int[] edge = edges.poll();
			int src = edge[0];
			int dst = edge[1];
			int cost = edge[2];

			if (find_parent(parent, src) != find_parent(parent, dst)){
				union_parent(parent, src, dst);
				answer += cost;
				last = cost;
			}
		}
		return answer - last;
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

	private static int find_parent(int[] parent, int i) {
		if (parent[i] != i){
			parent[i] = find_parent(parent, parent[i]);
		}
		return parent[i];
	}
}

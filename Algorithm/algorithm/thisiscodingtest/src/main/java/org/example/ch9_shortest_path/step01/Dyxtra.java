package org.example.ch9_shortest_path.step01;

import java.util.Arrays;
import java.util.LinkedList;

public class Dyxtra {
	// O(V^2)
	public int[] solution(int n, int start, LinkedList<int[]>[] graph){
		boolean[] visited = new boolean[n + 1];
		int[] distance = new int[n + 1];
		for (int i = 0; i <= n; i++){
			distance[i] = Integer.MAX_VALUE;
		}

		// 시작 노드에 대해서 초기화
		distance[start] = 0;
		visited[start] = true;
		for(int[] adj : graph[start]){
			distance[adj[0]] = adj[1];
		}

		// 시작 노드를 제외한 n-1개의 노드에 대해 반복
		for (int i = 1; i <= n; i++){
			// 현재 최단 거리가 가장 짧은 노드를 꺼내서, 방문 처리
			int now = getSmallestNode(n, distance, visited);
			visited[now] = true;
			// 현재 노드와 연결된 다른 노드를 확인
			for (int[] adj : graph[now]){
				// 현재 노드를 거쳐서 다른 노드로 이동하는 거리가 더 짧은 경우
				distance[adj[0]] = Math.min(distance[adj[0]], distance[now] + adj[1]);
			}
		}

		return distance;
	}

	private int getSmallestNode(int n, int[] distance, boolean[] visited) {
		int minValue = Integer.MAX_VALUE;
		int index = 1; // 가장 최단 거리가 짧은 노드 인덱스
		for (int i = 1; i <= n; i++){
			if (distance[i] < minValue && !visited[i]){
				minValue = distance[i];
				index = i;
			}
		}
		return index;
	}

	public static void main(String[] args) {
		int n = 6;
		int start = 1;
		LinkedList<int[]>[] graph = new LinkedList[n + 1];
		String[] inputs = {
			"1 2 2",
			"1 4 1",
			"1 3 5",
			"2 3 3",
			"2 4 2",
			"3 2 3",
			"3 6 5",
			"4 3 3",
			"4 5 1",
			"5 3 1",
			"5 6 2"};
		for (int i = 0; i <= n; i++){
			graph[i] = new LinkedList<>();
		}
		for (String input : inputs) {
			String[] split = input.split(" ");
			int src = Integer.parseInt(split[0]);
			int dst = Integer.parseInt(split[1]);
			int edge = Integer.parseInt(split[2]);
			graph[src].add(new int[] {dst, edge});
		}
		Dyxtra dyxtra = new Dyxtra();
		int[] answer = dyxtra.solution(n, start, graph);
		System.out.println(Arrays.toString(answer));
	}
}

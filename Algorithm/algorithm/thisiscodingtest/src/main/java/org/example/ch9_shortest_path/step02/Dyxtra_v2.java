package org.example.ch9_shortest_path.step02;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 시간복잡도 : O(ElogV)
 * E=간선개수, V=노드 개수
 */
public class Dyxtra_v2 {

	private int[] solution(int n, int start, LinkedList<int[]>[] graph){
		int[] distance = new int[n + 1];
		for (int i = 0; i <= n; i++){
			distance[i] = Integer.MAX_VALUE;
		}
		Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));

		// 시작 노드로 가기 위한 최단 경로는 0으로 설정하여 큐에 삽입
		queue.offer(new int[]{start, 0});
		distance[start] = 0;

		while(!queue.isEmpty()){
			// 가장 최단 거리가 짤븡ㄴ 노드에 대한 정보 꺼내기
			int[] node = queue.poll();
			int now = node[0];
			int dist = node[1];
			// 현재 노드가 이미 처리된 적이 있는 노드라면 무시
			if (distance[now] < dist){
				continue;
			}
			System.out.printf("%d번 노드, 간선=%d%n", now, dist);
			// 현재 노드와 연결된 다른 인접한 노드들을 확인
			for (int[] adj : graph[now]) {
				int cost = dist + adj[1];
				if (cost < distance[adj[0]]){
					distance[adj[0]] = cost;
					queue.offer(new int[]{adj[0], cost});
				}
			}
		}
		return distance;
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
		Dyxtra_v2 dyxtra = new Dyxtra_v2();
		int[] answer = dyxtra.solution(n, start, graph);
		System.out.println(Arrays.toString(answer));
	}
}

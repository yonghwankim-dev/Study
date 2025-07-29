package org.example.ch9_shortest_path.step05;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 최단 경로 : 전
 */
public class Solution {
	public int[] solution(LinkedList<int[]>[] graph, int start){
		int[] distance = new int[graph.length];
		// 1. distance 최댓값으로 초기화
		for (int i = 1; i < distance.length; i++){
			distance[i] = Integer.MAX_VALUE;
		}

		Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));

		queue.offer(new int[]{start, 0});
		while(!queue.isEmpty()){
			int[] node = queue.poll();
			int now = node[0];
			int dist = node[1];

			if (distance[now] < dist){
				continue;
			}

			for (int[] adj : graph[now]){
				int cost = adj[1] + dist;
				if (cost < distance[adj[0]]){
					distance[adj[0]] = cost;
					queue.offer(new int[]{adj[0], cost});
				}
			}
		}

		int city = 0;
		int maxTime = 0;
		for (int i = 1; i < distance.length; i++){
			if (distance[i] != Integer.MAX_VALUE && distance[i] != 0){
				city++;
				maxTime = Math.max(maxTime, distance[i]);
			}
		}
		return new int[]{city, maxTime};
	}

	public static void main(String[] args) {
		int n = 3;
		int m = 2;
		int start = 1;
		LinkedList<int[]>[] graph = new LinkedList[n + 1];

		for (int i = 1; i < graph.length; i++){
			graph[i] = new LinkedList<>();
		}

		String[] inputs = {"1 2 4", "1 3 2"};
		for (String input : inputs){
			String[] split = input.split(" ");
			int src = Integer.parseInt(split[0]);
			int dst = Integer.parseInt(split[1]);
			int time = Integer.parseInt(split[2]);
			graph[src].add(new int[]{dst, time});
		}
		int[] answer = new Solution().solution(graph, start);
		System.out.println(Arrays.toString(answer));
	}
}

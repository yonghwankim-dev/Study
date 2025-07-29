package org.example.practice.ch9.step02;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.Comparator;

public class DyxtraV2Example {
	public static void main(String[] args) {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
			String[] split = br.readLine().split(" ");
			int v = Integer.parseInt(split[0]);
			int e = Integer.parseInt(split[1]);

			LinkedList<int[]>[] graph = new LinkedList[v + 1];
			for(int i = 1; i <= v; i++){
				graph[i] = new LinkedList();
			}

			for(int i = 0; i < e; i++){
				split = br.readLine().split(" ");
				int src = Integer.parseInt(split[0]);
				int dst = Integer.parseInt(split[1]);
				int cost = Integer.parseInt(split[2]);
				graph[src].add(new int[]{dst, cost});	
			}

			int start = 1;
			int[] answer = solution(v, graph, start);
			int[] expected = Arrays.stream(br.readLine().split(" "))
.mapToInt(Integer::parseInt)
.toArray();
			System.out.println(Arrays.toString(answer));
			assert Arrays.equals(answer, expected);
		}catch(IOException e){
			throw new IllegalArgumentException(e);
		}		
	}

	private static int[] solution(int v, LinkedList<int[]>[] graph, int start){
		// 최단 거리 테이블 초기화
		int[] distance = new int[v + 1];
		Arrays.fill(distance, Integer.MAX_VALUE);
		distance[start] = 0;
		
		// 최단 거리 노드를 찾기위한 큐초기화
		Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));
		queue.offer(new int[]{start, 0});
		
		while(!queue.isEmpty()){
			int[] node = queue.poll();
			int now = node[0];
			int dist = node[1];
		
			if(distance[now] < dist){
				continue;
			}
			
			for(int[] adj : graph[now]){
				int dst = adj[0];
				int cost = adj[1];
				int newCost = distance[now] + cost;
				if(newCost < distance[dst]){
					distance[dst] = newCost;
					queue.offer(new int[]{dst, newCost});
				}
			}
		}
		return Arrays.copyOfRange(distance, 1, distance.length);
	}
}

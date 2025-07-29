package org.example.practice.ch9.step01;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Arrays;
import java.io.IOException;

public class DyxtraExample {
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
			System.out.println(Arrays.toString(answer));
			int[] expected = Arrays.stream(br.readLine().split(" "))
.mapToInt(Integer::parseInt)
.toArray();
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

		// 시작 노드에 대해서 최단 거리 초기화
		for(int[] adj : graph[start]){
			distance[adj[0]] = adj[1];
		}

		boolean[] visited = new boolean[v + 1];
		visited[start] = true;
		
		// 시작 노드를 제외한 v-1개의 노드에 대해서 최단 거리 갱신
		for(int i = 0; i < v - 1; i++){
			// 방문하지 않은 노드들중 최단 거리가 가장 잛은 노드 탐색
			int node = getSmallestNode(distance, visited);

			for(int[] adj : graph[node]){
				int dst = adj[0];
				int cost = adj[1];
				int newCost = distance[node] + cost;
				if(newCost < distance[dst]){
					distance[dst] = newCost;
				}
			}
			visited[node] = true;
		}
		return Arrays.copyOfRange(distance, 1, distance.length);
	}

	private static int getSmallestNode(int[] distance, boolean[] visited){
		int result = -1;
		int minValue = Integer.MAX_VALUE;
		for(int i = 1; i < distance.length; i++){
			if(!visited[i] && distance[i] < minValue){
				minValue = distance[i];
				result = i;
			}
		}
		return result;
	}
}

package org.example.practice.ch9.step06;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Arrays;

public class MessageExample {
	public static void main(String[] args) {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
			String[] split = br.readLine().split(" ");
			int n = Integer.parseInt(split[0]);
			int m = Integer.parseInt(split[1]);
			int c = Integer.parseInt(split[2]);
			LinkedList<int[]>[] graph = new LinkedList[n + 1];			
			for(int i = 1; i <= n; i++){
				graph[i] = new LinkedList();
			}

			for(int i = 0; i < m; i++){
				split = br.readLine().split(" ");
				int src = Integer.parseInt(split[0]);
				int dst = Integer.parseInt(split[1]);
				int cost = Integer.parseInt(split[2]);
				graph[src].add(new int[]{dst, cost});
			}
			int[] answer = solution(n, graph, c);
			int[] expected = Arrays.stream(br.readLine().split(" "))
.mapToInt(Integer::parseInt)
.toArray();
			assert Arrays.equals(answer, expected);
		}catch(IOException e){
			throw new IllegalArgumentException(e);
		}		
	}

	private static int[] solution(int v, LinkedList<int[]>[] graph, int start){
		// 최단 거리테이블 초기화
		int[] distance = new int[v + 1];
		Arrays.fill(distance, Integer.MAX_VALUE);
		distance[start] = 0;
		
		for(int[] adj : graph[start]){
			distance[adj[0]] = adj[1];
		}
		boolean[] visited = new boolean[v + 1];
		visited[start] = true;
		
		for(int i = 0; i < v - 1; i++){
			int now = getSmallestNode(distance, visited);

			for(int[] adj : graph[now]){
				int dst = adj[0];
				int cost = adj[1];
				int newCost = distance[now] + cost;

				if(newCost < distance[dst]){
					distance[dst] = newCost;
				}
			}
			visited[now] = true;
		}

		int visitedCity = 0;
		for(int i = 1; i < visited.length; i++){
			if(visited[i]){
				visitedCity++;
			}
		}

		int maxTime = 0;
		for(int i = 1; i < distance.length; i++){
			if(visited[i] && distance[i] > maxTime){
				maxTime = distance[i];
			}
		}
		return new int[]{visitedCity - 1, maxTime};
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

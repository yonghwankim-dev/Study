package org.example.practice.ch9.step04;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Comparator;

public class City {
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
				int cost = 1;
				graph[src].add(new int[]{dst, cost});
				graph[dst].add(new int[]{src, cost});
			}

			split = br.readLine().split(" ");
			int x = Integer.parseInt(split[0]);
			int k = Integer.parseInt(split[1]);

			int answer = solution(v, graph, x, k);
			int expected = Integer.parseInt(br.readLine());
			assert answer == expected;
		}catch(IOException e){
			throw new IllegalArgumentException(e);
		}
	}

	private static int solution(int v, LinkedList<int[]>[] graph, int x, int k){
		int[] distance1 = getShorestDistance(v, graph, 1);
		int[] distance2 = getShorestDistance(v, graph, k);
		System.out.println(Arrays.toString(distance1));
		System.out.println(Arrays.toString(distance2));
		return distance1[k] + distance2[x];
	}

	private static int[] getShorestDistance(int v, LinkedList<int[]>[] graph, int start){
		// 최단 거리 테이블 초기화
		int[] distance = new int[v + 1];
		Arrays.fill(distance, Integer.MAX_VALUE);
		distance[start] = 0;

		Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o->o[1]));
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
				int newCost = dist + cost;
				if(newCost < distance[dst]){
					distance[dst] = newCost;
					queue.offer(new int[]{dst, newCost});
				}
			}
		}
		return distance;
	}
}

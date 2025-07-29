package org.example.ch5_dfs_bfs;

import java.util.LinkedList;
import java.util.List;

public class DFS {
	public void dfs(LinkedList<Integer>[] graph, int v, boolean[] visited){
		visited[v] = true;
		System.out.print(v + " ");

		for (int adj : graph[v]){
			if (!visited[adj]){
				dfs(graph, adj, visited);
			}
		}
	}

	public static void main(String[] args) {
		DFS dfs = new DFS();
		int n = 8;
		LinkedList<Integer>[] graph = new LinkedList[n + 1];
		for(int i = 1; i <= n; i++){
			graph[i] = new LinkedList<>();
		}
		graph[1].addAll(List.of(2, 3, 8));
		graph[2].addAll(List.of(1, 7));
		graph[3].addAll(List.of(1, 4, 5));
		graph[4].addAll(List.of(3, 5));
		graph[5].addAll(List.of(3, 4));
		graph[6].addAll(List.of(7));
		graph[7].addAll(List.of(2, 6, 8));
		graph[8].addAll(List.of(1, 7));

		boolean[] visited = new boolean[n + 1];
		dfs.dfs(graph, 1, visited);
	}
}

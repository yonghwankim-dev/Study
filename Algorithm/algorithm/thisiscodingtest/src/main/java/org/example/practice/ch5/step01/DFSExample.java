package org.example.practice.ch5.step01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.sun.jdi.connect.spi.TransportService;

public class DFSExample {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());

		LinkedList<Integer>[] graph = new LinkedList[n + 1];
		for (int i = 1; i <= n; i++){
			graph[i] = new LinkedList<>();
		}
		for (int i = 1; i <= n; i++){
			int[] edges = Arrays.stream(br.readLine().split(" "))
				.mapToInt(Integer::parseInt)
				.toArray();
			int src = edges[0];
			for (int j = 1; j < edges.length; j++){
				graph[src].add(edges[j]);
			}
			graph[src].sort(Integer::compare);
		}

		int[] answer = solution(n, graph);
		System.out.println(Arrays.toString(answer));
		int[] expected = {1, 2, 7, 6, 8, 3, 4, 5};
		assert Arrays.equals(answer, expected);
	}

	private static int[] solution(int n, LinkedList<Integer>[] graph){
		boolean[] visited = new boolean[n + 1];
		int start = 1;
		List<Integer> result = dfs(start, graph, visited);
		return result.stream()
			.mapToInt(Integer::intValue)
			.toArray();
	}

	private static List<Integer> dfs(int start, LinkedList<Integer>[] graph, boolean[] visited){
		if (visited[start]){
			return new ArrayList<>();
		}
		visited[start] = true;

		List<Integer> result = new ArrayList<>();
		result.add(start);
		for (int adj : graph[start]){
			if (!visited[adj]){
				result.addAll(dfs(adj, graph, visited));
			}
		}
		return result;
	}
}

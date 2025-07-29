package org.example.practice.ch5.step02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFSExample {
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
		int[] expected = {1, 2, 3, 8, 7, 4, 5, 6};
		System.out.println(Arrays.toString(answer));
		assert Arrays.equals(answer, expected);
	}

	private static int[] solution(int n, LinkedList<Integer>[] graph){
		boolean[] visited = new boolean[n + 1];
		Queue<Integer> queue = new LinkedList<>();
		int start = 1;
		queue.offer(start);
		visited[start] = true;

		List<Integer> result = new ArrayList<>();

		while (!queue.isEmpty()){
			Integer cur = queue.poll();
			result.add(cur);

			for (int adj : graph[cur]){
				if (!visited[adj]){
					queue.offer(adj);
					visited[adj] = true;
				}
			}
		}
		return result.stream()
			.mapToInt(Integer::intValue)
			.toArray();
	}
}

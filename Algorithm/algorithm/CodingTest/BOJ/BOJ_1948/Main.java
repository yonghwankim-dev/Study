package BOJ.BOJ_1948;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		int m = Integer.parseInt(br.readLine());

		LinkedList<int[]>[] graph = new LinkedList[n + 1];
		LinkedList<int[]>[] reverseGraph = new LinkedList[n + 1];
		for (int i = 1; i <= n; i++){
			graph[i] = new LinkedList<>();
			reverseGraph[i] = new LinkedList<>();
		}
		int[] indegree = new int[n + 1];

		for (int i = 0; i < m; i++){
			StringTokenizer st = new StringTokenizer(br.readLine());
			int src = Integer.parseInt(st.nextToken());
			int dst = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			graph[src].add(new int[]{dst, cost});
			reverseGraph[dst].add(new int[]{src, cost});
			indegree[dst]++;
		}
		StringTokenizer st = new StringTokenizer(br.readLine());
		int start = Integer.parseInt(st.nextToken());
		int end = Integer.parseInt(st.nextToken());

		int[] answer = solution(graph, reverseGraph, indegree, start, end, n);
		Arrays.stream(answer)
			.forEach(System.out::println);
	}

	private static int[] solution(LinkedList<int[]>[] graph, LinkedList<int[]>[] reverseGraph, int[] indegree, int start, int end, int n) {
		int[] times = topologicalSort(graph, indegree, start, n);
		// 도착 도시에서 역방향으로 위상 정령를 수행한다
		int answerCount = 0;
		boolean[] visited = new boolean[n + 1];
		Queue<Integer> queue = new LinkedList<>();
		queue.offer(end);
		visited[end] = true;
		while (!queue.isEmpty()){
			int now = queue.poll();
			for (int[] adj : reverseGraph[now]){
				int dst = adj[0];
				int time = adj[1];
				if (times[now] == times[dst] + time){
					answerCount++;
					if (!visited[dst]){
						visited[dst] = true;
						queue.offer(dst);
					}
				}
			}
		}
		return new int[]{times[end], answerCount};
	}

	private static int[] topologicalSort(LinkedList<int[]>[] graph, int[] indegree, int start, int n) {
		int[] times = new int[n + 1];

		// 위상 정렬을 이용해서 start부터 end까지의 최장 거리 값 계산
		Queue<Integer> queue = new LinkedList<>();
		queue.offer(start);

		while (!queue.isEmpty()){
			int now = queue.poll();

			for (int[] adj : graph[now]){
				int node = adj[0];
				int time = adj[1];

				indegree[node]--;
				times[node] = Math.max(times[node], times[now] + time);

				if (indegree[node] == 0){
					queue.offer(node);
				}
			}
		}
		return times;
	}
}

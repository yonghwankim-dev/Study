package BOJ.BOJ_1504;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	private static final int INF = 200_000 * 1000;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		LinkedList<int[]>[] graph = new LinkedList[n + 1];
		for (int i = 1; i <= n; i++){
			graph[i] = new LinkedList<>();
		}

		for (int i = 0; i < m; i++){
			st = new StringTokenizer(br.readLine());
			int src = Integer.parseInt(st.nextToken());
			int dst = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			graph[src].add(new int[]{dst, cost});
			graph[dst].add(new int[]{src, cost});
		}

		st = new StringTokenizer(br.readLine());
		int v1 = Integer.parseInt(st.nextToken());
		int v2 = Integer.parseInt(st.nextToken());
		int answer = solution(graph, n, v1, v2);
		System.out.println(answer);
	}

	private static int solution(LinkedList<int[]>[] graph, int n, int v1, int v2) {
		int[] origin_dist = dijkstra(graph, 1, n);
		int[] v1_dist = dijkstra(graph, v1, n);
		int[] v2_dist = dijkstra(graph, v2, n);
		// System.out.println("origin_dist : " + Arrays.toString(origin_dist));
		// System.out.println("v1_dist : " + Arrays.toString(v1_dist));
		// System.out.println("v2_dist : " + Arrays.toString(v2_dist));

		int v1_path = origin_dist[v1] + v1_dist[v2] + v2_dist[n];
		int v2_path = origin_dist[v2] + v2_dist[v1] + v1_dist[n];
		// System.out.println("v1_path : " + v1_path);
		// System.out.println("v2_path : " + v2_path);
		int answer = Math.min(v1_path, v2_path);
		if (answer >= INF){
			return -1;
		}
		return answer;
	}

	private static int[] dijkstra(LinkedList<int[]>[] graph, int start, int n){
		int[] distance = new int[n + 1];
		for (int i = 1; i <= n; i++){
			distance[i] = INF;
		}
		distance[start] = 0;
		Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o1->o1[1]));
		queue.offer(new int[]{start, 0});

		while (!queue.isEmpty()){
			int[] node = queue.poll();
			int now = node[0];
			int cost = node[1];

			if (distance[now] < cost){
				continue;
			}

			for (int[] adj : graph[now]){
				int dist = adj[1] + cost;
				if (dist < distance[adj[0]]){
					distance[adj[0]] = dist;
					queue.offer(new int[]{adj[0], dist});
				}
			}
		}
		return distance;
	}
}

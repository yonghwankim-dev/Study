package BOJ.BOJ_1753;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	private static final int INF = 200_000;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int start = Integer.parseInt(br.readLine());

		LinkedList<int[]>[] graph = new LinkedList[n + 1];
		for (int i = 1; i <= n; i++){
			graph[i] = new LinkedList<>();
		}

		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int src = Integer.parseInt(st.nextToken());
			int dst = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			graph[src].add(new int[] { dst, cost });
		}
		int[] answer = solution(graph, start,n);
		for (int i = 1; i <= n; i++){
			if (answer[i] == INF)
				System.out.println("INF");
			else
				System.out.println(answer[i]);
		}

	}

	private static int[] solution(LinkedList<int[]>[] graph, int start, int n) {
		int[] answer = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			answer[i] = INF;
		}
		answer[start] = 0;

		Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o1->o1[1]));
		queue.offer(new int[]{start, 0});

		while (!queue.isEmpty()) {
			int[] node = queue.poll();
			int now = node[0];
			int cost = node[1];

			if (answer[now] < cost) {
				continue;
			}

			for (int[] adj : graph[now]) {
				int dist = adj[1] + cost;
				if(dist < answer[adj[0]]) {
					answer[adj[0]] = dist;
					queue.offer(new int[]{adj[0], dist});
				}
			}
		}
		return answer;
	}
}

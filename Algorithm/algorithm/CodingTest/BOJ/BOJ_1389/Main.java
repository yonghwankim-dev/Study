package BOJ.BOJ_1389;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	private static final int INF = 100;
	public static int solution(LinkedList<int[]>[] graph, int n){
		int answer = 0;
		int minValue = Integer.MAX_VALUE;
		for (int i = 1; i <= n; i++){
			int[] distance = dijkstra(graph, i, n);
			int sumValue = sum(distance);
			if (sumValue < minValue){
				answer = i;
				minValue = sumValue;
			}
		}
		return answer;
	}

	public static int[] dijkstra(LinkedList<int[]>[] graph, int start, int n){
		int[] distance = new int[n + 1];
		Arrays.fill(distance, INF);
		distance[start] = 0;

		Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));

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

	public static int sum(int[] arr){
		int sum = 0;
		for (int i = 1; i < arr.length; i++){
			sum += arr[i];
		}
		return sum;
	}

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

			graph[src].add(new int[]{dst, 1});
			graph[dst].add(new int[]{src, 1});
		}
		System.out.println(solution(graph, n));
	}
}

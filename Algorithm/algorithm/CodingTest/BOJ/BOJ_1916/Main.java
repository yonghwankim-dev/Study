package BOJ.BOJ_1916;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Main {
	private static int solution(int n, int m, int start, int end, LinkedList<int[]>[] graph){
		int[] distance = new int[n + 1];
		for (int i = 1; i <= n; i++){
			distance[i] = Integer.MAX_VALUE;
		}

		Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));
		queue.offer(new int[]{start, 0});
		distance[start] = 0;

		while (!queue.isEmpty()){
			int[] now = queue.poll();
			int nowNode = now[0];
			int fee = now[1];

			if (distance[nowNode] < fee){
				continue;
			}

			for (int[] adjCity : graph[nowNode]) {
				int cost = distance[nowNode] + adjCity[1];
				if (cost < distance[adjCity[0]]){
					distance[adjCity[0]] = cost;
					queue.offer(new int[]{adjCity[0], cost});
				}
			}
		}

		return distance[end];
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		int m = Integer.parseInt(br.readLine());
		LinkedList<int[]>[] graph = new LinkedList[n + 1];

		for (int i = 1; i <= n; i++){
			graph[i] = new LinkedList<>();
		}

		for (int i = 0; i < m; i++){
			int[] city = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			graph[city[0]].add(new int[]{city[1], city[2]});
		}

		int[] info = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		int start = info[0];
		int end = info[1];
		int answer = solution(n, m, start, end, graph);
		System.out.println(answer);
	}
}

package BOJ.BOJ_1005;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int tc = Integer.parseInt(br.readLine());

		for (int i = 0; i < tc; i++){
			StringTokenizer st = new StringTokenizer(br.readLine());
			int n = Integer.parseInt(st.nextToken());
			int k = Integer.parseInt(st.nextToken());
			int[] costs = new int[n + 1];
			LinkedList<Integer>[] graph = new LinkedList[n + 1];
			int[] indegree = new int[n + 1];

			for (int j = 1; j <= n; j++){
				graph[j] = new LinkedList<>();
			}

			st = new StringTokenizer(br.readLine());
			// 건설시간
			for (int j = 1; j <= n; j++){
				int d = Integer.parseInt(st.nextToken());
				costs[j] = d;
			}

			for (int j = 1; j <= k; j++){
				st = new StringTokenizer(br.readLine());
				int src = Integer.parseInt(st.nextToken());
				int dst = Integer.parseInt(st.nextToken());
				graph[src].add(dst);
				indegree[dst]++;
			}

			int w = Integer.parseInt(br.readLine());

			int answer = solution(graph, indegree, costs, w, n);
			System.out.println(answer);
		}
	}

	private static int solution(LinkedList<Integer>[] graph, int[] indegree, int[] costs, int w, int n) {
		int[] answer = new int[n + 1];
		for (int i = 1; i <= n; i++){
			answer[i] = costs[i];
		}
		Queue<Integer> queue = new LinkedList<>();
		for (int i = 1; i <= n; i++){
			if (indegree[i] == 0){
				queue.offer(i);
			}
		}

		while (!queue.isEmpty()){
			int node = queue.poll();

			for (int dst : graph[node]){
				indegree[dst]--;
				answer[dst] = Math.max(answer[dst], answer[node] + costs[dst]);
				if (indegree[dst] == 0){
					queue.offer(dst);
				}
			}
		}
		return answer[w];
	}
}

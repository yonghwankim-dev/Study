package BOJ.BOJ_1516;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		LinkedList<Integer>[] graph = new LinkedList[n + 1];
		for (int i = 1; i <= n; i++){
			graph[i] = new LinkedList<>();
		}
		int[] indegree = new int[n + 1];
		int[] times = new int[n + 1];
		for (int i = 1; i <= n; i++){
			StringTokenizer st = new StringTokenizer(br.readLine());
			int time = Integer.parseInt(st.nextToken());
			times[i] = time;
			while (st.hasMoreTokens()){
				int node = Integer.parseInt(st.nextToken());
				if (node == -1){
					break;
				}
				graph[node].add(i);
				indegree[i]++;
			}
		}
		int[] answer = solution(graph, indegree, times, n);
		for (int i = 1; i <= n; i++){
			System.out.println(answer[i]);
		}
	}

	private static int[] solution(LinkedList<Integer>[] graph, int[] indegree, int[] times, int n) {
		int[] answer = new int[n + 1];
		for (int i = 1; i <= n; i++){
			answer[i] = times[i];
		}
		Queue<Integer> queue = new LinkedList<>();
		for (int i = 1; i <= n; i++){
			if (indegree[i] == 0){
				queue.offer(i);
			}
		}

		while (!queue.isEmpty()){
			int now = queue.poll();

			for (int dst : graph[now]){
				indegree[dst]--;
				answer[dst] = Math.max(answer[dst], answer[now] + times[dst]);
				if (indegree[dst] == 0){
					queue.offer(dst);
				}
			}
		}
		return answer;
	}
}

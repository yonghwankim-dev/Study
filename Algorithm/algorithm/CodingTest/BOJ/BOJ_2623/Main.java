package BOJ.BOJ_2623;

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
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		LinkedList<Integer>[] graph = new LinkedList[n + 1];
		int[] inDegree = new int[n + 1];
		for (int i = 1; i <= n; i++){
			graph[i] = new LinkedList<>();
		}
		for (int i = 0; i < m; i++){
			st = new StringTokenizer(br.readLine());
			int p = Integer.parseInt(st.nextToken());
			int[] nodes = new int[p];
			for (int j = 0; j < p; j++){
				nodes[j] = Integer.parseInt(st.nextToken());
			}
			for (int j = 0; j < p - 1; j++){
				graph[nodes[j]].add(nodes[j + 1]);
				inDegree[nodes[j + 1]]++;
			}
		}
		int[] answer = solution(graph, inDegree, n);
		for (int i = 0; i < n; i++){
			if (answer[i] == 0){
				System.out.println(0);
				return;
			}
		}
		Arrays.stream(answer)
			.forEach(System.out::println);
	}

	private static int[] solution(LinkedList<Integer>[] graph, int[] inDegree, int n) {
		int[] answer = new int[n];
		int idx = 0;
		Queue<Integer> queue = new LinkedList<>();
		for (int i = 1; i <= n; i++){
			if (inDegree[i] == 0){
				queue.offer(i);
			}
		}

		while (!queue.isEmpty()){
			int node = queue.poll();
			for (int dst : graph[node]){
				inDegree[dst]--;
				if (inDegree[dst] == 0){
					queue.offer(dst);
				}
			}
			answer[idx++] = node;
		}
		return answer;
	}
}

package BOJ.BOJ_2252;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 * 위상 정렬
 * 1. 진입 차수가 0인 노드를 큐에 넣는다.
 * 2. 큐에서 노드를 꺼내고 노드와 인전한 간선을 제거한다
 * 3. 간선을 제거한 노드의 진입차수를 1 감소시킨다.
 * 4. 간선을 제거한 노드의 진입차수가 0이면 큐에 넣는다.
 * 5. 큐가 빌때까지 2~4번 과정을 반복한다
 */
public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());

		LinkedList<Integer>[] graph = new LinkedList[n + 1];
		for (int i = 1; i <= n; i++){
			graph[i] = new LinkedList<>();
		}
		int[] indegree = new int[n + 1];

		for (int i = 0; i < m; i++){
			st = new StringTokenizer(br.readLine());
			int src = Integer.parseInt(st.nextToken());
			int dst = Integer.parseInt(st.nextToken());
			graph[src].add(dst);
			indegree[dst]++;
		}

		int[] answer = solution(graph, indegree, n);
		String output = Arrays.stream(answer)
			.mapToObj(String::valueOf)
			.collect(Collectors.joining(" "));
		System.out.println(output);
	}

	private static int[] solution(LinkedList<Integer>[] graph, int[] indegree, int n) {
		int[] answer = new int[n];
		int idx = 0;
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
				if (indegree[dst] == 0){
					queue.offer(dst);
				}
			}
			answer[idx++] = node;
		}
		return answer;
	}
}

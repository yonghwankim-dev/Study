package BOJ.BOJ_1766;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 * 위상 정렬
 * 진입 차수가 0개인것이 여러개면 노드의 숫자가 작은것부터 처리합니다.
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
		Queue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o));
		for (int i = 1; i <= n; i++){
			if (indegree[i] == 0){
				queue.offer(i);
			}
		}

		while (!queue.isEmpty()){
			int now = queue.poll();
			answer[idx++] = now;
			for (int dst : graph[now]){
				indegree[dst]--;
				if (indegree[dst] == 0){
					queue.offer(dst);
				}
			}
		}
		return answer;
	}
}

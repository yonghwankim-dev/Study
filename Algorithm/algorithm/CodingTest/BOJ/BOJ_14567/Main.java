package BOJ.BOJ_14567;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

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

		int[] degree = new int[n + 1];
		for (int i = 0; i < m; i++){
			st = new StringTokenizer(br.readLine());
			int src = Integer.parseInt(st.nextToken());
			int dst = Integer.parseInt(st.nextToken());
			graph[src].add(dst);
			degree[dst]++;
		}

		int[] answer = solution(graph, degree, n);
		String output = Arrays.stream(answer)
			.skip(1)
			.mapToObj(String::valueOf)
			.collect(Collectors.joining(" "));
		System.out.println(output);
	}

	private static int[] solution(LinkedList<Integer>[] graph, int[] degree, int n) {
		int[] answer = new int[n + 1];
		Queue<Integer> queue = new LinkedList<>();
		for (int i = 1; i <= n; i++){
			if (degree[i] == 0){
				queue.offer(i);
			}
		}

		while (!queue.isEmpty()){
			int now = queue.poll();
			answer[now]++;
			for (int dst : graph[now]){
				degree[dst]--;
				answer[dst]++;
				if (degree[dst] == 0){
					queue.offer(dst);
				}
			}
		}
		return answer;
	}
}

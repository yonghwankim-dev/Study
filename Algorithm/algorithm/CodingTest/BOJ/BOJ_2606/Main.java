package BOJ.BOJ_2606;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

class Main {

	public static int solution(LinkedList<Integer>[] graph, boolean[] visited){
		Queue<Integer> queue = new LinkedList<>();
		int answer = 0;
		queue.offer(1);
		visited[1] = true;

		while(!queue.isEmpty()){
			Integer v = queue.poll();

			for (int adj : graph[v]){
				if (!visited[adj]){
					queue.add(adj);
					visited[adj] = true;
					answer++;
				}
			}
		}
		return answer;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		int m = Integer.parseInt(br.readLine());
		LinkedList<Integer>[] graph = new LinkedList[n + 1];
		boolean[] visited = new boolean[n + 1];
		for (int i = 1; i <= n; i++){
			graph[i] = new LinkedList<>();
		}
		for(int i = 0; i < m; i++){
			String[] split = br.readLine().split(" ");
			int start = Integer.parseInt(split[0]);
			int end = Integer.parseInt(split[1]);
			graph[start].add(end);
			graph[end].add(start);
		}
		System.out.println(solution(graph, visited));
	}
}

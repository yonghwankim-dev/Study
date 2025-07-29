package BOJ.BOJ_13549;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	private static final int INF = 100_000;
	public static int solution(int n, int k){
		int[] answer = new int[INF + 1];
		// INF로 초기화
		Arrays.fill(answer, INF);
		// 시작지점 초기화
		answer[n] = 0;

		Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));
		queue.offer(new int[]{n, 0});

		while (!queue.isEmpty()){
			int[] node = queue.poll();
			int now = node[0];
			int cost = node[1];

			if (answer[now] < cost){
				continue;
			}

			for (int[] adj : getAdj(now)){
				int dist = cost + adj[1];
				if (adj[0] > INF || adj[0] < 0){
					continue;
				}
				if (dist < answer[adj[0]]){
					answer[adj[0]] = dist;
					queue.offer(new int[]{adj[0], dist});
				}
			}
		}
		return answer[k];
	}

	public static int[][] getAdj(int x){
		return new int[][]{{x-1, 1}, {x+1, 1}, {2*x, 0}};
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		int answer = 0;
		if (n >= k){
			answer = n - k;
		}else{
			answer = solution(n, k);
		}
		System.out.println(answer);
	}
}

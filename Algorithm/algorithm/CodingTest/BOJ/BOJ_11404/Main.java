package BOJ.BOJ_11404;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	private static final int INF = 10_000_000;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		int m = Integer.parseInt(br.readLine());

		int[][] graph = new int[m][3];

		for (int i = 0; i < m; i++){
			StringTokenizer st = new StringTokenizer(br.readLine());
			int src = Integer.parseInt(st.nextToken());
			int dst = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			graph[i] = new int[]{src, dst, cost};
		}
		int[][] answer = solution(graph, n);
		for (int i = 1; i <= n; i++){
			for (int j = 1; j <= n; j++){
				if (answer[i][j] == INF){
					System.out.print(0 + " ");
				}else{
					System.out.print(answer[i][j] + " ");
				}

			}
			if (i != n){
				System.out.println();
			}
		}
	}

	public static int[][] solution(int[][] graph, int n){
		int[][] answer = new int[n + 1][n + 1];

		// answer를 INF으로 초기화 i==j는 0으로 초기화
		for (int i = 1; i <= n; i++){
			for (int j = 1; j <= n; j++){
				if (i==j){
					answer[i][j] = 0;
				}else{
					answer[i][j] = INF;
				}
			}
		}

		// graph 정보를 answer에 초기화
		for (int[] input : graph){
			int src = input[0];
			int dst = input[1];
			int cost = input[2];
			answer[src][dst] = Math.min(answer[src][dst], cost);
		}

		for (int i = 1; i <= n; i++){
			for (int a = 1; a <= n; a++){
				for (int b = 1; b <= n; b++){
					answer[a][b] = Math.min(answer[a][b], answer[a][i] + answer[i][b]);
				}
			}
		}
		return answer;
	}
}

package BOJ.BOJ_1261;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int m = Integer.parseInt(st.nextToken()); // 가로
		int n = Integer.parseInt(st.nextToken()); // 세로
		int[][] map = new int[n][m];

		for (int i = 0; i < n; i++){
			map[i] = Arrays.stream(br.readLine().split(""))
				.mapToInt(Integer::parseInt)
				.toArray();
		}


		int answer = solution(map, n, m);
		System.out.println(answer);
	}

	private static int solution(int[][] map, int n, int m) {
		int answer = 0;
		int[] dy = {-1, 1, 0, 0};
		int[] dx = {0, 0, -1, 1};
		boolean[][] visited = new boolean[n][m];

		Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o1->o1[2]));
		queue.offer(new int[]{0, 0, 0});
		visited[0][0] = true;
		while (!queue.isEmpty()){
			int[] node = queue.poll();
			int y = node[0];
			int x = node[1];
			int wall = node[2];

			if (y == n -1 && x == m - 1){
				answer = wall;
			}

			for (int i = 0; i < 4; i++){
				int ny = dy[i] + y;
				int nx = dx[i] + x;

				if (ny < 0 || ny >= n || nx < 0 || nx >= m || visited[ny][nx]){
					continue;
				}

				if (map[ny][nx] == 1){
					queue.offer(new int[]{ny, nx, wall + 1});
				}else{
					queue.offer(new int[]{ny, nx, wall});
				}
				visited[ny][nx] = true;
			}
		}
		return answer;
	}
}

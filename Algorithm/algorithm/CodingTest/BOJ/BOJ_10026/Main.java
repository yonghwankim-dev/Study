package BOJ.BOJ_10026;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		String[][] map = new String[n][n];
		for (int i = 0; i < n; i++){
			map[i] = br.readLine().split("");
		}
		int[] answer = solution(map, n);
		System.out.println(answer[0] + " " + answer[1]);
	}

	private static int[] solution(String[][] map, int n) {
		boolean[][] visited = new boolean[n][n];
		// 정상인 경우
		int cnt1 = 0;
		for (int i = 0; i < n; i++){
			for (int j = 0; j < n; j++){
				if (!visited[i][j]){
					bfs(map, n, i, j, visited);
					cnt1++;
				}
			}
		}

		// 적록색약인 경우 G->R로 변경
		for (int i = 0; i < n; i++){
			for (int j = 0; j < n; j++){
				if (map[i][j].equals("G")){
					map[i][j] = "R";
				}
			}
		}
		visited = new boolean[n][n];
		int cnt2 = 0;
		for (int i = 0; i < n; i++){
			for (int j = 0; j < n; j++){
				if (!visited[i][j]){
					bfs(map, n, i, j, visited);
					cnt2++;
				}
			}
		}

		return new int[]{cnt1, cnt2};
	}

	private static void bfs(String[][] map, int n, int y, int x, boolean[][] visited) {
		int[] dy = {-1, 1, 0, 0};
		int[] dx = {0, 0, -1, 1};

		Queue<int[]> queue = new LinkedList<>();
		queue.offer(new int[]{y,x});
		visited[y][x] = true;
		String color = map[y][x];

		while (!queue.isEmpty()){
			int[] node = queue.poll();
			int cur_x = node[0];
			int cur_y = node[1];

			for (int i = 0; i < 4; i++){
				int ny = dy[i] + cur_x;
				int nx = dx[i] + cur_y;

				if (ny < 0 || ny >= n || nx < 0 || nx >= n || visited[ny][nx] || !map[ny][nx].equals(color)){
					continue;
				}

				queue.offer(new int[]{ny, nx});
				visited[ny][nx] = true;
			}
		}
	}
}

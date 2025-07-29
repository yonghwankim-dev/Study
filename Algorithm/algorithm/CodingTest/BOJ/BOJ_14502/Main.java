package BOJ.BOJ_14502;

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

		int[][] map = new int[n][m];

		for (int i = 0; i < n; i++){
			map[i] = Arrays.stream(br.readLine().split(" "))
				.mapToInt(Integer::parseInt)
				.toArray();
		}
		int answer = solution(map, n, m);
		System.out.println(answer);
	}

	private static int solution(int[][] map, int n, int m) {
		return makeWall(0, map, n, m);
	}

	private static int makeWall(int count, int[][] map, int n, int m) {
		if (count == 3){
			return bfs(map);
		}
		int result = 0;
		for (int i = 0; i < map.length; i++){
			for (int j = 0; j < map[i].length; j++){
				if (map[i][j] == 0){
					map[i][j] = 1;
					result = Math.max(result, makeWall(count + 1, map, n, m));
					map[i][j] = 0;
				}
			}
		}
		return result;
	}

	private static int bfs(int[][] map) {
		int[] dy = {-1, 1, 0, 0};
		int[] dx = {0, 0, -1, 1};
		int n = map.length;
		int m = map[0].length;
		boolean[][] visited = new boolean[n][m];

		int[][] copyMap = new int[n][m];
		for (int i = 0; i < n; i++){
			System.arraycopy(map[i], 0, copyMap[i], 0, map[i].length);
		}

		Queue<int[]> queue = new LinkedList<>();
		for (int i = 0; i < n; i++){
			for (int j = 0; j < m; j++){
				if (copyMap[i][j] == 2){
					queue.offer(new int[]{i,j});
					visited[i][j] = true;
				}
			}
		}

		while (!queue.isEmpty()){
			int[] node = queue.poll();
			int y = node[0];
			int x = node[1];

			for (int i = 0; i < 4; i++){
				int ny = dy[i] + y;
				int nx = dx[i] + x;

				if (ny < 0 || ny >= n || nx < 0 || nx >= m || visited[ny][nx] || copyMap[ny][nx] != 0){
					continue;
				}

				queue.offer(new int[]{ny, nx});
				visited[ny][nx] = true;
				copyMap[ny][nx] = 2;
			}
		}

		return countSafeZone(copyMap, n, m);
	}

	private static int countSafeZone(int[][] map, int n, int m) {
		int result = 0;
		for (int i = 0; i < n; i++){
			for (int j = 0; j < m; j++){
				if (map[i][j] == 0){
					result++;
				}
			}
		}
		return result;
	}
}

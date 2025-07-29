package BOJ.BOJ_4485;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int tc = 1;
		while (true){
			int n = Integer.parseInt(br.readLine());
			if (n == 0){
				break;
			}
			int[][] map = new int[n][n];
			for (int i = 0; i < n; i++){
				map[i] = Arrays.stream(br.readLine().split(" "))
					.mapToInt(Integer::parseInt)
					.toArray();
			}

			int answer = solution(map, n);
			System.out.println(String.format("Problem %d: %d", tc, answer));
			tc++;
		}
	}

	private static int solution(int[][] map, int n) {
		int answer = 0;
		int[] dy = {-1, 1, 0, 0};
		int[] dx = {0, 0, -1, 1};

		Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o1->o1[2]));
		boolean[][] visited = new boolean[n][n];

		queue.offer(new int[]{0, 0, map[0][0]});

		while (!queue.isEmpty()){
			int[] node = queue.poll();
			int y = node[0];
			int x = node[1];
			int cost = node[2];

			if (y == n -1 && x == n - 1){
				answer = cost;
				break;
			}

			for (int i = 0; i < 4; i++){
				int ny = dy[i] + y;
				int nx = dx[i] + x;

				if (ny < 0 || ny >= n || nx < 0 || nx >= n || visited[ny][nx]){
					continue;
				}

				queue.offer(new int[]{ny, nx, map[ny][nx] + cost});
				visited[ny][nx] = true;
			}
		}
		return answer;
	}
}

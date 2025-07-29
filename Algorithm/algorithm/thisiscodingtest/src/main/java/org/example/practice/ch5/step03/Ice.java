package org.example.practice.ch5.step03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Ice {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String[] split = br.readLine().split(" ");
		int n = Integer.parseInt(split[0]);
		int m = Integer.parseInt(split[1]);

		int[][] map = new int[n][m];
		for (int i = 0; i < n; i++){
			map[i] = Arrays.stream(br.readLine().split(""))
				.mapToInt(Integer::parseInt)
				.toArray();
		}

		int answer = solution(n, m, map);
		System.out.println(answer);
		int expected = Integer.parseInt(br.readLine());
		assert answer == expected;
	}

	private static int solution(int n, int m, int[][] map){
		int result = 0;
		boolean[][] visited = new boolean[n][m];

		for (int i = 0; i < n; i++){
			for (int j = 0; j < m; j++){
				int[] start = new int[]{i, j};
				if (map[i][j] == 0 && !visited[i][j]){
					dfs(start, map, visited);
					result++;
				}
			}
		}
		return result;
	}

	private static void dfs(int[] start, int[][] map, boolean[][] visited){
		int y = start[0];
		int x = start[1];
		if (visited[y][x] || map[y][x] == 1){
			return;
		}
		visited[y][x] = true;

		int[] dy = {-1, 1, 0, 0};
		int[] dx = {0, 0, -1, 1};
		int n = map.length;
		int m = map[0].length;
		int result = 0;
		for (int i = 0; i < 4; i++){
			int ny = dy[i] + y;
			int nx = dx[i] + x;

			if (ny < 0 || ny >= n || nx < 0 || nx >= m || visited[ny][nx] || map[ny][nx] == 1){
				continue;
			}
			dfs(new int[]{ny, nx}, map, visited);
		}
	}
}

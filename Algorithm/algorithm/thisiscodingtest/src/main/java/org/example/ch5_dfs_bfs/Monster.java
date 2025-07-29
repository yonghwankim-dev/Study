package org.example.ch5_dfs_bfs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Monster {
	public int solution(int n, int m, int[][] map){
		boolean[][] visited = new boolean[n][m];
		int answer = bfs(0, 0, map, visited);
		System.out.println(Arrays.deepToString(map));
		return answer;
	}

	private int bfs(int y, int x, int[][] map, boolean[][] visited) {
		int n = map.length;
		int m = map[0].length;
		int[] dy = {-1, 1, 0, 0};
		int[] dx = {0, 0, -1, 1};

		Queue<int[]> queue = new LinkedList<>();
		queue.offer(new int[]{y, x});
		visited[y][x] = true;


		while (!queue.isEmpty()){
			int[] pos = queue.poll();

			for (int i = 0; i < 4; i++){
				int ny = dy[i] + pos[0];
				int nx = dx[i] + pos[1];

				if (ny < 0 || ny >= n || nx < 0 || nx >= m){
					continue;
				}
				if (map[ny][nx] == 0 || visited[ny][nx]){
					continue;
				}
				queue.offer(new int[]{ny, nx});
				map[ny][nx] = map[pos[0]][pos[1]] + 1;
				visited[ny][nx] = true;
			}
		}
		return map[n - 1][m - 1];
	}
}

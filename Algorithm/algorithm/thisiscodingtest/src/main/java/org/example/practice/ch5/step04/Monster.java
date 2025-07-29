package org.example.practice.ch5.step04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Monster {
	public static void main(String[] args) {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
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
			assert answer == 10;
		}catch (IOException e){
			throw new IllegalArgumentException(e);
		}
	}

	private static int solution(int n, int m, int[][] map){
		boolean[][] visited = new boolean[n][m];
		int[] dy = {-1, 1, 0, 0};
		int[] dx = {0, 0, -1, 1};

		Queue<int[]> queue = new LinkedList<>();
		queue.offer(new int[]{0, 0});

		while (!queue.isEmpty()){
			int[] node = queue.poll();
			int y = node[0];
			int x = node[1];
			visited[y][x] = true;

			for (int i = 0; i < 4; i++){
				int ny = dy[i] + y;
				int nx = dx[i] + x;

				if (ny < 0 || ny >= n || nx < 0 || nx >= m || visited[ny][nx] || map[ny][nx] == 0){
					continue;
				}
				map[ny][nx] += map[y][x];
				visited[ny][nx] = true;
				queue.offer(new int[]{ny, nx});
			}
		}
		return map[n - 1][m - 1];
	}
}

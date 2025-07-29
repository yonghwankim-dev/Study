package org.example.ch4_imple;

// 게임 개발
public class Solution4 {
	public int solution(int n, int m, int[] position, int direction, int[][] map){
		int answer = 0;
		// 북서남동
		int[] dy = {-1, 0, 1, 0};
		int[] dx = {0, -1, 0, 1};
		int[][] leftDirection = {{3, 2, 1}, {0, 3, 2}, {1, 0, 3}, {2, 1, 0}};
		boolean[][] visited = new boolean[n][m];

		int y = position[0];
		int x = position[1];

		visited[y][x] = true;
		boolean moved = false;
		while(true){
			for (int dir : leftDirection[direction]) {
				int ny = dy[dir] + y;
				int nx = dx[dir] + x;

				if (map[ny][nx] == 0 && !visited[ny][nx]){
					y = ny;
					x = nx;
					direction = dir;
					visited[ny][nx] = true;
					moved = true;
					answer++;
					break;
				}
			}
			if (moved){
				moved = false;
				continue;
			}
			// 네방향 모두 가본칸이거나 바다로 되어 있는 경우
			int backY = (dy[direction] * -1) + y;
			int backX = (dx[direction] * -1) + x;
			if (map[backY][backX] == 0){
				y = backY;
				x = backX;
				answer++;
				continue;
			}
			break;
		}
		return answer;
	}
}

package org.example.ch5_dfs_bfs;


public class Ice {
	public int solution(int[][] map){
		int n = map.length;
		int m = map[0].length;
		boolean[][] visited = new boolean[n][m];
		int answer = 0;

		for (int i = 0; i < n; i++){
			for (int j = 0; j < m; j++){
				if (!visited[i][j] && map[i][j] == 0){
					dfs(i, j, map, visited);
					answer++;
				}
			}
		}
		return answer;
	}

	private void dfs(int y, int x, int[][] map, boolean[][] visited) {
		visited[y][x] = true;
		// 세로, 가로
		int n = map.length;
		int m = map[0].length;

		// 상하좌우
		int[] dy = {-1, 1, 0, 0};
		int[] dx = {0, 0, -1, 1};
		// (y,x)에서 인접한 위치들을 조회한다, (y,x)에서 상하좌우 탐색한다
		for(int i = 0; i < 4; i++){
			int ny = dy[i] + y;
			int nx = dx[i] + x;

			if (ny < 0 || ny >= n || nx < 0 || nx >= m){
				continue;
			}
			// 만약 인접한 위치가 방문하지 않았으면 dfs 재귀 호출한다
			if (!visited[ny][nx] && map[ny][nx] == 0){
				dfs(ny, nx, map, visited);
			}
		}
	}
}

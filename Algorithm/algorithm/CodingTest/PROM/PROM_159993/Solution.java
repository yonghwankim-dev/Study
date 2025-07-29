package PROM.PROM_159993;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Solution {
	public int solution(String[] maps) {
		String[][] board = Arrays.stream(maps)
			.map(map -> map.split(""))
			.toArray(String[][]::new);
		int path1 = bfs("S", "L", board);
		int path2 = bfs("L", "E", board);
		if (path1 == -1 || path2 == -1){
			return -1;
		}
		return path1 + path2;
	}

	private int bfs(String start, String end, String[][] board){
		int n = board.length;
		int m = board[0].length;
		boolean[][] visited = new boolean[n][m];
		int[] dy = {0, 0, 1, -1};
		int[] dx = {1, -1, 0, 0};
		Queue<int[]> queue = new LinkedList<>();
		boolean flag = false;

		// 시작 위치 탐색
		for (int i = 0; i < n; i++){
			for (int j = 0; j < m; j++){
				if (board[i][j].equals(start)){
					queue.offer(new int[]{i, j, 0});
					flag = true;
					break;
				}
			}
			if (flag){
				break;
			}
		}


		while(!queue.isEmpty()){
			int[] position = queue.poll();
			int y = position[0];
			int x = position[1];
			int cost = position[2];
			if (board[y][x].equals(end)){
				return cost;
			}

			for(int i = 0; i < 4; i++){
				int ny = y + dy[i];
				int nx = x + dx[i];

				if (ny >= 0 && ny < n && nx >= 0 && nx < m && !board[ny][nx].equals("X") && !visited[ny][nx]){
					queue.offer(new int[]{ny, nx, cost + 1});
					visited[ny][nx] = true;
				}
			}
		}

		return -1;
	}

	public static void main(String[] args) {
		String[] maps = {"SOOOL","XXXXO","OOOOO","OXXXX","OOOOE"};
		int answer = new Solution().solution(maps);
		System.out.println(answer);
	}
}

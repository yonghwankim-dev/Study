package org.example.practice.ch4.step04;

public class Solution {
	public static void main(String[] args) {
		int n = 4;
		int m = 4;
		int[] position = {1, 1};
		int dir = 0;
		int[][] map = {
			{1, 1, 1, 1},
			{1, 0, 0, 1},
			{1, 1, 0, 1},
			{1, 1, 1, 1}
		};

		assert solution(n, m, position, dir, map) == 3;
	}
	private static int solution(int n, int m, int[] position, int dir, int[][] map){
		int result = 0;
		boolean[][] visited = new boolean[n][m];

		int curDir = dir;
		int[] curPosition = position;
		int turnTime = 0;
		while (true){
			curDir = turnLeft(curDir);
			int[] next = getForwardPosition(curPosition, curDir);
			int ny = next[0];
			int nx = next[1];
			final int GROUND = 0;
			if (map[ny][nx] == GROUND && !visited[ny][nx]) {
				curPosition = new int[] {ny, nx};
				visited[ny][nx] = true;
				result++;
				turnTime = 0;
				continue;
			}else{
				turnTime++;
			}

			if (turnTime == 4){
				// 바라보는 방향을 유지한채로 한칸 뒤로 간다
				int[] back = getBackPosition(curPosition, curDir);
				final int SEA = 1;
				if (map[back[0]][back[1]] == SEA){
					break;
				}else{
					curPosition = back;
				}
				turnTime = 0;
			}
		}

		return result;
	}

	private static int turnLeft(int dir){
		// 0->3
		// 3->2
		// 2->1
		// 1->0
		int cur = dir;
		cur--;
		if (cur < 0){
			return 3;
		}
		return cur;
	}

	private static int[] getForwardPosition(int[] position, int idx){
		// 북-동-남-서
		int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
		int dy = dirs[idx][0];
		int dx = dirs[idx][1];
		int y = position[0];
		int x = position[1];
		return new int[]{dy + y, dx + x};
	}

	private static int[] getBackPosition(int[] position, int idx){
		int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
		int backIdx = (idx + 2) % 4;
		int[] dir = dirs[backIdx];
		int dy = dir[0];
		int dx = dir[1];
		int y = position[0];
		int x = position[1];
		return new int[]{dy + y, dx + x};
	}
}

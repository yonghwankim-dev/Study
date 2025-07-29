package PROM.PROM_49994;

import java.util.HashMap;
import java.util.Map;

class Solution {

	public boolean move(boolean[][][][] points, int y, int x, int ny, int nx){
		if(points[y][x][ny][nx] || points[ny][nx][y][x]){
			return false;
		}else{
			points[y][x][ny][nx] = true;
			points[ny][nx][y][x] = true;
			return true;
		}
	}

	public boolean check(int y, int x, int[] d){
		if(y + d[0] < 0 || y + d[0] > 10 || x + d[1] < 0 || x + d[1] > 10){
			return false;
		}
		return true;
	}

	public int solution(String dirs) {
		int answer = 0;
		boolean[][][][] points = new boolean[11][11][11][11];
		int[][] direction = {{-1,0}, {1,0}, {0,-1}, {0,1}}; // 상,하,좌,우
		Map<Character, Integer> map = new HashMap<>(){{
			put('U', 0);
			put('D', 1);
			put('L', 2);
			put('R', 3);
		}};
		int y = 5, x = 5, ny = 5, nx = 5;

		for(char c : dirs.toCharArray()){
			int[] d = direction[map.get(c)];

			if(!check(y, x, d)){
				continue;
			}

			ny += d[0];
			nx += d[1];

			if(move(points, y, x, ny, nx)){
				answer++;
			}

			y += d[0];
			x += d[1];
		}

		return answer;
	}
}

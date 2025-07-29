package org.example.ch4_imple;

// N x N
public class Solution1 {
	public int[] solution(int n, String[] plans){
		int y = 1;
		int x = 1;

		int[] dy = new int[]{-1, 1, 0, 0};
		int[] dx = new int[]{0 , 0, -1 , 1};
		String[] moveTypes = {"L", "R", "U", "D"};

		for (String plan : plans){
			int ny = 0;
			int nx = 0;
			for (int i = 0; i < moveTypes.length; i++){
				if (plan.equals(moveTypes[i])) {
					ny = y + dy[i];
					nx = x + dx[i];
					break;
				}
			}
			if (ny < 1 || ny > n || nx < 1 || nx > n){
				continue;
			}
			y = ny;
			x = nx;
		}
		return new int[]{x, y};
	}
}

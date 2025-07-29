package org.example.practice.ch4.step01;

import java.util.Arrays;

public class Solution {
	public static void main(String[] args) {
		int n = 5;
		String[] dirs = {"R", "R", "R", "U", "D", "D"};
		assert Arrays.equals(solution(n, dirs), new int[]{3, 4});
	}

	private static int[] solution(int n, String[] dirs) {
		int y = 1;
		int x = 1;
		int[] dy = {-1, 1, 0, 0};
		int[] dx = {0, 0, -1, 1};

		for (String dir : dirs){
			int idx = findIndex(dir);
			int ny = dy[idx] + y;
			int nx = dx[idx] + x;

			if (ny <= 0 || ny > n || nx <= 0 || nx > n){
				continue;
			}
			y = ny;
			x = nx;
		}

		return new int[]{y, x};
	}

	private static int findIndex(String dir){
		if (dir.equals("U")){
			return 0;
		}else if (dir.equals("D")){
			return 1;
		}else if (dir.equals("L")){
			return 2;
		}else{ // R
			return 3;
		}
	}
}

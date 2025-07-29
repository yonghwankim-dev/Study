package org.example.practice.ch4.step03;

public class Solution {
	public static void main(String[] args) {
		assert solution("a1") == 2;
		assert solution("c3") == 8;
	}

	private static int solution(String position){
		int result = 0;
		int n = 8;

		// 상좌, 상우, 하좌, 하우, 좌상, 좌하, 우상, 우하
		int[][] dirs = {{-2, -1}, {-2, 1}, {2, -1}, {2, 1}, {-1, -2}, {1, -2}, {-1, 2}, {1, 2}};

		String[] params = position.split("");
		int y = Integer.parseInt(params[1]);
		int x = parseCol(params[0]);

		for (int[] dir : dirs){
			int ny = dir[0] + y;
			int nx = dir[1] + x;
			if (ny <= 0 || ny > n || nx <= 0 || nx > n){
				continue;
			}
			result++;
		}
		System.out.println(position + ": " + result);
		return result;
	}

	private static int parseCol(String text){
		return text.toLowerCase().charAt(0) - 'a' + 1;
	}
}

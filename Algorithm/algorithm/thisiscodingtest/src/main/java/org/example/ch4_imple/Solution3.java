package org.example.ch4_imple;

// 왕실의 나이트
public class Solution3 {
	public int solution(String position){
		int answer = 0;
		String cols = "abcdefgh";
		int x = cols.indexOf(position.split("")[0]) + 1;
		int y = Integer.parseInt(position.split("")[1]);

		int[] dy = {-2, -2, 2, 2, -1, 1, -1, 1};
		int[] dx = {-1, 1, -1, 1, -2, -2, 2, 2};
		int n = 8;
		for (int i = 0; i < n; i++){
			int ny = y + dy[i];
			int nx = x + dx[i];

			if (ny <= 0 || ny > n || nx <= 0 || nx > n){
				continue;
			}
			System.out.printf("ny = %d, nx = %d%n", ny, nx);
			answer++;
		}
		return answer;
	}
}

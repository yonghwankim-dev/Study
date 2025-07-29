package org.example.ch9_shortest_path.step03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FloydWarshall {
	private static int[][] solution(int n, int[][] map) {

		for (int k = 1; k <= n; k++){
			for (int a = 1; a <= n; a++){
				for (int b = 1; b <= n; b++){
					map[a][b] = Math.min(map[a][b], map[a][k] + map[k][b]);
				}
			}
		}
		return map;
	}

	public static void main(String[] args) {
		int n = 4;
		int m = 7;
		int[][] map = new int[n + 1][n + 1];
		for (int i = 1; i <= n; i++){
			for (int j = 1; j <= n; j++){
				if (i == j){
					map[i][j] = 0;
				}else{
					map[i][j] = 100_000_000;
				}
			}
		}

		String[] inputs = {
			"1 2 4",
			"1 4 6",
			"2 1 3",
			"2 3 7",
			"3 1 5",
			"3 4 4",
			"4 3 4"
		};
		for (String input : inputs){
			int[] arr = Arrays.stream(input.split(" ")).mapToInt(Integer::parseInt).toArray();
			int src = arr[0];
			int dst = arr[1];
			int dist = arr[2];
			map[src][dst] = dist;
		}
		int[][] answer = solution(n, map);
		for (int i = 1; i <= n; i++){
			for (int j = 1; j <= n; j++){
				System.out.print(answer[i][j] + " ");
			}
			System.out.println();
		}
	}
}

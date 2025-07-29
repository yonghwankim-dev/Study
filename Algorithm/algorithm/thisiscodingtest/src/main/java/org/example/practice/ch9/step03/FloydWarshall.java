package org.example.practice.ch9.step03;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

public class FloydWarshall {
	public static void main(String[] args) {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
			int v = Integer.parseInt(br.readLine());
			int e = Integer.parseInt(br.readLine());
			int[][] map = new int[v + 1][v + 1];
			for(int i = 1; i <= v; i++){
				for(int j = 1; j <= v; j++){
					if(i == j){
						map[i][j] = 0;
					}else{
						map[i][j] = 100_000_000;
					}
				}
			}
			

			for(int i = 0; i < e; i++){
				String[] split = br.readLine().split(" ");
				int src = Integer.parseInt(split[0]);
				int dst = Integer.parseInt(split[1]);
				int cost = Integer.parseInt(split[2]);
				map[src][dst] = cost;
			}

			int[][] answer = solution(v, map);
			int[][] expected = new int[][]{
					{0, 0, 0, 0, 0},
					{0, 0, 4, 10, 6},
					{0, 3, 0, 7, 9},
					{0, 5, 9, 0, 4},
					{0, 9, 13, 4, 0}
				};
			Arrays.stream(answer)
				.map(Arrays::toString)
				.forEach(System.out::println);
			assert Arrays.deepEquals(answer, expected);
		}catch(IOException e){
			throw new IllegalArgumentException(e);
		}
	}

	private static int[][] solution(int v, int[][] map){
		for(int k = 1; k <= v; k++){
			for(int a = 1; a <= v; a++){
				for(int b = 1; b <= v; b++){
					map[a][b] = Math.min(map[a][b], map[a][k] + map[k][b]);
				}
			}
		}
		return map;
	}
}

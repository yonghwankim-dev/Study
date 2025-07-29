package org.example.practice.ch9.step05;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class CityV2 {
	public static void main(String[] args) {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
			String[] split = br.readLine().split(" ");
			int v = Integer.parseInt(split[0]);
			int e = Integer.parseInt(split[1]);
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
				split = br.readLine().split(" ");
				int src = Integer.parseInt(split[0]);
				int dst = Integer.parseInt(split[1]);
				int cost = 1;
				map[src][dst] = cost;
				map[dst][src] = cost;
			}

			split = br.readLine().split(" ");
			int x = Integer.parseInt(split[0]);
			int k = Integer.parseInt(split[1]);
			int answer = solution(v, map, x, k);	
			int expected = Integer.parseInt(br.readLine());
			assert answer == expected;
		}catch(IOException e){
			throw new IllegalArgumentException(e);
		}
	}

	private static int solution(int v, int[][] map, int x, int k){
		for(int r = 1; r <= v; r++){
			for(int a = 1; a <= v; a++){
				for(int b = 1; b <= v; b++){
					map[a][b] = Math.min(map[a][b], map[a][r] + map[r][b]);
				}
			}
		}		
		int result = map[1][k] + map[k][x];
		return result >= 100_000_000 ? -1 : result;
	}
}

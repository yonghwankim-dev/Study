package org.example.ch9_shortest_path.step04;

import java.io.IOException;

public class Solution {
	public static int solution(int n, int x, int k, int[][] graph){
		// 점화식에 따라 플로이드 워셜 알고리즘 수행
		for (int i = 1; i <= n; i++){
			for(int a = 1; a <= n; a++){
				for (int b = 1; b <= n; b++){
					graph[a][b] = Math.min(graph[a][b], graph[a][i] + graph[i][b]);
				}
			}
		}
		int distance = graph[1][k] + graph[k][x];
		return distance >= 1_000_000 ? -1 : distance;
	}

	public static void main(String[] args) throws IOException {
		int n = 5;
		int[][] graph = new int[n + 1][n + 1];
		// 자기 자신에서 자기 간선으로 가는 비용은 0으로 초기화
		for (int i = 1; i <= n; i++){
			for(int j = 1; j <= n; j++){
				if (i == j){
					graph[i][j] = 0;
				}else{
					graph[i][j] = 100_000_000;
				}
			}
		}

		String[] inputs = {
			"1 2",
			"1 3",
			"1 4",
			"2 4",
			"3 4",
			"3 5",
			"4 5"};
		for (String input : inputs){
			String[] split = input.split(" ");
			int src = Integer.parseInt(split[0]);
			int dst = Integer.parseInt(split[1]);
			graph[src][dst] = 1;
			graph[dst][src] = 1;
		}

		int x = 4;
		int k = 5;
		System.out.println(solution(n, x, k, graph));
	}
}

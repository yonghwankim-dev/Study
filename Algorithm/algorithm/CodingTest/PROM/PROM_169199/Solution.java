package PROM.PROM_169199;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Solution {
	public int solution(String[] board) {
		int answer = 0;
		String[][] graph = Arrays.stream(board)
			.map(row -> row.split(""))
			.toArray(String[][]::new);
		int[] start = findStartPosition(graph);
		int[][] dist = initDist(graph.length, graph[0].length);
		answer = bfs(graph, start, dist);
		return answer;
	}

	private static int[][] initDist(int h, int w) {
		int[][] dist = new int[h][w];
		for (int i = 0; i < h; i++){
			for (int j = 0; j < w; j++){
				dist[i][j] = Integer.MAX_VALUE;
			}
		}
		return dist;
	}

	private int[] findStartPosition(String[][] graph) {
		for (int i = 0; i < graph.length; i++){
			for (int j = 0; j < graph[i].length; j++){
				if (graph[i][j].equals("R")){
					return new int[]{i, j, 0};
				}
			}
		}
		return null;
	}

	private int bfs(String[][] graph, int[] start, int[][] dist){
		int h = graph.length;
		int w = graph[0].length;
		int[] dy = {0, 0, 1, -1};
		int[] dx = {1, -1, 0, 0};

		Queue<int[]> queue = new LinkedList<>();
		queue.offer(start);

		while (!queue.isEmpty()){
			int[] cur = queue.poll();

			// 목표 지점(G) 도착한 경우 이동횟수 반환
			if (graph[cur[0]][cur[1]].equals("G")){
				return cur[2];
			}

			// 4방향 탐색
			for(int i = 0; i < 4; i++){
				int ny = cur[0];
				int nx = cur[1];

				// 해당 방향으로 미끄러지며 이동 가능한 위치 탐색
				while (ny + dy[i] >= 0
					&& ny + dy[i] < h
					&& nx + dx[i] >= 0
					&& nx + dx[i] < w
					&& !graph[ny + dy[i]][nx + dx[i]].equals("D")){
					ny += dy[i];
					nx += dx[i];
				}

				// 이전에 해당 위치에 도달한 적이 없거나, 이전에 도달한 경우보다 적은 이동횟수로 도달한 경우
				if (dist[ny][nx] > cur[2] + 1){
					dist[ny][nx] = cur[2] + 1;
					queue.offer(new int[]{ny, nx, cur[2] + 1});
				}
			}
		}

		return -1;
	}
}

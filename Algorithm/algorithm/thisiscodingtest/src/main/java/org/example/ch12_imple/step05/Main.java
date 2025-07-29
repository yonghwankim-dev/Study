package org.example.ch12_imple.step05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static final int EMPTY = 0;
	static final int SNAKE = 1;
	static final int APPLE = 2;
	static final int[][] directions = {{2, 3}, {3, 2}, {1, 0}, {0, 1}};
	static final int[] dy = {-1, 1, 0, 0};
	static final int[] dx = {0, 0, -1, 1};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		int k = Integer.parseInt(br.readLine());
		int[][] board = new int[n + 1][n + 1];
		StringTokenizer st;
		for (int i = 0; i < k; i++){
			st = new StringTokenizer(br.readLine());
			int y = Integer.parseInt(st.nextToken());
			int x = Integer.parseInt(st.nextToken());
			board[y][x] = APPLE;
		}

		int d = Integer.parseInt(br.readLine());
		Queue<String[]> queue = new LinkedList<>();
		for(int i = 0; i < d; i++){
			queue.offer(br.readLine().split(" "));
		}

		int answer = solution(board, queue);
		System.out.println(answer);
	}

	private static int solution(int[][] board, Queue<String[]> queue) {
		int y = 1;
		int x = 1;
		int dir = 3;
		board[y][x] = SNAKE;
		List<int[]> snake = new LinkedList<>();
		snake.add(new int[]{y, x});

		int time = 0;
		while(true){
			if(!queue.isEmpty()){
				int target = Integer.parseInt(queue.peek()[0]);
				if (time == target){
					String nextDir = queue.poll()[1];
					dir = getNextDir(dir, nextDir);
				}
			}
			boolean success = move(board, y, x, dir, snake);
			if(!success){
				time++;
				break;
			}
			int[] head = snake.get(0);
			y = head[0];
			x = head[1];
			time++;
		}
		return time;
	}

	private static int getNextDir(int dir, String nextDir) {
		if (nextDir.equals("L")) {
			return directions[dir][0];
		}
		// "D"
		return directions[dir][1];
	}

	private static boolean move(int[][] board, int y, int x, int dir, List<int[]> snake) {
		int n = board.length;
		int ny = y + dy[dir];
		int nx = x + dx[dir];

		if(ny <= 0 || ny >= n || nx <= 0 || nx >= n){
			return false;
		} else if(board[ny][nx] == SNAKE){
			return false;
		} else if (board[ny][nx] == APPLE){
			board[ny][nx] = SNAKE;
			snake.add(0, new int[]{ny, nx});
		} else if(board[ny][nx] == EMPTY){
			board[ny][nx] = SNAKE;
			int[] tail = snake.remove(snake.size() - 1);
			board[tail[0]][tail[1]] = EMPTY;
			snake.add(0, new int[]{ny, nx});
		}
		return true;
	}
}

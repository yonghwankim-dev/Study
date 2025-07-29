package BOJ.BOJ_11403;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static int[][] solution(int[][] board, int n){
		for (int i = 0; i < n; i++){
			for (int a = 0; a < n; a++){
				for (int b = 0; b < n; b++){
					if (board[a][i] == 1 && board[i][b] == 1){
						board[a][b] = 1;
					}
				}
			}
		}
		return board;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		int[][] board = new int[n][n];

		for (int i = 0; i < n; i++){
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < n; j++){
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		int[][] answer = solution(board, n);

		for (int i = 0; i < n; i++){
			for (int j = 0; j < n; j++){
				System.out.print(answer[i][j] + " ");
			}
			if (i < n - 1){
				System.out.println();
			}
		}
	}
}

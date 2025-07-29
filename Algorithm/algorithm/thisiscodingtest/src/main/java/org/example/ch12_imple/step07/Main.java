package org.example.ch12_imple.step07;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

// chicken delivery
public class Main{
    static int n;
    static int m;
    static boolean[] choosed;
    static List<int[]> houses;
    static List<int[]> chickens;
    static int answer = Integer.MAX_VALUE;
    
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        
        int[][] board = new int[n+1][n+1];
        for(int i = 1; i <= n; i++){
            String[] split = br.readLine().split(" ");
            for(int j = 1; j <= n; j++){
                board[i][j] = Integer.parseInt(split[j - 1]);
            }
        }
        int answer = solution(board);
        System.out.println(answer);
    }
    
    private static int solution(int[][] board){
        houses = new ArrayList<>();
        chickens = new ArrayList<>();
        for(int i = 1; i <= n; i++){
            for(int j = 1; j <= n; j++){
                if(board[i][j] == 1){            
                    houses.add(new int[]{i, j});
                }else if(board[i][j] == 2){
                    chickens.add(new int[]{i, j});
                }
            }
        }
        choosed = new boolean[chickens.size()];
        backtracking(0, 0);
        return answer;
    }
    
    private static void backtracking(int start, int count){
        if(count == m){
            int sum = 0;
			for (int[] h : houses) {
				int min = Integer.MAX_VALUE;
				for (int j = 0; j < chickens.size(); j++) {
					if (choosed[j]) {
						int[] c = chickens.get(j);
						int dist = Math.abs(h[0] - c[0]) + Math.abs(h[1] - c[1]);
						min = Math.min(min, dist);
					}
				}
				sum += min;
			}
            answer = Math.min(answer, sum);
            return;
        }

        for(int i = start; i < chickens.size(); i++){
            choosed[i] = true;
            backtracking(i + 1, count + 1);
            choosed[i] = false;
        }
    }
}

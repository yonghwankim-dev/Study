package org.example.ch13_graph.step06;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

// Avoid surveillance
public class Main{
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        String[][] map = new String[n][n];
        for(int i = 0; i < n; i++){
            map[i] = br.readLine().split(" ");
        }
        String answer = solution(n, map);
        System.out.println(answer);
    }

    private static String solution(int n, String[][] map){
        int wall = 0;
		return makeWall(n, map, wall);
    }

    private static String makeWall(int n, String[][] map, int wall){
        if(wall == 3){
            return bfs(n, map);
        }

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(map[i][j].equals("X")){
                    map[i][j] = "O";
                    String result = makeWall(n, map, wall + 1);
                    map[i][j] = "X";
                    if(result.equals("YES")){
                        return "YES";
                    }
                }
            }
        }
        return "NO";
    }

    private static String bfs(int n, String[][] map){
        Queue<int[]> queue = new LinkedList();
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(map[i][j].equals("T")){
                    queue.offer(new int[]{i, j});
                }
            }
        }
        int[] dy = {-1, 1, 0, 0};
        int[] dx = {0, 0, -1, 1};
        while(!queue.isEmpty()){
            int[] now = queue.poll();

            for(int i = 0; i < 4; i++){
                int ny = dy[i] + now[0];
                int nx = dx[i] + now[1];

                while(ny >= 0 && ny < n && nx >= 0 && nx < n){
                    if(map[ny][nx].equals("S")){
                        return "NO";
                    }else if(map[ny][nx].equals("T") || map[ny][nx].equals("O")){
                        break;
                    }
                    ny += dy[i];
                    nx += dx[i];
                }
            }
        }
        return "YES";
    }
}

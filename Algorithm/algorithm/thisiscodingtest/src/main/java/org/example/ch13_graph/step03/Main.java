package org.example.ch13_graph.step03;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

// competitive transmission
public class Main{
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int[][] map = new int[n+1][n+1];
        for(int i = 1; i <= n; i++){
            int[] row = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::valueOf)
                .toArray();
            for(int j = 1; j <= n; j++){
                map[i][j] = row[j-1];
            }
        }
        st = new StringTokenizer(br.readLine());
        int s = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        int answer = solution(map, s, new int[]{x, y}, n);
        System.out.println(answer);
    }
    
    private static int solution(int[][] map, int second, int[] target, int n){
        boolean[][] visited = new boolean[n + 1][n + 1];
        for(int i = 0; i < second; i++){
            bfs(map, n, visited);
        }
        
        return map[target[0]][target[1]];
    }
    
    private static void bfs(int[][] map, int n, boolean[][] visited){
        List<int[]> virus = new ArrayList();
        for(int i = 1; i <= n; i++){
            for(int j = 1; j <= n; j++){
                if(map[i][j] != 0 && !visited[i][j]){
                    virus.add(new int[]{i, j, map[i][j]});
                    visited[i][j] = true;
                }
            }
        }
        virus.sort(Comparator.comparingInt(o1->o1[2]));
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        Queue<int[]> queue = new LinkedList(virus);
        while(!queue.isEmpty()){
            int[] now = queue.poll();
            
            for(int i = 0; i < 4; i++){
                int nx = dx[i] + now[0];
                int ny = dy[i] + now[1];
                
                if(nx <= 0 || nx > n || ny <= 0 || ny > n || visited[nx][ny] || map[nx][ny] != 0){
                    continue;
                }
                map[nx][ny] = now[2];
            }
        }
    }
}

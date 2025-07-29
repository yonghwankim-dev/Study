package org.example.ch13_graph.step02;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

// Research Institute
public class Main{
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] map = new int[n][m];
        for(int i = 0; i < n; i++){
            map[i] = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::valueOf)
                .toArray();
        }
        
        int answer = solution(map, n, m);
        System.out.println(answer);
    }
    
    private static int solution(int[][] map, int n, int m){
        int wall = 0;
        return makeWall(map, n, m, wall);
    }
    
    private static int makeWall(int[][] map, int n, int m, int wall){
        if(wall == 3){
            return bfs(map);
        }
        
        int result = 0;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(map[i][j] == 0){
                    map[i][j] = 1;
                    int safe = makeWall(map, n, m, wall + 1);
                    map[i][j] = 0;
                    result = Math.max(result, safe);
                }
            }
        }
        return result;
    }
    
    private static int bfs(int[][] originalMap){
        int[][] map = copyMap(originalMap);
        int n = map.length;
        int m = map[0].length;
        Queue<int[]> queue = new LinkedList();
        boolean[][] visited = new boolean[n][m];
        
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(map[i][j] == 2){
                   queue.offer(new int[]{i, j});
                   visited[i][j] = true;
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
                
                if(ny < 0 || ny >= n || nx < 0 || nx >= m){
                    continue;
                }
                if(visited[ny][nx] || map[ny][nx] != 0){
                    continue;
                }
                
                visited[ny][nx] = true;
                queue.offer(new int[]{ny, nx});
                map[ny][nx] = 2;
            }
        }
        return countSafeZone(map);
    }
    
    private static int[][] copyMap(int[][] map){
        int n = map.length;
        int m = map[0].length;
        int[][] result = new int[n][m];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                result[i][j] = map[i][j];
            }
        }
        return result;
    }
    
    private static int countSafeZone(int[][] map){
        int result = 0;
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                if(map[i][j] == 0){
                    result++;
                }
            }
        }
        return result;
    }
}

package org.example.ch13_graph.step07;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

// move population
public class Main{

    static int n;
    static int left;
    static int right;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        left = Integer.parseInt(st.nextToken());
        right = Integer.parseInt(st.nextToken());

        int[][] map = new int[n][n];
        for(int i = 0; i < n; i++){
            map[i] = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::valueOf)
                .toArray();
        }
        int answer = solution(map);
        System.out.println(answer);
    }

    private static int solution(int[][] map){
        int day = 0;
        while(true){
            boolean[][] visited = new boolean[n][n];
            int stop = 0;
            for(int i = 0; i < n; i++){
                for(int j = 0; j < n; j++){
                    if(!visited[i][j]){
                        visited[i][j] = true;
                        stop += bfs(i, j, map, visited);
                    }
                }
            }
            if(stop == 0){
                break;
            }
            day++;
        }
        return day;
    }

    private static int bfs(int y, int x, int[][] map, boolean[][] visited){
        Queue<int[]> queue = new LinkedList();
        queue.offer(new int[]{y, x});
        int sum = map[y][x];

        int[] dy = {-1, 1, 0, 0};
        int[] dx = {0, 0, -1, 1};
        List<int[]> union = new ArrayList();

        while(!queue.isEmpty()){
            int[] now = queue.poll();
            union.add(now);

            for(int i = 0; i < 4; i++){
                int ny = dy[i] + now[0];
                int nx = dx[i] + now[1];
                int[] next = new int[]{ny, nx};

                if(ny < 0 || ny >= n || nx < 0 || nx >= n || visited[ny][nx]){
                    continue;
                }
                if(!checkDiff(map, now, next)){
                    continue;
                }

                sum += map[ny][nx];
                queue.offer(next);
                visited[ny][nx] = true;
            }
        }

        if(union.size() == 1){
            return 0;
        }

        int avg = sum / union.size();
        for(int[] u : union){
            map[u[0]][u[1]] = avg;
        }
        return 1;
    }

    private static boolean checkDiff(int[][] map, int[] from, int[] to){
        int a = map[from[0]][from[1]];
        int b = map[to[0]][to[1]];
        int diff = Math.abs(a - b);
        return diff >= left && diff <= right;
    }
}

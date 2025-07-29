package PROM.PROM_250136;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Solution {

    int n;
    int m;
    int[][] countMap;

    public int solution(int[][] land) {
        int answer = 0;
        this.n = land.length;
        this.m = land[0].length;
        this.countMap = new int[n][m];
        int unique = 1;
        List<Integer> oil = new ArrayList<>();
        oil.add(0);

        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(land[i][j] == 1 && countMap[i][j] == 0){
                    int depth = bfs(i, j, land, unique);
                    oil.add(depth);
                    unique++;
                }
            }
        }

        for(int i = 0; i < m; i++){
            int sum = 0;
            boolean[] visited = new boolean[oil.size()];
            for(int j = 0; j < n; j++){
                int uniqueVal = countMap[j][i];
                if(!visited[uniqueVal] && uniqueVal != 0){
                    visited[uniqueVal] = true;
                    sum += oil.get(uniqueVal);
                }
            }
            answer = Math.max(answer, sum);
        }

        return answer;
    }

    private int bfs(int y, int x, int[][] land, int unique){
        Queue<int[]> queue = new LinkedList<>();
        int[] dy = {-1, 1, 0, 0};
        int[] dx = {0, 0, -1, 1};
        int depth = 1;
        countMap[y][x] = unique;
        queue.offer(new int[]{y, x});

        while(!queue.isEmpty()){
            int[] p = queue.poll();


            for(int i = 0; i < 4; i++){
                int ny = dy[i] + p[0];
                int nx = dx[i] + p[1];

                if(ny < 0 || ny >= n || nx < 0 || nx >= m){
                    continue;
                }
                if(land[ny][nx] == 0){
                    continue;
                }
                if(countMap[ny][nx] != 0){
                    continue;
                }
                depth++;
                countMap[ny][nx] = unique;
                queue.offer(new int[]{ny, nx});
            }
        }
        return depth;
    }


    public static void main(String[] args) {
        Solution solution = new Solution();
        int[][] land = {{0, 0, 0, 1, 1, 1, 0, 0}, {0, 0, 0, 0, 1, 1, 0, 0}, {1, 1, 0, 0, 0, 1, 1, 0},
            {1, 1, 1, 0, 0, 0, 0, 0}, {1, 1, 1, 0, 0, 0, 1, 1}};
        System.out.println(solution.solution(land));
    }
}

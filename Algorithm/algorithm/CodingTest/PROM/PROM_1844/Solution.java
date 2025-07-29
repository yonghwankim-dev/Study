package PROM.PROM_1844;

import java.util.LinkedList;
import java.util.Queue;

class Solution {

    static class Point{
        int y, x;

        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
    public boolean isInside(int y, int x, int N, int M){
        return y >= 0 && y < N && x >= 0 && x < M;
    }

    public int solution(int[][] maps) {
        int answer = 0;
        int N = maps.length;
        int M = maps[0].length;
        int cur_x = 0;
        int cur_y = 0;
        int[][] check = new int[N][M]; // 탐색하면서 몇번지나갔는지 체크
        int[][] dirs = {{-1,0}, {1, 0}, {0, -1}, {0, 1}}; // 상 하 좌 우
        Queue<Point> queue = new LinkedList<>();

        queue.add(new Point(cur_y, cur_x));
        check[cur_y][cur_x] = 1;

        while(!queue.isEmpty()){
            cur_y = queue.peek().y;
            cur_x = queue.peek().x;
            queue.poll();

            if(cur_y == N-1 && cur_x == M-1){
                break;
            }

            for(int i = 0; i < 4; i ++){
                int next_y = cur_y + dirs[i][0];
                int next_x = cur_x + dirs[i][1];


                // next y, x가 배열 내부에 있고 check 2차원 배열에 기록된 적 없고, map의 next_y, next_x 좌표가 갈수 있는 길인경우
                if(isInside(next_y, next_x, N, M) && check[next_y][next_x] == 0 && maps[next_y][next_x] == 1){
                    check[next_y][next_x] = check[cur_y][cur_x] + 1; // 이전 방문값 + 1 = 다음 방문
                    queue.add(new Point(next_y, next_x)); // 방문할 곳을 넣음
                }
            }
        }
        answer = check[N-1][M-1] == 0 ? -1 : check[N-1][M-1];

        return answer;
    }

    public void print(int[][] checks){
        for(int i = 0; i < checks.length; i++){
            for(int j = 0; j < checks[i].length; j++){
                System.out.print(checks[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args){
        int[][] maps = {{1,0,1,1,1},{1,0,1,0,1},{1,0,1,1,1},{1,1,1,0,1},{0,0,0,0,1}};
        int[][] maps2 = {{1,0,1,1,1},{1,0,1,0,1},{1,0,1,1,1},{1,1,1,0,0},{0,0,0,0,1}};
        //new Solution().solution(maps);
        new Solution().solution(maps2);
    }
}

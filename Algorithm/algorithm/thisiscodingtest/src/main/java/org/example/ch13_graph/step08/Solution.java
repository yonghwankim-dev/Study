package org.example.ch13_graph.step08;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

// move block
class Solution {
    public int solution(int[][] board) {
        int n = board.length;
        int[][] newBoard = new int[n+2][n+2];
        for(int i = 0; i < newBoard.length; i++){
            Arrays.fill(newBoard[i], 1);
        }
        
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                newBoard[i+1][j+1] = board[i][j];
            }
        }
        
        Queue<int[]> queue = new LinkedList();
        queue.offer(new int[]{1,1,1,2,0});
        Set<int[]> confirm = new HashSet();
        confirm.add(new int[]{1,1,1,2});
        
        while(!queue.isEmpty()){
            int[] now = queue.poll();
            int[] cur1 = new int[]{now[0], now[1]};
            int[] cur2 = new int[]{now[2], now[3]};
            int count = now[4];
            
            if(Arrays.equals(cur1, new int[]{n, n}) || Arrays.equals(cur2, new int[]{n, n})){
                return count;
            }
            
            for(int[] next : canMove(cur1, cur2, newBoard)){
                if(!contains(next, confirm)){          
                    queue.offer(new int[]{next[0], next[1], next[2], next[3], count + 1});
                    confirm.add(next);
                }
            }
        }
        return 0;
    }
    
    private boolean contains(int[] target, Set<int[]> confirm){
        for(int[] c : confirm){
            if(Arrays.equals(target, c)){
                return true;
            }
        }
        return false;
    }
    
    private List<int[]> canMove(int[] cur1, int[] cur2, int[][] newBoard){
        List<int[]> cand = new ArrayList();
        int y = 0;
        int x = 1;
        int[] dy = {-1, 1, 0, 0};
        int[] dx = {0, 0, -1, 1};
        
        // 평행이동
        for(int i = 0; i < 4; i++){
            int ny1 = dy[i] + cur1[y];
            int nx1 = dx[i] + cur1[x];
            int ny2 = dy[i] + cur2[y];
            int nx2 = dx[i] + cur2[x];
            
            if(newBoard[ny1][nx1] == 0 && newBoard[ny2][nx2] == 0){
                cand.add(new int[]{ny1, nx1, ny2, nx2});
            }
        }
        
        // 회전
        if(cur1[y] == cur2[y]){ // 가로방향
            int up = -1;
            int down = 1;
            int[] dir = new int[]{up, down};
            for(int d : dir){
                if(newBoard[cur1[y]+d][cur1[x]] == 0 && newBoard[cur2[y]+d][cur2[x]] == 0){
                    cand.add(new int[]{cur1[y], cur1[x], cur1[y] + d, cur1[x]});
                    cand.add(new int[]{cur2[y], cur2[x], cur2[y] + d, cur2[x]});
                }
            }
        }else{ // 세로 방향
            int left = -1;
            int right = 1;
            int[] dir = new int[]{left, right};
            for(int d : dir){
                if(newBoard[cur1[y]][cur1[x] + d] == 0 && newBoard[cur2[y]][cur2[x] + d] == 0){
                    cand.add(new int[]{cur1[y], cur1[x] + d, cur1[y], cur1[x]});
                    cand.add(new int[]{cur2[y], cur2[x] + d, cur2[y], cur2[x]});
                }
            }
        }
        return cand;
    }
}

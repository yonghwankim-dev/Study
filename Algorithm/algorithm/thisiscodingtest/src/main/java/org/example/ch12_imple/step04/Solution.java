package org.example.ch12_imple.step04;

import java.util.Arrays;

class Solution {
    public boolean solution(int[][] key, int[][] lock) {
        int n = lock.length;
        int m = key.length;
        
        int[][] board = new int[n + m*2][n + m*2];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                board[m+i][m+j] = lock[i][j];
            }
        }
        
        for(int i = 0; i < 4; i++){
            key = rotate(key);
            
            for(int x = 1; x < n + m; x++){
                for(int y = 1; y < n + m; y++){
                    if(compare(board, key, x, y, m, n)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private int[][] rotate(int[][] key){
        int n = key.length;
        int[][] rotateKey = new int[n][n];
        
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                rotateKey[j][n-i-1] = key[i][j];
            }
        }
        return rotateKey;
    }
    
    private boolean compare(int[][] board, int[][] key, int x, int y, int m, int n){
        boolean result = true;
        for(int i = 0; i < m; i++){
            for(int j = 0; j < m; j++){
                board[x + i][y + j] += key[i][j];
            }
        }

        for(int i = 0; i < n; i++){
            if(!result){
                break;
            }
            for(int j = 0; j < n; j++){
                if(board[m + i][m + j] != 1){
                    result = false;
                    break;
                }
            }
        }
        
        for(int i = 0; i < m; i++){
            for(int j = 0; j < m; j++){
                board[x+i][y+j] -= key[i][j];
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[][] key = new int[][]{{0, 0, 0}, {1, 0, 0}, {0, 1, 1}};
        int[][] lock = new int[][]{{1, 1, 1}, {1, 1, 0}, {1, 0, 1}};
        boolean result = solution.solution(key, lock);
        System.out.println(result);
    }
}

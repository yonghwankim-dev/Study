package kr.yh.chap06_dp.matrixPath;

public class MatrixPath {
    public int solution(int[][] value){
        int n = value.length;
        int[][] sum = new int[n + 1][n + 1];
        for(int y = 1; y <= n; y++){
            for(int x = 1; x <= n; x++){
                sum[y][x] = Math.max(sum[y][x - 1], sum[y - 1][x]) + value[y - 1][x - 1];
            }
        }

        return sum[n][n];
    }
}

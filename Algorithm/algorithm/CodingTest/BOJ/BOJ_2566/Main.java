package BOJ.BOJ_2566;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = 9;
        int[][] matrix = new int[n][n];
        for(int i = 0; i < n; i++){
            matrix[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        Main.solution(n, matrix);
    }
    public static void solution(int n, int[][] matrix){
        int maxVal = -1;
        int y = 0, x = 0;

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(matrix[i][j] > maxVal){
                    maxVal = matrix[i][j];
                    y = i;
                    x = j;
                }
            }
        }

        printMaxVal(maxVal);
        printPoint(y, x);
    }

    private static void printMaxVal(int maxVal){
        System.out.println(maxVal);
    }

    private static void printPoint(int y, int x){
        System.out.println((y+1) + " " + (x+1));
    }
}

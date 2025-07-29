package BOJ.BOJ_2738;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    private static BufferedReader br;
    private static int n, m;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        String[] inputs = br.readLine().split(" ");
        n = Integer.parseInt(inputs[0]);
        m = Integer.parseInt(inputs[1]);
        int[][] matrix1 = inputMatrix();
        int[][] matrix2 = inputMatrix();

        int[][] answer = Main.solution(n, m, matrix1, matrix2);
        printDeepArray(answer);
    }

    private static int[][] inputMatrix() throws IOException {
        int[][] matrix = new int[n][m];
        for(int i = 0; i < n; i++){
            matrix[i] = inputIntArray();
        }
        return matrix;
    }

    private static int[] inputIntArray() throws IOException {
        return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
    }

    public static int[][] solution(int n, int m, int[][] matrix1, int[][] matrix2) {
        int[][] answer = new int[n][m];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                answer[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        return answer;
    }

    private static void printDeepArray(int[][] matrix){
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}

package BOJ.BOJ_2563;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    private static boolean[][] matrix;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[][] points = new int[n][2];
        for(int i = 0; i < n; i++){
            points[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        int answer = Main.solution(n, points);
        System.out.println(answer);
    }

    public static int solution(int n, int[][] points) {
        matrix = new boolean[100][100];
        int answer = 0;
        for(int[] point : points){
            int x = point[0];
            int y = point[1];
            answer += pastePaper(x, y);
        }
        return answer;
    }

    private static int pastePaper(int x, int y){
        int count = 0;
        int leftTop_x = x;
        int leftTop_y = y + 10;
        int rightBottom_x = x + 10;
        int rightBottom_y = y;

        for(int i = leftTop_y; i > rightBottom_y; i--){
            for(int j = leftTop_x; j < rightBottom_x; j++){
                if(matrix[i][j] == false){
                    matrix[i][j] = true;
                    count++;
                }
            }
        }
        return count;
    }
}

package BOJ.BOJ_1992;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int[][] arr;
    static StringBuilder sb;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[][] arr = new int[n][n];
        for(int i = 0; i < n; i++){
            arr[i] = Arrays.stream(br.readLine().split("")).mapToInt(Integer::parseInt).toArray();
        }
        String answer = Main.solution(n, arr);
        System.out.println(answer);
    }

    public static String solution(int n, int[][] arr){
        Main.arr = arr;
        Main.sb  = new StringBuilder();
        compress(n, 0, 0);
        return sb.toString();
    }

    public static void compress(int n, int y, int x){
        // ���� ���
        if(n == 1){
            sb.append(arr[y][x]);
            return;
        }

        boolean blsZero = true;
        boolean blsOne  = true;

        // y�� ~ y+n��
        for(int i = y; i < y + n; i++){
            // x�� ~ x+n��
            for(int j = x; j < x + n; j++){
                if (arr[i][j] == 1) {
                    blsZero = false;
                }else{
                    blsOne = false;
                }

                if(isAllFlase(blsZero, blsOne)){
                    break;
                }
            }

            // �� �࿡ 0�� 1�� ��� �����ִ°�� �ߴ�
            if(isAllFlase(blsZero, blsOne)){
                break;
            }
        }

        if(blsZero){ // 0���θ� �̷���� �ִ� ���
            sb.append(0);
            return;
        }else if(blsOne) { // 1�θ� �̿췯�� �ִ� ���
            sb.append(1);
            return;
        }
        // 0�� 1�� �����ִ� ���
        sb.append("(");
        compress(n / 2, y, x);                       // 2��и�
        compress(n / 2, y, x + n / 2);            // 1��и�
        compress(n / 2, y + n / 2, x);            // 3��и�
        compress(n / 2, y + n / 2, x + n / 2); // 4��и�
        sb.append(")");
        return;
    }

    private static boolean isAllFlase(boolean zero, boolean one){
        return !zero && !one;
    }
}

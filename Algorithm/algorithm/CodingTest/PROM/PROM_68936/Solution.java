package PROM.PROM_68936;

import java.util.Arrays;

class Solution {
    static int[][] arr;
    static StringBuilder sb;
    public static int[] solution(int[][] arr){
        Solution.arr = arr;
        Solution.sb  = new StringBuilder();
        int[] answer;
        int n = arr.length;

        compress(n, 0, 0);

        int zero = countNumber(sb.toString(), 0);
        int one = countNumber(sb.toString(), 1);
        answer = new int[]{zero, one};
        return answer;
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
        compress(n / 2, y, x);                       // 2��и�
        compress(n / 2, y, x + n / 2);            // 1��и�
        compress(n / 2, y + n / 2, x);            // 3��и�
        compress(n / 2, y + n / 2, x + n / 2); // 4��и�
        return;
    }

    private static boolean isAllFlase(boolean zero, boolean one){
        return !zero && !one;
    }

    private static int countNumber(String text, int number){
        return Arrays.stream(text.split("")).filter(i->i.equals(String.valueOf(number))).toArray().length;
    }
}

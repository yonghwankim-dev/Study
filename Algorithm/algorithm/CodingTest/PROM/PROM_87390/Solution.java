package PROM.PROM_87390;

class Solution {
    public static int[] solution(int n, long left, long right) {
        int[] answer = {};

        // n*n �迭 ����
        int[][] arr = new int[n][n];
        // ���� ä���
        fillNumber(arr, n);
        // arr[left] ~ arr[right]�� ������ �迭�� �߶󳻱�
        return sliceArray(arr, left, right, n);
    }

    private static void fillNumber(int[][] arr, int n){
        int i = 1;
        while(i <= n){
            for(int row = 0; row < i; row++){
                for(int col = 0; col < i; col++){
                    if(arr[row][col] == 0){
                        arr[row][col] = i;
                    }
                }
            }
            i++;
        }
    }

    private static int[] sliceArray(int[][] arr, long left, long right, int n){
        int start = (int) (right - left) - 1;
//        arr[start/n][start%n] ~ arr[start%n][start/n]
        int[] result = new int[start+2];
        int i = 0;
        for(int row = start/n; row <= start%n; row++){
            for(int col = start%n; col <= start/n; col++){
                result[i] = arr[row][col];
                i++;
            }
        }
        return result;
    }


}

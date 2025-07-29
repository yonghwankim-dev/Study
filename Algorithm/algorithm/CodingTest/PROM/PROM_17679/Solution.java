package PROM.PROM_17679;

class Solution {
    // m : ����
    // n : �ʺ�
    private static int m, n;
    public static int solution(int m, int n, String[] board) {
        int answer = 0;
        Solution.m = m;
        Solution.n = n;
        String[][] matrix, conversion, reversion;

        // matrix �ʱ�ȭ
        matrix = createMatrix(board);

        // �࿭��ȯ
        conversion = createConversionMatrix(matrix);

        // �ݴ�� ������
        reversion = createReversionMatrix(conversion);

        return answer;
    }

    private static String[][] createMatrix(String[] board){
        String[][] matrix = new String[m][n];
        for(int i = 0; i < m; i++){
            matrix[i] = board[i].split("");
        }
        return matrix;
    }

    private static String[][] createConversionMatrix(String[][] matrix){
        String[][] conversion = new String[n][m];
        for(int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                conversion[j][i] = matrix[i][j];
            }
        }
        return conversion;
    }

    private static String[][] createReversionMatrix(String[][] conversion){
        String[][] reversion = new String[n][m];
        for(int i = 0; i < conversion.length; i++){
            reversion[i] = reverseArray(conversion[i]);
        }
        return reversion;
    }

    private static String[] reverseArray(String[] arr){
        String[] result = arr.clone();
        int len = result.length;
        for(int i = 0; i < len / 2; i++){
            swap(result, i, len - i - 1);
        }
        return result;
    }

    private static void swap(String[] arr, int from, int to){
        String temp = arr[from];
        arr[from]   = arr[to];
        arr[to]     = temp;
    }


}

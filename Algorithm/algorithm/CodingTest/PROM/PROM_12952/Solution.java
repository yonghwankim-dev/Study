package PROM.PROM_12952;

class Solution {
    static int answer;
    static int n;
    static int[] board;
    public static int solution(int n) {
        Solution.n = n;
        Solution.board = new int[n];
        Solution.answer = 0;

        nQueen(0);
        return answer;
    }

    private static void nQueen(int y1){
        // y�� ����������� �����ϰ� ���Ⱑ���� ����, Ž�� �Ϸ�
        if(y1 == n){
            answer++;
            return;
        }

        for(int x = 0; x < n; x++){
            board[y1] = x; // y : ��, board[y] = i : ��

            if(promising(y1)){
                nQueen(y1 + 1); // ���� ������ �̵�
            }
        }
    }

    private static boolean promising(int y1){
        // ���� ���̸� �ȵǰ�, �밢���� �־ �ȵȴ�
        for(int y2 = 0; y2 < y1; y2++){
            if(isEqualCol(y1, y2) || isEqualDiagonal(y1, y2)){
                return false;
            }
        }
        return true;
    }

    // ���� ���� �ִ��� Ȯ��
    private static boolean isEqualCol(int y1, int y2){
        return board[y1] == board[y2];
    }

    // �밢���� �ִ��� Ȯ��
    // �밢�� ���� : �� ��((y1,x1), (y2,x)) ������ ���� ���̿� ���� ���̰� ������ ���
    // ���� ���� : abs(y1 - y2)
    // ���� ���� : abs(x1 - x2)
    // param y1, y2 : ��
    // board[y1], board[y2] : ��
    private static boolean isEqualDiagonal(int y1, int y2){
        return Math.abs(y1 - y2) == Math.abs(board[y1] - board[y2]);
    }

}

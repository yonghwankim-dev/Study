package PROM.PROM_12952;

// nQueen ������ DFS ������� �ذ�����
class Solution2 {
    static int answer;
    static int n;
    static int[] board;

    public static int solution(int n){
        Solution2.answer = 0;
        Solution2.n = n;
        Solution2.board = new int[n];

        nQueen(0, 0);
        return answer;
    }

    private static void nQueen(int y1, int count){

        if(y1 == n && count == n){
            answer++;
            return;
        }else if(y1 == n){
            return;
        }

        for(int x = 0; x < n; x++){
            board[y1] = x;

            if(promising(y1)){
                nQueen(y1 + 1, count + 1);
                continue;
            }
            nQueen(y1 + 1, count);
        }
    }

    private static boolean promising(int y1){
        for(int y2 = 0; y2 < y1; y2++){
            if(isEqualCol(y1, y2) || isEqualDiagonal(y1, y2)){
                return false;
            }
        }
        return true;
    }

    private static boolean isEqualCol(int y1, int y2){
        return board[y1] == board[y2];
    }

    private static boolean isEqualDiagonal(int y1, int y2){
        return Math.abs(y1 - y2) == Math.abs(board[y1] - board[y2]);
    }
}

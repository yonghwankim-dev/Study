package PROM.PROM_120866;

class Solution {
    private static final int SAFE = 0;
    private static final int MINE = 1;
    private static final int DANGER = 2;
    private int n;
    private int[][] board;

    public int solution(int[][] board) {
        int answer;
        n = board.length;
        this.board = copyBoard(board);

        iterateRow();
        answer = countingSafe();

        return answer;
    }

    private int[][] copyBoard(int[][] board){
        int[][] result = new int[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                result[i][j] = board[i][j];
            }
        }
        return result;
    }

    private void iterateRow(){
        for(int y = 0; y < n; y++){
            iterateColumnByRow(y);
        }
    }

    private void iterateColumnByRow(int y){
        for(int x = 0; x < n; x++){
            checkPosition(y, x);
        }
    }

    private void checkPosition(int y, int x){
        if(isMine(y, x)){
            changeAdjacentSafeZoneToDangerZone(y, x);
        }
    }

    private boolean isMine(int y, int x){
        return board[y][x] == MINE;
    }

    private void changeAdjacentSafeZoneToDangerZone(int y, int x) {
        final int TOP = y - 1;
        final int BOTTOM = y + 1;
        final int LEFT = x - 1;
        final int RIGHT = x + 1;

        for(int i = TOP; i <= BOTTOM; i++){
            iterateLeftToRight(i, LEFT, RIGHT);
        }
    }

    private void iterateLeftToRight(int y, int start, int end){
        for(int x = start; x <= end; x++){
            checkIsPossibleChangeToDanger(y, x);
        }
    }

    private void checkIsPossibleChangeToDanger(int y, int x){
        if(isChangeToDanger(y, x)){
            board[y][x] = DANGER;
        }
    }

    private boolean isChangeToDanger(int y, int x){
        return isInside(y, x) && isSafe(y, x);
    }

    private boolean isInside(int y, int x){
        return y >= 0 &&
                y < n &&
                x >= 0 &&
                x < n;
    }

    private boolean isSafe(int y, int x){
        return board[y][x] == SAFE;
    }

    private int countingSafe() {
        int result = 0;
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                if(board[y][x] == SAFE){
                    result++;
                }
            }
        }
        return result;
    }
}

package PROM.PROM_DEV.problem3;

class Solution {
    private static int[][] board;
    private static int n;
    private static final int MINE = -1;

    // ����, ��, ���, ��, �Ͽ�, ��, ����, ��
    private static int[][] directions = {{-1,-1}, {-1,0}, {-1,1}, {0,1}, {1,1}, {1,0}, {1,-1}, {0,-1}};
    private static boolean[][] visited;

    public static int solution(int N, int[][] mine, int[] P) {
        n = N;
        board = new int[N + 1][N + 1];
        visited = new boolean[N + 1][N + 1];
        int answer; // P ��ǥ�� ���� ���������� ������ ��ĭ�� ����

        // ���� ����
        initMine(mine);

        // ���� �ֺ� ���� ����
        createNumberAroundMine();

        // P ��ǥ�� ���� ��� ��ĭ ����
        answer = open(P);
        
        return answer;
    }

    private static void initMine(int[][] mine){
        for(int[] m : mine){
            int y = m[0];
            int x = m[1];
            board[y][x] = MINE;
        }
    }


    private static void createNumberAroundMine() {
        for(int y = 1; y <= n; y++){
            for(int x = 1; x <= n; x++){
                if(isMine(y,x)){ // ����
                    updateNumberAroundMine(y, x);
                }
            }
        }
    }

    // ����(Y,X) �ֺ��� ���� ����
    private static void updateNumberAroundMine(int y, int x) {
        for(int[] direction : directions){
            int next_y = y + direction[0];
            int next_x = x + direction[1];
            // ������ �����ȿ� �ְ� �Ǵٸ� ���ڰ� �ƴѰ�쿡�� ���� ����
            if(isInside(next_y, next_x) &&
                !isMine(next_y, next_x)){
                board[next_y][next_x]++;
            }
        }
    }

    private static boolean isInside(int y, int x) {
        if(y <= 0 || y > n || x <= 0 || x > n){
            return false;
        }
        return true;
    }

    private static int open(int[] p){
        return open(p, 0);
    }

    private static int open(int[] p, int openCount) {
        openCount++;
        int y = p[0];
        int x = p[1];
        boolean isExistMine = false;
        visited[y][x] = true;

        // (y,x) �ֺ��� ���ڰ� �ִ��� �˻�
        for(int[] direction : directions){
            int next_y = y + direction[0];
            int next_x = x + direction[1];

            if(isInside(next_y, next_x) &&
               isMine(next_y, next_x)){
                return openCount;
            }
        }

        // (y,x) �ֺ��� ���ڰ� ���� ��� �ֺ�ĭ�� ���Ͽ� �ٽ� Ž��
        if(!isExistMine){
            for(int[] direction : directions){
                int next_y = y + direction[0];
                int next_x = x + direction[1];

                if(isInside(next_y, next_x) &&
                   !isMine(next_y, next_x) &&
                   !isVisited(next_y, next_x)){
                    openCount = open(new int[]{next_y, next_x}, openCount);
                }
            }
        }
        return openCount;
    }

    private static boolean isMine(int y, int x) {
        return board[y][x] == MINE;
    }

    private static boolean isVisited(int y, int x){
        return visited[y][x];
    }


}

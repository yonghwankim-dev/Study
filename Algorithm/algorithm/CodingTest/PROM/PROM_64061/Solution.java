package PROM.PROM_64061;

import java.util.ArrayList;
import java.util.List;

class Solution {

    private static boolean isDoll(int val){
        return val != 0;
    }

    private static void processCrain(int[][] board, List<Integer> bucket, int col){
        int pointVal = 0;

        for (int row = 0; row < board.length; row++) {
            pointVal = board[row][col];
            if (isDoll(pointVal)) {
                bucket.add(pointVal);
                board[row][col] = 0;
                break;
            }
        }
    }

    private static boolean isPossibleRemoveAble(List<Integer> bucket){
        int lastIdx = bucket.size() - 1;
        int lastPrevIdx = bucket.size() - 2;
        if(bucket.size() >= 2 && (bucket.get(lastIdx) == bucket.get(lastPrevIdx))){
            return true;
        }
        return false;
    }

    private static void removeTwoDoll(List<Integer> bucket){
        bucket.remove(bucket.size() - 1);
        bucket.remove(bucket.size() - 1);
    }

    private static int countDoll(int count){
        return count + 2;
    }

    public static int solution(int[][] board, int[] moves) {
        int answer = 0;
        int moveCol = 0;
        List<Integer> bucket = new ArrayList<>();

        for (int move : moves) {
            moveCol = move - 1;

            processCrain(board, bucket, moveCol);

            if(isPossibleRemoveAble(bucket)){
                answer = countDoll(answer);
                removeTwoDoll(bucket);
            }
        }

        return answer;
    }
}

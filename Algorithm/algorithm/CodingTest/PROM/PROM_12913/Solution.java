package PROM.PROM_12913;

import java.util.Arrays;

class Solution {
    public static int solution(int[][] land) {
        int lastRow = land.length - 1;
        for(int row = 1; row < land.length; row++){
            for(int col = 0; col < land[row].length; col++){
                int[] prevLand = land[row-1];
                land[row][col] += findMaxVal(prevLand, col);
            }
        }
        return Arrays.stream(land[lastRow]).max().getAsInt();
    }

    private static int findMaxVal(int[] line, int curCol){
        int max = 0;
        for(int i = 0; i < line.length; i++){
            if(isGreaterThanMax(line[i], max) &&
               isDiffCol(i, curCol)){
                max = line[i];
            }
        }
        return max;
    }

    private static boolean isGreaterThanMax(int score, int max){
        return score > max;
    }

    private static boolean isDiffCol(int col1, int col2){
        return col1 != col2;
    }
}

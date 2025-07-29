package PROM.PROM_42748;

import java.util.Arrays;

class Solution {
    public static int[] solution(int[] array, int[][] commands) {
        int[] answer = new int[commands.length];
        int[] subArray;
        int start, end, idx, i=0;

        for(int[] command : commands){
            start = command[0] - 1;
            end   = command[1] - 1;
            idx   = command[2] - 1;
            subArray = toSubArray(array, start, end);
            Arrays.sort(subArray);
            answer[i++] = subArray[idx];
        }

        return answer;
    }

    private static int[] toSubArray(int[] array, int from, int to){
        int n = to - from + 1;
        int[] result = new int[n];
        for(int i = from, k = 0; i <= to; i++, k++){
            result[k] = array[i];
        }
        return result;
    }

}

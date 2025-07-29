package PROM.PROM_131705;

import java.util.Arrays;

class Solution {
    private static int[] number;
    private static int n;
    private static boolean[] visited;
    private static int r;
    public static int solution(int[] number){
        Solution.number = number;
        Solution.n = number.length;
        Solution.visited = new boolean[n];
        Solution.r = 3;

        int answer = combination(0, 0);
        return answer;
    }

    private static int combination(int index, int count){
        int answer = 0;

        if(count == r){
            return isTrio(getSum(getVisitedStudentArray())) ? 1 : 0;
        }

        for(int i = index; i < n; i++){
            if(visited[i]){
                continue;
            }

            visited[i] = true;
            answer += combination(i, count + 1);
            visited[i] = false;
        }

        return answer;
    }

    private static int[] getVisitedStudentArray() {
        int[] result = new int[r];

        for(int i = 0, position = 0; i < n; i++){
            if(visited[i]){
                result[position++] = number[i];
            }
        }

        return result;
    }

    private static int getSum(int[] visitedStudentArray) {
        return Arrays.stream(visitedStudentArray).sum();
    }

    private static boolean isTrio(int num){
        return num == 0;
    }

}

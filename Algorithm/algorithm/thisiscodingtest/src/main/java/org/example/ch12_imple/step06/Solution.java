package org.example.ch12_imple.step06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class Solution {
    
    static final int POLE = 0;
    static final int ROOF = 1;

    public int[][] solution(int n, int[][] build_frame) {
        List<int[]> answer = new ArrayList<>();
        
        for(int[] frame : build_frame){
            int x = frame[0];
            int y = frame[1];
            int a = frame[2];
            int b = frame[3];
            int[] target = new int[]{x, y, a};
            
            if(b == 0){
                delete(answer, target);

                if (!checkBuild(answer)){
                    answer.add(target);
                }
            }else{
                answer.add(target);

                if(!checkBuild(answer)){
                    delete(answer, target);
                }
            }
        }

        answer.sort(Comparator.comparingInt((int[] o) -> o[0])
            .thenComparingInt(o -> ((int[])o)[1])
            .thenComparing(o->((int[])o)[2]));
        return answer.toArray(new int[answer.size()][]);
    }

    private void delete(List<int[]> answer, int[] target) {
        for (int[] frame : answer){
            if (Arrays.equals(frame, target)){
                answer.remove(frame);
                break;
            }
        }
    }

    private boolean checkBuild(List<int[]> answer) {
        for (int[] frame : answer){
            int x = frame[0];
            int y = frame[1];
            int a = frame[2];

            if(a == POLE){
                // 좌표가 바닥에 있지 않은 경우
                // 좌표 아래에 기둥이 존재하지 않은 경우
                // 보의 한쪽 위에 존재하지 않은 경우
                if (y != 0 &&
                    !contains(answer, new int[]{x, y-1, 0}) &&
                    !contains(answer, new int[]{x-1, y, 1}) &&
                    !contains(answer, new int[]{x, y, 1})){
                    return false;
                }
            }else if(a == ROOF){
                // 좌표 아래에 기둥이 존재하지 않은 경우
                // 좌표 양쪽에 보가 존재하지 않은 경우
                if(!contains(answer, new int[]{x, y-1, 0}) &&
                    !contains(answer, new int[]{x+1, y-1, 0}) &&
                    (!contains(answer, new int[]{x-1, y, 1}) ||
                        !contains(answer, new int[]{x+1, y, 1}))){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean contains(List<int[]> answer, int[] target) {
        for(int[] frame : answer){
            if(Arrays.equals(frame, target)){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[][] build_frame = new int[][]{
            {1, 0, 0, 1},
            {1, 1, 1, 1},
            {2, 1, 0, 1},
            {2, 2, 1, 1},
            {5, 0, 0, 1},
            {5, 1, 0, 1},
            {4, 2, 1, 1},
            {3, 2, 1, 1}
        };
        int[][] result = solution.solution(5, build_frame);
        Arrays.stream(result)
            .forEach(r -> System.out.println(Arrays.toString(r)));
    }
}

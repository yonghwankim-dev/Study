package PROM.PROM_135808;

import java.util.Arrays;

class Solution {
    public int solution(int k, int m, int[] score) {
        Arrays.sort(score);
        int maxBoxLength = score.length / m;
        int[] box = new int[maxBoxLength];
        int idx = 0;
        int min = 10;
        // k=3, m =4, score = [1, 1, 1, 2, 2, 3, 3]
        for(int i = score.length - 1; i >= m-1; i -= m){
            for(int j = i; j > i - m; j--){
                min = Math.min(min, score[j]);
            }
            box[idx++] = min * m;
        }

        return Arrays.stream(box).sum();
    }

}

package PROM.PROM_138477;

import java.util.PriorityQueue;

class Solution {
    public int[] solution(int k, int[] score) {
        int[] answer = new int[score.length];
        PriorityQueue<Integer> hallOfFame = new PriorityQueue<>();
        int i = 0;
        for(int s : score){
            hallOfFame.add(s);
            aggregateHallOfFame(hallOfFame, k);
            answer[i++] = hallOfFame.peek();
        }
        return answer;
    }

    private void aggregateHallOfFame(PriorityQueue<Integer> pq, int k){
        if(pq.size() > k){
            pq.poll();
        }
    }
}

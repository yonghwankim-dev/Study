package PROM.PROM_138476;

import java.util.*;

class Solution {
    public int solution(int k, int[] tangerine) {
        int answer = 0;
        Map<Integer, Integer> counter = new HashMap();


        for(int i = 0; i < tangerine.length; i++){
            counter.put(tangerine[i], counter.getOrDefault(tangerine[i], 0) + 1);
        }

        int idx = 0;
        Map.Entry<Integer,Integer>[] arr = new Map.Entry[counter.size()];
        for(Map.Entry<Integer, Integer> entry : counter.entrySet()){
            arr[idx++] = entry;
        }

        Arrays.sort(arr, (o1, o2) -> o2.getValue() - o1.getValue());

        for(Map.Entry<Integer, Integer> entry : arr){
            k -= entry.getValue();
            answer++;
            if(k<=0){
                break;
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        int solution = new Solution().solution(6, new int[]{1, 3, 2, 5, 4, 5, 2, 3});
        System.out.println(solution);
    }
}

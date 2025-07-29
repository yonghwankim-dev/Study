package PROM.PROM_120812;

import java.util.HashMap;
import java.util.Map;

public class Solution {
    private static Map<Integer, Integer> counter;

    public static int solution(int[] array) {
        int answer = 0;
        int count = 0;

        counter = countArray(array);
        int maxModeVal = findMaxModeVal();

        for (int key : counter.keySet()) {
            if (maxModeVal == counter.get(key)) {
                count++;
                answer = key;
            }
        }

        return count >= 2 ? -1 : answer;
    }

    private static Map<Integer, Integer> countArray(int[] array){
        Map<Integer, Integer> counter = new HashMap<>();

        for(int element : array){
            counter.put(element, counter.getOrDefault(element, 0) + 1);
        }
        return counter;
    }

    private static int findMaxModeVal(){
        int maxModeVal = 0;
        for(int key : counter.keySet()){
            if(maxModeVal < counter.get(key)){
                maxModeVal = counter.get(key);
            }
        }
        return maxModeVal;
    }

}

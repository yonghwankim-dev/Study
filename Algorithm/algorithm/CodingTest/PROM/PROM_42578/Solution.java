package PROM.PROM_42578;

import java.util.HashMap;
import java.util.Map;

class Solution {
    public static int solution(String[][] clothes){
        int answer = 1;
        Map<String, Integer> map = new HashMap<>();

        for(String[] clothe : clothes){
            map.put(clothe[1], map.getOrDefault(clothe[1],0) + 1);
        }
        for(String category : map.keySet()){
            answer *= (map.get(category)+1);
        }

        return answer - 1;
    }
}

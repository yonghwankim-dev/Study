package PROM.PROM_131128;

import java.util.*;
import java.util.stream.Collectors;

class Solution {
    public static String solution(String X, String Y){
        Map<String, Integer> xCounterMap = new HashMap<>();
        Map<String, Integer> yCounterMap = new HashMap<>();

        for(String key : X.split("")){
            xCounterMap.put(key, xCounterMap.getOrDefault(key, 0) + 1);
        }

        for(String key : Y.split("")){
            yCounterMap.put(key, yCounterMap.getOrDefault(key, 0) + 1);
        }

        List<Integer> common = new ArrayList<>();
        for(String key : xCounterMap.keySet()){
            if(!yCounterMap.containsKey(key)){
                continue;
            }
            int minLen = Math.min(xCounterMap.get(key), yCounterMap.get(key));
            for(int i = 0; i < minLen; i++){
                common.add(Integer.parseInt(key));
            }
        }
        Collections.reverse(common);

        String answer = common.stream().map(String::valueOf).collect(Collectors.joining());

        if(answer.isEmpty()){
            return "-1";
        }
        if(answer.replaceAll("0","").isEmpty()){
            return "0";
        }
        return answer;
    }
}

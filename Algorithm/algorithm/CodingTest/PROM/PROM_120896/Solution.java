package PROM.PROM_120896;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

class Solution {
    public String solution(String s) {
        String answer = "";
        String[] split = s.split("");
        Map<String, Integer> counter = new HashMap<>();

        for(int i = 0; i < split.length; i++){
            counter.put(split[i], counter.getOrDefault(split[i], 0) + 1);
        }

        for(String key : counter.keySet()){
            if(counter.get(key) == 1){
                answer += key;
            }
        }

        split = answer.split("");
        Arrays.sort(split);
        answer = Arrays.stream(split).collect(Collectors.joining());

        return answer;
    }

    public String otherSolution(String s) {
        return Arrays.stream(s.split(""))
                .collect(Collectors.groupingBy(s1 -> s1))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().size() <= 1)
                .map(Map.Entry::getKey)
                .sorted()
                .collect(Collectors.joining());
    }
}

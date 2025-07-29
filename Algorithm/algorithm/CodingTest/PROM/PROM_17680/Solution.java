package PROM.PROM_17680;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public int solution(int cacheSize, String[] cities) {
        int answer = 0;
        final int cacheHit = 1;
        final int cacheMiss = 5;

        List<String> queue = new ArrayList<>();

        for(String city : cities){
            city = city.toUpperCase();
            if(queue.contains(city)){
                answer += cacheHit;
                queue.remove(city);
                queue.add(city);
                continue;
            }else{
                answer += cacheMiss;
                if(queue.size() == cacheSize && cacheSize != 0){
                    queue.remove(0);
                }

                if(cacheSize != 0){
                    queue.add(city);
                }
            }
        }

        return answer;
    }
}

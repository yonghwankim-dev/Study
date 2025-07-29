package PROM.PROM_118666;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Solution {
    private static Map<String,Integer> map; // key : ��ǥ��, value : �����հ�
    private static final String[] category = {"R","T","C","F","J","M","A","N"};
    private static final int[] scores = {3,2,1,0,1,2,3};
    private static final String[] indicators = {"RT","CF","JM","AN"};
    public static String solution(String[] survey, int[] choices) {
        String answer = "";
        map = new HashMap<>();
        Arrays.stream(category).forEach(key -> map.put(key,0));

        for(int i = 0; i < survey.length; i++){
            process(survey[i], choices[i]);
        }

        answer = cal();

        return answer;
    }

    private static void process(String s, int choice){
        String[] info = s.split("");
        String first = info[0];
        String second = info[1];
        int score = scores[choice-1];

        if(choice < 4){
            map.put(first,map.get(first)+score);
        }else if(choice > 4){
            map.put(second,map.get(second)+score);
        }
    }

    private static String cal(){
        StringBuilder sb = new StringBuilder();
        for(String indicator : indicators){
            String[] info = indicator.split("");
            String first = info[0];
            String second = info[1];

            if(map.get(first) > map.get(second)){
                sb.append(first);
            }else if(map.get(first) < map.get(second)){
                sb.append(second);
            }else{
                if(first.compareTo(second)<0){
                    sb.append(first);
                }else{
                    sb.append(second);
                }
            }
        }
        return sb.toString();
    }
}

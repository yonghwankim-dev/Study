package PROM.PROM_64065;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class Solution {
    public int[] solution(String s) {
        int[] answer = {};
        int len = s.length();
        String sub = s.substring(1, len-1);
        Map<Integer, Integer> counter = new HashMap<>();
        String num = "";
        for(int i = 0; i < sub.length(); i++){
            if(Character.isDigit(sub.charAt(i))){
                num += sub.charAt(i);
            }else if((sub.charAt(i) == ',' || sub.charAt(i) == '}') && !num.equals("")){
                int target = Integer.parseInt(num);
                int cnt  = counter.getOrDefault(target, 1);
                counter.put(target, cnt+1);
                num = "";
            }
        }

        answer = new int[counter.size()];

        // 부분집합에서 가장 빈번히 나온 숫자의 내림차순 탐색하여 저장
        int i = 0;
        while(counter.size() != 0){
            int max_key = 0;
            int max = 0;

            // 가장 큰 value를 가진 key를 탐색합니다.
            for(Integer key : counter.keySet()){
                if(counter.get(key) > max){
                    max = counter.get(key);
                    max_key = key;
                }
            }

            answer[i++] = max_key;
            counter.remove(max_key);
        }

        return answer;
    }

    @Test
    void test1(){
        String s = "{{2},{2,1},{2,1,3},{2,1,3,4}}";
        int[] answer = new Solution().solution(s);

        Assert.assertArrayEquals(new int[]{2, 1, 3, 4}, answer);

    }

    @Test
    void test2(){
        String s = "{{20,111},{111}}";

        int[] answer = new Solution().solution(s);
        Assert.assertArrayEquals(new int[]{111, 20}, answer);
    }
    

}

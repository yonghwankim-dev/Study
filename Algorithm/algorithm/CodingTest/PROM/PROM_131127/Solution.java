package PROM.PROM_131127;

import java.util.HashMap;
import java.util.Map;

public class Solution {
    public int solution(String[] want, int[] number, String[] discount) {
        int answer = 0;
        Map<String, Integer> wantMap = createWantMap(want, number);
        Map<String, Integer> discountMap = createDiscountMap(discount);

        answer = canSignUpMember(wantMap, discountMap) ? answer + 1 : answer;

        for(int i = 10; i < discount.length; i++){
            adjustmentDiscount(discountMap, discount[i - 10]);
            addItem(discountMap, discount[i]);
            answer = canSignUpMember(wantMap, discountMap) ? answer + 1 : answer;
        }

        return answer;
    }

    private void addItem(Map<String, Integer> discountMap, String item){
        discountMap.put(item, discountMap.getOrDefault(item, 0) + 1);
    }

    private void adjustmentDiscount(Map<String, Integer> discountMap, String item){
        if(discountMap.get(item) > 1){
            discountMap.put(item, discountMap.get(item) - 1);
            return;
        }
        discountMap.remove(item);
    }

    private Map<String, Integer> createWantMap(String[] want, int[] number){
        Map<String, Integer> wantMap = new HashMap<>();
        for(int i = 0; i < want.length; i++){
            wantMap.put(want[i], wantMap.getOrDefault(want[i], 0) + number[i]);
        }
        return wantMap;
    }

    private Map<String, Integer> createDiscountMap(String[] discount){
        Map<String, Integer> discountMap = new HashMap<>();

        for(int i = 0; i < 10; i++){
            discountMap.put(discount[i], discountMap.getOrDefault(discount[i], 0) + 1);
        }
        return discountMap;
    }

    private boolean canSignUpMember(Map<String, Integer> wantMap, Map<String, Integer> discountMap){
        for(String key : wantMap.keySet()){
            int wantNumber = wantMap.get(key);
            if(!discountMap.containsKey(key) || discountMap.get(key) < wantNumber){
                return false;
            }
        }
        return true;
    }
}

package PROM.PROM_42583;

import java.util.LinkedList;
import java.util.Queue;

public class Solution {

    public int solution(int bridge_length, int weight, int[] truck_weights) {
    	int answer = 0; // 모든 트럭이 다리를 통과하는데 경과된 시간
    	int sum    = 0; // 현재 다리에 있는 트럭들의 무게합
    	
    	Queue<Integer> trucksOnBridge = new LinkedList<Integer>(); // 다리를 건너는 트럭들
    	
    	for(int t : truck_weights) {
    		while(true) {
    			// 1. 다리 위에 트럭이 없는 경우
    			if(trucksOnBridge.isEmpty()) {
    				trucksOnBridge.add(t);
    				sum += t;
    				answer++; // 시간 경과
    				break;
    			}else if(trucksOnBridge.size() == bridge_length) { // 큐가 가득찬 경우
    				int exitedTruck = trucksOnBridge.poll();
    				sum -= exitedTruck;
    			}else {
    				// 다음 틀럭을 다리에 올릴 수 있는지 검사
        			if(t + sum > weight) { // 다리위에 트럭을 올릴 수 없는 경우
        				trucksOnBridge.add(0);
        				answer++;
        			}else { // 다리 위에 트럭을 올릴 수 있는 경우
        				trucksOnBridge.add(t);
        				sum += t;
        				answer++;
        				break;
        			}    				
    			}    			
    		}
    	}
    	answer = answer + bridge_length;
    	
        return answer;
    }
}

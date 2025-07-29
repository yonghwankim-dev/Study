package PROM.PROM_42747;

import java.util.Arrays;

public class Solution {
	
	
    public int solution(int[] citations) {
        int hIndex = 0;
        int n      = citations.length; // 어떤 과학자의 발표한 논무 개수
        int h      = 0; 			   
        
        // 1. 오름차순 정렬
        Arrays.sort(citations);
        
        // 2. 각각의 논문 인용 횟수 값이 h보다 크거나 같은지 검사
        for(int i = 0; i < n; i++) {
        	h = n - i; // citations[i]에 해당하는 수의 이상만큼 인용한 논문의 개수
        	
        	// 해당 논문이 h번 이상 이용되었는지 검사
        	if(citations[i] >= h) {
        		hIndex = h;
        		break;
        	}
        }
        
        return hIndex;
    }
}

package PROM.PROM_12985;

public class Solution {
	
	
	public boolean isFight(int a, int b) {
		return (a + 1) / 2 == (b + 1) / 2 ? true : false;
	}
	
    public int solution(int n, int a, int b)
    {
        int answer = 1;
        
        while(true) {
        	if(isFight(a, b)) {
        		break;
        	}
        	a = (a+1)/2;
        	b = (b+1)/2;
        	answer++;
        }
        
        return answer;
    }    
}

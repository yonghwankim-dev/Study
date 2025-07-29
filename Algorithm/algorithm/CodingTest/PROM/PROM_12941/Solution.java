package PROM.PROM_12941;

import java.util.Arrays;

public class Solution
{
    public int solution(int []A, int []B)
    {
        int answer = 0;
        int n = A.length;
        
        Arrays.sort(A);
        Arrays.sort(B);
        
        
        for(int i = 0; i < n; i++) {
        	answer += (A[i]*B[n-i-1]);
        }
        
        return answer;
    }
    
    public static void main(String[] args) {
    	int answer = new Solution().solution(new int[] {1, 4, 2}, new int[] {5, 4, 4});
    	System.out.println(answer);
    }
}

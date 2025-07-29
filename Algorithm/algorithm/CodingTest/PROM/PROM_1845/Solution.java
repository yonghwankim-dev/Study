package PROM.PROM_1845;

import java.util.ArrayList;
import java.util.List;

public class Solution{
	
    public static int solution(int[] nums) {
        int answer = 0;
        
        int a = nums.length/2;	// �������� �ִ� ���ϸ� ��
        
        int b = 0;	// �ߺ����� ���� ���ϸ� ��
        List<Integer> list = new ArrayList<>();
        
        for(int num : nums)
        {
        	if(!list.contains(num)) 
        	{
        		list.add(num);
        		b++;
        	}
        }
        
        if(a<=b)
        {
        	answer = a;
        }
        else
        {
        	answer = b;
        }
        
        
        return answer;
    }
    
	public static void main(String args[])
	{
		int[] nums = {3,1,2,3};
		System.out.println(solution(nums));
	}
}

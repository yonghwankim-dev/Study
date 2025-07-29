package PROM.PROM_12948;



public class Solution {

    public static int solution(int n) 
    {
    	String s = String.valueOf(n);
    	
    	int sum = 0;
    	
    	for(int i=0;i<s.length();i++)
    	{
    		sum += Integer.parseInt(s.charAt(i)+"");
    	}
    	return sum;
    	
    }
	public static void main(String[] args)
	{
		System.out.println(solution(123));
	}
}

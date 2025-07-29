package PROM.PROM_12910;



public class Solution{
	
	public static boolean isNumber(String s)
	{
		for(int i=0;i<s.length();i++)
		{
			if(!(s.charAt(i)>=48 && s.charAt(i)<=57))
			{
				return false;
			}
		}
		return true;
	}
	
    public static boolean solution(String s) {
        boolean answer = false;
        
        if((s.length()==4 || s.length()==6) && isNumber(s))
        {
        	answer = true;
        }
        
        return answer;
    }
    
    public static boolean solution2(String s)
    {
    	return s.matches("(^[0-9]{4}|{6}$)");
    }
    
	public static void main(String args[])
	{
		System.out.println(solution2("a123"));
		System.out.println(solution2("1234"));
	}
}

package PROM.PROM_12916;

public class Solution {
    
	public static boolean solution(String s)
	{
		String str = s.toLowerCase();
		int p_num = 0;
		int y_num = 0;
		
		char[] chars = str.toCharArray();
		
		for(int i=0;i<chars.length;i++)
		{
			if(chars[i]=='p')
			{
				p_num++;
			}
			if(chars[i]=='y')
			{
				y_num++;
			}
		}
			
		return p_num==y_num ? true : false;
	}
	

    public static void main(String[] args)
    {
    	
    }
}

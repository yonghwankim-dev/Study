package PROM.PROM_12926;



public class Solution {	
    public static String solution(String s, int n) {
        
        char[] chars = s.toCharArray();
        for(int i=0;i<chars.length;i++)
        {
        	if(chars[i]==' ')
        	{
        		continue;
        	}
        	else
        	{
        		char c = chars[i];
        		char cae = ' ';
        		if(c>='A' && c<='Z')
        		{
        			cae = (char)(c+n);
        			if(cae>'Z')
        			{
        				cae = (char)(65 + (n-(90-c)-1));
        			}
        		}
        		else if(c>='a' && c<='z')
        		{	
        			cae = (char)(c+n);
        			
        			if(cae>'z')
        			{
        				cae = (char)(97 + (n-(122-c)-1));
        			}
        		}
        		chars[i] = cae;
        	}
        	
        }
        return String.valueOf(chars);
    }
    
	public static void main(String[] args)
	{
		System.out.println(solution("AB",1));
		System.out.println(solution("Z",1));
		System.out.println(solution("a B z",4));
	}
}

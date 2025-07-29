package PROM.PROM_28219;

public class Solution2{
	public static int getXCount(String s)
	{
		int count = 0;
		for(int i=0;i<s.length();i++)
		{
			if(s.charAt(i)=='a')
			{
				count++;
			}
		}
		return count;
	}
	
    public static boolean[] solution(String[] a) {
        boolean[] answer = {};
        StringBuilder sb = new StringBuilder("a");
        
        for(String s : a)
        {
        	int x = getXCount(s);
        	String dummy = sb.toString();
        	for(int i=0;i<x;i++)
        	{
        		dummy = "b" + dummy + "b";
        	}
        	System.out.println(dummy);
        }
        return answer;
    }

	public static void main(String args[])
	{
		String[] a = {"abab","bbaa","bababa","bbbabababbbaa"};
		solution(a);
	}
}

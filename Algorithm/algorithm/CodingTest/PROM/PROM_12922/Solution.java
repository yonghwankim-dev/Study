package PROM.PROM_12922;


public class Solution {

	public static String copy(String s, int n)
	{
		StringBuilder sb = new StringBuilder();
		
		for(int i=0;i<n;i++)
		{
			sb.append(s);
		}
		return sb.toString();
	}
    public static String solution(int n) {
    	StringBuilder sb = new StringBuilder();
    
    	sb.append(copy("수박", n/2));
    	if(n%2==1)
    	{
    		sb.append("수");
    	}
    	
        return sb.toString();
    }
    public String solution2(int n){
        return new String(new char [n/2+1]).replace("\0", "수박").substring(0,n);
    }

	public static void main(String args[]) {
		for(int i=0;i<10;i++)
		{
			System.out.println(solution(i));
		}
		
	}
}

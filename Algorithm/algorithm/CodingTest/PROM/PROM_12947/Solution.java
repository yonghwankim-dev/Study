package PROM.PROM_12947;



public class Solution {

	public static int getEachNumberSum(int x)
	{
		String s = String.valueOf(x);
		int sum=0;
		for(int i=0;i<s.length();i++)
		{
			sum += Integer.parseInt(String.valueOf(s.charAt(i)));
		}
		return sum;
	}
	
    public static boolean solution(int x) {        
        int sum = getEachNumberSum(x);
        return x%sum==0 ? true : false;
    }
	public static void main(String[] args)
	{
		System.out.println(solution(10));
	}
}

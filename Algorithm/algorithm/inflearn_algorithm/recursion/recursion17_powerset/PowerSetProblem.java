package recursion.recursion17_powerset;

public class PowerSetProblem {
	private static String data = "abc";
	
	public static void powerSet(String S)
	{
		if(S.length()==0)
		{
			return;
		}
		else
		{
			String t = String.valueOf(S.charAt(0));
			S = S.substring(1);
			powerSet(S);
			System.out.println(S);
			System.out.println(t+S);
		}
	}
	
	public static void main(String[] args)
	{
		powerSet(data);
	}
}

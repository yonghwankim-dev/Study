package recursion.recursion07_printcharsreverse;

public class Main {

	// 문자열을 뒤집어서 프린트
	public static void printCharsReverse(String s) 
	{
		if (s.equals("")) 
		{
			return;
		}
		else
		{
			printCharsReverse(s.substring(1));
			System.out.print(s.charAt(0));
		}
	}

	public static void main(String[] args) {
		printCharsReverse("abcde"); // Expected Output : edcba
	}

}

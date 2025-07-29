package recursion.recursion06_printchars;

public class Main {

	// ���ڿ��� ����Ʈ
	public static void printChars(String s) 
	{
		if (s.equals("")) 
		{
			System.out.println();
			return;
		}
		else 
		{
			System.out.print(s.charAt(0));
			printChars(s.substring(1));
		}
	}

	public static void main(String[] args) {
		printChars("abcde");	// Expected Output : abcde
	}

}

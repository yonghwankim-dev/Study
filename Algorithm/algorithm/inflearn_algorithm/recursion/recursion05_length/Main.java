package recursion.recursion05_length;

public class Main {

	// ���ڿ� s�� ���� �Ի�
	public static int length(String s) 
	{
		if (s.equals("")) {
			return 0;
		}
		return 1 + length(s.substring(1));
	}

	public static void main(String[] args) {
		System.out.println(length("abcde")); // Expected Output : 5 
	}

}

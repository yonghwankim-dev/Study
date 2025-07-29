package recursion.recursion05_length;

public class Main {

	// 문자열 s의 길이 게산
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

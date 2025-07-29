package recursion.recursion02_factorial;

public class Main {

	// n! ���� ��������� ����
	public static int factorial(int n) 
	{
		if (n == 0) {
			return 1;
		}
		return n * factorial(n - 1);
	}

	public static void main(String[] args) {
		System.out.println(factorial(5)); // Expected Output : 120
	}

}

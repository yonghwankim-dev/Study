package recursion.recursion04_fibonacci;

public class Main {

	// n��°�� �Ǻ���ġ ���� ����
	public static int fibo(int n) 
	{
		if (n < 2) {
			return n;
		}
		return fibo(n - 1) + fibo(n - 2);
	}

	public static void main(String[] args) {
		System.out.println(fibo(5)); // Expected Output : 5
	}

}

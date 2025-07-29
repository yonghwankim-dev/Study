package recursion.recursion03_power;

public class Main {

	// x^n ���� ��������� ����
	public static double power(double x, int n) 
	{
		if (n == 0) {
			return 1;
		}
		return x * power(x, n - 1);
	}

	public static void main(String[] args) {
		System.out.println(power(2,5)); // Expected Output : 32
	}

}

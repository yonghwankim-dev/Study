package recursion.recursion01_sum;

public class Main {

	// 0~n������ �հ踦 ��������� ����
	public static int func(int n) 
	{
		if (n == 0) 
		{
			return 0;
		}
		return n + func(n - 1);
	}

	public static void main(String[] args) {
		System.out.println(func(10));	// Expected Output : 55
	}

}

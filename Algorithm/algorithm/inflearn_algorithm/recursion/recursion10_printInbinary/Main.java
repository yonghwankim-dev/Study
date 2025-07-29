package recursion.recursion10_printInbinary;

public class Main {

	// 2진수로 변환하여 출력
	public static void printInBinary(int n)
	{
		if(n<2)
		{
			System.out.print(n);
		}
		else
		{
			printInBinary(n/2);
			System.out.print(n%2);
		}
	}

	public static void main(String[] args) {
		
		printInBinary(10); // Expected Output : 1010
	}

}

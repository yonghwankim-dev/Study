package dp.dp01_fibo.fibo01_basic;

/**
 * title : �Ǻ���ġ ��
 * content : ���� n�� ���� �Ǻ���ġ ���� ���ϱ� ���� fibo �޼��� ����
 * �Ǻ���ġ ���� Ư�� n�׿� ���ؼ� n-1�װ� n-2���� ���� ���� ������ �˴ϴ�.
 * �Ǻ���ġ ���� : 0 1 1 2 3 5 8 13 ...
 * f(0)=0, f(1)=1, f(n) = f(n-1) + f(n-2)
 * 
 * ������
 * fibo �޼��带 ��������� �����Ͽ����� ������� ȣ�⿡�� �̹� ���� ����
 * �ٽ� ����ϴ� �ߺ� ����� �߻���
 */
public class Driver {
	public static int fibo(int n)
	{
		if(n<2)
		{
			return n;
		}
		return fibo(n-1) + fibo(n-2);
	}
	public static void main(String args[])
	{
		System.out.println(fibo(7));	// 13
	}
}

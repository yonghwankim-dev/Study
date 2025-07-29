package dp.dp02_binomial.binomial01_basic;

/**
 * title : �Ϲ����� ���װ��(binomial coefficient) ��������� ����
 * content : 
 * ���װ���� �����ΰ�?
 * ���� ����� �־��� ũ�� ����(n)���� ���ϴ� ����(k)��ŭ
 * ���� ���� �̴� ������ �������� �ǹ��մϴ�.
 * ���� ��� n=3, k=2�̸�
 * (1,2)
 * (1,3)
 * (2,3)
 * ���� �� 3������ ���� �� �ֽ��ϴ�.
 * 
 * (n,k) = 1, if n=k or k=0
 * (n,k) = (n-1,k) + (n-1,k-1), otherwise
 * 
 * ������
 * �� ������ ���װ���� ��������� ����ϴ� ����
 * �ߺ��� ����� �߻��մϴ�.
 */
public class Driver {
	public static int binomial(int n, int k)
	{
		if(n==k || k==0)
		{
			return 1;
		}
		return binomial(n-1,k) + binomial(n-1,k-1);
	}
	public static void main(String[] args) {
		System.out.println(binomial(5,2));
	}
}

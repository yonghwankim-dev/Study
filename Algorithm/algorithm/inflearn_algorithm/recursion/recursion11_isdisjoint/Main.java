package recursion.recursion11_isdisjoint;

public class Main {

	// Disjoint Sets
	// �迭 A�� A[0], ... , A[m-1]�� �迭 B�� B[0], ... , B[n-1]
	// �� �������� ���ĵǾ� ����Ǿ� ���� �� �� �迭�� �������� disjoint���� �˻��Ѵ�.
	// disjoint�� �� ������ ���ҵ��� ���� ���� ����� ��
	public static boolean isDisjoint(int m, int A[], int n, int B[])
	{
		if(m<0 || n<0)
		{
			return true;
		}
		else if(A[m]==B[n])
		{
			return false;
		}
		else if(A[m]>B[n])
		{
			return isDisjoint(m-1, A, n, B);
		}
		else
		{
			return isDisjoint(m, A, n-1, B);
		}
	}

	public static void main(String[] args) {
		int[] A = {1,2,3,4,5};
		int[] B = {6,7,8,9,10};
		System.out.println(Main.isDisjoint(A.length-1, A, B.length-1, B));	// Expected Output : true
	}

}

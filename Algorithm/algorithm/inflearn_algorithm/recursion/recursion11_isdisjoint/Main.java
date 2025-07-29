package recursion.recursion11_isdisjoint;

public class Main {

	// Disjoint Sets
	// 배열 A의 A[0], ... , A[m-1]과 배열 B의 B[0], ... , B[n-1]
	// 에 정수들이 정렬되어 저장되어 있을 대 두 배열의 정수들이 disjoint한지 검사한다.
	// disjoint란 두 집합의 원소들이 같은 것이 없어야 함
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

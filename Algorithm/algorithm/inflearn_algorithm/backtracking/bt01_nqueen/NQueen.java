package backtracking.bt01_nqueen;

public class NQueen {
	int count;	// ����� ��
	int n;		// ü���� ũ��
	boolean[] col, diag1, diag2;	// ��, �밢��1, �밢��2
	
	public NQueen(int n)
	{
		count=0;
		col = new boolean[n*2];
		diag1 = new boolean[n*2];
		diag2 = new boolean[n*2];
		this.n = n;
	}
	
	public void search(int y)
	{
		if(y==n)	// ���� ���� ��ġ�� ���
		{
			count++;
			return;
		}
		// 0��° ��~n-1��° ������ Ž��
		for(int x=0;x<n;x++)
		{
			// (y,x)�� x���� ���� ��ġ�ϸ� ������
			// (y,x)�� x+y �밢���� ��ġ�ϸ� ������
			// (y,x)�� x-y+n-1 �밢���� ��ġ�ϸ� ������
			if(col[x] || diag1[x+y] || diag2[x-y+n-1])
			{
				continue;
			}
			col[x] = diag1[x+y] = diag2[x-y+n-1] = true;	// �� ��ġ
			search(y+1);									// ���� ������ �̵�
			col[x] = diag1[x+y] = diag2[x-y+n-1] = false;	// �� ��ġ ���� 
		}
	}
	
	public static void main(String args[])
	{
		NQueen board = new NQueen(4);
		board.search(0);
		System.out.println(board.count);	// Expected Output : 2
	}
}

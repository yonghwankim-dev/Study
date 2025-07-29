package backtracking.bt01_nqueen;

public class NQueen {
	int count;	// 경우의 수
	int n;		// 체스판 크기
	boolean[] col, diag1, diag2;	// 열, 대각선1, 대각선2
	
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
		if(y==n)	// 퀸을 전부 배치한 경우
		{
			count++;
			return;
		}
		// 0번째 열~n-1번째 열까지 탐색
		for(int x=0;x<n;x++)
		{
			// (y,x)가 x번재 열에 위치하면 부적합
			// (y,x)가 x+y 대각선에 위치하면 부적합
			// (y,x)가 x-y+n-1 대각선에 위치하면 부적합
			if(col[x] || diag1[x+y] || diag2[x-y+n-1])
			{
				continue;
			}
			col[x] = diag1[x+y] = diag2[x-y+n-1] = true;	// 퀸 배치
			search(y+1);									// 다음 행으로 이동
			col[x] = diag1[x+y] = diag2[x-y+n-1] = false;	// 퀸 배치 해제 
		}
	}
	
	public static void main(String args[])
	{
		NQueen board = new NQueen(4);
		board.search(0);
		System.out.println(board.count);	// Expected Output : 2
	}
}

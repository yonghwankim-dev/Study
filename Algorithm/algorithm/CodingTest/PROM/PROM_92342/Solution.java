package PROM.PROM_92342;

import java.util.Arrays;

public class Solution {
	int max_diff;
	int[] max_board = null;
	int n;
	int[] apeach;
    public int[] solution(int n, int[] apeach) {
    	int[] board = new int[11];
    	this.n = n;
    	this.apeach = apeach;
    	
    	dfs(0,0,0,0,board);	
        return max_board!=null ? max_board : new int[] {-1};
    }
 
    /**
     * 
     * @param ascore 어피치 점수 합
     * @param lscore 라이언 점수 합
     * @param cnt	 현재 쏜 화살수	 	 
     * @param idx	 각 점수 인덱스 (0점,1점, ..., 10점)
     * @param board  라이언 점수판	 
     */
    public void dfs(int ascore, int lscore, int cnt, int idx, int[] board)
    {
    	if(cnt>this.n)	// 쏜 화살이 남아있는 화살보다 큰 경우
    	{
    		return;
    	}
    	if(idx>10)	// idx 순회완료
    	{
    		int diff = lscore - ascore;
    		if(diff>max_diff)
    		{
    			board[10] = n - cnt;	// 남아있는 화살수를 0점에 사격
    			max_board = board;		// lion 최대 점수차 보드판 갱신
    			max_diff = diff;		// 최대 점수차 갱신
    		}
    		return;
    	}

    	// 화살이 남아있고 lion이 이기는 경우
    	if(cnt<n)
    	{
    		int[] lion2 = Arrays.copyOf(board, board.length);	// 현재 lion board 복사
    		int need_arrow = apeach[10-idx]+1;
    		lion2[10-idx] = need_arrow; 
    		dfs(ascore, lscore+idx, cnt+need_arrow, idx+1, lion2);
    	}
    	
    	int[] lion1 = Arrays.copyOf(board, board.length);
    	
    	// apeach가 이기거나 둘다 0점인 경우
    	if(apeach[10-idx]>0)	// apeach가 이긴 경우
    	{
    		dfs(ascore+idx, lscore, cnt, idx+1, lion1);
    	}
    	else	// apeach, lion둘다 0점인 경우
    	{
    		dfs(ascore, lscore, cnt, idx+1, lion1);
    	}
    }
    
	public static void main(String[] args)
	{
		int[] apeach = {2,1,1,1,0,0,0,0,0,0,0};
		int[] result = new Solution().solution(5, apeach);
		System.out.println(Arrays.toString(result));
	}
}

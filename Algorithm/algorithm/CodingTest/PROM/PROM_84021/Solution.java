package PROM.PROM_84021;

class Solution {
	public static int[][] rotate_90(int[][] m)
	{
		int N = m.length;
		int[][] ret = new int[N][N];
		
		for(int row=0; row<N; row++)
		{
			for(int col=0; col<N; col++)
			{
				ret[col][N-1-row] = m[row][col];
			}
		}
		return ret;
	}
	
    public int solution(int[][] game_board, int[][] table) {
        int answer = -1;
        
        return answer;
    }
    
 
}

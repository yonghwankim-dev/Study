package PROM.PROM_312691;

public class Solution{
	public static int solution(int[][] land) {
        int answer = 0;
        
        int prev_col = 0;
        for(int i=0;i<land.length;i++)
        {	
        	int maxVal = land[i][0];
        	int cur_col = 0;
        	for(int j=0;j<land[i].length;j++)
        	{
        		if(land[i][j]>maxVal && j!=prev_col)
        		{
        			maxVal = land[i][j];
        			cur_col = j;
        		}
        	}
        	prev_col = cur_col;
        	answer += maxVal;
        	
        }

        return answer;
    }    
	public static void main(String args[])
	{
		int[][] land = {{1,2,3,5},{5,6,7,8},{4,3,2,1}};
		System.out.println(solution(land));
	}
}

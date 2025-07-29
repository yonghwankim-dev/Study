package PROM.PROM_87946;

public class Solution{
	int answer = 0;
	
	public int playDungeon(int k, int[][] sd)
	{
		int cnt = 0;
		
		for(int i = 0; i < sd.length; i++)
		{
			if(sd[i][0] <= k)
			{
				k -= sd[i][1];
				cnt++;
			}
			else
			{
				return cnt;
			}
		}
		return cnt;
	}
	
	/**
	 * 
	 * @param level		선택한 던전 개수
	 * @param n			모든 던전 개수
	 * @param chosen	던전 선택 여부
	 * @param dungeons	던전 정보
	 * @param sd		선택한 던전
	 */
	public void dfs(int level, int n, int k, boolean[] chosen, int[][] dungeons, int[][] sd)
	{
		if(level == n)
		{
			answer = Math.max(answer, playDungeon(k, sd));
			return;
		}
		
		for(int i = 0; i < n; i++)
		{
			if(!chosen[i])
			{
				chosen[i] = true;
				sd[level][0] = dungeons[i][0];
				sd[level][1] = dungeons[i][1];
				
				dfs(level + 1, n, k, chosen, dungeons, sd);
				
				chosen[i] = false;
				sd[level][0] = 0;
				sd[level][1] = 0;
			}
		}
		
	}
	
    public int solution(int k, int[][] dungeons) {
    	answer = 0;
    	
        int level = 0;				// 선택한 던전 개수
        int n = dungeons.length;	// 던전의 개수
        boolean[] chosen = new boolean[n+1];		// 던전 선택 여부
        int[][] selectedDungeons = new int[n][2];	// 선택한 던전
    
        dfs(level, n, k, chosen, dungeons, selectedDungeons);
        
        return answer;
    }
    
	public static void main(String args[])
	{
		int k = 80;
		int[][] dungeons = {{80, 20}, {50, 40}, {30, 10}};
		System.out.println(new Solution().solution(k, dungeons));
		
		
	}
}

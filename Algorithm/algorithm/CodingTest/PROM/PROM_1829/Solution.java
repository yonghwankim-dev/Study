package PROM.PROM_1829;


class Solution{
	private static final int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};	// UP DOWN LEFT RIGHT
	private static int M,N;
	private static boolean[][] visited;
	private static int[][] picture;
    public static int[] solution(int m, int n, int[][] picture) {
		int[] answer;
        int numOfArea = 0;
        int maxSizeOfOneArea = 0;
        M = m;
        N = n;
        visited = new boolean[m][n];
     	Solution.picture = picture;

        for(int y = 0; y < m; y++) {
        	for(int x = 0; x < n; x++) {
        		if(isNotVisited(y, x) &&
				   isExistedColor(y, x)){
        			numOfArea++;
            		int findNumOfArea = dfs(y, x);
                    maxSizeOfOneArea = Math.max(findNumOfArea, maxSizeOfOneArea);
        		}
        	}
        }

        answer = new int[]{numOfArea, maxSizeOfOneArea};
        return answer;
    }

	private static boolean isNotVisited(int y, int x){
		return visited[y][x] == false;
	}

	private static boolean isExistedColor(int y, int x){
		return picture[y][x] != 0;
	}
    
    public static int dfs(int y, int x)
    {
    	int count = 1;
		int value = picture[y][x];
    	visited[y][x] = true;
    	
    	for(int i = 0; i < 4; i++)
    	{
    		int ny = y + dirs[i][0];
    		int nx = x + dirs[i][1];
    		
    		if(checkPosition(ny, nx, value)) {
    			continue;
    		}
    		
    		count += dfs(ny, nx);
    	}
    	return count;
    }

	private static boolean checkPosition(int y, int x, int value){
		return !inInside(y, x) || isVisited(y, x) || isEqualValue(y, x, value);
	}

    private static boolean inInside(int y, int x)
    {
    	return (y >= 0 && y < M) && (x >= 0 && x < N);
    }

	private static boolean isVisited(int y, int x){
		return visited[y][x];
	}

	private static boolean isEqualValue(int y, int x, int value){
		return picture[y][x] != value;
	}



}

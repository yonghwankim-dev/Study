/**
 * 미로찾기
 */

package recursion.recursion14_maze;

public class Maze {
	private static int N=8;
	private static int[][] maze = {
			{0,0,0,0,0,0,0,1},
			{0,1,1,0,1,1,0,1},
			{0,0,0,1,0,0,0,1},
			{0,1,0,0,1,1,0,0},
			{0,1,1,1,0,0,1,1},
			{0,1,0,0,0,1,0,1},
			{0,0,0,1,0,0,0,1},
			{0,1,1,1,0,1,0,0}
	};
	private static final int PATHWAY_COLOUR = 0; // 일반 길
	private static final int WALL_COLOUR = 1;	 // 벽
	private static final int BLOCKED_COLOUR = 2; // 갈 수 없는 길
	private static final int PATH_COLOUR = 3;	 // 갈 수 있는 길
	
	public static boolean findMazePath(int x, int y)
	{
		if(x<0 || y<0 || x>=N || y>=N) // 미로의 범위를 벗어날 시
		{
			return false;
		}
		else if(maze[x][y]!=PATHWAY_COLOUR) // 일반 길을 제외한 길인 경우
		{
			return false;
		}
		else if(x==N-1 && y==N-1) // 출구인경우
		{
			maze[x][y] = PATH_COLOUR;
			return true;
		}
		else 
		{
			maze[x][y] = PATH_COLOUR;
			// 경로 상 하 좌 우 탐색
			if(findMazePath(x-1, y) || findMazePath(x+1, y) || findMazePath(x, y-1) || findMazePath(x, y+1))
			{
				return true;
			}
			maze[x][y] = BLOCKED_COLOUR; //상 하 좌 우 탐색 실패 시 해당 좌표는 벽
			return false;
		}
	}
	public static void printMaze()
	{
		for(int i=0;i<N;i++)
		{
			for(int j=0;j<N;j++)
			{
				System.out.print(maze[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		printMaze();
		boolean answer = findMazePath(0, 0);
		printMaze();
		
		System.out.println(answer);
	}

}

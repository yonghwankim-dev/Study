/**
 * �̷�ã��
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
	private static final int PATHWAY_COLOUR = 0; // �Ϲ� ��
	private static final int WALL_COLOUR = 1;	 // ��
	private static final int BLOCKED_COLOUR = 2; // �� �� ���� ��
	private static final int PATH_COLOUR = 3;	 // �� �� �ִ� ��
	
	public static boolean findMazePath(int x, int y)
	{
		if(x<0 || y<0 || x>=N || y>=N) // �̷��� ������ ��� ��
		{
			return false;
		}
		else if(maze[x][y]!=PATHWAY_COLOUR) // �Ϲ� ���� ������ ���� ���
		{
			return false;
		}
		else if(x==N-1 && y==N-1) // �ⱸ�ΰ��
		{
			maze[x][y] = PATH_COLOUR;
			return true;
		}
		else 
		{
			maze[x][y] = PATH_COLOUR;
			// ��� �� �� �� �� Ž��
			if(findMazePath(x-1, y) || findMazePath(x+1, y) || findMazePath(x, y-1) || findMazePath(x, y+1))
			{
				return true;
			}
			maze[x][y] = BLOCKED_COLOUR; //�� �� �� �� Ž�� ���� �� �ش� ��ǥ�� ��
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

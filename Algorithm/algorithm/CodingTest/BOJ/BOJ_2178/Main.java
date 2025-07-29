package BOJ.BOJ_2178;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

class Pair
{
	int y;
	int x;
	public Pair(int y, int x) {
		this.y = y;
		this.x = x;
	}
}

class Main {
	static int row, col;
	static int[][] map;
	static int[][] visited;
	static final int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};	// up, down, left, right

	private static int bfs()
	{
		int cur_y = 0;
		int cur_x = 0;
		
		Queue<Pair> queue = new LinkedList<>();
		
		queue.add(new Pair(cur_y, cur_x));
		
		visited[cur_y][cur_x] = 1;
		
		while(!queue.isEmpty())
		{
			cur_y = queue.peek().y;
			cur_x = queue.peek().x;
			queue.remove();
			
			if(isExit(cur_y, cur_x))
			{
				break;
			}
			
			for(int i = 0; i < 4; i++)
			{
				//up, down, left, right
				int next_y = cur_y + dirs[i][0];
				int next_x = cur_x + dirs[i][1];

				if(findPath(next_y, next_x))
				{
					visited[next_y][next_x] = visited[cur_y][cur_x] + 1;
					queue.add(new Pair(next_y, next_x));
				}
			}
		}
		return visited[row-1][col-1];
	}

	private static boolean isExit(int y, int x){
		return (y == row - 1) && (x == col - 1);
	}

	private static boolean findPath(int y, int x){
		return isInside(y, x) && isNotVisited(y, x) && isRoad(y, x);
	}

	private static boolean isInside(int y, int x)
	{
		return (y >= 0 && y < row) && (x >= 0 && x < col);
	}

	private static boolean isNotVisited(int y, int x){
		return visited[y][x] == 0;
	}

	private static boolean isRoad(int y, int x){
		return map[y][x] == 1;
	}

	public static int solution(int row, int col, String[][] map){
		int answer = 0;
		Main.row = row;
		Main.col = col;
		Main.map = toIntArray(map);
		Main.visited = new int[row][col];

		answer = bfs();

		return answer;
	}

	private static int[][] toIntArray(String[][] text){
		int[][] result = new int[row][col];
		for(int i = 0; i < row; i++){
			result[i] = Arrays.stream(text[i]).mapToInt(Integer::parseInt).toArray();
		}
		return result;
	}

	
	public static void main(String args[]) throws IOException
	{
		int row, col;
		String[][] map;

		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
			String[] split = br.readLine().split(" ");
			row = Integer.parseInt(split[0]);
			col = Integer.parseInt(split[1]);
			map = new String[row][col];

			for(int i = 0; i < row; i++){
				map[i] = br.readLine().split("");
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		int answer = Main.solution(row, col, map);
		System.out.println(answer);
	}	
}

package BOJ.BOJ_7576;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
public class Main {
	
	// 상하좌우
	private static int[] dr = {-1,1,0,0};
	private static int[] dc = {0,0,-1,1};
	private static int[][] map;
	private static int m, n;
	
	static class Tomato{
		int y, x;

		public Tomato(int y, int x) {
			this.y = y;
			this.x = x;
		}

		@Override
		public String toString() {
			return "Tomato [y=" + y + ", x=" + x + "]";
		}		
	}
	
	public static boolean check_adjust(Tomato t)
	{
		int x = t.x;
		int y = t.y;
		
		// 범위를 벗어나지 않고 인접 토마토의 상태가 0인 경우 
		if(!(x<0 || x>m-1 || y>n-1 || y<0) && map[y][x]==0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static void bfs(Queue<Tomato> queue)
	{
		while(!queue.isEmpty())
		{
			Tomato t = queue.poll();
			
			for(int i=0;i<4;i++)
			{
				int ny = t.y + dr[i];
				int nx = t.x + dc[i];
				Tomato adj_tomato = new Tomato(ny, nx);
				
				if(check_adjust(adj_tomato))
				{
					map[ny][nx] = map[t.y][t.x] + 1;
					queue.add(adj_tomato);
				}
			}
		}
	}

	public static int solution()
	{
		Queue<Tomato> queue = new LinkedList<Tomato>();
		int answer = 0;
		for(int i=0; i<n; i++)
		{
			for(int j=0; j<m; j++)
			{
				if(map[i][j]==1)
				{
					queue.add(new Tomato(i, j));
				}
			}
		}
		
		bfs(queue);
		
		
		for(int i=0;i<n; i++)
		{
			for(int j=0;j<m;j++)
			{
				if(map[i][j]==0)	// 익지 않은 토마토가 존재하는 경우
				{
					return -1;
				}
				if(answer < map[i][j])	// map에서 가장 큰 일수를 구한다.
				{
					answer = map[i][j];
				}
			}
		}
		// 최종일수에서 하루를 뺀다. 기본값이 1부터 시작하기 때문이다.
		return answer-1;
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String str[] = br.readLine().split(" ");
		m = Integer.parseInt(str[0]);
		n = Integer.parseInt(str[1]);
		
		map = new int[n][m];
		
		for(int i=0;i<n;i++)
		{
			map[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		}
		
		System.out.println(solution());
		
	}
}

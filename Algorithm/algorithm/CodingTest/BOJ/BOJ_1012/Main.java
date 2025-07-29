package BOJ.BOJ_1012;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
	private static int n, m, k;	// 세로, 가로, 배추개수
	private static int[][] map;	// 배추밭, 1 : 배추, 0 : 배추없음
	private static boolean[][] visited;	// 배추흰지렁이 방문여부, true : 방문, false : 미방문
	
	// 상하좌우
	private static int[] dr = {-1,1,0,0};
	private static int[] dc = {0,0,-1,1};
	
	private static class Cabbage{
		int y, x;	// 세로, 가로

		public Cabbage(int y, int x) {
			this.y = y;
			this.x = x;
		}
		
	}
	
	// 인접한 곳에 배추(1)가 존재하는 지 검사
	private static boolean check_adjust(Cabbage c)
	{
		int y = c.y;	// 세로
		int x = c.x;	// 가로
		
		// 범위를 벗어나지 않고 인접 밭의 상태가 배추(1)인 경우 
		if(!(x<0 || x>m-1 || y>n-1 || y<0) && map[y][x]==1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private static void dfs(Cabbage c)
	{
		
	}

	private static void bfs(Cabbage c)
	{
		Queue<Cabbage> queue = new LinkedList<Cabbage>();
		
		queue.offer(c);

		// queue가 빌때까지 지렁이의 인접한 배추 탐색 수행
		while(!queue.isEmpty())
		{
			c = queue.poll();
			
			// 해당 배추가 이미 방문했는지 검사
			if(visited[c.y][c.x])
			{
				continue;
			}
			
			// 배추 방문
			visited[c.y][c.x] = true;
			
			// 해당 배추 방문후 배추와 인접한 다른 배추 탐색
			for(int i=0; i<4; i++)
			{
				Cabbage adj_cabbage = new Cabbage(c.y+dr[i], c.x+dc[i]);
				
				// 인접한 배추가 존재할 경우 queue에 삽입
				if(check_adjust(adj_cabbage))
				{
					queue.add(adj_cabbage);
				}
			}
		}
	}
	
	public static int solution()
	{
		int answer = 0;	// 필요한 배추흰지렁이 수
		// 배추밭 탐색
		for(int i=0; i<n; i++)
		{
			for(int j=0; j<m; j++)
			{
				if(map[i][j]==1 && visited[i][j]==false)
				{
					bfs(new Cabbage(i, j));
					answer++;
				}
			}
		}
		return answer;
	}
	
	// 배추밭 상태 출력
	public static void print()
	{
		for(int i=0;i<map.length;i++)
		{
			for(int j=0;j<map[i].length;j++)
			{
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int tc = Integer.parseInt(br.readLine());
		int y,x;
		for(int i=0;i<tc;i++)
		{
			String str[] = br.readLine().split(" ");
			n = Integer.parseInt(str[0]);	// 세로
			m = Integer.parseInt(str[1]);	// 가로
			k = Integer.parseInt(str[2]);	// 배추개수
			
			map = new int[n][m];
			visited = new boolean[n][m];
			
			for(int j=0;j<k;j++)
			{
				str = br.readLine().split(" ");
				y = Integer.parseInt(str[0]);	// 세로
				x = Integer.parseInt(str[1]);	// 가로
				map[y][x] = 1;
			}
			
			System.out.println(solution());
			
		}
		
	}
}

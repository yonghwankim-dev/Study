package BOJ.BOJ_2667;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class Main {
	
	public static LinkedList<Integer>[] nodeList;
	// 상하좌우
	private static int[] dr = {-1,1,0,0};
	private static int[] dc = {0,0,-1,1};
	static class House{
		int x, y;
		House(int y, int x)
		{
			this.y = y;
			this.x = x;
		}
	}
	
	public static boolean check_adjust(House house, int[][] map, boolean[][] visited)
	{
		int x = house.x;
		int y = house.y;
		
		// 범위 벗어남
		if(x<0 || x>map.length-1 || y>map.length-1 || y<0)
		{
			return false;
		}
		
		if(map[y][x]==1 && visited[y][x]==false)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static int bfs(House house, int[][] map, boolean[][] visited)
	{
		Queue<House> queue = new LinkedList<House>();
		int count = 0;
		
		queue.offer(house);
		
		while(!queue.isEmpty())
		{
			house = queue.poll();
			
			// 방문한적 있는 경우 skip
			if(visited[house.y][house.x])
			{
				continue;
			}
			
			visited[house.y][house.x] = true;
			count++;
			
			// 인접 하우스 탐색
			for(int i=0;i<4;i++)
			{
				House adj_house = new House(house.y+dr[i], house.x+dc[i]);
				// 인접하우스가 존재(1)하고 방문한적 없는 경우 큐에 추가한다.
				if(check_adjust(adj_house, map, visited))
				{
					queue.add(adj_house);
				}
			}
		}
		return count;
	}
	
	public static void solution(int[][] map, boolean[][] visited)
	{
		int answer = 0;	// 총 단지 수
		List<Integer> house_count = new ArrayList<Integer>();	// 각각의 단지의 집개수
		for(int i=0;i<map.length;i++)
		{
			for(int j=0;j<map.length;j++)
			{
				if(map[i][j]==1 && visited[i][j]==false)
				{
					house_count.add(bfs(new House(i,j),map,visited));
					answer++;
				}
			}
		}
		Collections.sort(house_count);
		System.out.println(answer);
		house_count.stream().forEach(System.out::println);
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		int[][] map = new int[n][n];
		boolean[][] visited = new boolean[n][n];
		
		for(int i=0;i<n;i++)
		{
			// ex) "0110100" -> [0,1,1,0,1,0,0]
			map[i] = Arrays.stream(br.readLine().split("")).mapToInt(Integer::parseInt).toArray();
		}
		
		solution(map,visited);
	}
}

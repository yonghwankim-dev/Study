package BOJ.BOJ_2580;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

	/**
	 * 가로줄 제거
	 */
	private static void removeHorizontal(List<Integer> list, int[][] map, int N, int row)
	{
		for(int i=0; i<N; i++)
		{
			if(map[row][i]==0 || !list.contains(map[row][i]))
			{
				continue;
			}			
			list.remove(Integer.valueOf(map[row][i]));
		}
	}
	
	/**
	 * 세로줄 제거
	 */
	private static void removeVertical(List<Integer> list, int[][] map, int N, int col)
	{
		for(int i=0; i<N; i++)
		{
			if(map[i][col]==0 || !list.contains(map[i][col]))
			{
				continue;
			}			
			list.remove(Integer.valueOf(map[i][col]));
		}
	}
	
	/**
	 * 3*3 영역 제거
	 */
	private static void removeArea(List<Integer> list, int[][] map, int y, int x)
	{
		int start_y = (y/3)*3;
		int start_x = (x/3)*3;
		
		int end_y = start_y+2;
		int end_x = start_x+2;
		
		for(int row=start_y; row<=end_y; row++)
		{
			for(int col=start_x; col<=end_x; col++)
			{
				if(map[row][col]==0 || !list.contains(map[row][col]))
				{
					continue;
				}
				list.remove(Integer.valueOf(map[row][col]));
			}
		}
	}
	
	public static boolean solution(int[][] map, int N, int y, int x)
	{
		for(int row=y; row < N; row++)
		{
			for(int col=x; col < N; col++)
			{
				if(map[row][col]==0)
				{
					List<Integer> list = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
					
					// step1 가로줄 제거
					removeHorizontal(list, map, N, row);
				
					// 세로줄에 있는 숫자 제거
					removeVertical(list, map, N, col);
					
					// 3*3 영역에 있는 숫자 제거
					removeArea(list, map, row, col);
					
					// 스도쿠 실패
					if(list.size()==0)
					{
						return false;
					}
					
					// 백트래킹
					for(int candidate : list)
					{
						int temp = map[row][col];
						map[row][col] = candidate;
						if(solution(map, N, row, col+1))
						{
							return true;
						}
						map[row][col] = temp;
					}
					
					// 스도쿠 실패
					if(map[row][col]==0)
					{
						return false;
					}
				}
			}
			x=0;
		}
		return true;
	}
	
	public static void print(int[][] map)
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
		int N = 9;
		int[][] map = new int[N][N];
		
		for(int i=0;i<N;i++)
		{
			String[] line = br.readLine().split(" ");
			map[i] = Arrays.stream(line).mapToInt(Integer::parseInt).toArray();
		}
		
		solution(map, N, 0, 0);
		print(map);
	}
}

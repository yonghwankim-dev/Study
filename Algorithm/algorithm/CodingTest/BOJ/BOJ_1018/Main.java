package BOJ.BOJ_1018;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	// (x,y)를 기준 8*8 보드판의 수정해야 하는 수
	public static int checkColor(String start, String[][] map)
	{	
		int cnt = 0;
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				// start="W" => even : "W", odd : "B"
				// start="B" => even : "B", odd : "W"
				// j열이 짝수(0열 포함)인데 start와 동일하지 않으면 수정해야 함 
				if(j%2==0)	
				{
					if(!start.equals(map[i][j]))
					{
//						System.out.printf("(%d,%d) : start=%s, map[%d][%d]=%s\n",i,j,start,i,j,map[i][j]);
						cnt++;
					}
				}
				// j열이 홀수인데 start 동일하면 수정해야 함
				else {
					if(start.equals(map[i][j]))
					{
//						System.out.printf("(%d,%d) : start=%s, map[%d][%d]=%s\n",i,j,start,i,j,map[i][j]);
						cnt++;
					}
				}
			}
			// start의 값을 반전시킵니다.(W->B, B->W)
			start = (start.equals("W") ? "B" : "W");
		}
		return cnt;
	}
	
	public static void print(String[][] map)
	{
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				System.out.print(map[i][j]+",");
			}
			System.out.println();
		}
	}
	
	public static String[][] sliceMap(String[][] map, int row, int col)
	{
		String[][] result = new String[8][8];
		
		for(int i=0, r=row; i<8; i++,r++)
		{
			for(int j=0, c=col; j<8; j++, c++)
			{
				result[i][j] = map[r][c];
			}
		}
		return result;
	}
	
	public static int solution(String[][] map, int row, int col)
	{
		int min = 64;
		for(int i=0;i<=row-8;i++)
		{
			for(int j=0;j<=col-8; j++)
			{
				String[][] partMap = sliceMap(map, i, j);
				String start = partMap[0][0];	// Black or White
				String not_start = (start.equals("B") ? "W" : "B");
//				print(partMap);
				int val = Math.min(
									checkColor(start, partMap),
									checkColor(not_start, partMap)
									);
//				System.out.printf("result is (%d,%d) : %d\n", i,j,val);
				if(val<min)
				{
					min = val;
				}
			}
		}
		return min;
	}
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String[] str = br.readLine().split(" ");
		int n = Integer.parseInt(str[0]);
		int m = Integer.parseInt(str[1]);
		String[][] map = new String[n][m];
		
		for(int i=0;i<n;i++)
		{
			map[i] = br.readLine().split("");
		}
		
		System.out.println(solution(map, n, m));
	}
}

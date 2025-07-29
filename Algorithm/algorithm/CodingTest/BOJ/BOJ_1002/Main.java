package BOJ.BOJ_1002;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	static class Point{
		int x, y,r;

		public Point(int x, int y, int r) {
			this.x = x;
			this.y = y;
			this.r = r;
		}
			
	}
	
	public static void solution(Point p1, Point p2)
	{
		double d =Math.sqrt((p1.x-p2.x)*(p1.x-p2.x) + (p1.y-p2.y)*(p1.y-p2.y));
	
		// 두 중점의 거리가 0이고, 반지름까지 같음 => 같은 원
		if(d==0 && p1.r==p2.r)
		{
			System.out.println(-1);
		}
		// 두 원이 서로 외접하거나 내접 하지 않은 경우 => 접점이 없음
		else if(d>p1.r+p2.r || d<Math.abs(p1.r-p2.r))
		{
			System.out.println(0);
		}
		// 두 원이 서로 외접하거나 내접하는 경우 => 한점에서 만남
		else if(d==p1.r+p2.r || d==Math.abs(p1.r-p2.r))
		{
			System.out.println(1);
		}
		else if(d<p1.r+p2.r)
		{
			System.out.println(2);	
		}
	}
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		
		for(int i=0;i<n;i++)
		{
			String str[] = br.readLine().split(" ");
			int x1 = Integer.parseInt(str[0]);
			int y1 = Integer.parseInt(str[1]);
			int r1 = Integer.parseInt(str[2]);
			int x2 = Integer.parseInt(str[3]);
			int y2 = Integer.parseInt(str[4]);
			int r2 = Integer.parseInt(str[5]);
			
			Point p1 = new Point(x1,y1,r1);
			Point p2 = new Point(x2,y2,r2);
			
			solution(p1, p2);	
		}
	}
}

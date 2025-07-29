package BOJ.BOJ_11651;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
	static class Point{
		int x,y;
		Point(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		
	}
	
	public static void solution(List<Point> list)
	{
		list.sort(Comparator.comparing(Point::getY).thenComparing(Point::getX));
		
		for(Point p : list)
		{
			System.out.println(p.x + " " + p.y);
		}
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		List<Point> list = new ArrayList<>();
		
		for(int i=0;i<n;i++)
		{
			String[] str = br.readLine().split(" ");
			int x = Integer.parseInt(str[0]);
			int y = Integer.parseInt(str[1]);
			list.add(new Point(x,y));
		}
		solution(list);
	}
}

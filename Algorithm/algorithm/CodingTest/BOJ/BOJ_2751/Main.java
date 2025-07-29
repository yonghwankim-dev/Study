package BOJ.BOJ_2751;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Main {
	
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	public static void solution() throws NumberFormatException, IOException
	{
		int n = Integer.parseInt(br.readLine());
		List<Integer> list = new ArrayList<Integer>();
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<n;i++)
		{
			list.add(Integer.parseInt(br.readLine()));
		}
		
		Collections.sort(list);
		list.stream().forEach(item->{sb.append(item+"\n");});
		
		System.out.println(sb.toString());
	}
	
	public static void main(String args[]) throws IOException
	{
		solution();	
	}
}

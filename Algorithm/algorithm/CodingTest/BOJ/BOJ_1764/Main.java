package BOJ.BOJ_1764;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		Set<String> g1 = new HashSet<String>();
		List<String> g2 = new ArrayList<String>();
		 
		
		String str[] = br.readLine().split(" ");
		int n = Integer.parseInt(str[0]);
		int m = Integer.parseInt(str[1]);
		
		for(int i=0; i<n;i++)
		{
			g1.add(br.readLine());
		}
		
		for(int i=0;i<m;i++)
		{
			String s = br.readLine();
			if(g1.contains(s))
			{
				g2.add(s);
			}
		}
		Collections.sort(g2);
		System.out.println(g2.size());
		g2.stream().forEach(System.out::println);
	}
	
}

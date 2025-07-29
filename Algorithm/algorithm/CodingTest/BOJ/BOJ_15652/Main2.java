package BOJ.BOJ_15652;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main2 {
	
	static StringBuilder sb = new StringBuilder();
	public static void search(int n, int m, List<Integer> p, boolean[] chosen)
	{
		if(p.size() == m)
		{
			p.forEach(item -> sb.append(item+" "));
			sb.append("\n");
		}
		else
		{
			for(int i=1; i<=n; i++)
			{
				if(p.size()>=1 && i<p.get(p.size()-1))
				{
					continue;
				}
				
				chosen[i] = true;
				p.add(i);
				
				search(n, m, p, chosen);
				
				chosen[i] = false;
				p.remove(p.size()-1);
				
			}
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String[] str = br.readLine().split(" ");
		int n = Integer.parseInt(str[0]);
		int m = Integer.parseInt(str[1]);
		boolean[] chosen = new boolean[n+1];
		List<Integer> p = new ArrayList<Integer>();
		
		search(n, m, p, chosen);
		System.out.println(sb.toString());
	}
}

package BOJ.BOJ_1406;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringBuilder str = new StringBuilder(br.readLine());
		int n = Integer.parseInt(br.readLine());
		
		int cursor = str.length();
		
		for(int i=0;i<n;i++)
		{
			String[] cmds = br.readLine().split(" ");
			String cmd = cmds[0];
			switch(cmd)
			{
			case "L":
				if(cursor!=0)
				{
					cursor--;
				}
				break;
			case "D":
				if(cursor!=str.length())
				{
					cursor++;
				}
				break;
			case "B":
				if(cursor!=0)
				{
					str.deleteCharAt(cursor-1);
					cursor--;
				}
				break;
			case "P":
				str.insert(cursor, cmds[1]);
				cursor++;
				break;
			}
		}
		System.out.println(str.toString());
	}
}

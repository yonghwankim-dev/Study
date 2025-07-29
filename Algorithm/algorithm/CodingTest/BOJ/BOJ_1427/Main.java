package BOJ.BOJ_1427;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;



public class Main {
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String str = br.readLine();
		Integer[] data = new Integer[str.length()];
		
		for(int i=0;i<str.length();i++)
		{
			data[i] = Integer.parseInt(String.valueOf(str.charAt(i)));
		}
		
		Arrays.sort(data, Collections.reverseOrder());
		String answer = "";
		for(int i=0;i<data.length;i++)
		{
			answer += data[i];
		}
		System.out.println(answer);
	}
}

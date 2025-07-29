package BOJ.BOJ_4153;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


public class Main {
	
	public static String solution(long[] arr)
	{	
		return (arr[0]*arr[0] + arr[1]*arr[1] == arr[2]*arr[2] ? "right" : "wrong");
	}
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] str;
		
		long[] arr = new long[3];
		
		while(true)
		{
			str = br.readLine().split(" ");
			arr[0] = Integer.parseInt(str[0]);
			arr[1] = Integer.parseInt(str[1]);
			arr[2] = Integer.parseInt(str[2]);
			
			if(arr[0]==0 && arr[1]==0 && arr[2]==0)
			{
				break;
			}
			Arrays.sort(arr);
			
			System.out.println(solution(arr));
		}		
	}
}

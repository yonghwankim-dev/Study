package BOJ.BOJ_11053;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {
	
	public static int bSearch(List<Integer> B, int val, int first, int last)
	{
		if(first>last)
		{
			return first-1;
		}
		int mid = (first+last)/2;
		if(val <= B.get(mid))
		{
			return bSearch(B, val, first, mid-1); 
		}
		else
		{
			return bSearch(B, val, mid+1, last);
		}
	}
	
	public static int solution2(int n, int[] arr, int[] d)
	{
		List<Integer> D_list = new ArrayList<>();
		List<Integer> B = new ArrayList<>();
		D_list.add(0);
		B.add(0);
		
		for(int i=1;i<=n;i++)
		{
			int val = arr[i];
			int idx = bSearch(B, val, 0, B.size()-1); 
			d[i] = D_list.get(idx);
			
			if(B.size()-1==idx)
			{
				D_list.add(d[i]+1);
				B.add(val);
			}else {
				B.set(idx+1, val);
			}
		}
		
		System.out.println("arr : " + Arrays.toString(arr));
		System.out.println("d : " + Arrays.toString(d));
		System.out.println("D_list : " + D_list);
		System.out.println("B : " + B);
		
		return D_list.get(D_list.size()-1);
	}
	
	public static int solution(int n, int[] arr, int[] d)
	{
		for(int i=1;i<=n;i++)
		{
			int max = arr[i];
			for(int j=0;j<i;j++)
			{
				if(arr[j]<max && d[i]<=d[j])
				{
					d[i] = d[j] + 1;
				}
			}
		}
		
		return Arrays.stream(d).max().getAsInt();
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		String[] str = br.readLine().split(" ");
		int[] arr = new int[n+1];
		int[] d = new int[n+1];
		
		for(int i=1;i<=n;i++)
		{
			arr[i] = Integer.parseInt(str[i-1]);
		}
		
		System.out.println(solution2(n, arr,d));
	}
}

package PROM.PROM_68645;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {

	public static int[]	pasing(int n, int[][] map)
	{
		List<Integer> list = new ArrayList<>();
		
		for(int i=0;i<n;i++)
		{
			for(int j=0; j<n; j++)
			{
				if(map[i][j]!=0)
				{
					list.add(map[i][j]);
				}
				else
				{
					break;
				}
			}
		}
		
		return list.stream().mapToInt(Integer::intValue).toArray();
	}
	
	public static boolean isRange(int n, int i, int j)
	{
		if(i<0 || i>=n || j<0 || j>=n)
		{
			return false;
		}
		return true;
	}

	
    public static int[] solution(int n)
    {
    	int[][] map = new int[n][n];
    	int i,j,val;
    	i=j=val=0;
    	
    	while(isRange(n, i, j) && map[i][j]==0)
    	{
    		// down
        	while(i<=n)
        	{
        		if(i==n || map[i][j]!=0)
        		{
        			i--;
        			j++;
        			break;
        		}
        		
        		map[i][j] = ++val;
        		
        		i++;
        	}
        	
        	// right
        	while(j<=n)
        	{
        		if(j==n || map[i][j]!=0)
        		{
        			i--;
        			j-=2;
        			break;
        		}
        		
        		map[i][j] = ++val;
        		j++;
        	}
        	
        	// upAndLeft
        	while(isRange(n, i, j))
        	{
        		if(map[i][j]!=0 || map[i][j]!=0)
        		{
        			i+=2;
        			j++;
        			break;
        		}
        		map[i][j] = ++val;
        		i--;
        		j--;
        	}
    	}
    	
    	return pasing(n, map);
    }
    
    public static void print(int[][] map)
    {
    	Arrays.stream(map).forEach((item)->{
    		Arrays.stream(item).forEach((val)->{
    			System.out.print(val + " ");
    		});
    		System.out.println();
    	});
    	System.out.println();
    }
	public static void main(String[] args)
	{
		System.out.println(Arrays.toString(solution(1)));
		System.out.println(Arrays.toString(solution(2)));
		System.out.println(Arrays.toString(solution(3)));
		System.out.println(Arrays.toString(solution(4)));
		System.out.println(Arrays.toString(solution(5)));
		System.out.println(Arrays.toString(solution(6)));
	}
}

package PROM.PROM_28219;

import java.util.Arrays;



public class Solution{
    public static int solution(int[][] dice) {
        int answer = 0;
        int n = dice.length;
        int size = (int) Math.pow(10, n)-1;
        boolean[] numbers = new boolean[size];
        
        for(int i=0;i<n-1;i++)
        {
        	for(int j=0;j<dice[i].length;j++)
        	{
        		System.out.print(dice[i][j] + ""+dice[i+1][j] + " ");
        		System.out.print(dice[i+1][j] + ""+dice[i][j] + " ");
        		if(dice[i][j]!=0 && numbers[dice[i][j]]==false)
        		{
        			numbers[dice[i][j]] = true;
        		}
        	}
        }
        for(int i=0;i<n-1;i++)
        {
        	for(int j=0;j<dice[i].length;j++)
        	{
        		System.out.print(dice[i][j]);
        	}
        }
        
        
        
        System.out.println(Arrays.toString(numbers));
        return answer;
    }
	public static void main(String args[])
	{
//		int[][] dice = {{1,6,2,5,3,4},{9,9,1,0,7,8}};
		int[][] dice = {{1,2},{3,4}};
		solution(dice);
	}
}

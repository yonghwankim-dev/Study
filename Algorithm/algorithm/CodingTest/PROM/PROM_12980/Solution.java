package PROM.PROM_12980;

public class Solution {
	public static int move(int k, int n, int b) {
		if(k==n-1)
		{
			return b+1;
		}
		else if(k==n)
		{
			return b;
		}
		
		if(k*2<=n)
		{
			return move(k*2,n,b);
		}
		else
		{
			return move(k+1,n,b+1);
		}
	}
	
    public static int solution(int n) {
        int k = 0;
        int min = n;
        for(int i=1;i<=n;i++)
        {
        	k = move(i,n,i);
        	if(k<min)
        	{
        		min = k;
        	}
        }

        return min;
    }
    
	public static void main(String[] args)
	{
		solution(5);
	}
}

package PROM.PROM_68645;

import java.util.Arrays;

public class Solution2 {
    public int[] solution(int n) {
    	// a_n = a1(1) + (n^2+n-2)/2
    	//	  = 2/2 + (n^2+n-2)/2
    	//    = (n^2+n)/2
    	//    = (n(n+1))/2
        int[] answer = new int[(n*(n+1))/2];
        int[][] matrix = new int[n][n];

        int x = -1, y = 0;	// x:row y:col
        int num = 1;

        
        for (int i = 0; i < n; ++i) {
            for (int j = i; j < n; ++j) {
                if (i % 3 == 0) {	// down
                    ++x;
                } else if (i % 3 == 1) {	// right
                    ++y;
                } else if (i % 3 == 2) {	// up & left 
                    --x;
                    --y;
                }
                matrix[x][y] = num++;
            }
        }

        int k = 0;
        for(int i = 0; i < n; ++i) {
            for(int j = 0; j < n; ++j) {
                if(matrix[i][j] == 0) break;
                answer[k++] = matrix[i][j];
            }
        }

        return answer;
    }
    
    public static void main(String[] args)
    {
    	for(int i=1;i<=6;i++)
    	{
    		System.out.println(Arrays.toString(new Solution2().solution(i)));
    	}
    }
}

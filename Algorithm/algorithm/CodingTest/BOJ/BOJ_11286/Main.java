/**
 * ������ ���� : �켱���� ť
 */

package BOJ.BOJ_11286;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class Main {
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PriorityQueue<Integer> queue = new PriorityQueue<>((o1,o2)->{
			int abs1 = Math.abs(o1);
			int abs2 = Math.abs(o2);
			
			if(abs1==abs2)
			{
				return o1>o2 ? 1 : -1;
			}
			return abs1-abs2;
		});
		
		int tc = Integer.parseInt(br.readLine());
		
		for(int i=0;i<tc;i++)
		{
			int key = Integer.parseInt(br.readLine());
			
			if(key==0)
			{
				if(queue.size()==0)
				{
					System.out.println(0);
				}
				else
				{
					System.out.println(queue.poll());
				}
			}
			else 
			{
				queue.add(key);
				
			}
		}
	}
}

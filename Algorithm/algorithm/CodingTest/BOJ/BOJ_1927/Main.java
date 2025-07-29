/**
 * ������ ���� : �켱���� ť
 */

package BOJ.BOJ_1927;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class Main {
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PriorityQueue<Integer> queue = new PriorityQueue<>();
		
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

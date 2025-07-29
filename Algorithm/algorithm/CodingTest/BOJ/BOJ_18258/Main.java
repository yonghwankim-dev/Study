package BOJ.BOJ_18258;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static class Queue2{
		int[] queue;
		int front, back, size;
		public Queue2() {
			queue = new int[2000000];
			front = 0;
			back = 0;
			size = 0;
		}
		
		public void push(int x){
			queue[back] = x;
			back++;
			size++;
		}
		public int pop() {
			if(size==0) {
				return -1;
			}
			size--;
			return queue[front++];
		}
		public int size() {
			return size;
		}
		public int empty() {
			return (size==0 ? 1 : 0);
		}
		public int front() {
			if(size==0) {
				return -1;
			}
			return queue[front];
		}
		public int back() {
			if(size==0) {
				return -1;
			}
			return queue[back-1];
		}
		
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Queue2 q = new Queue2();
		int n = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		for(int i=0;i<n;i++){
			String[] str = br.readLine().split(" ");
			
			switch(str[0])
			{
			case "push":
				q.push(Integer.parseInt(str[1]));
				break;
			case "pop":
				sb.append(q.pop()+"\n");
				break;
			case "size":
				sb.append(q.size()+"\n");
				break;
			case "empty":
				sb.append(q.empty()+"\n");
				break;
			case "front":
				sb.append(q.front()+"\n");
				break;
			case "back":
				sb.append(q.back()+"\n");
				break;
			default:
				break;
			}
		}
		System.out.println(sb.toString());
		
	}
}

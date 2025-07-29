package BOJ.BOJ_14888;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class Main {
	
	static int N;
	static boolean[] choosen;
	static int max_val = Integer.MIN_VALUE;
	static int min_val = Integer.MAX_VALUE;
	
	private static List<String> getOperationList(int[] op)
	{
		List<String> op_list = new ArrayList<String>();
		for(int i = 0; i < 4; i++)
		{
			while(op[i]!=0)
			{
				if(i==0)
				{
					op_list.add("+");
				}
				else if(i==1)
				{
					op_list.add("-");
				}
				else if(i==2)
				{
					op_list.add("*");
				}
				else
				{
					op_list.add("/");
				}
				op[i]--;
			}
		}
		return op_list;
	}
	
	public static int cal(int a, int b, String op)
	{
		int result = 0;
		switch(op)
		{
			case "+":
					result = a + b;
					break;
			case "-":
					result = a - b;
					break;
			case "*":
					result = a * b;
					break;
			case "/":
					result = a / b;
					break;
			default:
					break;
		}
		return result;
	}
	
	public static int makeExp(int[] arr, List<String> vc)
	{
		Stack<Integer> stack = new Stack<Integer>();
		int vc_idx = 0;
		int a,b, result=0;
		int i=0;
		String op;
		
		while(!stack.isEmpty() || i<arr.length)
		{
			if(stack.size()==2)
			{
				a = stack.pop();
				b = stack.pop();
				op = vc.get(vc_idx);
				stack.add(cal(b, a, op));
				vc_idx++;
				
				if(vc_idx==vc.size())
				{
					result = stack.pop();
					break;
				}
			}
			stack.add(arr[i]);
			i++;
		}
		
		return result;
	}
	
	public static void backtracking(List<String> operator, List<String> cur_operator, int pos, int[] arr)
	{
		if(cur_operator.size()==N-1)
		{	
			int result = makeExp(arr, cur_operator);
			max_val = Math.max(max_val, result);
			min_val = Math.min(min_val, result);
			return;
		}
		
		for(int i=0; i<operator.size(); i++)
		{
			if(choosen[i])
			{
				continue;
			}
			
			choosen[i] = true;
			cur_operator.add(operator.get(i));
			
			backtracking(operator, cur_operator, pos+1, arr);
			
			choosen[i] = false;
			cur_operator.remove(cur_operator.size()-1);
		}
	}
	
	public static void solution(int[] arr, int[] op)
	{
		List<String> operator = getOperationList(op);
		List<String> cur_operator = new ArrayList<String>();
		
		choosen = new boolean[N-1];
		backtracking(operator, cur_operator, 0, arr);	
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		int[] arr = new int[N];
		int[] op = new int[4];
		
		// 피연산자 N개 입력
		String[] str = br.readLine().split(" ");
		for(int i=0;i<arr.length;i++)
		{
			arr[i] = Integer.parseInt(str[i]);
		}
		
		// 연산자 입력 
		str = br.readLine().split(" ");
		for(int i=0;i<4;i++)
		{
			op[i] = Integer.parseInt(str[i]);
		}
		
		solution(arr, op);
		System.out.println(max_val);
		System.out.println(min_val);
	}
}

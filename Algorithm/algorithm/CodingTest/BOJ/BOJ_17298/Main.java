package BOJ.BOJ_17298;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;


public class Main {

	// stack api 사용
	public static String solution(int[] seq, int n)
	{
		StringBuilder sb = new StringBuilder();
		Stack<Integer> stack = new Stack<Integer>();
		
		stack.push(0);	// 수열에서 0번째 값은 stack에서 꺼낼것이 없으므로 현재 인덱스 추가
		
		for(int i=1;i<n;i++)
		{
			// seq[i]와 seq[이전 인덱스] 비교 수행
			while(!stack.isEmpty() && seq[stack.peek()]<seq[i])
			{
				seq[stack.pop()] = seq[i];
			}
			// stack이 비었거나 seq[i]보다 크므로 i를 stack에 추가
			stack.push(i);
		}
		
		// 위 반복문이 끝났음에도 불구하고 남은 stack의 인덱스 값들은 
		// 자신보다 큰 값이 없거나 비교할 요소가 없는 경우임
		// 스택에 남아있는 인덱스 위치의 값들은 -1로 저장
		while(!stack.isEmpty())
		{
			seq[stack.pop()] = -1;
		}

		// 출력문 저장
		Arrays.stream(seq)
				.forEach(item->sb.append(item+" "));
		String result = sb.toString().trim();
		
		return result;
	}

	// stack으로 int형 배열 사용
	public static String solution2(int[] seq, int n)
	{
		StringBuilder sb = new StringBuilder();
		int[] stack = new int[n];
		int topIndex = 0;
		int size = 1;
		
		stack[0] = 0;	// 수열에서 0번째 값은 stack에서 꺼낼것이 없으므로 현재 인덱스 추가
		
		for(int i=1;i<n;i++)
		{
			// seq[i]와 seq[이전 인덱스] 비교 수행
			while(size!=0 && seq[stack[topIndex]]<seq[i])
			{
				seq[stack[topIndex]] = seq[i];
				topIndex--;
				size--;
			}
			// stack이 비었거나 seq[i]보다 크므로 i를 stack에 추가
			stack[++topIndex] = i;
			size++;
		}
		
		// 위 반복문이 끝났음에도 불구하고 남은 stack의 인덱스 값들은 
		// 자신보다 큰 값이 없거나 비교할 요소가 없는 경우임
		// 스택에 남아있는 인덱스 위치의 값들은 -1로 저장
		while(size!=0)
		{
			seq[stack[topIndex]] = -1;
			topIndex--;
			size--;
		}

		// 출력문 저장
		Arrays.stream(seq)
				.forEach(item->sb.append(item+" "));
		String result = sb.toString().trim();
		
		return result;
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		// String Array to Integer Array
		int[] seq = Arrays.stream(
						br.readLine()
							.split(" "))
								.mapToInt(Integer::parseInt)
									.toArray();
		
		System.out.println(solution2(seq, n));		
	}
}

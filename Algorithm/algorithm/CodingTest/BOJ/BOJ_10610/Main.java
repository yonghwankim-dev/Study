package BOJ.BOJ_10610;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;



public class Main {
	

	/* 
	 * 3�� ��� ������ : �� �ڸ��� ���� 3�� ���
	 * 30�� ��� ������ : 3�� ����̸鼭 ���� �ڸ��� 0�μ�
	 * 
	 * ��, 30�� ��� �������� �� �ڸ��� ���� 3�� ����̰� ���� �ڸ��� 0�μ��̸� 30�� ����̴�.
	 * */
	public static String solution(List<Integer> numbers)
	{
		Collections.sort(numbers, Collections.reverseOrder());
		int sum = numbers.stream().mapToInt(Integer::intValue).sum();
		
		if(sum%3==0 && numbers.get(numbers.size()-1)==0)
		{
			return numbers.stream().map(String::valueOf).collect(Collectors.joining());
		}
		else
		{
			return "-1";
		}
		
	}
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		List<Integer> numbers = Arrays.asList(br.readLine().split(""))
								.stream()
								.map(Integer::parseInt)
								.collect(Collectors.toList());
		
		System.out.println(solution(numbers));
	}
}

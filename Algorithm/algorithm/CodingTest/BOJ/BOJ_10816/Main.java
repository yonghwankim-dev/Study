package BOJ.BOJ_10816;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Main {
	
	public static String solution(List<Integer> my_cards, List<Integer> cards)
	{
		StringBuilder sb = new StringBuilder();
		Map<Integer, Integer> map = new HashMap<>();
		
		for(int card : my_cards)
		{
			// hashmap�� card�� ���ԵǾ� ���� ���� ��� 1�� �ʱ�ȭ
			if(!map.containsKey(card))
			{
				map.put(card, 1);
			}
			else // ���ԵǾ� �ִ� ��� 1 ����
			{
				map.put(card, map.get(card)+1);
			}
		}
		
		// cards ����Ʈ�� ������ ������ ���� ī��Ʈ �� ����
		for(int card : cards)
		{
			if(map.containsKey(card))
			{
				sb.append(map.get(card) + " ");
			}
			else
			{
				sb.append(0 + " ");
			}
		}
		
		return sb.toString().trim();
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		List<Integer> my_cards = Arrays.asList(br.readLine().split(" ")).stream().map(Integer::parseInt).collect(Collectors.toList());
		int m = Integer.parseInt(br.readLine());
		List<Integer> cards = Arrays.asList(br.readLine().split(" ")).stream().map(Integer::parseInt).collect(Collectors.toList());
		
		System.out.println(solution(my_cards, cards));
		
	}
}

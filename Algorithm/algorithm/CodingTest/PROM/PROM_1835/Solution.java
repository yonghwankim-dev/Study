package PROM.PROM_1835;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Solution{
	static boolean[] chosen;
	static int[] nums;
	static Map<String, Integer> position;
	static int person;
	static String[] data;
	static final String[] FRIENDS = {"", "A", "C", "F", "J", "M", "N", "R", "T"};

	public static int DFS(int level)
	{
		int count = 0;
		if(level == person)
		{
			for(String condition : data)
			{
				String[] split = condition.split("");
				int p1 = position.get(split[0]);
				int p2 = position.get(split[2]);
				String r = split[3];
				int interval = Integer.parseInt(split[4]);
				
				if(!isSatisfiedInterval(p1, p2, r, interval))
				{
					return count;
				}
			}
			count++;
			return count;
		}

		for(int i = 1; i <= person; i++)
		{
			if(!chosen[i])
			{
				position.put(FRIENDS[level+1], nums[i]);
				chosen[i] = true;
				count += DFS(level + 1);
				chosen[i] = false;
			}
		}
		return count;
	}
	
	private static boolean isSatisfiedInterval(int p1, int p2, String r, int interval)
	{
		if(r.equals("="))
		{
			// |p1 위치 - b 위치| = interval + 1
			return Math.abs(p1-p2) == interval + 1;
		}
		else if(r.equals(">"))
		{
			// |p1 위치 - b 위치| > interval + 1
			return Math.abs(p1-p2) > interval + 1;
		}
		// |p1 위치 - b 위치| < interval + 1
		return Math.abs(p1-p2) < interval + 1;
	}
	
    public static int solution(int n, String[] data) {
		Solution.person = 8;
		Solution.nums = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
		Solution.chosen = new boolean[person+1];
		Solution.position = new HashMap<>();
		Solution.data = data;
		Arrays.stream(FRIENDS).forEach(f->Solution.position.put(f,0));

        int answer = Solution.DFS(0);
    	
        return answer;
    }
}

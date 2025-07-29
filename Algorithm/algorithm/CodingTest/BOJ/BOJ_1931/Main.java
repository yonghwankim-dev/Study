package BOJ.BOJ_1931;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
	static class Conference
	{
		int start;
		int end;

		public Conference(int start, int end)
		{
			this.start = start;
			this.end = end;
		}
	}

	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int n = Integer.parseInt(br.readLine());
		String[] times = new String[n];

		for(int i=0;i<n;i++) {
			times[i] = br.readLine();
		}

		int answer = Main.solution(times);
		System.out.println(answer);
	}

	public static int solution(String[] times){
		int answer = 0;
		List<Conference> conferences = createConferenceList(times);
		Comparator<Conference> ascByEndTime = Comparator.comparingInt(c -> c.end);
		Comparator<Conference> ascByStartTime = Comparator.comparingInt(c -> c.start);
		Collections.sort(conferences, ascByEndTime.thenComparing(ascByStartTime));

		Conference prev = null;
		for(Conference c : conferences)
		{
			// ù��° ȸ���ΰ��
			if(prev == null) {
				answer++;
				prev = c;
				continue;
			}

			if(c.start >= prev.end){
				answer++;
				prev = c;
			}

		}
		return answer;
	}

	private static List<Conference> createConferenceList(String[] times) {
		List<Conference> result = new ArrayList<>();
		int end;
		int start;
		for(String time : times){
			String[] info = time.split(" ");
			start = Integer.parseInt(info[0]);
			end   = Integer.parseInt(info[1]);
			result.add(new Conference(start, end));
		}
		return result;
	}

}



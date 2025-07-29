package PROM.PROM_77484;

import java.util.ArrayList;
import java.util.List;

public class Solution{
	private static final int[] RANK = {6,6,5,4,3,2,1};
    public static int[] solution(int[] lottos, int[] win_nums) {
		int[] answer = new int[2];
		int zero_count;	// ���� ����
		int win_count;  // ��÷ ����
		List<Integer> win_nums_list = toIntegerList(win_nums);

		zero_count = getZeroCount(lottos);
        win_count = getLottoCount(win_nums_list, lottos);	// ��÷ ����

        answer[0] = getMaximumRank(win_count, zero_count);
        answer[1] = getMinimumRank(win_count);
        return answer;
    }


	private static List<Integer> toIntegerList(int[] arr){
		List<Integer> result = new ArrayList<>();
		for(int num : arr){
			result.add(num);
		}
		return result;
	}

	private static int getZeroCount(int[] arr){
		int count = 0;
		for(int num : arr){
			count = isZero(num) ? count+1 : count;
		}
		return count;
	}


	private static int getLottoCount(List<Integer> list, int[] lottos) {
		int count = 0;
		for(int lotto : lottos){
			if(list.contains(lotto)){
				count++;
			}
		}
		return count;
	}

	private static int getMaximumRank(int win_count, int zero_count){
		return RANK[win_count + zero_count];
	}

	private static int getMinimumRank(int win_count){
		return RANK[win_count];
	}

	private static boolean isZero(int number){
		return number == 0;
	}
}

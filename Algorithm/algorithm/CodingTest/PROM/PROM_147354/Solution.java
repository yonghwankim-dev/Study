package PROM.PROM_147354;

import java.util.Arrays;

public class Solution {
	public int solution(int[][] data, int col, int row_begin, int row_end) {
		int answer = 0;
		// col번째 기준 asc, 1번째 기준 desc
		Arrays.sort(data, (arr1, arr2)->{
			if (arr1[col-1] == arr2[col-1]){
				return Integer.compare(arr2[0], arr1[0]);
			}else{
				return Integer.compare(arr1[col-1], arr2[col-1]);
			}
		});

		// row_begin ~ row_end 범위의 S_i의 합계
		int sum;
		int reduce = 0;
		for(int row = row_begin - 1; row <= row_end - 1; row++){
			sum = sum(data, row);
			reduce = sum ^ reduce;
		}
		return reduce;
	}

	private int sum(int[][] data, int row){
		int result = 0;
		for (int i = 0; i < data[row].length; i++){
			result += (data[row][i] % (row + 1));
		}
		return result;
	}
}

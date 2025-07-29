package PROM.PROM_250121;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Solution {
	public int[][] solution(int[][] data, String ext, int val_ext, String sort_by) {
		int[][] answer = {};
		Map<String, Integer> columnIndexMap = Map.of(
			"code", 0,
			"date", 1,
			"maximum", 2,
			"remain", 3
		);

		int extIndex = columnIndexMap.get(ext);
		List<int[]> list = Arrays.stream(data)
			.filter(item -> item[extIndex] < val_ext)
			.collect(Collectors.toList());
		int sortIndex = columnIndexMap.get(sort_by);
		Collections.sort(list, Comparator.comparingInt(arr -> arr[sortIndex]));
		return list.stream().toArray(int[][]::new);
	}
}

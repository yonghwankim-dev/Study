package PROM.PROM_72412;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * complete
 * 
 * reference
 * https://loosie.tistory.com/265
 *
 */
public class Solution2 {
	
	private Map<String, List<Integer>> allInfo; 
	
	public Solution2() {
		allInfo = new HashMap<String, List<Integer>>();
	}
		
    public int[] solution(String[] info, String[] query) {
    	int[] answer = new int[query.length];
    	
    	// 1. info의 모든 경우의 수 map에 저장
    	// 각 지원자에 대한 query에 뽑힐 수 있는 경우의 수를 해시맵에 저장
    	// key : 뽑힐수 있는 요구조건, value : 코딩테스트 점수
    	for(int i = 0; i < info.length; i++) {
    		dfs("", 0, info[i].split(" "));
    	}
    	
    	// 2. map에 저장된 점수 리스트(list) 오름차순으로 정렬
    	for(List<Integer> scoreList : allInfo.values()) {
    		Collections.sort(scoreList);
    	}
    	
    	// 3. 이진 탐색
    	for(int i = 0; i < query.length; i++) {
    		//    "java and backend and junior and pizza 100"
    		// -> "javabackendjuniorpizza 100"
    		query[i] = query[i].replaceAll(" and ", "");
    		// query[i] = "javabackendjuniorpizza 100" 
    		// -> str = ["javabackendjuniorpizza", "100"]
    		String[] str = query[i].split(" ");
    		
    		int score = Integer.parseInt(str[1]);
    		
    		answer[i] = search(str[0], score);
    	}
    
        return answer;
    }
    
	private void dfs(String pos, int depth, String[] info) {
		if(depth == 4) {
			List<Integer> in = null;
			if(!allInfo.containsKey(pos)) {
				in = new ArrayList<>();
				in.add(Integer.parseInt(info[4]));
				allInfo.put(pos, in);
			}
			else{
				allInfo.get(pos).add(Integer.parseInt(info[4]));
			}
			return;
		}
		
		dfs(pos + "-", depth + 1, info);
		dfs(pos + info[depth], depth + 1, info);
	}
	
	private int search(String pos, int score) {
		if(!allInfo.containsKey(pos)) {
			return 0;
		}
		
		List<Integer> scoreList = allInfo.get(pos);
		int start = 0, end = scoreList.size() - 1;
		
		while(start <= end) {
			int mid = (start + end) / 2;
			if(scoreList.get(mid) < score) {
				start = mid + 1;
			}
			else
			{
				end = mid - 1;
			}
		}
		
		return scoreList.size() - start;
	}
}

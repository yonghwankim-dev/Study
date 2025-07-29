package PROM.PROM_84325;

import java.util.HashMap;
import java.util.Map;

class Solution {
	static class Table{
		// key : 직업군, value : 직업군에 대한 언어 기준 map
		Map<String, Map<String,Integer>> map = new HashMap<String, Map<String,Integer>>();
		
		public Table(String[] table)
		{
			String[] str = null; // str : ["직업군", "5점언어", "4점언어", ... , "1점언어"]
			for(String t : table)
			{
				str = t.split(" ");
				
				String job = str[0];	// 직업군
				Map<String,Integer> dummy = new HashMap<String, Integer>();	// 직업군에 대한 언어 기준 map
				
				// 각각의 언어에 대한 점수 삽입 (5점 ~ 1점 순서)
				for(int i=1;i<str.length;i++)
				{
					dummy.put(str[i], str.length-i);
				}
				
				map.put(job, dummy);
			}	
		}

		// 참여자에 대한 추천 직업군 매칭
		public String getRecommandJob(Participant p) {
			int maxScore = 0;			// 최고 점수
			String recommandJob = "";	// 추천 직업군
			
			for(String job : map.keySet())
			{
				int sum = 0;
				for(String lang : p.lang_map.keySet())
				{ 
					sum += ((map.get(job).containsKey(lang) ? 
								map.get(job).get(lang) : 0) 
							* p.lang_map.get(lang));
				}
				
				// 점수 총합이 최고점수와 동일하고 recommandJob이 job보다 사전순으로 낮은경우
				if(sum==maxScore && recommandJob.compareTo(job)>0)
				{
					recommandJob = job;
				}
				else if(sum>maxScore)
				{
					maxScore = sum;
					recommandJob = job;
				}
				
			}
			
			return recommandJob;
		}
	}
	
	static class Participant{
		Map<String,Integer> lang_map = new HashMap<>();	// key : 사용언어, value : 언어 선호도 점수
		
		public Participant(String[] languages, int[] preference)
		{
			// 사용언어에 대한 언어 선호도 값 저장
			for(int i=0;i<languages.length;i++)
			{
				lang_map.put(languages[i], preference[i]);
			}
		}
	}
	
    public String solution(String[] table, String[] languages, int[] preference) {
		Participant p = new Participant(languages, preference);	// 참여자 정보 초기화
		Table t = new Table(table);	// 직업군 추천 테이블 초기화
		
        return t.getRecommandJob(p); // 참여자에 대한 직업군 추천
    }
}

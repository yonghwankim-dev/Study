package PROM.PROM_42579;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Solution {
	class Music{
		String genre;
		int play;
		int id;
		
		public Music(String genre, int play, int id) {
			this.genre = genre;
			this.play = play;
			this.id = id;
		}

		@Override
		public String toString() {
			return String.format("[genre=%s, play=%d, id=%d]", genre, play, id);
		}		
	}
	class GenreInfo{
		String genre;
		int totalPlay;
		
		public GenreInfo(String genre, int totalPlay) {
			this.genre = genre;
			this.totalPlay = totalPlay;
		}

		@Override
		public String toString() {
			return String.format("[genre=%s, totalPlay=%d]", genre, totalPlay);
		}
		
		
	}
	
    public int[] solution(String[] genres, int[] plays) {
        List<Integer> answer = new ArrayList<Integer>();
        
        String genre = null; // 장르
        int play = 0;		 // 재생 횟수
        int n = plays.length;// 앨범 갯수
        Map<String, PriorityQueue<Music>> map = new HashMap<String, PriorityQueue<Music>>(); //  key : 장르, value : 음악정보
        Map<String, GenreInfo> totalPlayMap = new HashMap<String, GenreInfo>(); // key : 장르, value : 플레이횟수 총합
        
        Comparator<Music> comp = new Comparator<Solution.Music>() {
			
			@Override
			public int compare(Music o1, Music o2) {
				if(o1.play > o2.play) { // 장르내에서 많이 재생된 노래 순서로
					return -1;
				}
				else if(o1.play < o2.play) {
					return 1;
				}
				else { // 재생횟수가 같은 경우 고유번호가 낮은 순서로
					return o1.id - o2.id;
				}
			}
		};
        
        for(int i = 0; i < n; i++) {
        	genre = genres[i];
        	play  = plays[i];
        	
        	if(!map.containsKey(genre)) { // 장르가 없으면 리스트 추가
        		map.put(genre, new PriorityQueue<>(comp));
        	}
        	
        	map.get(genre).add(new Music(genre, play, i));
        	
        	if(!totalPlayMap.containsKey(genre)) {
        		totalPlayMap.put(genre, new GenreInfo(genre, 0));
        	}
        	totalPlayMap.get(genre).totalPlay += play;
        	
        }
        
        
                       
        // 1. 속한 노래가 가장 많이 재생된 장르 탐색
        List<String> genreList = new ArrayList<String>(); // 속한 노래가 가장 많이 재생된 장르 리스트
        
        while(totalPlayMap.size() != 0) {
        	
        	int max = 0;
        	String maxGenre = null;
        	
        	// 현재 totalPlayMap에서 가장 많이 재생된 장르를 탐색
        	for(String g : totalPlayMap.keySet()) {
        		if(totalPlayMap.get(g).totalPlay > max) {
        			maxGenre = g;
        			max = totalPlayMap.get(g).totalPlay;
        		}
        	}
        	genreList.add(maxGenre);
        	totalPlayMap.remove(maxGenre);
        }
        
        // 2. 장르 내에서 많이 재생된 노래
        // 3. 장르 내에서 재생횟수가 같은 노래중에서는 고유번호가 낮은 노래
        for(String g : genreList) {
        	PriorityQueue<Music> pq = map.get(g);
        	
        	for(int i = 0; i < 2; i++) {
        		if(pq.size() == 0) {
        			break;
        		}
        		answer.add(pq.poll().id);
        	}
        }

        return answer.stream().mapToInt(Integer::intValue).toArray();
    }
	
	public static void main(String[] args) {
		String[] genres = {"A"};
		int[] plays = {1};
		// [0,1,2,4]
		System.out.println(Arrays.toString(new Solution().solution(genres, plays)));
	}
}
